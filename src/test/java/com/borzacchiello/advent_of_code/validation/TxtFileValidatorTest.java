package com.borzacchiello.advent_of_code.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.web.MockMultipartFile;

import com.borzacchiello.advent_of_code.exception.InvalidFileException;
import com.borzacchiello.advent_of_code.service.impl.TxtFileValidator;

class TxtFileValidatorTest {

    private TxtFileValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TxtFileValidator();
    }

    @Test
    void shouldAcceptValidTxtFile() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "input.txt",
                        "text/plain",
                        "R50".getBytes(
                                StandardCharsets.UTF_8
                        )
                );

        assertDoesNotThrow(
                () -> validator.validate(file)
        );
    }

    @ParameterizedTest(name = "{index} - {0}")
    @MethodSource("invalidFileScenarios")
    void shouldRejectInvalidFile(
            String scenario,
            MockMultipartFile file) {

        assertThrows(
                InvalidFileException.class,
                () -> validator.validate(file)
        );
    }

    private static Stream<Arguments> invalidFileScenarios() {

        return Stream.of(
                Arguments.of(
                        "Empty TXT file",
                        new MockMultipartFile(
                                "file",
                                "input.txt",
                                "text/plain",
                                new byte[0]
                        )
                ),
                Arguments.of(
                        "CSV file",
                        new MockMultipartFile(
                                "file",
                                "input.csv",
                                "text/csv",
                                "R50".getBytes(
                                        StandardCharsets.UTF_8
                                )
                        )
                ),
                Arguments.of(
                        "Missing filename",
                        new MockMultipartFile(
                                "file",
                                null,
                                "text/plain",
                                "R50".getBytes(
                                        StandardCharsets.UTF_8
                                )
                        )
                )
        );
    }

    @Test
    void shouldRejectNullFile() {

        assertThrows(
                InvalidFileException.class,
                () -> validator.validate(null)
        );
    }
}