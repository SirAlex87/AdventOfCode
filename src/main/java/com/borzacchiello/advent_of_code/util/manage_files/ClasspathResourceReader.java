package com.borzacchiello.advent_of_code.util.manage_files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class ClasspathResourceReader {

    private ClasspathResourceReader() {
        // Classe utility non istanziabile.
    }

    public static String readString(
            String resourceName)
            throws IOException {

        validateResourceName(resourceName);

        try (InputStream inputStream =
                     openResource(resourceName);
             BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     inputStream,
                                     StandardCharsets.UTF_8))) {

            StringBuilder result =
                    new StringBuilder();

            String line;

            while ((line = reader.readLine())
                    != null) {

                result.append(line)
                        .append(System.lineSeparator());
            }

            return result.toString();
        }
    }

    public static List<String> readLines(
            String resourceName)
            throws IOException {

        validateResourceName(resourceName);

        try (InputStream inputStream =
                     openResource(resourceName);
             BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     inputStream,
                                     StandardCharsets.UTF_8))) {

            return reader.lines()
                    .toList();
        }
    }

    private static InputStream openResource(
            String resourceName)
            throws IOException {

        InputStream inputStream =
                ClasspathResourceReader.class
                        .getClassLoader()
                        .getResourceAsStream(
                                resourceName);

        if (inputStream == null) {

            throw new IOException(
                    "Classpath resource not found: "
                            + resourceName);
        }

        return inputStream;
    }

    private static void validateResourceName(
            String resourceName) {

        if (resourceName == null
                || resourceName.isBlank()) {

            throw new IllegalArgumentException(
                    "Resource name cannot be null or blank");
        }
    }
}