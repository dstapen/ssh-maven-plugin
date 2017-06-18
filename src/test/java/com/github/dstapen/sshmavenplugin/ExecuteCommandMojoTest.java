package com.github.dstapen.sshmavenplugin;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ExecuteCommandMojoTest extends AbstractSSHContainer {

    @Test
    public void test() throws Exception {
        TimeUnit.SECONDS.sleep(1L);
        ExecuteCommandMojo sut = ExecuteCommandMojo.class.newInstance();
        sut.host = "localhost";
        sut.port = 2222;
        sut.trust = true;
        sut.timeout = 1000;
        sut.user = "root";
        sut.password = "root";
        sut.command = "ls";
        sut.execute();
    }

}