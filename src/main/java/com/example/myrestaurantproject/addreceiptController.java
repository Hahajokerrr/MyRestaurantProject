package com.example.myrestaurantproject;

import com.example.myrestaurantproject.DBConnection.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class addreceiptController implements Initializable {
    @FXML
    private DatePicker DatePicker;

    @FXML
    private ComboBox<String> MenuChooser;

    @FXML
    private ComboBox<Integer> RatingChooser;

    @FXML
    private TextField Review;

    @FXML
    private Text Success;

    private int userID;
    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;

    private ObservableList<String> Menu;
    private ObservableList<Integer> Rating;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setUserID(homepageController.getInstance().getIDInstance());
        handler = new DBHandler();

        Success.setVisible(false);

        connection = handler.getConnection();

        String s = String.valueOf(getUserID());
        Menu = FXCollections.observableArrayList();
        Rating = FXCollections.observableArrayList(1,2,3,4,5);

        String q = "Select MenuName from menu where users_id = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, s);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Menu.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MenuChooser.setItems(Menu);
        RatingChooser.setItems(Rating);
    }

    public String getDate() {
        if(DatePicker.getValue() != null) {
            LocalDate mydate = DatePicker.getValue();
            String myFormattedDate = mydate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return myFormattedDate;
        }
        else return "Error";
    }

    public void add() {
        if(MenuChooser.getValue()!=null && DatePicker.getValue()!=null
            && RatingChooser.getValue()!= null && Review.getText().length() > 0) {
            connection = handler.getConnection();
            String s = String.valueOf(getUserID());
            String q1 = "Select MenuID from menu where MenuName = ? and users_id = ?";
            String q2 = "insert into receipt(MenuID, Review, Rating, PaidDate) values (?,?,?,?)";
            try {
                PreparedStatement p1 = connection.prepareStatement(q1);
                p1.setString(1, MenuChooser.getValue());
                p1.setString(2, String.valueOf(getUserID()));

                String id = "";
                ResultSet r1 = p1.executeQuery();
                while (r1.next()) {
                    id = r1.getString(1);
                }
                PreparedStatement p2 = connection.prepareStatement(q2);
                p2.setString(1, id);
                p2.setString(2, Review.getText());
                p2.setString(3, String.valueOf(RatingChooser.getValue()));
                p2.setString(4, getDate());
                p2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Success.setVisible(true);
        }
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
