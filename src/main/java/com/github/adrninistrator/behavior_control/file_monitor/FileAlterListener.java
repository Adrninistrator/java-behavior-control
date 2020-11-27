package com.github.adrninistrator.behavior_control.file_monitor;

import com.github.adrninistrator.behavior_control.conf.ConfManager;
import com.github.adrninistrator.behavior_control.constants.BCConstants;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author easonzheng
 * @date 2020/6/13
 * @description:
 */

public class FileAlterListener extends FileAlterationListenerAdaptor {

    private static final Logger logger = LoggerFactory.getLogger(BCConstants.BEHV_LOG_NAME);

    @Override
    public void onFileCreate(final File file) {
        ConfManager.updateConf("onFileCreate", file);
    }

    @Override
    public void onFileChange(final File file) {
        ConfManager.updateConf("onFileChange", file);
    }
}
