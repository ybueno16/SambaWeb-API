package com.br.SambaWebAPI.adapter.impl;

import com.br.SambaWebAPI.adapter.UserAdapter;

public class UserAdapterImpl implements UserAdapter {
    private final ProcessBuilder processBuilder;

    public UserAdapterImpl() {
        processBuilder = new ProcessBuilder();
    }

    @Override
    public void command(String... command) {
        processBuilder.command(command);
    }
}