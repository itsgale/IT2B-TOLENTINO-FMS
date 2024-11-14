package flightmanagementsystem;

import java.util.Scanner;

public class FlightManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = true;

        do {
            System.out.println("--------------------------------------");
            System.out.println("|                                    |");
            System.out.println("|        WELCOME TO FLIGHT           |");
            System.out.println("|           MANAGEMENT SYSTEM        |");
            System.out.println("|                                    |");
            System.out.println("--------------------------------------");
            System.out.println("| 1. FLIGHTS                         |");
            System.out.println("| 2. PASSENGERS                      |");
            System.out.println("| 3. BOOKINGS                        |");
            System.out.println("| 4. REPORTS                         |");
            System.out.println("| 5. EXIT                            |");
            System.out.println("--------------------------------------");

            int choice = 0;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Enter your choice (1-5): ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice >= 1 && choice <= 5) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid choice, Please select a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input, Please enter a valid number.");
                    sc.next(); 
                }
            }

            switch (choice) {
                case 1:
                    flights fs = new flights();
                    fs.fManagement();
                    break;

                case 2:
                    passengers ps = new passengers();
                    ps.pManagement();
                    break;

                case 3:
                    bookings bs = new bookings();
                    bs.bManagement();
                    break;

                case 4:
                    reports rs = new reports();
                    rs.rManagement();
                    break;

                case 5:
                    sc.nextLine(); 
                    boolean validResponse = false;
                    while (!validResponse) {
                        System.out.print("Exiting selection... type 'yes' to exit or 'no' to continue: ");
                        String resp = sc.nextLine().trim();
                        if (resp.equalsIgnoreCase("yes")) {
                            exit = false;
                            validResponse = true;
                        } else if (resp.equalsIgnoreCase("no")) {
                            validResponse = true;
                        } else {
                            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
                        }
                    }
                    break;
            }

            System.out.println("Thank you!");
        } while (exit);
        
    }
}