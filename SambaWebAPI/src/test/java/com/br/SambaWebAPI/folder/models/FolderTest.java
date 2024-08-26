package com.br.SambaWebAPI.folder.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FolderTest {

  @Test
  @DisplayName("Should return path")
  void getPath() {
    Folder folder = new Folder();
    folder.setPath("/foo/bar");
    assertEquals("/foo/bar", folder.getPath());
  }

  @Test
  @DisplayName("Should set path")
  void setPath() {
    Folder folder = new Folder();
    folder.setPath("/foo/bar");
    assertEquals("/foo/bar", folder.getPath());
  }
}
