package com.arrking.express.common;

import java.util.HashMap;

/**
 * Created by hain on 06/01/2015.
 */
public class ServerURLHelper {

    public static String getLoginURL(String username) {
        if (Constants.serverPort > 0) {
            return String.format("%s://%s:%d/%s/identity/users/%s", Constants.serverProtocol,
                    Constants.serverHost,
                    Constants.serverPort,
                    Constants.serverRestRootPath,
                    username);
        } else {
            return String.format("%s://%s/%s/identity/users/%s", Constants.serverProtocol,
                    Constants.serverHost,
                    Constants.serverRestRootPath,
                    username);
        }
    }

    public static HashMap<String, String> getJSONHeaders(){
        HashMap<String, String> h = new HashMap<String, String>();
        h.put("Accept", "application/json");
        h.put("Content-Type", "application/json");
        return h;
    }
}
