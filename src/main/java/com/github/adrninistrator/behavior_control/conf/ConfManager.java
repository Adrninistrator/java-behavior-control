package com.github.adrninistrator.behavior_control.conf;

import com.github.adrninistrator.behavior_control.constants.BCConstants;
import com.github.adrninistrator.behavior_control.enums.BehaviorEnum;
import com.github.adrninistrator.behavior_control.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author easonzheng
 * @date 2020/6/13
 * @description:
 */

public class ConfManager {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    public static void init() {
        for (BehaviorEnum behaviorEnum : BCConstants.INIT_CONF_ENUMS) {
            initConf(behaviorEnum);
        }
    }

    public static void initConf(BehaviorEnum behaviorEnum) {
        Set<String> confSet = getConfSet(AppConfStore.getConfPath() + behaviorEnum.getConfFileName());

        setConfSet(behaviorEnum, confSet);
    }

    public static void updateConf(String operate, File file) {
        String fileName = file.getName();

        BehaviorEnum behaviorEnum = BehaviorEnum.getFromConfFileName(fileName);
        if (behaviorEnum == BehaviorEnum.BEHV_UNDEFINED) {
            if (logger.isDebugEnabled()) {
                logger.debug("未定义的类型，不需要处理 {} {}", operate, file.getAbsolutePath());
            }
            return;
        }

        logger.info("updateConf: {} {} {}", operate, fileName, file.getAbsolutePath());

        Set<String> confSet = FileUtil.getFile2Set(file);

        setConfSet(behaviorEnum, confSet);
    }

    private static Set<String> getConfSet(String confFilePath) {
        File confFile = new File(confFilePath);
        if (!confFile.exists()) {
            logger.info("file not exists: {}", confFilePath);
            return new HashSet<>(0);
        }

        return FileUtil.getFile2Set(confFilePath);
    }

    private static void setConfSet(BehaviorEnum behaviorEnum, Set<String> confSet) {
        logger.info("setConfSet: {} confSet.size: {}", behaviorEnum, confSet.size());

        switch (behaviorEnum) {
            case BEHV_EXEC:
                ControlConfStore.setExecSet(confSet);
                break;
            case BEHV_LISTEN:
                ControlConfStore.setListenSet(confSet);
                break;
            case BEHV_ACCEPT:
                ControlConfStore.setAcceptSet(confSet);
                break;
            case BEHV_CONNECT:
                ControlConfStore.setConnectSet(confSet);
                break;
            default:
                logger.error("unknown: {}", behaviorEnum);
                break;
        }
    }

    private ConfManager() {
        throw new IllegalStateException("illegal");
    }
}
