package com.arrking.express;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arrking.android.util.HTTPRequestHelper;
import com.arrking.express.common.ServerURLHelper;
import com.arrking.express.model.ErrorMessage;
import com.arrking.express.model.User;
import com.google.gson.Gson;

import java.util.HashMap;

import cn.trinea.android.common.util.StringUtils;

/**
 * Created by hain on 06/01/2015.
 */
public class LoginPageActivity extends Activity {

    private final static String CLASSNAME = LoginPageActivity.class.getName();
    private EditText username;
    private EditText password;
    private Button login;
    private TextView loginLockedTV;
    private TextView attemptsLeftTV;
    private int numberOfRemainingLoginAttempts = 3;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String resp = (String) msg.getData().get("RESPONSE");
            Log.d(CLASSNAME, resp);
            Gson gson = new Gson();
            switch (msg.what) {
                case 200:
                    User user = gson.fromJson(resp, User.class);
                    Log.d(CLASSNAME, " get username " + user.getFirstName());
                    break;
                case 401:
                    ErrorMessage err = gson.fromJson(resp, ErrorMessage.class);
                    Log.w(CLASSNAME, " get error " + err.getErrorMessage());
                    break;
                default:
                    Log.e(CLASSNAME, String.format("unknown msg.what %d from httpRequestHelper",
                            msg.what));
                    break;
            }
        }
    };
    private static HTTPRequestHelper httpRequestHelper = new HTTPRequestHelper(handler);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        setupVariables();
    }

    public void authenticateLogin(View view) {
        // TODO check the username and password should not be null or empty
        if (validateUsernameAndPassword(username.getText().toString(), password.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Hello admin!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Seems like you 're not admin!",
                    Toast.LENGTH_SHORT).show();
            numberOfRemainingLoginAttempts--;
            attemptsLeftTV.setVisibility(View.VISIBLE);
            attemptsLeftTV.setText(String.format(getResources().getString(R.string.login_attempt), numberOfRemainingLoginAttempts));

            if (numberOfRemainingLoginAttempts == 0) {
                login.setEnabled(false);
                loginLockedTV.setVisibility(View.VISIBLE);
                loginLockedTV.setBackgroundColor(getResources().getColor(R.color.red));
                loginLockedTV.setTextColor(getResources().getColor(R.color.white));
                loginLockedTV.setText(R.string.login_locked);
                attemptsLeftTV.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);
            }
        }
    }

    private boolean validateUsernameAndPassword(final String u, final String pass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                httpRequestHelper.performGet(ServerURLHelper.getLoginURL(u),
                        u,
                        pass,
                        ServerURLHelper.getJSONHeaders());
            }
        }).start();
        return true;
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.usernameET);
        password = (EditText) findViewById(R.id.passwordET);
        login = (Button) findViewById(R.id.loginBtn);
        loginLockedTV = (TextView) findViewById(R.id.loginLockedTV);
        attemptsLeftTV = (TextView) findViewById(R.id.attemptsLeftTV);
        attemptsLeftTV.setText(getResources().getString(R.string.login_attempt) + Integer.toString(numberOfRemainingLoginAttempts));
    }
}
