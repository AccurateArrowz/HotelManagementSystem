package org.example.Controllers;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivitiesController  {

    @FXML
    Button selectImgButton;
    @FXML
    ImageView previewImageView;

    public void openAddActivityPage(){
        try {
            Stage addActivityStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/AddActivityPage.fxml"));
            Scene addActivityScene = new Scene(fxmlLoader.load());
            addActivityStage.setScene(addActivityScene);
            addActivityStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void selectImg(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Your Image");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(Main.getLoginStage());//passing the main stage
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            System.out.println("image selected");
            previewImageView.setImage(image);
        }
    }


}
