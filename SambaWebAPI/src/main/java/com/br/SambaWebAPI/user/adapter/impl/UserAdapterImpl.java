package com.br.SambaWebAPI.user.adapter.impl;

import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.user.adapter.UserAdapter;
import com.br.SambaWebAPI.user.models.User;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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