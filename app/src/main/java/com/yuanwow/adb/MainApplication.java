package com.yuanwow.adb;
import android.app.Application;
import android.widget.Toast;
import android.content.Intent;
import android.os.Looper;
import android.os.Handler;
import android.content.Context;
import android.annotation.SuppressLint;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.os.Process;
import java.io.StringWriter;
import java.io.PrintWriter;

import android.util.Log;

/**
 * @Author yuanwow
 * @Date 2023/02/11 21:41
 */
public class MainApplication extends Application {
    
    public static boolean skipcheck=false;
    private static boolean sEarlyCheckSignResult = false;

    private String i="f5fe4906fc28dd0";
    public static boolean getEarlyCheckSignResult(){ return sEarlyCheckSignResult;}

    public MainApplication() {
        // 在构造函数里提早检测
        sEarlyCheckSignResult = earlyCheckSign();
    }
    /**
     * 做普通的签名校验
     */
    private String true1 = "d03609274015852bd";
    private boolean doNormalSignCheck() {
        
        String nowSignMD5 = "";
        try {
            // 得到签名的MD5
            PackageInfo packageInfo = getPackageManager().getPackageInfo(
                getPackageName(),
                PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            String signBase64 = Base64.encodeToString(signs[0].toByteArray(),Base64.NO_WRAP);
            nowSignMD5 = MD5Utils.MD5(signBase64);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return (i+true1).equals(nowSignMD5);
    }
    
    boolean earlyCheckSign(){
        // 手动构造 context
        try {
            Context context = ContextUtils.getContext();
            // 得到签名的MD5
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                context.getPackageName(),
                PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            String signBase64 = Base64.encodeToString(signs[0].toByteArray(),Base64.NO_WRAP);
            String nowSignMD5 = MD5Utils.MD5(signBase64);
            
            return (nowSignMD5)==(i+true1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
                @Override
                public void uncaughtException(Thread t, final Throwable e) {
                    Intent in=new Intent(MainApplication.this, Errorer.class);
                    in.putExtra("msg", ""+e.toString()+"\n"+getExceptionSrintStackTrace(e));
                    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    System.exit(0);
                }
                private String getExceptionSrintStackTrace(Throwable e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    return sw.toString();
                }
                
            });
        Handler handler = new Handler(); 
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!sEarlyCheckSignResult&&!doNormalSignCheck())
                    throw new RuntimeException(new String(Base64.decode("5L2g5piv55uX54mI6L2v5Lu255qE5Y+X5a6z6ICFLOivt+Wwj+W/g+egtOino+iAheakjeWFpeaBtuaEj+S7o+eggSEKCuato+eJiOi9r+S7tueUsUdlbm91a2HlvIDlj5Es5a6M5YWo5YWN6LS5IQoK6K+35pSv5oyB5q2j54mIIQoK5aaC5pyJ5Lu75L2V6ZyA5rGC6K+36IGU57O75byA5Y+R6ICFIQoK55uX54mI5Y+v6IC7LOegtOino+WPr+iAuyzor7flnZrlhrPnu7TmiqTkupLogZTnvZHnjq/looMh",Base64.NO_WRAP)));
                    
                }
            }, 5000); 
        
    }
    
    public static class MD5Utils {

        public static String MD5(String sourceStr) {
            String result = "";
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(sourceStr.getBytes());
                byte b[] = md.digest();
                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                result = buf.toString();
            } catch (NoSuchAlgorithmException e) {
                System.out.println(e);
            }
            return result;
        }
    }
    
    private static class ContextUtils {

        /**
         * 手动构建 Context
         */
        @SuppressLint({"DiscouragedPrivateApi","PrivateApi"})
        public static Context getContext() throws ClassNotFoundException,
        NoSuchMethodException,
        InvocationTargetException,
        IllegalAccessException,
        NoSuchFieldException,
        NullPointerException{

            // 反射获取 ActivityThread 的 currentActivityThread 获取 mainThread
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod =
                activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object mainThreadObj = currentActivityThreadMethod.invoke(null);

            // 反射获取 mainThread 实例中的 mBoundApplication 字段
            Field mBoundApplicationField = activityThreadClass.getDeclaredField("mBoundApplication");
            mBoundApplicationField.setAccessible(true);
            Object mBoundApplicationObj = mBoundApplicationField.get(mainThreadObj);

            // 获取 mBoundApplication 的 packageInfo 变量
            if (mBoundApplicationObj == null) throw new NullPointerException("mBoundApplicationObj 反射值空");
            Class mBoundApplicationClass = mBoundApplicationObj.getClass();
            Field infoField = mBoundApplicationClass.getDeclaredField("info");
            infoField.setAccessible(true);
            Object packageInfoObj = infoField.get(mBoundApplicationObj);

            // 反射调用 ContextImpl.createAppContext(ActivityThread mainThread, LoadedApk packageInfo)
            if (mainThreadObj == null) throw new NullPointerException("mainThreadObj 反射值空");
            if (packageInfoObj == null) throw new NullPointerException("packageInfoObj 反射值空");
            Method createAppContextMethod = Class.forName("android.app.ContextImpl").getDeclaredMethod(
                "createAppContext", 
                mainThreadObj.getClass(), 
                packageInfoObj.getClass());
            createAppContextMethod.setAccessible(true);
            return (Context) createAppContextMethod.invoke(null, mainThreadObj, packageInfoObj);

        }
    }
    
}
