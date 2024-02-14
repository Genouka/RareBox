package com.yuanwow.adb;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.content.Intent;
import com.genouka.ard.ui.Dialog;
import java.io.IOException;
import android.net.Uri;
import android.app.Activity;
import java.util.ArrayList;
import android.Manifest;
import android.support.v4.content.ContextCompat;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import com.genouka.adblib.AdbCrypto;
import com.genouka.ard.AdbUtils;
import com.genouka.ard.ui.SpinnerDialog;
import java.io.File;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.io.FileOutputStream;
import android.view.View;
import android.view.LayoutInflater;
import yuanwow.wear.dialog.WearDialog;
import android.widget.EditText;
import android.widget.CheckBox;
import java.net.Socket;
import java.net.InetSocketAddress;
import com.genouka.adblib.AdbConnection;
import android.os.Handler;
import android.os.Message;
import com.genouka.adblib.SocketCell;
import android.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Gallery.LayoutParams;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import android.widget.TextView;
import android.view.Gravity;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.provider.Settings;
import android.content.ComponentName;
import android.widget.Button;
import java.util.List;
import android.widget.Toast;
import cn.qqtheme.framework.picker.FilePicker;
import android.graphics.Color;
import android.view.Display;
import android.view.ViewTreeObserver.OnDrawListener;
import android.view.ViewTreeObserver.OnWindowAttachListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;

/**
 * @Author yuanwow
 * @Date 2023/04/28 21:11
 */
public class C {
    public static String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

    private static SpinnerDialog spd;

    private static ArrayList<C.Ent> selectedItems;

    static{
        selectedItems = new ArrayList<C.Ent>();
        selectedItems.add(new C.Ent("修改屏幕信息：修改屏幕尺寸", "wm size ${长}x${宽}"));
        selectedItems.add(new C.Ent("修改屏幕信息：重置屏幕尺寸", "wm size reset"));
        selectedItems.add(new C.Ent("修改屏幕信息：修改dpi", "wm density ${dpi\n警告：不要作死调太大或者太小，一般210-400的范围差不多了}"));
        selectedItems.add(new C.Ent("修改屏幕信息：重置dpi", "wm density reset"));
        selectedItems.add(new C.Ent("修改屏幕信息：修改显示范围", "wm overscan ${左边距},${上边距},${右边距},${下边距}"));
        selectedItems.add(new C.Ent("修改屏幕信息：重置显示范围", "wm overscan reset"));
        selectedItems.add(new C.Ent("修改屏幕信息：屏幕自动旋转", "content insert --uri content://settings/system --bind name:s:accelerometer_rotation --bind value:i:${输入0关闭自动旋转；输入1打开自动旋转}"));
        selectedItems.add(new C.Ent("修改屏幕信息：设置屏幕方向", "adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:${输入0代表竖屏模式；输入1代表横屏模式}"));

        selectedItems.add(new C.Ent("查看电池信息", "dumpsys battery"));
        selectedItems.add(new C.Ent("修改电池信息：模拟充电状态", "dumpsys battery set ${\n输入以下中的一个或多个(用/分开):\n[ac|usb|wireless]\n例如：ac\n例如：ac/usb\n例如：ac/usb/wireless}"));
        selectedItems.add(new C.Ent("修改电池信息：模拟断开充电", "dumpsys battery unplug"));
        selectedItems.add(new C.Ent("修改电池信息：模拟电量百分比", "dumpsys battery level ${输入模拟电量\n此功能不会增加实际电量！！！\n警告：不要输入低于10或者大于100的数字，否则进入无法退出的省电模式后果自负！}"));
        selectedItems.add(new C.Ent("修改电池信息：恢复正常状态", "dumpsys battery reset"));

        selectedItems.add(new C.Ent("下载并安装Rare浏览器", "echo 未完成"));

    }

