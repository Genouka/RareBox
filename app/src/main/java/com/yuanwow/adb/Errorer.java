package com.yuanwow.adb;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.os.Process;

/**
 * @Author yuanwow
 * @Date 2023/02/17 19:19
 */
public class Errorer extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        ((EditText)findViewById(R.id.activityerrorEditText1)).setText(getIntent().getStringExtra("msg"));
    }

    @Override
    protected void onDestroy() {
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }
    
}
