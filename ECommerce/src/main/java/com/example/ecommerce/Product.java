package com.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    // Constructor
    public Product(int id, String name, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public static ObservableList<Product> getAllProducts(){
        String selectAllProducts = "SELECT id,name,price FROM product";
        return fetchProductData(selectAllProducts);
    }

    public static ObservableList<Product> getSpecific(String keyword){
        String selectSpecific = "SELECT id,name,price FROM product where name like \"%"+keyword+"%\"";
        return fetchProductData(selectSpecific);
    }

    public static ObservableList<Product> getUserOrders(int ID){
        String selectSpecific = "SELECT group_order_id, quandity, order_date, order_status from orders where customer_id = "+ID+"";
        System.out.println(selectSpecific);
        return fetchProductData(selectSpecific);
    }
    public static ObservableList<Product> fetchProductData(String query){
        ObservableList<Product> data = FXCollections.observableArrayList();
        DBConnection connection = new DBConnection();

        try{
            ResultSet rs = connection.getQueryTable(query);
            while(rs.next()){
                Product Product = new Product(rs.getInt("id"), rs.getString("name"),rs.getDouble("price"));
                data.add(Product);
                System.out.println("Data fetched");
            }
            return data;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }
}
