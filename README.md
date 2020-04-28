Task: To calculate the overall cost of business trips they need to know a close approximation of fuel cost.

A GUI based, stand-alone application that takes 5 parameters from the user; 
1-the distance in miles, 
2-cars fuel efficiency in miles per gallon,
3-the type of the fuel (98-Octane and Diesel), the costs of the fuels are in per liter. The application should provide an ability to pick which fuel is used in the algorithm,
4-user name,
5-the user will have the option of displaying the past trips(Within 3 days, Within 7 days, and Show all).

The cost of fuel per litter is read from the DataBase.The parameters used to calculate the cost of fuel as well as the final cost is saved in the DataBase.

The data is transferred between client and server using a dedicated class object. The class will contain all the parameters required by the algorithm. 

The calculation and writing to a file is take place on server side, and the results is transferred to a client and displayed on client side. 
This design of application is thin client fat server.
The server is multithreaded and a ClientHandler is used.

Implementation Notes:

The layout of the application done by using FXML along with the Scene Builder.
Java8.
MySQL-Connector-java-8.0.19.jar ( To be able to use sql-connector import "com.mysql.jdbc.Driver;").
https://dev.mysql.com/downloads/connector/j/
Apache NetBeans 11.

DataBase Description:

CREATE TABLE users_table(
	User_ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	User_Name VARCHAR(20)
);


CREATE TABLE trip_information(
	ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	User_Name VARCHAR(20) NOT NULL,
	distance DOUBLE,
	MPG DOUBLE,
	fuelType VARCHAR(20),
	cost DOUBLE,
	date DATE
);


CREATE TABLE fuel_costs(
	ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	fuel_type VARCHAR(20),
	cost DOUBLE
);




