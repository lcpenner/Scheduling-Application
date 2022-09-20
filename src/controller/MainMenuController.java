package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**This class is the controller for the Main Menu screen. */
public class MainMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) { }

    /**This method sends the user to the Appointments screen. */
    @FXML
    void onActionAppointments(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Appointments.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method sends the user to the Customers screen. */
    @FXML
    void onActionCustomers(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Customers.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method sends the user back to the Login screen. */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method sends the user to the Reports screen. */
    @FXML
    void onActionReports(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Reports.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

}