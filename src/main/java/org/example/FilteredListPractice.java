package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Model.Customer;

import java.util.logging.Filter;

public class FilteredListPractice extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
//        ObservableList<String> foodList = FXCollections.observableArrayList();
//        foodList.addAll("Apple","Arch","Bannana", "Blueberry", "Coconut", "Walnut");
//        FilteredList<String> filteredList = new FilteredList<>(foodList, item->true);

        Stage newStage = new Stage();
        VBox vBox = new VBox();
        TextField textField = new TextField();
        textField.setPromptText("Search Customer");

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("Alex");
        customer1.setPassport("PA22407");
        Customer customer2 = new Customer();
        customer2.setCustomerId(2);
        customer2.setFirstName("Hamz");
        customer2.setPassport("PA23407");
        Customer customer3 = new Customer();
        customer3.setCustomerId(1);
        customer3.setFirstName("Rodri");
        customer3.setPassport("PA22407");
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        customerList.addAll(customer1, customer2, customer3);
        FilteredList<Customer> filteredCustomers= new FilteredList<>(customerList, customer-> true );


        textField.textProperty().addListener((observable, oldValue, newValue)->
        { filteredCustomers.setPredicate(customer -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String lowerCasedNewValue = newValue.toLowerCase();
                 return (Integer.toString(customer.getCustomerId()).contains(lowerCasedNewValue)||
                        customer.getFirstName().contains(lowerCasedNewValue) ||
                        customer.getPassport().contains(lowerCasedNewValue));
                });
            System.out.println(filteredCustomers);
    });
        vBox.getChildren().add(textField);

        Scene scene = new Scene(vBox, 400, 300);

        newStage.setScene(scene);
        newStage.show();
    }
}
