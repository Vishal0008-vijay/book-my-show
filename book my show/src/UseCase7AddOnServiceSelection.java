/**
 * Book My Stay App
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates attaching optional services to confirmed reservations
 * while keeping core booking and inventory intact.
 *
 * @author Vishal
 * @version 7.1
 */

import java.util.*;

// Add-On Service Class
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return serviceName + " (₹" + cost + ")";
    }
}

// Add-On Service Manager
class AddOnServiceManager {
    // Map reservationID → list of selected services
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Attach service to a reservation
    public void addService(String reservationId, Service service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
        System.out.println("Added service to " + reservationId + ": " + service);
    }

    // Calculate total add-on cost for a reservation
    public double calculateTotalCost(String reservationId) {
        List<Service> services = reservationServices.getOrDefault(reservationId, Collections.emptyList());
        double total = 0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }

    // Display all services per reservation
    public void displayAllServices() {
        System.out.println("\nAdd-On Services for Reservations:");
        for (Map.Entry<String, List<Service>> entry : reservationServices.entrySet()) {
            System.out.println("Reservation ID: " + entry.getKey() + " → " + entry.getValue());
        }
    }
}

// Main class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("====== Book My Stay App (v7.1) ======");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Simulated reservation IDs (from Use Case 6 allocation)
        String resAlice = "R101";
        String resBob = "R102";
        String resCharlie = "R103";

        // Define some add-on services
        Service breakfast = new Service("Breakfast", 250);
        Service spa = new Service("Spa Session", 1000);
        Service airportPickup = new Service("Airport Pickup", 500);

        // Attach services to reservations
        serviceManager.addService(resAlice, breakfast);
        serviceManager.addService(resAlice, airportPickup);
        serviceManager.addService(resBob, spa);
        serviceManager.addService(resCharlie, breakfast);
        serviceManager.addService(resCharlie, spa);

        // Display total add-on cost per reservation
        System.out.println("\nTotal Add-On Costs:");
        System.out.println(resAlice + ": ₹" + serviceManager.calculateTotalCost(resAlice));
        System.out.println(resBob + ": ₹" + serviceManager.calculateTotalCost(resBob));
        System.out.println(resCharlie + ": ₹" + serviceManager.calculateTotalCost(resCharlie));

        // Display all services mapping
        serviceManager.displayAllServices();
    }
}