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
import java.util.Objects;


@WebServlet("/users/add-group")
public class AddUserToGroupServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(AddUserToGroupServlet.class);
    private final EditUserServlet editServlet = new EditUserServlet();

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(Util.notLoggedInOrNotAdmin(req, resp, "/groups")) {
            return;
        }

        String referer = req.getHeader("referer");
        String redirectTo = Objects.requireNonNullElseGet(referer, () -> req.getContextPath() + "/users");

        Util.Pair<User, Group> userAndGroup = Util.getUserAndGroupFromParams(req);
        if(userAndGroup == null) {
            resp.sendRedirect(redirectTo);
            return;
        }

        try {
            DatabaseManager.getInstance().addUserToGroup(userAndGroup.first, userAndGroup.second);
        } catch (SQLException exc) {
            log.error("Failed to add user to group!", exc);
            req.setAttribute("error", "Failed to add user to group");

            editServlet.doGetInternal(req, resp, userAndGroup.first);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/users/edit/" + userAndGroup.first.getId());
    }
}