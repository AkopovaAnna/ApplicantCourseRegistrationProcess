package com.example.ApplicantCourseApp.service;

import com.example.ApplicantCourseApp.domain.Applicant;
import com.example.ApplicantCourseApp.domain.Course;
import com.example.ApplicantCourseApp.exception.DocumentCreationException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;

@Service
public class GenerateCertificatePdfService {

    @Autowired
    private ApplicantService applicantService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ResourceLoader resourceLoader;

    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 11,
            Font.NORMAL);

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void addContentToPDf(File pdf, Applicant applicant, Course course) {
        try {
            Document document = new Document();

            Paragraph preface1 = new Paragraph();

            Paragraph certificate = new Paragraph("CERTIFICATE");
            preface1.add(certificate);
            addEmptyLine(preface1, 1);

            Paragraph courseName = new Paragraph(course.getName().toUpperCase());
            preface1.add(courseName);
            addEmptyLine(preface1, 1);

            Paragraph description = new Paragraph(course.getDescription(), smallBold);
            preface1.add(description);

            preface1.setAlignment(Element.ALIGN_CENTER);
            addEmptyLine(preface1, 1);

            Paragraph preface2 = new Paragraph();

            preface2.add(new Paragraph("Applicant name - " + applicant.getName()));
            addEmptyLine(preface2, 1);
            preface2.add(new Paragraph("Teacher name - " + course.getTeacherName()));
            addEmptyLine(preface2, 1);
            preface2.add(new Paragraph(findDiff(course.getStartDate(), course.getEndDate())));

            PdfWriter.getInstance(document, new FileOutputStream(pdf));

            document.open();
            document.add(preface1);
            document.add(preface2);
            document.close();

        } catch (DocumentException | FileNotFoundException e) {
            throw new DocumentCreationException(e.getMessage());
        }
    }

    private String findDiff(LocalDate start, LocalDate end) {

        Period diff = Period.between(start, end);
        return ("Course duration - " + diff.getMonths() + " months");

    }


    public File pdfCreation(Long courseId, Long applicantId) {
        Applicant applicant = applicantService.getApplicantById(applicantId);
        Course course = courseService.getCourseById(courseId);


        String fileName = "certifications/" + applicant.getName() + "_" + courseId + ".pdf";
        File pdf = new File("src/main/resources/" + fileName);
//        Resource fileResource = resourceLoader.getResource("classpath:" + fileName);

        try {
            if (!pdf.exists()) {
                if (pdf.createNewFile()) {
                    System.out.println("=======================");
                    addContentToPDf(pdf, applicant, course);
                }
            }
            return pdf;
        } catch (IOException e) {
            throw new DocumentCreationException(e.getMessage());
        }
    }
}
