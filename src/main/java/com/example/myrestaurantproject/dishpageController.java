package com.example.myrestaurantproject;

import com.example.myrestaurantproject.DBConnection.DBHandler;
import com.example.myrestaurantproject.commonObject.Dish;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.css.CSSStyleDeclaration;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class dishpageController implements Initializable {
    @FXML
    private TextField searchDishName;

    @FXML
    private ComboBox<String> chooseType;
    @FXML
    private ComboBox<String> chooseStyle;

    @FXML
    private TableView<Dish> dishTable;

    @FXML
    private TableColumn<Dish, String> nameColumn;
    @FXML
    private TableColumn<Dish, String> styleColumn;
    @FXML
    private TableColumn<Dish, String> typeColumn;
    @FXML
    private TableColumn<Dish, Integer> priceColumn;
    @FXML
    private TableColumn<Dish, String> descriptionColumn;

    private ObservableList<Dish> dishList;



    private int IDInstance;


    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        handler = new DBHandler();

        setIDInstance(homepageController.getInstance().getIDInstance());
        String s = String.valueOf(getIDInstance());

        chooseStyle.setItems(StyleTypeList.StyleList);
        chooseType.setItems(StyleTypeList.TypeList);

        dishList = FXCollections.observableArrayList();
        nameColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("DishName"));
        styleColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("Style"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("Type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Dish, Integer>("Price"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("Description"));

        connection = handler.getConnection();
        String q = "select d.DishName, s.StyleName, t.TypeName, d.Price, d.DishDes\n" +
                "from dish d inner join style s inner join type t \n" +
                "where d.StyleId = s.StyleID and d.TypeID = t.TypeID and d.users_id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1, s);
            ResultSet rs = pst.executeQuery();
            Dish tempDish;
            while (rs.next()) {
                tempDish = new Dish(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4), rs.getString(5));
                dishList.add(tempDish);
            }

            FilteredList<Dish> filteredList = new FilteredList<>(dishList, b -> true);
            searchDishName.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate(dish -> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if(dish.getDishName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            FilteredList<Dish> filteredList2 = new FilteredList<>(filteredList, b -> true);
            chooseType.getSelectionModel().selectedItemProperty().addListener((option, oldValue, newValue) -> {
                filteredList2.setPredicate(dish -> {
                    if(dish.getType().equals(newValue) || newValue.equals("None Specific")) {
                        return true;
                    }
                    return false;
                });
            });

            FilteredList<Dish> filteredList3 = new FilteredList<>(filteredList2, b -> true);
            chooseStyle.getSelectionModel().selectedItemProperty().addListener((option, oldValue, newValue) -> {
                filteredList3.setPredicate(dish -> {
                    if(dish.getStyle().equals(newValue) || newValue.equals("None Specific")) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Dish> sortedDish = new SortedList<>(filteredList3);
            sortedDish.comparatorProperty().bind(dishTable.comparatorProperty());
            dishTable.setItems(sortedDish);

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
