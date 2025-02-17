package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.example.Main.getMyHikariConnection;
import static org.example.Main.theStage;

public class RegisterPageController implements Initializable {

    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField emailField;
    @FXML
    ChoiceBox<String> accountType;
    @FXML
    DatePicker datePicker;
    @FXML
    TextField countryField;
    @FXML
    TextField passwordField;
    @FXML
    TextField confirmPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountType.getItems().addAll("Receptionist", "Admin");

    }
    public void returnToLoginPage(ActionEvent event){
        Stage registerStage= (Stage)((Node)event.getSource()).getScene().getWindow();
        registerStage.close();
    }


    public void validateRegistration(ActionEvent event) {
        // Check if any field is empty
        if (firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() ||
                emailField.getText().isEmpty() ||
                accountType.getValue() == null ||
                datePicker.getValue() == null ||
                countryField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                confirmPasswordField.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Missing Fields", "Please fill in all required fields.");
            return;
        }

        // Validate Email Format
        String email= emailField.getText();
        if (!(email.contains("@") && email.contains(".") && email.indexOf("@") < email.lastIndexOf("."))) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Email Format Error", "Please enter a valid email address.");
            return;
        }

        // Validate Password Match
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match", "Please enter the same password in both fields.");
            return;
        }

        // If all validations pass, Insert into database


        String query = "INSERT INTO users (firstName, lastName, email, dateOfBirth, type, country, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String hashedPassword = BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt());
        try (Connection conn = getMyHikariConnection();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, firstNameField.getText());
            stmt.setString(2, lastNameField.getText());
            stmt.setString(3, emailField.getText());
            stmt.setDate(4, java.sql.Date.valueOf(datePicker.getValue()));
            stmt.setString(5, accountType.getValue());
            stmt.setString(6, countryField.getText());
            stmt.setString(7, hashedPassword);

            stmt.executeUpdate();
            System.out.println("Registration successful!");


        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Validated", "Your account has been created successfully!");

        returnToLoginPage(event);
    }

        // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initOwner(theStage);
        alert.showAndWait();
    }
}
