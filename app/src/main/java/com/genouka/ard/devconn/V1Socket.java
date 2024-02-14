package com.genouka.ard.devconn;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketImpl;
import android.net.Proxy;
import java.net.SocketException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketOption;
import java.net.SocketImplFactory;
import java.util.Set;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;

/**
 * @Author yuanwow
 * @Date 2023/03/25 09:40
 */
public class V1Socket extends Socket {

    private LocalSocket socket=new LocalSocket();
    

    public V1Socket() {
        
    }

    private V1Socket(String host, int port) throws IOException, UnknownHostException {}

    private V1Socket(String host, int port, InetAddress localAddr, int localPort) throws IOException {}

    @Deprecated
    private V1Socket(String host, int port, boolean stream) throws IOException {}

    private V1Socket(InetAddress address, int port) throws IOException {}

    private V1Socket(InetAddress address, int port, InetAddress localAddr, int localPort) throws IOException {}

    @Deprecated
    private V1Socket(InetAddress host, int port, boolean stream) throws IOException {}

    private V1Socket(Proxy proxy) {}

    private V1Socket(SocketImpl impl) throws SocketException {}
@Override
    public void bind(SocketAddress bindpoint) throws IOException {
        socket.bind(new LocalSocketAddress("/adb-hub", LocalSocketAddress.Namespace.RESERVED));
    }
@Override
    public synchronized void close() throws IOException {
        socket.close();
    }
@Override
    public void connect(SocketAddress endpoint) throws IOException {
        socket.connect(new LocalSocketAddress("/adb-hub", LocalSocketAddress.Namespace.RESERVED), 60);
    }
@Override
    public void connect(SocketAddress endpoint, int timeout) throws IOException {
        socket.connect(new LocalSocketAddress("/adb-hub", LocalSocketAddress.Namespace.RESERVED), timeout);
    }

    public SocketChannel getChannel() {return null;
    }

    public InetAddress getInetAddress() {return null;
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public boolean getKeepAlive() throws SocketException {return true;
    }

    public InetAddress getLocalAddress() {return null;
    }

    public int getLocalPort() {return 0;
    }

    public SocketAddress getLocalV1SocketAddress() {return null;
    }

    public boolean getOOBInline() throws SocketException {return false;
    }

    public <T extends Object> T getOption(SocketOption<T> name) throws IOException {return null;
    }

    public OutputStream getOutputStream() throws IOException {return socket.getOutputStream();
    }

    public int getPort() {return 0;
    }
@Override
    public synchronized int getReceiveBufferSize() throws SocketException {
        try {
            return socket.getReceiveBufferSize();
        } catch (IOException e) {
            throw new SocketException(e.toString());
        }
    }

    public SocketAddress getRemoteV1SocketAddress() {return null;
    }

    public boolean getReuseAddress() throws SocketException {return false;
    }
@Override
    public synchronized int getSendBufferSize() throws SocketException {
        try {
            return socket.getSendBufferSize();
        } catch (IOException e) {
            throw new SocketException(e.toString());
        }
    }

    public int getSoLinger() throws SocketException {return 0;
    }
@Override
    public synchronized int getSoTimeout() throws SocketException {
        try {
            return socket.getSoTimeout();
            
        } catch (IOException e) {
            throw new SocketException(e.toString());
        }
    }

    public boolean getTcpNoDelay() throws SocketException {return false;
    }

    public int getTrafficClass() throws SocketException {return 0;
    }

    public boolean isBound() {return socket.isBound();
    }

    public boolean isClosed() {return socket.isClosed();
    }

    public boolean isConnected() {return socket.isConnected();
    }

    public boolean isInputShutdown() {return socket.isInputShutdown();
    }

    public boolean isOutputShutdown() {return socket.isOutputShutdown();
    }
@Override
    public void sendUrgentData(int data) throws IOException {
        throw new IOException("不支持");
    }

    public void setKeepAlive(boolean on) throws SocketException {
        
    }

    public void setOOBInline(boolean on) throws SocketException {
        
    }

    public <T extends Object> V1Socket setOption(SocketOption<T> name, T value) throws IOException {return null;
    }

    public void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {}

    public synchronized void setReceiveBufferSize(int size) throws SocketException {}

    public void setReuseAddress(boolean on) throws SocketException {
        
    }
@Override
    public synchronized void setSendBufferSize(int size) throws SocketException {
        try {
            socket.setSendBufferSize(size);
        } catch (IOException e) {
            throw new SocketException(e.toString());
        }
    }

    public static synchronized void setSocketImplFactory(SocketImplFactory fac) throws IOException {}

    public void setSoLinger(boolean on, int linger) throws SocketException {
        
    }
@Override
    public synchronized void setSoTimeout(int timeout) throws SocketException {
        try {
            socket.setSoTimeout(timeout);
        } catch (IOException e) {
            throw new SocketException(e.toString());
        }
    }
@Override
    public void setTcpNoDelay(boolean on) throws SocketException {
        
    }

    public void setTrafficClass(int tc) throws SocketException {}

    public void shutdownInput() throws IOException {
        socket.shutdownInput();
    }

    public void shutdownOutput() throws IOException {
        socket.shutdownOutput();
    }

    public Set<SocketOption<?>> supportedOptions() {return null;
    }

	public String toString() {return socket.toString();
    }

}
