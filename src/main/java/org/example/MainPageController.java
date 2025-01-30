package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {


   @FXML
   Button dashboardButton;
   @FXML
   Button availableRoomsButton;
   @FXML
   Button customersButton;


   @FXML
   AnchorPane rightPane; //parent pane on the right side
   AnchorPane availableRoomsPane;
   AnchorPane customersPane;
   AnchorPane dashboardPane;



   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      try {
         FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/fxmlFolder/AvailableRoomsPane.fxml"));
         availableRoomsPane=fxmlLoader1.load();
         FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxmlFolder/CustomersPane.fxml"));
         customersPane=fxmlLoader2.load();
         FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/fxmlFolder/DashboardPane.fxml"));
         dashboardPane=fxmlLoader3.load();

      }
      catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
    public void addLabel(String name) {
      System.out.println("method is called. Name: "+name);
      Label newLabel = new Label();
      newLabel.setText(name);
//      newLabel.setFont(new Font(10));

      // Set a white background
//      newLabel.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: black;");
      rightPane.getChildren().add(newLabel);
   }

   public void showDashboard(){
      rightPane.getChildren().clear();

      rightPane.getChildren().add(dashboardPane);
   }



   public void showAvailableRooms()  {
      System.out.println("method is called. Available Rooms: "+availableRoomsButton.getText());
      //should retrieve rooms data from db and then load

      rightPane.getChildren().clear();
      AnchorPane.setTopAnchor(availableRoomsPane, 0.0);
      AnchorPane.setLeftAnchor(availableRoomsPane, 0.0);
      rightPane.getChildren().add(availableRoomsPane);
   }
   public void showCustomers() {
      rightPane.getChildren().clear();
//      AnchorPane.setTopAnchor(customersPane, 0.0);
//      AnchorPane.setLeftAnchor(customersPane, 0.0);
      rightPane.getChildren().add(customersPane);
   }


}
