/**
 * Book My Stay App
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates safe read-only access to centralized inventory
 * and displays available room details without modifying system state.
 *
 * @author Vishal
 * @version 4.1
 */

import java.util.HashMap;
import java.util.Map;

// Room abstract class (reuse from Use Case 2)
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public abstract void displayDetails();

    public String getRoomType() {
        return roomType;
    }
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 1000); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 2000); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 5000); }
    @Override
    public void displayDetails() {
        System.out.println(roomType + " | Beds: " + beds + " | Price: ₹" + price);
    }
}

// Centralized Inventory
class RoomInventory {
    private final Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // simulate unavailable
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return new HashMap<>(inventory); // return a copy to prevent modification
    }
}

// Search service (read-only)
class RoomSearchService {
    private final RoomInventory inventory;
    private final Room[] rooms;

    public RoomSearchService(RoomInventory inventory, Room[] rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void displayAvailableRooms() {
        System.out.println("\nAvailable Rooms:\n");
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayDetails();
                System.out.println("Available: " + available + "\n");
            }
        }
    }
}

// Main class
public class UseCase4RoomSearch {

    public static void main(String[] args) {
        System.out.println("====== Book My Stay App (v4.1) ======");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize room objects
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        // Initialize search service
        RoomSearchService searchService = new RoomSearchService(inventory, rooms);

        // Display available rooms
        searchService.displayAvailableRooms();
    }
}