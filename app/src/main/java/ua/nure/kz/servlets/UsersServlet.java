package ua.nure.kz.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.nure.kz.entities.User;

import java.io.IOException;


@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = Util.getUserFromSession(req);
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }

        req.getRequestDispatcher("users.jsp").forward(req, resp);
    }
}