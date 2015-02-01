package com.arrking.express;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arrking.android.component.LoadingUI;
import com.arrking.android.database.OrderSQLUtils;
import com.arrking.android.database.Properties;
import com.arrking.android.util.HTTPRequestHelper;
import com.arrking.express.common.Constants;
import com.arrking.express.common.ServerURLHelper;
import com.arrking.express.model.Food;
import com.arrking.express.model.Order;
import com.arrking.express.model.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;


public class OrderDetailActivity extends Activity {

    private static final String TAG = OrderDetailActivity.class.getName();
    private HTTPRequestHelper httpRequestHelper;
    private Properties properties;
    private String userId;
    private String userPass;

    private TextView mTvABTitle;
    private TextView mTvGuestId;
    private TextView mTvAddress;
    private TextView mTvPrice,mTvPriceNow;
    private TextView mTvDiscount;
    private ListView mLvFood;
    private FoodListAdapter mAdapter;
    private ImageView mIvClose;
    private RelativeLayout mRlBack;

    private Order mOrder;
    private String taskId,location;

    private LoadingUI loadingUI;

    private Handler taskDataRequestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Gson gson = new Gson();
            Bundle data = msg.getData();
            String resp = data.getString("RESPONSE");
            JSONObject resp_json=null;
            Log.i("fangjie","resp:"+resp);
            switch (msg.what) {
                case 200:
                    removeLoading();
                    try {
                        resp_json=new JSONObject(resp);
                        resp_json=new JSONObject(resp_json.getString("value"));
                        mOrder = gson.fromJson(resp_json.toString(), Order.class);
                        mTvABTitle.setText(taskId);
                        mTvAddress.setText(mOrder.getLocation());
                        mTvDiscount.setText(mOrder.getDiscount()+" off");
                        mTvGuestId.setText(mOrder.getGuestId());
                        mAdapter=new FoodListAdapter(OrderDetailActivity.this,mOrder.getFoods());
                        mLvFood.setAdapter(mAdapter);

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

    private Handler taskdoneRequestHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    removeLoading();
                    finish();
                    break;
                default:
                    break;
            }
        }
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_detail);

        taskId= getIntent().getStringExtra("id");
        location=getIntent().getStringExtra("location");

        httpRequestHelper = new HTTPRequestHelper(taskDataRequestHandler);
        properties = Properties.getInstance(this);
        userId = properties.get(Constants.USER_ID);
        userPass = properties.get(Constants.USER_PASSWORD);

        requestTaskData(taskId);

        mTvGuestId=(TextView)findViewById(R.id.tv_order_guestid);
        mTvABTitle=(TextView)findViewById(R.id.tv_titlebar);
        mTvAddress=(TextView)findViewById(R.id.tv_order_address);
        mTvPrice=(TextView)findViewById(R.id.tv_order_price);
        mTvPriceNow=(TextView)findViewById(R.id.tv_order_pricenow);
        mTvDiscount=(TextView)findViewById(R.id.tv_order_discount);
        mLvFood=(ListView)findViewById(R.id.lv_order_list);
        mIvClose=(ImageView)findViewById(R.id.iv_order_close);
        mRlBack=(RelativeLayout)findViewById(R.id.rl_back);
        mRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            addLoading();
            requestDoneTask(taskId);

            new OrderSQLUtils(OrderDetailActivity.this).insert(taskId, location,mOrder);

            }
        });
    }

    /**
     * 获取task 详情
     * @param id
     */
    private void requestTaskData(final String id) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                httpRequestHelper.performGet(ServerURLHelper.getQueryOrderDetailURL(id),
                        userId,
                        userPass,
                        ServerURLHelper.getJSONHeaders()
                );
            }
        })).start();
        addLoading();

    }

    /**
     *关闭task的请求
     * @param id
     */
    public void requestDoneTask(final String id){
        new Thread(){
            @Override
            public void run() {
                super.run();
                httpRequestHelper = new HTTPRequestHelper(taskdoneRequestHandler);
                httpRequestHelper.performPostJSON(ServerURLHelper.getDoneTaskURL(id),
                        userId,
                        userPass,
                        ServerURLHelper.getJSONHeaders(),ServerURLHelper.getDoneTaskBody()
                );
            }
        }.start();
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



    class FoodListAdapter extends BaseAdapter{


        private List<Food> list;
        private Context context;
        public FoodListAdapter(Context context,List<Food> foods){
            this.context=context;
            this.list=foods;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_order_list,parent,false);
            TextView name=(TextView)convertView.findViewById(R.id.tv_order_foodname);
            TextView count=(TextView)convertView.findViewById(R.id.tv_order_foodcount);
            TextView price=(TextView)convertView.findViewById(R.id.tv_order_foodprice);

            name.setText(list.get(position).getName());
            count.setText(list.get(position).getCount()+"份");
            price.setText("单价："+list.get(position).getPrice());
            return convertView;
        }
    }


}
