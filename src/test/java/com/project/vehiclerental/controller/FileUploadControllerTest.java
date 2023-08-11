package com.project.vehiclerental.controller;

import com.project.vehiclerental.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileUploadControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileUploadController fileUploadController;

    private MultipartFile multipartFile;

    @BeforeEach
    public void setUp() {
        multipartFile = new MockMultipartFile("file", "hello.txt", "text/plain", "Hello, World!".getBytes());
    }

    @Test
    public void testUploadFile() {
        when(fileService.uploadFile(any(MultipartFile.class))).thenReturn("fileId");

        ResponseEntity<?> response = fileUploadController.uploadFile(multipartFile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(fileService, times(1)).uploadFile(any(MultipartFile.class));
    }

    @Test
    public void testDownloadFile() {
        when(fileService.downloadFile(anyString())).thenReturn(multipartFile);

        ResponseEntity<?> response = fileUploadController.downloadFile("fileId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(fileService, times(1)).downloadFile(anyString());
    }

    @Test
    public void testDeleteFile() {
        when(fileService.delete(anyString())).thenReturn(true);

        ResponseEntity<?> response = fileUploadController.delete("fileId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(fileService, times(1)).delete(anyString());
    }
}
