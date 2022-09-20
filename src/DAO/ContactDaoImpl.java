package DAO;

import utility.JDBC;
import model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**This class is used for interfacing with the Contacts table in the database. */
public class ContactDaoImpl {

    /**This method returns all contacts in the Contacts database table. */
    public static ObservableList<Contact> getAllContacts(){

        ObservableList<Contact> clist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact C = new Contact(contactId, contactName, email);
                clist.add(C);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return clist;

    }


}