package com.example.myrestaurantproject.DBConnection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHandler extends Configs{
    Connection dbConnection;

    public Connection getConnection() {
        String connectionString = "jdbc:mysql://" + dbhost + ":" + dbport
                + "/" + dbname;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            dbConnection = DriverManager.getConnection(connectionString, dbuser, dbpass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dbConnection;
    }
}
