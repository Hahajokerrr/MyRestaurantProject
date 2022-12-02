package com.example.myrestaurantproject;

import com.example.myrestaurantproject.DBConnection.DBHandler;
import com.example.myrestaurantproject.commonObject.Dish;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adddishpageController implements Initializable {
    @FXML
    private Button Add;

    @FXML
    private TextField Description;

    @FXML
    private TextField Name;

    @FXML
    private TextField Price;

    @FXML
    private ComboBox<String> Style;

    @FXML
    private ComboBox<String> Type;

    @FXML
    private Text DesError;

    @FXML
    private Text PriceError;

    @FXML
    private Text dishNameError;

    @FXML
    private Text Success;

    @FXML
    private Text NameExisted;

    private int userID;
    private Dish dish;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Style.setItems(StyleTypeList.StyleList);
        Type.setItems(StyleTypeList.TypeList);
        setUserID(homepageController.getInstance().getIDInstance());
        handler = new DBHandler();


        DesError.setVisible(false);
        dishNameError.setVisible(false);
        PriceError.setVisible(false);
        Success.setVisible(false);
        NameExisted.setVisible(false);
    }

    public void add() {
        if(Name.getText().length() == 0) {
            dishNameError.setVisible(true);
        } else {
            dishNameError.setVisible(false);
        }

        if(getExistedDishError(Name.getText())) {
            NameExisted.setVisible(true);
        } else {
            NameExisted.setVisible(false);
        }

        if(Price.getText().length() == 0) {
            PriceError.setVisible(true);
        } else {
            PriceError.setVisible(false);
        }

        if(Description.getText().length() == 0) {
            DesError.setVisible(true);
        } else {
            DesError.setVisible(false);
        }

        String s1 = Name.getText();
        int s2 = getType(Type.getValue());
        int s3 = getStyle(Style.getValue());
        String s4= Description.getText();
        int s5 = - 1;
        if(Price.getText().length() > 0 && Integer.parseInt(Price.getText()) > 0) {
            s5 = Integer.parseInt(Price.getText());
        }

        if(s1.length() > 0 && s5 > 0 && s2 > 0 && s3 > 0 && s4.length() > 0) {
            insertIntoDB(s1,s2,s3,s4,s5);
            Success.setVisible(true);
        }
    }

    public boolean getExistedDishError(String name) {
        connection = handler.getConnection();
        String q = "select * from dish where DishName = ? and users_id = ?;";
        boolean b = false;
        try {
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, name);
            pst.setString(2, String.valueOf(getUserID()));
            ResultSet rs = pst.executeQuery();
            int count = 0;
            while(rs.next()) {
                count++;
            }
            if(count > 0) {
                b = true;
            } else {
                b = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public int getType(String type) {
        connection = handler.getConnection();
        String q = "select TypeID from type where TypeName = ?;";
        int res = -1;
        try {
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, type);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int getStyle(String style) {
        connection = handler.getConnection();
        String q = "select StyleID from style where StyleName = ?;";
        int res = -1;
        try {
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, style);
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                res = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }


    public void insertIntoDB(String s1, int s2, int s3, String s4, int s5) {
        connection = handler.getConnection();
        String q = "insert into dish (DishName, TypeID, StyleID, DishDes, Price, users_id) " +
                "values (?, ?, ?, ?, ?, ?);\n";
        try {
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, s1);
            pst.setString(2, String.valueOf(s2));
            pst.setString(3, String.valueOf(s3));
            pst.setString(4, s4);
            pst.setString(5, String.valueOf(s5));
            pst.setString(6, String.valueOf(getUserID()));
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
