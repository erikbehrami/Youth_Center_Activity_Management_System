package model;



public class Schedules {
    private int id;
    private int id_courses;
    private String day;
    private String timeStart;
    private String timeEnd;

    private Schedules(int id, int id_courses, String day, String timeStart, String timeEnd) {
        this.id = id;
        this.id_courses = id_courses;
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getId() {
        return this.id;
    }

    public int getId_courses() {
        return this.id_courses;
    }

    public String getDay() {
        return this.day;
    }

    public String getTimeStart() {
        return this.timeStart;
    }

    public String getTimeEnd() {
        return this.timeEnd;
    }
}
