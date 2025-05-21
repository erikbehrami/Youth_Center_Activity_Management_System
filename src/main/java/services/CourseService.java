package services;

import model.Courses;
import model.LectureRooms;
import model.Professors;
import model.dto.course.CreateCourseDto;
import model.dto.course.UpdateCourseDto;
import repository.CourseRepository;
import repository.LectureRoomsRepository;
import repository.ProfessorsRepository;

import java.util.List;

public class CourseService {
    private static final ProfessorsRepository professorRepo = new ProfessorsRepository();
    private static final LectureRoomsRepository roomRepo = new LectureRoomsRepository();
    private static final CourseRepository courseRepo = new CourseRepository();

    private static int selectedCourseId = -1;

    private CourseService() {}

    public static List<Professors> getAllProfessors() {
        return professorRepo.getAll();
    }

    public static List<LectureRooms> getAllLectureRooms() {
        return roomRepo.getAll();
    }

    public static Courses getCourse(int courseId) {
        return courseRepo.getById(courseId);
    }

    public static LectureRooms getLectureRoom(int roomId) {
        return roomRepo.getById(roomId);
    }

    public static Professors getProfessor(int professorId) {
        return professorRepo.getById(professorId);
    }

    public static int getProfessorIdByIndex(int index) {
        List<Professors> professors = getAllProfessors();
        return (index >= 0 && index < professors.size()) ? professors.get(index).getId() : -1;
    }

    public static int getLectureRoomIdByIndex(int index) {
        List<LectureRooms> rooms = getAllLectureRooms();
        return (index >= 0 && index < rooms.size()) ? rooms.get(index).getId() : -1;
    }

    public static String saveCourse(CreateCourseDto dto) {
        if (dto.getProfessorId() == -1 || dto.getLectureRoomId() == -1) {
            return "Error: Invalid professor or room.";
        }
        courseRepo.create(dto);
        return "Course saved successfully: " + dto.getName();
    }

    public static String updateCourse(UpdateCourseDto dto) {
        if (dto.getProfessorId() == -1 || dto.getLectureRoomId() == -1) {
            return "Error: Invalid professor or room.";
        }
        Courses updated = courseRepo.update(dto);
        return (updated != null) ? "Course updated successfully." : "Failed to update the course.";
    }

    public static void setSelectedCourseId(int id) {
        selectedCourseId = id;
    }

    public static int getSelectedCourseId() {
        return selectedCourseId;
    }
}
