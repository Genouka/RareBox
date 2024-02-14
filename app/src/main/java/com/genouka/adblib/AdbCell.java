package com.genouka.adblib;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * @Author genouka
 * @Date 2023/04/07 17:29
 */
public interface AdbCell {

    public void close() throws IOException;

    public InputStream getInputStream() throws IOException;
    
    public OutputStream getOutputStream() throws IOException;
}
