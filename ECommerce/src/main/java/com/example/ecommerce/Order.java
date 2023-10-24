package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {

    // ####################################### SINGLE-ORDER ####################################### //
    public static boolean placeOrder(Customer customer, Product product){
        String groupOrder = "SELECT max(group_order_id) +1 id FROM orders";

        DBConnection connection = new DBConnection();

        try{
            ResultSet rs = connection.getQueryTable(groupOrder);
            if(rs.next()){
                String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES ("+rs.getInt("id")+" , " +customer.getId()+", "+product.getId()+")";
                return connection.updateDB((placeOrder)) != 0;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    // ####################################### MULTIPLE-ORDER ####################################### //

    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList){
        String groupOrder = "SELECT max(group_order_id) +1 id FROM orders";

        DBConnection connection = new DBConnection();

        try{
            ResultSet rs = connection.getQueryTable(groupOrder);
            int count = 0;
            if(rs.next()){
                for(Product product : productList){
                    String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES ("+rs.getInt("id")+" , " +customer.getId()+", "+product.getId()+")";
                    count += connection.updateDB(placeOrder);
                }
                return count;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}
