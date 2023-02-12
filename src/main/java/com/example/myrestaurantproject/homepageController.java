package com.example.myrestaurantproject;

import com.example.myrestaurantproject.DBConnection.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
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

    @FXML
    private Text BRMenu;

    @FXML
    private Text Balance;

    @FXML
    private Text DishNum;

    @FXML
    private Text MSMenu;

    @FXML
    private Text MenuNum;

    @FXML
    private Button logOut;

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
        setDishNum();
        setMenuNum();
        setBRMenu();
        setMSMenu();
    }

    public void setDishNum() {
        connection = handler.getConnection();
        String q = "SELECT COUNT(DishID)\n" +
                "FROM Dish;";
        int temp = 0;
        try {
            PreparedStatement t1 = connection.prepareStatement(q);
            ResultSet rs = t1.executeQuery();
            while(rs.next()) {
                temp = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DishNum.setText("Total number of Dishes: " + temp);
    }

    public void setMenuNum() {
        connection = handler.getConnection();
        String q = "SELECT COUNT(MenuID)\n" +
                "FROM Menu;";
        int temp = 0;
        try {
            PreparedStatement t1 = connection.prepareStatement(q);
            ResultSet rs = t1.executeQuery();
            while(rs.next()) {
                temp = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MenuNum.setText("Total number of Menus: " + temp);
    }

    public void setBRMenu() {
        connection = handler.getConnection();
        String q = "SELECT \n" +
                "     m.MenuName, AVG(r.Rating) average\n" +
                "FROM\n" +
                "    Menu m\n" +
                "        INNER JOIN\n" +
                "    Receipt r\n" +
                "WHERE\n" +
                "    m.menuid = r.menuid\n" +
                "GROUP BY m.MenuID\n" +
                "ORDER BY average DESC\n" +
                "LIMIT 1;";
        String temp = "";
        try {
            PreparedStatement t1 = connection.prepareStatement(q);
            ResultSet rs = t1.executeQuery();
            while(rs.next()) {
                temp = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BRMenu.setText("Best rated Menu: " + temp);
    }

    public void setMSMenu() {
        connection = handler.getConnection();
        String q = "SELECT \n" +
                "    MenuName, COUNT(*) served\n" +
                "FROM\n" +
                "    Menu m\n" +
                "        INNER JOIN\n" +
                "    Receipt r\n" +
                "WHERE\n" +
                "    m.menuid = r.menuid\n" +
                "GROUP BY m.MenuID\n" +
                "order by served desc\n" +
                "limit 1;\n";
        String temp = "";
        try {
            PreparedStatement t1 = connection.prepareStatement(q);
            ResultSet rs = t1.executeQuery();
            while(rs.next()) {
                temp = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MSMenu.setText("Most served Menu: " + temp);
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

    public void addMenuAction() {
        try {
            Stage addMenu = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
            Scene scene = new Scene(root);
            addMenu.setScene(scene);
            addMenu.show();
            addMenu.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addReceiptAction() {
        try {
            Stage addReceipt = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddReceiptPage.fxml"));
            Scene scene = new Scene(root);
            addReceipt.setScene(scene);
            addReceipt.show();
            addReceipt.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logOutAction() {
        try {
            logOut.getScene().getWindow().hide();
            Stage LogIn = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
            Scene scene = new Scene(root);
            LogIn.setScene(scene);
            LogIn.show();
            LogIn.setResizable(false);
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
