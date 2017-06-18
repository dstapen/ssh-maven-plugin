package com.github.dstapen.sshmavenplugin.internal;

import com.jcraft.jsch.*;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class SessionTenant implements AutoCloseable {
    private static final Logger LOG = getLogger(SessionTenant.class);
    private final Session session;

    public static SessionTenant connect(String host, int port, String user, String password, boolean trust) throws JSchException {
        JSch ssh = new JSch();
        Session session = ssh.getSession(user, host, port);
        session.setUserInfo(new DefaultUserInfo(LOG, "", password, trust));
        session.connect();
        return new SessionTenant(session);
    }

    private SessionTenant(Session session) {
        this.session = session;
    }

    public ChannelTenant<ChannelExec> exec() throws JSchException {
        return new ChannelTenant<>(((ChannelExec) session.openChannel("exec")));
    }

    public ChannelTenant<ChannelSftp> sftp() throws JSchException {
        return new ChannelTenant<>(((ChannelSftp) session.openChannel("sftp")));
    }

    @Override
    public void close() throws JSchException {
        session.disconnect();
    }
}
