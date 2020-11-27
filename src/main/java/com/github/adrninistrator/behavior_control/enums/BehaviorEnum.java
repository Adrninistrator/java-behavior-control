package com.github.adrninistrator.behavior_control.enums;

public enum BehaviorEnum {
    BEHV_EXEC("exec.conf", "behv_alert_exec", "创建进程"),
    BEHV_LISTEN("listen.conf", "behv_alert_listen", "监听端口"),
    BEHV_ACCEPT("accept.conf", "behv_alert_accept", "接受Socket连接"),
    BEHV_CONNECT("connect.conf", "behv_alert_connect", "建立Socket连接"),
    BEHV_SETSECMAN("", "behv_alert_setsecman", "设置安全管理器"),
    BEHV_UNDEFINED("", "", "未定义"),
    ;

    // 配置文件名
    private String confFileName;

    // 告警关键字
    private String alertFlag;

    private String desc;

    BehaviorEnum(String confFileName, String alertFlag, String desc) {
        this.confFileName = confFileName;
        this.alertFlag = alertFlag;
        this.desc = desc;
    }

    public static BehaviorEnum getFromConfFileName(String confFileName) {
        for (BehaviorEnum behaviorEnum : BehaviorEnum.values()) {
            if (behaviorEnum.confFileName.equals(confFileName)) {
                return behaviorEnum;
            }
        }

        return BEHV_UNDEFINED;
    }

    public String getConfFileName() {
        return confFileName;
    }

    public String getAlertFlag() {
        return alertFlag;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return name() + "-" + confFileName + "-" + desc;
    }
}
