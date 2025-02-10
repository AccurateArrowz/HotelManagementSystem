package org.example.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
   private Button selectedButton = null;


   @FXML
   HBox rightPane; //parent pane on the right side
   Pane roomsPane;
   Pane customersPane;
   Pane dashboardPane;
   Pane employeesPane;


   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      try {
         FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/fxmlFolder/RoomsPane.fxml"));
         roomsPane = fxmlLoader1.load();
         FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxmlFolder/BookingsPane.fxml"));
         customersPane = fxmlLoader2.load();
         FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/fxmlFolder/DashboardPane.fxml"));
         dashboardPane = fxmlLoader3.load();

      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      displayHoverEffect(dashboardButton, this::showDashboard);
      displayHoverEffect(roomsButton, this::showAvailableRooms);
      displayHoverEffect(bookingsButton, this::showBookings);
   }

   public void displayHoverEffect(Button button, Runnable action) {
      // Animation: Border & Background Change on Hover
      Timeline borderIn = new Timeline(
              new KeyFrame(Duration.millis(50),
                      new KeyValue(button.styleProperty(), "-fx-border-width: 0px 0px 0px 4px;" +
                              " -fx-border-color: #CDF0F7;" +
                              " -fx-background-color: #5496A5;"))
      );

      // Animation: Border & Background Reset on Exit (Only if not selected)
      Timeline borderOut = new Timeline(
              new KeyFrame(Duration.millis(50),
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
      rightPane.getChildren().clear();
      rightPane.getChildren().add(dashboardPane);
   }


   @FXML
   public void showAvailableRooms()  {
      rightPane.getChildren().clear();
      rightPane.getChildren().add(roomsPane);
   }
   @FXML
   public void showBookings() {
      rightPane.getChildren().clear();
      rightPane.getChildren().add(customersPane);
   }


}
