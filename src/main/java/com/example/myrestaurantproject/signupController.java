package com.example.myrestaurantproject;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.myrestaurantproject.DBConnection.DBHandler;

public class signupController implements Initializable {
    @FXML
    private Button signup;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField name;

    @FXML
    private Button login;

    @FXML
    private ImageView progress;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        progress.setVisible(false);
        username.setStyle("-fx-text-inner-color: #a0a2ab");
        password.setStyle("-fx-text-inner-color: #a0a2ab");
        name.setStyle("-fx-text-inner-color: #a0a2ab");

        handler = new DBHandler();
    }

    @FXML
    public void signupAction(ActionEvent ev) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition(Duration.seconds(1));
        pt.setOnFinished(e -> {
            System.out.println("Sign up Successful");
        });
        pt.play();

        // Saving new user
        String insert = "INSERT INTO users(username, password, res_name)"
                + "VALUES(?,?,?)";
        connection = handler.getConnection();
        try {
            pst = connection.prepareStatement(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            pst.setString(1, username.getText());
            pst.setString(2, password.getText());
            pst.setString(3, name.getText());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loginAction(ActionEvent ev) throws IOException {
        signup.getScene().getWindow().hide();

        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(root);
        login.setScene(scene);
        login.show();
        login.setResizable(false);
    }
}
