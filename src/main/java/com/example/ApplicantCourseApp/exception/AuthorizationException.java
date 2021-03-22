package com.example.ApplicantCourseApp.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super("UnAuthorized Access");
    }
    public AuthorizationException(String message) {
        super(message);
    }
}
