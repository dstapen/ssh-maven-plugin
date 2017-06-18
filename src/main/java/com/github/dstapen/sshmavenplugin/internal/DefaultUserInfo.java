package com.github.dstapen.sshmavenplugin.internal;

import com.jcraft.jsch.UserInfo;
import org.slf4j.Logger;

public class DefaultUserInfo implements UserInfo {
    private final Logger log;
    private final String passphrase;
    private final String password;
    private final boolean trust;

    public DefaultUserInfo(Logger log, String passphrase, String password, boolean trust) {
        this.log = log;
        this.passphrase = passphrase;
        this.password = password;
        this.trust = trust;
    }

    @Override
    public String getPassphrase() {
        return passphrase;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean promptPassword(String s) {
        log.info("{} {}", s, trust);
        return trust;
    }

    @Override
    public boolean promptPassphrase(String s) {
        log.info("{} {}", s, trust);
        return trust;
    }

    @Override
    public boolean promptYesNo(String s) {
        log.info("{} {}", s, trust);
        return trust;
    }

    @Override
    public void showMessage(String message) {
        log.info(message);
    }
}
