package com.br.SambaWebAPI.adapter.impl;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import java.io.IOException;

public class ProcessBuilderAdapterImpl implements ProcessBuilderAdapter {
  private final ProcessBuilder processBuilder;

  public ProcessBuilderAdapterImpl() {
    processBuilder = new ProcessBuilder();
  }

  /**
   * @param command
   * @return processBuilder
   */
  @Override
  public ProcessBuilder command(String... command) {
    processBuilder.command(command);
    return processBuilder;
  }

  @Override
  public void start() throws IOException {
    processBuilder.start();
  }
}
