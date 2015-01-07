package com.arrking.express.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
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

import com.arrking.android.component.LoadingUI;
import com.arrking.express.MainActivity;
import com.arrking.express.R;

import java.util.ArrayList;
import java.util.List;


public class PendingOrdersFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LinearLayout root;
    private ListView tab01ListView;
    private List<ContentValues> listContentValues;
    private BaseAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        this.root = (LinearLayout) inflater.inflate(R.layout.tab01, container, false);
        initUI();
        loadData();
        return this.root;
    }

    private void loadData() {
        refreshList();
    }

    private void refreshList() {
        this.tab01ListView.setVisibility(View.VISIBLE);
        this.listContentValues = fakeListContentValues();
        ((MainActivity) getActivity()).setBadge(1, this.listContentValues.size());
        setAdapter(this.listContentValues);
    }

    private List<ContentValues> fakeListContentValues() {
        List<ContentValues> lis = new ArrayList();
        for(int i =0; i < 10; i++){
            ContentValues cv = new ContentValues();
            cv.put("date", "fooo");
            lis.add(cv);
        }
        return lis;
    }

    private void setAdapter(List<ContentValues> lis) {
        this.adapter = new TaskListAdapter(getActivity(), lis);
        this.tab01ListView.setAdapter(this.adapter);
    }

    private void initUI() {
        this.tab01ListView = (ListView) this.root.findViewById(R.id.tab01_listView);
        this.tab01ListView.setOnItemClickListener(this);
        this.tab01ListView.setDivider(getResources().getDrawable(R.drawable.line_image));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
            return this.lis.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "\n ================== \n getView @" + Integer.toString(position));
            PendingOrdersFragment.TaskViewHolder taskViewHolder;
            if (convertView == null) {
                convertView = this.inflater.inflate(R.layout.task_list_item, null);
                taskViewHolder = new TaskViewHolder();
                taskViewHolder.date = (TextView) convertView.findViewById(R.id.user_name);
                convertView.setTag(taskViewHolder);
            } else {
                taskViewHolder = (TaskViewHolder) convertView.getTag();
            }
            ContentValues l = (ContentValues) this.lis.get(position);

            taskViewHolder.date.setText(l.getAsString("date"));
            return convertView;
        }
    }

    private static class TaskViewHolder {
        ImageView headImage;
        TextView orderId;
        TextView date;
        TextView guestId;
    }
}
