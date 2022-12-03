package com.example.myrestaurantproject;

import com.example.myrestaurantproject.DBConnection.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TextField MenuName;

    @FXML
    private Text NameExistedError;

    @FXML
    private Text NameFilledError;

    @FXML
    private Text Success;


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

        setIDInstance(homepageController.getInstance().getIDInstance());
        String s = String.valueOf(getIDInstance());

        NameFilledError.setVisible(false);
        NameExistedError.setVisible(false);
        Success.setVisible(false);

        connection = handler.getConnection();

        String q1 = "select DishName\n" +
                "from dish d inner join style s \n" +
                "where d.StyleId = s.StyleID and d.TypeID = 1 and d.users_id = ?; ";
        String q2 = "select DishName\n" +
                "from dish d inner join style s \n" +
                "where d.StyleId = s.StyleID and d.TypeID = 2 and d.users_id = ?; ";
        String q3 = "select DishName\n" +
                "from dish d inner join style s \n" +
                "where d.StyleId = s.StyleID and d.TypeID = 3 and d.users_id = ?; ";
        String q4 = "select DishName\n" +
                "from dish d inner join style s \n" +
                "where d.StyleId = s.StyleID and d.TypeID = 4 and d.users_id = ?; ";

        try {
            PreparedStatement p1 = connection.prepareStatement(q1);
            PreparedStatement p2 = connection.prepareStatement(q2);
            PreparedStatement p3 = connection.prepareStatement(q3);
            PreparedStatement p4 = connection.prepareStatement(q4);
            p1.setString(1, s);
            p2.setString(1, s);
            p3.setString(1, s);
            p4.setString(1, s);
            ResultSet rs1 = p1.executeQuery();
            ResultSet rs2 = p2.executeQuery();
            ResultSet rs3 = p3.executeQuery();
            ResultSet rs4 = p4.executeQuery();
            while (rs1.next()) {
                appetizer.add(rs1.getString(1));
            }
            while (rs2.next()) {
                mainCourse.add(rs2.getString(1));
            }
            while (rs3.next()) {
                sideDish.add(rs3.getString(1));
            }
            while (rs4.next()) {
                dessert.add(rs4.getString(1));
            }

            AppetizerChooser.setItems(appetizer);
            MainCourseChooser.setItems(mainCourse);
            SideDishChooser.setItems(sideDish);
            DesertChooser.setItems(dessert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean setFilledError() {
        if (MenuName.getText().length() == 0) {

            return true;
        } else {

            return false;
        }
    }

    public boolean setExistedError() {
        boolean res = false;
        connection = handler.getConnection();
        String q = "select MenuName from menu where users_id = ?";
        try {
            PreparedStatement p = connection.prepareStatement(q);
            p.setString(1, String.valueOf(getIDInstance()));
            ResultSet rs = p.executeQuery();
            int count = 0;
            while (rs.next()) {
                if (rs.getString(1).equals(MenuName.getText())) {
                    count++;
                }
            }
            if (count > 0) {

                res = true;
            } else {

                res = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void add() {
        if(setExistedError()) {
            NameExistedError.setVisible(true);
            NameFilledError.setVisible(false);
        } else if(setFilledError()) {
            NameExistedError.setVisible(false);
            NameFilledError.setVisible(true);
        }
        else {
            NameExistedError.setVisible(false);
            NameFilledError.setVisible(false);
            if(AppetizerChooser.getValue() != null && MainCourseChooser.getValue() != null
                    && DesertChooser.getValue() != null && SideDishChooser.getValue()!= null) {
                connection = handler.getConnection();
                String q1 = "insert into menu (users_id, MenuName) values (?, ?)";

                String q2 = "select DishID from dish where DishName = ?";
                String q3 = "select DishID from dish where DishName = ?";
                String q4 = "select DishID from dish where DishName = ?";
                String q5 = "select DishID from dish where DishName = ?";

                String q6 = "select MenuID from menu where MenuName = ?";

                String q7 = "insert into menudetail (MenuID, DishID) values (?, ?)";
                String q8 = "insert into menudetail (MenuID, DishID) values (?, ?)";
                String q9 = "insert into menudetail (MenuID, DishID) values (?, ?)";
                String q10 = "insert into menudetail (MenuID, DishID) values (?, ?)";

                try {
                    PreparedStatement p1 = connection.prepareStatement(q1);
                    PreparedStatement p2 = connection.prepareStatement(q2);
                    PreparedStatement p3 = connection.prepareStatement(q3);
                    PreparedStatement p4 = connection.prepareStatement(q4);
                    PreparedStatement p5 = connection.prepareStatement(q5);
                    PreparedStatement p6 = connection.prepareStatement(q6);
                    PreparedStatement p7 = connection.prepareStatement(q7);
                    PreparedStatement p8 = connection.prepareStatement(q8);
                    PreparedStatement p9 = connection.prepareStatement(q9);
                    PreparedStatement p10 = connection.prepareStatement(q10);

                    p1.setString(1, String.valueOf(getIDInstance()));
                    p1.setString(2, MenuName.getText());
                    p1.executeUpdate();

                    p2.setString(1, AppetizerChooser.getValue());
                    p3.setString(1, MainCourseChooser.getValue());
                    p4.setString(1, SideDishChooser.getValue());
                    p5.setString(1, DesertChooser.getValue());

                    p6.setString(1, MenuName.getText());

                    ResultSet rs2 = p2.executeQuery();
                    ResultSet rs3 = p3.executeQuery();
                    ResultSet rs4 = p4.executeQuery();
                    ResultSet rs5 = p5.executeQuery();
                    ResultSet rs6 = p6.executeQuery();

                    while (rs2.next()) {
                        int i2 = rs2.getInt(1);
                        p7.setString(2, String.valueOf(i2));
                    }
                    while (rs3.next()) {
                        int i3 = rs3.getInt(1);
                        p8.setString(2, String.valueOf(i3));
                    }
                    while (rs4.next()) {
                        int i4 = rs4.getInt(1);
                        p9.setString(2, String.valueOf(i4));
                    }
                    while (rs5.next()) {
                        int i5 = rs5.getInt(1);
                        p10.setString(2, String.valueOf(i5));
                    }
                    while (rs6.next()) {
                        int i6 = rs6.getInt(1);
                        p7.setString(1, String.valueOf(i6));
                        p8.setString(1, String.valueOf(i6));
                        p9.setString(1, String.valueOf(i6));
                        p10.setString(1, String.valueOf(i6));
                    }


                    p7.executeUpdate();
                    p8.executeUpdate();
                    p9.executeUpdate();
                    p10.executeUpdate();

                    Success.setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public int getIDInstance() {
        return IDInstance;
    }

    public void setIDInstance(int IDInstance) {
        this.IDInstance = IDInstance;
    }
}
