package test.socket;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.base.TestBase;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestSocketClient1 extends TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TestSocketClient1.class);

    @Test
    public void test() throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        String localAddress = inetAddress.getHostAddress();

        logger.info("localAddress: {}", localAddress);

        new Socket().connect(new InetSocketAddress(localAddress, 8888));
    }
}
