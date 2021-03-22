package com.example.ApplicantCourseApp.controller;

import com.example.ApplicantCourseApp.domain.Applicant;
import com.example.ApplicantCourseApp.security.SecurityContextAccessor;
import com.example.ApplicantCourseApp.service.ApplicantService;
import com.example.ApplicantCourseApp.system.UserPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses/{courseId}/applicants")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private UserPermissionEvaluator permissionEvaluator;

    @Autowired
    private SecurityContextAccessor contextAccessor;

    @GetMapping("/{id}")
    public Applicant getById(@PathVariable("id") final Long id) {
        return applicantService.getApplicantById(id);
    }

    @PostMapping("")
    public ResponseEntity createApplicant(@PathVariable("courseId") final Long courseId, @RequestBody Applicant applicant) {

        Applicant applicantSummary = applicantService.createApplicant(applicant, courseId);
        return new ResponseEntity<>(applicantSummary, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Applicant updateApplicant(@PathVariable("id") final Long id,
                                     @RequestBody Applicant applicant) {
        permissionEvaluator.checkPermission(contextAccessor.getUserDetails());
        applicant.setId(id);
        return applicantService.updateApplicant(applicant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteApplicant(@PathVariable("id") final Long id) {
        permissionEvaluator.checkPermission(contextAccessor.getUserDetails());
        applicantService.deleteApplicant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "")
    public List<Applicant> search(@RequestParam(value = "filter", required = false) String search) {
        if (search != null) {
            return applicantService.getApplicantByQuery(search);
        } else {
            permissionEvaluator.checkPermission(contextAccessor.getUserDetails());
            return applicantService.findAllApplicants();
        }

    }
}
