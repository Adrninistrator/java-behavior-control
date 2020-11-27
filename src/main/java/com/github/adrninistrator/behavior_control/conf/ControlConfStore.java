package com.github.adrninistrator.behavior_control.conf;

import java.util.Set;

/**
 * @author easonzheng
 * @date 2020/6/11
 * @description:
 */

public class ControlConfStore {

    private static Set<String> execSet;

    private static Set<String> listenSet;

    private static Set<String> connectSet;

    private static Set<String> acceptSet;

    public static void setExecSet(Set<String> execSet) {
        ControlConfStore.execSet = execSet;
    }

    public static void setListenSet(Set<String> listenSet) {
        ControlConfStore.listenSet = listenSet;
    }

    public static void setConnectSet(Set<String> connectSet) {
        ControlConfStore.connectSet = connectSet;
    }

    public static void setAcceptSet(Set<String> acceptSet) {
        ControlConfStore.acceptSet = acceptSet;
    }

    // 返回true代表允许
    public static boolean checkExec(String cmd) {
        return execSet.contains(cmd);
    }

    // 返回true代表允许
    public static boolean checkListen(String strPort) {
        return listenSet.contains(strPort);
    }

    // 返回true代表允许
    public static boolean checkConnect(String ipAndPort) {
        return connectSet.contains(ipAndPort);
    }

    // 返回true代表允许
    public static boolean checkAccept(String ip) {
        return acceptSet.contains(ip);
    }

    private ControlConfStore() {
        throw new IllegalStateException("illegal");
    }
}
