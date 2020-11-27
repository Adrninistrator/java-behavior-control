package com.github.adrninistrator.behavior_control.control;

import com.github.adrninistrator.behavior_control.conf.AppConfStore;
import com.github.adrninistrator.behavior_control.conf.ControlConfStore;
import com.github.adrninistrator.behavior_control.constants.BCConstants;
import com.github.adrninistrator.behavior_control.handler.BehaviorHandlerInterface;

/**
 * @author easonzheng
 * @date 2020/6/11
 * @description:
 */

public class SocketControl {

    public static void checkListen(int port) {
        // 判断是否允许执行，listen配置文件格式为：[port]，即监听的端口
        String strPort = String.valueOf(port);
        if (ControlConfStore.checkListen(strPort)) {
            return;
        }

        // 对发现的异常行为进行处理
        BehaviorHandlerInterface handler = AppConfStore.getHandler();
        handler.handleListen(port, strPort);
    }

    public static void checkAccept(String host) {
        if ("127.0.0.1".equals(host) || "localhost".equals(host) || "::1".equals(host) || "0:0:0:0:0:0:0:1".equals(host)) {
            // 本机访问时，不处理
            return;
        }

        // 判断是否允许执行，accept配置文件格式为：[ip]，即连接对应的客户端IP
        if (ControlConfStore.checkAccept(host)) {
            return;
        }

        // 对发现的异常行为进行处理
        BehaviorHandlerInterface handler = AppConfStore.getHandler();
        handler.handleAccept(host);
    }

    public static void checkConnect(String host, int port) {
        if ("127.0.0.1".equals(host) || "localhost".equals(host) || "::1".equals(host) || "0:0:0:0:0:0:0:1".equals(host)) {
            // 访问本机时，不处理
            return;
        }

        if (port < 0 || port > 0xFFFF) {
            // 当端口不是正常范围时，不需要处理
            return;
        }

        // 判断是否允许执行，connect配置文件格式为：[ip]:[port]，即连接对应的服务器IP与端口
        String ipAndPort = host + BCConstants.IP_PORT_FLAG + port;
        if (ControlConfStore.checkConnect(ipAndPort)) {
            return;
        }

        // 对发现的异常行为进行处理
        BehaviorHandlerInterface handler = AppConfStore.getHandler();
        handler.handleConnect(host, port, ipAndPort);
    }

    private SocketControl() {
        throw new IllegalStateException("illegal");
    }
}
