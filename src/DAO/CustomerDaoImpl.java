package DAO;

import model.Customer;
import utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**This class is used for interfacing with the Customer table in the database. */
public class CustomerDaoImpl {

    /**This method returns all customers in the Customers database table. */
    public static ObservableList<Customer> getAllCustomers(){

        ObservableList<Customer> clist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, " +
                    "customers.Division_ID, first_level_divisions.Division FROM customers INNER JOIN first_level_divisions " +
                    "ON customers.Division_ID = first_level_divisions.Division_ID";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");

                Customer C = new Customer(customerId, customerName, address, postalCode, phone, divisionId, divisionName);
                clist.add(C);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return clist;

    }

    /**This method inserts customers into the database Customer table. */
    public static int insertCustomer(String name, String address, String postalCode, String phone, int divisionId) {

        int rowsAffected = 0;

        try {

            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?,?,?,?,?)";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);

            rowsAffected = ps.executeUpdate();

        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return rowsAffected;
    }

    /**This method updates customers in the database customer table. */
    public static int updateCustomer(int customerId, String name, String address, String postalCode, String phone, int divisionId) {

        int rowsAffected = 0;

        try {

            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            ps.setInt(6, customerId);

            rowsAffected = ps.executeUpdate();

        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return rowsAffected;
    }

    /**This method deletes customers in the database customer table. */
    public static int deleteCustomer(int customerId) {

        int rowsAffected = 0;

        try {

            String sql = "DELETE FROM customers WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ps.setInt(1,customerId);
            rowsAffected = ps.executeUpdate();

        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return rowsAffected;
    }

}



