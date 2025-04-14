package model.dto.schedule;

import java.sql.Time;

public class CreateScheduleDto {
    private int courseID;
    private String day;
    private Time timeStart;
    private Time timeEnd;

    public CreateScheduleDto(int courseID, String day, Time timeStart, Time timeEnd) {
        this.courseID = courseID;
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public String getDay() {
        return this.day;
    }

    public Time getTimeStart() {
        return this.timeStart;
    }

    public Time getTimeEnd() {
        return this.timeEnd;
    }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public void setDay(String day) { this.day = day; }

    public void setTimeStart(Time timeStart) { this.timeStart = timeStart; }

    public void setTimeEnd(Time timeEnd) { this.timeEnd = timeEnd; }
}

