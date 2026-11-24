package com.borzacchiello.advent_of_code.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.borzacchiello.advent_of_code.dto.request.TaskExecutionRequest;
import com.borzacchiello.advent_of_code.dto.response.TaskResult;
import com.borzacchiello.advent_of_code.service.TaskService;

@WebMvcTest(
        controllers = TaskController.class
)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldExecuteTask01Part1() throws Exception {

        TaskResult taskResult =
                new TaskResult(1, 1, 1180);

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "prova1.txt",
                        "text/plain",
                        "R50\nL20"
                                .getBytes(StandardCharsets.UTF_8)
                );

        when(
                taskService.execute(
                        any(TaskExecutionRequest.class)
                )
        ).thenReturn(taskResult);

        mockMvc.perform(
                        multipart(
                                "/api/tasks/{task}/parts/{part}",
                                1,
                                1
                        )
                                .file(file)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task").value(1))
                .andExpect(jsonPath("$.part").value(1))
                .andExpect(jsonPath("$.result").value(1180));

        ArgumentCaptor<TaskExecutionRequest> captor =
                ArgumentCaptor.forClass(
                        TaskExecutionRequest.class
                );

        verify(taskService)
                .execute(captor.capture());

        TaskExecutionRequest request =
                captor.getValue();

        assertEquals(1, request.getTask());
        assertEquals(1, request.getPart());
        assertEquals(
                "prova1.txt",
                request.getFile().getOriginalFilename()
        );
    }

    @Test
    void shouldReturnBadRequestWhenFileIsMissing()
            throws Exception {

        mockMvc.perform(
                        multipart(
                                "/api/tasks/{task}/parts/{part}",
                                1,
                                1
                        )
                )
                .andExpect(status().isBadRequest());

        verifyNoInteractions(taskService);
    }
}