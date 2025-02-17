package org.example.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
   @FXML
   Button dashboardButton;
   @FXML
   Button roomsButton;
   @FXML
   Button bookingsButton;
   @FXML
   Button activitiesButton;
   @FXML
   Button logoutButton;
   private Button selectedButton = null;



   @FXML
   HBox rightPane; //parent pane on the right side
   Pane roomsPane;
   Pane bookingsPane;
   ScrollPane dashboardPane;
   ScrollPane activitiesPane;


   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

      displayHoverEffect(dashboardButton, this::showDashboard);
      displayHoverEffect(roomsButton, this::showAvailableRooms);
      displayHoverEffect(bookingsButton, this::showBookings);
      displayHoverEffect(activitiesButton, this::showActivities);
   }

   public void displayHoverEffect(Button button, Runnable action) {
      // Animation: Border & Background Change on Hover
      Timeline borderIn = new Timeline(
              new KeyFrame(Duration.millis(25),
                      new KeyValue(button.styleProperty(), "-fx-border-width: 0px 0px 0px 4px;" +
                              " -fx-border-color: #CDF0F7;" +
                              " -fx-background-color: #5496A5;"))
      );

      // Animation: Border & Background Reset on Exit (Only if not selected)
      Timeline borderOut = new Timeline(
              new KeyFrame(Duration.millis(25),
                      new KeyValue(button.styleProperty(), "-fx-border-width: 0px 0px 0px 4px;" +
                              " -fx-border-color: transparent;" +
                              " -fx-background-color: #276977;"))
      );

      button.setOnMouseEntered(e -> {
         if (button != selectedButton) borderIn.play(); // Apply hover effect only if not selected
      });

      button.setOnMouseExited(e -> {
         if (button != selectedButton) borderOut.play(); // Reset only if not selected
      });

      button.setOnAction(e -> {
         // Reset previous button (if any)
         if (selectedButton != null && selectedButton != button) {
            selectedButton.setStyle("-fx-border-width: 0px 0px 0px 4px;" +
                    " -fx-border-color: transparent;" +
                    " -fx-background-color: #276977;");
         }

         // Set new selected button and keep its hover style
         selectedButton = button;
         button.setStyle("-fx-border-width: 0px 0px 0px 4px;" +
                 " -fx-border-color: #CDF0F7;" +
                 " -fx-background-color: #2E4A62;");
         action.run();
      });
   }

//
   @FXML
   public void showDashboard(){
      try {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/DashboardPane.fxml"));
         dashboardPane = fxmlLoader.load();
         rightPane.getChildren().clear();
         rightPane.getChildren().add(dashboardPane);

      } catch (IOException e) {
         throw new RuntimeException(e);
      }

   }


   @FXML
   public void showAvailableRooms()  {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/RoomsPane.fxml"));
       try {
           roomsPane = fxmlLoader.load();
          rightPane.getChildren().clear();
          rightPane.getChildren().add(roomsPane);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }
   @FXML
   public void showBookings() {
       try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/BookingsPane.fxml"));
          bookingsPane = fxmlLoader.load();
          rightPane.getChildren().clear();
          rightPane.getChildren().add(bookingsPane);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }


   public void showActivities() {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/Activities.fxml"));
       try {
           activitiesPane= fxmlLoader.load();
          rightPane.getChildren().clear();
          rightPane.getChildren().add(activitiesPane);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }

   public void logOut(){
      Stage primaryStage = (Stage) logoutButton.getScene().getWindow();
      try {
         FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmlFolder/LoginPage.fxml"));
         Scene loginScene = new Scene(fxmlLoader.load());
         primaryStage.setScene(loginScene);
      }
      catch (IOException e) {
         System.out.println(e.getMessage());
      }

   }
}
