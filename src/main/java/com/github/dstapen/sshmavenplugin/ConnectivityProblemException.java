package com.github.dstapen.sshmavenplugin;

import org.apache.maven.plugin.MojoFailureException;

/**
 * TODO javadoc
 *
 * @see MojoFailureException
 */

public class ConnectivityProblemException extends MojoFailureException {
    public ConnectivityProblemException(Object source, String shortMessage, String longMessage) {
        super(source, shortMessage, longMessage);
    }

    public ConnectivityProblemException(String message) {
        super(message);
    }

    public ConnectivityProblemException(String message, Throwable cause) {
        super(message, cause);
    }
}
