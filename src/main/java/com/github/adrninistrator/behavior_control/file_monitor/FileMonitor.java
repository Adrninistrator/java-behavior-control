package com.github.adrninistrator.behavior_control.file_monitor;

import com.github.adrninistrator.behavior_control.conf.AppConfStore;
import com.github.adrninistrator.behavior_control.constants.BCConstants;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author easonzheng
 * @date 2020/6/13
 * @description:
 */

public class FileMonitor {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    public static void init() {
        logger.info("FileMonitor init");

        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(AppConfStore.getConfPath());
        fileAlterationObserver.addListener(new FileAlterListener());

        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(AppConfStore.getMonitorInterval(),
                fileAlterationObserver);

        // 设置ThreadFactory为自定义，在其中会将创建的线程设置为守护线程
        // 若不进行以上设置，FileAlterationMonitor创建的线程为非守护线程，会导致程序无法自动退出
        fileAlterationMonitor.setThreadFactory(new ThreadFactor4FileMonitor());

        try {
            fileAlterationMonitor.start();
        } catch (Exception e) {
            logger.error("{} fileAlterationMonitor.start error: {} ", AppConfStore.getDftAlertFlag(), e);
            return;
        }

        // 设置进程退出时关闭文件夹监控
        Runtime.getRuntime().addShutdownHook(new Thread(new CloseFileMonitorThread(fileAlterationMonitor)));
    }

    private FileMonitor() {
        throw new IllegalStateException("illegal");
    }
}
