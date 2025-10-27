package ua.nure.kz.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ua.nure.kz.DatabaseManager;
import ua.nure.kz.entities.Group;
import ua.nure.kz.entities.User;

import java.io.IOException;
import java.sql.SQLException;

public class Util {
    private static final Log log = LogFactory.getLog(Util.class);

    public static User getUserFromSession(HttpServletRequest req) {
        User userIdFromSess = (User)req.getSession().getAttribute("user");
        if(userIdFromSess != null) {
            User user = null;
            try {
                user = DatabaseManager.getInstance().getUser(userIdFromSess.getId());
            } catch (SQLException exc) {
                log.error("Failed get user!", exc);
            }

            req.getSession().setAttribute("user", user);
            return user;
        }

        return null;
    }

    public static User getUserFromPath(HttpServletRequest req) {
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

    public static Group getGroupFromPath(HttpServletRequest req) {
        String[] pathParts = req.getPathInfo().split("/");
        if(pathParts.length == 0) {
            return null;
        }

        String groupIdFromPath = pathParts[1];
        long groupId;

        try {
            groupId = Long.parseLong(groupIdFromPath);
        } catch (NumberFormatException e) {
            log.warn("Failed parse group id!", e);
            return null;
        }

        try {
            return DatabaseManager.getInstance().getGroup(groupId);
        } catch (SQLException exc) {
            log.error("Failed get group!", exc);
            return null;
        }
    }

    public static boolean notLoggedInOrNotAdmin(HttpServletRequest req, HttpServletResponse resp, String notAdminRedirect) throws IOException {
        User currentUser = Util.getUserFromSession(req);
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return true;
        }
        if(currentUser.getRole() != User.Role.ADMIN) {
            resp.sendRedirect(req.getContextPath() + notAdminRedirect);
            return true;
        }

        return false;
    }

    public static Group getGroupFromParam(HttpServletRequest req, String paramName) {
        String groupIdFromParam = req.getParameter(paramName);
        if(groupIdFromParam == null || groupIdFromParam.isEmpty()) {
            return null;
        }

        long groupId;

        try {
            groupId = Long.parseLong(groupIdFromParam);
        } catch (NumberFormatException e) {
            log.warn("Failed parse group id!", e);
            return null;
        }

        try {
            return DatabaseManager.getInstance().getGroup(groupId);
        } catch (SQLException exc) {
            log.error("Failed get group!", exc);
            return null;
        }
    }

    public static class Pair<T1, T2> {
        public T1 first;
        public T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }

    public static Pair<User, Group> getUserAndGroupFromParams(HttpServletRequest req) {
        String userIdFromPath = req.getParameter("userId");
        String groupIdFromPath = req.getParameter("groupId");
        String groupNameFromPath = req.getParameter("groupName");

        if(groupIdFromPath == null && groupNameFromPath == null) {
            return null;
        }

        long userId;
        long groupId;

        try {
            userId = Long.parseLong(userIdFromPath);
            if(groupIdFromPath != null) {
                groupId = Long.parseLong(groupIdFromPath);
            } else {
                groupId = 0;
            }
        } catch (NumberFormatException e) {
            log.warn("Failed parse user or group id!", e);
            return null;
        }

        User user;
        Group group;

        try {
            user = DatabaseManager.getInstance().getUser(userId);
            if(groupIdFromPath != null) {
                group = DatabaseManager.getInstance().getGroup(groupId);
            } else {
                group = DatabaseManager.getInstance().getGroup(groupNameFromPath);
            }
        } catch (SQLException exc) {
            log.error("Failed get user or group!", exc);
            return null;
        }

        if(user == null || group == null) {
            return null;
        }

        return new Pair<>(user, group);
    }
}
