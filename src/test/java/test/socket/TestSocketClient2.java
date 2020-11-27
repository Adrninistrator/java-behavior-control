package test.socket;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.base.TestBase;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestSocketClient2 extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TestSocketClient1.class);

    private String localAddress;

    @Test
    public void test() throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        localAddress = inetAddress.getHostAddress();

        logger.info("localAddress: {}", localAddress);

        runMulti(100, 300, 300);
    }

    @Override
    protected void operate() {
        try {
            new Socket().connect(new InetSocketAddress(localAddress, 8888));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
