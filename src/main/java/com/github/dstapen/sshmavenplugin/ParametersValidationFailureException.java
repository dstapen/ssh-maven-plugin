package com.github.dstapen.sshmavenplugin;

import org.apache.maven.plugin.MojoFailureException;

/**
 * TODO javadoc
 *
 * @see MojoFailureException
 */
public class ParametersValidationFailureException extends MojoFailureException {
    public ParametersValidationFailureException(Object source, String shortMessage, String longMessage) {
        super(source, shortMessage, longMessage);
    }

    public ParametersValidationFailureException(String message) {
        super(message);
    }

    public ParametersValidationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
