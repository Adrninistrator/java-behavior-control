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

public class TestExec1 extends TestBase {

    @Test
    public void test() throws IOException {
        Runtime.getRuntime().exec(TestCommon.EXE_CMD);
    }
}
