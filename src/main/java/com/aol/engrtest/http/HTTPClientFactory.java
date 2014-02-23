package com.aol.engrtest.http;

import org.apache.log4j.Logger;

/**
 * The HTTPClientFactory class used to initialize web client.
 * @author Pankaj Patel
 */
public class HTTPClientFactory {

    private static final Logger logger = Logger.getLogger(HTTPClientFactory.class);

    /**
     * Method instantiates web application client implementor class based on passed parameter value.
     * @param clientName Implementor web application client class name.
     * @return Web application client instance.
     */
    public static HTTPClient getInstance(String clientName) {
        try {
            return (HTTPClient) Class.forName("com.aol.engrtest.http.clients." + clientName).newInstance();
        } catch (InstantiationException ex) {
            logger.error("Error while instanciating client: " + clientName, ex);
        } catch (IllegalAccessException ex) {
            logger.error("Error while instanciating client: " + clientName, ex);
        } catch (ClassNotFoundException ex) {
            logger.error("Client class [" + clientName + "] not exist on system: " + clientName, ex);
        }
        return null;
    }
}
