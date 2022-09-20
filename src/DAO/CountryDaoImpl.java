package DAO;

import utility.JDBC;
import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**This class is used for interfacing with the Country table in the database. */
public class CountryDaoImpl {

    /**This method returns all countries in the Countries database table. */
    public static ObservableList<Country> getAllCountries(){

        ObservableList<Country> clist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country C = new Country(countryId, countryName);
                clist.add(C);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return clist;

    }


}