package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.example.Controllers.MainPageController;

public class Main extends Application{
    @FXML
    TextField login_emailTextField;
    @FXML
    Button registerButton;

    public static Connection getMyHikariConnection() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/HotelManagementSystem");
        config.setUsername("root");
        config.setPassword("sujay10AWM");
        config.setMaximumPoolSize(5); // Set max connections in the pool
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        return hikariDataSource.getConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage loginStage) throws Exception {
        loginStage.setTitle("Hello World");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/LoginPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.show();
    }

    public void authenticateAccount(ActionEvent event) throws IOException {
        //For login page
        String enteredEmail = login_emailTextField.getText();
        //just load and display mainPageStage once authenticated
        FXMLLoader mainPageFxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/MainPage.fxml"));
        Stage mainPageStage = new Stage();
        Scene scene= new Scene(mainPageFxmlLoader.load());
        mainPageStage.setScene(scene);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();
        mainPageStage.show();
    }

    public void openRegisterStage(ActionEvent event) throws IOException {
        Stage registerStage = new Stage();
        registerStage.setTitle("Register Account");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/RegisterPage.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        registerStage.setScene(scene);
        registerStage.show();
    }
}