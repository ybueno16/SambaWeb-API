package com.br.SambaWebAPI.sambaconfig.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.sambaconfig.exceptions.ReloadSambaConfigException;
import com.br.SambaWebAPI.sambaconfig.factory.ReloadSambaConfigFactory;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import com.br.SambaWebAPI.utils.CommandConstants;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SambaConfigService {

  private ProcessBuilderAdapter processBuilderAdapter;

  @Autowired
  public SambaConfigService(ProcessBuilderAdapter processBuilderAdapter) {
    this.processBuilderAdapter = processBuilderAdapter;
  }

  public void sambaConfigWriteNewConfig(
      SambaConfig sambaConfig, SudoAuthentication sudoAuthentication) throws IOException {

    boolean sectionExists = false;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(Global.SMB_CONF_PATH, true));
        BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("[" + sambaConfig.getSection() + "]")) {
          sectionExists = true;
          for (String sectionParam : sambaConfig.getSectionParams()) {
            writer.write(sectionParam.toLowerCase() + "\n");
          }
        }
      }

      if (!sectionExists) {
        writer.write("\n[" + sambaConfig.getSection() + "]\n");
        for (String sectionParam : sambaConfig.getSectionParams()) {
          writer.write(sectionParam.toLowerCase() + "\n");
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void sambaConfigEditConfig(SambaConfig sambaConfig, SudoAuthentication sudoAuthentication)
      throws IOException {

    boolean sectionExists = false;
    StringBuilder modifiedContent = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH))) {

      String line;

      while ((line = reader.readLine()) != null) {
        if (line.startsWith("[" + sambaConfig.getSection() + "]")) {
          sectionExists = true;
        } else if (sectionExists && line.contains("[")) {
          sectionExists = false;
          modifiedContent.append("[").append(sambaConfig.getSection()).append("]\n");
          for (String sectionParam : sambaConfig.getSectionParams()) {
            modifiedContent.append(sectionParam).append("\n");
          }
          modifiedContent.append("\n");
        } else if (!sectionExists) {
          modifiedContent.append(line).append("\n");
        }
      }
    }

    if (sectionExists) {
      modifiedContent.append("[").append(sambaConfig.getSection()).append("]\n");
      for (String sectionParam : sambaConfig.getSectionParams()) {
        modifiedContent.append(sectionParam).append("\n");
      }
      modifiedContent.append("\n");
    }

    try (FileWriter writer = new FileWriter(Global.SMB_CONF_PATH)) {
      writer.write(modifiedContent.toString());
    }
  }

  public void sambaConfigRemoveSectionParams(
      SambaConfig sambaConfig, SudoAuthentication sudoAuthentication) throws IOException {

    boolean sectionExists = false;
    StringBuilder modifiedContent = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH))) {

      String line;

      while ((line = reader.readLine()) != null) {
        if (line.startsWith("[" + sambaConfig.getSection() + "]")) {
          sectionExists = true;
        } else if (sectionExists && line.contains("[")) {
          sectionExists = false;
          modifiedContent.append("[").append(sambaConfig.getSection()).append("]\n\n");
        } else if (!sectionExists) {
          modifiedContent.append(line).append("\n");
        }
      }
    }

    if (sectionExists) {
      modifiedContent.append("[").append(sambaConfig.getSection()).append("]\n\n");
    }

    try (FileWriter writer = new FileWriter(Global.SMB_CONF_PATH)) {
      writer.write(modifiedContent.toString());
    }
  }

  public void sambaConfigRemoveSection(
      SambaConfig sambaConfig, SudoAuthentication sudoAuthentication) throws Exception {

    boolean sectionExists = false;
    StringBuilder modifiedContent = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(Global.SMB_CONF_PATH))) {

      String line;

      while ((line = reader.readLine()) != null) {
        if (line.startsWith("[" + sambaConfig.getSection() + "]")) {
          sectionExists = true;
        } else if (sectionExists && line.contains("[")) {
          sectionExists = false;
          modifiedContent.append(line).append("\n");
        } else if (!sectionExists) {
          modifiedContent.append(line).append("\n");
        }
      }
    }

    try (FileWriter writer = new FileWriter(Global.SMB_CONF_PATH)) {
      writer.write(modifiedContent.toString());
    }
  }

  public boolean refreshSambaConfig(SudoAuthentication sudoAuthentication)
      throws IOException, InterruptedException, ReloadSambaConfigException {
    System.out.println("Iniciando refreshSambaConfig...");

    ProcessBuilder processBuilder =
        processBuilderAdapter.command(
            CommandConstants.SUDO,
            CommandConstants.SUDO_STDIN,
            CommandConstants.SMBCONTROL,
            CommandConstants.SMBCONTROL_ALL,
            CommandConstants.SMBCONTROL_RELOAD_CONF);

    Process process = processBuilder.start();

    OutputStream outputStream = process.getOutputStream();

    outputStream.write((sudoAuthentication.getSudoPassword() + "\n").getBytes());
    outputStream.flush();
    outputStream.close();
    process.waitFor();

    int exitCode = process.waitFor();

    if (exitCode != 0) {
      throw ReloadSambaConfigFactory.createException(exitCode);
    }
    return true;
  }
}
