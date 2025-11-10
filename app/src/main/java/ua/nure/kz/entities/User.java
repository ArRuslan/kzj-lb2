package ua.nure.kz.entities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ua.nure.kz.servlets.auth.LoginServlet;
import ua.nure.kz.utils.HexUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class User {
    private static final Log log = LogFactory.getLog(User.class);

    private long id;
    private String login;
    private String password;
    private boolean passwordHashed;
    private String fullName;
    private Role role;

    public User(long id, String login, String password, boolean passwordHashed, String fullName, Role role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.passwordHashed = passwordHashed;
        this.fullName = fullName;
        this.role = role;
    }

    public User(String login, String password, String fullName, Role role) {
        this(0, login, password, false, fullName, role);
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

    public boolean isPasswordHashed() {
        return passwordHashed;
    }

    public void setPasswordHashed(boolean passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    public void hashPasswordIfNotHashed() throws NoSuchAlgorithmException {
        if(passwordHashed) {
            return;
        }

        MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        password = HexUtils.bytesToHex(passwordHash);
        passwordHashed = true;
    }

    public boolean checkPassword(String check) {
        if(!passwordHashed) {
            return Objects.equals(check, password);
        }

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
        } catch (NoSuchAlgorithmException exc) {
            log.error("SHA3-256 is not supported ig", exc);
            return false;
        }

        byte[] bytesToCheck = digest.digest(check.getBytes(StandardCharsets.UTF_8));
        byte[] passwordHash = HexUtils.hexToBytes(password);

        return Arrays.equals(bytesToCheck, passwordHash);
    }

    public static User fromResultSet(ResultSet result) throws SQLException {
        long id = result.getLong("id");
        String login = result.getString("login");
        String password = result.getString("password");
        String fullName = result.getString("fullName");
        User.Role role = User.Role.valueOf(result.getString("role"));

        return new User(id, login, password, true, fullName, role);
    }

    public enum Role {
        USER,
        ADMIN,
    }
}
