package UI.Market;

import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MainUI extends VerticalLayout {

    public MainUI() {
        LoginOverlay login = new LoginOverlay();
        login.setAction("login"); // the URL to submit the username and password to
        login.setTitle("Market Login");
        login.setDescription("Enter your username and password to log in.");
        add(login);

        VaadinSession session = VaadinSession.getCurrent();
        VaadinServletRequest request = VaadinServletRequest.getCurrent();
        VaadinService service = VaadinService.getCurrent();
        VaadinRequest vaadinRequest = service.getCurrentRequest();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            // the user is already authenticated, redirect to the main view
            vaadinRequest.getWrappedSession().setAttribute("user", auth.getName());
        } else if (request.getParameter("error") != null) {
            // the user entered incorrect credentials, show an error message
            login.setError(true);
        }
    }
}
