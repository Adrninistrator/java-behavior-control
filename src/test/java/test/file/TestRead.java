package test.file;

import com.github.adrninistrator.behavior_control.conf.ConfManager;
import com.github.adrninistrator.behavior_control.enums.BehaviorEnum;
import org.junit.Test;
import test.common.TestCommon;

import java.io.File;

public class TestRead {

//    @Test
    public void test() throws InterruptedException {
        Thread.sleep(10 * 1000L);

        BehaviorEnum[] INIT_CONF_ENUMS = new BehaviorEnum[]{
                BehaviorEnum.BEHV_EXEC, BehaviorEnum.BEHV_LISTEN,
                BehaviorEnum.BEHV_ACCEPT, BehaviorEnum.BEHV_CONNECT};

        for (int i = 0; i < 20000; i++) {
            for (BehaviorEnum behaviorEnum : INIT_CONF_ENUMS) {
                ConfManager.updateConf("test", new File(TestCommon.CONF_PATH + File.separator + behaviorEnum.getConfFileName()));
            }
        }

        Thread.sleep(2 * 60 * 1000L);
    }
}
