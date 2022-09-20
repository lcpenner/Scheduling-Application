package DAO;

import model.FirstLevelDivision;
import utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**This class is used for interfacing with the First Level Divisions table in the database. */
public class FirstLevelDivisionDaoImpl {

    /**This method returns all divisions in the First Level Divisions database table. */
    public static ObservableList<FirstLevelDivision> getAllDivisions(){

        ObservableList<FirstLevelDivision> dlist = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");
                FirstLevelDivision D = new FirstLevelDivision(divisionId, divisionName, countryId);
                dlist.add(D);
            }
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return dlist;

    }


}
