package org.example.Controllers;

public enum RoomAvailabilityStatuses {
    AVAILABLE,       // Room is available for booking
    BOOKED,          // Room is confirmed as booked
//    RESERVED,        // Room is reserved but not yet confirmed
//    OUT_OF_SERVICE,  // Room is unavailable due to maintenance or other issues
    PREPARING        // Room is being prepared (e.g., cleaned or inspected)
}