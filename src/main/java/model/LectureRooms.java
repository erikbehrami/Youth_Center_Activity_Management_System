package model;

public class LectureRooms {
    private int id;
    private String name;
    private int floor;
    private int capacity;

    public LectureRooms(int id, String name, int floor, int capacity) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.capacity = capacity;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getFloor() {
        return this.floor;
    }

    public int getCapacity() {
        return this.capacity;
    }
}
