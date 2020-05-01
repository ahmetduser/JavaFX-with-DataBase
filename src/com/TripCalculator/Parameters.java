/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.TripCalculator;

import java.io.Serializable;

/**
 *
 * @author ahmetduser
 */
public class Parameters implements Serializable {

    double distance;
    double MPG;
    String fuelType;
    double cost;
    String userName;
    String error;

    public Parameters() {

    }

    public Parameters(double distance, double MPG, String fuelType, double cost, String userName, String error) {
        this.distance = distance;
        this.MPG = MPG;
        this.fuelType = fuelType;
        this.cost = cost;
        this.userName = userName;
        this.error = error;
    }

    // setters
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setMPG(double MPG) {
        this.MPG = MPG;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public void setError(String error){
        this.error = error;
    }

    // getters
    public String getUserName() {
        return this.userName;
    }

    public double getDistance() {
        return this.distance;
    }

    public double getMPG() {
        return this.MPG;
    }

    public String getFuelType() {
        return this.fuelType;
    }

    public double getCost() {
        return this.cost;
    }
    
    public String getError(){
        return this.error;
    }

    @Override
    public String toString() {
        return " User Name: " + getUserName() + " Distance:" + getDistance() + " MPG:" + getMPG() + " Fuel Type:" + getFuelType() + " Cost:" + getCost();
    }

}
