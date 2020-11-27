package test.file;

import com.github.adrninistrator.behavior_control.util.FileUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.common.TestCommon;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

public class TestFileBig {

    private static final Logger logger = LoggerFactory.getLogger(TestFileBig.class);

    @Test
    public void test() {
        Set<String> set = FileUtil.getFile2Set(TestCommon.CONF_PATH + File.separator + "test_big.txt");
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            logger.info("{}", iterator.next());
        }
    }
}
