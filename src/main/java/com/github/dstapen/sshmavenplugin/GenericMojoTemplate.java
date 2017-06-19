package com.github.dstapen.sshmavenplugin;

import com.github.dstapen.sshmavenplugin.internal.SessionTenant;
import com.jcraft.jsch.JSchException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.impl.StaticLoggerBinder;

import static com.github.dstapen.sshmavenplugin.internal.EnvUtils.findSystemProperty;
import static com.github.dstapen.sshmavenplugin.internal.SessionTenant.connect;
import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * TODO javadoc
 * <p/>
 * TODO abstract regarding template method pattern
 *
 * @see AbstractMojo
 */
abstract class GenericMojoTemplate extends AbstractMojo {
    private static final Logger LOG = getLogger(GenericMojoTemplate.class);
    private static final String HOST_PARAMETER = "host";
    private static final String PORT_PARAMETER = "port";
    private static final String USER_PARAMETER = "user";
    private static final String TRUST_PARAMETER = "trust";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String TIMEOUT_PARAMETER = "timeout";
    private static final String SKIP_PARAMETER = "skip";


    /**
     * Use <code>-Dssh.skip=true</code> to skip all Mojo execution
     */
    @Parameter(name = SKIP_PARAMETER, defaultValue = "false", alias = "ssh.skip")
    boolean skip;

    @Parameter(name = HOST_PARAMETER)
    String host;

    @Parameter(name = PORT_PARAMETER, defaultValue = "22")
    Integer port;

    @Parameter(name = USER_PARAMETER)
    String user;

    @Parameter(name = TRUST_PARAMETER, defaultValue = "false")
    Boolean trust;

    @Parameter(name = PASSWORD_PARAMETER, defaultValue = "")
    String password;

    @Parameter(name = TIMEOUT_PARAMETER, defaultValue = "1000")
    Integer timeout;


    public GenericMojoTemplate() { // for the mojo
    }

    GenericMojoTemplate(String host, Integer port, String user, Boolean trust,
                        String password, Integer timeout, boolean skip) { // for testing reasons
        this.host = host;
        this.port = port;
        this.user = user;
        this.trust = trust;
        this.password = password;
        this.timeout = timeout;
        this.skip = skip;
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {

        // enable SLF4j logging
        StaticLoggerBinder.getSingleton().setMavenLog(this.getLog());

        if (!skip) {
            validateParameters();
            try (SessionTenant sessionTenant = connect(host(), port(), user(), password(), trust())) {
                perform(sessionTenant);
            } catch (JSchException e) {
                throw new ConnectivityProblemException(e.getMessage(), e);
            }
        } else {
            LOG.info("Skipping...");
        }
    }

    protected abstract void perform(SessionTenant sessionTenant) throws MojoExecutionException, MojoFailureException;

    protected final String host() {
        return findSystemProperty(HOST_PARAMETER).orElse(host);
    }

    protected final int port() {
        return findSystemProperty(HOST_PARAMETER).map(Integer::parseInt).orElse(port); // TODO handle NumberFormatException
    }

    protected final String user() {
        return findSystemProperty(USER_PARAMETER).orElse(user);
    }

    protected final boolean trust() {
        return findSystemProperty(TRUST_PARAMETER).map(Boolean::parseBoolean).orElse(trust);
    }

    protected final String password() {
        return findSystemProperty(PASSWORD_PARAMETER).orElse(password);
    }

    protected final int timeout() {
        return findSystemProperty(TIMEOUT_PARAMETER).map(Integer::parseInt).orElse(timeout); // TODO handle NumberFormatException
    }

    protected void validateParameters() throws ParametersValidationFailureException {
        validateHost();
        validatePort();
        validateUser();
        validateTrust();
        validatePassword();
        validateTimeout();
    }

    private void validateHost() throws ParametersValidationFailureException {
        ofNullable(host()).orElseThrow(() -> new ParametersValidationFailureException("host parameter must not be empty"));
    }

    private void validatePort() throws ParametersValidationFailureException {
        int actualPortValue = port();
        if (actualPortValue < 1) {
            throw new ParametersValidationFailureException(String.format("port must be positive, but: %d", actualPortValue));
        }
    }

    private void validateUser() throws ParametersValidationFailureException {
        ofNullable(user()).orElseThrow(() -> new ParametersValidationFailureException("user must be not empty"));
    }

    private void validateTrust() throws ParametersValidationFailureException {
        // TODO do something
    }

    private void validatePassword() throws ParametersValidationFailureException {
        ofNullable(password()).orElseThrow(() -> new ParametersValidationFailureException("password must not be empty"));
    }

    private void validateTimeout() throws ParametersValidationFailureException {
        int actualValue = timeout();
        if (actualValue < 1) {
            throw new ParametersValidationFailureException(String.format("timeout must be positive, but: %d", actualValue));
        }
    }


}
