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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    @FXML
    private Text usernameLength;
    @FXML
    private Text usernameFilled;
    @FXML
    private Text usernameExisted;
    @FXML
    private Text passwordLength;
    @FXML
    private Text passwordFilled;
    @FXML
    private Text resnameFilled;

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

    public int getUsernameError() {
        int res = 0;
        String s = username.getText();
        connection = handler.getConnection();
        String q1 = "SELECT * from users where username = ?";
        try {
            PreparedStatement temp = connection.prepareStatement(q1);
            temp.setString(1, s);
            ResultSet rs = temp.executeQuery();
            int count = 0;

            while (rs.next()) {
                count++;
            }
            if(count >= 1) {
                res = 3;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(s.length() == 0) {
            res = 1;
        } else if(s.length() < 8) {
            res = 2;
        }
        return res;
    }

    public int getPasswordError() {
        int res = 0;
        String temp = password.getText();
        if(temp.length() == 0) {
            res = 1;
        } else if(temp.length() < 8) {
            res = 2;
        }
        return res;
    }

    public int getResNameError() {
        int res = 0;
        String temp = name.getText();
        if(temp.length() == 0) {
            res = 1;
        }
        return res;
    }

    @FXML
    public void signupAction(ActionEvent ev) {

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
            if(getUsernameError() >= 1 || getPasswordError() >= 1 || getResNameError() >= 1) {
                System.out.println(getUsernameError() + " " + getPasswordError() + " " + getResNameError());

                switch (getUsernameError()) {
                    case 0:
                        usernameFilled.setVisible(false);
                        usernameExisted.setVisible(false);
                        usernameLength.setVisible(false);
                    case 1:
                        usernameFilled.setVisible(true);
                        usernameExisted.setVisible(false);
                        usernameLength.setVisible(false);
                        break;
                    case 2:
                        usernameLength.setVisible(true);
                        usernameFilled.setVisible(false);
                        usernameExisted.setVisible(false);
                        break;
                    case 3:
                        usernameExisted.setVisible(true);
                        usernameLength.setVisible(false);
                        usernameFilled.setVisible(false);
                        break;
                }
                switch (getPasswordError()) {
                    case 0:
                        passwordFilled.setVisible(false);
                        passwordLength.setVisible(false);
                    case 1:
                        passwordFilled.setVisible(true);
                        passwordLength.setVisible(false);
                        break;
                    case 2:
                        passwordFilled.setVisible(false);
                        passwordLength.setVisible(true);
                        break;
                }
                switch (getResNameError()) {
                    case 0:
                        resnameFilled.setVisible(false);
                        break;
                    case 1:
                        resnameFilled.setVisible(true);
                        break;
                }
            } else {
                usernameFilled.setVisible(false);
                usernameExisted.setVisible(false);
                usernameLength.setVisible(false);
                passwordFilled.setVisible(false);
                passwordLength.setVisible(false);
                resnameFilled.setVisible(false);

                pst.setString(1, username.getText());
                pst.setString(2, password.getText());
                pst.setString(3, name.getText());



                pst.executeUpdate();

                progress.setVisible(true);
                PauseTransition pt = new PauseTransition(Duration.seconds(1));
                pt.setOnFinished(e -> {
                    //System.out.println("Sign up Successful");
                    progress.setVisible(false);
                });
                pt.play();
            }
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
