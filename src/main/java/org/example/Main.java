package org.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{
    @FXML
    TextField usernameTextField;

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

    public void authenticateLogin(ActionEvent event) throws IOException {
        //rough- acess and username
        String username = usernameTextField.getText();
        //just load and display mainPageStage once authenticated
        FXMLLoader mainPageFxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/MainPage.fxml"));
        Stage mainPageStage = new Stage();
        Scene scene= new Scene(mainPageFxmlLoader.load());
        mainPageStage.setScene(scene);
        Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        loginStage.close();
        mainPageStage.show();
        Label usernameLabel = new Label("Username ");
        MainPageController mainPageController = mainPageFxmlLoader.getController();
        usernameLabel.setText(username);
        usernameLabel.setFont(Font.font("Arial", 20));
        mainPageController.addLabel(usernameLabel.getText());
    }
}