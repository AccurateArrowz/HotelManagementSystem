package org.example.Model;

import java.time.LocalDate;

public class Customer {
    private Integer customerId;  // Using wrapper class for flexibility
    private String firstName;
    private String lastName;
    private String phone;
    private String passport;
    private String notes;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double discount;
    private Integer roomId;

    //Constructor with limited parameters,to be used for CustomerDetails Table

    public Customer(Integer customerId, String firstName, String lastName, String phone, String passport, String notes) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.passport = passport;
        this.notes = notes;
    }

    // Constructor with all parameters,to be used for customerBookingsTable
    public Customer(Integer customerId, String firstName, String lastName, String phone,
                    String passport, String notes, LocalDate checkInDate, LocalDate checkOutDate, Double discount, Integer roomId) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.passport = passport;
        this.notes = notes;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.discount = discount;
        this.roomId = roomId;
    }

    // Default constructor
    public Customer() {}

    // Getters and Setters
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    // toString() method for easy debugging
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", passport='" + passport + '\'' +
                ", notes='" + notes + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", discount=" + discount +
                '}';
    }
}