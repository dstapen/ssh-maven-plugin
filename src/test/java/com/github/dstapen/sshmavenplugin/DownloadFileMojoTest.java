package com.github.dstapen.sshmavenplugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class DownloadFileMojoTest extends ParametrizedTransferTest {

    @Test
    public void testDownloadFileMojo() throws Exception {
        TimeUnit.SECONDS.sleep(1L);

        UploadFileMojo arrangement = new UploadFileMojo("localhost", SSH_HOST_PORT, SSH_USERNAME, true,
                SSH_PASSWORD, 1_000,
                local,
                remote, false);
        arrangement.execute();

        File tempFile = File.createTempFile("downloaded_little", ".tmp");
        tempFile.deleteOnExit();
        DownloadFileMojo sut = new DownloadFileMojo("localhost", SSH_HOST_PORT, SSH_USERNAME, true,
                SSH_PASSWORD, 1000, remote, tempFile.getAbsolutePath(), false);
        sut.execute();
    }

}