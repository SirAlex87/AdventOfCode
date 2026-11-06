package com.borzacchiello.advent_of_code.service.impl;

import java.util.Locale;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.borzacchiello.advent_of_code.exception.InvalidFileException;
import com.borzacchiello.advent_of_code.service.FileValidator;

@Component
public class TxtFileValidator implements FileValidator {

    private static final long MAX_SIZE = 5 * 1024 * (long)1024;

    @Override
    public void validate(MultipartFile file) throws InvalidFileException {

        if (file == null || file.isEmpty()) {
            throw new InvalidFileException(
                    "Il file non può essere vuoto"
            );
        }

        String filename = file.getOriginalFilename();

        if (filename == null ||
            !filename.toLowerCase(Locale.ROOT).endsWith(".txt")) {

            throw new InvalidFileException(
                    "Sono ammessi solo file .txt"
            );
        }

        if (file.getSize() > MAX_SIZE) {
            throw new InvalidFileException(
                    "Il file supera la dimensione massima consentita"
            );
        }
    }
}
