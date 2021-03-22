create table courses
(
	course_id int auto_increment,
	name varchar(50) not null,
	start_date date not null,
	end_date date not null,
	teacher_name varchar(50) not null,
	description varchar(255) null,
	constraint courses_pk
		primary key (course_id)
);

create unique index courses_name_uindex
	on courses (name);

create table applicants
(
	applicant_id int auto_increment,
	name varchar(50) not null,
	email varchar(255) not null,
	phone_number varchar(100) not null,
	address varchar(255) null,
	status varchar(20) not null,
	course_id int not null,
	constraint applicants_pk
		primary key (applicant_id),
	constraint applicants_courses_course_id_fk
		foreign key (course_id) references courses (course_id)
			on delete cascade
);

create unique index applicants_email_uindex
	on applicants (email);

create table users
(
	id int auto_increment,
	email varchar(100) not null,
	password varchar(255) not null,
	is_admin BOOLEAN not null,
	constraint users_pk
		primary key (id)
);

create unique index users_email_uindex
	on users (email);

INSERT INTO users (email, password, is_admin)
VALUES ('userAdmin@gmail.com', '$2a$10$Tz946GwRon9IHq.kARodMeI7wGons4bdpmUfQgdYzdpjvdPwkUlBC', 1);

