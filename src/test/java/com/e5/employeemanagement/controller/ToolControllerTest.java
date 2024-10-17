package com.e5.employeemanagement.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.e5.employeemanagement.dto.ToolDTO;
import com.e5.employeemanagement.mapper.ToolMapper;
import com.e5.employeemanagement.model.Tool;
import com.e5.employeemanagement.service.ToolService;

@ExtendWith(MockitoExtension.class)
public class ToolControllerTest {
    @Mock
    private ToolService toolService;
    @InjectMocks
    private ToolController toolController;
    private ToolDTO toolDTO;
    private int employeeId;

    @BeforeEach
    public void setUp() {
        toolDTO = ToolDTO.builder()
                .id(1)
                .name("Intellij")
                .type("compiler")
                .version("1.0.0")
                .build();
        employeeId = 1;
    }

    @Test
    public void testAddToolSuccess() {
        when(toolService.addTool(any(ToolDTO.class), anyInt())).thenReturn(toolDTO);
        ResponseEntity<ToolDTO> response = toolController.addTool(toolDTO, 1);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(toolDTO, response.getBody());
    }

    @Test
    public void testGetToolsSuccess() {
        List<Tool> tools = Arrays.asList(ToolMapper.dtoToTool(toolDTO));
        when(toolService.getToolsById(anyInt())).thenReturn(tools);
        ResponseEntity<List<Tool>> response = toolController.getToolsById(employeeId);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tools, response.getBody());
    }

    @Test
    public void testRemoveToolsSuccess() {
        doNothing().when(toolService).removeTool(anyInt());
        ResponseEntity<HttpStatus> response = toolController.removeTool(employeeId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
