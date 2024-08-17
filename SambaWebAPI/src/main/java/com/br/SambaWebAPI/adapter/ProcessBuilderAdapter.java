package com.br.SambaWebAPI.adapter;

import java.io.IOException;

public interface ProcessBuilderAdapter {
  ProcessBuilder command(String... command);

  void start() throws IOException;
}
