# Applicant Course management system

ApplicantCourseManagement system is a Java Spring Hibernate REST project, which allows adding courses
 and applicants can enroll one of the courses
 
There is a scheduling task, from Spring, which is working to update Applicant status

The generated pdf files are saved to resources folder

## Usage

The user admin could create courses after the login
The authentication done by Spring Security, using jwt
user admin info: 
 
* email: userAdmin@gmail.com
* password: 123456

#####API Endpoints

* POST /users/token = user login

#####Course Endpoints

* POST /courses => add course, with authentication
* GET  /courses/{id} => get course by id
* GET  /courses/ => get all courses
* GET  /courses?name=java => get course name
* PUT  /courses/{id} => update course, with authentication 
* DELETE /courses/{id} => delete course, with auth

#####Applicant Endpoints

* POST /courses/{id}/applicants => applicant enrolment by course
* POST /courses/{id}/applicants/{id} => get applicant by id
* POST /courses/{id}/applicants/{id} => update applicant, with authentication
* DELETE /courses/{id}/applicants/{id} => delete an applicant, with auth
* GET /courses/{id}/applicants?filter=name:{name} => search by name
* GET /courses/{id}/applicants?filter=email:{email} => search by email
* PUT /courses/{id}/applicants/{id} => update applicant, with auth

#####Download Zip Endpoints

* GET /downloadzip => will download zip with auth

#####Generate pdf Endpoints

* GET /courses/{courseId}/applicants/{applicantId}/pdfreport => will generate pdf, by course and applicant


#####Swagger Endpoint

* GET /swagger-ui/index.html
