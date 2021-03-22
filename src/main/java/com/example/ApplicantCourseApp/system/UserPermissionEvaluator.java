package com.example.ApplicantCourseApp.system;

import com.example.ApplicantCourseApp.exception.AuthorizationException;
import com.example.ApplicantCourseApp.security.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserPermissionEvaluator {


    public void checkPermission(final UserDetails userDetails) {
        if (!userDetails.getAdmin()) {
            throw new AuthorizationException("only admin user can perform this action");
        }
    }
}
