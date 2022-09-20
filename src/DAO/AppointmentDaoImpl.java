package DAO;

import model.Appointment;
import utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**This class is used for interfacing with the Appointment table in the database. */
public class AppointmentDaoImpl {

    /**This method returns all appointments in the appointments database table. */
    public static ObservableList<Appointment> getAllAppointments(){

        ObservableList<Appointment> alist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, " +
                    "appointments.Start, appointments.End, appointments.Customer_ID, appointments.User_ID, appointments.Contact_ID, contacts.Contact_Name " +
                    "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment A = new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId, contactName);
                alist.add(A);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return alist;

    }

/**This method inserts appointments into the database appointment table. */
    public static int insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {

        int rowsAffected = 0;

        try {

            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9, contactId);
            rowsAffected = ps.executeUpdate();

        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return rowsAffected;
    }

    /**This method updates appointments in the database appointments table. */
    public static int updateAppointment(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {

        int rowsAffected = 0;

        try {

            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7,customerId);
            ps.setInt(8,userId);
            ps.setInt(9,contactId);
            ps.setInt(10,appointmentId);

            rowsAffected = ps.executeUpdate();

        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return rowsAffected;
    }


    /**This method deletes appointments from the appointments database table. */
    public static int deleteAppointment(int appointmentId) {

        int rowsAffected = 0;

        try {

            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1,appointmentId);
            rowsAffected = ps.executeUpdate();

        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return rowsAffected;
    }

}

