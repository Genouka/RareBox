package com.yuanwow.adb;

import android.app.Activity;
import android.os.Bundle;
import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import android.widget.TextView;
import android.view.Window;
import android.view.View;
import java.util.Map;
import java.util.HashMap;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import com.genouka.ard.ui.Dialog;
import java.io.IOException;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.Display;
import com.genouka.ard.AdbShell;
import java.net.Socket;
import android.os.HardwarePropertiesManager;
import yuanwow.wear.dialog.WearDialog;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.os.Looper;

/**
 * @Author yuanwow
 * @Date 2023/04/28 20:51
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Intent getlinker() {
        
        Intent shellIntent = new Intent(this, AdbShell.class);
        shellIntent.putExtra("IP", "127.0.0.1");
        shellIntent.putExtra("Port", 5555);
        return shellIntent;
    }
    
    @Override
    public void onClick(View v) {
        
        switch (v.getId()) {
            case R.id.command:
                C.linkadb(this,getlinker(),false);
                break;
            case R.id.cmds:
                C.cmdmenu(this,getlinker());
                break;
            case R.id.rare:
                C.rare(this);
                break;
            case R.id.duty:
                C.duty(this);
                break;
            case R.id.updatelog:
                C.updatelog(this);
                break;
            case R.id.author:

                C.author(this);
                break;
            case R.id.grant:
                if(C.grantps(this))
                v.setVisibility(View.GONE);
                break;
                
            case R.id.ins:
                C.sendins(this,getlinker(),true);
                break;
            case R.id.fullver:
                jumpfull();
                break;
            case R.id.quit:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
        }
    }

    private void jumpfull() {
        startActivity(new Intent(this,FullActivity.class));
        finish();
    }

    
    
    private Map<Integer,View> views;
    
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        final SharedPreferences sharedPreferences = getSharedPreferences("checker",MODE_PRIVATE);
        if(sharedPreferences.getInt("isphone",0)==1){
            jumpfull();
            
            return;
        }
        if(sharedPreferences.getInt("isphone",0)!=2&&getPackageManager().hasSystemFeature(PackageManager.FEATURE_APP_WIDGETS)&&!MainApplication.skipcheck){
            WearDialog wd=new WearDialog(this);
            wd.setTitle("询问");
            wd.setMessage("请问您现在是在手机上还是手表上打开此应用程序?");
            wd.setPositive("手机");
            wd.setNegtive("手表");
            wd.setOnClickBottomListener(new WearDialog.OnClickBottomListener(){
                    @Override
                    public void onPositiveClick(WearDialog w) {
                        //获取SharedPreferences对象
                                                //获取Editor对象的引用
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        
                        
                        editor.putInt("isphone",1);
                        // 提交数据
                        editor.commit();
                        
                        startActivity(new Intent(MainActivity.this,FullActivity.class));
                        w.dismiss();
                        finish();
                    }

                    @Override
                    public void onNegtiveClick(WearDialog w) {
                        MainApplication.skipcheck=true;
                        
                        SharedPreferences.Editor editor = sharedPreferences.edit();


                        editor.putInt("isphone",2);
                        // 提交数据
                        editor.commit();
                        
                        w.dismiss();
                        recreate();
                    }
                });
            wd.show();
            return;
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*
        WindowManager m = getWindowManager();   
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高 
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值  
        p.height = (int) (d.getWidth());   //高度设置为屏幕的0.7 
        p.width = (int) (d.getWidth());    //宽度设置为屏幕的0.7
        getWindow().setAttributes(p);     //设置生效
        */
        setContentView(R.layout.activity_newmain);
        C.initrsa(this);
        new Thread(){
            @Override
            public void run(){
                try {
                    new Socket("127.0.0.1", 5555);
                } catch (IOException e) {
                    Looper.prepare();
                    WearDialog wd=new WearDialog(MainActivity.this);
                    wd.setTitle("WLAN调试未开启");
                    
                    try {
                        wd.setMessage(C.readTxt(getAssets().open("help1.txt")).toString());
                        //Dialog.displayDialog(MainActivity.this, "WLAN调试未开启", C.readTxt(getAssets().open("help1.txt")).toString(), false);
                    } catch (IOException e1) {
                        wd.setMessage("本地帮助文件无法打开，请在官网查找帮助信息。");
                        //Dialog.displayDialog(MainActivity.this,"WLAN调试未开启","本地帮助文件无法打开，请在官网查找帮助信息。",false);
                    }
                    wd.setSingle(false);
                    wd.setPositive("忽略并继续");
                    wd.setNegtive("打开开发者选项");
                    wd.setOnClickBottomListener(new WearDialog.OnClickBottomListener(){
                            @Override
                            public void onPositiveClick(WearDialog w) {
                                w.dismiss();
                            }
                            @Override
                            public void onNegtiveClick(WearDialog w) {
                                
                                C.startDevelopmentActivity(MainActivity.this);//跳转到开发者选项界面
                                
                                w.dismiss();
                            }
                        });
                    wd.show();
                    Looper.loop();
                }
            }
        }.start();
        views=new HashMap<>();
        for (int i:new int[]{R.id.grant,R.id.ins,R.id.command,R.id.updatelog,R.id.quit,R.id.rare,R.id.cmds,R.id.duty,R.id.fullver}) {
            View p1=findViewById(i);
            views.put(i,p1);
            p1.setOnClickListener(this);
        }
        if(C.grantps(this)){
            views.get(R.id.grant).setVisibility(View.GONE);
        }

        TextView vl=findViewById(R.id.activitymainVersionLabel);
        try {
            vl.setText("当前版本:" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {}
        
        
        
    }
    
}
