package com.arrking.android.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.arrking.express.MainActivity;
import com.arrking.express.R;

/**
 * Created by Jim on 2015/2/4.
 */
public class NotificationUtils {


    public static void notify(Context context,String title,String content){

        NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        //新建状态栏通知
        Notification baseNF = new Notification();

        //设置通知在状态栏显示的图标
        baseNF.icon = R.drawable.ic_launcher;
        //通知时在状态栏显示的内容
        baseNF.tickerText = title;
        baseNF.sound =  Uri.parse("android.resource://com.arrking.express/"+R.raw.test);
        baseNF.defaults |= Notification.DEFAULT_VIBRATE;
        Intent intent = new Intent(context,MainActivity.class);
        PendingIntent pd = PendingIntent.getActivity(context, 0, intent, 0);
        baseNF.setLatestEventInfo(context, title, content, pd);

        nm.notify(110, baseNF);
    }
}
