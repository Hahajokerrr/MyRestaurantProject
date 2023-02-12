package com.example.myrestaurantproject;

import com.example.myrestaurantproject.DBConnection.DBHandler;
import com.example.myrestaurantproject.commonObject.Dish;
import com.example.myrestaurantproject.commonObject.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class allmenuController implements Initializable {

    @FXML
    private TableColumn<Menu, String> AppetizerCol;

    @FXML
    private TableColumn<Menu, String> DessertCol;

    @FXML
    private TableColumn<Menu, String> MainCourseCol;

    @FXML
    private TableColumn<Menu, String> MenuNameCol;

    @FXML
    private TableView<Menu> MenuTable;

    @FXML
    private TableColumn<Menu, Integer> PriceCol;

    @FXML
    private TableColumn<Menu, Double> RatingCol;

    @FXML
    private TableColumn<Menu, Integer> TotalServedCol;


    @FXML
    private TextField SearchBar;

    @FXML
    private TableColumn<Menu, String> SideDishCol;

    private ObservableList<Menu> menuList;

    private int IDInstance;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handler = new DBHandler();

        setIDInstance(homepageController.getInstance().getIDInstance());
        String s = String.valueOf(getIDInstance());

        menuList = FXCollections.observableArrayList();

        setMenuNameList();

        MenuNameCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("name"));
        AppetizerCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("appetizer"));
        SideDishCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("sideDish"));
        MainCourseCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("mainCourse"));
        DessertCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("dessert"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("price"));
        RatingCol.setCellValueFactory(new PropertyValueFactory<Menu, Double>("rating"));
        TotalServedCol.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("totalServed"));

        connection = handler.getConnection();
        String q = "select  m.MenuName, d.DishName, d.Price, d.TypeID from menu m inner join menudetail md inner join dish d\n" +
                "where md.MenuID = m.MenuID and md.DishID = d.DishID and d.users_id = ?;";
        String q2 = "SELECT \n" +
                "     m.MenuName, AVG(r.Rating) average, COUNT(MenuName) total\n" +
                "FROM\n" +
                "    Menu m\n" +
                "        INNER JOIN\n" +
                "    Receipt r\n" +
                "WHERE\n" +
                "    m.menuid = r.menuid and m.users_id = ?\n" +
                "GROUP BY m.MenuID;";
        try {
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, s);
            ResultSet rs = pst.executeQuery();

            PreparedStatement p2 = connection.prepareStatement(q2);
            p2.setString(1, String.valueOf(getIDInstance()));
            ResultSet rs2 = p2.executeQuery();


            while(rs.next()) {
                for(Menu m : menuList) {
                    if(m.getName().equals(rs.getString(1))) {
                        m.setPrice(m.getPrice() + rs.getInt(3));
                        switch (rs.getInt(4)) {
                            case 1:
                                m.setAppetizer(rs.getString(2));
                                break;
                            case 2:
                                m.setMainCourse(rs.getString(2));
                                break;
                            case 3:
                                m.setSideDish(rs.getString(2));
                                break;
                            case 4:
                                m.setDessert(rs.getString(2));
                                break;
                        }
                    }
                }
            }


            while (rs2.next()) {
                for(Menu m : menuList) {
                    if(m.getName().equals(rs2.getString(1))) {
                        m.setRating(rs2.getDouble(2));
                        m.setTotalServed(rs2.getInt(3));
                    }
                }
            }


            FilteredList<Menu> filteredList = new FilteredList<>(menuList, b -> true);
            SearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(menu -> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if(menu.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Menu> sortedMenu = new SortedList<>(filteredList);
            sortedMenu.comparatorProperty().bind(MenuTable.comparatorProperty());
            MenuTable.setItems(sortedMenu);
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void setMenuNameList() {
        connection = handler.getConnection();
        String q = "Select MenuName from menu";
        try {
            PreparedStatement pst = connection.prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            Menu temp;
            while(rs.next()) {
                temp = new Menu(rs.getString(1));
                menuList.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIDInstance() {
        return IDInstance;
    }

    public void setIDInstance(int IDInstance) {
        this.IDInstance = IDInstance;
    }
}
