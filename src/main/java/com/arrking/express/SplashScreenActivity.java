package com.arrking.express;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.arrking.android.database.Properties;
import com.arrking.android.exception.DBException;

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
                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    SplashScreenActivity.this.startActivity(mainIntent);
                    SplashScreenActivity.this.finish();
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (StringUtils.isEmpty(properties.get("username"))) {
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
