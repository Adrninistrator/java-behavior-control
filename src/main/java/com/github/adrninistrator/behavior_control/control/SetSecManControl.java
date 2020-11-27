package com.github.adrninistrator.behavior_control.control;

import com.github.adrninistrator.behavior_control.conf.AppConfStore;
import com.github.adrninistrator.behavior_control.handler.BehaviorHandlerInterface;

import java.security.Permission;

/**
 * @author easonzheng
 * @date 2020/6/11
 * @description:
 */

public class SetSecManControl {

    public static void checkSetSecMan(Permission permission) {
        String name = permission.getName();
        if (name == null || !name.contains("setSecurityManager")) {
            return;
        }

        // 对发现的异常行为进行处理
        BehaviorHandlerInterface handler = AppConfStore.getHandler();
        handler.handleSetSecurityManager();
    }

    private SetSecManControl() {
        throw new IllegalStateException("illegal");
    }
}
