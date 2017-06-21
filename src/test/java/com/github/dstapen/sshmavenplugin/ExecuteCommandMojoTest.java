package com.github.dstapen.sshmavenplugin;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ExecuteCommandMojoTest extends AbstractSSHContainer {

    @Test
    public void test() throws Exception {
        TimeUnit.SECONDS.sleep(1L);
        ExecuteCommandMojo sut = ExecuteCommandMojo.class.newInstance();
        sut.host = "localhost";
        sut.port = SSH_HOST_PORT;
        sut.trust = true;
        sut.timeout = 1000;
        sut.user = SSH_USERNAME;
        sut.password = SSH_PASSWORD;
        sut.command = "ls";
        sut.execute();
    }

}