package com.br.SambaWebAPI.logs.controller;

import com.br.SambaWebAPI.logs.service.LogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LogControllerTest {

    @Test
    @DisplayName("""
        Give a valid log insertion,
        when writeSambaFile is called,
        then it should return HTTP 200 with a success message
    """)
    public void testWriteSambaFileSuccess() throws Exception {
        LogService logService = mock(LogService.class);
        when(logService.insertLogs()).thenReturn(true);
        LogController logController = new LogController(logService);
        ResponseEntity<?> response = logController.writeSambaFile();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("""
        Give a database error during log insertion,
        when writeSambaFile is called,
        then it should return HTTP 500 with an error message
    """)
    public void testWriteSambaFileDatabaseError() throws Exception {
        LogService logService = mock(LogService.class);
        when(logService.insertLogs()).thenThrow(SQLException.class);
        LogController logController = new LogController(logService);
        ResponseEntity<?> response = logController.writeSambaFile();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    @DisplayName("""
        Give a generic error during log insertion,
        when writeSambaFile is called,
        then it should return HTTP 500 with an error message
    """)
    public void testWriteSambaFileGenericError() throws Exception {
        LogService logService = mock(LogService.class);
        when(logService.insertLogs()).thenThrow(Exception.class);
        LogController logController = new LogController(logService);
        ResponseEntity<?> response = logController.writeSambaFile();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}