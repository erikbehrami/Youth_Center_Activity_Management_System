package utils;

interface OverallPages {
    String PROFILE = "/fxml/profile.fxml";
    String LOGO = "/images/youthcenter_logo.png";
    String HOME = "/fxml/home.fxml";
    String INFO = "/fxml/info.fxml";
    String REGISTER_COURSE = "/fxml/registerCourse.fxml";
    String UPDATE_COURSE = "/fxml/updateCourse.fxml";

    String REGISTER_STUDENT = "/fxml/registerStudent.fxml";
}

interface AdminPages {
    String ADMIN_DASHBOARD = "/fxml/adminDashboard.fxml";
    String ADMIN_STUDENTS = "/fxml/adminStudents.fxml";
    String ADMIN_TEACHERS = "/fxml/adminProfessors.fxml";
    String ADMIN_COURSES = "/fxml/adminCourses.fxml";

}

interface ProfPages {
    String PROF_DASHBOARD = "/fxml/professorDashboard.fxml";
    String PROF_COURSES = "/fxml/profCourses.fxml";
    String PROF_STUDENTS = "/fxml/profStudents.fxml";
    String PROF_MESSAGES = "/fxml/profMessages.fxml";
}

interface Modes {
    String DARK_MODE = "/css/modes/darkMode.css";

}

interface AccountPages {
    String SIGN_IN = "/fxml/signin.fxml";
    String SIGN_UP = "/fxml/signup.fxml";
}

interface StudentPages {
    String STUDENT_PROFILE = "/fxml/studentHome.fxml";
}

public class Navigator implements OverallPages, AdminPages, AccountPages, Modes, ProfPages, StudentPages {

}
