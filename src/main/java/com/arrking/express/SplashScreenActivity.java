package com.arrking.express;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.arrking.android.database.Properties;
import com.arrking.android.exception.DBException;
import com.arrking.android.util.HTTPRequestHelper;
import com.arrking.express.common.ServerURLHelper;
import com.arrking.express.model.ErrorMessage;
import com.arrking.express.model.User;
import com.google.gson.Gson;

import cn.trinea.android.common.util.StringUtils;
import cn.jpush.android.api.JPushInterface;


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
                case 500:
                    Log.w(CLASSNAME, "timeout event happens ? or server is broken ?");
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
        if (checkInternetConnection(this)) {
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
        } else {
            // the network is unavailable
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_no_network_tip),
                    Toast.LENGTH_SHORT).show();
            (new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Log.e(CLASSNAME, "kill app", e);
                    }
                    SplashScreenActivity.this.finish();
                }
            })).start();
        }

    }


    /**
     * THIS IS FUNCTION FOR CHECKING INTERNET CONNECTION
     *
     * @return TRUE IF INTERNET IS PRESENT ELSE RETURN FALSE
     */
    public boolean checkInternetConnection(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
