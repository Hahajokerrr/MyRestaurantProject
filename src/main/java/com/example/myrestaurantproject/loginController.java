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
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        progress.setVisible(false);
        username.setStyle("-fx-text-inner-color: #a0a2ab");
        password.setStyle("-fx-text-inner-color: #a0a2ab");
    }

    @FXML
    public void loginAction(ActionEvent e) {
        progress.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(1));
        pt.setOnFinished(ev -> {
            System.out.print("Login Successfully");
        });

        pt.play();
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