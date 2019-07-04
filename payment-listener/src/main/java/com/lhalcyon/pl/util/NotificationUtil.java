package com.lhalcyon.pl.util;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2019/7/2
 * Brief Desc :
 * </pre>
 */
public class NotificationUtil {


    /**
     * 开启通知栏设置界面
     * @param context 上下文
     */
    public static void startNotificationActivity(Context context){
        context.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS").setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }


    /**
     *
     * @return 是否已授权通知栏
     */
    public static boolean isNotificationServiceEnable(Context context) {
        return NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.getPackageName());
    }
}
