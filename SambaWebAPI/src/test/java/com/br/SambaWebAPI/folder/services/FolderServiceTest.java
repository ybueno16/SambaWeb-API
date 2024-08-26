package com.br.SambaWebAPI.folder.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class FolderServiceTest {

    @Mock
    private SudoAuthentication sudoAuthentication;

    @Mock
    private Folder folder;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sudoAuthentication.setSudoPassword("sudo_password");
        folder.setPath("foo/bar");

    }
    @Test
    @DisplayName("""
            Dado um processo de criação de pasta,
            quando criar uma pasta com sucesso,
            então deve retornar true
            """)
    void createFolder() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(folder.getPath()).thenReturn("/foo/bar");

        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        Process process = Mockito.mock(Process.class);

        when(processBuilderAdapter.command(any(String[].class))).thenReturn(processBuilder);
        when(processBuilder.start()).thenReturn(process);

        InputStream inputStream = new ByteArrayInputStream("home_dir".getBytes());
        when(process.getInputStream()).thenReturn(inputStream);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(process.waitFor()).thenReturn(0);

        FolderService folderService = new FolderService(processBuilderAdapter);

        boolean result = folderService.createFolder(folder, sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter, times(3)).command(any(String[].class));
    }

    @Test
    @DisplayName("""
            Dado um processo de remoção de pasta,
            quando remover uma pasta com sucesso,
            então deve retornar true
            """)
    void removeFolder() throws Exception {
        when(sudoAuthentication.getSudoPassword()).thenReturn("sudo_password");
        when(folder.getPath()).thenReturn("/foo/bar");
        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        Process process = Mockito.mock(Process.class);

        when(processBuilderAdapter.command(any(String[].class))).thenReturn(processBuilder);
        when(processBuilder.start()).thenReturn(process);

        InputStream inputStream = new ByteArrayInputStream("home_dir".getBytes());
        when(process.getInputStream()).thenReturn(inputStream);
        when(process.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(process.waitFor()).thenReturn(0);

        FolderService folderService = new FolderService(processBuilderAdapter);

        boolean result = folderService.removeFolder(folder, sudoAuthentication);

        assertTrue(result);

        verify(processBuilderAdapter, times(3)).command(any(String[].class));
    }

}