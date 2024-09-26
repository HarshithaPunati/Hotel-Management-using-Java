import java.io.*;
import java.util.*;

class HotelManagementSystem {
    static Scanner sc = new Scanner(System.in);
    static Hotel hotel = new Hotel();  // Instance of Hotel class to manage bookings

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Welcome to the Hotel Management System ---");
            System.out.println("1. Book a Room");
            System.out.println("2. Order Food");
            System.out.println("3. Display All Bookings");
            System.out.println("4. Display Current Orders");
            System.out.println("5. Modify Orders");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    bookRoom();
                    break;
                case 2:
                    orderFood();
                    break;
                case 3:
                    displayAllBookings();
                    break;
                case 4:
                    displayCurrentOrders();
                    break;
                case 5:
                    modifyOrders();
                    break;
                case 6:
                    System.out.println("Thank you for using the Hotel Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    // Method to book a room
    private static void bookRoom() {
        System.out.println("Select Room Type to Book:");
        System.out.println("1. Luxury Double Room");
        System.out.println("2. Deluxe Double Room");
        System.out.println("3. Luxury Single Room");
        System.out.println("4. Deluxe Single Room");
        int roomType = sc.nextInt();
        
        sc.nextLine();  // consume leftover newline
        System.out.print("Enter customer name: ");
        String name = sc.nextLine();
        if (!name.matches("[a-zA-Z\\s]+")) {
            System.out.println("Invalid name format. Please enter alphabetic characters only.");
            return;
        }

        System.out.print("Enter contact number (10 digits): ");
        String contact = sc.nextLine();
        if (!contact.matches("\\d{10}")) {
            System.out.println("Invalid contact number. Please enter a 10-digit number.");
            return;
        }

        System.out.print("Enter gender (Male/Female): ");
        String gender = sc.nextLine();

        if (hotel.bookRoom(roomType, name, contact, gender)) {
            System.out.println("Room booked successfully!");
        } else {
            System.out.println("Booking failed. No available rooms of the selected type.");
        }
    }

    // Method to order food
    private static void orderFood() {
        System.out.print("Enter your room number: ");
        int roomNumber = sc.nextInt();

        System.out.println("Available Food Items:");
        System.out.println("1. Sandwich - ₹100");
        System.out.println("2. Pasta - ₹150");
        System.out.println("3. Noodles - ₹120");
        System.out.println("4. Coke - ₹50");

        System.out.print("Enter the item number you want to order: ");
        int foodItem = sc.nextInt();
        System.out.print("Enter the quantity: ");
        int quantity = sc.nextInt();

        if (hotel.orderFood(roomNumber, foodItem, quantity)) {
            System.out.println("Food order placed successfully!");
        } else {
            System.out.println("Failed to place order. Please check your room number.");
        }
    }

    // Method to display all booked rooms
    private static void displayAllBookings() {
        hotel.displayBookings();
    }

    // Method to display current orders of a room
    private static void displayCurrentOrders() {
        System.out.print("Enter your room number: ");
        int roomNumber = sc.nextInt();
        hotel.displayOrders(roomNumber);
    }

    // Method to modify food orders
    private static void modifyOrders() {
        System.out.print("Enter your room number: ");
        int roomNumber = sc.nextInt();

        System.out.println("Available Food Items for Modification:");
        System.out.println("1. Sandwich");
        System.out.println("2. Pasta");
        System.out.println("3. Noodles");
        System.out.println("4. Coke");

        System.out.print("Enter the item number you want to modify: ");
        int foodItem = sc.nextInt();
        System.out.print("Enter the new quantity: ");
        int quantity = sc.nextInt();

        if (hotel.modifyOrder(roomNumber, foodItem, quantity)) {
            System.out.println("Order modified successfully!");
        } else {
            System.out.println("Failed to modify order. Please check your room number.");
        }
    }
}

// Sample Hotel class handling the core logic (basic structure for demo purposes)
class Hotel {
    ArrayList<Room> rooms = new ArrayList<>();  // List of all rooms

    public Hotel() {
        // Initialize rooms - 5 of each type
        for (int i = 0; i < 5; i++) {
            rooms.add(new Room("Luxury Double Room"));
            rooms.add(new Room("Deluxe Double Room"));
            rooms.add(new Room("Luxury Single Room"));
            rooms.add(new Room("Deluxe Single Room"));
        }
    }

    // Method to book a room
    public boolean bookRoom(int roomType, String name, String contact, String gender) {
        for (Room room : rooms) {
            if (room.type.equals(getRoomType(roomType)) && !room.isBooked) {
                room.book(name, contact, gender);
                return true;
            }
        }
        return false;
    }

    // Method to place a food order
    public boolean orderFood(int roomNumber, int foodItem, int quantity) {
        if (roomNumber > 0 && roomNumber <= rooms.size() && rooms.get(roomNumber - 1).isBooked) {
            rooms.get(roomNumber - 1).placeOrder(foodItem, quantity);
            return true;
        }
        return false;
    }

    // Display all booked rooms
    public void displayBookings() {
        for (Room room : rooms) {
            if (room.isBooked) {
                room.displayDetails();
            }
        }
    }

    // Display orders of a room
    public void displayOrders(int roomNumber) {
        if (roomNumber > 0 && roomNumber <= rooms.size() && rooms.get(roomNumber - 1).isBooked) {
            rooms.get(roomNumber - 1).displayOrders();
        } else {
            System.out.println("Invalid room number or room is not booked.");
        }
    }

    // Modify an order
    public boolean modifyOrder(int roomNumber, int foodItem, int quantity) {
        if (roomNumber > 0 && roomNumber <= rooms.size() && rooms.get(roomNumber - 1).isBooked) {
            rooms.get(roomNumber - 1).modifyOrder(foodItem, quantity);
            return true;
        }
        return false;
    }

    private String getRoomType(int choice) {
        switch (choice) {
            case 1: return "Luxury Double Room";
            case 2: return "Deluxe Double Room";
            case 3: return "Luxury Single Room";
            case 4: return "Deluxe Single Room";
            default: return "";
        }
    }
}

// Room class
class Room {
    String type;
    boolean isBooked = false;
    String customerName;
    String contactNumber;
    String gender;
    HashMap<Integer, Integer> foodOrders = new HashMap<>(); // foodItem -> quantity

    public Room(String type) {
        this.type = type;
    }

    public void book(String name, String contact, String gender) {
        this.isBooked = true;
        this.customerName = name;
        this.contactNumber = contact;
        this.gender = gender;
    }

    public void placeOrder(int foodItem, int quantity) {
        foodOrders.put(foodItem, foodOrders.getOrDefault(foodItem, 0) + quantity);
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Customer: " + customerName);
        System.out.println("Contact: " + contactNumber);
        System.out.println("Gender: " + gender);
    }

    public void displayOrders() {
        System.out.println("Current Food Orders for Room:");
        for (Map.Entry<Integer, Integer> order : foodOrders.entrySet()) {
            System.out.println("Item " + order.getKey() + " -> Quantity: " + order.getValue());
        }
    }

    public void modifyOrder(int foodItem, int quantity) {
        if (foodOrders.containsKey(foodItem)) {
            foodOrders.put(foodItem, quantity);
        } else {
            System.out.println("No existing order for this item.");
        }
    }
}
