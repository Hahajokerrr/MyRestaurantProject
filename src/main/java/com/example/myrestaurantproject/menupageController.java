package com.example.myrestaurantproject;

import com.example.myrestaurantproject.DBConnection.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class menupageController implements Initializable {
    @FXML
    private Button AddButton;

    @FXML
    private ComboBox<String> AppetizerChooser;

    @FXML
    private ComboBox<String> DesertChooser;

    @FXML
    private ComboBox<String> MainCourseChooser;

    @FXML
    private ComboBox<String> SideDishChooser;

    @FXML
    private ComboBox<String> StyleChooser;

    @FXML
    private TextField MenuName;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;
    private int IDInstance;

    private ObservableList<String> appetizer = FXCollections.observableArrayList();
    private ObservableList<String> mainCourse = FXCollections.observableArrayList();
    private ObservableList<String> sideDish = FXCollections.observableArrayList();
    private ObservableList<String> dessert = FXCollections.observableArrayList();

    boolean canAdd = true;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handler = new DBHandler();
        StyleChooser.setItems(StyleTypeList.StyleList);

        setIDInstance(homepageController.getInstance().getIDInstance());

        String curStyle = StyleChooser.getValue();
        connection = handler.getConnection();
        String q1 = "select DishName\n" +
                "from dish d inner join style s \n" +
                "where d.StyleId = s.StyleID and d.TypeID = 1 and d.StyleID = ? and d.users_id = ?; ";


    }

    public int getIDInstance() {
        return IDInstance;
    }

    public void setIDInstance(int IDInstance) {
        this.IDInstance = IDInstance;
    }
}
