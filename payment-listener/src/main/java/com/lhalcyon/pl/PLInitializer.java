package com.lhalcyon.pl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lhalcyon.pl.service.NotificationCollectorMonitorService;
import com.lhalcyon.pl.service.NotificationForegroundMonitorService;
import com.lhalcyon.pl.support.ILog;
import com.lhalcyon.pl.support.OnNotificationReceivedListener;

import java.util.Map;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2019/7/2
 * Brief Desc :
 * </pre>
 */
public class PLInitializer {


    public static PLInitializer shared() {
        return Singleton.instance;
    }

    private PLInitializer() {

    }

    private ILog mLog;

    private OnNotificationReceivedListener mNotificationReceivedListener;

    private String mNotificationTitle;

    private String mNotificationDesc;

    public String getNotificationTitle() {
        if (TextUtils.isEmpty(mNotificationTitle)) {
            return "支付自动确认";
        }
        return mNotificationTitle;
    }

    public String getNotificationDesc() {
        if (TextUtils.isEmpty(mNotificationDesc)) {
            return "正在为您服务";
        }
        return mNotificationDesc;
    }

    public ILog getLog() {
        return mLog;
    }

    public PLInitializer setNotificationTitle(String notificationTitle) {
        mNotificationTitle = notificationTitle;
        return this;
    }

    public PLInitializer setNotificationDesc(String notificationDesc) {
        mNotificationDesc = notificationDesc;
        return this;
    }

    public PLInitializer setLog(ILog log) {
        mLog = log;
        return this;
    }

    public OnNotificationReceivedListener getNotificationReceivedListener() {
        return mNotificationReceivedListener;
    }

    public PLInitializer setNotificationReceivedListener(OnNotificationReceivedListener notificationReceivedListener) {
        mNotificationReceivedListener = notificationReceivedListener;
        return this;
    }

    public PLInitializer startListening(Context context) {
        context.startService(new Intent(context, NotificationCollectorMonitorService.class));
        return this;
    }

    public PLInitializer startForegroundListening(Context context) {
        context.startService(new Intent(context, NotificationForegroundMonitorService.class));
        return this;
    }

    public PLInitializer stopListening(Context context) {
        context.stopService(new Intent(context, NotificationForegroundMonitorService.class));
        context.stopService(new Intent(context, NotificationCollectorMonitorService.class));
        return this;
    }


    public boolean handle(Map<String, String> params) {
        if (getNotificationReceivedListener() == null) {
            return false;
        }
        getNotificationReceivedListener().onPaymentTypeReceived(params);
        return true;
    }

    public boolean handleUndefine(String pkg, String content) {
        if (getNotificationReceivedListener() == null){
            return false;
        }
        getNotificationReceivedListener().onUndefineNotificationReceived(pkg,content);
        return true;
    }

    private static class Singleton {

        static PLInitializer instance = new PLInitializer();
    }
}




