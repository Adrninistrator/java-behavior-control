package com.github.adrninistrator.behavior_control.control;

import com.github.adrninistrator.behavior_control.conf.AppConfStore;
import com.github.adrninistrator.behavior_control.conf.ControlConfStore;
import com.github.adrninistrator.behavior_control.handler.BehaviorHandlerInterface;

/**
 * @author easonzheng
 * @date 2020/6/11
 * @description:
 */

public class ExecControl {

    // 处理Runtime.getRuntime().exec()
    public static void checkExec(String cmd) {
        // 判断是否允许执行，exec配置文件格式为：[cmd]，即被执行程序的相对路径或绝对路径
        if (ControlConfStore.checkExec(cmd)) {
            return;
        }

        // 对发现的异常行为进行处理
        BehaviorHandlerInterface handler = AppConfStore.getHandler();
        handler.handleExec(cmd);
    }

    private ExecControl() {
        throw new IllegalStateException("illegal");
    }
}
