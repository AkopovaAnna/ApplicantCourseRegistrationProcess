package com.example.ApplicantCourseApp.validator;

import com.example.ApplicantCourseApp.exception.CustomValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public static void validatePassword(String password) throws CustomValidationException {
        String regex = "^(?=(?:.*[0-9]){3,})(?=.*[a-z])(?=(?:.*[A-Z]){2,}).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (isValidString(password)) {
            if (!matcher.matches()) {
                throw new CustomValidationException("at least 2 uppercase, 3 numbers and length greater than 8");
            }
        } else {
            throw new CustomValidationException("Please enter password");
        }
    }

    public static boolean isValidString(String str) {
        return str != null && !str.isEmpty();
    }
}
