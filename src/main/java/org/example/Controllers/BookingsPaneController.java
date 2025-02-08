package org.example.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Main;
import org.example.Model.Booking;
import org.example.Model.Customer;

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
    //obserable list for both the tables
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Booking> bookingsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        //mapping appropriate fields of Customer class to the columns
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("passport"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("phone"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<Customer,String>("notes"));
        // mapping appropriate fields of Bookings class to bookings Table
        bookings_customerId.setCellValueFactory(new PropertyValueFactory<Booking,Integer>("customerId"));
        bookings_roomId.setCellValueFactory(new PropertyValueFactory<Booking,Integer>("roomId"));
        bookings_roomPrice.setCellValueFactory(new PropertyValueFactory<Booking,Double>("roomPrice"));
        bookings_checkinDate.setCellValueFactory(new PropertyValueFactory<Booking,LocalDate>("checkInDate"));
        bookings_checkoutDate.setCellValueFactory(new PropertyValueFactory<Booking,LocalDate>("checkOutDate"));
        bookings_discount.setCellValueFactory(new PropertyValueFactory<Booking,Double>("discount"));
        bookings_finalPrice.setCellValueFactory(new PropertyValueFactory<Booking,Double>("finalPrice"));
        populateCustomersTable();
        populateBookingsTable();
        }

    private void populateCustomersTable() {
        try {
            Connection dbConnection = Main.getMyHikariConnection();
            ResultSet resultSet= dbConnection.prepareStatement("select * from Customers").executeQuery();
            while (resultSet.next()){
                System.out.print(resultSet.getString("CustomerId"));
                System.out.print(resultSet.getString("FirstName"));
                System.out.print(resultSet.getString("LastName"));
                System.out.print(resultSet.getString("Passport"));
                System.out.print(resultSet.getString("Phone"));
                System.out.print(resultSet.getString("Notes"));
                System.out.println("");
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
            customersTable.setItems(customerList);
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

                bookingsList.add(retrievedBooking);
            }
//            System.out.println(bookingsList);
            customerBookingsTable.setItems(bookingsList);
            customerBookingsTable.refresh();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insertBooking(Booking booking) {

        try (Connection dbConnection = Main.getMyHikariConnection();
             PreparedStatement statement = dbConnection.prepareStatement("INSERT INTO Bookings (CustomerId, RoomId, RoomPrice, CheckInDate, CheckOutDate, Discount, FinalPrice) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            statement.setInt(1, booking.getCustomerId());
            statement.setInt(2, booking.getRoomId());
            statement.setDouble(3, booking.getRoomPrice());
            statement.setDate(4, java.sql.Date.valueOf(booking.getCheckInDate()));
            statement.setDate(5, java.sql.Date.valueOf(booking.getCheckOutDate()));
            statement.setDouble(6, booking.getDiscount());
            statement.setDouble(7, booking.getFinalPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
