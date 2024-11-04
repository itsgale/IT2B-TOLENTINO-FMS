package flightmanagementsystem;

import java.util.Scanner;

public class bookings {
    
    public void bManagement(){
        
                   try (Scanner sc = new Scanner(System.in)) {
            int choice;
            String response;
            do {
                 System.out.println("\n--- BOOKINGS MENU ---");
                System.out.println("1. ADD BOOKINGS");
                System.out.println("2. VIEW BOOKINGS");
                System.out.println("3. UPDATE BOOKINGS");
                System.out.println("4. DELETE BOOKINGS");
                System.out.println("5. EXIT");


                choice = getIntInput(sc, "Enter your choice: ");

                 bookings bs = new bookings();

                switch (choice) {
                    case 1:
                        bookings.addBookings(sc);
                        break;
                    case 2:
                        bookings.viewBookings();
                        break;
                    case 3:
                        bookings.viewBookings();
                        bookings.updateBookings(sc);
                        bookings.viewBookings();
                        break;
                    case 4:
                        bookings.viewBookings();
                        bookings.deleteBookings(sc);
                        bookings.viewBookings();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.next();
            } while (response.equalsIgnoreCase("yes"));
            System.out.println("Thank you, See you soonest!");
        }
                   
    }
}