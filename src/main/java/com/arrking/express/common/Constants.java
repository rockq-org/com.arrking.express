package com.arrking.express.common;

/**
 * Created by hain on 06/01/2015.
 */
public class Constants {

    public static final String serverHost = "bizflow.mybluemix.net";
    public static final String serverProtocol = "http";
    // if server is a testing instance, set the port
    // if the server is running in bluemix, just set it <0
    public static final int serverPort = -1;
    public static final String serverRestRootPath = "service";

    // user properties names
    public static final String USER_FIRST_NAME = "userFirstName";
    public static final String USER_LAST_NAME = "userLastName";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_ID = "userId";
    public static final String USER_URL = "userUrl";
    public static final String USER_PASSWORD = "userPassword";

}
