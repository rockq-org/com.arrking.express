package com.arrking.express;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arrking.android.component.LoadingUI;
import com.arrking.android.database.OrderSQLUtils;
import com.arrking.android.database.Properties;
import com.arrking.android.util.HTTPRequestHelper;
import com.arrking.express.common.Constants;
import com.arrking.express.common.ServerURLHelper;
import com.arrking.express.model.Order;
import com.arrking.express.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;


public class OrderDetailActivity extends Activity {

    private static final String TAG = OrderDetailActivity.class.getName();
    private Button mBtnClose;
    private HTTPRequestHelper httpRequestHelper;
    private Properties properties;
    private String userId;
    private String userPass;

    private TextView mTvTitle;
    private TextView mTvOrder;
    private TextView mTvUser;
    private TextView mTvFood;

    private Order mOrder;
    private String taskId;

    private LoadingUI loadingUI;

    private Handler taskDataRequestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            Bundle data = msg.getData();
            String resp = data.getString("RESPONSE");
            JSONObject resp_json=null;

            switch (msg.what) {
                case 200:
                    removeLoading();
                    try {
                        resp_json=new JSONObject(resp);
                        resp_json=new JSONObject(resp_json.getString("value"));
                        mOrder = gson.fromJson(resp_json.toString(), Order.class);
                        mTvOrder.setText("订单id:"+mOrder.get_id());
                        mTvFood.setText("食物:"+mOrder.getFoods().get(0).getName()+"-数量："+mOrder.getFoods().get(0).getCount());
                        mTvTitle.setText(mOrder.get_id());
                        mTvUser.setText("消费者:"+mOrder.getGuestId());

                    }catch (Exception e){
                        Log.d(TAG,"exception");
                        e.printStackTrace();
                    }
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

        taskId= getIntent().getStringExtra("id");

        httpRequestHelper = new HTTPRequestHelper(taskDataRequestHandler);
        properties = Properties.getInstance(this);
        userId = properties.get(Constants.USER_ID);
        userPass = properties.get(Constants.USER_PASSWORD);

        requestTaskData(taskId);

        mTvOrder=(TextView)findViewById(R.id.tv_orderdetail_name);
        mTvTitle=(TextView)findViewById(R.id.tv_titlebar);
        mTvUser=(TextView)findViewById(R.id.tv_orderdetail_user);
        mTvFood=(TextView)findViewById(R.id.tv_orderdetail_food);

        mBtnClose=(Button)findViewById(R.id.btn_closeorder);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            addLoading();
            new OrderSQLUtils(OrderDetailActivity.this).insert(taskId, mOrder);
            removeLoading();
            }
        });
    }


    private void requestTaskData(final String id) {
        (new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i(TAG, ServerURLHelper.getQueryOrderDetailURL(id));
                httpRequestHelper.performGet(ServerURLHelper.getQueryOrderDetailURL(id),
                        userId,
                        userPass,
                        ServerURLHelper.getJSONHeaders()
                );
            }
        })).start();
        addLoading();

    }
    public void addLoading() {
        FrameLayout rootFrameLayout = (FrameLayout) this.getWindow().getDecorView();
        if (loadingUI == null) {
            loadingUI = new LoadingUI(this, this.getResources().getString(R.string.task_loading_tip));
            FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 17);
            loadingUI.setLayoutParams(localLayoutParams);
            loadingUI.setVisiable(0);
        }
        rootFrameLayout.addView(loadingUI);
    }

    public void removeLoading() {
        if (loadingUI != null) {
            ((FrameLayout) this.getWindow().getDecorView()).removeView(loadingUI);
            loadingUI = null;
        }
    }
}
