package com.spring.exercise;

/**
 * Standard Structure of the error response
 */
import java.time.LocalDateTime;

public class ErrorResponse {

    @SuppressWarnings("FieldMayBeFinal")
    private int status;

    @SuppressWarnings("FieldMayBeFinal")
    private String error;

    @SuppressWarnings("FieldMayBeFinal")
    private String message;

    @SuppressWarnings("FieldMayBeFinal")
    private String path;

    @SuppressWarnings("FieldMayBeFinal")
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    public String getMessage() {
        return this.message;
    }

    public String getPath() {
        return this.path;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }
}
