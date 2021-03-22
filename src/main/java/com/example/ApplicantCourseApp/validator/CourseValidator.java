package com.example.ApplicantCourseApp.validator;


import com.example.ApplicantCourseApp.exception.CustomValidationException;

import java.time.LocalDate;

public class CourseValidator {

    public static void dateValidation(LocalDate startDate, LocalDate endDate) throws CustomValidationException {
        if (startDate.isBefore(LocalDate.now()) && endDate.isBefore(LocalDate.now())) {
            if (endDate.isBefore(startDate)) {
                throw new CustomValidationException("endDate should be greater than startDate");
            }
            throw new CustomValidationException("provided bad date");
        }
    }
}
