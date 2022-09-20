package controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
import DAO.FirstLevelDivisionDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.FirstLevelDivision;
import utility.Alerts;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

import static controller.LoginController.currentUser;

/**This class is the controller for the Update Appointment screen. */
public class UpdateApptController implements Initializable {
    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<Contact> updateContactCombo;

    @FXML
    private TextField updateCustomerIdTxt;

    @FXML
    private TextField updateDescriptionTxt;

    @FXML
    private DatePicker updateEndDatePicker;

    @FXML
    private ComboBox<LocalTime> updateEndTimeCombo;

    @FXML
    private TextField updateLocationTxt;

    @FXML
    private DatePicker updateStartDatePicker;

    @FXML
    private ComboBox<LocalTime> updateStartTimeCombo;

    @FXML
    private TextField updateTitleTxt;

    @FXML
    private ComboBox<String> updateTypeCombo;

    @FXML
    private TextField updateUserIdTxt;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private Appointment appointmentToUpdate = null;
    private boolean firstLoad = true;

/**This method is used to send the selected appointment to the Update Appointment screen. */
    public void sendAppointment(Appointment appointment)
    {
        this.appointmentToUpdate = appointment;

        appointmentIdTxt.setText(String.valueOf(appointmentToUpdate.getAppointmentId()));
        updateTitleTxt.setText(appointmentToUpdate.getTitle());
        updateDescriptionTxt.setText(appointmentToUpdate.getDescription());
        updateLocationTxt.setText(appointmentToUpdate.getLocation());

        updateContactCombo.setItems(getUpdateContactsList());
        updateContactCombo.getSelectionModel().selectFirst();

        updateTypeCombo.setItems(getTypeUpdateOptions());
        updateTypeCombo.getSelectionModel().select(appointmentToUpdate.getType());

        updateCustomerIdTxt.setText(String.valueOf(appointmentToUpdate.getCustomerId()));
        updateUserIdTxt.setText(String.valueOf(appointmentToUpdate.getUserId()));

        updateStartDatePicker.setValue(appointmentToUpdate.getStart().toLocalDate());
        updateEndDatePicker.setValue(appointmentToUpdate.getEnd().toLocalDate());

        updateStartTimeCombo.setItems(getTimeOptions());
        updateStartTimeCombo.getSelectionModel().select(appointmentToUpdate.getStart().toLocalTime());

        updateEndTimeCombo.setItems(getTimeOptions());
        updateEndTimeCombo.getSelectionModel().select(appointmentToUpdate.getEnd().toLocalTime());


    }

    /**This method returns a list of contacts starting with the selected contact. */
    public ObservableList<Contact> getUpdateContactsList(){

        ObservableList<Contact> clist = FXCollections.observableArrayList();

        if (firstLoad){
            for (Contact contact: ContactDaoImpl.getAllContacts()){
                if (contact.getContactId() == appointmentToUpdate.getContactId()){
                    clist.add(contact);
                }
            }
            firstLoad = false;
        }

        for (Contact contact: ContactDaoImpl.getAllContacts()){
            if (contact.getContactId() != appointmentToUpdate.getContactId()){
                clist.add(contact);
            }
        }

        return clist;
    }

/**This method returns the user to the Appointments screen. */
    @FXML
    void onActionCancelApptUpdate(ActionEvent event)throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Appointments.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method saves the updated appointment to the database and returns the user to the Appointments screen. */
    @FXML
    void onActionSaveUpdate(ActionEvent event) throws IOException{
        try {

            String title = updateTitleTxt.getText();
            String description = updateDescriptionTxt.getText();
            String location = updateLocationTxt.getText();
            String type = updateTypeCombo.getSelectionModel().getSelectedItem();

            int customerId = Integer.parseInt(updateCustomerIdTxt.getText());

            int userId = Integer.parseInt(updateUserIdTxt.getText());
            int updatedContactId = updateContactCombo.getSelectionModel().getSelectedItem().getContactId();

            LocalDate startDate = updateStartDatePicker.getValue();
            LocalDate endDate = updateEndDatePicker.getValue();

            LocalTime startTime = updateStartTimeCombo.getSelectionModel().getSelectedItem();
            LocalTime endTime = updateEndTimeCombo.getSelectionModel().getSelectedItem();

            LocalDateTime start = LocalDateTime.of(startDate, startTime); //temporary assignment for testing
            LocalDateTime end = LocalDateTime.of(endDate, endTime);

            //check for no change
            if (appointmentToUpdate.getTitle().equals(title) && appointmentToUpdate.getDescription().equals(description) &&
                    appointmentToUpdate.getLocation().equals(location) && appointmentToUpdate.getType().equals(type) &&
                    appointmentToUpdate.getStart().isEqual(start) && appointmentToUpdate.getEnd().isEqual(end) &&
                    appointmentToUpdate.getCustomerId() == customerId && appointmentToUpdate.getContactId() == updatedContactId) {

                Alert aWarning = new Alert(Alert.AlertType.WARNING);
                aWarning.setTitle("Notice");
                aWarning.setHeaderText("No change was made to the appointment");
                aWarning.showAndWait();

                return;
            }

            //check for appointment conflict
            boolean scheduleIsOpen = true;

            for (Appointment a : AppointmentDaoImpl.getAllAppointments()) {
                if ((a.getCustomerId() == customerId) && (appointmentToUpdate.getAppointmentId() != a.getAppointmentId()) && ((start.isAfter(a.getStart()) || start.isEqual(a.getStart())) && start.isBefore(a.getEnd()))) {
                    scheduleIsOpen = false;
                    break;
                }
                if ((a.getCustomerId() == customerId) && (appointmentToUpdate.getAppointmentId() != a.getAppointmentId()) && (end.isAfter(a.getStart()) && (end.isBefore(a.getEnd()) || end.isEqual(a.getEnd())))) {
                    scheduleIsOpen = false;
                    break;
                }
                if ((a.getCustomerId() == customerId) && (appointmentToUpdate.getAppointmentId() != a.getAppointmentId()) && ((start.isBefore(a.getStart()) || start.isEqual(a.getStart())) && (end.isAfter(a.getEnd()) || end.isEqual(a.getEnd())))) {
                    scheduleIsOpen = false;
                    break;
                }

            }

            if (scheduleIsOpen) {

                AppointmentDaoImpl.updateAppointment(appointmentToUpdate.getAppointmentId(), title, description, location, type, start, end, customerId, userId, updatedContactId);

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

    /**This method returns a list of time options. */
    private ObservableList<LocalTime> getTimeOptions() {

        ObservableList<LocalTime> tlist = FXCollections.observableArrayList();

        LocalTime startTimeEST = LocalTime.of(8,00);
        LocalDateTime startLocalDateTimeEST = LocalDateTime.of(updateStartDatePicker.getValue(), startTimeEST);
        ZonedDateTime startZoneDateTimeEST = ZonedDateTime.of(startLocalDateTimeEST, ZoneId.of("America/New_York"));

        LocalTime endTimeEST = LocalTime.of(22,00);
        LocalDateTime endLocalDateTimeEST = LocalDateTime.of(updateStartDatePicker.getValue(), endTimeEST);
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

    /**This method returns a list of type options. */
    public static ObservableList<String> getTypeUpdateOptions() {

        ObservableList<String> tlist = FXCollections.observableArrayList();
        tlist.add("Consultation");
        tlist.add("De-Briefing");
        tlist.add("Upgrade");
        tlist.add("Planning Session");
        return tlist;
    }

}
