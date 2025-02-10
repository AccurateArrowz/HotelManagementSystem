package org.example.Controllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.example.Main;
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
    private void choiceBoxesHandler() {
        //my 2 choiceboxes: roomType and roomAvailability
        roomType.getItems().addAll(roomTypes);
        for(RoomAvailabilityStatuses status : RoomAvailabilityStatuses.values()){
            roomAvailability.getItems().add(status.toString());
        }


    }
    private void tableViewInitializer() {
        roomNoColumn.setCellValueFactory(new PropertyValueFactory<Rooms,String>("roomNo"));
        //the argument in constructor of PropertyValueFactory is for setting text top of column
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<Rooms,String>("roomType"));
        roomAvailabilityColumn.setCellValueFactory(new PropertyValueFactory<Rooms, RoomAvailabilityStatuses>("roomAvailability"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<Rooms,String>("roomPrice"));
        cleaningStatusColumn.setCellValueFactory(new PropertyValueFactory<Rooms, String>("cleaningStatus"));

        //getting data from db and adding via observable list
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HotelManagementSystem", "root", "sujay10AWM");
            PreparedStatement pS = dbConnection.prepareStatement("SELECT * FROM Rooms");
            ResultSet resultSet = pS.executeQuery();
            while(resultSet.next()){
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

    public void clearFields() {


        roomNoField.clear();
        roomType.getSelectionModel().clearSelection();
        roomAvailability.setValue(null);

//        roomAvailability.getItems().remove("BOOKED");

        roomPriceField.clear();

        cleaningStatus.selectToggle(null);

        roomNoField.setDisable(false);
    }
    private void setErrorLabel(String errorMsg) {
        errorLabel.setText(errorMsg);
        // Remove error message after 2 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> errorLabel.setText(""));
        pause.play();

    }

    public void addNewRoom() {
        String newRoomNo = roomNoField.getText();
        String newRoomType = roomType.getValue();
        String newRoomAvailability = roomAvailability.getValue();
        String newRoomPrice = roomPriceField.getText();
        Integer parsedNewRoomNo;
        Double parsedNewRoomPrice;

        if (newRoomNo == null || newRoomNo.trim().isEmpty() ||
                newRoomType == null || newRoomAvailability == null ||
                newRoomPrice == null || newRoomPrice.trim().isEmpty() ||
                cleaningStatus.getSelectedToggle() == null) {
            setErrorLabel("Error: All fields must be filled.");
            return;
        }

        for (Rooms room : roomsList) {
            if (Integer.toString(room.getRoomNo()).equals(newRoomNo)) {
                setErrorLabel("Room with id " + room.getRoomNo() + " already exists!");
                return;
            }
        }
        //parsing and validating roomNO and roomPrice
        try {
             parsedNewRoomNo = Integer.parseInt(newRoomNo);
        } catch (NumberFormatException e) {
            setErrorLabel("Room No must be a valid number.");
            return;
        }
        try {
            parsedNewRoomPrice = Double.parseDouble(newRoomPrice);
        } catch (NumberFormatException e) {
            setErrorLabel("Price must be a valid price.");
            return;
        }

        //Persisting the data to db
        try(Connection conn = Main.getMyHikariConnection()) {


            RoomAvailabilityStatuses parsedNewRoomAvailability = RoomAvailabilityStatuses.valueOf(newRoomAvailability.toUpperCase());
            String newRoomCleaningStatus = ((RadioButton) cleaningStatus.getSelectedToggle()).getText();

            String sql = "INSERT INTO Rooms (Room_No, Type, Availability, Price, Cleaning_Status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, parsedNewRoomNo);
            pstmt.setString(2, newRoomType);
            pstmt.setString(3, parsedNewRoomAvailability.name()); // Convert Enum to String
            pstmt.setDouble(4, parsedNewRoomPrice);
            pstmt.setString(5, newRoomCleaningStatus);
            pstmt.executeUpdate();

            //  Add to UI Table**
            roomsList.add(new Rooms(parsedNewRoomNo, newRoomType, parsedNewRoomAvailability, parsedNewRoomPrice, newRoomCleaningStatus));
            roomsTable.setItems(roomsList);
            roomsTable.refresh();
            clearFields();

        } catch (SQLException e) {
            setErrorLabel("Database Error: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            setErrorLabel("Invalid input: Room number and price must be numeric.");
        }
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
    public void updateRoom() {
        Rooms selectedRoom = roomsTable.getSelectionModel().getSelectedItem();

        if (selectedRoom == null) {
            setErrorLabel("Error: No room selected for update.");
            return;
        }

        String newType = roomType.getValue();
        String newAvailability = roomAvailability.getValue();
        String newPrice = roomPriceField.getText();
        String newCleaningStatus = ((RadioButton) cleaningStatus.getSelectedToggle()).getText();

        // Validate Input (Ensure all fields are filled)
        if (newType == null || newAvailability == null || newPrice == null || newPrice.trim().isEmpty() || newCleaningStatus == null) {
            setErrorLabel("Error: All fields must be filled.");
            return;
        }

        double parsedNewPrice;
        try {
            parsedNewPrice = Double.parseDouble(newPrice);
        } catch (NumberFormatException e) {
            setErrorLabel("Invalid input: Price must be numeric.");
            return;
        }

        RoomAvailabilityStatuses parsedNewAvailability = RoomAvailabilityStatuses.valueOf(newAvailability.toUpperCase());

        // Update the selected room in the UI
        selectedRoom.setRoomType(newType);
        selectedRoom.setRoomAvailability(parsedNewAvailability);
        selectedRoom.setRoomPrice(parsedNewPrice);
        selectedRoom.setCleaningStatus(newCleaningStatus);

        roomsTable.refresh(); // Refresh table to reflect changes

        // Update the database
        String updateQuery = "UPDATE Rooms SET Type = ?, Availability = ?, Price = ?, Cleaning_Status = ? WHERE Room_No = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_db", "root", "your_password");
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {

            pstmt.setString(1, newType);
            pstmt.setString(2, parsedNewAvailability.name());
            pstmt.setDouble(3, parsedNewPrice);
            pstmt.setString(4, newCleaningStatus);
            pstmt.setInt(5, selectedRoom.getRoomNo());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Room successfully updated in the database.");
            } else {
                setErrorLabel("Error: Could not update room in database.");
            }

        } catch (SQLException e) {
            setErrorLabel("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteRoom() {
        // First, alert and ask for confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText(null); // No header
        alert.setContentText("Are you sure you want to delete this room?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Rooms selectedRoom = roomsTable.getSelectionModel().getSelectedItem();

            if (selectedRoom == null) {
                System.out.println("No room selected for deletion.");
                return;
            }

            // Remove from the database
            String query = "DELETE FROM Rooms WHERE Room_No = ?";

            try (Connection conn = Main.getMyHikariConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, selectedRoom.getRoomNo());
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    roomsList.remove(selectedRoom); // Remove from UI list
                    System.out.println("Room deleted successfully from database!");
                    roomsTable.refresh();
                    clearFields();
                } else {
                    System.out.println("Failed to delete room from database.");
                }

            } catch (SQLException e) {
                System.out.println("Database error while deleting room: " + e.getMessage());
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
}
