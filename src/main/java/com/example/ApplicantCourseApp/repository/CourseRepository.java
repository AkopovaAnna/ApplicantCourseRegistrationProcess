package com.example.ApplicantCourseApp.repository;

import com.example.ApplicantCourseApp.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findCourseByApplicants(Long applicantId);

    Optional<Course> findCourseByName(String name);

    List<Course> findByNameContaining(String name);

}
