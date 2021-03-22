package com.example.ApplicantCourseApp.service;

import com.example.ApplicantCourseApp.domain.Course;
import com.example.ApplicantCourseApp.exception.ApplicantCourseIllegalStateException;
import com.example.ApplicantCourseApp.exception.CustomValidationException;
import com.example.ApplicantCourseApp.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.ApplicantCourseApp.validator.CourseValidator.dateValidation;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(Course course) {
        validateCourse(course);
        return courseRepository.save(course);
    }

    public Course loadCourse(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("course with %s id not found", courseId)));
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("user with %s id not found", id)));
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getByName(String name) {
        return courseRepository.findCourseByName(name);
    }

    public Course updateCourse(Course course) {
        dateValidation(course.getStartDate(), course.getEndDate());
        courseRepository.findById(course.getCourseId()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("course with %s id not found", course.getName())));
        courseRepository.save(course);
        return course;
    }

    public void deleteCourse(Long courseId) {
        Optional<Course> applicant = courseRepository.findById(courseId);
        if (!applicant.isPresent()) {
            throw new ApplicantCourseIllegalStateException("Failed to remove Course. Invalid CourseId :: " + courseId);
        }
        courseRepository.deleteById(courseId);

    }

    public List<Course> getCoursesByQuery(String queryName) {
        return courseRepository.findByNameContaining(queryName);
    }

    public void validateCourse(Course course) {
        dateValidation(course.getStartDate(), course.getEndDate());
        validateUniqueness(course.getName());
    }

    public void validateUniqueness(String name) {
        Optional<Course> course = getByName(name);
        if (course.isPresent()) {
            throw new CustomValidationException("email already exists");
        }
    }
}
