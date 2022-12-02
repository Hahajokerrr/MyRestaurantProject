package com.example.myrestaurantproject.commonObject;

public class Dish {
    private String dishName;
    private String style;
    private String type;
    private int price;
    private String description;


    public Dish(String dishName, String style, String type, int price, String description) {
        this.dishName = dishName;
        this.style = style;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public Dish() {
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
