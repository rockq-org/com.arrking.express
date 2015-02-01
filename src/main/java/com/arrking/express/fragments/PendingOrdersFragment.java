package com.arrking.express.fragments;

import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.arrking.android.database.Properties;
import com.arrking.android.util.HTTPRequestHelper;
import com.arrking.express.MainActivity;
import com.arrking.express.OrderDetailActivity;
import com.arrking.express.R;
import com.arrking.express.common.Constants;
import com.arrking.express.common.ServerURLHelper;
import com.arrking.express.model.ActivitiTask;
import com.arrking.express.model.ActivitiTasks;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


public class PendingOrdersFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String CLASSNAME = PendingOrdersFragment.class.getName();
    private LinearLayout root;
    private PullToRefreshListView tab01ListView;
    private List<ContentValues> listContentValues;
    private BaseAdapter adapter;
    private HTTPRequestHelper httpRequestHelper;
    private Properties properties;
    private String userId;
    private String userPass;
    private DateTimeFormatter isoDateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
    private PullToRefreshListView.OnRefreshListener onRefreshListener = new PullToRefreshBase.OnRefreshListener<ListView>() {
        @Override
        public void onRefresh(PullToRefreshBase<ListView> refreshView) {
            // Do work to refresh the list here.
            refreshList();
        }
    };

    private Handler taskDataRequestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(CLASSNAME, "get data ...");
            Gson gson = new Gson();
            Bundle data = msg.getData();
            String resp = data.getString("RESPONSE");
            Log.d(CLASSNAME, " resp:"+resp);
            switch (msg.what) {
                case 200:
                    ActivitiTasks activitiTasks = gson.fromJson(resp, ActivitiTasks.class);
                    Log.d(CLASSNAME, "activitiTasks get size " + activitiTasks.getSize());
                    listContentValues = tasks2contentValues(activitiTasks.getData());
                    ((MainActivity) getActivity()).setBadge(1, listContentValues.size());
                    setAdapter(listContentValues);

                    if (tab01ListView.isRefreshing()) {
                        tab01ListView.onRefreshComplete();
                    } else {
                        tab01ListView.setVisibility(View.VISIBLE);
                        ((MainActivity) getActivity()).removeLoading();
                    }
                    break;
                default:
                    Log.w(CLASSNAME, "taskDataRequestHandler resp:" + resp);
                    break;
            }
        }

        private List<ContentValues> tasks2contentValues(List<ActivitiTask> data) {
//            DateTimeZone.setDefault(DateTimeZone.forID("Asia/Tokyo"));
//            Log.d(CLASSNAME, DateTimeZone.getAvailableIDs());
            List<ContentValues> t = new ArrayList();
            for (int i = 0; i < data.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put(Constants.TASK_ID, data.get(i).getId());
                DateTime dt = isoDateFormatter.parseDateTime(data.get(i).getCreateTime());
                DateTime localTime = dt.toDateTime(DateTimeZone.forID("Asia/Chongqing"));
                cv.put(Constants.TASK_ORDER_DATE, localTime.toString("yyyy-MM-dd HH:mm:ss"));
                cv.put(Constants.TASK_ORDER_LOCATION, "Hello World Cafe");
                t.add(cv);
            }
            return t;
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpRequestHelper = new HTTPRequestHelper(taskDataRequestHandler);
        properties = Properties.getInstance(getActivity());
        userId = properties.get(Constants.USER_ID);
        userPass = properties.get(Constants.USER_PASSWORD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        this.root = (LinearLayout) inflater.inflate(R.layout.tab01, container, false);
        initUI();
        refreshList();
        return this.root;
    }

    private void refreshList() {
        if (!tab01ListView.isRefreshing()) {
            ((MainActivity) getActivity()).addLoading();
            this.tab01ListView.setVisibility(View.INVISIBLE);
        }
        requestTaskData();
    }

    private void requestTaskData() {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                httpRequestHelper.performPostJSON(ServerURLHelper.queryCashierTasksURL(),
                        userId,
                        userPass,
                        ServerURLHelper.getJSONHeaders(),
                        ServerURLHelper.getQueryCashierTasksBody()
                );
            }
        })).start();
    }

    private void setAdapter(List<ContentValues> lis) {
        this.adapter = new TaskListAdapter(getActivity(), lis);
        this.tab01ListView.setAdapter(this.adapter);
    }

    private void initUI() {
        this.tab01ListView = (PullToRefreshListView) this.root.findViewById(R.id.tab01_listView);
        this.tab01ListView.setOnItemClickListener(this);
        ((ListView) this.tab01ListView.getRefreshableView()).setDivider(getResources().getDrawable(R.drawable.line_image));
        this.tab01ListView.setOnRefreshListener(onRefreshListener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ContentValues v = this.listContentValues.get((int) id);
        Intent intent=new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("id",v.getAsString(Constants.TASK_ID));
        intent.putExtra("location",v.getAsString(Constants.TASK_ORDER_LOCATION));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        requestTaskData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private class TaskListAdapter extends BaseAdapter {
        private final String TAG = TaskListAdapter.class.getName();
        private LayoutInflater inflater;
        private List<ContentValues> lis;

        public TaskListAdapter(Activity ctx, List<ContentValues> lis) {
            this.inflater = LayoutInflater.from(ctx);
            this.lis = lis;
        }

        @Override
        public int getCount() {
            return lis.size();
        }

        @Override
        public Object getItem(int position) {
            Log.d(TAG, "get item ... " + position);
            return lis.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "\n ================== \n getView @" + Integer.toString(position));
            TaskViewHolder taskViewHolder;
            if (convertView == null) {
                convertView = this.inflater.inflate(R.layout.task_list_item, null);
                taskViewHolder = new TaskViewHolder();
                taskViewHolder.taskId = (TextView) convertView.findViewById(R.id.task_id);
                taskViewHolder.date = (TextView) convertView.findViewById(R.id.order_date);
                taskViewHolder.orderLocation = (TextView) convertView.findViewById(R.id.order_location);
                taskViewHolder.headImage = (ImageView) convertView.findViewById(R.id.head_icon);
            } else {
                taskViewHolder = (TaskViewHolder) convertView.getTag();
            }
            final ContentValues l = (ContentValues) lis.get(position);

            taskViewHolder.date.setText(l.getAsString(Constants.TASK_ORDER_DATE));
            taskViewHolder.orderLocation.setText(l.getAsString(Constants.TASK_ORDER_LOCATION));
            taskViewHolder.taskId.setText(l.getAsString(Constants.TASK_ID));
            // event listener for image
//            taskViewHolder.headImage.setClickable(true);
//            taskViewHolder.headImage.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "clicked ... " + l.getAsString(Constants.TASK_ID));
//                }
//            });

            convertView.setTag(taskViewHolder);
            return convertView;
        }
    }

    public final class TaskViewHolder {
        public ImageView headImage;
        public TextView date;
        public TextView taskId;
        public TextView orderLocation;
    }
}
