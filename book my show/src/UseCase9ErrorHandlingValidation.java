/**
 * Book My Stay App
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates structured validation and custom exceptions
 * to prevent invalid bookings and preserve system state.
 *
 * Author: Vishal
 * Version: 9.0
 */

import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private double totalCost;

    public Reservation(String reservationId, String guestName, String roomType, double totalCost) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return reservationId + ": " + guestName + " → " + roomType + " (₹" + totalCost + ")";
    }
}

// Inventory class
class Inventory {
    private Map<String, Integer> availableRooms;

    public Inventory() {
        availableRooms = new HashMap<>();
        availableRooms.put("Single Room", 2);
        availableRooms.put("Double Room", 1);
        availableRooms.put("Suite Room", 1);
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        if (!availableRooms.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        if (availableRooms.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        checkAvailability(roomType); // validate first
        availableRooms.put(roomType, availableRooms.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : availableRooms.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        System.out.println("====== Book My Stay App (v9.0) ======");

        Inventory inventory = new Inventory();
        List<Reservation> confirmedReservations = new ArrayList<>();

        // Sample booking requests (some invalid)
        String[][] bookingRequests = {
                {"R201", "Alice", "Single Room", "700"},
                {"R202", "Bob", "Triple Room", "1200"}, // Invalid room type
                {"R203", "Charlie", "Suite Room", "1500"},
                {"R204", "Dave", "Double Room", "1000"},
                {"R205", "Eve", "Double Room", "1000"} // No rooms left
        };

        for (String[] request : bookingRequests) {
            try {
                String id = request[0];
                String guest = request[1];
                String roomType = request[2];
                double cost = Double.parseDouble(request[3]);

                // Validate and book
                inventory.checkAvailability(roomType);
                inventory.bookRoom(roomType);

                Reservation reservation = new Reservation(id, guest, roomType, cost);
                confirmedReservations.add(reservation);
                System.out.println("Booking confirmed: " + reservation);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Booking failed: Invalid cost format");
            } catch (Exception e) {
                System.out.println("Booking failed: Unexpected error - " + e.getMessage());
            }
        }

        System.out.println("\nFinal Inventory:");
        inventory.printInventory();
        System.out.println("\nConfirmed Reservations:");
        for (Reservation r : confirmedReservations) {
            System.out.println(r);
        }
    }
}
