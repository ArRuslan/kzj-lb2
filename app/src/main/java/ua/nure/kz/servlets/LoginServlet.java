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


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(LoginServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long userIdFromSess = (Long)req.getSession().getAttribute("userId");
        if(userIdFromSess != null) {
            User user = null;
            try {
                user = DatabaseManager.getInstance().getUser(userIdFromSess);
            } catch (SQLException exc) {
                log.error("Failed get user!", exc);
            }

            if(user != null) {
                resp.sendRedirect("users");
                return;
            }
        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user;
        try {
            user = DatabaseManager.getInstance().getUser(login);
        } catch (SQLException exc) {
            log.error("Failed get user!", exc);
            req.setAttribute("error", "Failed to fetch user from database");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        // TODO: password hashing (sha-256)
        if (user == null || !user.getPassword().equals(password)) {
            log.error("Unknown user or password is incorrect!");
            req.setAttribute("error", "User with this parameters does no exist");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("userId", user.getId());
        resp.sendRedirect("users");
    }
}