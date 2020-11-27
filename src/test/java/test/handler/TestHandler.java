package test.handler;

import com.github.adrninistrator.behavior_control.control.BehaviorControl;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.common.TestCommon;

import static org.junit.Assert.assertThrows;

public class TestHandler {

    private static final Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @Before
    public void init() throws InterruptedException {
        BehaviorControl.register(TestCommon.CONF_PATH, new TestExptBehaviorHandler(), 1000L, "alert", 3);

        while (!BehaviorControl.isInitDone()) {
            logger.info("sleep...");
            Thread.sleep(10L);
        }
    }

    @Test
    public void test() {
        assertThrows(SecurityException.class, () ->
                Runtime.getRuntime().exec(TestCommon.EXE_CMD)
        );
    }

    @AfterClass
    public static void afterClass() {
        System.setSecurityManager(null);
    }
}
