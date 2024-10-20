package com.br.SambaWebAPI.sambaconfig.models.dto;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;

public class SambaConfigOperationsDTO {
    private SambaConfig sambaConfig;
    private SudoAuthentication sudoAuthentication;

    public SambaConfig getSambaConfig() {
        return sambaConfig;
    }

    public void setSambaConfig(SambaConfig sambaConfig) {
        this.sambaConfig = sambaConfig;
    }

    public SudoAuthentication getSudoAuthentication() {
        return sudoAuthentication;
    }

    public void setSudoAuthentication(SudoAuthentication sudoAuthentication) {
        this.sudoAuthentication = sudoAuthentication;
    }
}
