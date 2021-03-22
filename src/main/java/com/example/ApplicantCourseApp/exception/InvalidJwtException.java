package com.example.ApplicantCourseApp.exception;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(final String message, Exception ex) {
        super(message, ex);
    }
}
