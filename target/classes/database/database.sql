
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
                         FOREIGN KEY (id_Course) REFERENCES courses(id) ON DELETE SET NULL ON UPDATE CASCADE,

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