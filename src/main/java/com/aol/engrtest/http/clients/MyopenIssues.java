package com.aol.engrtest.http.clients;

import com.aol.engrtest.http.HTTPClient;
import com.aol.engrtest.http.ResponseCode;
import com.aol.engrtest.http.Result;
import com.aol.engrtest.utils.AppConstants;
import com.aol.engrtest.utils.HTTPClientException;
import com.aol.engrtest.utils.WebUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * The MyopenIssues class used to create new user account on MyopenIssues using HTTP POST method.
 * @author Pankaj Patel
 */
public class MyopenIssues extends HTTPClient {

    private static final Logger logger = Logger.getLogger(MyopenIssues.class);
    private static final String MYOPENISSUES_HOME_URL = "http://myopenissues.com/magento/index.php/customer/account/create/";
    private static final String MYOPENISSUES_SIGNUP_URL = "http://myopenissues.com/magento/index.php/customer/account/createpost/";

    public MyopenIssues() {
    }

    @Override
    public Result execute(HttpServletRequest request, String action, String httpMethod) {
        Result result = null;
        if (AppConstants.SIGNUP.equalsIgnoreCase(action)) {
            result = signupUser(request);
        } else {
            throw new UnsupportedOperationException("Opration [" + action + "] not supported yet!");
        }
        return result;
    }

    private Result signupUser(HttpServletRequest request) {
        Result result = new Result();
        try {

            //Set request parameters to register user on client application.
            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
            Map<String, String> inputs = WebUtils.getFormInputNames(AppConstants.WEB_CLIENT_MYOPENISSUES.toLowerCase(), AppConstants.SIGNUP, AppConstants.HTTP_POST);
            for (Iterator<Entry<String, String>> it = inputs.entrySet().iterator(); it.hasNext();) {
                Entry<String, String> entry = it.next();
                urlParameters.add(new BasicNameValuePair(entry.getKey(), request.getParameter(entry.getValue())));
            }

            //Get cookie: call web application home page.
            HttpResponse response = super.sendGet(MYOPENISSUES_HOME_URL, null, null);
            setCookies(response.getFirstHeader(AppConstants.HTTP_HEADER_SET_COOKIE) == null ? AppConstants.EMPTY : response.getFirstHeader(AppConstants.HTTP_HEADER_SET_COOKIE).toString());

            //Set extra parameters if required to call perticular client application.
            Map<String, String> headers = new HashMap<String, String>();
            headers.put(AppConstants.HTTP_HEADER_REFERER, MYOPENISSUES_HOME_URL);
            headers.put(AppConstants.HTTP_HEADER_CONTENT_TYPE, AppConstants.HTTP_HEADER_CONTENT_TYPE_URLENCODED);

            //Call POST method from super class.
            response = super.sendPost(MYOPENISSUES_SIGNUP_URL, urlParameters, headers);
            int responseCode = response.getStatusLine().getStatusCode();

            switch (responseCode) {
                case 301:
                case 302:
                    //Redirect URL if status code 301, 302 Using GET method.(This is client application specific)
                    response.getEntity().getContent().close();
                    String redirectURL = response.getFirstHeader(AppConstants.HTTP_HEADER_LOCATION).getValue();
                    logger.debug("REDIRECT URL: " + redirectURL);
//                    HttpGet httpGetReq = new HttpGet(redirectURL);
//                    httpGetReq.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:27.0) Gecko/20100101 Firefox/27.0");
//                    httpGetReq.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//                    httpGetReq.setHeader("Accept-Language", "en-US,en;q=0.5");
//                    HttpResponse httpGetRes = client.execute(httpGetReq);
                    HttpResponse httpGetRes = sendGet(redirectURL, null, null);

                    String html = EntityUtils.toString(httpGetRes.getEntity());
                    Document doc = Jsoup.parse(html);
                    Elements links = doc.select("li[class=error-msg] span");
                    if (links != null && !AppConstants.EMPTY.equals(links.toString())) {
                        result.setStatus(ResponseCode.FAILUER);
                        result.setMessage(links.toString());
                    } else {
                        result.setStatus(ResponseCode.SUCCCESS);
                        result.setMessage(html);
                    }
                    break;
                case 200:
                    //Get content of page to display in our application in case of success response.
                    result.setStatus(ResponseCode.SUCCCESS);
                    result.setMessage(EntityUtils.toString(response.getEntity()));
                    break;
                default:
                    //Set failuer status if any other response code.
                    result.setStatus(ResponseCode.FAILUER);
                    result.setMessage("Error while connecting to client application: " + MYOPENISSUES_SIGNUP_URL);
            }
        } catch (HTTPClientException ex) {
            logger.error("Error while connecting to client application: " + MYOPENISSUES_SIGNUP_URL, ex);
            result.setStatus(ResponseCode.FAILUER);
            result.setMessage("Error while connecting to client application: " + MYOPENISSUES_SIGNUP_URL + " --- " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Error while connecting to client application: " + MYOPENISSUES_SIGNUP_URL, ex);
            result.setStatus(ResponseCode.FAILUER);
            result.setMessage("Error while connecting to client application: " + MYOPENISSUES_SIGNUP_URL + " --- " + ex.getMessage());
        }
        return result;
    }

    private boolean validate(List<NameValuePair> urlParameters) {
        if (urlParameters != null) {
            for (Iterator<NameValuePair> it = urlParameters.iterator(); it.hasNext();) {
                NameValuePair nameValuePair = it.next();
                if ("firstname".equalsIgnoreCase(nameValuePair.getName()) && (nameValuePair.getValue() == null || nameValuePair.getValue().length() < 1)) {
                    return false;
                } else if ("lastname".equalsIgnoreCase(nameValuePair.getName()) && (nameValuePair.getValue() == null || nameValuePair.getValue().length() < 1)) {
                    return false;
                } else if ("email".equalsIgnoreCase(nameValuePair.getName()) && (nameValuePair.getValue() == null || nameValuePair.getValue().length() < 1)) {
                    return false;
                } else if ("password".equalsIgnoreCase(nameValuePair.getName()) && (nameValuePair.getValue() == null || nameValuePair.getValue().length() < 1)) {
                    return false;
                } else if ("confirmation".equalsIgnoreCase(nameValuePair.getName()) && (nameValuePair.getValue() == null || nameValuePair.getValue().length() < 1)) {
                    return false;
                }
            }
        }
        return true;
    }
//    private HttpResponse getPageContent(String url) throws Exception {
//        HttpGet request = new HttpGet(url);
//        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:27.0) Gecko/20100101 Firefox/27.0");
//        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        request.setHeader("Accept-Language", "en-US,en;q=0.5");
//        HttpResponse response = client.execute(request);
//        return response;
//    }
}
