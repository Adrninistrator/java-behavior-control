package com.github.adrninistrator.behavior_control.control;

import com.github.adrninistrator.behavior_control.conf.AppConfStore;
import com.github.adrninistrator.behavior_control.conf.ConfManager;
import com.github.adrninistrator.behavior_control.constants.BCConstants;
import com.github.adrninistrator.behavior_control.enums.BehaviorEnum;
import com.github.adrninistrator.behavior_control.file_monitor.FileMonitor;
import com.github.adrninistrator.behavior_control.handler.BehaviorHandlerInterface;
import com.github.adrninistrator.behavior_control.handler.DefaultAlertBehaviorHandler;
import com.github.adrninistrator.behavior_control.secman.CheckSecMan;
import com.github.adrninistrator.behavior_control.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.Permission;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author easonzheng
 * @date 2020/6/10
 * @description: 进程行为控制类
 */

public class BehaviorControl {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    private static final AtomicBoolean inited = new AtomicBoolean(false);

    // 记录初始化是否完成，仅单元测试时需要使用
    private static boolean initDone = false;

    /**
     * 对进程行为控制进行注册，使用默认参数
     *
     * @param confPath 保存配置文件的目录，结尾可包含或不包含目录分隔符“/"”\"。配置文件某行的内容若前后包含空格，在读取时会被去除
     */
    public static void register(String confPath) {
        register(confPath, new DefaultAlertBehaviorHandler(), BCConstants.DFT_MONITOR_INTERVAL, BCConstants.DFT_ALERT_FLAG,
                BCConstants.DFT_MAX_ALERT_TIMES);
    }

    /**
     * 对进程行为控制进行注册
     *
     * @param confPath        保存配置文件的目录，结尾可包含或不包含目录分隔符“/"”\"。配置文件某行的内容若前后包含空格，在读取时会被去除
     * @param monitorInterval 监控配置文件发生变化的时间间隔，单位为毫秒，默认：30000L，代表30秒
     * @param dftAlertFlag    默认的告警关键字，用于在出现异常时告警，默认：DftErrorFlag
     * @param maxAlertTimes   某个未知行为的最大告警次数，可为0，默认：3
     */
    public static void register(String confPath, BehaviorHandlerInterface handler, long monitorInterval, String dftAlertFlag, int maxAlertTimes) {
        if (!inited.compareAndSet(false, true)) {
            logger.info("initialized, exit");
            return;
        }

        new Thread(() -> doRegister(confPath, handler, monitorInterval, dftAlertFlag, maxAlertTimes)).start();
    }

    private static void doRegister(String confPath, BehaviorHandlerInterface handler, long monitorInterval, String dftAlertFlag, int maxAlertTimes) {
        if (!FileUtil.isDirectoryExists(confPath)) {
            logger.error("directory not exists: {}", confPath);
            return;
        }

        long startTime = System.currentTimeMillis();
        // dftAlertFlag不打印，否则会告警
        logger.info("begin initialize {} {} {}", confPath, monitorInterval, maxAlertTimes);

        try {
            // 当配置文件不存在时，尝试创建
            for (BehaviorEnum behaviorEnum : BCConstants.INIT_CONF_ENUMS) {
                FileUtil.createNotExistsFile(confPath + File.separator + behaviorEnum.getConfFileName());
            }

            AppConfStore.store(confPath, handler, monitorInterval, dftAlertFlag, maxAlertTimes);

            ConfManager.init();

            FileMonitor.init();

            SecurityManager origSecurityManager = System.getSecurityManager();
            if (origSecurityManager != null) {
                logger.info("SecurityManager exists: {}", origSecurityManager);
            } else {
                logger.info("SecurityManager not exists");
            }

            // 创建SecurityManager
            System.setSecurityManager(new CheckSecMan(origSecurityManager));

            logger.info("after System.setSecurityManager");
        } catch (Exception e) {
            logger.error("error {} ", dftAlertFlag, e);
        } finally {
            logger.info("end initialize. spend: {}ms", System.currentTimeMillis() - startTime);
            initDone = true;
        }
    }

    // 设置一个空的SecurityManager
    public static void setEmptySecMan() {
        SecurityManager securityManager = new SecurityManager() {
            @Override
            public void checkPermission(Permission permission) {
            }

            @Override
            public void checkPermission(Permission permission, Object context) {
            }
        };

        System.setSecurityManager(securityManager);
    }

    public static boolean isInitDone() {
        return initDone;
    }

    private BehaviorControl() {
        throw new IllegalStateException("illegal");
    }
}
