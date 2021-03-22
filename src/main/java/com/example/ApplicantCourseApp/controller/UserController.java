package com.example.ApplicantCourseApp.controller;

import com.example.ApplicantCourseApp.domain.User;
import com.example.ApplicantCourseApp.security.JwtService;
import com.example.ApplicantCourseApp.security.SecurityContextAccessor;
import com.example.ApplicantCourseApp.service.UserService;
import com.example.ApplicantCourseApp.system.UserPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserPermissionEvaluator permissionEvaluator;

    @Autowired
    private SecurityContextAccessor contextAccessor;

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.CREATED)
    public String createToken(@RequestBody User credentials) {
        User checkedUser = userService.checkAuth(credentials.getEmail(), credentials.getPassword());
        return jwtService.createToken(checkedUser.getId(), checkedUser.getEmail(), checkedUser.getAdmin());

    }


    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long userId) {
        permissionEvaluator.checkPermission(contextAccessor.getUserDetails());
        User user =  userService.getUser(userId);
        return userService.mapToUserResponse(user);
    }

    @PutMapping("/{userId}")
    public User update(@RequestBody User userData, @PathVariable("userId") Long userId) {
        permissionEvaluator.checkPermission(contextAccessor.getUserDetails());
        return userService.updateUser(userData, userId);
    }

}
