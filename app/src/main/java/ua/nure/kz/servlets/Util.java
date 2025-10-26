package ua.nure.kz.servlets;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ua.nure.kz.DatabaseManager;
import ua.nure.kz.entities.User;

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
}
