package com.example.ApplicantCourseApp.service;

import com.example.ApplicantCourseApp.domain.Course;
import com.example.ApplicantCourseApp.domain.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@Component
public class ScheduledTasksService {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksService.class);

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private GenerateCertificatePdfService pdfService;


    @Scheduled(cron = "0 1 1 * * ?")
    public void updateApplicantStatusProgress() {
        log.info("=============Start job to update applicant status by start of course=============");
        List<Course> courses = courseService.getAllCourses();

        courses
                .stream()
                .filter(course -> course.getStartDate().equals(LocalDate.now()))
                .forEach(course -> {
                    log.info(String.format("Updating applicant for course[name=%s]", course.getName()));
                    course
                            .getApplicants()
                            .stream()
                            .forEach(applicant -> {
                                applicant.setStatus(Status.IN_PROGRESS);
                                applicantService.updateApplicant(applicant);
                            });
                });
        log.info("=============Ended job to update applicant status=============");
    }

    @Scheduled(cron = " 0 30 22 * * ?")
    public void updateApplicantStatusEnd() {
        log.info("=============Start job to update applicant status by end of course=============");
        List<Course> courses = courseService.getAllCourses();

        courses
                .stream()
                .filter(course -> course.getEndDate().equals(LocalDate.now()))
                .forEach(course -> {
                    log.info(String.format("Updating applicant for course[name=%s]", course.getName()));
                    course
                            .getApplicants()
                            .stream()
                            .forEach(applicant -> {
                                applicant.setStatus(Status.COMPLETED);
                                applicantService.updateApplicant(applicant);
                                pdfService.pdfCreation(applicant.getId(), course.getCourseId());
                            });
                });
        log.info("=============Ended job to update applicant status=============");
    }
}
