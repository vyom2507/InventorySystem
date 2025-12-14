package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.DBConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController 
{

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    public void handleLogin(ActionEvent event) 
    {
        try 
        {
            
            String user = txtUsername.getText();
            String pass = txtPassword.getText();

          
            Connection conn = DBConnect.getConnection();
            
            String sql = "SELECT role FROM vyom_users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, pass);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) 
            {
                
                String role = rs.getString("role");
                
            
                String fxmlFile = "";
                if(role.equalsIgnoreCase("ADMIN")) 
                {
                    fxmlFile = "/views/AdminView.fxml";
                } 
                else 
                {
                    fxmlFile = "/views/UserView.fxml";
                }
                
               
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Pane root = FXMLLoader.load(getClass().getResource(fxmlFile));
                Scene scene = new Scene(root);
                
               
                scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
                
                stage.setScene(scene);
                stage.show();
            } 
            else 
            {
                
                lblError.setText("Invalid Credentials");
            }

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            lblError.setText("Database Error!");
        }
    }
}