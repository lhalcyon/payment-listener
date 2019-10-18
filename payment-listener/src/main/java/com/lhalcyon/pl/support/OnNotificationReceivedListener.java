package com.lhalcyon.pl.support;

import java.util.Map;

/**
 * <pre>
 * Create by  :    L
 * Create Time:    2019/7/3
 * Brief Desc :
 * </pre>
 */
public interface OnNotificationReceivedListener {

    void onPaymentTypeReceived(Map<String,String> params);

    void onUndefineNotificationReceived(String pkg,String content);
}
