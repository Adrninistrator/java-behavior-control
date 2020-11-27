package test.socket;

import org.junit.Test;
import test.base.TestBase;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class TestSocketServer2 extends TestBase {

//    @Test
    public void test() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8888));
        while (true) {
            serverSocket.accept();
        }
    }
}
