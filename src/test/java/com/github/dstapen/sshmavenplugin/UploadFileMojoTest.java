package com.github.dstapen.sshmavenplugin;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static com.github.dstapen.sshmavenplugin.FilePathCreating.pathToSmallSample;


public class UploadFileMojoTest extends AbstractSSHContainer {


    @Test
    public void testUploadFileMojo() throws Exception {
        TimeUnit.SECONDS.sleep(1L);

        UploadFileMojo sut = new UploadFileMojo("localhost", 2222, "root", true,
                "root", 1_000,
                pathToSmallSample(),
                "little");

        sut.execute();
    }

}