package application;

import java.sql.Connection;
import java.sql.Statement;
import dao.DBConnect;

public class SetupDB 
{
    public static void main(String[] args) 
    {
        try 
        {
            Connection conn = DBConnect.getConnection();
            Statement stmt = conn.createStatement();

            String sqlUser = "CREATE TABLE IF NOT EXISTS vyom_users (" +
                             "id INT AUTO_INCREMENT PRIMARY KEY, " +
                             "username VARCHAR(50) UNIQUE NOT NULL, " +
                             "password VARCHAR(50) NOT NULL, " +
                             "role VARCHAR(20) NOT NULL)";
            stmt.executeUpdate(sqlUser);

            String sqlProduct = "CREATE TABLE IF NOT EXISTS vyom_products (" +
                                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                                "name VARCHAR(100) NOT NULL, " +
                                "quantity INT NOT NULL, " +
                                "price DOUBLE NOT NULL)";
            stmt.executeUpdate(sqlProduct);

            stmt.executeUpdate("INSERT IGNORE INTO vyom_users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN')");
            stmt.executeUpdate("INSERT IGNORE INTO vyom_users (username, password, role) VALUES ('user', 'user123', 'USER')");

            System.out.println("Database Setup Complete.");
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}