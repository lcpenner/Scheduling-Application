package controller;

import DAO.AppointmentDaoImpl;
import DAO.CustomerDaoImpl;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**This class is the controller for the Customers screen. */
public class CustomersController implements Initializable {

    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerDivCol;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    private TableColumn<Customer, String> customerPostalCodeCol;

    /**This method initializes the customer table for the Customer screen. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerTableView.setItems(CustomerDaoImpl.getAllCustomers());

        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerDivCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
    }

    /**This method sends the user to the Add Customer screen. */
    @FXML
    void onActionAdd(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/AddCustomer.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
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

/**This method sends the user to the Update Customer screen for the selected customer. */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {

        if (customerTableView.getSelectionModel().getSelectedItem() == null) {
            Alerts.showAlert(1);
            return;
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/UpdateCustomer.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            UpdateCustomerController UCustomerController = loader.getController();

            UCustomerController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setTitle("Scheduling Application");
            stage.setScene(scene);
            stage.show();

        }
    }

    /**This method deletes the selected customer from the database. */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {

        Customer customerToDelete = customerTableView.getSelectionModel().getSelectedItem();

        if (customerToDelete == null) {
            Alerts.showAlert(1);
            return;
        } else {

            for (Appointment a : AppointmentDaoImpl.getAllAppointments()) {
                if (a.getCustomerId() == customerToDelete.getId()) {
                    Alerts.showAlert(4);
                    return;
                }
            }

            if (customerToDelete == null) {
                Alerts.showAlert(1);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Customer");
                alert.setHeaderText("Delete");
                alert.setContentText("Are you sure you want to delete the selected customer?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    CustomerDaoImpl.deleteCustomer(customerToDelete.getId());
                }
                customerTableView.setItems(CustomerDaoImpl.getAllCustomers());

            }
        }


    }
}
