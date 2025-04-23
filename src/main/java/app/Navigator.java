package app;

interface OverallPages {
    public static final String PROFILE = "/fxml/profile.fxml";
    public static final String LOGO = "/images/youthcenter_logo.png";
}

interface AdminPages {
    public static final String ADMIN_DASHBOARD = "/fxml/adminDashboard.fxml";
    public static final String ADMIN_STUDENTS = "/fxml/adminStudents.fxml";
    public static final String ADMIN_TEACHERS = "/fxml/adminTeachers.fxml";
}

public class Navigator implements OverallPages, AdminPages {

}
