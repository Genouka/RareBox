package com.yuanwow.adb;
import com.genouka.adblib.AdbConnection;
import java.io.File;
import com.genouka.adblib.AdbStream;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import android.util.Log;
import android.widget.EditText;

/**
 * @Author yuanwow
 * @Date 2023/03/03 23:40
 */
public class Push {

    private AdbConnection adbConnection;
    private File local;
    private String remotePath;

    public Push(AdbConnection adbConnection, File local, String remotePath) {
        this.adbConnection = adbConnection;
        this.local = local;
        this.remotePath = remotePath;
    }

    public byte[] execute(Handler handler) throws InterruptedException, IOException {

        AdbStream stream = adbConnection.open("sync:");

        String sendId = "SEND";

        String mode = ",33206";

        int length = (remotePath + mode).length();

        stream.write(ByteUtils.concat(sendId.getBytes(), ByteUtils.intToByteArray(length)));

        stream.write(remotePath.getBytes());

        stream.write(mode.getBytes());

        byte[] buff = new byte[adbConnection.getMaxData()];
        InputStream is = new FileInputStream(local);

        long sent = 0;
        long total = local.length();
        int lastProgress = 0;
        while (true) {
            int read = is.read(buff);
            if (read < 0) {
                break;
            }

            stream.write(ByteUtils.concat("DATA".getBytes(), ByteUtils.intToByteArray(read)));

            if (read == buff.length) {
                stream.write(buff);
            } else {
                byte[] tmp = new byte[read];
                System.arraycopy(buff, 0, tmp, 0, read);
                stream.write(tmp);
            }

            sent += read;

            final int progress = (int)(sent * 100 / total);
            if (lastProgress != progress) {
                handler.sendMessage(handler.obtainMessage(1, 2, progress));//自定义的数字
                lastProgress = progress;
            }

        }

        stream.write(ByteUtils.concat("DONE".getBytes(), ByteUtils.intToByteArray((int) System.currentTimeMillis())));

        byte[] res = stream.read();
        // TODO: test if res contains "OKEY" or "FAIL"
        //Log.d("res", new String(res));

        stream.write(ByteUtils.concat("QUIT".getBytes(), ByteUtils.intToByteArray(0)));
        return res;
    }
    
}
