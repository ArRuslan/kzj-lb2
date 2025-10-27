package ua.nure.kz.servlets.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ua.nure.kz.DatabaseManager;
import ua.nure.kz.entities.Group;
import ua.nure.kz.entities.User;
import ua.nure.kz.servlets.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/users/edit/*")
public class EditUserServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(EditUserServlet.class);

    public void doGetInternal(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException, ServletException {
        List<Group> userGroups;

        try {
            userGroups = DatabaseManager.getInstance().getUserGroups(new ArrayList<>() {{ add(user.getId()); }}).get(user.getId());
        } catch (SQLException exc) {
            log.error("Failed to get user's groups!", exc);
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        req.setAttribute("user", user);
        req.setAttribute("userGroups", userGroups);
        req.getRequestDispatcher("/users/edit.jsp").forward(req, resp);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(Util.notLoggedInOrNotAdmin(req, resp, "/groups")) {
            return;
        }

        User user = Util.getUserFromPath(req);
        if(user == null) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        doGetInternal(req, resp, user);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(Util.notLoggedInOrNotAdmin(req, resp, "/groups")) {
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String fullName = req.getParameter("fullName");
        String role = req.getParameter("role");

        try {
            User.Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            role = null;
        }

        if(login == null || password == null || fullName == null || role == null) {
            req.setAttribute("error", "Invalid user data");
            req.getRequestDispatcher("/users/edit.jsp").forward(req, resp);
            return;
        }

        User user = Util.getUserFromPath(req);
        if(user == null) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        user.setLogin(login);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setRole(User.Role.valueOf(role));

        try {
            DatabaseManager.getInstance().updateUser(user);
        } catch (SQLException exc) {
            log.error("Failed to edit user!", exc);
            req.setAttribute("error", "Failed to edit user");
            req.getRequestDispatcher("/users/edit.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}