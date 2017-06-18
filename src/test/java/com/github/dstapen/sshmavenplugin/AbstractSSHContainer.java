package com.github.dstapen.sshmavenplugin;

import org.junit.Rule;
import org.testcontainers.containers.FixedHostPortGenericContainer;

public abstract class AbstractSSHContainer {

    @Rule
    public FixedHostPortGenericContainer ct =
            new FixedHostPortGenericContainer("sickp/alpine-sshd:7.5")
                    .withFixedExposedPort(2222, 22);

}
