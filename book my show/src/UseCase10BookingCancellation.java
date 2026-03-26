import java.util.*;

// Booking class to store booking details
class Booking {
    String bookingId;
    String roomType;
    String roomId;
    boolean isCancelled;

    Booking(String bookingId, String roomType, String roomId) {
        this.bookingId = bookingId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.isCancelled = false;
    }
}

// Main system class
public class UseCase10BookingCancellation {

    // Inventory: Room Type -> Count
    static Map<String, Integer> inventory = new HashMap<>();

    // Bookings: Booking ID -> Booking Object
    static Map<String, Booking> bookings = new HashMap<>();

    // Stack for rollback (LIFO)
    static Stack<String> rollbackStack = new Stack<>();

    public static void main(String[] args) {

        // Initial inventory
        inventory.put("Single", 2);
        inventory.put("Double", 1);

        // Create sample bookings
        createBooking("B1", "Single");
        createBooking("B2", "Single");

        // Try cancellation
        cancelBooking("B1");   // Valid
        cancelBooking("B1");   // Already cancelled
        cancelBooking("B3");   // Invalid booking

        // Final state
        System.out.println("\nFinal Inventory: " + inventory);
    }

    // Method to create booking
    static void createBooking(String bookingId, String roomType) {

        if (!inventory.containsKey(roomType) || inventory.get(roomType) == 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        // Allocate room
        String roomId = roomType + "_" + (inventory.get(roomType));
        inventory.put(roomType, inventory.get(roomType) - 1);

        Booking booking = new Booking(bookingId, roomType, roomId);
        bookings.put(bookingId, booking);

        System.out.println("Booking Confirmed: " + bookingId + " Room: " + roomId);
    }

    // Method to cancel booking
    static void cancelBooking(String bookingId) {

        System.out.println("\nCancelling Booking: " + bookingId);

        // Step 1: Validate booking exists
        if (!bookings.containsKey(bookingId)) {
            System.out.println("Invalid booking ID!");
            return;
        }

        Booking booking = bookings.get(bookingId);

        // Step 2: Check if already cancelled
        if (booking.isCancelled) {
            System.out.println("Booking already cancelled!");
            return;
        }

        // Step 3: Push room ID to rollback stack
        rollbackStack.push(booking.roomId);

        // Step 4: Restore inventory
        String roomType = booking.roomType;
        inventory.put(roomType, inventory.get(roomType) + 1);

        // Step 5: Update booking status
        booking.isCancelled = true;

        // Step 6: Confirm rollback
        System.out.println("Cancellation successful!");
        System.out.println("Room released: " + booking.roomId);
        System.out.println("Updated Inventory: " + inventory);
    }
}