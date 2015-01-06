package com.arrking.express;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.arrking.android.database.Properties;
import com.arrking.android.exception.DBException;
import com.arrking.android.util.HTTPRequestHelper;
import com.arrking.express.common.ServerURLHelper;
import com.arrking.express.model.ErrorMessage;
import com.arrking.express.model.User;
import com.google.gson.Gson;

import cn.trinea.android.common.util.StringUtils;

/**
 * Created by hain on 05/01/2015.
 */
public class SplashScreenActivity extends Activity {
    private static final String CLASSNAME = SplashScreenActivity.class.getName();
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    // status flags
    private final int LOGIN_NONE = 1;
    private final int LOGIN_AVIL = 2;

    private HTTPRequestHelper httpRequestHelper;

    private Handler httpReqHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            String resp = (String) msg.getData().get("RESPONSE");
            Log.d(CLASSNAME, resp);
            switch (msg.what) {
                case 200:
                    String userIdVal = properties.get("userId");
                    Log.d(CLASSNAME, String.format("User %s logged in successfully.",
                            properties.get("userId")));
                    User u = gson.fromJson(resp, User.class);
                    if (userIdVal.equals(u.getId())) {
                        Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                        SplashScreenActivity.this.startActivity(i);
                        SplashScreenActivity.this.finish();
                    } else {
                        Intent i = new Intent(SplashScreenActivity.this, LoginPageActivity.class);
                        SplashScreenActivity.this.startActivity(i);
                        SplashScreenActivity.this.finish();
                    }
                    break;
                case 401:
                    ErrorMessage err = gson.fromJson(resp, ErrorMessage.class);
                    Log.w(CLASSNAME, String.format("Can not login user %s .",
                            properties.get("userId")));
                    Intent i = new Intent(SplashScreenActivity.this, LoginPageActivity.class);
                    SplashScreenActivity.this.startActivity(i);
                    SplashScreenActivity.this.finish();
                    break;
                default:
                    // just bring user to login page
                    // TODO check it network, maybe there is no connection
                    Log.w(CLASSNAME, "what happens ??");
                    Intent it = new Intent(SplashScreenActivity.this, LoginPageActivity.class);
                    SplashScreenActivity.this.startActivity(it);
                    SplashScreenActivity.this.finish();
                    break;
            }
        }
    };

    private static Properties properties;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_NONE:
                    Intent LgIntent = new Intent(SplashScreenActivity.this, LoginPageActivity.class);
                    SplashScreenActivity.this.startActivity(LgIntent);
                    SplashScreenActivity.this.finish();
                    break;
                case LOGIN_AVIL:
                    final String userId = new String(properties.get("userId"));
                    final String userPass = new String(properties.get("userPassword"));
                    // request network
                    (new Thread(new Runnable() {
                        @Override
                        public void run() {
                            httpRequestHelper.performGet(ServerURLHelper.getLoginURL(userId),
                                    userId,
                                    userPass,
                                    ServerURLHelper.getJSONHeaders());
                        }
                    })).start();


                    break;
            }
        }
    };


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_screen);
        properties = Properties.getInstance(this);
        httpRequestHelper = new HTTPRequestHelper(httpReqHandler);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (StringUtils.isEmpty(properties.get("userId")) ||
                        StringUtils.isEmpty(properties.get("userPassword"))
                        ) {
                    Log.d(CLASSNAME, "user is not available.");
                    handler.sendEmptyMessage(LOGIN_NONE);
                } else {
                    Log.d(CLASSNAME, "user is available");
                    handler.sendEmptyMessage(LOGIN_AVIL);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
