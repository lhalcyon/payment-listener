package com.lhalcyon.pl.handler;

import android.app.Notification;

import com.lhalcyon.pl.PLInitializer;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2019-10-18
 * Brief Desc :
 * </pre>
 */
public class OtherNotificationHandle extends NotificationHandle {

    public OtherNotificationHandle(String pkgtype, Notification notification) {
        super(pkgtype, notification);
    }

    @Override
    public void handleNotification() {
        PLInitializer.shared().handleUndefine(pkgtype,content);
    }
}
