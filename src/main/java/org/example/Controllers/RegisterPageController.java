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

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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


    public void validateRegistration() {
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

        // If all validations pass
        showAlert(Alert.AlertType.INFORMATION, "Success", "Registration Validated", "All fields are valid!");
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
