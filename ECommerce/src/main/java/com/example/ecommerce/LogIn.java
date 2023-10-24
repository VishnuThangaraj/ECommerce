package com.example.ecommerce;

import java.sql.ResultSet;

public class LogIn {
    public Customer customerLogin(String userId, String password){

        String query = "SELECT * FROM customer WHERE email = \""+userId+"\" AND password = \""+password+"\"";
        //System.out.println(query);
        DBConnection connection = new DBConnection();

        try{
           ResultSet rs = connection.getQueryTable(query);
           if(rs.next()){
               return new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"),rs.getString("mobile"));
           }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null; // Invalid Credentials
    }
}

