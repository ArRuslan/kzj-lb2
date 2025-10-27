package ua.nure.kz;

import ua.nure.kz.entities.Group;
import ua.nure.kz.entities.User;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class DatabaseManager {
    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException var1) {
            throw new RuntimeException("Can't register driver!");
        }
    }

    private static DatabaseManager instance;

    private final String connectionUrl;
    private final String user;
    private final String password;

    private DatabaseManager() {
        Properties props = new Properties();
        try {
            props.load(DatabaseManager.class.getClassLoader().getResourceAsStream("database.properties"));
        } catch(IOException exc) {
            throw new RuntimeException("Failed to read database properties!", exc);
        }

        connectionUrl = props.getProperty("db_connection_string");
        user = props.getProperty("db_user");
        password = props.getProperty("db_password");
    }

    public static synchronized DatabaseManager getInstance() {
        if(instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, user, password);
    }

    public List<User> getUsers(int page, int pageSize) throws SQLException {
        List<User> users = new ArrayList<>();

        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.GET_USERS_PAGINATED);
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);

            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                users.add(User.fromResultSet(result));
            }
        }

        return users;
    }

    public long getUsersCount() throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();

            ResultSet result = stmt.executeQuery(Queries.GET_USER_COUNT);
            result.next();

            return result.getLong(0);
        }
    }

    public User getUser(long id) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.GET_USER_BY_ID);
            stmt.setLong(1, id);

            ResultSet result = stmt.executeQuery();
            if(!result.next()) {
                return null;
            }

            return User.fromResultSet(result);
        }
    }

    public User getUser(String login) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.GET_USER_BY_LOGIN);
            stmt.setString(1, login);

            ResultSet result = stmt.executeQuery();
            if(!result.next()) {
                return null;
            }

            return User.fromResultSet(result);
        }
    }

    public User createUser(User user) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getRole().toString());

            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Failed to create user (no rows affected)!");
            }

            ResultSet result = stmt.getGeneratedKeys();
            if(!result.next()) {
                throw new SQLException("Failed to create user (no id)!");
            }

            long id = result.getLong(1);
            return new User(id, user.getLogin(), user.getPassword(), user.getFullName(), user.getRole());
        }
    }

    public void updateUser(User user) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.UPDATE_USER);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getRole().toString());
            stmt.setLong(5, user.getId());

            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Failed to update user (no rows affected)!");
            }
        }
    }

    public void deleteUser(User user) throws SQLException {
        deleteUser(user.getId());
    }

    public void deleteUser(long userId) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.DELETE_USER);
            stmt.setLong(1, userId);
            stmt.execute();
        }
    }

    public List<Group> getGroups(int page, int pageSize) throws SQLException {
        List<Group> groups = new ArrayList<>();

        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.GET_GROUPS_PAGINATED);
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (page - 1) * pageSize);

            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                groups.add(Group.fromResultSet(result));
            }
        }

        return groups;
    }

    public long getGroupsCount() throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();

            ResultSet result = stmt.executeQuery(Queries.GET_GROUP_COUNT);
            result.next();

            return result.getLong(0);
        }
    }

    public Group getGroup(long id) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.GET_GROUP_BY_ID);
            stmt.setLong(1, id);

            ResultSet result = stmt.executeQuery();
            if(!result.next()) {
                return null;
            }

            return Group.fromResultSet(result);
        }
    }

    public Group createGroup(Group group) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.CREATE_GROUP, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, group.getName());

            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Failed to create group (no rows affected)!");
            }

            ResultSet result = stmt.getGeneratedKeys();
            if(!result.next()) {
                throw new SQLException("Failed to create group (no id)!");
            }

            long id = result.getLong(1);
            return new Group(id, group.getName());
        }
    }

    public void updateGroup(Group group) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.UPDATE_GROUP);
            stmt.setString(1, group.getName());
            stmt.setLong(2, group.getId());

            if (stmt.executeUpdate() == 0) {
                throw new SQLException("Failed to update group (no rows affected)!");
            }
        }
    }

    public void deleteGroup(Group group) throws SQLException {
        deleteGroup(group.getId());
    }

    public void deleteGroup(long groupId) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.DELETE_GROUP);
            stmt.setLong(1, groupId);
            stmt.execute();
        }
    }

    public Map<Long, List<Group>> getUserGroups(List<Long> userIds) throws SQLException {
        Map<Long, List<Group>> groups = new HashMap<>();

        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(Queries.GET_GROUPS_BY_USERS);
            stmt.setString(1, String.join(",", userIds.stream().map(String::valueOf).toList()));

            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                long userId = result.getLong("user_id");
                long groupId = result.getLong("group_id");
                String name = result.getString("group_name");

                if(!groups.containsKey(userId)) {
                    groups.put(userId, new ArrayList<>());
                }

                groups.get(userId).add(new Group(groupId, name));
            }
        }

        return groups;
    }

    private static class Queries {
        private static final String GET_USERS_PAGINATED = "SELECT * FROM `users` LIMIT ? OFFSET ?;";
        private static final String GET_USER_COUNT = "SELECT COUNT(*) FROM `users`;";
        private static final String GET_USER_BY_ID = "SELECT * FROM `users` WHERE `id`=?;";
        private static final String GET_USER_BY_LOGIN = "SELECT * FROM `users` WHERE `login`=?;";
        private static final String CREATE_USER = "INSERT INTO `users`(`login`, `password`, `fullName`, `role`) VALUES (?, ?, ?, ?);";
        private static final String UPDATE_USER = "UPDATE `users` SET `login`=?, `password`=?, `fullName`=?, `role`=? WHERE `id`=?;";
        private static final String DELETE_USER = "DELETE FROM `users` WHERE `id`=?;";

        private static final String GET_GROUPS_PAGINATED = "SELECT * FROM `groups` LIMIT ? OFFSET ?;";
        private static final String GET_GROUP_COUNT = "SELECT COUNT(*) FROM `groups`;";
        private static final String GET_GROUP_BY_ID = "SELECT * FROM `groups` WHERE `id`=?;";
        private static final String CREATE_GROUP = "INSERT INTO `groups`(`name`) VALUES (?);";
        private static final String UPDATE_GROUP = "UPDATE `groups` SET `name`=? WHERE `id`=?;";
        private static final String DELETE_GROUP = "DELETE FROM `groups` WHERE `id`=?;";

        private static final String GET_GROUPS_BY_USERS = "SELECT g.id AS `group_id`, g.name AS `group_name`, ug.user_id AS `user_id` FROM `groups` g INNER JOIN `user_groups` JOIN `user_groups` ug on g.id = ug.group_id WHERE FIND_IN_SET(ug.user_id, ?);";
    }
}
