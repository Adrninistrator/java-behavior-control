package test.secman;

import com.github.adrninistrator.behavior_control.control.BehaviorControl;
import org.junit.Test;
import test.base.TestBase;

/**
 * @author easonzheng
 * @date 2020/6/10
 * @description:
 */

public class TestSecMan extends TestBase {

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            BehaviorControl.setEmptySecMan();
        }
    }
}
