package com.example.ApplicantCourseApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApplicantCourseAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicantCourseAppApplication.class, args);
	}

}
