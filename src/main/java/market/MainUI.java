package market;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import javax.servlet.annotation.WebServlet;
import com.vaadin.flow.server.VaadinServlet;
import jakarta.servlet.ServletException;
import market.LoginView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/*", name = "MainUIServlet", asyncSupported = true)
@PWA(name = "Market Application", shortName = "Market")
public class MainUI extends VerticalLayout implements RouterLayout {

    public MainUI() {
        // Set up the UI layout
        RouterLink loginLink = new RouterLink("Login", LoginView.class);
        RouterLink signUpLink = new RouterLink("Sign Up", SignUpView.class);
        // Add more navigation links as needed

        add(loginLink, signUpLink);
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, javax.servlet.ServletException {
        VaadinServlet servlet = new VaadinServlet();
        servlet.service((HttpServletRequest) request, (HttpServletResponse) response);
    }
}
