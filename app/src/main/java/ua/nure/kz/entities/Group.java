package ua.nure.kz.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Group {
    private long id;
    private String name;

    public Group(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Group(String name) {
        this(0, name);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Group fromResultSet(ResultSet result) throws SQLException {
        long id = result.getLong("id");
        String name = result.getString("name");

        return new Group(id, name);
    }
}
