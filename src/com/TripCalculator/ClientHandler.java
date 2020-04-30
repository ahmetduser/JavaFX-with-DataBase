/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.TripCalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
import java.sql.PreparedStatement;
import java.text.MessageFormat;
import static com.TripCalculator.Server.costOfFuel;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author ahmetduser
 */
// FXMLMiniProjectController.java client
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    double distance;
    double MPG;
    String type;
    String userName;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try {
            objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
            objectIn = new ObjectInputStream(clientSocket.getInputStream());

            Parameters par = (Parameters) objectIn.readObject();

            distance = par.getDistance();
            MPG = par.getMPG();
            type = par.getFuelType();
            userName = par.getUserName();

            if ("98-Octane".equalsIgnoreCase(type)) {
                costOfFuel = (distance / (MPG * 0.219969248)) * fuelRead("98-Octane");
                par.setCost(costOfFuel);
                objectOut.writeObject(par);
            } else if ("Diesel".equalsIgnoreCase(type)) {
                costOfFuel = (distance / (MPG * 0.219969248)) * fuelRead("Diesel");
                par.setCost(costOfFuel);
                objectOut.writeObject(par);
            }

        } catch (IOException ex) {
            System.err.println("Server exception: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println("Class Not Found: " + ex.getMessage());
        }

        // sends user names to the database
        usersTable(userName);
        // sends trip informations to the database
        tripInformationTable(userName, distance, MPG, type, costOfFuel);

    }

    public static void usersTable(String userName) {

        try (Connection conn = GetDatabaseConnection()) {
            System.out.println("Database connected!");

            // The mysql insert statement for table users_table
            String query = " insert into users_table (User_Name, date)"
                    + " values (?, ?)";

            // Create a sql date object so we can use it in our INSERT statement
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

            // Create the mysql insert prepared statement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, userName);
            preparedStmt.setDate(2, sqlDate);

            // Execute the preparedstatement
            preparedStmt.execute();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect to the database " + e.getMessage());
        }
    }

    public static void tripInformationTable(String userName, double distance, double MPG, String type, double cost) {

        try (Connection conn = GetDatabaseConnection()) {
            System.out.println("Database connected!");

            // The mysql insert statement for table trip_information
            String query = " insert into trip_information (User_Name, distance, MPG, fuelType, cost, date)"
                    + " values (?, ?, ?, ?, ?, ?)";

            // Create a sql date object so we can use it in our INSERT statement
            java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

            // Create the mysql insert prepared statement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, userName);
            preparedStmt.setDouble(2, distance);
            preparedStmt.setDouble(3, MPG);
            preparedStmt.setString(4, type);
            preparedStmt.setDouble(5, cost);
            preparedStmt.setDate(6, sqlDate);

            // Execute the preparedstatement
            preparedStmt.execute();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect to the database " + e.getMessage());
        } catch (com.mysql.cj.exceptions.WrongArgumentException e) {
            System.out.println("Wrong Argument Exception " + e.getMessage());
        }

    }

    // Retrieve the costs of the per fuel from fuel_costs table
    public static double fuelRead(String fuelType) {
        double result = 0;
        try (Connection conn = GetDatabaseConnection()) {
            System.out.println("Database connected!");

            // create the java statement
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(" select * from fuel_costs where fuel_type='" + fuelType + "'");
            while (rs.next()) {
                result = rs.getDouble("cost");
            }
            conn.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        return result;
    }

    // returns DataBase connection
    public static Connection GetDatabaseConnection() {
        Connection connection = null;

        String dbUrl = "jdbc:mysql://localhost:3306/MiniProject";       // hostname:localhost, portNumber:3306, database: MiniProject
        String user = "root";       //username
        String pass = ""; //password
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

    @Override
    public String toString() {
        return MessageFormat.format("\nDistance:{0} MPG:{1} Fuel Type:{2} Cost:{3}", distance, MPG, type, costOfFuel);
    }

}
