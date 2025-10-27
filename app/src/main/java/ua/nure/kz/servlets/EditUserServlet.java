package ua.nure.kz.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ua.nure.kz.DatabaseManager;
import ua.nure.kz.entities.User;

import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/users/edit/*")
public class EditUserServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(EditUserServlet.class);

    private User getUserFromPath(HttpServletRequest req) {
        String[] pathParts = req.getPathInfo().split("/");
        if(pathParts.length == 0) {
            return null;
        }

        String userIdFromPath = pathParts[1];
        long userId;

        try {
            userId = Long.parseLong(userIdFromPath);
        } catch (NumberFormatException e) {
            log.warn("Failed parse user id!", e);
            return null;
        }

        try {
            return DatabaseManager.getInstance().getUser(userId);
        } catch (SQLException exc) {
            log.error("Failed get user!", exc);
            return null;
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User currentUser = Util.getUserFromSession(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if(currentUser.getRole() != User.Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        User user = getUserFromPath(req);
        if(user == null) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("/user_edit.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User currentUser = Util.getUserFromSession(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if(currentUser.getRole() != User.Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/users");
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
            req.getRequestDispatcher("/user_edit.jsp").forward(req, resp);
            return;
        }

        User user = getUserFromPath(req);
        if(user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        user.setLogin(login);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setRole(User.Role.valueOf(role));

        try {
            DatabaseManager.getInstance().updateUser(user);
        } catch (SQLException exc) {
            log.error("Failed edit user!", exc);
            req.setAttribute("error", "Failed to edit user");
            req.getRequestDispatcher("/user_edit.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}