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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Application{
    public static Stage loginStage; //for later reference to use FileChooser
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
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Hello World");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/LoginPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        loginStage = primaryStage;

    }
    public static Stage getLoginStage() {
        return loginStage;
    }

    public void authenticateAccount(ActionEvent event) throws IOException {
        //For login page
        String enteredEmail = login_emailTextField.getText();
        //just load and display mainPageStage once authenticated
        FXMLLoader mainPageFxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/MainPage.fxml"));
        Stage mainPageStage = new Stage();
        Scene scene= new Scene(mainPageFxmlLoader.load());
        mainPageStage.setScene(scene);
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
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