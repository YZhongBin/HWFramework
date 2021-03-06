package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.http.params.HttpParams;

@Deprecated
public class SocketInputBuffer extends AbstractSessionInputBuffer {
    private final Socket socket;

    public SocketInputBuffer(Socket socket2, int buffersize, HttpParams params) throws IOException {
        if (socket2 != null) {
            this.socket = socket2;
            init(socket2.getInputStream(), 8192, params);
            return;
        }
        throw new IllegalArgumentException("Socket may not be null");
    }

    @Override // org.apache.http.io.SessionInputBuffer
    public boolean isDataAvailable(int timeout) throws IOException {
        boolean result = hasBufferedData();
        if (!result) {
            int oldtimeout = this.socket.getSoTimeout();
            try {
                this.socket.setSoTimeout(timeout);
                fillBuffer();
                result = hasBufferedData();
            } catch (InterruptedIOException e) {
                if (!(e instanceof SocketTimeoutException)) {
                    throw e;
                }
            } catch (Throwable th) {
                this.socket.setSoTimeout(oldtimeout);
                throw th;
            }
            this.socket.setSoTimeout(oldtimeout);
        }
        return result;
    }

    public boolean isStale() throws IOException {
        boolean z = false;
        if (hasBufferedData()) {
            return false;
        }
        int oldTimeout = this.socket.getSoTimeout();
        try {
            this.socket.setSoTimeout(1);
            if (fillBuffer() == -1) {
                z = true;
            }
            return z;
        } catch (SocketTimeoutException e) {
            return false;
        } catch (IOException e2) {
            return true;
        } finally {
            this.socket.setSoTimeout(oldTimeout);
        }
    }
}
