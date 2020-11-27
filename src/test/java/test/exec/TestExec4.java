package test.exec;

import com.github.adrninistrator.behavior_control.control.BehaviorControl;
import com.github.adrninistrator.behavior_control.handler.DefaultAlertBehaviorHandler;
import org.junit.Test;
import test.base.TestBase;
import test.common.TestCommon;

import java.io.IOException;

/**
 * @author easonzheng
 * @date 2020/6/10
 * @description:
 */

public class TestExec4 extends TestBase {

    @Test
    public void test() throws IOException, InterruptedException {
        BehaviorControl.register(TestCommon.CONF_PATH, new DefaultAlertBehaviorHandler(), 60 * 1000L, "alert", 0);

        while (!BehaviorControl.isInitDone()) {
            Thread.sleep(10L);
        }

        Runtime.getRuntime().exec(TestCommon.EXE_CMD);
    }
}
