package com.arrking.tory;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.arrking.tory.fragments.ClosedOrdersFragment;
import com.arrking.tory.fragments.InProgressOrdersFragment;
import com.arrking.tory.fragments.PendingOrdersFragment;
import com.jauker.widget.BadgeView;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = new String("MainActivity");
    private ViewPager mViewPager;
    private List<Fragment> mDatas;
    private FragmentPagerAdapter mAdapter;
    private TextView text1, text2, text3;
    private ImageView mLine;
    private int ScreenWidth;
    private LinearLayout mTab01LinerLayout;
    private LinearLayout mTab02LinerLayout;
    private LinearLayout mTab03LinerLayout;
    private BadgeView tab01Badge;
    private BadgeView tab02Badge;
    private BadgeView tab03Badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        ScreenWidth = (int) (dm.widthPixels / 3);

        // tab 1 in waiting orders
        // tab 2 in progress orders
        // tab 3 closed orders

        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        mLine = (ImageView) findViewById(R.id.iv_line);

        mTab01LinerLayout = (LinearLayout) findViewById(R.id.ll_tab1);
        mTab02LinerLayout = (LinearLayout) findViewById(R.id.ll_tab2);
        mTab03LinerLayout = (LinearLayout) findViewById(R.id.ll_tab3);

        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);

        // set badges
        tab01Badge = new BadgeView(this);
        mTab01LinerLayout.addView(tab01Badge);

        tab02Badge = new BadgeView(this);
        mTab02LinerLayout.addView(tab02Badge);

        tab03Badge = new BadgeView(this);
        mTab03LinerLayout.addView(tab03Badge);


//        tab01Badge.setBadgeCount(99);

        mDatas = new ArrayList<Fragment>();
        ClosedOrdersFragment tab03 = new ClosedOrdersFragment();
        InProgressOrdersFragment tab02 = new InProgressOrdersFragment();
        PendingOrdersFragment tab01 = new PendingOrdersFragment();

        mDatas.add(tab01);
        mDatas.add(tab02);
        mDatas.add(tab03);


        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("TAG", position + "," + positionOffset + "," + positionOffsetPixels);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mLine.getLayoutParams();
                lp.leftMargin = position * ScreenWidth + (int) (positionOffset * ScreenWidth);
                mLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetFontColor();
                switch (position) {
                    case 0:
                        text1.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        text2.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        text3.setTextColor(Color.parseColor("#008000"));
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void onClick(View v) {
        resetFontColor();
        switch (v.getId()) {
            case R.id.text1:
                mViewPager.setCurrentItem(0);
                text1.setTextColor(Color.parseColor("#008000"));
                break;
            case R.id.text2:
                mViewPager.setCurrentItem(1);
                text2.setTextColor(Color.parseColor("#008000"));
                break;
            case R.id.text3:
                mViewPager.setCurrentItem(2);
                text3.setTextColor(Color.parseColor("#008000"));
                break;
        }


    }

    void resetFontColor() {
        text1.setTextColor(Color.BLACK);
        text2.setTextColor(Color.BLACK);
        text3.setTextColor(Color.BLACK);
    }
}
