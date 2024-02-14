package com.genouka.adblib;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.io.IOException;
import java.net.SocketException;

/**
 * @Author genouka
 * @Date 2023/04/07 17:32
 */
public class SocketCell implements AdbCell {

    private Socket socket;
    
    public SocketCell(Socket socket){
        this.socket=socket;
        try {
            socket.setTcpNoDelay(true);
        } catch (SocketException e) {}
    }
    
    @Override
    public void close() throws IOException {
        socket.close();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }
    
}
