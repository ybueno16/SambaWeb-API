package com.br.SambaWebAPI.sambaconfig.services;

import com.br.SambaWebAPI.adapter.ProcessBuilderAdapter;
import com.br.SambaWebAPI.config.Global;
import com.br.SambaWebAPI.folder.models.Folder;
import com.br.SambaWebAPI.password.models.SudoAuthentication;
import com.br.SambaWebAPI.permission.models.GroupPermission;
import com.br.SambaWebAPI.permission.models.OwnerPermission;
import com.br.SambaWebAPI.permission.models.PublicPermission;
import com.br.SambaWebAPI.sambaconfig.models.SambaConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SambaConfigServiceTest {
    @Mock
    private ProcessBuilderAdapter processBuilderAdapter;

    @Mock
    private ProcessBuilder processBuilder;

    @Mock
    private SudoAuthentication sudoAuthentication;

    @InjectMocks
    private SambaConfigService sambaConfigService;


    @Mock
    private Process process;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSambaConfigWriteNewConfig() throws IOException, InterruptedException {
        ProcessBuilderAdapter processBuilderAdapter = Mockito.mock(ProcessBuilderAdapter.class);
        ProcessBuilder processBuilder = Mockito.mock(ProcessBuilder.class);
        Process process = Mockito.mock(Process.class);
        when(processBuilderAdapter.command(String.valueOf(any(String[].class)))).thenReturn(processBuilder);
        when(processBuilder.start()).thenReturn(process);
        when(process.waitFor()).thenReturn(0);

        File tempFile = File.createTempFile("samba-config", ".tmp");
        tempFile.deleteOnExit();

        System.setProperty("samba.config.file", tempFile.getAbsolutePath());

        Global.SMB_CONF_PATH = tempFile.getAbsolutePath();

        SambaConfigService service = new SambaConfigService(processBuilderAdapter);

        SambaConfig config = new SambaConfig();
        config.setSection("teste");
        config.setSectionParams(Arrays.asList("param1", "param2"));

        SudoAuthentication auth = new SudoAuthentication();

        service.sambaConfigWriteNewConfig(config, auth);

        BufferedReader reader = new BufferedReader(new FileReader(tempFile));

        reader.close();

        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);
    }


}