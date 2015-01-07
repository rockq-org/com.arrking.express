package com.arrking.express.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.arrking.android.component.LoadingUI;
import com.arrking.express.MainActivity;
import com.arrking.express.R;


public class PendingOrdersFragment extends Fragment implements AdapterView.OnItemClickListener {

    private LinearLayout root;
    private ListView tab01ListView;


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
        ((MainActivity) getActivity()).addLoading();
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
}
