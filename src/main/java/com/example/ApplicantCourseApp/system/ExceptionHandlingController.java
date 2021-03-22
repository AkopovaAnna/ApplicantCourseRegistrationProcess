package com.example.ApplicantCourseApp.system;

import com.example.ApplicantCourseApp.exception.*;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionResponse resourceNotFound(final RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("Not Found", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse allExceptions(final Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("Bad Request", "Something weird happened: " + ex.getMessage());

    }

    @ExceptionHandler(DocumentCreationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse pdfCreationException(final Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("Bad Request", "Something weird happened: " + ex.getMessage());
    }

    @ExceptionHandler(DocumentDownloadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse zipCreationException(final Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("Bad Request", "Something weird happened: " + ex.getMessage());
    }

    @ExceptionHandler(AuthenticationFailureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResponse authenticationFailure(final AuthenticationFailureException ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("UNAUTHORIZED", "Invalid email or password.");

    }

    @ExceptionHandler(InvalidJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResponse invalidJwt(final InvalidJwtException ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("UNAUTHORIZED", "Malformed Jwt");

    }

    @ExceptionHandler(CustomValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse notValid(final CustomValidationException ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("BAD_REQUEST", ex.getMessage());

    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionResponse authorizationFailure(final AuthorizationException ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("UNAUTHORIZED", "Not Authorized.");

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponse sqlConstraintFailure(final ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        return new ExceptionResponse("BAD_REQUEST", ex.getMessage());

    }

    @ExceptionHandler({AccessDeniedException.class, UnsupportedOperationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleForbiddenRequests(final Exception ex, final HttpServletRequest request) {
//        log.warn(String.format("%s - %s", requestLogMessageBuilder.getRequestMessage(request), ex.getMessage()));
//        Throwable error = firstNonNull(ex.getCause(), ex);
//        log.info(error.getMessage());
//        error.printStackTrace();

        return new ExceptionResponse(ex.getCause().getMessage(), request.getRequestURI());
    }

}
