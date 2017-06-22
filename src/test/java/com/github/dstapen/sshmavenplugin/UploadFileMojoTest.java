package com.github.dstapen.sshmavenplugin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class UploadFileMojoTest extends ParametrizedTransferTest {

    @Test
    public void testUploadFileMojo() throws Exception {
        TimeUnit.SECONDS.sleep(1L);

        UploadFileMojo sut = new UploadFileMojo("localhost", SSH_HOST_PORT, SSH_USERNAME, true,
                SSH_PASSWORD, 1_000,
                local,
                remote, false);

        sut.execute();
    }

}