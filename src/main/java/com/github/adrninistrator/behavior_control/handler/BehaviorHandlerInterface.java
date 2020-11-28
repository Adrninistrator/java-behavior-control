package com.github.adrninistrator.behavior_control.handler;

public interface BehaviorHandlerInterface {

    // 以下方法中若抛出SecurityException异常，可以阻断对应行为

    /**
     * 监控到创建进程行为时进行处理
     *
     * @param cmd 创建进程时执行的完整命令
     */
    void handleExec(String cmd);

    /**
     * 监控到监听端口行为时进行处理
     *
     * @param port    监听的端口号，int格式
     * @param strPort 监听的端口号，String格式
     */
    void handleListen(int port, String strPort);

    /**
     * 监控到接受Socket连接行为时进行处理
     *
     * @param host 客户端IP
     */
    void handleAccept(String host);

    /**
     * 监控到建立Socket连接行为时进行处理
     *
     * @param host      服务器IP
     * @param port      服务器端口
     * @param ipAndPort [服务器IP]:[服务器端口]格式的字符串
     */
    void handleConnect(String host, int port, String ipAndPort);

    /**
     * 监控到设置安全管理器行为时进行处理
     */
    void handleSetSecurityManager();
}
