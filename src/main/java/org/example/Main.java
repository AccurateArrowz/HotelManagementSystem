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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application{
    public static Stage theStage; //for reference of injected stage of the start method
    @FXML
    TextField emailTextField;
    @FXML
    TextField passwordTextField;
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
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
        theStage = primaryStage;

    }

    public void authenticateAccount(ActionEvent event) throws IOException {
        String enteredEmail = emailTextField.getText();
        String enteredPassword = passwordTextField.getText();
        if(enteredEmail.isEmpty() || enteredPassword.isEmpty()) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Both email and password must be filled");
//            alert.initOwner(theStage);
//            alert.showAndWait();
        }
        else {
            try (Connection conn = getMyHikariConnection()) {
                PreparedStatement pst = conn.prepareStatement("select password from Users where email = ? ");
                pst.setString(1, enteredEmail);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    if (BCrypt.checkpw(enteredPassword, rs.getString("password"))) {
                        System.out.println("passowrd matched");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        FXMLLoader mainPageFxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/MainPage.fxml"));
        Scene scene= new Scene(mainPageFxmlLoader.load());
        theStage.setScene(scene);
        theStage.setFullScreenExitHint("");
        theStage.setFullScreen(true);
        theStage.show();
    }

    public void openRegisterStage(ActionEvent event) throws IOException {
        Stage registerStage = new Stage();
        registerStage.setTitle("Register Account");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/RegisterPage.fxml"));
        Scene scene= new Scene(fxmlLoader.load());
        registerStage.setScene(scene);
        registerStage.initOwner(theStage);
        registerStage.show();
    }


}