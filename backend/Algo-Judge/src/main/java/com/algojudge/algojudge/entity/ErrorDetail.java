package com.algojudge.algojudge.entity;

import java.time.LocalDateTime;

public class ErrorDetail {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetail(LocalDateTime timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters for all fields
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    // Setters (optional, typically not needed for immutable error details)
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
