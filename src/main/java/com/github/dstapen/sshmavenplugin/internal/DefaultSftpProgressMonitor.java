package com.github.dstapen.sshmavenplugin.internal;

import com.jcraft.jsch.SftpProgressMonitor;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * TODO javadoc and better progress monitor
 */
public class DefaultSftpProgressMonitor implements SftpProgressMonitor {
    private static final Logger LOG = getLogger(DefaultSftpProgressMonitor.class);

    @Override
    public void init(int op, String src, String dest, long max) {
        LOG.debug("init ( {}, {}, {}, {} )", op, src, dest, max);
    }

    @Override
    public boolean count(long count) {
        LOG.debug("count ( {} )", count);
        return false;
    }

    @Override
    public void end() {
        LOG.debug("end");
    }
}
