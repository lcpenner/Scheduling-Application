package controller;

import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import utility.Alerts;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class controls the Appointments screen. */
public class AppointmentsController implements Initializable {
    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> apptContactCol;

    @FXML
    private TableColumn<Appointment, Integer> apptCustomerIdCol;

    @FXML
    private TableColumn<Appointment, String> apptDescriptionCol;

    @FXML
    private TableColumn<Appointment, Timestamp> apptEndCol;

    @FXML
    private TableColumn<Appointment, Integer> apptIdCol;

    @FXML
    private TableColumn<Appointment, String> apptLocationCol;

    @FXML
    private TableColumn<Appointment, Timestamp> apptStartCol;

    @FXML
    private TableColumn<Appointment, String> apptTitleCol;

    @FXML
    private TableColumn<Appointment, String> apptTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> apptUserIdCol;

/**This method initializes the appointments table for the Appointments screen. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentTableView.setItems(AppointmentDaoImpl.getAllAppointments());
        apptContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**This method returns the user to the Main Menu screen. */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method sends the user to the Add Appointment screen. */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/AddAppt.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method updates the table to only display appointments for the next month. */
    @FXML
    void onActionMonthFilterSelected(ActionEvent event) {
        appointmentTableView.setItems(getAppointmentsMonthFilter());
    }

    /**This method returns a list of appointments that start within the next month. */
    private ObservableList<Appointment> getAppointmentsMonthFilter() {
        ObservableList<Appointment> flist = FXCollections.observableArrayList();;

        LocalDateTime monthStart = LocalDateTime.now();
        LocalDateTime monthEnd = monthStart.plusMonths(1);

        for(Appointment a: AppointmentDaoImpl.getAllAppointments()){
            if (a.getStart().isAfter(monthStart) && a.getStart().isBefore(monthEnd)){
                flist.add(a);
            }
        }

        return flist;

    }

    /**This method displays all appointments in the appointments table (replaces filtered lists). */
    @FXML
    void onActionAllFilterSelected(ActionEvent event) {
        appointmentTableView.setItems(AppointmentDaoImpl.getAllAppointments());
    }

    /**This method displays all appointments within the next week. */
    @FXML
    void onActionWeekFilterSelected(ActionEvent event) {
        appointmentTableView.setItems(getAppointmentsWeekFilter());
    }

    /**This method returns a list of all appointments in the next week. */
    private ObservableList<Appointment> getAppointmentsWeekFilter() {
        ObservableList<Appointment> flist = FXCollections.observableArrayList();;

        LocalDateTime weekStart = LocalDateTime.now();
        LocalDateTime weekEnd = weekStart.plusWeeks(1);

        for(Appointment a: AppointmentDaoImpl.getAllAppointments()){
            if (a.getStart().isAfter(weekStart) && a.getStart().isBefore(weekEnd)){
                flist.add(a);
            }
        }

        return flist;

    }

/**This method deletes the selected appointment from the database. */
    @FXML
    void onActionDelete(ActionEvent event){
        Appointment appointmentToDelete = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointmentToDelete == null){
            Alerts.showAlert(2);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Appointment");
            alert.setHeaderText("Cancel");
            alert.setContentText("Please confirm you would like to cancel the following appointment:\nApppointment ID: " + appointmentToDelete.getAppointmentId() + "\nType: " + appointmentToDelete.getType());
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentDaoImpl.deleteAppointment(appointmentToDelete.getAppointmentId());
            }
            appointmentTableView.setItems(AppointmentDaoImpl.getAllAppointments());

        }

    }

/**This method send the user to the Update Appointment screen for the selected appointment. */
    @FXML
    void onActionUpdate(ActionEvent event) throws IOException {

        if(appointmentTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alerts.showAlert(2);
            return;
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UpdateAppt.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            UpdateApptController ApptController = loader.getController();

            ApptController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Scheduling Application");
            stage.setScene(scene);
            stage.show();

        }
    }

}
