package org.example.Controllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.example.Model.Rooms;

import java.net.URL;
import java.sql.*;
import java.util.*;

public class RoomsPaneController implements Initializable {
    //@FXML topLeft Container(room Fields) Nodes
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
    private RadioButton cleanedRadioButton;
    @FXML
    private RadioButton notCleanedRadioButton;
    @FXML
    private Label errorLabel;
    private ArrayList<String> roomTypes = new ArrayList<>(Arrays.asList("Single", "Double", "Triple", "King"));

    //@FXML tableView and table columns
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
    ObservableList<Rooms> roomsList = FXCollections.observableArrayList();
    Connection dbConnection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBoxesHandler();
        tableViewInitializer();

    }

    private void tableViewInitializer() {
        roomNoColumn.setCellValueFactory(new PropertyValueFactory<Rooms,String>("roomNo"));
        //the argument in constructor of PropertyValueFactory is for setting text top of column
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<Rooms,String>("roomType"));
        roomAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<Rooms, RoomAvailabilityStatuses>("roomAvailability"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<Rooms,String>("roomPrice"));
        cleaningStatusColumn.setCellValueFactory(new PropertyValueFactory<Rooms, String>("cleaningStatus"));

        //getting data from db
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HotelManagementSystem", "root", "sujay10AWM");
            PreparedStatement pS = dbConnection.prepareStatement("SELECT * FROM Rooms");
            ResultSet resultSet = pS.executeQuery();
            while(resultSet.next()){
                System.out.println(
                        resultSet.getInt("Room_No") + " " +
                                resultSet.getString("Type") + " " +
                                resultSet.getString("Availability") + " " +
                                resultSet.getDouble("Price") + " " +
                                resultSet.getString("Cleaning_Status"));
                Rooms retrievedRoom = new Rooms(
                        resultSet.getInt("Room_No"),
                        resultSet.getString("Type"),
                        RoomAvailabilityStatuses.valueOf((resultSet.getString("Availability")).toUpperCase()),
                        resultSet.getDouble("Price"),
                        resultSet.getString("Cleaning_Status")
                        );
                roomsList.add(retrievedRoom);
            }
            roomsTable.setItems(roomsList);
            dbConnection.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void choiceBoxesHandler() {
        //my 2 choiceboxes: roomType and roomAvailability
        roomType.getItems().addAll(roomTypes);
        for(RoomAvailabilityStatuses status : RoomAvailabilityStatuses.values()){
            roomAvailability.getItems().add(status.toString());
        }


    }
    public void addNewRoom(){
        String newRoomNo = roomNoField.getText();
        String newRoomType = roomType.getValue();
        String newRoomAvailability = roomAvailability.getValue();
        String newRoomPrice = roomPriceField.getText();

        if (newRoomNo == null || newRoomNo.trim().isEmpty() || newRoomType == null || newRoomAvailability == null || newRoomPrice == null || newRoomPrice.trim().isEmpty() || cleaningStatus.getSelectedToggle() == null) {
            errorLabel.setText("Error: All fields must be filled.");
            // Remove error message after 2 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> errorLabel.setText(""));
            pause.play();
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
    public void clearFields() {
        roomNoField.clear();
        roomType.getSelectionModel().clearSelection();
        roomAvailability.setValue(null);
        roomPriceField.clear();
        cleaningStatus.selectToggle(null);

        roomNoField.setDisable(false);
    }
    public void tableClicked(){
        Rooms selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        clearFields();
        roomNoField.setText(selectedRoom.getRoomNo().toString());
        roomNoField.setDisable(true);
        roomType.setValue(selectedRoom.getRoomType());
        roomAvailability.setValue(selectedRoom.getRoomAvailability().toString());
        roomPriceField.setText(selectedRoom.getRoomPrice().toString());
        if(selectedRoom.getCleaningStatus().toString().equals( "Cleaned")) {
            cleaningStatus.selectToggle(cleanedRadioButton);
        }
        else {
            cleaningStatus.selectToggle(notCleanedRadioButton);
        }


    }
    public void updateRoom(){
//        Integer roomNo = Integer.parseInt(roomNoField.getText());
        Rooms selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
        String newType = roomType.getValue();
        String newAvailability = roomAvailability.getValue();
        String newPrice = roomPriceField.getText();
        String newCleaningStatus = ((RadioButton) cleaningStatus.getSelectedToggle()).getText();;
        selectedRoom.setRoomType(newType);
        selectedRoom.setRoomAvailability(RoomAvailabilityStatuses.valueOf(newAvailability));
        selectedRoom.setRoomPrice(Double.parseDouble(newPrice));
        selectedRoom.setCleaningStatus(newCleaningStatus);
        roomsTable.setItems(roomsList);
        roomsTable.refresh();

    }
    public void deleteRoom(){
        //first alert and ask for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null); // No header
        alert.setContentText("Are you sure you want to delete this room?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Rooms selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
            roomsList.remove(selectedRoom);
            System.out.println("Room deleted!");
            roomsTable.refresh();
            clearFields();
            // Call delete method here
        } else {
            System.out.println("Deletion cancelled.");
        }

    }
}
