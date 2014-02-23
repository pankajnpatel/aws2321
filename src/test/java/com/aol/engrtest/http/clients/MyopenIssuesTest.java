package com.aol.engrtest.http.clients;

import com.aol.engrtest.http.ResponseCode;
import com.aol.engrtest.http.Result;
import com.aol.engrtest.utils.AppConstants;
import com.aol.engrtest.utils.WebUtils;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Admin
 */
public class MyopenIssuesTest extends TestCase {

    public MyopenIssuesTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of execute method, of class MyopenIssues.
     */
    public void testExecute() {
        System.out.println("execute");

        DummyHTTPServletRequest request = new DummyHTTPServletRequest();
        request.setParameter("success_url", "");
        request.setParameter("error_url", "");
        request.setParameter("firstname", "Pankaj");
        request.setParameter("lastname", "Patel");
        request.setParameter("email", "discodiwane@yahoo.com");
        request.setParameter("password", "Pankaj1");
        request.setParameter("confirmation", "Pankaj1");

        Map<String, String> inputs = WebUtils.getFormInputNames(AppConstants.WEB_CLIENT_MYOPENISSUES.toLowerCase(), "signup", "post");        
        assertEquals("No. of 7 request parameters are required to register account.", 7, inputs.size());

        MyopenIssues instance = new MyopenIssues();
        Result result = instance.execute(request, AppConstants.SIGNUP, "post");

        assertNotNull("Error while connecting to client application.", result);
        
        assertSame(result.getMessage(), ResponseCode.SUCCCESS, result.getStatus());
    }
}
