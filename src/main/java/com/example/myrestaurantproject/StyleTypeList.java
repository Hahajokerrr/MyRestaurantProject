package com.example.myrestaurantproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StyleTypeList {
    public static final ObservableList<String> StyleList = FXCollections.observableArrayList("None Specific", "Vietnamese", "Japanese", "Chinese", "Indian", "French");
    public static final ObservableList<String> TypeList = FXCollections.observableArrayList("None Specific", "Appetizer", "Side Dish", "Main Course", "Dessert");
}
