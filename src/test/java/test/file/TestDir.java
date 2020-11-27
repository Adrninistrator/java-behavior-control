package test.file;

import com.github.adrninistrator.behavior_control.util.FileUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestDir {
    @Test
    public void test() throws IOException {
        String path1 = "build/dir1";
        File dir1 = new File(path1);
        dir1.mkdirs();

        assertTrue(FileUtil.isDirectoryExists(path1));

        String path2 = "build/dir2";
        assertTrue(FileUtil.isDirectoryExists(path2));

        String path3 = "build/file1";
        File file1 = new File(path3);
        file1.createNewFile();

        assertFalse(FileUtil.isDirectoryExists(path3));
    }
}
