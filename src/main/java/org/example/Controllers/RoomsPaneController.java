package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Model.Rooms;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import org.example.Controllers.RoomAvailabilityStatuses;

public class RoomsPaneController implements Initializable {
    @FXML
    private TextField roomNoField;
    @FXML
    private ChoiceBox<String> roomType;
    @FXML
    private ChoiceBox<String> roomAvailability;
    @FXML
    private TextField roomPriceField;
    @FXML
    private ToggleGroup cleaningStatus;
    @FXML
    private Label errorLabel;
    private ArrayList<String> roomTypes = new ArrayList<>(Arrays.asList("Single", "Double", "Triple", "King"));

    @FXML
    TableView<Rooms> roomsTable;
    @FXML
    TableColumn<Rooms, String> roomNoColumn;
    @FXML
    TableColumn<Rooms, String> roomTypeColumn;
    @FXML
    TableColumn<Rooms, RoomAvailabilityStatuses> roomAvailabilityColumn;
    @FXML
    TableColumn<Rooms, String> roomPriceColumn;
    @FXML
    TableColumn<Rooms, String> cleaningStatusColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxesHandler();
        tableViewInitializer();
    }

    private void tableViewInitializer() {
        roomNoColumn.setCellValueFactory(new PropertyValueFactory<Rooms,String>("roomNo"));
        roomNoColumn.setCellValueFactory(new PropertyValueFactory<Rooms,String>("roomNo"));
        ObservableList<Rooms> roomsList = FXCollections.observableArrayList();

    }

    private void choiceBoxesHandler() {
        roomType.getItems().addAll(roomTypes);
        roomAvailability.getItems().addAll("Available", "Unavailable");

    }

    public void addNewRoom(){
        String newRoomNo = roomNoField.getText();
        String newRoomType = roomType.getValue();
        String newRoomAvailability = roomAvailability.getValue();
        String newRoomPrice = roomPriceField.getText();
        if (newRoomNo == null || newRoomNo.trim().isEmpty() || newRoomType == null || newRoomAvailability == null || newRoomPrice == null || newRoomPrice.trim().isEmpty() || cleaningStatus.getSelectedToggle() == null) {
//            errorLabel.setText("Error: All fields must be filled.");
            return;
        }

        // Parsing Integer and Double safely
        try {
            Integer parsedNewRoomNo = Integer.parseInt(newRoomNo);
            Double parsedNewRoomPrice = Double.parseDouble(newRoomPrice);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format for Room Number or Price.");
            return;
        }

        // Get the selected cleaning status
        String newRoomCleaningStatus = ((RadioButton) cleaningStatus.getSelectedToggle()).getText();

        // If all inputs are valid, print the values
        System.out.println("Room Number: " + newRoomNo);
        System.out.println("Room Type: " + newRoomType);
        System.out.println("Room Availability: " + newRoomAvailability);
        System.out.println("Room Price: " + newRoomPrice);
        System.out.println("Cleaning Status: " + newRoomCleaningStatus);
    }
}
