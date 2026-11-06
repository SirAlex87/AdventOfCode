package com.borzacchiello.advent_of_code.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class TestFileUtils {

    private TestFileUtils() {
        // Utility class
    }

    public static List<String> readLines(String resource) {

        try (InputStream inputStream =
                     TestFileUtils.class
                             .getClassLoader()
                             .getResourceAsStream(resource)) {

            if (inputStream == null) {
                throw new IllegalArgumentException(
                        "Test resource not found: " + resource
                );
            }

            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(
                                         inputStream,
                                         StandardCharsets.UTF_8))) {

                return reader.lines().toList();
            }

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Unable to read test resource: " + resource,
                    e
            );
        }
    }
}
