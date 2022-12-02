package com.example.myrestaurantproject.commonObject;

public class Menu {
    private String name;
    private String appetizer;
    private String mainCourse;
    private String sideDish;
    private String dessert;
    private int price;

    public Menu(String name, String appetizer, String mainCourse, String sideDish, String dessert, int price) {
        this.name = name;
        this.appetizer = appetizer;
        this.mainCourse = mainCourse;
        this.sideDish = sideDish;
        this.dessert = dessert;
        this.price = price;
    }

    public Menu(String name) {
        this.name = name;
        price = 0;
    }

    public Menu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(String appetizer) {
        this.appetizer = appetizer;
    }

    public String getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
    }

    public String getSideDish() {
        return sideDish;
    }

    public void setSideDish(String sideDish) {
        this.sideDish = sideDish;
    }

    public String getDessert() {
        return dessert;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
