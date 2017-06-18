package com.github.dstapen.sshmavenplugin.internal;

import javax.annotation.Nonnull;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public final class EnvUtils {

    private EnvUtils() {
    }

    @Nonnull
    public static Optional<String> findSystemProperty(String key) {
        return ofNullable(System.getProperty(key));
    }


}
