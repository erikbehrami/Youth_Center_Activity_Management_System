package model.dto.schedule;

import java.sql.Time;

public class UpdateScheduleDto {
    private String day;
    private Time timeStart;
    private Time timeEnd;

    public UpdateScheduleDto(String day, Time timeStart, Time timeEnd) {
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getDay() { return this.day; }

    public Time getTimeStart() {
        return this.timeStart;
    }

    public Time getTimeEnd() {
        return this.timeEnd;
    }

    public void setDay(String day) { this.day = day; }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }
}

