package com.aol.engrtest.http;

import com.aol.engrtest.utils.AppConstants;
import com.aol.engrtest.utils.HTTPClientException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

/**
 *
 * @author Pankaj Patel
 */
public abstract class HTTPClient {

    private static final Logger logger = Logger.getLogger(HTTPClient.class);
    private String cookies;
    protected HttpClient client = HttpClientBuilder.create().build();

    /**
     * The implementor class must implement this method to connect to client application. From overridden method user can set extra header parameters
     * with HTTP method if required. Call different HTTP methods sendPost() or sendGet() to connect.
     **/
    public abstract Result execute(HttpServletRequest request, String action, String httpMethod);

    /**
     * Method used to POST data on client application.
     * @param uri The client web application URI
     * @param parameters Input parameters(Key-Value pair).
     * @param headers If additional header required to call service.
     * @throws HTTPClientException if any exception occurs while connecting to client application.
     **/
    protected final HttpResponse sendPost(String uri, List<NameValuePair> parameters, Map<String, String> headers) throws HTTPClientException {
        CookieHandler.setDefault(new CookieManager());
        try {
            HttpPost post = new HttpPost(uri);

            //Default HTTP headers
            post.setHeader(AppConstants.HTTP_HEADER_CONNECTION, AppConstants.HTTP_HEADER_CONNECTION_KEEP_ALIVE);
            post.setHeader(AppConstants.HTTP_HEADER_USER_AGENT, AppConstants.HTTP_HEADER_USER_AGENT_MOZILLA);
            post.setHeader(AppConstants.HTTP_HEADER_ACCEPT, AppConstants.HTTP_HEADER_ACCEPT_TEXT_HTML);
            post.setHeader(AppConstants.HTTP_HEADER_ACCEPT_LANGUAGE, AppConstants.HTTP_HEADER_ACCEPT_LANGUAGE_EN_US);

            //Set cookie value
            if (getCookies() != null) {
                post.setHeader(AppConstants.HTTP_HEADER_COOKIE, getCookies());
            }

            //requrest paramters;
            if (parameters != null) {
                post.setEntity(new UrlEncodedFormEntity(parameters));
            }

            //Extra headers if required for cleint website
            if (headers != null) {
                for (Iterator<Entry<String, String>> it = headers.entrySet().iterator(); it.hasNext();) {
                    Entry<String, String> entry = it.next();
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }

            HttpResponse response = client.execute(post);

            int responseCode = response.getStatusLine().getStatusCode();
            logger.debug("HTTP STATUS CODE: " + responseCode);

            return response;

        } catch (Exception ex) {
            throw new HTTPClientException("Error while connecting to client - " + ex.getMessage());
        }
    }

    /**
     * Method used to call client API using HTTP GET method.
     * @param uri The client web application URI
     * @param parameters Input parameters(Key-Value pair).
     * @param headers If additional header required to call service.
     * @throws HTTPClientException if any exception occurs while connecting to client application.
     **/
    protected final HttpResponse sendGet(String uri, List<NameValuePair> parameters, Map<String, String> headers) throws HTTPClientException {
        CookieHandler.setDefault(new CookieManager());
        try {
            HttpGet get = new HttpGet(uri);

            get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:27.0) Gecko/20100101 Firefox/27.0");
            get.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            get.setHeader("Accept-Language", "en-US,en;q=0.5");

            //Extra headers if required for cleint website
            if (headers != null) {
                for (Iterator<Entry<String, String>> it = headers.entrySet().iterator(); it.hasNext();) {
                    Entry<String, String> entry = it.next();
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }

            HttpResponse response = client.execute(get);

            int responseCode = response.getStatusLine().getStatusCode();
            logger.debug("HTTP STATUS CODE: " + responseCode);

            return response;

        } catch (Exception ex) {
            throw new HTTPClientException("Error while connecting to client - " + ex.getMessage());
        }
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
}
