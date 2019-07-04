/*
 * Created By WeihuaGu (email:weihuagu_work@163.com)
 * Copyright (c) 2017
 * All right reserved.
 */

package com.lhalcyon.pl.util;

import android.util.Log;


public class LogUtil {
        public static String TAG="NLService";
        public static String DEBUGTAG="NLDebugService";
        public static String EXCEPTIONTAG="NLExceptionService";
        public static void infoLog(String info){
                Log.i(TAG,info);
        }

        public static void debugLog(String info){
                Log.d(TAG,info);
        }
        
        public static void debugLogWithDeveloper(String info){
                Log.d(DEBUGTAG,info);
        }

        public static void debugLogWithJava(String info){
                System.out.println(DEBUGTAG+":"+info);
        }

        public static void postRecordLog(String tasknum,String post){
                Log.i(TAG,"*********************************");
                Log.i(TAG,"开始推送 随机序列号:"+tasknum);
                Log.i(TAG,post);
        }

        public static void postResultLog(String tasknum,String result,String returnstr){

                Log.i(TAG,"推送结果 随机序列号:"+tasknum);
                Log.i(TAG,"推送结果:"+result);
                Log.i(TAG,"返回内容:" + returnstr);
                Log.i(TAG,"------------------------------------------");


        }

}
