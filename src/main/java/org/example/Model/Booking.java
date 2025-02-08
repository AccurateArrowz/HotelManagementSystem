package org.example.Model;

import java.time.LocalDate;

public class Booking {
    private Integer customerId;
    private Integer roomId;
    private Double roomPrice;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Double discount;
    private Double finalPrice;

    public Booking(Integer customerId, Integer roomId, Double roomPrice, LocalDate checkInDate, LocalDate checkOutDate, Double discount, Double finalPrice) {
        this.customerId = customerId;
        this.roomId = roomId;
        this.roomPrice = roomPrice;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    // Getters and Setters
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Integer getRoomId() { return roomId; }
    public void setRoomId(Integer roomId) { this.roomId = roomId; }

    public Double getRoomPrice() { return roomPrice; }
    public void setRoomPrice(Double roomPrice) { this.roomPrice = roomPrice; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }

    public Double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(Double finalPrice) { this.finalPrice = finalPrice; }
}