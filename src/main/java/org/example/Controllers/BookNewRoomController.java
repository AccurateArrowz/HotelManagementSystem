package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Main;
import org.example.Model.Rooms;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static org.example.Controllers.RoomsPaneController.roomsListGetter;
import static org.example.Controllers.RoomsPaneController.updateRoomToBooked;
//import org.example.Controllers.RoomsPaneController.;

public class BookNewRoomController implements Initializable {
    @FXML
    VBox bookRoomVbox;
    @FXML
    VBox customerDetailsVbox;
    @FXML
    RadioButton newCustomerRadioButton;
    @FXML
    TextField customerIdField;
    @FXML
    ChoiceBox<Integer> roomIdChoiceBox;
    @FXML
    TextField roomPriceField;
    @FXML
    TextField discountField;
    @FXML
    DatePicker checkInDatePicker;
    @FXML
    DatePicker checkOutDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIdField.setDisable(true);
        newCustomerRadioButton.setSelected(true);
        fillRoomIdChoiceBox();
        roomPriceField.setDisable(true);
        roomIdChoiceBox.setOnAction(event -> showRoomPrice());

    }



    private void fillRoomIdChoiceBox() {
        try(Connection con= Main.getMyHikariConnection()){
            PreparedStatement pst = con.prepareStatement("select * from ROOMS where Availability='AVAILABLE'");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                roomIdChoiceBox.getItems().add(rs.getInt(1));
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private void showRoomPrice() {
        //right now accesing the roomsList from frontend , and not retrieving from the db
        List<Rooms> roomsList= roomsListGetter();
        for (Rooms room : roomsList) {
            if(room.getRoomNo().equals(roomIdChoiceBox.getValue())){
                roomPriceField.setText(room.getRoomPrice().toString());
            }
        }
    }

    public void removeCustomerDetailsFields(){
        customerIdField.setDisable(false); //can enter the id
        customerIdField.setPromptText("");
        bookRoomVbox.getChildren().remove(customerDetailsVbox);
    }
    public void showCustomerDetailsFields(){
        customerIdField.setDisable(true);
        customerIdField.setPromptText("Auto-generated");
        bookRoomVbox.getChildren().add(3,customerDetailsVbox);
    }
    public void bookNewRoom(ActionEvent e) {
        Integer customerId= Integer.parseInt(customerIdField.getText());
        Integer bookedRoomId = roomIdChoiceBox.getValue();
        Double roomPrice = Double.parseDouble(roomPriceField.getText());
        Double discount = Double.parseDouble(discountField.getText());
        LocalDate checkInDate = checkInDatePicker.getValue();
        LocalDate checkOutDate = checkOutDatePicker.getValue();

        RoomsPaneController.updateRoomToBooked(bookedRoomId);

        BookingsPaneController.updateBooking(customerId, bookedRoomId, checkInDate, checkOutDate, roomPrice, discount);
        Stage bookNewRoomStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        bookNewRoomStage.close();
    }


}
