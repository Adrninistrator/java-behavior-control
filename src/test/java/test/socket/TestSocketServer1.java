package test.socket;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.base.TestBase;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TestSocketServer1 extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TestSocketServer1.class);

//    @Test
    public void test() throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8888));
        while (true) {
            Socket socket = serverSocket.accept();
            logger.info("accept: {}", socket.getInetAddress());
            break;
        }
    }
}
