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
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        progress.setVisible(false);
        username.setStyle("-fx-text-inner-color: #a0a2ab");
        password.setStyle("-fx-text-inner-color: #a0a2ab");
        name.setStyle("-fx-text-inner-color: #a0a2ab");
    }

    @FXML
    public void signupAction(ActionEvent ev) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition(Duration.seconds(1));
        pt.setOnFinished(e -> {
            System.out.println("Sign up Successful");
        });
        pt.play();

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
