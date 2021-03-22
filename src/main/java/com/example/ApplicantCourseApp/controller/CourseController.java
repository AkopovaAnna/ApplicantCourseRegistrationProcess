package com.example.ApplicantCourseApp.controller;

import com.example.ApplicantCourseApp.domain.Course;
import com.example.ApplicantCourseApp.security.SecurityContextAccessor;
import com.example.ApplicantCourseApp.service.CourseService;
import com.example.ApplicantCourseApp.system.UserPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserPermissionEvaluator permissionEvaluator;

    @Autowired
    private SecurityContextAccessor contextAccessor;


    @GetMapping("/{id}")
    public Course getById(@PathVariable("id") final Long id) {
        return courseService.getCourseById(id);
    }

    @GetMapping(value = "")
    public List<Course> search(@RequestParam(name = "name", required = false) String search) {
        if (search != null) {
            return courseService.getCoursesByQuery(search);
        } else {
            return courseService.getAllCourses();
        }

    }

    @PostMapping("")
    public ResponseEntity createCourse(@RequestBody Course course) {
        permissionEvaluator.checkPermission(contextAccessor.getUserDetails());
        Course courseSummary = courseService.createCourse(course);
        return new ResponseEntity<>(courseSummary, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable("id") final Long id,
                               @RequestBody Course course) {
        permissionEvaluator.checkPermission(contextAccessor.getUserDetails());
        course.setCourseId(id);
        return courseService.updateCourse(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable("id") final Long id) {
        permissionEvaluator.checkPermission(contextAccessor.getUserDetails());
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
