package DAO;


import utility.JDBC;
import model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**This class is used for interfacing with the User table in the database. */
public class UserDaoImpl {

    /**This method returns all users in the Users database table. */
    public static ObservableList<User> getAllUsers(){

        ObservableList<User> ulist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM users";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User U = new User(userId, userName, password);
                ulist.add(U);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return ulist;

    }


}