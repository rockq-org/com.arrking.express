package com.arrking.android.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hain on 24/12/2014.
 */
public class AppUtil {
    public static String getDataString(long paramLong, String paramString)
    {
        return new SimpleDateFormat(paramString).format(new Date(1000L * paramLong));
    }

}


