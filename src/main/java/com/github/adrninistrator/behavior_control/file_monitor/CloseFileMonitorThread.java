package com.github.adrninistrator.behavior_control.file_monitor;

import com.github.adrninistrator.behavior_control.constants.BCConstants;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by easonzheng on 2018/11/7
 * description:
 */

public class CloseFileMonitorThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    private FileAlterationMonitor fileAlterationMonitor;

    public CloseFileMonitorThread(FileAlterationMonitor fileAlterationMonitor) {
        this.fileAlterationMonitor = fileAlterationMonitor;
    }

    @Override
    public void run() {
        System.out.println("fileAlterationMonitor.stop...");
        logger.info("fileAlterationMonitor.stop...");

        try {
            fileAlterationMonitor.stop();
        } catch (Exception e) {
            System.out.println("fileAlterationMonitor.stop error: " + e.getMessage());
            logger.error("fileAlterationMonitor.stop error: ", e);
        }
    }
}