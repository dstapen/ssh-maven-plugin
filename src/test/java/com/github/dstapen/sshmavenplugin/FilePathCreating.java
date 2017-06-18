package com.github.dstapen.sshmavenplugin;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

class FilePathCreating {

    public static String pathToBigSample() throws Exception {
        return pathToSample("/samples/big.txt");
    }

    public static String pathToSmallSample() throws Exception {
        return pathToSample("/samples/small.txt");
    }

    private static String pathToSample(String fullQualifiedResource) throws Exception {
        InputStream is = FilePathCreating.class.getResourceAsStream(fullQualifiedResource);
        File temp = File.createTempFile("test", ".tmp");
        temp.deleteOnExit();
        Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return temp.getAbsolutePath();
    }

}
