package com.lhalcyon.pl.handler;

import android.app.Notification;

import com.lhalcyon.pl.PLInitializer;

import java.util.HashMap;
import java.util.Map;


public class WechatNotificationHandle extends NotificationHandle {
    public WechatNotificationHandle(String pkgtype, Notification notification) {
        super(pkgtype, notification);
    }

    public void handleNotification() {
        if ((title.contains("微信支付") | title.contains("微信收款")) && content.contains("收款")) {
            Map<String, String> postmap = new HashMap<String, String>();
            postmap.put("type", "wechat");
            postmap.put("time", notitime);
            postmap.put("title", title);
            postmap.put("money", extractMoney(content));
            postmap.put("content", content);
            PLInitializer.shared().handle(postmap);
            return;
        }


    }


}
