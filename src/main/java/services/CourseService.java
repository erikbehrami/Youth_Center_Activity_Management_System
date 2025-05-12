package services;

import model.Courses;
import model.LectureRooms;
import model.Professors;
import model.dto.course.CreateCourseDto;
import model.dto.course.UpdateCourseDto;
import repository.CourseRepository;
import repository.LectureRoomsRepository;
import repository.ProfessorsRepository;

import java.util.ArrayList;

public class CourseService {
    private static final ProfessorsRepository professorRepository = new ProfessorsRepository();
    private static final LectureRoomsRepository lectureRoomsRepository = new LectureRoomsRepository();
    private static final CourseRepository coursesRepository = new CourseRepository();

    private static final ArrayList<Professors> professorList = professorRepository.getAll();
    private static final ArrayList<LectureRooms> roomList = lectureRoomsRepository.getAll();
    private static final ArrayList<Courses> coursesList = coursesRepository.getAll();

    private static int selectedCourseId = -1;

    private CourseService() {}

    public static int getProfessorIdByIndex(int index) {
        return (index >= 0 && index < professorList.size()) ? professorList.get(index).getId() : -1;
    }

    public static int getLectureRoomIdByIndex(int index) {
        return (index >= 0 && index < roomList.size()) ? roomList.get(index).getId() : -1;
    }

    public static Courses getCourse(int courseId){
        return coursesRepository.getById(courseId);
    }

    public static ArrayList<Professors> getAllProfessors() {
        return professorList;
    }

    public static ArrayList<LectureRooms> getAllLectureRooms() {
        return roomList;
    }

    public static ArrayList<Courses> getAllCourses() {
        return coursesList;
    }

    public static LectureRooms getLectureRoom(int lectureRoomId){
        return lectureRoomsRepository.getById(lectureRoomId);
    }

    public static Professors getProfessor(int professoerId){
        return professorRepository.getById(professoerId);
    }

    public static String saveCourse(CreateCourseDto dto) {
        if (dto.getProfessorId() == -1 || dto.getLectureRoomId() == -1)
            return "Error: Invalid professor or room.";
        coursesRepository.create(dto);
        return "Course saved successfully: " + dto.getName();
    }

    public static String updateCourse(UpdateCourseDto dto) {
        if (dto.getProfessorId() == -1 || dto.getLectureRoomId() == -1)
            return "Error: Invalid professor or room.";
        Courses updated = coursesRepository.update(dto);
        return updated != null ? "Course updated successfully." : "Failed to update the course.";
    }

    public static void setSelectedCourseId(int id) {
        selectedCourseId = id;
    }

    public static int getSelectedCourseId() {
        return selectedCourseId;
    }

}
