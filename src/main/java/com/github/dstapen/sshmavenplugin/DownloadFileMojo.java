package com.github.dstapen.sshmavenplugin;

import com.github.dstapen.sshmavenplugin.internal.ChannelTenant;
import com.github.dstapen.sshmavenplugin.internal.DefaultSftpProgressMonitor;
import com.github.dstapen.sshmavenplugin.internal.SessionTenant;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * TODO javadoc
 * <p/>
 * example
 *
 * @see GenericMojoTemplate
 */
@Mojo(name = "download", requiresProject = false)
@SuppressWarnings("unused")
public class DownloadFileMojo extends GenericMojoTemplate {
    private static final Logger LOG = getLogger(DownloadFileMojo.class);

    /**
     * remote file location
     */
    @Parameter(name = "from", required = true)
    private String from; // remote

    /**
     * local file location
     */
    @Parameter(name = "to", required = true)
    private String to;

    public DownloadFileMojo() { // for the mojo
    }

    DownloadFileMojo(String host, Integer port, String user, Boolean trust,
                     String password, Integer timeout, String from, String to, boolean skip) { // for testing purpose
        super(host, port, user, trust, password, timeout, skip);
        this.from = from;
        this.to = to;
    }

    @Override
    protected void perform(final SessionTenant sessionTenant) throws MojoExecutionException, MojoFailureException {
        LOG.info("downloading {} from {}", to, from);
        try (final ChannelTenant<ChannelSftp> channelTenant = sessionTenant.sftp()) {
            ChannelSftp sftp = channelTenant.channel();
            sftp.connect();
            sftp.get(from, to, new DefaultSftpProgressMonitor(), ChannelSftp.OVERWRITE);
        } catch (JSchException | SftpException e) {
            throw new ConnectivityProblemException("something goes wrong", e); // TODO
        }
    }
}
