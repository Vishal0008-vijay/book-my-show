/**
 * Book My Stay App
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates use of HashMap for centralized inventory handling.
 *
 * @author Vishal
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory Class
class RoomInventory {

    private Map<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:\n");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase3InventorySetup {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App (v3.1) ======");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Simulate update
        System.out.println("\nUpdating inventory...\n");
        inventory.updateAvailability("Single Room", 4);

        // Display updated inventory
        inventory.displayInventory();
    }
}
