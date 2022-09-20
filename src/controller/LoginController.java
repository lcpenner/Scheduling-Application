package controller;

import DAO.AppointmentDaoImpl;
import DAO.UserDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.*;


/**This class is the controller for the Login screen. */
public class LoginController implements Initializable {

    public static User currentUser;

    @FXML
    private TextField passwordTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private Label zoneTxt;

    @FXML
    private Label loginFailed;

    @FXML
    private Label headerLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button logInBtn;

    /**This method displays the zone and text depending on the user's system. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ZoneId zone = ZoneId.systemDefault();
            zoneTxt.setText(String.valueOf(zone));
        }catch (Exception e) {
            System.out.println(e);
        }

        try {
            rb = ResourceBundle.getBundle("properties/login", Locale.getDefault());

            headerLabel.setText(rb.getString("header"));
            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            logInBtn.setText(rb.getString("login"));

        }catch (Exception e){
            System.out.println(e);
        }

    }

    /**This method logs the user into the application after checking that the username and password match. */
    @FXML
    void onActionLogIn(ActionEvent event) throws IOException {

        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        boolean validLogin = false;

        String filename = "login_activity.txt";
        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fwriter);

        ZonedDateTime loginAttempt = ZonedDateTime.now();

        //check for valid password
        for (User user: UserDaoImpl.getAllUsers()) {

            if ((user.getName().equals(username)) && (user.getPassword().equals(password))) {
                validLogin = true;
                currentUser = user;
                break;
            }

        }

        if(validLogin) {
            outputFile.println("Successful login: " + loginAttempt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
            outputFile.close();

            checkForAppointment(currentUser);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
            stage.setTitle("Scheduling Application");
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            ResourceBundle rb = ResourceBundle.getBundle("properties/login", Locale.getDefault());
            loginFailed.setText(rb.getString("error"));
            outputFile.println("Unsuccessful login: " + loginAttempt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
            outputFile.close();
        }



    }

    /**This method displays an alert if the user has an appointment within 15 minutes of login. */
    private void checkForAppointment(User cUser){

        LocalDateTime loginTime = LocalDateTime.now();

        for (Appointment a: AppointmentDaoImpl.getAllAppointments()){

            if ((cUser.getId() == a.getUserId()) && (a.getStart().isAfter(loginTime.minusMinutes(15)) && a.getStart().isBefore(loginTime.plusMinutes(15)))){
                Alert aWarning = new Alert(Alert.AlertType.WARNING);
                aWarning.setTitle("Warning");
                aWarning.setHeaderText("Appointment within 15 minutes of login");
                aWarning.setContentText("Appointment ID " + a.getAppointmentId() + "\n" + a.getStart());
                aWarning.showAndWait();
                return;
            }

        }

        Alert aWarning = new Alert(Alert.AlertType.WARNING);
        aWarning.setTitle("Notice");
        aWarning.setHeaderText("No appointments within 15 minutes of login");
        aWarning.showAndWait();
        return;

    }

}
