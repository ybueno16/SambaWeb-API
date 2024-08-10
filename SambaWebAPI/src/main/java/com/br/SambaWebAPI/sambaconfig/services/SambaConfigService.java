package com.br.SambaWebAPI.sambaconfig.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.adapter.impl.ProcessBuilderAdapterImpl;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.folder.factory.FolderDeleteFactory;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import java.io.*;

import com.br.SambaWebAPI.utils.CommandConstants;
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

  public void refreshSambaConfig() throws Exception {
    processBuilderAdapter = new ProcessBuilderAdapterImpl();

    processBuilderAdapter.command("exit");
    ProcessBuilder processBuilder =
            processBuilderAdapter
                    .command(
                            CommandConstants.RELOAD_SMB_CONF)
                    .redirectInput(ProcessBuilder.Redirect.PIPE);

    Process process = processBuilder.start();

    process.waitFor();

    int exitCode = process.exitValue();

    if (exitCode != 0) {
      throw FolderDeleteFactory.createException();
    }
  }
}
