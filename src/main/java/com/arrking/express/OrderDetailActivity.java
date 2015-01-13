package com.arrking.express;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.arrking.android.database.Properties;
import com.arrking.android.util.HTTPRequestHelper;
import com.arrking.express.common.Constants;
import com.arrking.express.common.ServerURLHelper;
import com.arrking.express.model.ActivitiTask;
import com.arrking.express.model.ActivitiTasks;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;


public class OrderDetailActivity extends Activity {

    private Button mBtnClose;
    private HTTPRequestHelper httpRequestHelper;
    private Properties properties;
    private String userId;
    private String userPass;

    private Handler taskDataRequestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            Bundle data = msg.getData();
            String resp = data.getString("RESPONSE");
            Log.d("fangjie","resp:"+resp);
            switch (msg.what) {
                case 200:

                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_detail);

        String id= getIntent().getStringExtra("id");

        httpRequestHelper = new HTTPRequestHelper(taskDataRequestHandler);
        properties = Properties.getInstance(this);
        userId = properties.get(Constants.USER_ID);
        userPass = properties.get(Constants.USER_PASSWORD);

        requestTaskData(id);

        mBtnClose=(Button)findViewById(R.id.btn_closeorder);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });
    }


    private void requestTaskData(final String id) {
        (new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("fangjie",ServerURLHelper.getQueryOrderDetailURL(id));
                httpRequestHelper.performPostJSON(ServerURLHelper.getQueryOrderDetailURL(id),
                        userId,
                        userPass,
                        ServerURLHelper.getJSONHeaders(),
                        ServerURLHelper.getQueryCashierTasksBody()
                );
            }
        })).start();
    }

}
