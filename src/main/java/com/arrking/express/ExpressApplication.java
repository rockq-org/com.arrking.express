package com.arrking.express;


import android.app.Application;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by hain on 04/02/2015.
 */
public class ExpressApplication extends Application {

    private static final String TAG = "ExpressApplication";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

}
