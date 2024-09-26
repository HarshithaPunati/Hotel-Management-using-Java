import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Food implements Serializable {
    int itemno;
    int quantity;
    float price;

    Food(int itemno, int quantity) {
        this.itemno = itemno;
        this.quantity = quantity;
        switch (itemno) {
            case 1:
                price = quantity * 50;
                break;
            case 2:
                price = quantity * 60;
                break;
            case 3:
                price = quantity * 70;
                break;
            case 4:
                price = quantity * 30;
                break;
        }
    }
}

class Singleroom implements Serializable {
    String name;
    String contact;
    String gender;
    ArrayList<Food> food = new ArrayList<>();

    Singleroom() {
        this.name = "";
    }

    Singleroom(String name, String contact, String gender) {
        this.name = name;
        this.contact = contact;
        this.gender = gender;
    }
}

class Doubleroom extends Singleroom implements Serializable {
    String name2;
    String contact2;
    String gender2;

    Doubleroom() {
        this.name = "";
        this.name2 = "";
    }

    Doubleroom(String name, String contact, String gender, String name2, String contact2, String gender2) {
        this.name = name;
        this.contact = contact;
        this.gender = gender;
        this.name2 = name2;
        this.contact2 = contact2;
        this.gender2 = gender2;
    }
}

class NotAvailable extends Exception {
    @Override
    public String toString() {
        return "Not Available!";
    }
}

class Holder implements Serializable {
    Doubleroom luxury_doublerrom[] = new Doubleroom[10];
    Doubleroom deluxe_doublerrom[] = new Doubleroom[20];
    Singleroom luxury_singleerrom[] = new Singleroom[10];
    Singleroom deluxe_singleerrom[] = new Singleroom[20];
}

class Hotel {
    static Holder hotel_ob = new Holder();
    static Scanner sc = new Scanner(System.in);

    static boolean isValidName(String name) {
        return Pattern.matches("[a-zA-Z]+", name);
    }

    static boolean isValidContact(String contact) {
        return Pattern.matches("\\d{10}", contact);
    }

    static void CustDetails(int i, int rn) {
        String name, contact, gender;
        String name2 = null, contact2 = null;
        String gender2 = "";

        do {
            System.out.print("\nEnter customer name: ");
            name = sc.next();
            if (!isValidName(name)) {
                System.out.println("Invalid name. Please enter alphabetic characters only.");
            }
        } while (!isValidName(name));

        do {
            System.out.print("Enter contact number (10 digits): ");
            contact = sc.next();
            if (!isValidContact(contact)) {
                System.out.println("Invalid contact number. Please enter a 10-digit number.");
            }
        } while (!isValidContact(contact));

        System.out.print("Enter gender: ");
        gender = sc.next();

        if (i < 3) {
            do {
                System.out.print("Enter second customer name: ");
                name2 = sc.next();
                if (!isValidName(name2)) {
                    System.out.println("Invalid name. Please enter alphabetic characters only.");
                }
            } while (!isValidName(name2));

            do {
                System.out.print("Enter second contact number (10 digits): ");
                contact2 = sc.next();
                if (!isValidContact(contact2)) {
                    System.out.println("Invalid contact number. Please enter a 10-digit number.");
                }
            } while (!isValidContact(contact2));

            System.out.print("Enter gender: ");
            gender2 = sc.next();
        }

        switch (i) {
            case 1:
                hotel_ob.luxury_doublerrom[rn] = new Doubleroom(name, contact, gender, name2, contact2, gender2);
                break;
            case 2:
                hotel_ob.deluxe_doublerrom[rn] = new Doubleroom(name, contact, gender, name2, contact2, gender2);
                break;
            case 3:
                hotel_ob.luxury_singleerrom[rn] = new Singleroom(name, contact, gender);
                break;
            case 4:
                hotel_ob.deluxe_singleerrom[rn] = new Singleroom(name, contact, gender);
                break;
            default:
                System.out.println("Wrong option");
                break;
        }
    }

    // New feature: Display all booked rooms
    static void displayAllBookedRooms() {
        System.out.println("\nAll booked rooms:");
        displayBookedRooms(hotel_ob.luxury_doublerrom, "Luxury Double Room");
        displayBookedRooms(hotel_ob.deluxe_doublerrom, "Deluxe Double Room");
        displayBookedRooms(hotel_ob.luxury_singleerrom, "Luxury Single Room");
        displayBookedRooms(hotel_ob.deluxe_singleerrom, "Deluxe Single Room");
    }

    static void displayBookedRooms(Singleroom[] rooms, String roomType) {
        System.out.println("\n" + roomType + ":");
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] != null) {
                System.out.println("Room " + (i + 1) + " is booked by " + rooms[i].name);
            }
        }
    }

    static void displayBookedRooms(Doubleroom[] rooms, String roomType) {
        System.out.println("\n" + roomType + ":");
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i] != null) {
                System.out.println("Room " + (i + 1) + " is booked by " + rooms[i].name + " and " + rooms[i].name2);
            }
        }
    }

    // New feature: Show current orders for a specific room
    static void showCurrentOrders(int rn, int rtype) {
        try {
            ArrayList<Food> foodOrders = getRoomOrders(rn, rtype);
            if (foodOrders != null && !foodOrders.isEmpty()) {
                System.out.println("\nCurrent Orders:");
                System.out.println("Item   Quantity    Price");
                System.out.println("-------------------------");
                String[] menuItems = {"Sandwich", "Pasta", "Noodles", "Coke"};
                for (Food order : foodOrders) {
                    System.out.printf("%-10s%-10s%-10s%n", menuItems[order.itemno - 1], order.quantity, order.price);
                }
            } else {
                System.out.println("No current orders for this room.");
            }
        } catch (Exception e) {
            System.out.println("Error displaying orders.");
        }
    }

    // Helper function to fetch orders from a room
    static ArrayList<Food> getRoomOrders(int rn, int rtype) {
        switch (rtype) {
            case 1:
                return hotel_ob.luxury_doublerrom[rn].food;
            case 2:
                return hotel_ob.deluxe_doublerrom[rn].food;
            case 3:
                return hotel_ob.luxury_singleerrom[rn].food;
            case 4:
                return hotel_ob.deluxe_singleerrom[rn].food;
            default:
                return null;
        }
    }

    // New feature: Modify existing orders
    static void modifyOrders(int rn, int rtype) {
        try {
            ArrayList<Food> foodOrders = getRoomOrders(rn, rtype);
            if (foodOrders != null && !foodOrders.isEmpty()) {
                showCurrentOrders(rn, rtype);
                System.out.print("Enter the order item number to modify: ");
                int itemNo = sc.nextInt();
                System.out.print("Enter the new quantity: ");
                int quantity = sc.nextInt();
                foodOrders.get(itemNo - 1).quantity = quantity;
                foodOrders.get(itemNo - 1).price = quantity * getPricePerItem(itemNo);
                System.out.println("Order modified successfully.");
            } else {
                System.out.println("No orders found for this room.");
            }
        } catch (Exception e) {
            System.out.println("Error modifying orders.");
        }
    }

    static float getPricePerItem(int itemNo) {
        switch (itemNo) {
            case 1:
                return 50;
            case 2:
                return 60;
            case 3:
                return 70;
            case 4:
                return 30;
            default:
                return 0;
        }
    }
}
