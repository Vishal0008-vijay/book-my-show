/**
 * Book My Stay App
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates historical tracking of confirmed bookings
 * and simple reporting for admin purposes.
 *
 * Author: Vishal
 * Version: 8.0
 */

import java.util.*;

// Simple Reservation class
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

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return reservationId + ": " + guestName + " → " + roomType + " (₹" + totalCost + ")";
    }
}

// Booking History Manager
class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    // Add confirmed reservation to history
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
        System.out.println("Reservation added: " + reservation);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(confirmedReservations);
    }

    // Generate a simple report
    public void generateReport() {
        System.out.println("\n===== Booking Report =====");
        double totalRevenue = 0;
        for (Reservation r : confirmedReservations) {
            System.out.println(r);
            totalRevenue += r.getTotalCost();
        }
        System.out.println("Total Reservations: " + confirmedReservations.size());
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main Class
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        System.out.println("====== Book My Stay App (v8.0) ======");

        BookingHistory history = new BookingHistory();

        // Simulated reservations (as if confirmed via Use Case 6 & 7)
        Reservation r1 = new Reservation("R101", "Alice", "Single Room + Breakfast + Airport Pickup", 750);
        Reservation r2 = new Reservation("R102", "Bob", "Double Room + Spa", 1000);
        Reservation r3 = new Reservation("R103", "Charlie", "Suite Room + Breakfast + Spa", 1250);

        // Add reservations to history
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin generates booking report
        history.generateReport();
    }
}
