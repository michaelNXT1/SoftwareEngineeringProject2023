package Security;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityUtils implements SecurityAdapter {

    private static final String LOGOUT_SUCCESS_URL = "/";

    public static boolean isAuthenticated() {
        VaadinServletRequest request = VaadinServletRequest.getCurrent();
        return request != null && request.getUserPrincipal() != null;
    }

    public boolean authenticate(String username, String password) {
        VaadinServletRequest request = VaadinServletRequest.getCurrent();
        if (request == null) {
            // This is in a background thread and we can't access the request to
            // log in the user
            return false;
        }
        try {
            HttpServletRequest servletRequest = (HttpServletRequest) request.getHttpServletRequest();
            servletRequest.login(username, password);
            return true;
        } catch (jakarta.servlet.ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public static void logout() {
        VaadinSession.getCurrent().getSession().invalidate();
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
    }
}
