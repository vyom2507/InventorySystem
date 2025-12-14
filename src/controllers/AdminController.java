package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dao.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.Product;

public class AdminController 
{

    @FXML private TableView<Product> tableProducts;
    @FXML private TableColumn<Product, Integer> colId;
    @FXML private TableColumn<Product, String> colName;
    @FXML private TableColumn<Product, Integer> colQty;
    @FXML private TableColumn<Product, Double> colPrice;
    
    @FXML private TextField txtName;
    @FXML private TextField txtQty;
    @FXML private TextField txtPrice;
    @FXML private Label lblMessage;

    
    public void initialize() 
    {
        try 
        {
          
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            
            
            loadProducts();
            
        } catch(Exception e) 
        {
            e.printStackTrace();
        }
    }

    
    private void loadProducts() 
    {
        try {
            ObservableList<Product> list = FXCollections.observableArrayList();
            Connection conn = DBConnect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM vyom_products"); 
            
            while (rs.next()) 
            {
                list.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
                ));
            }
            
            tableProducts.setItems(list);
            
        } catch(Exception e) 
        {
            e.printStackTrace();
        }
    }

    
    public void addProduct(ActionEvent event) 
    {
        try 
        {
            String name = txtName.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double price = Double.parseDouble(txtPrice.getText());
            
            Connection conn = DBConnect.getConnection();
            String sql = "INSERT INTO vyom_products (name, quantity, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, qty);
            stmt.setDouble(3, price);
            
            stmt.executeUpdate();
            lblMessage.setText("Product Added!");
            loadProducts(); 
            
        } catch(Exception e) 
        {
            lblMessage.setText("Error adding product.");
            e.printStackTrace();
        }
    }
    
    
    public void updateProduct(ActionEvent event) 
    {
        try 
        {
            
            String name = txtName.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double price = Double.parseDouble(txtPrice.getText());
            
            Connection conn = DBConnect.getConnection();
            String sql = "UPDATE vyom_products SET quantity = ?, price = ? WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, qty);
            stmt.setDouble(2, price);
            stmt.setString(3, name);
            
            int rows = stmt.executeUpdate();
            if(rows > 0) 
            {
                lblMessage.setText("Product Updated!");
                loadProducts();
            } else 
            {
                lblMessage.setText("Product name not found.");
            }
            
        } catch(Exception e)
        {
            lblMessage.setText("Error updating.");
            e.printStackTrace();
        }
    }

    public void deleteProduct(ActionEvent event) 
    {
        try 
        {
            String name = txtName.getText();
            
            Connection conn = DBConnect.getConnection();
            String sql = "DELETE FROM vyom_products WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            
            int rows = stmt.executeUpdate();
            if(rows > 0) 
            {
                lblMessage.setText("Product Deleted!");
                loadProducts();
            } else 
            {
                lblMessage.setText("Product name not found.");
            }
            
        } catch(Exception e)
        {
            lblMessage.setText("Error deleting.");
            e.printStackTrace();
        }
    }

    
    public void logout(ActionEvent event) 
    {
        try 
        {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Pane root = FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}