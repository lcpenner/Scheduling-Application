package controller;

import DAO.CountryDaoImpl;
import DAO.CustomerDaoImpl;
import DAO.FirstLevelDivisionDaoImpl;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import utility.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**This class is the controller for the Update Customer screen. */
public class UpdateCustomerController implements Initializable {

    private Customer customerToUpdate = null;

    private boolean firstLoad = true;

    /**This method is used to send the selected customer to the Update Customer screen. */
    public void sendCustomer(Customer customer)
    {
        this.customerToUpdate = customer;

        updateCustomerId.setText(String.valueOf(customerToUpdate.getId()));
        updateCustomerNameTxt.setText(customerToUpdate.getName());
        updateCustomerAddressTxt.setText(customerToUpdate.getAddress());
        updateCustomerPhoneTxt.setText(customerToUpdate.getPhone());
        updateCustomerPostalCodeTxt.setText(customerToUpdate.getPostalCode());

        updateCountryCombo.setItems(getFilteredCountryList());
        updateCountryCombo.getSelectionModel().selectFirst();

        updateFirstLevelDivisionCombo.setItems(getFilteredFirstLevelDivisions());
        updateFirstLevelDivisionCombo.getSelectionModel().selectFirst();


    }

    @FXML
    private ComboBox<Country> updateCountryCombo;

    @FXML
    private ComboBox<FirstLevelDivision> updateFirstLevelDivisionCombo;

    @FXML
    private TextField updateCustomerAddressTxt;

    @FXML
    private TextField updateCustomerNameTxt;

    @FXML
    private TextField updateCustomerPhoneTxt;

    @FXML
    private TextField updateCustomerPostalCodeTxt;

    @FXML
    private TextField updateCustomerId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**This method sends the user back to the Customer screen. */
    @FXML
    void onActionCancelUpdate(ActionEvent event)throws IOException {

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Customers.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method saves the updated customer information to the database then returns to the customer screen. */
    @FXML
    void onActionSaveUpdate(ActionEvent event) throws IOException {
        try {
            String name = updateCustomerNameTxt.getText();
            String address = updateCustomerAddressTxt.getText();
            String postalCode = updateCustomerPostalCodeTxt.getText();
            String phone = updateCustomerPhoneTxt.getText();
            int divisionId = updateFirstLevelDivisionCombo.getSelectionModel().getSelectedItem().getId();

            CustomerDaoImpl.updateCustomer(customerToUpdate.getId(), name, address, postalCode, phone, divisionId);
        }
        catch(Exception e){
        Alerts.showAlert(6);
        return;
    }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Customers.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method returns a list of divisions that match the selected country ID. */
    public ObservableList<FirstLevelDivision> getFilteredFirstLevelDivisions(){

     ObservableList<FirstLevelDivision> fdlist = FXCollections.observableArrayList();

     if (firstLoad){
           for (FirstLevelDivision division: FirstLevelDivisionDaoImpl.getAllDivisions()){
               if (division.getId() == customerToUpdate.getDivisionId()){
                   fdlist.add(division);
                 }
            }
           firstLoad = false;
      }

     for (FirstLevelDivision division: FirstLevelDivisionDaoImpl.getAllDivisions()){
            if (division.getCountryId() == updateCountryCombo.getSelectionModel().getSelectedItem().getId()){
                fdlist.add(division);
            }
        }

     return fdlist;
    }


    /**This method returns a list of countries starting with the selected customer's associated country. */
    public ObservableList<Country> getFilteredCountryList(){

        ObservableList<Country> clist = FXCollections.observableArrayList();
        int countryId;

        if (customerToUpdate.getDivisionId() <= 54 ){
            countryId = 1;
        }
        else if (customerToUpdate.getDivisionId() > 54 && customerToUpdate.getDivisionId() <=72 )
        {
            countryId = 3;
        }
        else{
            countryId = 2;
        }

        for (Country country: CountryDaoImpl.getAllCountries()){
            if (country.getId() == countryId){
                clist.add(country);
            }
        }

        for (Country country: CountryDaoImpl.getAllCountries()){
            if (country.getId() != countryId){
                clist.add(country);
            }
        }

        return clist;
    }

    /**This method updates the division list when a new country is selected. */
    @FXML
    void onActionUpdateCountryChange(ActionEvent event) {
        updateFirstLevelDivisionCombo.setItems(getFilteredFirstLevelDivisions());
        updateFirstLevelDivisionCombo.getSelectionModel().selectFirst();
    }

}
