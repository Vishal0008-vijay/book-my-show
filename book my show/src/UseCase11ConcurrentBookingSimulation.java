import java.util.*;

// Booking Request class
class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking System
class BookingSystem {

    // Shared inventory
    private Map<String, Integer> inventory = new HashMap<>();

    // Shared queue
    private Queue<BookingRequest> requestQueue = new LinkedList<>();

    BookingSystem() {
        inventory.put("Single", 2);
        inventory.put("Double", 1);
    }

    // Add request to queue (synchronized)
    public synchronized void addRequest(BookingRequest request) {
        requestQueue.add(request);
        System.out.println(request.guestName + " requested " + request.roomType);
    }

    // Process booking (critical section)
    public synchronized void processBooking() {

        if (requestQueue.isEmpty()) {
            return;
        }

        BookingRequest request = requestQueue.poll();

        String roomType = request.roomType;

        // Critical section: inventory check + update
        if (inventory.getOrDefault(roomType, 0) > 0) {
            System.out.println(Thread.currentThread().getName() +
                    " allocated " + roomType + " to " + request.guestName);

            inventory.put(roomType, inventory.get(roomType) - 1);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED for " + request.guestName + " (No rooms)");
        }
    }

    public void showInventory() {
        System.out.println("Final Inventory: " + inventory);
    }
}

// Worker Thread
class BookingProcessor extends Thread {

    BookingSystem system;

    BookingProcessor(BookingSystem system, String name) {
        super(name);
        this.system = system;
    }

    public void run() {
        // Each thread processes multiple requests
        for (int i = 0; i < 3; i++) {
            system.processBooking();

            try {
                Thread.sleep(100); // simulate delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Main Class
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        BookingSystem system = new BookingSystem();

        // Simulate multiple guests
        system.addRequest(new BookingRequest("Vishal", "Single"));
        system.addRequest(new BookingRequest("Arun", "Single"));
        system.addRequest(new BookingRequest("David", "Single"));
        system.addRequest(new BookingRequest("John", "Double"));
        system.addRequest(new BookingRequest("Rahul", "Double"));

        // Create multiple threads
        BookingProcessor t1 = new BookingProcessor(system, "Thread-1");
        BookingProcessor t2 = new BookingProcessor(system, "Thread-2");

        // Start threads
        t1.start();
        t2.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final state
        system.showInventory();
    }
}