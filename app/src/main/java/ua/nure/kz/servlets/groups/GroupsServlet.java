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
import java.util.List;


@WebServlet("/groups")
public class GroupsServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(GroupsServlet.class);

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = Util.getUserFromSession(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Util.PageInfo pageInfo = Util.parsePageInfo(req);
        List<Group> groups;
        long entriesCount;

        try {
            groups = DatabaseManager.getInstance().getGroups(pageInfo.page, pageInfo.pageSize);
            entriesCount = DatabaseManager.getInstance().getGroupsCount();
        } catch (SQLException exc) {
            log.error("Failed get groups!", exc);
            req.setAttribute("error", "Failed to fetch groups from database");
            req.getRequestDispatcher("/groups/list.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("groups", groups);
        req.setAttribute("pagination", Util.calculatePagination(req, pageInfo, entriesCount));
        req.getRequestDispatcher("/groups/list.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = Util.getUserFromSession(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String name = req.getParameter("name");

        if(name == null || name.isEmpty()) {
            req.setAttribute("error", "Invalid group data");
            req.getRequestDispatcher("/groups/list.jsp").forward(req, resp);
            return;
        }

        Group newGroup = new Group(name);

        try {
            DatabaseManager.getInstance().createGroup(newGroup);
        } catch (SQLException exc) {
            log.error("Failed create group!", exc);
            req.setAttribute("error", "Failed to create group");
            req.getRequestDispatcher("/groups/list.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/groups");
    }
}