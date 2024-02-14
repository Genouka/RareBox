package com.yuanwow.adb;

import android.app.Activity;
import android.os.Bundle;
/*
import com.yhao.floatwindow.FloatWindow;
import android.view.LayoutInflater;
import com.yhao.floatwindow.Screen;
import com.yhao.floatwindow.ViewStateListener;
import android.content.pm.PackageManager;
import java.util.List;
import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.cgutman.androidremotedebugger.ui.Dialog;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View;
//import com.lzf.easyfloat.EasyFloat;

/**
 * @Author yuanwow
 * @Date 2023/03/11 16:41
 */
public class FloatOptionActivity extends Activity {
/*
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.open:
            case R.id.reset:
                open();
                getRunningApp();
                break;
            case R.id.close:
                FloatWindow.destroy();
            
        }
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatoption);
        for (int i:new int[]{R.id.open,R.id.close,R.id.reset}) {
            findViewById(i).setOnClickListener(this);
        }
        //EasyFloat.with(this).setLayout(R.layout.floatwindow).show();
        open();
        
    }

    private void open() {
        if(FloatWindow.get()!=null) FloatWindow.destroy();
        FloatWindow
            .with(getApplicationContext())
            .setView(LayoutInflater.from(this).inflate(R.layout.floatwindow, null))
            .setWidth(50)                               //设置控件宽高
            .setHeight(50)
            .setX(0)                                   //设置控件初始位置
            .setY(Screen.height, 0.5f)
            .setDesktopShow(true)                        //桌面显示

            .setViewStateListener(new ViewStateListener(){

                @Override
                public void onBackToDesktop() {
                }

                @Override
                public void onDismiss() {
                }

                @Override
                public void onHide() {
                }

                @Override
                public void onMoveAnimEnd() {
                }

                @Override
                public void onMoveAnimStart() {
                }

                @Override
                public void onPositionUpdate(int p, int p1) {
                }

                @Override
                public void onShow() {
                }


            })    //监听悬浮控件状态改变
            .build();
    }
    public void getRunningApp(){
        StringBuilder s=new StringBuilder();
        PackageManager localPackageManager = getPackageManager();
        List localList = localPackageManager.getInstalledPackages(0);
        for (int i = 0; i < localList.size(); i++) {
            PackageInfo localPackageInfo1 = (PackageInfo) localList.get(i);
            String str1 = localPackageInfo1.packageName.split(":")[0];
            if (((ApplicationInfo.FLAG_SYSTEM & localPackageInfo1.applicationInfo.flags) == 0) && ((ApplicationInfo.FLAG_UPDATED_SYSTEM_APP & localPackageInfo1.applicationInfo.flags) == 0) && ((ApplicationInfo.FLAG_STOPPED & localPackageInfo1.applicationInfo.flags) == 0)) {
                s.append(str1+"\n");
            }
        }
        Dialog.displayDialog(this,"Apps",s.toString(),false);
    }
    */
}
