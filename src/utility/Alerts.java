package utility;

import javafx.scene.control.Alert;

/**This class is used for generating alerts. */
public class Alerts {

    /**This method displays an alert depending on the alert case. */
    public static void showAlert(int alertCase) {

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertCase) {
            case 1:
                alertError.setTitle("Error");
                alertError.setHeaderText("Customer not selected");
                alertError.setContentText("Please select customer in table");
                alertError.showAndWait();
                break;

             case 2:
             alertError.setTitle("Error");
             alertError.setHeaderText("Appointment not selected");
             alertError.setContentText("Please select appointment in table");
             alertError.showAndWait();
             break;

             case 3:
             alertError.setTitle("Error");
             alertError.setHeaderText("Customer has an appointment conflict");
             alertError.showAndWait();
             break;

             case 4:
             alertError.setTitle("Error");
             alertError.setHeaderText("All of customer's appointments must be deleted first before deleting the customer.");
             alertError.showAndWait();
             break;

             case 5:
             alertError.setTitle("Error");
             alertError.setHeaderText("Appointment end time is before start time.");
             alertError.showAndWait();
             break;

            case 6:
                alertError.setTitle("Error");
                alertError.setHeaderText("Please enter valid values into each field.");
                alertError.showAndWait();
                break;

            case 7:
                alertError.setTitle("Error");
                alertError.setHeaderText("Name, Address, Phone, Postal Code cannot be left blank.");
                alertError.showAndWait();
                break;

        }
    }
}
