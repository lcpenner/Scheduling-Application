package controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import utility.Alerts;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

import static controller.LoginController.currentUser;
import static java.time.LocalTime.now;

/**This class is the controller for the Add Appointment screen. */
public class AddApptController implements Initializable {


    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private ComboBox<LocalTime> startTimeCombo;

    @FXML
    private ComboBox<LocalTime> endTimeCombo;
    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField userIdTxt;

    /**This method initializes the Add Appointment screen. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contactCombo.setItems(ContactDaoImpl.getAllContacts());
        contactCombo.getSelectionModel().selectFirst();

        typeCombo.setItems(getTypeOptions());
        typeCombo.getSelectionModel().selectFirst();

        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());

        startTimeCombo.setItems(getTimeOptions());
        startTimeCombo.getSelectionModel().selectFirst();

        endTimeCombo.setItems(getTimeOptions());
        endTimeCombo.getSelectionModel().selectFirst();

        userIdTxt.setText(String.valueOf(currentUser.getId()));
    }

    /**This method returns the user to the Appointments screen. */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Appointments.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method provides a list of Appointment type options. */
    public static ObservableList<String> getTypeOptions() {

        ObservableList<String> tlist = FXCollections.observableArrayList();
        tlist.add("Consultation");
        tlist.add("De-Briefing");
        tlist.add("Upgrade");
        tlist.add("Planning Session");
        return tlist;
    }


/**This method saves the new appointment data and stores it in the database. */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException {
    try {
    String title = titleTxt.getText();
    String description = descriptionTxt.getText();
    String location = locationTxt.getText();
    String type = typeCombo.getSelectionModel().getSelectedItem();

    int customerId = Integer.parseInt(customerIdTxt.getText());
    int userId = Integer.parseInt(userIdTxt.getText());

    int contactId = contactCombo.getSelectionModel().getSelectedItem().getContactId();

    LocalDate startDate = startDatePicker.getValue();
    LocalDate endDate = endDatePicker.getValue();

    LocalTime startTime = startTimeCombo.getSelectionModel().getSelectedItem();
    LocalTime endTime = endTimeCombo.getSelectionModel().getSelectedItem();

    LocalDateTime start = LocalDateTime.of(startDate, startTime);
    LocalDateTime end = LocalDateTime.of(endDate, endTime);

    //check to see if start time is after end time
    if (startTime.isAfter(endTime)) {
        Alerts.showAlert(5);
        return;
    }

    //check for appointment conflict
    boolean scheduleIsOpen = true;

    for (Appointment a : AppointmentDaoImpl.getAllAppointments()) {
        if ((a.getCustomerId() == customerId) && ((start.isAfter(a.getStart()) || start.isEqual(a.getStart())) && start.isBefore(a.getEnd()))) {
            scheduleIsOpen = false;
            break;
        }
        if ((a.getCustomerId() == customerId) && (end.isAfter(a.getStart()) && (end.isBefore(a.getEnd()) || end.isEqual(a.getEnd())))) {
            scheduleIsOpen = false;
            break;
        }
        if ((a.getCustomerId() == customerId) && ((start.isBefore(a.getStart()) || start.isEqual(a.getStart())) && (end.isAfter(a.getEnd()) || end.isEqual(a.getEnd())))) {
            scheduleIsOpen = false;
            break;
        }

    }

    if (scheduleIsOpen) {
        AppointmentDaoImpl.insertAppointment(title, description, location, type, start, end, customerId, userId, contactId);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Appointments.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    } else {
        Alerts.showAlert(3);
    }
    }
    catch(Exception e){
        Alerts.showAlert(6);
        return;
    }

    }

    /**This method returns a list of appointment time options. The purpose of this method is to prevent any user from selecting an appointment time outside of working hours. */
    private ObservableList<LocalTime> getTimeOptions() {

        ObservableList<LocalTime> tlist = FXCollections.observableArrayList();

        LocalTime startTimeEST = LocalTime.of(8,00);
        LocalDateTime startLocalDateTimeEST = LocalDateTime.of(startDatePicker.getValue(), startTimeEST);
        ZonedDateTime startZoneDateTimeEST = ZonedDateTime.of(startLocalDateTimeEST, ZoneId.of("America/New_York"));

        LocalTime endTimeEST = LocalTime.of(22,00);
        LocalDateTime endLocalDateTimeEST = LocalDateTime.of(startDatePicker.getValue(), endTimeEST);
        ZonedDateTime endZonedDateTimeEST = ZonedDateTime.of(endLocalDateTimeEST, ZoneId.of("America/New_York"));

        //next step is to convert to local system time
        ZonedDateTime startDateTimeSystem = startZoneDateTimeEST.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime startLocalDateTimeSystem = startDateTimeSystem.toLocalDateTime();
        LocalTime startTimeSystem = startLocalDateTimeSystem.toLocalTime();

        ZonedDateTime endDateTimeSystem = endZonedDateTimeEST.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime endLocalDateTimeSystem = endDateTimeSystem.toLocalDateTime();
        LocalTime endTimeSystem = endLocalDateTimeSystem.toLocalTime();



        while(startTimeSystem.isBefore(endTimeSystem)){
            tlist.add(startTimeSystem);
            startTimeSystem = startTimeSystem.plusMinutes(30);
        }

        return tlist;
    }



}
