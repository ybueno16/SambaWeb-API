package com.br.SambaWebAPI.config;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public ProcessBuilder processBuilder() {
    return new ProcessBuilder();
  }

  @Bean
  public ProcessBuilderAdapter processBuilderAdapter() {
    return new ProcessBuilderAdapterImpl();
  }

  @Bean
  public Global global() {
    return new Global();
  }



}