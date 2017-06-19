package com.github.dstapen.sshmavenplugin;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.github.dstapen.sshmavenplugin.FilePathCreating.pathToSmallSample;

public class DownloadFileMojoTest extends AbstractSSHContainer {

    @Before
    public void setUp() throws Exception {
        TimeUnit.SECONDS.sleep(1L);


        UploadFileMojo sut = new UploadFileMojo("localhost", 2222, "root", true,
                "root", 1_000,
                pathToSmallSample(),
                "little", false);
        sut.execute();
    }

    @Test
    public void testDownloadFileMojo() throws Exception {
        File tempFile = File.createTempFile("downloaded_little", ".tmp");
        tempFile.deleteOnExit();
        DownloadFileMojo sut = new DownloadFileMojo("localhost", 2222, "root", true,
                "root", 1000, "little", tempFile.getAbsolutePath(), false);
        sut.execute();
    }

}