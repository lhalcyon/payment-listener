package org.loois.test_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lhalcyon.pl.PLInitializer;
import com.lhalcyon.pl.service.NotificationCollectorMonitorService;
import com.lhalcyon.pl.support.ILog;
import com.lhalcyon.pl.support.OnNotificationReceivedListener;
import com.lhalcyon.pl.util.NotificationUtil;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SwitchCompat mSwitchCompat;

    TextView mTextView;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.content);

        mSwitchCompat = findViewById(R.id.notifySwitch);


        mContext = this;

        PLInitializer.shared()
                .setLog(new ILog() {
                    @Override
                    public void info(String message) {
                        Log.i("监听", message);
                    }
                })
                .setNotificationReceivedListener(new OnNotificationReceivedListener() {
                    @Override
                    public void onPaymentTypeReceived(Map<String, String> params) {
                        mTextView.setText(params.toString());
                        showNotification(params.get("title"), params.get("money") + " | " + params.get("content"));
                    }
                });

        startService(new Intent(this, NotificationCollectorMonitorService.class));


    }

    private void showNotification(String title, String content) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "payment-listener")
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                .setAutoCancel(true)
                .build();
        assert manager != null;
        manager.notify(2, notification);
    }


    public void onTestNotify(View v) {
        showNotification("123", "234dsklj");
    }

    @Override
    protected void onResume() {
        super.onResume();
        setStatus();

    }

    public void onJumpNotifySetting(View v) {
        NotificationUtil.startNotificationActivity(mContext);
    }

    private void setStatus() {
        boolean notificationServiceEnable = NotificationUtil.isNotificationServiceEnable(mContext);
        Log.i("Notify enable:", "" + notificationServiceEnable);
        mSwitchCompat.setChecked(notificationServiceEnable);
    }
}
