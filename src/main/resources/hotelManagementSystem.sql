use  HotelManagementSystem;

CREATE TABLE IF NOT EXISTS Rooms (
                                     Room_No INT AUTO_INCREMENT PRIMARY KEY,
                                     Type VARCHAR(50) NOT NULL,
                                     Availability ENUM('BOOKED', 'AVAILABLE', 'PREPARING') NOT NULL,
                                     Price DECIMAL(10,2) NOT NULL,
                                     Cleaning_Status VARCHAR(20) NOT NULL
);
# INSERT INTO Rooms (Type, Availability, Price, Cleaning_Status) VALUES
#                                                                    ('Double', 'AVAILABLE', 2200.00, 'Cleaned'),
#                                                                     ('King', 'PREPARING', 3900.00, 'Cleaned'),
#                                                                    ('Single', 'BOOKED', 1800.00, 'Not Cleaned'),
#                                                                    ('Triple', 'AVAILABLE', 2500.00, 'Cleaned'),
#                                                                    ('Double', 'BOOKED', 2300.00, 'Not Cleaned'),
#                                                                    ('King', 'AVAILABLE', 4000.00, 'Cleaned'),
#                                                                    ('Single', 'PREPARING', 1700.00, 'Not Cleaned'),
#                                                                    ('Triple', 'BOOKED', 2700.00, 'Not Cleaned'),
#                                                                    ('Double', 'AVAILABLE', 2400.00, 'Cleaned'),
#                                                                    ('King', 'BOOKED', 4200.00, 'Not Cleaned');

CREATE TABLE IF NOT EXISTS Customers (
                                         CustomerId INT AUTO_INCREMENT PRIMARY KEY,
                                         FirstName VARCHAR(50) NOT NULL,
                                         LastName VARCHAR(50) NOT NULL,
                                         Phone VARCHAR(20) UNIQUE NOT NULL,
                                         Passport VARCHAR(20) UNIQUE,
                                         Notes TEXT
);
# INSERT INTO Customers (FirstName, LastName, Phone, Passport, Notes) VALUES
#                                                                         ('John', 'Doe', '1234567890', 'A1234567', 'Regular guest, prefers sea-facing rooms.'),
#                                                                         ('Alice', 'Johnson', '9876543210', 'B7654321', 'VIP customer, requests early check-ins.'),
#                                                                         ('Michael', 'Smith', '4567891230', 'C9876543', 'Prefers non-smoking rooms.'),
#                                                                         ('Emma', 'Brown', '3216549870', NULL, 'Loyal customer, often books deluxe rooms.'),
#                                                                         ('Robert', 'Wilson', '7418529630', 'D7418529', 'Requested airport pickup service.'),
#                                                                         ('Sophia', 'Miller', '8529637410', NULL, 'Enjoys spa services at the hotel.'),
#                                                                         ('William', 'Davis', '3692581470', 'E3692581', 'Prefers quiet floors, late check-outs.'),
#                                                                         ('Olivia', 'Martinez', '1597534862', 'F1597534', 'Guest for a business conference.'),
#                                                                         ('James', 'Taylor', '7531594826', NULL, 'Requested extra pillows and blankets.'),
#                                                                         ('Isabella', 'Anderson', '9517536284', 'G9517536', NULL);

CREATE TABLE IF NOT EXISTS Bookings (
                                        BookingId INT AUTO_INCREMENT PRIMARY KEY,
                                        RoomNo INT NOT NULL,
                                        CustomerId INT NOT NULL,
                                        Price DECIMAL(10,2) NOT NULL,
                                        Discount DECIMAL(5,2) NOT NULL DEFAULT 0,  -- Discount in percentage
                                        FinalPrice DECIMAL(10,2) NOT NULL,  -- Price after discount
                                        CheckinDate DATE NOT NULL,
                                        CheckoutDate DATE NOT NULL,
                                        FOREIGN KEY (RoomNo) REFERENCES Rooms(Room_No) ON DELETE CASCADE,
                                        FOREIGN KEY (CustomerId) REFERENCES Customers(CustomerId) ON DELETE CASCADE
);
# INSERT INTO Bookings (RoomNo, CustomerId, Price, Discount, FinalPrice, CheckinDate, CheckoutDate) VALUES
#
#               (1, 1, 2200.00, 10, 1980.00, '2024-03-01', '2024-03-03'),
#               (2, 2, 3900.00, 5, 3705.00, '2024-03-02', '2024-03-04'),
#               (3, 3, 1800.00, 15, 1530.00, '2024-03-03', '2024-03-05'),
#               (4, 4, 2500.00, 8, 2300.00, '2024-03-04', '2024-03-06'),
#               (5, 5, 2300.00, 12, 2024.00, '2024-03-05', '2024-03-07'),
#               (6, 6, 4000.00, 10, 3600.00, '2024-03-06', '2024-03-08'),
#               (7, 7, 1700.00, 7, 1581.00, '2024-03-07', '2024-03-09'),
#               (8, 8, 2700.00, 5, 2565.00, '2024-03-08', '2024-03-10'),
#               (9, 9, 2400.00, 6, 2256.00, '2024-03-09', '2024-03-11'),
#               (10, 10, 4200.00, 9, 3822.00, '2024-03-10', '2024-03-12');

CREATE TABLE IF NOT EXISTS USERS(
                       UserId INT AUTO_INCREMENT PRIMARY KEY,
                       FirstName VARCHAR(50) NOT NULL,
                       LastName VARCHAR(50) NOT NULL,
                       Email VARCHAR(100) UNIQUE NOT NULL,
                       DateOfBirth DATE NOT NULL,
                       Type ENUM('admin', 'receptionist') NOT NULL,
                       Phone VARCHAR(20) UNIQUE NOT NULL,
                       Country VARCHAR(50) NOT NULL,
                       Password VARCHAR(255) NOT NULL
);