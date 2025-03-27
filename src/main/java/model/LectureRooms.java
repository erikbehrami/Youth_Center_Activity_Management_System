package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LectureRooms {
    private int id;
    private String name;
    private int floor;
    private int capacity;

    private LectureRooms(int id, String name, int floor, int capacity) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.capacity = capacity;
    }

    public static LectureRooms getInstance(ResultSet resultSet) throws SQLException {
      int id=resultSet.getInt("id");
      String name=resultSet.getString("name");
      int floor=resultSet.getInt("floor");
      int capacity=resultSet.getInt("capacity");

      return new LectureRooms(id, name, floor, capacity);

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
