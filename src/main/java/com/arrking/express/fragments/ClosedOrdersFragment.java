package com.arrking.express.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.arrking.android.component.LoadingUI;
import com.arrking.android.database.OrderSQLUtils;
import com.arrking.android.util.TimeUtils;
import com.arrking.express.DoneOrderDetailActivity;
import com.arrking.express.R;
import com.arrking.express.model.Order;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


public class ClosedOrdersFragment extends Fragment {

    private PullToRefreshListView mLvList;
    private TaskAdapter mAdapter;
    private List<Order> mLists=new ArrayList<Order>();

    private LoadingUI loadingUI;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter=new TaskAdapter(getActivity(),mLists);
            mLvList.setAdapter(mAdapter);
            mLvList.onRefreshComplete();
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.tab02,container,false);
        addLoading();
        mLists= new OrderSQLUtils(getActivity()).queryOrders();
        mLvList=(PullToRefreshListView)v.findViewById(R.id.tab02_listView);
        mAdapter=new TaskAdapter(getActivity(),mLists);
        mLvList.setAdapter(mAdapter);

        mLvList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        mLists= new OrderSQLUtils(getActivity()).queryOrders();
                        mHandler.sendMessage(new Message());
                    }
                }.start();
            }
        });

        mLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), DoneOrderDetailActivity.class);
                intent.putExtra("taskId",mLists.get(position-1).getTaskId());
                startActivity(intent);
            }
        });
        removeLoading();
        return v;
    }

    private class TaskAdapter extends BaseAdapter {
        private final String TAG = TaskAdapter.class.getName();
        private Context context;
        private List<Order> list;

        public TaskAdapter(Context ctx, List<Order> lis) {
            this.context = ctx;
            this.list = lis;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.task_list_item, null);
                holder = new ViewHolder();
                holder.taskId = (TextView) convertView.findViewById(R.id.task_id);
                holder.date = (TextView) convertView.findViewById(R.id.order_date);
                holder.orderLocation = (TextView) convertView.findViewById(R.id.order_location);
                holder.headImage = (ImageView) convertView.findViewById(R.id.head_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.taskId.setText(list.get(position).getTaskId()+"");
            holder.orderLocation.setText(list.get(position).getLocation());
            holder.date.setText(TimeUtils.dateToStr(list.get(position).getDoneTime()));
            return convertView;
        }
    }

    public final class ViewHolder {
        public ImageView headImage;
        public TextView date;
        public TextView taskId;
        public TextView orderLocation;
    }


    public void addLoading() {
        FrameLayout rootFrameLayout = (FrameLayout) getActivity().getWindow().getDecorView();
        if (loadingUI == null) {
            loadingUI = new LoadingUI(getActivity(), getActivity().getResources().getString(R.string.task_loading_tip));
            FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 17);
            loadingUI.setLayoutParams(localLayoutParams);
            loadingUI.setVisiable(0);
        }
        rootFrameLayout.addView(loadingUI);
    }

    public void removeLoading() {
        if (loadingUI != null) {
            ((FrameLayout) getActivity().getWindow().getDecorView()).removeView(loadingUI);
            loadingUI = null;
        }
    }
}
