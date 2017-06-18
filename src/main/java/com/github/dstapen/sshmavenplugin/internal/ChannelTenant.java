package com.github.dstapen.sshmavenplugin.internal;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;

public class ChannelTenant<T extends Channel> implements AutoCloseable {

    private final T channel;

    ChannelTenant(final T session) {
        this.channel = session;
    }

    public T channel() {
        return channel;
    }

    @Override
    public void close() throws JSchException {
        channel.disconnect();
    }
}
