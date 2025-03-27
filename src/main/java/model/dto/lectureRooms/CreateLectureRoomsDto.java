package model.dto.lectureRooms;

public class CreateLectureRoomsDto {

    private String name;
    private int floor;
    private int capacity;

    public CreateLectureRoomsDto(String name, int capacity, int floor) {
        this.name = name;
        this.capacity = capacity;
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
