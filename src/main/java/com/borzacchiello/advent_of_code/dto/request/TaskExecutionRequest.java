package com.borzacchiello.advent_of_code.dto.request;

import org.springframework.web.multipart.MultipartFile;

/**
 * Rappresenta una richiesta completa di esecuzione di un task.
 *
 * <p>
 * L'oggetto viene costruito tramite Builder per centralizzare
 * la creazione e la validazione dei parametri necessari
 * all'esecuzione.
 * </p>
 */
public final class TaskExecutionRequest {

    private final int task;
    private final int part;
    private final MultipartFile file;

    private TaskExecutionRequest(Builder builder) {
        this.task = builder.task;
        this.part = builder.part;
        this.file = builder.file;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getTask() {
        return task;
    }

    public int getPart() {
        return part;
    }

    public MultipartFile getFile() {
        return file;
    }

    public static final class Builder {

        private int task;
        private int part;
        private MultipartFile file;

        private Builder() {
        }

        public Builder task(int task) {
            this.task = task;
            return this;
        }

        public Builder part(int part) {
            this.part = part;
            return this;
        }

        public Builder file(MultipartFile file) {
            this.file = file;
            return this;
        }

        public TaskExecutionRequest build() {
            validate();
            return new TaskExecutionRequest(this);
        }

        private void validate() {

            if (task <= 0) {
                throw new IllegalArgumentException(
                        "Task must be greater than zero"
                );
            }

            if (part != 1 && part != 2) {
                throw new IllegalArgumentException(
                        "Part must be 1 or 2"
                );
            }

            if (file == null) {
                throw new IllegalArgumentException(
                        "File cannot be null"
                );
            }
        }
    }
}