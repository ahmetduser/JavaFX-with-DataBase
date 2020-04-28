/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.TripCalculator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.mysql.jdbc.Driver;
import java.text.DecimalFormat;

/**
 *
 * @author ahmetduser
 */
public class Record {

    // returns ObservableList which contains trip information record
    public static ObservableList<String> getAllInfo(String user) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        ObservableList<String> list = FXCollections.observableArrayList();
        while (true) {
            try (Connection conn = GetDatabaseConnection()) {
                System.out.println("Fetching data...");

                // create the java statement
                Statement st = conn.createStatement();

                // SQL SELECT query.
                // Execute the query, and get a java resultset
                // Select everything from table called trip_information where the User_Name row is equals to 'user'
                ResultSet rs = st.executeQuery("select * from trip_information where User_Name='" + user + "'");

                while (rs.next()) {
                    // Add the informations into the ObservableList
                    list.addAll("User Name:" + String.valueOf(rs.getNString("User_Name"))
                            + " |Distance:" + String.valueOf(rs.getDouble("distance"))
                            + " |MPG:" + String.valueOf(rs.getDouble("MPG"))
                            + " |Fuel Type:" + rs.getString("fuelType")
                            + " |Cost:" + String.valueOf(df.format(rs.getDouble("cost")))
                            + " |Date:" + String.valueOf(rs.getDate("date")));
                }
                System.out.println("Data Fetched");
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Record class, SQL Exception " + ex.getMessage());
            }
            return list;
        }
    }

    // returns ObservableList which contains the trip information within the specified timespan
    public static ObservableList<String> recordTime(String time, String user) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        ObservableList<String> list = FXCollections.observableArrayList();
        while (true) {
            try (Connection conn = GetDatabaseConnection()) {
                System.out.println("Fetching Interval data...");

                // create the java statement
                Statement st = conn.createStatement();

                // SQL SELECT Date query.
                // execute the query, and get a java resultset
                // select everything from table ,that is in time interval and belongs to the 'user', called trip_information
                ResultSet rs = st.executeQuery("SELECT * FROM MiniProject.trip_information where date > date_sub(now(), interval " + time + " day) "
                        + "and User_Name='" + user + "'");

                while (rs.next()) {
                    // Add the informations into the ObservableList
                    list.addAll("User Name:" + String.valueOf(rs.getNString("User_Name"))
                            + " |Distance:" + String.valueOf(rs.getDouble("distance"))
                            + " |MPG:" + String.valueOf(rs.getDouble("MPG"))
                            + " |Fuel Type:" + rs.getString("fuelType")
                            + " |Cost:" + String.valueOf(df.format(rs.getDouble("cost")))
                            + " |Date:" + String.valueOf(rs.getDate("date")));
                }
                System.out.println("TimeSpan-Data Fetched");
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Record class, SQL Exception " + ex.getMessage());
            }
            return list;
        }
    }
    
    // returns DataBase connection
    public static Connection GetDatabaseConnection() {
        Connection connection = null;

        String dbUrl = "jdbc:mysql://localhost:3306/MiniProject"; // hostname:localhost, portNumber:3306, database: MiniProject
        String user = "root";
        String pass = "";
        try {
            //driver setup for database
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, user, pass);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
        return connection;
    }
}
