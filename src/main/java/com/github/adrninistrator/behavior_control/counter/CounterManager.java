package com.github.adrninistrator.behavior_control.counter;

import com.github.adrninistrator.behavior_control.conf.AppConfStore;
import com.github.adrninistrator.behavior_control.constants.BCConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author easonzheng
 * @date 2020/6/13
 * @description:
 */

public class CounterManager {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    // 记录未知的创建进程行为，key为被执行程序的相对路径或绝对路径，value为发生的次数
    private static Map<String, AtomicInteger> execMap = new ConcurrentHashMap<>(16);

    // 记录未知的监听端口行为，key为监听的端口，value为发生的次数
    private static Map<String, AtomicInteger> listenMap = new ConcurrentHashMap<>(16);

    // 记录未知的接受Socket连接行为，key为连接对应的客户端IP，value为发生的次数
    private static Map<String, AtomicInteger> acceptMap = new ConcurrentHashMap<>(16);

    // 记录未知的接受Socket连接行为，key为连接对应的服务器IP与端口，value为发生的次数
    private static Map<String, AtomicInteger> connectMap = new ConcurrentHashMap<>(16);

    // 记录System.setSecurityManager行为的次数
    private static AtomicInteger securityManagerInt = new AtomicInteger(0);

    public static boolean allowExecAlert(String key) {
        return checkAllowAlert(execMap, key);
    }

    public static boolean allowListenAlert(String key) {
        return checkAllowAlert(listenMap, key);
    }

    public static boolean allowAcceptAlert(String key) {
        return checkAllowAlert(acceptMap, key);
    }

    public static boolean allowConnectAlert(String key) {
        return checkAllowAlert(connectMap, key);
    }

    public static boolean allowSetSecManAlert() {
        return checkAllow(securityManagerInt.incrementAndGet(), "System.setSecurityManager");
    }

    /**
     * 判断指定的key对应的未知行为是否还允许告警
     *
     * @param map 需要判断的未知行为对应的map
     * @param key 需要判断的未知行为对应的key
     * @return true：允许告警，false：不允许告警
     */
    private static boolean checkAllowAlert(Map<String, AtomicInteger> map, String key) {
        AtomicInteger atomicInteger = map.get(key);
        if (atomicInteger != null) {
            // map中AtomicInteger已存在
            if (atomicInteger.get() > AppConfStore.getMaxAlertTimes()) {
                // 次数已超过
                return false;
            }

            return checkAllow(atomicInteger.incrementAndGet(), key);
        }

        // map中AtomicInteger未存在
        AtomicInteger atomicInteger4Put = new AtomicInteger();
        AtomicInteger atomicIntegerRtn = map.putIfAbsent(key, atomicInteger4Put);
        if (atomicIntegerRtn == null) {
            // put成功，使用创建的AtomicInteger
            return checkAllow(atomicInteger4Put.incrementAndGet(), key);
        }

        // put未成功，使用返回的AtomicInteger
        return checkAllow(atomicIntegerRtn.incrementAndGet(), key);
    }

    /**
     * @param count key对应的告警次数加1后的值
     * @param key   未知行为
     * @return true：允许告警，false：不允许告警
     */
    private static boolean checkAllow(int count, String key) {
        if (count > AppConfStore.getMaxAlertTimes()) {
            // 次数已超过
            return false;
        }

        //次数未超过
        logger.info("allow alert {} {}", key, count);
        return true;
    }

    private CounterManager() {
        throw new IllegalStateException("illegal");
    }
}
