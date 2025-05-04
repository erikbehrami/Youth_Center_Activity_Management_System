package utils;

interface OverallPages {
    String PROFILE = "/fxml/profile.fxml";
    String LOGO = "/images/youthcenter_logo.png";
}

interface AdminPages {
    String ADMIN_DASHBOARD = "/fxml/adminDashboard.fxml";
    String ADMIN_STUDENTS = "/fxml/adminStudents.fxml";
    String ADMIN_TEACHERS = "/fxml/adminTeachers.fxml";
}

interface Modes {
    String DARK_MODE = "/css/modes/darkMode.css";
    String LIGHT_MODE = "/css/modes/lightMode.css";

}

interface AccountPages {
    String SIGN_IN = "/fxml/signin.fxml";
    String SIGN_UP = "/fxml/signup.fxml";
}

public class Navigator implements OverallPages, AdminPages, AccountPages, Modes {

}
