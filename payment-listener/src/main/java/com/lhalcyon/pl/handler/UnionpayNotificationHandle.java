package com.lhalcyon.pl.handler;

import android.app.Notification;

import com.lhalcyon.pl.PLInitializer;

import java.util.HashMap;
import java.util.Map;


public class UnionpayNotificationHandle extends NotificationHandle {
    public UnionpayNotificationHandle(String pkgtype, Notification notification) {
        super(pkgtype, notification);
    }

    public void handleNotification() {
        if (title.contains("消息推送") && content.contains("云闪付收款")) {
            Map<String, String> postmap = new HashMap<String, String>();
            postmap.put("type", "unionpay");
            postmap.put("time", notitime);
            postmap.put("title", title);
            postmap.put("money", extractMoney(content));
            postmap.put("content", content);
            PLInitializer.shared().handle(postmap);
            return;
        }


    }


}
