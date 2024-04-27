package com.br.SambaWebAPI.UbuntuConfig.models;

import java.util.List;

public class User {
    private String user;
    private String password;
    private String senhaSudo;

    public String getSenhaSudo() {
        return senhaSudo;
    }

    public void setSenhaSudo(String senhaSudo) {
        this.senhaSudo = senhaSudo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
