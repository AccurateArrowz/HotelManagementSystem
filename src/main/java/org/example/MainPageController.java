package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
   AnchorPane rightPane; //parent pane on the right side
   Pane roomsPane;
   Pane customersPane;
   Pane dashboardPane;
   Pane employeesPane;
   ArrayList<Pane> dynamicPanes = new ArrayList<>();



   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      try {
         FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("/fxmlFolder/AvailableRoomsPane.fxml"));
         roomsPane=fxmlLoader1.load();
         FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("/fxmlFolder/CustomersPane.fxml"));
         customersPane=fxmlLoader2.load();
         FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("/fxmlFolder/DashboardPane.fxml"));
         dashboardPane=fxmlLoader3.load();
         dynamicPanes.add(roomsPane);
         dynamicPanes.add(customersPane);
         dynamicPanes.add(dashboardPane);


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
      rightPane.getChildren().removeAll(dynamicPanes);
      AnchorPane.setTopAnchor(customersPane, 0.0);
      AnchorPane.setLeftAnchor(customersPane, 0.0);
      rightPane.getChildren().add(dashboardPane);
   }



   public void showAvailableRooms()  {
//      System.out.println("method is called. Available Rooms: "+roomsButton.getText());
      //should retrieve rooms data from db and then load

      rightPane.getChildren().removeAll(dynamicPanes);
      AnchorPane.setTopAnchor(roomsPane, 0.0);
      AnchorPane.setLeftAnchor(roomsPane, 0.0);
      rightPane.getChildren().add(roomsPane);
   }
   public void showCustomers() {
      rightPane.getChildren().removeAll(dynamicPanes);
      AnchorPane.setTopAnchor(customersPane, 0.0);
      AnchorPane.setLeftAnchor(customersPane, 0.0);
      rightPane.getChildren().add(customersPane);
   }


}
