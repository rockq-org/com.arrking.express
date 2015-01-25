package com.arrking.express;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arrking.android.component.LoadingUI;
import com.arrking.android.database.OrderSQLUtils;
import com.arrking.express.model.Order;


public class DoneOrderDetailActivity extends ActionBarActivity {

    private int taskId;
    private Order mOrder;
    private LoadingUI loadingUI;
    private TextView mTvGuest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_order_detail);
        addLoading();
        taskId=getIntent().getIntExtra("taskId",0);
        if(taskId!=0) {
            mOrder=new OrderSQLUtils(this).queryOrderDetail(taskId);
        }
        mTvGuest=(TextView)findViewById(R.id.tv_done_taskdetail_guestid);
        mTvGuest.setText(mOrder.getTaskId()+"");
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

}
