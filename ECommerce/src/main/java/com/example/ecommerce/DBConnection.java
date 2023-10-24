package com.example.ecommerce;
import java.sql.*;

public class DBConnection {
    // Database location
    private final String DBUrl = "jdbc:mysql://localhost:3306/ecommerce";

    // userName and password of Database
    private final String userName = "root";
    private final String password = "vishnu";

    private Statement getStatement(){
        try{
            Connection connection = DriverManager.getConnection(DBUrl, userName, password);
            return connection.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query){
        try{
            Statement statement = getStatement();
            return statement.executeQuery(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateDB(String query){
        try{
            Statement statement = getStatement();
            return statement.executeUpdate(query);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
