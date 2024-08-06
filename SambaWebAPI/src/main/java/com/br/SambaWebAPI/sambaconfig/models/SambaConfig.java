package com.br.SambaWebAPI.sambaconfig.models;

import java.util.List;

public class SambaConfig {
  private String section;
  private List<String> sectionParams;

  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public List<String> getSectionParams() {
    return sectionParams;
  }

  public void setSectionParams(List<String> sectionParams) {
    this.sectionParams = sectionParams;
  }
}
