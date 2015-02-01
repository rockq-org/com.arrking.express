package com.arrking.express;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arrking.android.component.LoadingUI;
import com.arrking.android.database.OrderSQLUtils;
import com.arrking.express.model.Food;
import com.arrking.express.model.Order;

import java.util.List;


public class DoneOrderDetailActivity extends Activity {

    private int taskId;
    private Order mOrder;
    private LoadingUI loadingUI;
    private TextView mTvABTitle;
    private TextView mTvGuestId;
    private TextView mTvAddress;
    private TextView mTvPrice,mTvPriceNow;
    private TextView mTvDiscount;
    private ListView mLvFood;
    private FoodListAdapter mAdapter;
    private ImageView mIvClose;
    private RelativeLayout mRlBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_detail);
        addLoading();
        taskId=getIntent().getIntExtra("taskId",0);
        if(taskId!=0) {
            mOrder=new OrderSQLUtils(this).queryOrderDetail(taskId);
        }

        mTvGuestId=(TextView)findViewById(R.id.tv_order_guestid);
        mTvABTitle=(TextView)findViewById(R.id.tv_titlebar);
        mTvAddress=(TextView)findViewById(R.id.tv_order_address);
        mTvPrice=(TextView)findViewById(R.id.tv_order_price);
        mTvPriceNow=(TextView)findViewById(R.id.tv_order_pricenow);
        mTvDiscount=(TextView)findViewById(R.id.tv_order_discount);
        mLvFood=(ListView)findViewById(R.id.lv_order_list);
        mIvClose=(ImageView)findViewById(R.id.iv_order_close);
        mRlBack=(RelativeLayout)findViewById(R.id.rl_back);

        mTvABTitle.setText(taskId+"");
        mTvAddress.setText(mOrder.getLocation());
        mTvDiscount.setText(mOrder.getDiscount()+" off");
        mTvGuestId.setText(mOrder.getGuestId());
        mAdapter=new FoodListAdapter(DoneOrderDetailActivity.this,mOrder.getFoods());
        mLvFood.setAdapter(mAdapter);
        mIvClose.setVisibility(View.GONE);
        mRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        removeLoading();
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


    class FoodListAdapter extends BaseAdapter {


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
