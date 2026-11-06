package com.borzacchiello.advent_of_code.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.borzacchiello.advent_of_code.dto.response.TaskResult;
import com.borzacchiello.advent_of_code.model.TaskKey;
import com.borzacchiello.advent_of_code.model.task01.Direction;
import com.borzacchiello.advent_of_code.model.task01.Rotation;
import com.borzacchiello.advent_of_code.parser.task01.Task01Parser;
import com.borzacchiello.advent_of_code.service.impl.TaskServiceImpl;
import com.borzacchiello.advent_of_code.strategy.TaskStrategy;
import com.borzacchiello.advent_of_code.strategy.TaskStrategyFactory;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private FileValidator fileValidator;

    @Mock
    private Task01Parser parser;

    @Mock
    private TaskStrategyFactory strategyFactory;

    @Mock
    private TaskStrategy<List<Rotation>, Integer> strategy;

    private TaskServiceImpl service;

    @BeforeEach
    void setUp() {

        when(parser.getTaskNumber())
                .thenReturn(1);

        service = new TaskServiceImpl(
                fileValidator,
                List.of(parser),
                strategyFactory
        );
    }

    @Test
    void shouldExecuteTask01Part1() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "input.txt",
                        "text/plain",
                        "R50\nL20".getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        List<String> lines = List.of(
                "R50",
                "L20"
        );

        List<Rotation> rotations = List.of(
                new Rotation(Direction.RIGHT, 50),
                new Rotation(Direction.LEFT, 20)
        );

        TaskKey taskKey =
                new TaskKey(1, 1);

        when(parser.parse(lines))
                .thenReturn(rotations);

        when(strategyFactory.getStrategy(taskKey))
                .thenAnswer(invocation -> strategy);

        when(strategy.execute(rotations))
                .thenReturn(1);

        TaskResult result =
                service.execute(1, 1, file);

        assertAll(
                () -> assertEquals(1, result.task()),
                () -> assertEquals(1, result.part()),
                () -> assertEquals(1, result.result())
        );

        verify(fileValidator)
                .validate(file);

        verify(parser)
                .parse(lines);

        verify(strategyFactory)
                .getStrategy(taskKey);

        verify(strategy)
                .execute(rotations);
    }
}