    public static StringBuilder readTxt(InputStream it) {  
        StringBuilder sb = new StringBuilder();  
        try {
            InputStreamReader isr = new InputStreamReader(it, "UTF-8");  
            BufferedReader br = new BufferedReader(isr);  

            String mimeTypeLine = null ;  
            sb.delete(0, sb.length());  
            while ((mimeTypeLine = br.readLine()) != null) {  
                sb.append(mimeTypeLine + "\n");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return  sb;  
    }  
    public static void rare(Activity a) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //https://www.bilibili.com/video/BV17Y411C7zu
        //"https://space.bilibili.com/3493116076100126"
        intent.setData(Uri.parse("https://rare.genouka.rr.nu/"));
        a.startActivity(intent);
    }
    public static void author(Activity a) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //https://www.bilibili.com/video/BV17Y411C7zu
        //"https://space.bilibili.com/3493116076100126"
        intent.setData(Uri.parse("https://www.bilibili.com/video/BV17Y411C7zu"));
        a.startActivity(intent);
    }
    public static void updatelog(Activity a) {
        try {
            Dialog.displayDialog(a, "更新日志", C.readTxt(a.getAssets().open("log.txt")).toString(), false);
        } catch (IOException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //https://www.bilibili.com/video/BV17Y411C7zu
            //"https://space.bilibili.com/3493116076100126"
            intent.setData(Uri.parse("https://rare.genouka.rr.nu/"));
            a.startActivity(intent);
        }
    }
    public static void duty(Activity a) {
        Intent intent;
        try {
            Dialog.displayDialog(a, "免责声明", C.readTxt(a.getAssets().open("duty.txt")).toString(), false);
        } catch (IOException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            //https://www.bilibili.com/video/BV17Y411C7zu
            //"https://space.bilibili.com/3493116076100126"
            intent.setData(Uri.parse("https://rare.genouka.rr.nu/"));
            a.startActivity(intent);
        }
    }
    public static boolean grantps(Activity a) {
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        ArrayList<String> f=new ArrayList<>();
        for (String i:permissions) {
            if (ContextCompat.checkSelfPermission(a, i) == PackageManager.PERMISSION_DENIED) {
                f.add(i);
            }
        }
        if (f.isEmpty()) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            ActivityCompat.requestPermissions(a, f.toArray(new String[f.size()]), 12);
        } else {

        }
        return false;
    }
    public static void initrsa(final Activity a) {
        AdbCrypto crypto = AdbUtils.readCryptoConfig(a.getFilesDir());
        if (crypto == null) {
            /* We need to make a new pair */
            final SpinnerDialog keygenSpinner = SpinnerDialog.displayDialog(a,
                                                                            "软件初始化",
                                                                            "正在初始化RareBox，请勿退出",
                                                                            true);

            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AdbCrypto crypto;

                        crypto = AdbUtils.writeNewCryptoConfig(a.getFilesDir());
                        keygenSpinner.dismiss();

                        if (crypto == null) {
                            Dialog.displayDialog(a, "初始化失败，请报告开发者",
                                                 "创建RSA密钥失败", 
                                                 true);
                            return;
                        }
                    }
                }).start();
        }
    }

    public static void unzipFile(InputStream zip, File file) throws IOException {

        // 如果目标目录不存在，则创建
        if (!file.exists()) {
            file.mkdirs();
        }
        ZipInputStream zipInputStream = new ZipInputStream(zip);

        // 读取一个进入点
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        // 使用1Mbuffer
        byte[] buffer = new byte[1024 * 1024];
        // 解压时字节计数
        int count = 0;
        // 如果进入点为空说明已经遍历完所有压缩包中文件和目录
        while (zipEntry != null) {
            String fileName = zipEntry.getName();

            //fileName = fileName.substring(fileName.lastIndexOf("/") + 1);  //截取文件的名字 去掉原文件夹名字

            File file1 = new File(file , fileName);  //放到新的解压的文件路径
            if (!zipEntry.isDirectory()) {  //如果是一个文件

                // 如果是文件
                file1.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file1);
                while ((count = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();

            } else {
                file1.mkdirs();
            }

            // 定位到下一个文件入口
            zipEntry = zipInputStream.getNextEntry();

        }
        zipInputStream.close();


    }
    public static void rfile(final Activity a, final Intent linker) {
        final View ll=LayoutInflater.from(a).inflate(R.layout.sendfiledialog, null);
        WearDialog builder = new WearDialog(a);
        builder.setTitle("传输文件");

        builder.setSingle(false);
        builder.setOnClickBottomListener(new WearDialog.OnClickBottomListener(){
                @Override
                public void onPositiveClick(WearDialog w) {
                    final EditText l1=ll.findViewById(R.id.sendfiledialogEditText1);
                    EditText l2=ll.findViewById(R.id.sendfiledialogEditText2);
                    CheckBox c1=ll.findViewById(R.id.sendfiledialogCheckBox1);

                    File local=new File(l1.getText().toString());
                    if (local.exists() && local.isFile()) {
                        sendfile(a, local, l2.getText().toString(), linker, c1.isSelected());
                    } else {
                        Dialog.displayDialog(a, "传输失败", "本地文件不存在", false);
                    }
                    w.dismiss();
                }

                @Override
                public void onNegtiveClick(WearDialog w) {
                    w.dismiss();
                }
            });

        builder.show();
        builder.setContentView(ll);
        ll.findViewById(R.id.sendfiledialogButtonVisit).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    filepicker(a, new FilePicker.OnFilePickListener(){
                            @Override
                            public void onFilePicked(String string) {
                                ((EditText)ll.findViewById(R.id.sendfiledialogEditText1)).setText(string);
                            }
                        });
                }
            });
    }
    private static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1 && msg.arg1 == 2 && spd != null) {
                spd.setMessage("已传输" + msg.arg2 + "%");
            }
        }
    };
    public static void sendfile(final Activity a, final File l, final String r, final Intent linker, final boolean install) {
        spd = SpinnerDialog.displayDialog(a, "传输中", "正在启动传输...", false);
        new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean connected = false;
                    int cn = 0;
                    Socket socket = new Socket();
                    AdbCrypto crypto;

                    /* Load the crypto config */
                    crypto = AdbUtils.readCryptoConfig(a.getFilesDir());
                    if (crypto == null) {
                        return;
                    }
                    Exception err = null;
                    while (cn < 5) {
                        try {
                            /* Establish a connect to the remote host */

                            socket.connect(new InetSocketAddress(linker.getStringExtra("IP"), linker.getIntExtra("Port", 0)), 60);
                            break;
                        } catch (IOException e) {
                            //spd.setMessage("正在重试第" + cn + "次");
                            err = e;
                        }
                        cn++;
                    }
                    if (cn == 5) {
                        spd.dismiss();
                        spd = null;
                        Dialog.displayDialog(a, "连接失败", err.toString(), false);
                        return;
                    }
                    try {
                        /* Establish the application layer connection */
                        AdbConnection connection = AdbConnection.create(new SocketCell(socket), crypto);
                        connection.connect();

                        Push push=new Push(connection, l, r);

                        byte[] b=push.execute(handler);
                        connection.close();
                        String bb=new String(b);
                        if (bb.contains("OKAY")) {
                            spd.dismiss();
                            Dialog.displayDialog(a, "传输成功", "成功将文件传输至远程计算机,具体信息如下:\n" + bb, false);
                            if (install) install_intent(a, linker, r);
                        } else if (bb.contains("FAIL")) {
                            spd.dismiss();
                            Dialog.displayDialog(a, "传输失败", "具体信息如下:\n" + bb, false);
                        } else {
                            spd.dismiss();
                            Dialog.displayDialog(a, "未知状态", "具体信息如下:\n" + bb, false);
                        }
                    } catch (Exception e) {
                        spd.dismiss();
                        Dialog.displayDialog(a, "创建错误", e.toString(), false);

                    }
                    spd = null;
                }
            }).start();
    }
    public static void install_intent(Activity a, Intent shellIntent, String path) {


        shellIntent.putExtra("ipe", "apkp='" + path + "';cat $apkp|pm install -r -S $(wc -c $apkp |sed 's/ .*//g')&&echo '安装成功!'||echo '安装失败!'");

        a.startActivity(shellIntent);
    }

    public static void linkadb(Activity a, Intent linker, boolean fin) {
        //Intent shellIntent = getlinker();
        linker.putExtra("fin", fin);
        a.startActivity(linker);
    }
    public static void cmdmenu(final Activity a, final Intent linker) {

        AlertDialog.Builder builder = new AlertDialog.Builder(a);
        // Set the dialog title
        builder.setTitle("常用终端命令")

            .setAdapter(
            new ArrayAdapter<Ent>(a, android.R.layout.simple_list_item_1, android.R.id.text1, selectedItems){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {//重载该方法，在这个方法中，将每个Item的Gravity设置为CENTER
                    TextView textView = (TextView) super.getView(position, convertView, parent);
                    textView.setGravity(Gravity.CENTER);
                    return textView;
                }
            },
            new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    inputbox(a, selectedItems.get(which).command, new Callback1(){
                            @Override
                            public void onData(String thing) {
                                Intent i=linker;
                                i.putExtra("ipe", thing);
                                a.startActivity(i);
                            }
                        });
                }
            });
        builder.show();

    }


    public static void inputbox(Activity a, final String i, final Callback1 j) {
        final ArrayList<EditText> list=new ArrayList<>();
        LinearLayout ll=new LinearLayout(a);
        ll.setOrientation(LinearLayout.VERTICAL);
        LayoutParams ddr=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(ddr);
        ll.setGravity(Gravity.CENTER);
        Pattern p = Pattern.compile("\\$\\{(.*?)\\}", Pattern.MULTILINE | Pattern.DOTALL);

        Matcher m = p.matcher(i);
        while (m.find()) {
            MatchResult mr = m.toMatchResult();
            String text = mr.group(1);
            TextView itt=new TextView(a);
            EditText inputServer = new EditText(a);
            inputServer.setLayoutParams(ddr);
            itt.setLayoutParams(ddr);
            itt.setGravity(Gravity.CENTER);
            itt.setText(text);
            inputServer.setTag(text);
            list.add(inputServer);
            ll.addView(itt);
            ll.addView(inputServer);
        }
        WearDialog builder = new WearDialog(a);
        builder.setTitle("设置参数并确认继续");
        builder.setMessage("本操作无需设置参数.");
        builder.setSingle(false);
        builder.setOnClickBottomListener(new WearDialog.OnClickBottomListener(){
                @Override
                public void onPositiveClick(WearDialog w) {
                    String ki=i;
                    for (EditText k:list) {
                        ki = ki.replace("${" + (String)k.getTag() + "}", k.getText().toString());
                    }
                    j.onData(ki);
                    w.dismiss();
                }

                @Override
                public void onNegtiveClick(WearDialog w) {
                    w.dismiss();
                }
            });
        builder.show();
        if (list.size() > 0) builder.setContentView(ll);
    }

    public static class Ent {
        public String name;
        public String command;
        public Ent(String n, String c) {
            name = n;
            command = c;
        }
        @Override
        public String toString() {
            return name;
        }

    }
    public static interface Callback1 {
        public void onData(String thing);
    }
    public static void shell(final Activity a, final Intent linker) {
        WearDialog builder = new WearDialog(a);
        builder.setTitle("使用说明");
        try {
            builder.setMessage(C.readTxt(a.getAssets().open("help2.txt")).toString());
        } catch (IOException e) {
            builder.setMessage("本地帮助信息显示失败，请在官网查看教程。");
        }
        builder.setSingle(false);
        builder.setOnClickBottomListener(new WearDialog.OnClickBottomListener(){
                @Override
                public void onPositiveClick(WearDialog w) {
                    try {
                        File bg=new File("/sdcard/rarebox/");
                        //bg.mkdir();
                        C.unzipFile(a.getAssets().open("toolchain.zip"), bg);
                        Intent fi=linker;
                        fi.putExtra("ipe", "cp -r /sdcard/rarebox/* /data/local/tmp/&&cp chmod 777 /data/local/tmp/*&&PATH=$PATH:/data/local/tmp");

                        a.startActivity(fi);

                    } catch (IOException e) {
                        Dialog.displayDialog(a, "安装失败", "工具链无法解压：" + e.toString(), false);
                    }
                    w.dismiss();
                }

                @Override
                public void onNegtiveClick(WearDialog w) {
                    w.dismiss();
                }
            });

        builder.show();
    }
    public static void sendins(final Activity a, final Intent linker, boolean lookup) {
        final EditText inputServer = new EditText(a);
        final Button inp=new Button(a);

        inp.setText("浏览...");

        LinearLayout fpq=new LinearLayout(a);
        fpq.setOrientation(LinearLayout.VERTICAL);
        fpq.addView(inputServer);
        if (lookup) fpq.addView(inp);
        inputServer.getText().append("/sdcard/Download/app.apk");
        WearDialog builder = new WearDialog(a);
        builder.setTitle("设置目标计算机的文件完整路径");
        builder.setSingle(false);
        builder.setOnClickBottomListener(new WearDialog.OnClickBottomListener() {

                @Override
                public void onPositiveClick(WearDialog w) {
                    install_intent(a, linker, inputServer.getText().toString());
                    w.dismiss();
                }

                @Override
                public void onNegtiveClick(WearDialog w) {
                    w.dismiss();
                }


            });
        builder.show();
        builder.setContentView(fpq);
        inp.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    filepicker(a, new FilePicker.OnFilePickListener(){
                            @Override
                            public void onFilePicked(String string) {
                                inputServer.setText(string);
                            }
                        });


                }


            });
    }
    private static void filepicker(Activity a, FilePicker.OnFilePickListener of) {
        Display dd=a.getWindowManager().getDefaultDisplay();
        FilePicker fp=new FilePicker(a, FilePicker.FILE);
        fp.setRootPath("/sdcard/");
        fp.setFillScreen(true);
        fp.setCancelable(true);
        fp.setShowHideDir(true);
        fp.setShowUpDir(true);
        fp.setShowHomeDir(true);
        //fp.setHeight(dd.getHeight());

        fp.setOnFilePickListener(of);
        fp.show();
        final ViewGroup cc=(ViewGroup) fp.getContentView();
        cc.setPadding(dd.getWidth() * 10 / 100, 20, dd.getWidth() * 10 / 100, 20);

        makeblack(cc);
        cc.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(){
                @Override
                public synchronized void onGlobalLayout() {
                    makeblack(cc);
                }
            });
        cc.postDelayed(new Runnable(){
                @Override
                public void run() {
                    makeblack(cc);
                }
            }, 1000);
    }

    public static synchronized void makeblack(View cc) {
        //实现bds广度优先算法
        
        Queue<View> ak=new LinkedList<>();
        ak.offer(cc);
        while(true) {
            View d1=ak.poll();
            if(d1==null) break;
            d1.setBackgroundColor(Color.BLACK);
            if (d1 instanceof TextView) ((TextView)d1).setTextColor(Color.WHITE);
            if (d1 instanceof ViewGroup) {
                ViewGroup cc1=(ViewGroup) d1;
                for (int i=0;i < cc1.getChildCount();i++) {
                    ak.offer(cc1.getChildAt(i));
                }
            }
        }
    }
    /**
     * 打开开发者模式界面
     */
    public static void startDevelopmentActivity(Activity a) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            a.startActivity(intent);
        } catch (Exception e) {
            try {
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.setAction("android.intent.action.View");
                a.startActivity(intent);
            } catch (Exception e1) {
                try {
                    Intent intent = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");//部分小米手机采用这种方式跳转
                    a.startActivity(intent);
                } catch (Exception e2) {

                }

            }
        }
    }
}
