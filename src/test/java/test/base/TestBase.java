package test.base;

import com.github.adrninistrator.behavior_control.control.BehaviorControl;
import com.github.adrninistrator.behavior_control.handler.DefaultAlertBehaviorHandler;
import org.junit.AfterClass;
import org.junit.Before;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.common.TestCommon;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    @Before
    public void initTestBase() throws InterruptedException {
        BehaviorControl.register(TestCommon.CONF_PATH, new DefaultAlertBehaviorHandler(), 1000L, "alert", 3);

        while (!BehaviorControl.isInitDone()) {
            logger.info("sleep...");
            Thread.sleep(10L);
        }
    }

    @AfterClass
    public static void afterClassTestBase() {
        Whitebox.setInternalState(BehaviorControl.class, "inited", new AtomicBoolean(false));
        Whitebox.setInternalState(BehaviorControl.class, "initDone", false);

        System.setSecurityManager(null);
    }

    protected void runMulti(int poolSize, int queueSize, int runTimes) {
        ThreadPoolExecutor threadPoolExecutor = genThreadPoolExecutor(poolSize, queueSize);
        for (int i = 0; i < runTimes; i++) {
            threadPoolExecutor.submit(() -> {
                operate();
            });
        }

        while (threadPoolExecutor.getActiveCount() > 0) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected ThreadPoolExecutor genThreadPoolExecutor(int poolSize, int queueSize) {
        return new ThreadPoolExecutor(poolSize, poolSize, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueSize),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    protected void operate() {
    }
}
