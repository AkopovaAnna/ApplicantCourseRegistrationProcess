create schema coursesDB;

create table applicants
(
	applicant_id int auto_increment,
	name varchar(255) not null,
	email varchar(255) not null,
	phone_number varchar(255) not null,
	address varchar(255) not null,
	status varchar(50) not null,
	constraint applicants_pk
		primary key (applicant_id)
);

create unique index applicants_email_uindex
	on applicants (email);

create table courses
(
	course_id int auto_increment,
	name varchar(255) not null,
	start_date date not null,
	end_date date not null,
	teacher_name varchar(255) not null,
	description varchar(255) not null,
	constraint courses_pk
		primary key (course_id)
);

create table users
(
	id int auto_increment,
	email varchar(255) not null,
	password varchar(1024) not null,
	is_admin tinyint not null,
	constraint users_pk
		primary key (id)
);

create unique index users_email_uindex
	on users (email);


alter table applicants
	add constraint applicants_courses_course_id_fk
		foreign key (applicant_id) references courses (course_id)
			on delete cascade;



@Anna , here write
insert into user (1, admin, admin, 123121232132131212....)
