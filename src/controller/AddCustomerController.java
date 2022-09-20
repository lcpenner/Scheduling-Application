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
import model.FirstLevelDivision;
import utility.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**This class is the controller for the Add Customer screen. */
public class AddCustomerController implements Initializable {


    @FXML
    public ComboBox<Country> countryCombo;

    @FXML
    public ComboBox<FirstLevelDivision> firstLevelDivisionCombo;

    @FXML
    private TextField addCustomerAddressTxt;

    @FXML
    private TextField addCustomerNameTxt;

    @FXML
    private TextField addCustomerPhoneTxt;

    @FXML
    private TextField addCustomerPostalCodeTxt;

/**This method initializes the selection options for the Add Customer screen. */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

       countryCombo.setItems(CountryDaoImpl.getAllCountries());
       countryCombo.getSelectionModel().selectFirst();

       firstLevelDivisionCombo.setItems(getFilteredFirstLevelDivisions());
       firstLevelDivisionCombo.getSelectionModel().selectFirst();

    }

    /**This method returns the user to the Customer screen. */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("../view/Customers.fxml"));
        stage.setTitle("Scheduling Application");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method returns a list of divisions that match the selected country ID. */
    public ObservableList<FirstLevelDivision> getFilteredFirstLevelDivisions(){

        ObservableList<FirstLevelDivision> fdlist = FXCollections.observableArrayList();

        for (FirstLevelDivision division: FirstLevelDivisionDaoImpl.getAllDivisions()){
            if (division.getCountryId() == countryCombo.getSelectionModel().getSelectedItem().getId()){
                fdlist.add(division);
            }
        }

        return fdlist;
    }

    /**This method updates the available divisions when the selected country is changed. */
    @FXML
    void onActionCountryChange(ActionEvent event) {
        firstLevelDivisionCombo.setItems(getFilteredFirstLevelDivisions());
        firstLevelDivisionCombo.getSelectionModel().selectFirst();
    }

    /**This method saves the new customer to the database. */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        try {
            if (addCustomerNameTxt.getText().isEmpty() || addCustomerAddressTxt.getText().isEmpty() ||
                    addCustomerPhoneTxt.getText().isEmpty() || addCustomerPostalCodeTxt.getText().isEmpty()){
                Alerts.showAlert(7);
                return;
            }
            else {

                String name = addCustomerNameTxt.getText();
                String address = addCustomerAddressTxt.getText();
                String postalCode = addCustomerPostalCodeTxt.getText();
                String phone = addCustomerPhoneTxt.getText();
                int divisionId = firstLevelDivisionCombo.getSelectionModel().getSelectedItem().getId();

                CustomerDaoImpl.insertCustomer(name, address, postalCode, phone, divisionId);
            }
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

}
