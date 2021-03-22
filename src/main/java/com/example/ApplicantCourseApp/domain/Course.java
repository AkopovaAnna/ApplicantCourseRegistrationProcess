package com.example.ApplicantCourseApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "applicants"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", updatable = false)
    private LocalDate startDate;

    @Column(name = "end_date", updatable = false)
    private LocalDate endDate;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "course",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Applicant> applicants = new ArrayList<>();

    public Course() {

    }

    public Course(
            Long id, String name, LocalDate startDate, LocalDate endDate,
            String teacherName, String description, List<Applicant> applicants) {
        this.courseId = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacherName = teacherName;
        this.description = description;
        this.applicants = applicants;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }
}
