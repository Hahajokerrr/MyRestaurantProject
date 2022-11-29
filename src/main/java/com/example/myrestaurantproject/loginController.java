package com.example.myrestaurantproject;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.myrestaurantproject.DBConnection.DBHandler;

public class loginController implements Initializable {

    @FXML
    private Button signup;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private CheckBox remember;

    @FXML
    private Button login;

    @FXML
    private ImageView progress;
    @FXML
    private Text incorrect;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        progress.setVisible(false);
        incorrect.setVisible(false);
        username.setStyle("-fx-text-inner-color: #a0a2ab");
        password.setStyle("-fx-text-inner-color: #a0a2ab");

        handler = new DBHandler();
    }

    @FXML
    public void loginAction(ActionEvent e) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(1));
        pt.setOnFinished(ev -> {
            System.out.print("Login Successful");
        });

        pt.play();

        //Retrieve user information
        connection = handler.getConnection();
        String q1 = "SELECT * from users where username = ? and password = ?";

        try {
            pst = connection.prepareStatement(q1);
            pst.setString(1,username.getText());
            pst.setString(2,password.getText());
            ResultSet rs = pst.executeQuery();

            int count = 0;

            while (rs.next()) {
                count++;
            }
            if(count == 1) {
                System.out.println("Login Successful");
            } else {
                incorrect.setStyle("-fx-text-inner-color: #ff0000");
                incorrect.setVisible(true);
            }

        } catch (SQLException e0) {
            e0.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @FXML
    public void signupAction(ActionEvent e) throws IOException {
        login.getScene().getWindow().hide();
        Stage SignUp = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(root);
        SignUp.setScene(scene);
        SignUp.show();
        SignUp.setResizable(false);
    }
}