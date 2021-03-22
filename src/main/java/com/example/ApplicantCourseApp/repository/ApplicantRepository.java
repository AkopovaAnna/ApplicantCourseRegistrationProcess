package com.example.ApplicantCourseApp.repository;

import com.example.ApplicantCourseApp.domain.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

    Optional<Applicant> findByEmail(String email);

    List<Applicant> findByNameContaining(String name);

    List<Applicant> findApplicantByCourse(Long courseId);

}
