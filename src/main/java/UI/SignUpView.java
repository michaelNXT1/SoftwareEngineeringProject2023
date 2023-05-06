//package Market;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.PasswordField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.router.Route;
//import MainUI;
//
//@Route(value = "signup", layout = MainUI.class)
//public class SignUpView extends VerticalLayout {
//
//    public SignUpView() {
//        FormLayout formLayout = new FormLayout();
//        TextField usernameField = new TextField("Username");
//        PasswordField passwordField = new PasswordField("Password");
//        PasswordField confirmPasswordField = new PasswordField("Confirm Password");
//        Button signUpButton = new Button("Sign Up", event -> {
//            String username = usernameField.getValue();
//            String password = passwordField.getValue();
//            String confirmPassword = confirmPasswordField.getValue();
//            // Perform sign-up logic here
//            Notification.show("Sign up successful");
//        });
//
//        formLayout.add(usernameField, passwordField, confirmPasswordField, signUpButton);
//        add(formLayout);
//    }
//}
