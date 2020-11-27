package com.github.adrninistrator.behavior_control.util;

import com.github.adrninistrator.behavior_control.constants.BCConstants;
import com.github.adrninistrator.behavior_control.secman.CheckSecMan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author easonzheng
 * @date 2020/6/11
 * @description:
 */

public class BCCommUtil {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    // 打印堆栈，找到CheckSecMan类后，再开始记录日志，堆栈作为一行进行打印
    public static void printStack(String behavior) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements == null) {
            logger.info("stackTrace is null");
            return;
        }

        boolean startLog = false;
        StringBuilder stringBuilder = new StringBuilder();

        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (!startLog) {
                if (logger.isDebugEnabled()) {
                    logger.debug("not log {}", stackTraceElement);
                }

                if (CheckSecMan.class.getName().equals(stackTraceElement.getClassName())) {
                    startLog = true;
                }
                continue;
            }

            stringBuilder.append("\r\n").append(stackTraceElement.toString());
        }

        logger.error("{} {}", behavior, stringBuilder);
    }

    private BCCommUtil() {
        throw new IllegalStateException("illegal");
    }
}
