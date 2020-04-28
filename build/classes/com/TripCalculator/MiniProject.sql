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

CREATE TABLE validation(
	ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	User_Name VARCHAR(20) UNIQUE
);


#To find rows created within the 3 days:
SELECT * FROM MiniProject.trip_information where date > date_sub(now(), interval 2 day);

CREATE TABLE fuel_costs(
	ID INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	fuel_type VARCHAR(20),
	cost DOUBLE
);