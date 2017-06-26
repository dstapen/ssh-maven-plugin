package com.github.dstapen.sshmavenplugin;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.concurrent.TimeUnit;

import static com.github.dstapen.sshmavenplugin.AbstractSSHContainer.*;
import static org.junit.Assert.assertEquals;

public class SkipMojoTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void skipExecute() throws Exception {
        ExecuteCommandMojo sut = new ExecuteCommandMojo("localhost",
                SSH_HOST_PORT,
                SSH_USERNAME, true,
                SSH_PASSWORD, 1_000,
                true, null, true);
        sut.execute();
        assertEquals("[info] Skipping...\n", systemOutRule.getLog());
    }

    @Test
    public void skipUploadTest() throws Exception {
        TimeUnit.SECONDS.sleep(1L);

        UploadFileMojo sut = new UploadFileMojo("localhost",
                SSH_HOST_PORT, SSH_USERNAME,
                true, SSH_PASSWORD, 1_000,
                "local", "remote", true);
        sut.execute();
        assertEquals("[info] Skipping...\n", systemOutRule.getLog());

    }

    @Test
    public void skipDownloadTest() throws Exception {
        TimeUnit.SECONDS.sleep(1L);

        DownloadFileMojo sut = new DownloadFileMojo("localhost",
                SSH_HOST_PORT, SSH_USERNAME,
                true, SSH_PASSWORD, 1_000,
                "local", "remote", true);
        sut.execute();
        assertEquals("[info] Skipping...\n", systemOutRule.getLog());
    }
}