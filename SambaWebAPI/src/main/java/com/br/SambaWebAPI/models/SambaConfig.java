package com.br.SambaWebAPI.models;

import java.util.List;

public class SambaConfig {
    private List<String> workgroups;
    private List<String> serverStrings;
    private List<String> securities;
    private Boolean encryptPasswords;
    private List<String> smbPasswdFiles;
    private List<String> hostsAllow;
    private List<String> hostsDeny;
    private List<String> interfaces;
    private List<String> socketOptions;
    private List<String> logFiles;
    private List<Integer> logLevels;

    public List<String> getWorkgroups() {
        return workgroups;
    }

    public void setWorkgroups(List<String> workgroups) {
        this.workgroups = workgroups;
    }

    public List<String> getServerStrings() {
        return serverStrings;
    }

    public void setServerStrings(List<String> serverStrings) {
        this.serverStrings = serverStrings;
    }

    public List<String> getSecurities() {
        return securities;
    }

    public void setSecurities(List<String> securities) {
        this.securities = securities;
    }

    public Boolean getEncryptPasswords() {
        return encryptPasswords;
    }

    public void setEncryptPasswords(Boolean encryptPasswords) {
        this.encryptPasswords = encryptPasswords;
    }

    public List<String> getSmbPasswdFiles() {
        return smbPasswdFiles;
    }

    public void setSmbPasswdFiles(List<String> smbPasswdFiles) {
        this.smbPasswdFiles = smbPasswdFiles;
    }

    public List<String> getHostsAllow() {
        return hostsAllow;
    }

    public void setHostsAllow(List<String> hostsAllow) {
        this.hostsAllow = hostsAllow;
    }

    public List<String> getHostsDeny() {
        return hostsDeny;
    }

    public void setHostsDeny(List<String> hostsDeny) {
        this.hostsDeny = hostsDeny;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public List<String> getSocketOptions() {
        return socketOptions;
    }

    public void setSocketOptions(List<String> socketOptions) {
        this.socketOptions = socketOptions;
    }

    public List<String> getLogFiles() {
        return logFiles;
    }

    public void setLogFiles(List<String> logFiles) {
        this.logFiles = logFiles;
    }

    public List<Integer> getLogLevels() {
        return logLevels;
    }

    public void setLogLevels(List<Integer> logLevels) {
        this.logLevels = logLevels;
    }
}
