package com.github.adrninistrator.behavior_control.constants;

import com.github.adrninistrator.behavior_control.enums.BehaviorEnum;

/**
 * @author easonzheng
 * @date 2020/6/11
 * @description:
 */

public class BCConstants {

    // 日志名称
    public static final String BEHV_LOG_NAME = "behaviorControl";

    public static final String BEHV_LOG_FLAG = "###";

    // IP与端口分隔符
    public static final String IP_PORT_FLAG = ":";

    // 监控配置文件修改时间间隔，单位毫秒，默认值
    public static final long DFT_MONITOR_INTERVAL = 30 * 1000L;

    // 某项内容告警最大次数，默认值
    public static final String DFT_ALERT_FLAG = "DftErrorFlag";

    // 某项未知行为告警最大次数，默认值
    public static final int DFT_MAX_ALERT_TIMES = 3;

    // 需要初始化的白名单配置
    public static final BehaviorEnum[] INIT_CONF_ENUMS = new BehaviorEnum[]{
            BehaviorEnum.BEHV_EXEC, BehaviorEnum.BEHV_LISTEN,
            BehaviorEnum.BEHV_ACCEPT, BehaviorEnum.BEHV_CONNECT};

    private BCConstants() {
        throw new IllegalStateException("illegal");
    }
}
