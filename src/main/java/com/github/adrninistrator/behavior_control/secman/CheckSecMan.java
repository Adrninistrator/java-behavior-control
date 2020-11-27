package com.github.adrninistrator.behavior_control.secman;

import com.github.adrninistrator.behavior_control.conf.AppConfStore;
import com.github.adrninistrator.behavior_control.constants.BCConstants;
import com.github.adrninistrator.behavior_control.control.ExecControl;
import com.github.adrninistrator.behavior_control.control.SetSecManControl;
import com.github.adrninistrator.behavior_control.control.SocketControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Permission;

/**
 * @author easonzheng
 * @date 2020/6/11
 * @description:
 */

public class CheckSecMan extends SecurityManager {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    private SecurityManager originalSecurityManager;

    public CheckSecMan(SecurityManager origSecurityManager) {
        this.originalSecurityManager = origSecurityManager;
    }

    @Override
    public void checkExec(String cmd) {
        super.checkExec(cmd);

        if (logger.isDebugEnabled()) {
            logger.debug("begin ExecControl.checkExec {}", System.currentTimeMillis());
        }

        try {
            ExecControl.checkExec(cmd);
        } catch (SecurityException e) {
            logger.error("SecurityException ", e);
            throw e;
        } catch (Exception e) {
            logger.error("error {} ", AppConfStore.getDftAlertFlag(), e);
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("end ExecControl.checkExec {}", System.currentTimeMillis());
            }
        }

        // 当现有SecurityManager非空时，调用现有的SecurityManager对应方法
        if (originalSecurityManager != null) {
            originalSecurityManager.checkExec(cmd);
        }
    }

    @Override
    public void checkListen(int port) {
        super.checkListen(port);

        if (logger.isDebugEnabled()) {
            logger.debug("begin SocketControl.checkListen {}", System.currentTimeMillis());
        }

        try {
            SocketControl.checkListen(port);
        } catch (SecurityException e) {
            logger.error("SecurityException ", e);
            throw e;
        } catch (Exception e) {
            logger.error("error {} ", AppConfStore.getDftAlertFlag(), e);
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("end SocketControl.checkListen {}", System.currentTimeMillis());
            }
        }

        // 当现有SecurityManager非空时，调用现有的SecurityManager对应方法
        if (originalSecurityManager != null) {
            originalSecurityManager.checkListen(port);
        }
    }

    @Override
    public void checkAccept(String host, int port) {
        // port为客户端的端口，不是服务器监听的端口，因此不处理port
        super.checkAccept(host, port);

        if (logger.isDebugEnabled()) {
            logger.debug("begin SocketControl.checkAccept {}", System.currentTimeMillis());
        }

        try {
            SocketControl.checkAccept(host);
        } catch (SecurityException e) {
            logger.error("SecurityException ", e);
            throw e;
        } catch (Exception e) {
            logger.error("error {} ", AppConfStore.getDftAlertFlag(), e);
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("end SocketControl.checkAccept {}", System.currentTimeMillis());
            }
        }

        // 当现有SecurityManager非空时，调用现有的SecurityManager对应方法
        if (originalSecurityManager != null) {
            originalSecurityManager.checkAccept(host, port);
        }
    }

    @Override
    public void checkConnect(String host, int port) {
        super.checkConnect(host, port);

        if (logger.isDebugEnabled()) {
            logger.debug("begin SocketControl.checkConnect {}", System.currentTimeMillis());
        }

        try {
            SocketControl.checkConnect(host, port);
        } catch (SecurityException e) {
            logger.error("SecurityException ", e);
            throw e;
        } catch (Exception e) {
            logger.error("error {} ", AppConfStore.getDftAlertFlag(), e);
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("end SocketControl.checkConnect {}", System.currentTimeMillis());
            }
        }

        // 当现有SecurityManager非空时，调用现有的SecurityManager对应方法
        if (originalSecurityManager != null) {
            originalSecurityManager.checkConnect(host, port);
        }
    }

    @Override
    public void checkPermission(Permission permission) {
        doCheckPermission(permission);

        // 当现有SecurityManager非空时，调用现有的SecurityManager对应方法
        if (originalSecurityManager != null) {
            originalSecurityManager.checkPermission(permission);
        }
    }

    @Override
    public void checkPermission(Permission permission, Object context) {
        doCheckPermission(permission);

        // 当现有SecurityManager非空时，调用现有的SecurityManager对应方法
        if (originalSecurityManager != null) {
            originalSecurityManager.checkPermission(permission, context);
        }
    }

    private void doCheckPermission(Permission permission) {
        if (!(permission instanceof RuntimePermission)) {
            return;
        }

        // 处理System.setSecurityManager
        if (logger.isDebugEnabled()) {
            logger.debug("begin checkPermission {} {}", permission, System.currentTimeMillis());
        }

        try {
            SetSecManControl.checkSetSecMan(permission);
        } catch (SecurityException e) {
            logger.error("SecurityException ", e);
            throw e;
        } catch (Exception e) {
            logger.error("error {} ", AppConfStore.getDftAlertFlag(), e);
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("end checkPermission {}", System.currentTimeMillis());
            }
        }
    }
}
