package services;

import model.LectureRooms;
import model.Professors;
import model.Students;
import model.dto.course.CreateCourseDto;
import repository.CourseRepository;
import repository.LectureRoomsRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class RegisterCourseService {

    private final ProfessorsRepository professorRepository = new ProfessorsRepository();
    private final LectureRoomsRepository lectureRoomsRepository = new LectureRoomsRepository();
    private final CourseRepository coursesRepository = new CourseRepository();

    private final ArrayList<Professors> professorList;
    private final ArrayList<LectureRooms> roomList;

    public RegisterCourseService() {
        professorList = professorRepository.getAll();
        roomList = lectureRoomsRepository.getAll();
    }

    public int getProfessorIdByIndex(int index) {
        if (index >= 0 && index < professorList.size()) {
            return professorList.get(index).getId();
        }
        return -1;
    }

    public int getLectureRoomIdByIndex(int index) {
        if (index >= 0 && index < roomList.size()) {
            return roomList.get(index).getId();
        }
        return -1;
    }

    public String saveCourse(CreateCourseDto courseDto) {
        if (courseDto.getProfessorId() == -1 || courseDto.getLectureRoomId() == -1) {
            return "Error: Invalid professor or room.";
        }

        int courseId = coursesRepository.create(courseDto).getId();
        if (courseId != -1) {
            return "Course saved successfully: ID " + courseId;
        } else {
            return "Error: Failed to save course.";
        }
    }

    public ArrayList<Professors> getAllProfessors() {
        return professorRepository.getAll();
    }

    public ArrayList<LectureRooms> getAllLectureRooms() {
        return lectureRoomsRepository.getAll();
    }

    public boolean canRegisterMoreCourses(int professorId) {
        int currentCoursesCount = coursesRepository.getAll(professorId).size();
        int maxCourses = professorRepository.getMaxCourses(professorId);
        System.out.println("Current number of courses for Professor ID " + professorId + ": " + currentCoursesCount);
        return currentCoursesCount < maxCourses;
    }

    public boolean isRoomAvailable(int lectureRoomId, String day, LocalTime timeStart, LocalTime timeEnd) {
        return coursesRepository.isRoomAvailable(lectureRoomId, day, timeStart, timeEnd);
    }
}