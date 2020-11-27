package com.github.adrninistrator.behavior_control.conf;

import com.github.adrninistrator.behavior_control.handler.BehaviorHandlerInterface;

import java.io.File;

/**
 * @author easonzheng
 * @date 2020/6/11
 * @description:
 */

public class AppConfStore {

    private static String confPath;

    private static BehaviorHandlerInterface handler;

    private static long monitorInterval;

    private static String dftAlertFlag;

    private static int maxAlertTimes;


    public static void store(String confPath, BehaviorHandlerInterface handler, long monitorInterval, String dftAlertFlag, int maxAlertTimes) {
        storeConfPath(confPath);

        AppConfStore.handler = handler;
        AppConfStore.monitorInterval = monitorInterval < 0 ? 0 : monitorInterval;
        AppConfStore.dftAlertFlag = dftAlertFlag;
        AppConfStore.maxAlertTimes = maxAlertTimes < 0 ? 0 : maxAlertTimes;
    }

    // 保存路径，结尾带目录分隔符
    private static void storeConfPath(String confPath) {
        if (confPath == null || confPath.trim().isEmpty()) {
            AppConfStore.confPath = "";
            return;
        }

        if (confPath.endsWith(File.separator)) {
            AppConfStore.confPath = confPath;
            return;
        }

        AppConfStore.confPath = confPath + File.separator;
    }

    // 返回的路径结尾带目录分隔符
    public static String getConfPath() {
        return confPath;
    }

    public static BehaviorHandlerInterface getHandler() {
        return handler;
    }

    public static long getMonitorInterval() {
        return monitorInterval;
    }

    public static String getDftAlertFlag() {
        return dftAlertFlag;
    }

    public static int getMaxAlertTimes() {
        return maxAlertTimes;
    }

    private AppConfStore() {
        throw new IllegalStateException("illegal");
    }
}
