package com.example.ApplicantCourseApp.service;

import com.example.ApplicantCourseApp.domain.Applicant;
import com.example.ApplicantCourseApp.exception.ApplicantCourseIllegalStateException;
import com.example.ApplicantCourseApp.exception.CustomValidationException;
import com.example.ApplicantCourseApp.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.ApplicantCourseApp.validator.EmailValidator.validateEmail;

@Service
@Qualifier(value = "applicantService")
public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    private final CourseService courseService;

    @Autowired
    public ApplicantService(ApplicantRepository applicantRepository,
                            CourseService courseService
    ) {
        this.applicantRepository = applicantRepository;
        this.courseService = courseService;
    }

    public Applicant getApplicantById(Long id) throws ResourceNotFoundException {
        return applicantRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("applicant with %s id not found", id)));
    }

    public List<Applicant> findAllApplicants() {
        return applicantRepository.findAll();
    }

    public Optional<Applicant> getByEmail(String email) {
        return applicantRepository.findByEmail(email);
    }

    public List<Applicant> getByName(String name) {
        return applicantRepository.findByNameContaining(name);
    }

    public List<Applicant> getByCourseId(Long courseId) {
        return applicantRepository.findApplicantByCourse(courseId);
    }

    public Applicant createApplicant(Applicant applicant, Long courseId) throws ResourceNotFoundException {
        validateApplicant(applicant);
        applicant.setCourse(courseService.loadCourse(courseId));
        return applicantRepository.save(applicant);
    }

    public Applicant updateApplicant(Applicant applicant) {
        validateEmail(applicant.getEmail());
        applicantRepository
                .findById(applicant.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("applicant with %s id not found", applicant.getId()))
                );
        applicantRepository.save(applicant);
        return applicant;
    }

    public void deleteApplicant(Long applicantId) {

        Optional<Applicant> applicant = applicantRepository.findById(applicantId);
        if (!applicant.isPresent()) {
            throw new ApplicantCourseIllegalStateException("Failed to remove Course. Invalid CourseId :: " + applicantId);
        }
        applicantRepository.deleteById(applicantId);

    }

    public List<Applicant> getApplicantByQuery(String search) {
        String[] keyValue = search.split(":");
        if (keyValue.length < 2) {
            return Collections.emptyList();//wrong filter
        } else {
            String key = keyValue[0];
            String value = keyValue[1];

            switch (key) {
                case "name":
                    return getByName(value);
                case "email":
                    if (getByEmail(value).isPresent())
                        return Collections.singletonList(getByEmail(value).get());
                case "courseId":
                    return getByCourse(value);
                default:
                    return Collections.emptyList();
            }
        }
    }

    public List<Applicant> getByCourse(String id) {
        try {
            return getByCourseId(Long.valueOf(id));
        } catch (NumberFormatException ex) {
            return Collections.emptyList();
        }
    }

    public void validateApplicant(Applicant applicant) {
        validateEmail(applicant.getEmail());
        validateUniqueness(applicant.getEmail());
    }

    public void validateUniqueness(String email) {
        Optional<Applicant> applicant = getByEmail(email);
        if (applicant.isPresent()) {
            throw new CustomValidationException("email already exists");
        }
    }
}
