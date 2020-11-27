package com.github.adrninistrator.behavior_control.file_monitor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by easonzheng on 2018/11/7
 * description:
 */

public class ThreadFactor4FileMonitor implements ThreadFactory {

    private static AtomicInteger ai = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("BC_FileMonitorThread-" + ai.addAndGet(1));

        // 设置线程为守护线程
        thread.setDaemon(true);

        return thread;
    }
}
