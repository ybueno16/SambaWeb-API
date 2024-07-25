package com.br.SambaWebAPI.utils;

public class CommandConstants {
    public static final String SUDO = "sudo";
    public static final String USER_ADD = "useradd";
    public static final String USER_DEL = "userdel";
    public static final String SUDO_STDIN = "-S";
    public static final String GROUP_ADD = "groupadd";
    public static final String USER_MOD = "/usr/sbin/usermod";
    public static final String ADD_GROUP_OPTION = "-aG";
    public static final String BASH = "bash";
    public static final String EXECUTE_COMMAND = "-c";
    public static final String ECHO = "echo";
    public static final String GET_USER = "cat /etc/passwd | grep ";
    public static final String MKDIR = "mkdir";
    public static final String USER_ADD_SMB = "smbpasswd -a";
    public static final String USER_DEL_SMB = "smbpasswd -x";
    public static final String CHMOD = "chmod";


}
