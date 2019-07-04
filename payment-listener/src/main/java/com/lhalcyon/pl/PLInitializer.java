package com.lhalcyon.pl;

import android.content.Context;
import android.content.Intent;

import com.lhalcyon.pl.service.NotificationCollectorMonitorService;
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


    public static PLInitializer shared(){
        return Singleton.instance;
    }

    private PLInitializer() {

    }

    private ILog mLog;

    private OnNotificationReceivedListener mNotificationReceivedListener;


    public ILog getLog() {
        return mLog;
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

    public PLInitializer startListening(Context context){
        context.startService(new Intent(context, NotificationCollectorMonitorService.class));
        return this;
    }



    public boolean handle(Map<String,String> params){
        if (getNotificationReceivedListener() == null){
            return false;
        }
        getNotificationReceivedListener().onPaymentTypeReceived(params);
        return true;
    }

    private static class Singleton {

        static PLInitializer instance = new PLInitializer();
    }
}




