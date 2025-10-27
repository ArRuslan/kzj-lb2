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


@WebServlet("/users/delete/*")
public class DeleteUserServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(DeleteUserServlet.class);

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

        User user = Util.getUserFromPath(req);
        if(user == null) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("/users/delete.jsp").forward(req, resp);
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

        User user = Util.getUserFromPath(req);
        if(user == null) {
            resp.sendRedirect(req.getContextPath() + "/users");
            return;
        }

        try {
            DatabaseManager.getInstance().deleteUser(user);
        } catch (SQLException exc) {
            log.error("Failed to delete user!", exc);
            req.setAttribute("error", "Failed to delete edit user");
            req.getRequestDispatcher("/users/delete.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/users");
    }
}