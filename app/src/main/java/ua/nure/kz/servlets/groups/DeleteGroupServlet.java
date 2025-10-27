package ua.nure.kz.servlets.groups;

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


@WebServlet("/groups/delete/*")
public class DeleteGroupServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(DeleteGroupServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(Util.notLoggedInOrNotAdmin(req, resp, "/groups")) {
            return;
        }

        Group group = Util.getGroupFromPath(req);
        if(group == null) {
            resp.sendRedirect(req.getContextPath() + "/groups");
            return;
        }

        req.setAttribute("group", group);
        req.getRequestDispatcher("/groups/delete.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(Util.notLoggedInOrNotAdmin(req, resp, "/groups")) {
            return;
        }

        Group group = Util.getGroupFromPath(req);
        if(group == null) {
            resp.sendRedirect(req.getContextPath() + "/groups");
            return;
        }

        try {
            DatabaseManager.getInstance().deleteGroup(group);
        } catch (SQLException exc) {
            log.error("Failed to delete group!", exc);
            req.setAttribute("error", "Failed to delete edit group");
            req.getRequestDispatcher("/groups/delete.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/groups");
    }
}