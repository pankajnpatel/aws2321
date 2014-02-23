package com.aol.engrtest.utils;

/**
 *
 * @author Pankaj Patel
 */
public class HTTPClientException extends Exception {

    public HTTPClientException(Throwable thrwbl) {
        super(thrwbl);
    }

    public HTTPClientException(String message, Throwable thrwbl) {
        super(message, thrwbl);
    }

    public HTTPClientException(String message) {
        super(message);
    }

    public HTTPClientException() {
        super();
    }
}
