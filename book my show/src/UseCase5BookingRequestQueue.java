/**
 * Book My Stay App
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates booking request intake using a Queue to preserve request order.
 *
 * @author Vishal
 * @version 5.1
 */

import java.util.LinkedList;
import java.util.Queue;

// Reservation class represents a guest's booking intent
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Requested Room: " + roomType;
    }
}

// Booking queue to handle requests in arrival order
class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add reservation request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation); // FIFO
        System.out.println("Booking request added: " + reservation);
    }

    // Peek next request (without removing)
    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }

    // Process next request (removes from queue)
    public Reservation processNextRequest() {
        return requestQueue.poll();
    }

    // Display all queued requests
    public void displayAllRequests() {
        System.out.println("\nCurrent Booking Queue:");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        for (Reservation r : requestQueue) {
            System.out.println(r);
        }
    }
}

// Main class
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App (v5.1) ======");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulate incoming booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double Room"));

        // Display queued requests
        bookingQueue.displayAllRequests();

        // Peek next request
        System.out.println("\nNext request to process: " + bookingQueue.peekNextRequest());

        // Process next request (FIFO)
        System.out.println("\nProcessing request: " + bookingQueue.processNextRequest());

        // Display queue after processing one request
        bookingQueue.displayAllRequests();
    }
}