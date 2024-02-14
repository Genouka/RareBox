package com.genouka.adblib;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import android.net.LocalSocket;
import android.net.LocalServerSocket;

/**
 * @Author genouka
 * @Date 2023/04/07 17:36
 */
public class LocalSocketCell implements AdbCell {

    private LocalSocket ls;
    
    public LocalSocketCell(LocalSocket ls){
        this.ls=ls;
    }

    @Override
    public void close() throws IOException {
        ls.close();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return ls.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return ls.getOutputStream();
    }

}
