package com.lhalcyon.pl.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import com.google.gson.Gson;
import com.lhalcyon.pl.util.DeviceInfoUtil;
import com.lhalcyon.pl.util.LogUtil;
import com.lhalcyon.pl.util.PreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;




/**
 * Created by xinghui on 9/20/16.
 * <p>
 * calling this in your Application's onCreate
 * startService(new Intent(this, NotificationCollectorMonitorService.class));
 * <p>
 * BY THE WAY Don't Forget to Add the Service to the AndroidManifest.xml File.
 * <service android:name=".NotificationCollectorMonitorService"/>
 */
public class NotificationCollectorMonitorService extends Service {

        /**
         * {@link Log#isLoggable(String, int)}
         * <p>
         * IllegalArgumentException is thrown if the tag.length() > 23.
         */
        private static final String TAG = "NotifiCollectorMonitor";
        private Timer timer=null;
        private String echointerval=null;
        private TimerTask echotimertask =null;

        @Override
        public void onCreate() {
                super.onCreate();
                ensureCollectorRunning();
                startEchoTimer();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
                return START_STICKY;
        }
        private boolean echoServerBySocketio(String echourl,String echojson){

                return true;
        }
        private String getDefaultEchoInterval(){
                if (Build.VERSION.SDK_INT >= 22 )
                        return  "300";
                else
                        return  "100";
        }
        private void startEchoTimer(){
                PreferenceUtil preference=new PreferenceUtil(getBaseContext());
                String interval=preference.getEchoInterval();
                this.echointerval=(!interval.equals("") ?  interval:getDefaultEchoInterval());
                this.echotimertask=returnEchoTimerTask();
                this.timer=new Timer();
                int intervalmilliseconds = Integer.parseInt(this.echointerval)*1000;
                LogUtil.infoLog("now socketio timer milliseconds:"+intervalmilliseconds);
                timer.schedule(echotimertask,5*1000,intervalmilliseconds);
        }
        private TimerTask returnEchoTimerTask(){
                return new TimerTask() {
                @Override
                public void run() {
                        if(!isIntervalMatchPreference()){
                            restartEchoTimer();
                            return;
                        }
                        LogUtil.debugLog("once socketio timer task run");
                        boolean flag= echoServer();
                        if(!flag)
                                LogUtil.debugLog("socketio timer task not have a server");
                }
          };
        }
        private void restartEchoTimer(){
                        if (this.timer != null) {  
                            this.timer.cancel();  
                            this.timer = null;  
                        }  
                        if (echotimertask != null) {  
                            echotimertask.cancel();  
                            echotimertask = null;  
                        }   
                        LogUtil.debugLog("restart echo timer task");
                        startEchoTimer();
        }
        private boolean isIntervalMatchPreference(){
                PreferenceUtil preference=new PreferenceUtil(getBaseContext());
                String interval=preference.getEchoInterval();
                if(interval.equals(""))
                    return true;
                if(interval.equals(this.echointerval))
                    return true;
                return false;
        }
        private boolean echoServer(){
                PreferenceUtil preference=new PreferenceUtil(getBaseContext());
                Gson gson = new Gson();
                if(preference. isEcho()&&(preference.getEchoServer()!=null)){
                        Date date=new Date(System.currentTimeMillis());
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time=format.format(date);
                        DeviceBean device=new DeviceBean();
                        String deviceid=preference.getDeviceid();
                        deviceid=(!deviceid.equals("") ? deviceid: DeviceInfoUtil.getUniquePsuedoID());
                        device.setDeviceid(deviceid);
                        device.setTime(time);
                        LogUtil.debugLog("start connect socketio");
                        echoServerBySocketio(preference.getEchoServer(),gson.toJson(device));
                        LogUtil.debugLog(gson.toJson(device));
                        return true;
                }
                else
                        return false;

        }
        private void ensureCollectorRunning() {
                ComponentName collectorComponent = new ComponentName(this, /*NotificationListenerService Inheritance*/ NLService.class);
                Log.v(TAG, "ensureCollectorRunning collectorComponent: " + collectorComponent);
                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                boolean collectorRunning = false;
                List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(Integer.MAX_VALUE);
                if (runningServices == null ) {
                        Log.w(TAG, "ensureCollectorRunning() runningServices is NULL");
                        return;
                }
                for (ActivityManager.RunningServiceInfo service : runningServices) {
                        if (service.service.equals(collectorComponent)) {
                                Log.w(TAG, "ensureCollectorRunning service - pid: " + service.pid + ", currentPID: " + Process.myPid() + ", clientPackage: " + service.clientPackage + ", clientCount: " + service.clientCount
                                                + ", clientLabel: " + ((service.clientLabel == 0) ? "0" : "(" + getResources().getString(service.clientLabel) + ")"));
                                if (service.pid == Process.myPid() /*&& service.clientCount > 0 && !TextUtils.isEmpty(service.clientPackage)*/) {
                                        collectorRunning = true;
                                }
                        }
                }
                if (collectorRunning) {
                        Log.d(TAG, "ensureCollectorRunning: collector is running");
                        return;
                }
                Log.d(TAG, "ensureCollectorRunning: collector not running, reviving...");
                toggleNotificationListenerService();
        }

        private void toggleNotificationListenerService() {
                Log.d(TAG, "toggleNotificationListenerService() called");
                ComponentName thisComponent = new ComponentName(this, /*getClass()*/ NLService.class);
                PackageManager pm = getPackageManager();
                pm.setComponentEnabledSetting(thisComponent, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                pm.setComponentEnabledSetting(thisComponent, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        }

        @Override
        public IBinder onBind(Intent intent) {
                return null;
        }

        public class DeviceBean{
                public String deviceid;
                public String connectedtime;
                public void setDeviceid(String deviceid){
                        this.deviceid=deviceid;
                }
                public void setTime(String time){
                        this.connectedtime=time;
                }

        }



}
