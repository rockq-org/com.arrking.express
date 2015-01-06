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

import java.util.HashMap;

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
            Log.d(CLASSNAME, (String) msg.getData().get("RESPONSE"));
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

    private boolean validateUsernameAndPassword(String s, String s1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> h = new HashMap<String, String>();
                h.put("Accept", "application/json");
                h.put("Content-Type", "application/json");
                httpRequestHelper.performGet("http://bizflow.mybluemix.net/service/identity/users/abby",
                        "abby",
                        "a12345",
                        h);
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
