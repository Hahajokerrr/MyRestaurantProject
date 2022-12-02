package com.example.myrestaurantproject;

import com.example.myrestaurantproject.DBConnection.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.security.auth.login.LoginContext;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class homepageController implements Initializable {
    @FXML
    private Label Resname;
    @FXML
    private Button AllMenu;
    @FXML
    private Button AddMenu;
    @FXML
    private Button AllDish;
    @FXML
    private Button AddDish;
    @FXML
    private Button AddReceipt;

    private int IDInstance;
    private String ResNameInstance;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;

    private static homepageController instance;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handler = new DBHandler();
        setUpFisrtTime();
        Resname.setText("Welcome to " + ResNameInstance);
    }

    public void setUpFisrtTime() {
        connection = handler.getConnection();
        String q = "SELECT * from users where username = ?";
        try {
            PreparedStatement t1 = connection.prepareStatement(q);
            t1.setString(1, loginController.getInstance().getUsername());
            ResultSet rs = t1.executeQuery();
            while(rs.next()) {
                setIDInstance(rs.getInt("users_id"));
                setResNameInstance(rs.getString("res_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void allDishAction() {
        try {
            Stage allDish = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("DishPage.fxml"));
            Scene scene = new Scene(root);
            allDish.setScene(scene);
            allDish.show();
            allDish.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addDishAction() {
        try {
            Stage addDish = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddDishPage.fxml"));
            Scene scene = new Scene(root);
            addDish.setScene(scene);
            addDish.show();
            addDish.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void allMenuAction() {
        try {
            Stage allMenu = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AllMenu.fxml"));
            Scene scene = new Scene(root);
            allMenu.setScene(scene);
            allMenu.show();
            allMenu.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public homepageController() {
        instance = this;
    }

    public static homepageController getInstance() {
        return instance;
    }

    public void setIDInstance(int IDInstance) {
        this.IDInstance = IDInstance;
    }

    public void setResNameInstance(String resNameInstance) {
        ResNameInstance = resNameInstance;
    }

    public int getIDInstance() {
        return IDInstance;
    }

    public String getResNameInstance() {
        return ResNameInstance;
    }

}
