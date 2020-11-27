package com.github.adrninistrator.behavior_control.util;

import com.github.adrninistrator.behavior_control.conf.AppConfStore;
import com.github.adrninistrator.behavior_control.constants.BCConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author easonzheng
 * @date 2020/6/13
 * @description:
 */

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    // 获取文件行数，不判断文件是否存在
    public static int getFileLineNum(File filePath) {
        int lineNum = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            while (br.readLine() != null) {
                lineNum++;
            }

            return lineNum;
        } catch (Exception e) {
            logger.error("error {} ", AppConfStore.getDftAlertFlag(), e);
            return 0;
        }
    }

    // 读取文件内容，读取到Set中
    public static Set<String> getFile2Set(String filePath) {
        return getFile2Set(new File(filePath));
    }

    // 读取文件内容，读取到Set中
    public static Set<String> getFile2Set(File file) {
        int lineNum = getFileLineNum(file);
        if (lineNum == 0) {
            logger.info("set is empty: {}", file.getAbsolutePath());
            return new HashSet<>(0);
        }

        int initSize = (int) (lineNum / 0.75D) + 1;
        Set<String> contentSet = new HashSet<>(initSize);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String realLine = line.trim();
                if (realLine.isEmpty()) {
                    continue;
                }
                contentSet.add(realLine);
            }

            logger.info("lineNum: {} set size: {} {}", lineNum, contentSet.size(), file);
            return contentSet;
        } catch (Exception e) {
            logger.error("error {} ", AppConfStore.getDftAlertFlag(), e);
            return new HashSet<>(0);
        }
    }

    /**
     * 判断目录是否存在
     *
     * @param dirPath 需要判断的目录路径
     * @return true：指定路径的目录存在（已存在或新创建），false：目录不存在（指定路径为文件，或创建失败）
     */
    public static boolean isDirectoryExists(String dirPath) {
        File file = new File(dirPath);
        if (file.exists()) {
            if (file.isDirectory()) {
                logger.info("directory exists: {}", dirPath);
                return true;
            }

            logger.error("file exists: {}", dirPath);
            return false;
        }

        // 目录不存在，则尝试创建
        if (file.mkdirs()) {
            logger.info("mkdirs: {}", dirPath);
            return true;
        }

        logger.error("mkdirs fail: {}", dirPath);
        return false;
    }

    // 当文件不存在时，尝试创建
    public static void createNotExistsFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }

        try {
            boolean success = file.createNewFile();
            logger.info("file not exists, create {} {}", (success ? "success" : "fail"), file.getAbsolutePath());
        } catch (Exception e) {
            logger.error("error {} ", AppConfStore.getDftAlertFlag(), e);
        }
    }

    private FileUtil() {
        throw new IllegalStateException("illegal");
    }
}

