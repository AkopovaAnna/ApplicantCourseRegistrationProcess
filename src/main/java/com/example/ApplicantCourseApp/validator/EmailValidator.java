package com.example.ApplicantCourseApp.validator;

import com.example.ApplicantCourseApp.exception.CustomValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    public static boolean isValidString(String str) {
        return str != null && !str.isEmpty();
    }

    public static void validateEmail(String email) throws CustomValidationException {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        if (isValidString(email)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new CustomValidationException("Invalid email");
            }

        } else {
            throw new CustomValidationException("Please insert email");
        }
    }
}
