package test.exec;

import org.junit.Test;
import test.base.TestBase;
import test.common.TestCommon;

import java.io.IOException;

/**
 * @author easonzheng
 * @date 2020/6/10
 * @description:
 */

public class TestExec3 extends TestBase {

    @Test
    public void test() {
        runMulti(10, 20, 20);
    }

    @Override
    protected void operate() {
        try {
            Runtime.getRuntime().exec(TestCommon.EXE_CMD);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
