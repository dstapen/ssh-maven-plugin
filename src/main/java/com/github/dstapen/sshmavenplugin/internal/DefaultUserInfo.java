package com.github.dstapen.sshmavenplugin.internal;

import com.jcraft.jsch.UserInfo;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class DefaultUserInfo implements UserInfo {
    private final Logger log;
    private final String passphrase;
    private final String password;
    private final boolean trust;

    public DefaultUserInfo(Logger log, @Nullable String passphrase, @Nullable String password, boolean trust) {
        this.log = log;
        this.passphrase = passphrase;
        this.password = password;
        this.trust = trust;
    }

    @Nullable
    @Override
    public String getPassphrase() {
        return passphrase;
    }

    @Nullable
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean promptPassword(String message) {
        log.info("{} {}", message, trust);
        return trust;
    }

    @Override
    public boolean promptPassphrase(String message) {
        log.info("{} {}", message, trust);
        return trust;
    }

    @Override
    public boolean promptYesNo(String message) {
        log.info("{} {}", message, trust);
        return trust;
    }

    @Override
    public void showMessage(String message) {
        log.info(message);
    }
}
