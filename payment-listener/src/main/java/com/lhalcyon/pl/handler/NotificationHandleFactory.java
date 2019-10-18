package com.lhalcyon.pl.handler;

import android.app.Notification;

public class NotificationHandleFactory {
    public NotificationHandle getNotificationHandle(String pkg, Notification notification) {
        //mipush
        if ("com.xiaomi.xmsf".equals(pkg)) {
            return new MipushNotificationHandle("com.xiaomi.xmsf", notification);
        }
        //支付宝
        if ("com.eg.android.AlipayGphone".equals(pkg)) {
            return new AlipayNotificationHandle("com.eg.android.AlipayGphone", notification);
        }

        //应用管理GCM代收
        if ("android".equals(pkg)) {
            return new XposedmoduleNotificationHandle("github.tornaco.xposedmoduletest", notification);
        }
        //微信
        if ("com.tencent.mm".equals(pkg)) {
            return new WechatNotificationHandle("com.tencent.mm", notification);
        }
        //收钱吧
        if ("com.wosai.cashbar".equals(pkg)) {
            return new CashbarNotificationHandle("com.wosai.cashbar", notification);
        }
        //云闪付
        if ("com.unionpay".equals(pkg)) {
            return new UnionpayNotificationHandle("com.unionpay", notification);
        }
        //工银商户之家
        if ("com.icbc.biz.elife".equals(pkg)) {
            return new IcbcelifeNotificationHandle("com.icbc.biz.elife", notification);
        }

        return new OtherNotificationHandle(pkg,notification);

    }

}


