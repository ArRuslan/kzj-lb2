package ua.nure.kz.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ua.nure.kz.DatabaseManager;
import ua.nure.kz.entities.Group;
import ua.nure.kz.entities.User;
import ua.nure.kz.utils.Pagination;
import ua.nure.kz.utils.PaginationPage;

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

    public static class PageInfo {
        public int page;
        public int pageSize;
        public String query;

        public PageInfo(int page, int pageSize, String query) {
            this.page = page;
            this.pageSize = pageSize;
            this.query = query;
        }
    }

    public static PageInfo parsePageInfo(HttpServletRequest req) {
        String qs = req.getQueryString();
        if(qs == null) {
            return new PageInfo(1, 10, "");
        }

        StringBuilder queryStringBuilder = new StringBuilder(qs.length());

        int page = 1;
        int pageSize = 10;

        for(String qsPart : qs.split("&")) {
            if(qsPart.startsWith("page=")) {
                String pageStr = qsPart.substring(5);
                try {
                    int pageMaybe = Integer.parseInt(pageStr);
                    if(pageMaybe >= 1) {
                        page = pageMaybe;
                    }
                } catch (NumberFormatException exc) {
                    log.error("Failed parse page number!", exc);
                }
            } else if (qsPart.startsWith("pageSize=")) {
                String pageSizeStr = qsPart.substring(9);
                try {
                    int pageSizeMaybe = Integer.parseInt(pageSizeStr);
                    if(pageSizeMaybe >= 1) {
                        pageSize = Math.min(pageSizeMaybe, 100);
                    }
                } catch (NumberFormatException exc) {
                    log.error("Failed parse page number!", exc);
                }
            } else {
                if(!queryStringBuilder.isEmpty()) {
                    queryStringBuilder.append("&");
                }
                queryStringBuilder.append(qsPart);
            }
        }

        return new PageInfo(page, pageSize, queryStringBuilder.toString());
    }

    public static Pagination calculatePagination(HttpServletRequest req, PageInfo pageInfo, long totalEntries) {
        long totalPages = (totalEntries + pageInfo.pageSize - 1) / pageInfo.pageSize;

        long minPage = Math.max(pageInfo.page - 2, 1);
        long maxPage = Math.min(pageInfo.page + 2, totalPages);

        PaginationPage[] pages = new PaginationPage[(int)(maxPage - minPage + 1)];
        int i = 0;
        for(long page = minPage; page <=maxPage; ++page, ++i) {
            pages[i] = new PaginationPage(page == pageInfo.page, page);
        }

        return new Pagination(
                pageInfo.page > 1,
                pageInfo.page < totalPages,
                pages,
                req.getRequestURI(),
                pageInfo.page > 1 ? pageInfo.page - 1 : pageInfo.page,
                pageInfo.page < totalPages ? pageInfo.page + 1 : pageInfo.page,
                pageInfo.pageSize,
                pageInfo.query);
    }
}
