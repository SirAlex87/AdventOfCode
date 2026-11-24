package com.borzacchiello.advent_of_code.dto.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

class TaskExecutionRequestTest {

    @Test
    void shouldBuildTaskExecutionRequest() {

        MultipartFile file =
                new MockMultipartFile(
                        "file",
                        "input.txt",
                        "text/plain",
                        "test".getBytes()
                );

        TaskExecutionRequest request =
                TaskExecutionRequest.builder()
                        .task(5)
                        .part(2)
                        .file(file)
                        .build();

        assertEquals(5, request.getTask());
        assertEquals(2, request.getPart());
        assertSame(file, request.getFile());
    }

    @Test
    void shouldRejectZeroTask() {

        MultipartFile file =
                new MockMultipartFile(
                        "file",
                        "input.txt",
                        "text/plain",
                        new byte[0]
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> TaskExecutionRequest.builder()
                        .task(0)
                        .part(1)
                        .file(file)
                        .build()
        );
    }

    @Test
    void shouldRejectNegativeTask() {

        MultipartFile file =
                new MockMultipartFile(
                        "file",
                        "input.txt",
                        "text/plain",
                        new byte[0]
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> TaskExecutionRequest.builder()
                        .task(-1)
                        .part(1)
                        .file(file)
                        .build()
        );
    }

    @Test
    void shouldRejectInvalidPart() {

        MultipartFile file =
                new MockMultipartFile(
                        "file",
                        "input.txt",
                        "text/plain",
                        new byte[0]
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> TaskExecutionRequest.builder()
                        .task(1)
                        .part(3)
                        .file(file)
                        .build()
        );
    }

    @Test
    void shouldRejectZeroPart() {

        MultipartFile file =
                new MockMultipartFile(
                        "file",
                        "input.txt",
                        "text/plain",
                        new byte[0]
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> TaskExecutionRequest.builder()
                        .task(1)
                        .part(0)
                        .file(file)
                        .build()
        );
    }

    @Test
    void shouldRejectNullFile() {

        assertThrows(
                IllegalArgumentException.class,
                () -> TaskExecutionRequest.builder()
                        .task(1)
                        .part(1)
                        .file(null)
                        .build()
        );
    }
}