package com.aol.engrtest.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * The class WebUtils used to read form input parameters required to call client API.
 * @author Pankaj Patel
 */
public class WebUtils {

    public static Map<String, String> getFormInputNames(String client, String page, String httpmethod) {
        Map<String, String> inputs = new HashMap<String, String>();
        ResourceBundle formInputsProp = ResourceBundle.getBundle(AppConstants.FORM_INPUTS_PROPERTIES + client);
        Enumeration<String> keys = formInputsProp.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.startsWith(client + "." + page + "." + httpmethod)) {
                inputs.put(key.substring((client + "." + page + "." + httpmethod + ".input.").length(), key.length()), formInputsProp.getString(key));
            }
        }
        return inputs;
    }
}
