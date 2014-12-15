package com.arrking.tory;

import android.os.Bundle;
import org.apache.cordova.*;
import cn.jpush.android.api.JPushInterface;

public class Tory extends CordovaActivity 
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.init();
        // Set by <content src="index.html" /> in config.xml
        super.loadUrl(Config.getStartUrl());
        //super.loadUrl("file:///android_asset/www/index.html");
    }

    @Override
    public void onResume(){
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public void onPause(){
        super.onPause();
        JPushInterface.onPause(this);
    }
}
