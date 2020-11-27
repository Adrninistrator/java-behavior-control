package com.github.adrninistrator.behavior_control.handler;

public interface BehaviorHandlerInterface {

    void handleExec(String cmd);

    void handleListen(int port, String strPort);

    void handleAccept(String host);

    void handleConnect(String host, int port, String ipAndPort);

    void handleSetSecurityManager();
}
