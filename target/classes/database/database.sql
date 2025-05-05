
CREATE SEQUENCE professors_id_seq START 1000 INCREMENT 1;
CREATE SEQUENCE admins_id_seq START 1 INCREMENT 1;
CREATE SEQUENCE students_id_seq START 220000 INCREMENT 1;
CREATE SEQUENCE lectureRooms_id_seq START 75000 INCREMENT 1;
CREATE SEQUENCE courses_id_seq START 500 INCREMENT 1;
CREATE SEQUENCE enrolled_id_seq START 50000 INCREMENT 1;
CREATE SEQUENCE schedules_id_seq START 100000 INCREMENT 1;
CREATE SEQUENCE requests_id_seq START 500000 INCREMENT 1;

create table admins(
                       id integer default nextval('admins_id_seq') Primary Key ,
                       username varchar(255),
                       password varchar(255),
                       name varchar(255),
                       surname varchar(255),
                       email varchar(255),
                       birthdate date,
                       phoneNumber varchar(15),
                       address varchar(255),
                       gender varchar(20)
);

create table professors(
                           id integer default nextval('professors_id_seq') Primary Key ,
                           username varchar(255),
                           password varchar(255),
                           verified boolean DEFAULT false,
                           name varchar(255),
                           surname varchar(255),
                           email varchar(255),
                           birthdate date,
                           phoneNumber varchar(15),
                           address varchar(255),
                           gender varchar(20),
                           biographicalInfo text

);

create table students(
                         id integer default nextval('students_id_seq') Primary Key ,
                         username varchar(255),
                         password varchar(255),
                         name varchar(255),
                         surname varchar(255),
                         email varchar(255),
                         birthdate date,
                         phoneNumber varchar(15),
                         address varchar(255),
                         gender varchar(20),
                         biographicalInfo text

);

create table lectureRooms(
                             id integer default nextval('lectureRooms_id_seq') Primary Key ,
                             name varchar(255),
                             floor integer,
                             capacity integer
);

create table courses(
                        id integer default nextval('courses_id_seq') Primary Key ,
                        name varchar(255),
                        category varchar(255),
                        id_Professor integer,
                        id_lectureRooms integer,
                        totalNum integer,
                        studentsEnrolled integer,
                        dateStarted date,
                        dateEnding date,
                        FOREIGN KEY (id_Professor) REFERENCES professors(id) ON DELETE SET NULL ON UPDATE CASCADE,
                        FOREIGN KEY (id_lectureRooms) REFERENCES lectureRooms(id) ON DELETE SET NULL ON UPDATE CASCADE
);

create table enrolled(
                         id integer default nextval('enrolled_id_seq') Primary Key ,
                         id_Professor integer,
                         id_Student integer,
                         id_Course integer,
                         FOREIGN KEY (id_Professor) REFERENCES professors(id) ON DELETE SET NULL ON UPDATE CASCADE,
                         FOREIGN KEY (id_Student) REFERENCES students(id) ON DELETE cascade ON UPDATE CASCADE,
                         FOREIGN KEY (id_Course) REFERENCES courses(id) ON DELETE SET NULL ON UPDATE CASCADE

);

create table schedules (
                           id integer default nextval('schedules_id_seq') Primary Key ,
                           id_Courses integer,
                           day varchar(10),
                           timeStart varchar(5),
                           timeEnd varchar(5),
                           FOREIGN KEY (id_Courses) REFERENCES courses(id) ON DELETE SET NULL ON UPDATE CASCADE
);
create table requests(
                         id integer default nextval('requests_id_seq') Primary Key ,
                         id_Student integer,
                         id_Professor integer,
                         id_Course integer,
                         FOREIGN KEY (id_Student) REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (id_Professor) REFERENCES professors(id) ON DELETE SET NULL ON UPDATE CASCADE,
                         FOREIGN KEY (id_Course) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE student_messages (
                                  id SERIAL PRIMARY KEY,
                                  id_Student INTEGER,
                                  id_Professor INTEGER,
                                  message TEXT,
                                  sentAt DATE,
                                  FOREIGN KEY (id_Student) REFERENCES students(id),
                                  FOREIGN KEY (id_Professor) REFERENCES professors(id)
);

CREATE TABLE student_badges (
                                id SERIAL PRIMARY KEY,
                                id_Student INTEGER,
                                badgeName VARCHAR(100),
                                description TEXT,
                                awardedAt DATE,
                                FOREIGN KEY (id_Student) REFERENCES students(id)
);


CREATE TABLE feedback (
                          id SERIAL PRIMARY KEY,
                          userType VARCHAR(20),
                          message TEXT,
                          submittedAt DATE

);

CREATE TABLE issues (
                        id SERIAL PRIMARY KEY,
                        professorId INTEGER,
                        studentId INTEGER,
                        userType VARCHAR(20),
                        subject VARCHAR(255),
                        description TEXT,
                        createdAt DATE,
                        CHECK (
                            (professorID IS NOT NULL AND studentId IS NULL)
                                OR
                            (studentId IS NOT NULL AND professorID IS NULL)
                            ),
                        FOREIGN KEY (professorId) REFERENCES professors(id),
                        FOREIGN KEY (studentId) REFERENCES students(id)
);

CREATE TABLE contact_messages (
                                  id SERIAL PRIMARY KEY,
                                  name VARCHAR(100),
                                  email VARCHAR(255),
                                  message TEXT,
                                  sentAt DATE
);


CREATE TABLE faqs (
                      id SERIAL PRIMARY KEY,
                      question TEXT,
                      answer TEXT
);

CREATE TABLE login_logs (
                            id SERIAL PRIMARY KEY,
                            adminId INTEGER,
                            professorId INTEGER,
                            studentId INTEGER,
                            userType VARCHAR(20),
                            loginTime DATE,

                            CHECK (
                                (adminId IS NOT NULL AND professorID IS NULL AND studentId IS NULL)
                                    OR
                                (professorID IS NOT NULL AND adminId IS NULL AND studentId IS NULL)
                                    OR
                                (studentId IS NOT NULL AND adminId IS NULL AND professorID IS NULL)
                                ),
                            FOREIGN KEY (adminId) REFERENCES admins(id),
                            FOREIGN KEY (professorId) REFERENCES professors(id),
                            FOREIGN KEY (studentId) REFERENCES students(id)
);


CREATE TABLE professor_specializations (
                                           id SERIAL PRIMARY KEY,
                                           id_Professor INTEGER,
                                           specialization VARCHAR(100),
                                           FOREIGN KEY (id_Professor) REFERENCES professors(id)
);


CREATE TABLE advertisements (
                                id SERIAL PRIMARY KEY,
                                sponsorName VARCHAR(255),
                                adTitle VARCHAR(255),
                                adImageUrl TEXT

);


CREATE TABLE course_enrollment_logs (
                                        id SERIAL PRIMARY KEY,
                                        student_id INTEGER,
                                        course_id INTEGER,
                                        action VARCHAR(50),
                                        action_time DATE,
                                        FOREIGN KEY (student_id) REFERENCES students(id),
                                        FOREIGN KEY (course_id) REFERENCES courses(id)
);



