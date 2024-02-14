package com.yuanwow.adb;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.genouka.adblib.AdbCrypto;
import com.genouka.ard.AdbShell;
import com.genouka.ard.AdbUtils;
import com.genouka.ard.ui.Dialog;
import com.genouka.ard.ui.SpinnerDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.genouka.adblib.AdbConnection;
import java.net.Socket;
import java.net.InetSocketAddress;
import com.genouka.ard.devconn.DeviceConnectionListener;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.ArrayAdapter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import android.view.ViewGroup;
import yuanwow.wear.dialog.WearDialog;
import android.widget.ListView;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;
import com.genouka.adblib.SocketCell;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.text.Editable;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.view.WindowManager;
import android.view.Display;

public class FullActivity extends Activity implements OnClickListener {
    
    private Switch hwmode,huaweicoremode;
    private EditText u;

    private SpinnerDialog keygenSpinner;

    // 要申请的权限
    
    

    private HistoryUtils ipHistory;

    
    private SpinnerDialog spd;

    private Switch newmainmode;

    private SharedPreferences spf;
    
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            
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
                if(C.grantps(this)){
                    v.setVisibility(View.GONE);
                }

                break;
            case R.id.command:
                C.linkadb(this,getlinker(),false);
                break;

                //startActivityForResult(new Intent(this,com.cgutman.androidremotedebugger.ConnectActivity.class),RESULT_OK);
            case R.id.ins:

                C.sendins(this,getlinker(),false);
                break;
            case R.id.rfile:
                /*
                 File d=new File(getFilesDir(), "txd.sh");
                 d.setExecutable(true);
                 try {
                 InputStream input=new FileInputStream(d);
                 byte[] byt = new byte[input.available()];

                 input.read(byt);
                 sendadbbb(byt, true);
                 } catch (Exception e) {
                 Dialog.displayDialog(this, "插件访问失败,联系开发者解决", e.toString(), false);
                 }
                 */
                C.rfile(this,getlinker());
                break;
            
            case R.id.quit:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
        }
    }

    

    

    
    

    

    private void sendadbbb(final byte[] readAllBytes, final boolean p1) {
        final EditText inputServer = new EditText(this);
        final EditText inputServer2 = new EditText(this);
        inputServer.getText().append("/sdcard/local.txt");
        inputServer2.getText().append("/sdcard/remote.txt");
        final LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(inputServer);
        ll.addView(inputServer2);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置本地/目标计算机的文件完整路径").setIcon(android.R.drawable.ic_dialog_info).setView(ll)
            .setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Intent shellIntent = new Intent(FullActivity.this, AdbShell.class);
                    shellIntent.putExtra("cmd", ":");
                    shellIntent.putExtra("cmdb", readAllBytes);
                    shellIntent.putExtra("fin", p1);
                    startActivity(shellIntent);
                    shellIntent = new Intent(FullActivity.this, AdbShell.class);
                    shellIntent.putExtra("cmd", "mv /sdcard/1 '" + inputServer2.getText().toString() + "'");
                    shellIntent.putExtra("fin", p1);
                    startActivity(shellIntent);
                    try {
                        Process up=Runtime.getRuntime().exec("cat '" + inputServer.getText().toString() + "'|nc " + u.getText().toString().split(":")[0] + " 17979");
                        try {
                            if (up.waitFor() != 0) {
                                Dialog.displayDialog(FullActivity.this, "命令执行失败", "返回值非0", false);
                            }
                        } catch (InterruptedException e) {
                            Dialog.displayDialog(FullActivity.this, "命令执行失败", e.toString(), false);
                        }
                    } catch (IOException e) {
                        Dialog.displayDialog(FullActivity.this, "设备兼容性问题", e.toString(), false);
                    }
                }
            });
        builder.show();

    }

    private void sendadb(String p0, boolean p1) {
        Intent shellIntent = new Intent(this, AdbShell.class);
        shellIntent.putExtra("cmd", p0);
        shellIntent.putExtra("fin", p1);
        startActivity(shellIntent);
    }
    private Intent getlinker() {
        
        String ip=u.getText().toString();
        ipHistory.add(ip);
        ipHistory.save();
        Intent shellIntent = new Intent(this, AdbShell.class);
        String[] iiu=ip.split(":");
        if (iiu.length < 2) {
            Dialog.displayDialog(this, "Invaild Data", "必须是正确的ip:端口的格式", false);
            return null;
        }
        int port;

        shellIntent.putExtra("IP", iiu[0]);
        try {
            port = Integer.parseInt(iiu[1]);
            if (port < 0 || port > 65535) {
                Dialog.displayDialog(this, "Invalid Port", "The port number must be between 1 and 65535", false);
                return null;
            }
            shellIntent.putExtra("Port", port);
        } catch (NumberFormatException e) {
            Dialog.displayDialog(this, "Invalid Port", "The port must be an integer", false);
            return null;
        }
        return shellIntent;
    }
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_main);
        
        if(C.grantps(this)){
            findViewById(R.id.grant).setVisibility(View.GONE);
        }
        C.initrsa(this);
        
        File ku=getFilesDir();
        TextView vl=findViewById(R.id.activitymainVersionLabel);
        try {
            vl.setText("当前版本:" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {}

        
        u = findViewById(R.id.activitymainEditText1);
        for (int i:new int[]{R.id.grant,R.id.ins,R.id.author,R.id.command,R.id.rfile,R.id.updatelog,R.id.quit,R.id.rare,R.id.cmds,R.id.duty}) {
            findViewById(i).setOnClickListener(this);
        }
        
        huaweicoremode=findViewById(R.id.activitymainSwitchHuawei);
        huaweicoremode.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        //TODO:华为安装模式
                    }
                }
            });
        newmainmode=findViewById(R.id.activitymainSwitchNewmain);
        spf=getSharedPreferences("checker",MODE_PRIVATE);
        newmainmode.setChecked(spf.getInt("isphone",0)==2);
        newmainmode.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    
                    spf.edit().putInt("isphone",isChecked?2:1).commit();
                    Toast.makeText(FullActivity.this,"重启应用程序生效",Toast.LENGTH_SHORT).show();
                }
            });
            
        
        ipHistory=HistoryUtils.loadCommandHistoryFromPrefs(100,this,"ips");
        
        u.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    openContextMenu(v);
                    return true;
                }
            });
        registerForContextMenu(u);
        /*
         if(!new File(u,"txd.sh").exists()){
         try {
         unzipFile(getResources().openRawResource(R.raw.txd),u);
         } catch (IOException e) {
         Dialog.displayDialog(this,"警告","解压txd插件失败,将无法使用传输文件功能.如您不需要该功能忽略警告即可.",false);
         }
         }
         */
    }

    

    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v == u) {
            ipHistory.populateMenu(menu);
        } 
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==0){
            Editable f=u.getText();
            f.clear();
            f.append(item.getTitle());
        }
        return true;
    }

    
    }
