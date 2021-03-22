package com.example.ApplicantCourseApp.controller;

import com.example.ApplicantCourseApp.service.ApplicantService;
import com.example.ApplicantCourseApp.service.GenerateCertificatePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/courses/{courseId}/applicants/{applicantId}")
public class PdfController {
    @Autowired
    ApplicantService applicantService;

    @Autowired
    GenerateCertificatePdfService pdfService;

    @GetMapping(value = "/pdfreport", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport1(@PathVariable("courseId") final Long courseId,
                                                             @PathVariable("applicantId") final Long applicantId) throws IOException {

        File pdf = pdfService.pdfCreation(courseId, applicantId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=certificate.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(new FileInputStream(pdf)));
    }
}
