package com.github.dstapen.sshmavenplugin;

import org.junit.Rule;
import org.testcontainers.containers.FixedHostPortGenericContainer;

public abstract class AbstractSSHContainer {
    public static final int SSH_HOST_PORT = 2222;
    public static final String SSH_USERNAME = "root";
    public static final String SSH_PASSWORD = "root";



    @Rule
    public FixedHostPortGenericContainer ct =
            new FixedHostPortGenericContainer("sickp/alpine-sshd:7.5")
                    .withFixedExposedPort(SSH_HOST_PORT, 22);

}
