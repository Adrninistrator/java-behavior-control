package com.github.adrninistrator.behavior_control.handler;

import com.github.adrninistrator.behavior_control.constants.BCConstants;
import com.github.adrninistrator.behavior_control.counter.CounterManager;
import com.github.adrninistrator.behavior_control.enums.BehaviorEnum;
import com.github.adrninistrator.behavior_control.util.BCCommUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAlertBehaviorHandler implements BehaviorHandlerInterface {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    @Override
    public void handleExec(String cmd) {
        // 判断当前行为是否还允许告警
        if (!CounterManager.allowExecAlert(cmd)) {
            return;
        }

        logger.error("exec {}{}{} {}", BCConstants.BEHV_LOG_FLAG, cmd, BCConstants.BEHV_LOG_FLAG, BehaviorEnum.BEHV_EXEC.getAlertFlag());

        BCCommUtil.printStack("exec");
    }

    @Override
    public void handleListen(int port, String strPort) {
        // 判断当前行为是否还允许告警
        if (!CounterManager.allowListenAlert(strPort)) {
            return;
        }

        logger.error("listen {}{}{} {}", BCConstants.BEHV_LOG_FLAG, strPort, BCConstants.BEHV_LOG_FLAG, BehaviorEnum.BEHV_LISTEN.getAlertFlag());

        BCCommUtil.printStack("listen");
    }

    @Override
    public void handleAccept(String host) {
        // 判断当前行为是否还允许告警
        if (!CounterManager.allowAcceptAlert(host)) {
            return;
        }

        logger.error("accept {}{}{} {}", BCConstants.BEHV_LOG_FLAG, host, BCConstants.BEHV_LOG_FLAG, BehaviorEnum.BEHV_ACCEPT.getAlertFlag());

        BCCommUtil.printStack("accept");
    }

    @Override
    public void handleConnect(String host, int port, String ipAndPort) {
        // 判断当前行为是否还允许告警
        if (!CounterManager.allowConnectAlert(ipAndPort)) {
            return;
        }

        logger.error("connect {}{}{} {}", BCConstants.BEHV_LOG_FLAG, ipAndPort, BCConstants.BEHV_LOG_FLAG, BehaviorEnum.BEHV_CONNECT.getAlertFlag());

        BCCommUtil.printStack("connect");
    }

    @Override
    public void handleSetSecurityManager() {
        // 判断当前行为是否还允许告警
        if (!CounterManager.allowSetSecManAlert()) {
            return;
        }

        logger.error("setSecurityManager {}", BehaviorEnum.BEHV_SETSECMAN.getAlertFlag());

        BCCommUtil.printStack("setSecurityManager");
    }
}
