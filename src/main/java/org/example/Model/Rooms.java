package org.example.Model;

import org.example.Controllers.RoomAvailabilityStatuses;

public class Rooms {
    //fields
    private Integer roomNo;
    private String roomType;
    private RoomAvailabilityStatuses roomAvailability;  // Using the RoomAvailabilityStatuses enum
    private Double roomPrice;
    private String cleaningStatus;


    // Constructor
    public Rooms(Integer roomNo, String roomType, RoomAvailabilityStatuses roomAvailability, Double roomPrice, String cleaningStatus) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.roomAvailability = roomAvailability;
        this.roomPrice = roomPrice;
        this.cleaningStatus = cleaningStatus;
    }

    // Getters and Setters
    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public RoomAvailabilityStatuses getRoomAvailability() {
        return roomAvailability;
    }

    public void setRoomAvailability(RoomAvailabilityStatuses roomAvailability) {
        this.roomAvailability = roomAvailability;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getCleaningStatus() {
        return cleaningStatus;
    }

    public void setCleaningStatus(String cleaningStatus) {
        this.cleaningStatus = cleaningStatus;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNo=" + roomNo +
                ", roomType='" + roomType + '\'' +
                ", roomAvailability=" + roomAvailability +
                ", roomPrice=" + roomPrice +
                ", cleaningStatus='" + cleaningStatus + '\'' +
                '}';
    }
}