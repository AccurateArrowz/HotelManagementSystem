package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.Main;
import org.example.Model.Booking;
import org.example.Model.Customer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookingsPaneController {
    //@FXML CustomersTable and columns
    @FXML
    TableView customersTable;
    @FXML
    TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    TableColumn<Customer,String> firstNameColumn;
    @FXML
    TableColumn<Customer,String> lastNameColumn;
    @FXML
    TableColumn<Customer,String> passportColumn;
    @FXML
    TableColumn<Customer,String> phoneColumn;
    @FXML
    TableColumn<Customer,String> notesColumn;
    //@FXML BookingsTable and columns
    @FXML
    private TableView<Booking> customerBookingsTable;
    @FXML
    private TableColumn<Booking, Integer> bookings_customerId;
    @FXML
    private TableColumn<Booking, Integer> bookings_roomId;
    @FXML
    private TableColumn<Booking, Double> bookings_roomPrice;
    @FXML
    private TableColumn<Booking, LocalDate> bookings_checkinDate;
    @FXML
    private TableColumn<Booking, LocalDate> bookings_checkoutDate;
    @FXML
    private TableColumn<Booking, Double> bookings_discount;
    @FXML
    private TableColumn<Booking, Double> bookings_finalPrice;
    @FXML
    private TextField bookingsSearchField;
    @FXML
    private TextField customersSearchField;
    //obserable list for both the tables
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private static ObservableList<Booking> bookingsList = FXCollections.observableArrayList();

    private FilteredList<Booking> filteredBookings = new FilteredList<>(bookingsList, booking->true);
    private FilteredList<Customer> filteredCustomers = new FilteredList<>(customerList, customer->true);
    public static BookingsPaneController instance;

    public BookingsPaneController() {
        instance = this;
    }
    public static BookingsPaneController getInstance() {
        return instance;
    }
    @FXML
    public void initialize() {
        //mapping appropriate fields of Customer class to the columns
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("passport"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("notes"));
        // mapping appropriate fields of Bookings class to bookings Table
        bookings_customerId.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("customerId"));
        bookings_roomId.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("roomId"));
        bookings_roomPrice.setCellValueFactory(new PropertyValueFactory<Booking, Double>("roomPrice"));
        bookings_checkinDate.setCellValueFactory(new PropertyValueFactory<Booking, LocalDate>("checkInDate"));
        bookings_checkoutDate.setCellValueFactory(new PropertyValueFactory<Booking, LocalDate>("checkOutDate"));
        bookings_discount.setCellValueFactory(new PropertyValueFactory<Booking, Double>("discount"));
        bookings_finalPrice.setCellValueFactory(new PropertyValueFactory<Booking, Double>("finalPrice"));

        populateCustomersTable();
        populateBookingsTable();
        addDynamicSearch(); //for bookings and customers search
    }



    private void addDynamicSearch() {
        //applying filter to filteredList once the searchField is entered
        bookingsSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBookings.setPredicate(booking-> {
                if (newValue.isEmpty()) {
                    return true;
                }
                String keyword = newValue.toLowerCase();
                return (String.valueOf(booking.getCustomerId())).contains(keyword) ||
                        (String.valueOf(booking.getCheckInDate())).contains(keyword) ||
                        (String.valueOf(booking.getCheckOutDate())).contains(keyword);

            });
        });
        customersSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomers.setPredicate(customer -> {
                if (newValue.isEmpty()) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                // Converting all fields to lowercase before checking contains
                String customerId = String.valueOf(customer.getCustomerId()).toLowerCase();
                String firstName = (customer.getFirstName() != null) ? customer.getFirstName().toLowerCase() : "";
                String lastName = (customer.getLastName() != null) ? customer.getLastName().toLowerCase() : "";
                String passport = (customer.getPassport() != null) ? customer.getPassport().toLowerCase() : "";
                String phone = (customer.getPhone() != null) ? customer.getPhone().toLowerCase() : "";

                return customerId.contains(keyword) ||
                        firstName.contains(keyword) ||
                        lastName.contains(keyword) ||
                        passport.contains(keyword) ||
                        phone.contains(keyword);
            });
        });
    }


    private void populateCustomersTable() {
        try {
            Connection dbConnection = Main.getMyHikariConnection();
            ResultSet resultSet= dbConnection.prepareStatement("select * from Customers").executeQuery();
            while (resultSet.next()){
                Customer retrievedCustomer= new Customer(
                        resultSet.getInt("CustomerId"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Passport"),
                        resultSet.getString("Notes")
                );
                customerList.add(retrievedCustomer);

            }
            customersTable.setItems(filteredCustomers);
            customersTable.refresh();
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void populateBookingsTable() {
        try {
            Connection dbConnection = Main.getMyHikariConnection();
            ResultSet resultSet = dbConnection.prepareStatement("SELECT * FROM Bookings").executeQuery();

            while (resultSet.next()) {
                //printing the result set in console
                System.out.print(resultSet.getInt("CustomerId") + " ");
                System.out.print(resultSet.getInt("RoomId") + " ");
                System.out.print(resultSet.getDouble("RoomPrice") + " ");
                System.out.print(resultSet.getDate("CheckInDate") + " ");
                System.out.print(resultSet.getDate("CheckOutDate") + " ");
                System.out.print(resultSet.getDouble("Discount") + " ");
                System.out.print(resultSet.getDouble("FinalPrice"));
                System.out.println();

                Booking retrievedBooking = new Booking(
                        resultSet.getInt("CustomerId"),
                        resultSet.getInt("RoomId"),
                        resultSet.getDouble("RoomPrice"),
                        resultSet.getDate("CheckInDate").toLocalDate(),
                        resultSet.getDate("CheckOutDate").toLocalDate(),
                        resultSet.getDouble("Discount"),
                        resultSet.getDouble("FinalPrice")
                );

                bookingsList.add(retrievedBooking);//storing in the observableList so that it is safe
            }

            customerBookingsTable.setItems(filteredBookings);//however, inserting the filteredList for any filter functionality in future
            customerBookingsTable.refresh();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void openBookNewRoomPage()  {
        Stage newRoomStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFolder/BookNewRoom.fxml"));
            Scene scene = new Scene(loader.load());
            newRoomStage.setScene(scene);
            newRoomStage.setTitle("Book New Room");
            newRoomStage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }
    public static void updateBooking(Integer customerId, Integer roomId, LocalDate checkInDate, LocalDate checkOutDate, Double roomPrice, Double discount) {
        for(Booking booking: bookingsList){
            if(booking.getCustomerId().equals(customerId)){
                booking.setRoomId(roomId);
                booking.setCheckInDate(checkInDate);
                booking.setCheckOutDate(checkOutDate);
                booking.setDiscount(discount);
                Double discountAmount= (roomPrice*discount)/100;
                booking.setFinalPrice(roomPrice - discountAmount);
                if(instance!=null){
                    instance.customerBookingsTable.refresh();
                }
            }
        }



    }

}
