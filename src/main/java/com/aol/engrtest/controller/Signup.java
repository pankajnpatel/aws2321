package com.aol.engrtest.controller;

import com.aol.engrtest.http.HTTPClient;
import com.aol.engrtest.http.HTTPClientFactory;
import com.aol.engrtest.http.ResponseCode;
import com.aol.engrtest.http.Result;
import com.aol.engrtest.utils.AppConstants;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Signup class provides functionality to register user on external website.
 * @author Pankaj Patel
 */
public class Signup extends HttpServlet {

    private static final Logger logger = Logger.getLogger(HTTPClient.class);

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String clientApp = request.getParameter("clientapp");

            HTTPClient client = HTTPClientFactory.getInstance(clientApp);
            Result result = client.execute(request, AppConstants.SIGNUP, AppConstants.HTTP_POST);

            if (result.getStatus() == ResponseCode.SUCCCESS) {
                PrintWriter out = response.getWriter();
                try {
                    out.print(result.getMessage());
                } finally {
                    out.close();
                }
            } else {
                response.sendRedirect("register.jsp?aws_error=" + URLEncoder.encode(result.getMessage(), "UTF-8"));
            }
        } catch (Exception ex) {
            logger.error("Error while signup user: ", ex);
            request.setAttribute("aws_error", "Error while creating user account. Please try again later!!!<br/>" + ex.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private String getApplicationHeaderDiv() {
        return "<div class='header><h2>OneUI Register</h2></div><br/>";
    }

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
