package ua.nure.kz.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private long id;
    private String login;
    private String password;
    private String fullName;
    private Role role;

    public User(long id, String login, String password, String fullName, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public User(String login, String password, String fullName, Role role) {
        this(0, login, password, fullName, role);
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static User fromResultSet(ResultSet result) throws SQLException {
        long id = result.getLong("id");
        String login = result.getString("login");
        String password = result.getString("password");
        String fullName = result.getString("fullName");
        User.Role role = User.Role.valueOf(result.getString("role"));

        return new User(id, login, password, fullName, role);
    }

    public enum Role {
        USER,
        ADMIN,
    }
}
