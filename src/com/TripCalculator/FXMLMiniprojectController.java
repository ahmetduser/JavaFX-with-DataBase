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
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ahmetduser
 */
public class FXMLMiniprojectController implements Initializable {

    DecimalFormat df; //Decimal format for cost of fuel result
    public String type;

    //Server connection
    Socket socket;
    ObjectOutputStream objectOut;
    ObjectInputStream objectIn;

    @FXML
    private RadioButton radioButton, radioButton1;

    @FXML
    private TextField txtGetDistance, txtGetMPG;

    @FXML
    private Label lblResult, lblError;

    @FXML
    private Label inputError, inputError1;

    @FXML
    private Label lblTripInfo;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label lblUserNameError;

    @FXML
    private ListView listView;

    @FXML
    private ListView recordTime;
    ObservableList<String> observableList = FXCollections.observableArrayList("Within 3 days", "Within 7 days", "Show all");

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        // display with 2 decimal points
        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        double distance = 0;
        double MPG = 0;

        socket = new Socket("localhost", 9999);
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectIn = new ObjectInputStream(socket.getInputStream());
        
        // If-Else blocks for checking the user's input
        if (txtGetDistance.getText().isEmpty() && txtGetMPG.getText().isEmpty()) {
            lblError.setText("Please enter the paramteres");
        } else if (txtGetDistance.getText().isEmpty()) {
            lblError.setText("Please enter distance value");
        } else if (txtGetMPG.getText().isEmpty()) {
            lblError.setText("Please enter MPG value");
        } else if (radioButton.isSelected() == false && radioButton1.isSelected() == false) {
            lblError.setText("Please select fuel option");
        } else if ("0".equals(txtGetMPG.getText())) {
            inputError1.setText("0 is not valid MPG value");
        } else if ("".equals(txtUserName.getText())) {
            lblUserNameError.setText("Please enter a user name");
        } else {
            // remove lblUserNameError
            lblUserNameError.setText("");
            try {
                // Parse the user's distance input into double
                distance = Double.parseDouble(txtGetDistance.getText());
                inputError.setText(""); // remove the error message
            } catch (NumberFormatException e) {
                inputError.setText("Please enter only number values");
            }

            try {
                // Parse the user's MPG input into double
                MPG = Double.parseDouble(txtGetMPG.getText());
                inputError1.setText(""); // remove the error message
            } catch (NumberFormatException e) {
                inputError1.setText("Please enter only number values");
            }
            lblError.setText(""); // remove the error message

        }
        
        // !! After user's input passes the if-else checking part...
        if (radioButton.isSelected()) {
            type = "98-Octane";
            // send the object with the user's inputs to the Server
            objectOut.writeObject(new Parameters(distance, MPG, type, 0, txtUserName.getText()));// cost variable's value is 0 for now!!
            double result = 0;
            try {
                // read the object that is sent from the Server
                Parameters par = (Parameters) objectIn.readObject();
                result = par.getCost();
            } catch (ClassNotFoundException ex) {
                lblResult.setText("Class Not Found");
            }
            lblResult.setText(" £" + df.format(result));  // radioButton

        } else {
            type = "Diesel";
            // send the object with the user's inputs to the Server
            objectOut.writeObject(new Parameters(distance, MPG, type, 0, txtUserName.getText()));// cost variable's value is 0 for now!!
            double result = 0;
            try {
                // read the object that is sent from the Server
                Parameters par = (Parameters) objectIn.readObject();
                result = par.getCost();
            } catch (ClassNotFoundException ex) {
                lblResult.setText("Class Not Found");
            }
            lblResult.setText(" £" + df.format(result)); // radioButton1
        }

        lblTripInfo.setText("Distance: " + distance
                + "\nMiles per Galon:  " + MPG
                + "\nFuel type: " + type);

    }

    @FXML
    private void handleRecordAction(ActionEvent event) throws IOException {
        String selectedString = (String) recordTime.getSelectionModel().getSelectedItem();

        if (null != selectedString) {
            switch (selectedString) {
                case "Within 3 days":
                    listView.setItems(Record.recordTime("3",txtUserName.getText()));
                    break;
                case "Within 7 days":
                    listView.setItems(Record.recordTime("7",txtUserName.getText()));
                    break;
                case "Show all":
                    listView.setItems(Record.getAllInfo(txtUserName.getText()));
                    break;
                default:
                    break;
            }
        }

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recordTime.setItems(observableList);
    }

}
