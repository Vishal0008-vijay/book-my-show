/**
 * Book My Stay App
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Handles queued booking requests, confirms reservations,
 * assigns unique room IDs, and updates inventory safely.
 *
 * @author Vishal
 * @version 6.1
 */

import java.util.*;

// Room abstract class
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

// Centralized Inventory Service
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean decrementInventory(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}

// Reservation class (from Use Case 5)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// Booking Request Queue (from Use Case 5)
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added: " + reservation.getGuestName() +
                " | " + reservation.getRoomType());
    }

    public Reservation pollRequest() {
        return requestQueue.poll();
    }

    public boolean hasRequests() {
        return !requestQueue.isEmpty();
    }
}

// Booking Service: confirms reservations
class BookingService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms; // roomType → assigned room IDs
    private int roomIdCounter;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        roomIdCounter = 100; // starting room ID
    }

    // Generate unique room ID
    private String generateRoomId() {
        roomIdCounter++;
        return "R" + roomIdCounter;
    }

    public void processBooking(Reservation reservation) {
        String type = reservation.getRoomType();
        if (inventory.getAvailability(type) > 0) {
            String roomId = generateRoomId();
            allocatedRooms.putIfAbsent(type, new HashSet<>());
            allocatedRooms.get(type).add(roomId);

            inventory.decrementInventory(type);
            System.out.println("Reservation Confirmed: " + reservation.getGuestName() +
                    " → " + type + " | Room ID: " + roomId);
        } else {
            System.out.println("Reservation Failed: " + reservation.getGuestName() +
                    " → " + type + " (No availability)");
        }
    }

    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Rooms:");
        for (Map.Entry<String, Set<String>> entry : allocatedRooms.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

// Main Class
public class UseCase6RoomAllocationService {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App (v6.1) ======");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        BookingService bookingService = new BookingService(inventory);

        // Simulate incoming requests
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Suite Room"));
        queue.addRequest(new Reservation("Charlie", "Double Room"));
        queue.addRequest(new Reservation("David", "Single Room"));
        queue.addRequest(new Reservation("Eve", "Suite Room"));
        queue.addRequest(new Reservation("Frank", "Suite Room")); // extra to test availability

        // Process booking queue
        System.out.println("\nProcessing Booking Requests:\n");
        while (queue.hasRequests()) {
            bookingService.processBooking(queue.pollRequest());
        }

        // Display final allocated rooms and inventory
        bookingService.displayAllocatedRooms();
        inventory.displayInventory();
    }
}
