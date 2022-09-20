package schedulingApplicationMain;

import DAO.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import utility.JDBC;

import java.sql.Timestamp;

/**This class is the main class for the scheduling application program. */
public class Main extends Application {

    @Override
    public void init(){
        System.out.println("Starting");
    }

    /**This method brings the user to the Login screen. */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    @Override
    public void stop(){
        System.out.println("Terminated");
    }

    /**The main method opens the connection to the database then launches the program. */
    public static void main(String[] args) {

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }

}
