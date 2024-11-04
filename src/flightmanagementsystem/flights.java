package flightmanagementsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class flights {
    
    public void fManagement(){
        
                try (Scanner sc = new Scanner(System.in)) {
            int choice;
            String response;
            do {
                System.out.println("\n--- FLIGHTS MENU ---");
                System.out.println("1. ADD FLIGHTS");
                System.out.println("2. VIEW FLIGHTS");
                System.out.println("3. UPDATE FLIGHTS");
                System.out.println("4. DELETE FLIGHTS");
                System.out.println("5. EXIT");

                choice = getIntInput(sc, "Enter your choice: ");

                flights fs = new flights();

                switch (choice) {
                    case 1:
                        fs.addFlights(sc);
                        break;
                    case 2:
                        fs.viewFlights();
                        break;
                    case 3:
                        fs.viewFlights();
                        fs.updateFlights(sc);
                        fs.viewFlights();
                        break;
                    case 4:
                        fs.viewFlights();
                        fs.deleteFlights(sc);
                        fs.viewFlights();
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

    public void addFlights(Scanner sc) {
        config conf = new config();

        System.out.print("Enter Departure: ");
        String fdeparture = sc.nextLine();
        System.out.print("Enter Destination: ");
        String fdestination = sc.nextLine();
        System.out.print("Enter Date: ");
        String fdate = sc.nextLine();
        System.out.print("Enter Time: ");
        String ftime = sc.nextLine();

        String sql = "INSERT INTO tbl_flights (f_departure, f_destination, f_date, f_time) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fdeparture, fdestination, fdate, ftime);
    }

    public void viewFlights() {
        config conf = new config();
        String sqlQuery = "SELECT * FROM tbl_Flights";
        String[] columnHeaders = {"ID", "DEPARTURE", "DESTINATION", "DATE", "TIME"};
        String[] columnNames = {"f_id", "f_departure", "f_destination", "f_date", "f_time"};
        conf.viewRecord(sqlQuery, columnHeaders, columnNames);
    }

    public void updateFlights(Scanner sc) {
        config conf = new config();

        int id = getIntInput(sc, "Enter the ID of flight to edit: ");
        sc.nextLine();

        System.out.print("Enter new departure: ");
        String fdeparture = sc.nextLine();
        System.out.print("Enter new destination: ");
        String fdestination = sc.nextLine();
        System.out.print("Enter new date: ");
        String fdate = sc.nextLine();
        System.out.print("Enter new time: ");
        String ftime = sc.nextLine();

        String sql = "UPDATE tbl_flights SET f_departure = ?, f_destination = ?, f_date = ?, f_time = ? WHERE f_id = ?";
        conf.updateRecord(sql, fdeparture, fdestination, fdate, ftime, id);
        System.out.println("Flight updated successfully.");
    }

    public void deleteFlights(Scanner sc) {
        config conf = new config();
        int id = getIntInput(sc, "Enter the ID of flight to delete: ");

        String sql = "DELETE FROM tbl_flights WHERE f_id = ?";
        conf.deleteRecord(sql, id);
        System.out.println("Flights deleted successfully.");
    }

    public static int getIntInput(Scanner sc, String prompt) {
        int input = -1;
        while (true) {
            System.out.print(prompt);
            try {
                input = sc.nextInt();
                sc.nextLine(); 
                break; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); 
            }
        }
        return input;
    }
}