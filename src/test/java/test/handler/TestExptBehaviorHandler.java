package test.handler;

import com.github.adrninistrator.behavior_control.handler.BehaviorHandlerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestExptBehaviorHandler implements BehaviorHandlerInterface {

    private static final Logger logger = LoggerFactory.getLogger(TestExptBehaviorHandler.class);

    @Override
    public void handleExec(String cmd) {
        logger.error("exec: {}", cmd);
        throw new SecurityException("exec");
    }

    @Override
    public void handleListen(int port, String strPort) {
        logger.error("listen: {}", strPort);
        throw new SecurityException("listen");
    }

    @Override
    public void handleAccept(String host) {
        logger.error("accept: {}", host);
        throw new SecurityException("accept");
    }

    @Override
    public void handleConnect(String host, int port, String ipAndPort) {
        logger.error("connect: {}", ipAndPort);
        throw new SecurityException("connect");
    }

    @Override
    public void handleSetSecurityManager() {
        logger.error("setSecurityManager");
    }
}
