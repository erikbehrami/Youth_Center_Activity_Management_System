package model;



public class Schedules {
    private int id;
    private int id_student;
    private String day;
    private String timeStart;
    private String timeEnd;

    public Schedules(int id, int id_student, String day, String timeStart, String timeEnd) {
        this.id = id;
        this.id_student = id_student;
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getId() {
        return this.id;
    }

    public int getId_student() {
        return this.id_student;
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
