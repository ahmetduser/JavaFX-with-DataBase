/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.TripCalculator;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author ahmetduser
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // conncetion between JavaFX with FXML file
        Parent root = FXMLLoader.load(getClass().getResource("FXMLMiniproject.fxml"));

        //Icon for the Application window
        final ImageView img = new ImageView();
        final Image imageIcon = new Image(Main.class.getResourceAsStream("applicationIcon.png"));
        img.setImage(imageIcon);
        primaryStage.getIcons().add(imageIcon);

        final Scene scene = new Scene(root);
        primaryStage.setTitle("ALSET Fuel Cost Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
