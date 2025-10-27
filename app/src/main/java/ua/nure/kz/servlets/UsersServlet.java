package ua.nure.kz.servlets;

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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(UsersServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = Util.getUserFromSession(req);
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }

        try {
            List<User> users = DatabaseManager.getInstance().getUsers(1, 5);
            Map<Long, List<Group>> userGroups = DatabaseManager.getInstance().getUserGroups(users.stream().map(User::getId).toList());
            req.setAttribute("users", users);
            req.setAttribute("userGroups", userGroups);
        } catch (SQLException exc) {
            log.error("Failed get users!", exc);
            req.setAttribute("error", "Failed to fetch users from database");
            req.getRequestDispatcher("users.jsp").forward(req, resp);
            return;
        }

        req.getRequestDispatcher("users.jsp").forward(req, resp);
    }
}