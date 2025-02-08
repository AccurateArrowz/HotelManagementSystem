package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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
   Button customersButton;


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
         roomsPane=fxmlLoader1.load();
         FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxmlFolder/BookingsPane.fxml"));
         customersPane=fxmlLoader2.load();
         FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/fxmlFolder/DashboardPane.fxml"));
         dashboardPane=fxmlLoader3.load();

      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void showDashboard(){
      rightPane.getChildren().clear();
      rightPane.getChildren().add(dashboardPane);
   }



   public void showAvailableRooms()  {
      rightPane.getChildren().clear();
      rightPane.getChildren().add(roomsPane);
   }
   public void showBookings() {
      rightPane.getChildren().clear();
      rightPane.getChildren().add(customersPane);
   }


}
