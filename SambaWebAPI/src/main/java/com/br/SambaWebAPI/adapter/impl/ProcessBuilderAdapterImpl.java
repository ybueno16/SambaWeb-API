package com.br.SambaWebAPI.adapter.impl;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;

public class ProcessBuilderAdapterImpl implements ProcessBuilderAdapter {
    private final ProcessBuilder processBuilder;

    public ProcessBuilderAdapterImpl() {
        processBuilder = new ProcessBuilder();
    }

    @Override
    public ProcessBuilder command(String... command) {
        processBuilder.command(command);
        return processBuilder;
    }
}