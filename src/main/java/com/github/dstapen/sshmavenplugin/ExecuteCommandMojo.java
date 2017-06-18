package com.github.dstapen.sshmavenplugin;

import com.github.dstapen.sshmavenplugin.internal.ChannelTenant;
import com.github.dstapen.sshmavenplugin.internal.SessionTenant;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * TODO javadoc
 */
@Mojo(name = "execute", requiresProject = false)
@SuppressWarnings("unused")
public class ExecuteCommandMojo extends GenericMojoTemplate {
    private static final Logger LOG = getLogger(ExecuteCommandMojo.class);
    private static final int BUFFER_SIZE = 4096;
    private static final int NORMAL_EXIT_CODE = 0;
    private static final String COMMAND_PARAMETER = "command";
    private static final String FAILONERROR_PARAMETER = "failOnError";

    @Parameter(name = COMMAND_PARAMETER, required = true)
    String command;

    @Parameter(name = FAILONERROR_PARAMETER, defaultValue = "true")
    Boolean failOnError;

    @Override
    protected void perform(final SessionTenant sessionTenant) throws MojoExecutionException, MojoFailureException {
        try (final ChannelTenant<ChannelExec> channelTenant = sessionTenant.exec()) { // RAII
            final ChannelExec channel = channelTenant.channel();
            channel.setCommand(command);
            channel.setErrStream(System.err);
            channel.setOutputStream(System.out);
            LOG.info("{}", command);
            channel.connect();

            process(channel);

        } catch (JSchException | IOException e) {
            throw new ConnectivityProblemException("something goes wrong", e); // TODO
        }
    }

    private void process(final ChannelExec channel) throws MojoExecutionException, IOException {
        InputStream in = channel.getInputStream();

        byte[] tmp = new byte[BUFFER_SIZE];
        while (true) {
            while (in.available() > 0) {
                int used = in.read(tmp, 0, BUFFER_SIZE);
                if (used < 0) {
                    break;
                }
                System.out.print(new String(tmp, 0, used));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) {
                    continue;
                }
                int exitCode = channel.getExitStatus();
                LOG.info("Exit status {}", exitCode);
                if (NORMAL_EXIT_CODE != exitCode && failOnError) {
                    throw new MojoExecutionException(""); // TODO
                }
                break;
            }
            delay(300L, MILLISECONDS);
        }
    }

    private void delay(long timeout, TimeUnit unit) {
        try {
            unit.sleep(timeout);
        } catch (Exception e) {
            LOG.warn("something goes wrong: {}", e.getMessage(), e); // TODO
        }
    }
}
