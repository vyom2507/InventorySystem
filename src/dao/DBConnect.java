package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect 
{

    
    private static final String URL = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "fp510";
    private static final String PASSWORD = "510";

    private static Connection connection = null;

    public static Connection getConnection() 
    {
        try 
        {
            
            if (connection == null || connection.isClosed()) 
            {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load the driver
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("DEBUG: Successfully Connected to Database!");
            }
        } catch (ClassNotFoundException e) 
        {
            System.out.println("Error: MySQL Driver not found.");
            e.printStackTrace();
        } catch (SQLException e)
        {
            System.out.println("Error: Could not connect to database.");
            e.printStackTrace();
        }
        return connection;
    }
}