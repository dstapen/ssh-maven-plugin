package com.github.dstapen.sshmavenplugin;

import org.junit.Before;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;


public abstract class ParametrizedTransferTest extends AbstractSSHContainer {

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new String[][]{
                {"/samples/big.txt", "big_one.txt"},
                {"/samples/small.txt", "small_one.txt"}
        });
    }

    @Parameter(0)
    public String fullQualifiedResource;

    protected String local;

    @Parameter(1)
    public String remote;


    @Before
    public void initLocal() throws Exception {
        local = pathToSample(fullQualifiedResource);
    }

    private static String pathToSample(String fullQualifiedResource) throws Exception {
        InputStream is = ParametrizedTransferTest.class.getResourceAsStream(fullQualifiedResource);
        File temp = File.createTempFile("test", ".tmp");
        temp.deleteOnExit();
        Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return temp.getAbsolutePath();
    }

}
