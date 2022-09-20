package utility;

import schedulingApplicationMain.JDBCInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**This class is used for connecting and disconnecting from the database. */
public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface

    /**Lambda expression #2. Used to display updates on the status of interacting with the database. Simplifies the code for displaying updates. */
    public static JDBCInterface message = s -> System.out.println(s);

    /**This method opens up connection to the database. */
    public static Connection openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object

            /**Lambda expression #2. Used to display updates on the status of interacting with the database. Simplifies the code for displaying updates. */
            message.displayMessage("Connection successful!");

        } catch(SQLException e){
            e.printStackTrace();
            //System.out.println("Error:" + e.getMessage());
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            //System.out.println("Error:" + e.getMessage());
        }
        return connection;
    }

    /**This method gets the connection to the database once connection has been established. */
    public static Connection getConnection(){
        return connection;
    }

    /**This method closes the connection to the database. */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            //do nothing
        }
    }
}
