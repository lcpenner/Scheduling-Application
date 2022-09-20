package controller;

import DAO.AppointmentDaoImpl;
import DAO.ContactDaoImpl;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import schedulingApplicationMain.reportsInterface;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**This class is the controller for the Reports screen. */
public class ReportsController implements Initializable {

    @FXML
    private TableView<Appointment> reportsAppointmentTableview;

    @FXML
    private ComboBox<Contact> contactCombo;

    @FXML
    private TableColumn<Appointment, Integer> reportApptIdCol;

    @FXML
    private TableColumn<Appointment, String> reportApptTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> reportApptCustIdCol;

    @FXML
    private TableColumn<Appointment, String> reportDescriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> reportEndCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> reportStartCol;

    @FXML
    private TableColumn<Appointment, String> reportTitleCol;

    @FXML
    private TableColumn<ApptType, Integer> reportTypeTotalApptsCol;

    @FXML
    private TableColumn<ApptType, String> reportTypeTotalCol;

    @FXML
    private TableView<ApptType> reportsTypeTableview;

    @FXML
    private TableView<Customer> reportsCustomerTableview;

    @FXML
    private TableColumn<Customer, Integer> customerTotalApptsCol;

    @FXML
    private TableColumn<Customer, String> customerTotalCol;

    @FXML
    private TableColumn<Customer, Integer> customerIdTotalCol;

    @FXML
    private TableView<ApptMonth> reportsMonthTableview;

    @FXML
    private TableColumn<ApptMonth, Integer> reportMonthTotalApptsCol;

    @FXML
    private TableColumn<ApptMonth, String> reportMonthTotalCol;

/**This method sets the Contact options and populates the Type, Month, and Customer reports. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contactCombo.setItems(ContactDaoImpl.getAllContacts());

        populateTypeReport();

        populateMonthReport();

        populateCustomerReport();

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

    /**This method displays the appointments associated with the selected contact. */
    @FXML
    void onActionSelectContact(ActionEvent event) {

        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        for (Appointment a : AppointmentDaoImpl.getAllAppointments()) {
            if (a.getContactId() == contactCombo.getSelectionModel().getSelectedItem().getContactId()) {
                alist.add(a);
            }
        }

        reportsAppointmentTableview.setItems(alist);
        reportApptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        reportTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        reportApptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        reportEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        reportApptCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

    }

    /**This method populates the Type report. The method adds up and displays the total appointments associated with each Type.
    Included in this method is the Lambda expression #1. This lambda is used to increment the total appointments associated with a specific appointment type and simplifies the code to be used as an effective counter.
     */
    public void populateTypeReport(){

        ObservableList<ApptType> tlist = FXCollections.observableArrayList();

        tlist.add(new ApptType("Consultation", 0));
        tlist.add(new ApptType("De-Briefing", 0));
        tlist.add(new ApptType("Upgrade", 0));
        tlist.add(new ApptType("Planning Session", 0));

        /**Lambda expression #1. This lambda is used to increment the total appointments associated with a specific appointment type and simplifies the code to be used as an effective counter. */
        reportsInterface total = (currentTotal) -> currentTotal + 1;

        for (Appointment a : AppointmentDaoImpl.getAllAppointments()) {
            for (ApptType t : tlist) {
                if (a.getType().equals(t.getApptType())) {

                    /**Lambda expression #1. This lambda is used to increment the total appointments associated with a specific appointment type and simplifies the code to be used as an effective counter. */
                    t.setApptTotal(total.newTotal(t.getApptTotal()));
                    break;
                }
            }
        }

        reportsTypeTableview.setItems(tlist);
        reportTypeTotalApptsCol.setCellValueFactory(new PropertyValueFactory<>("apptTotal"));
        reportTypeTotalCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));

    }

    /**This method is used to populate the Month Report. */
    private void populateMonthReport(){

        ObservableList<ApptMonth> mlist = FXCollections.observableArrayList();

        mlist.add(new ApptMonth("JANUARY", 0,1));
        mlist.add(new ApptMonth("FEBRUARY", 0, 2));
        mlist.add(new ApptMonth("MARCH", 0, 3));
        mlist.add(new ApptMonth("APRIL", 0, 4));
        mlist.add(new ApptMonth("MAY", 0, 5));
        mlist.add(new ApptMonth("JUNE", 0, 6));
        mlist.add(new ApptMonth("JULY", 0, 7));
        mlist.add(new ApptMonth("AUGUST", 0,8));
        mlist.add(new ApptMonth("SEPTEMBER", 0, 9));
        mlist.add(new ApptMonth("OCTOBER", 0, 10));
        mlist.add(new ApptMonth("NOVEMBER", 0, 11));
        mlist.add(new ApptMonth("DECEMBER", 0,12));

        for (Appointment a : AppointmentDaoImpl.getAllAppointments()) {

            for (ApptMonth m : mlist) {
                if (a.getStart().getMonthValue() == m.getMonthNumber()) {
                    int count = m.getApptMonthTotal() + 1;
                    m.setApptMonthTotal(count);
                    break;
                }
            }
        }

        reportsMonthTableview.setItems(mlist);
        reportMonthTotalApptsCol.setCellValueFactory(new PropertyValueFactory<>("apptMonthTotal"));
        reportMonthTotalCol.setCellValueFactory(new PropertyValueFactory<>("apptMonth"));

    }

    /**This method is used to populate the Customer Report. */
    private void populateCustomerReport() {

        ObservableList<Customer> clist = FXCollections.observableArrayList();
        clist = CustomerDaoImpl.getAllCustomers();

        for (Appointment a: AppointmentDaoImpl.getAllAppointments()){
            for (Customer c: clist){
                if (a.getCustomerId() == c.getId()){
                    int count = c.getTotalAppointments() + 1;
                    c.setTotalAppointments(count);
                    break;
                }
            }
        }

        reportsCustomerTableview.setItems(clist);
        customerTotalApptsCol.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));
        customerTotalCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerIdTotalCol.setCellValueFactory(new PropertyValueFactory<>("id"));

    }

}

