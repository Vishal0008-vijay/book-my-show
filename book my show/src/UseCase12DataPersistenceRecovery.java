import java.io.*;
import java.util.*;

// Booking class (must implement Serializable)
class Booking implements Serializable {
    String bookingId;
    String roomType;

    Booking(String bookingId, String roomType) {
        this.bookingId = bookingId;
        this.roomType = roomType;
    }
}

// Main System
public class UseCase12DataPersistenceRecovery {

    static Map<String, Integer> inventory = new HashMap<>();
    static Map<String, Booking> bookings = new HashMap<>();

    static final String FILE_NAME = "system_data.ser";

    public static void main(String[] args) {

        // Step 1: Load previous state (Recovery)
        loadData();

        // If no data exists, initialize fresh inventory
        if (inventory.isEmpty()) {
            inventory.put("Single", 2);
            inventory.put("Double", 1);
        }

        // Perform some operations
        createBooking("B1", "Single");
        createBooking("B2", "Double");

        // Show current state
        System.out.println("Current Inventory: " + inventory);

        // Step 2: Save state before exit (Persistence)
        saveData();

        System.out.println("System state saved successfully!");
    }

    // Create booking
    static void createBooking(String bookingId, String roomType) {

        if (inventory.getOrDefault(roomType, 0) > 0) {

            bookings.put(bookingId, new Booking(bookingId, roomType));
            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Booking Confirmed: " + bookingId);

        } else {
            System.out.println("No rooms available for " + roomType);
        }
    }

    // Save data to file
    static void saveData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            oos.writeObject(inventory);
            oos.writeObject(bookings);

            oos.close();

        } catch (IOException e) {
            System.out.println("Error saving data!");
        }
    }

    // Load data from file
    static void loadData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));

            inventory = (Map<String, Integer>) ois.readObject();
            bookings = (Map<String, Booking>) ois.readObject();

            ois.close();

            System.out.println("Data restored successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");

        } catch (Exception e) {
            System.out.println("Data corrupted! Starting with safe state.");
            inventory = new HashMap<>();
            bookings = new HashMap<>();
        }
    }
}
