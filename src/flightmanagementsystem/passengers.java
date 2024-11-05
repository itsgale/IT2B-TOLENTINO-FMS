package flightmanagementsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class passengers {
    
 public void pManagement(){
        
                try (Scanner sc = new Scanner(System.in)) {
            int choice;
            String response;
            do {
                System.out.println("\n--- PASSENGERS MENU ---");
                System.out.println("1. ADD PASSENGERS");
                System.out.println("2. VIEW PASSENGERS");
                System.out.println("3. UPDATE PASSENGERS");
                System.out.println("4. DELETE PASSENGERS");
                System.out.println("5. EXIT");

                choice = getIntInput(sc, "Enter your choice: ");

                passengers ps = new passengers();

                switch (choice) {
                    case 1:
                        ps.addPassengers(sc);
                        break;
                    case 2:
                        ps.viewPassengers();
                        break;
                    case 3:
                        ps.viewPassengers();
                        ps.updatePassengers(sc);
                        ps.viewPassengers();
                        break;
                    case 4:
                        ps.viewPassengers();
                        ps.deletePassengers(sc);
                        ps.viewPassengers();
                        break;
                    case 5:
                        
                        break;
                        
                     }
                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.next();
            } while (response.equalsIgnoreCase("yes"));
            
        }
 }

    public void addPassengers(Scanner sc) {
        config conf = new config();

        System.out.print("Passengers first name: ");
        String fname = sc.nextLine();
        System.out.print("Passengers last name: ");
        String lname = sc.nextLine();
        System.out.print("Passengers email: ");
        String email = sc.nextLine();
        System.out.print("Passengers status: ");
        String status = sc.nextLine();

        String sql = "INSERT INTO tbl_passengers (p_fname, p_lname, p_email, p_status) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname, email, status);
    }

    public void viewPassengers() {
        config conf = new config();
        String sqlQuery = "SELECT * FROM tbl_passengers";
        String[] columnHeaders = {"ID", "FIRST NAME", "LAST NAME", "EMAIL", "STATUS"};
        String[] columnNames = {"p_id", "p_fname", "p_lname", "p_email", "p_status"};
        conf.viewRecord(sqlQuery, columnHeaders, columnNames);
    }

    public void updatePassengers(Scanner sc) {
        config conf = new config();

        int id = getIntInput(sc, "Enter the ID of passenger to edit: ");
        sc.nextLine();

        System.out.print("Passengers new first name: ");
        String fname = sc.nextLine();
        System.out.print("Passengers new last name: ");
        String lname = sc.nextLine();
        System.out.print("Passengers new email: ");
        String email = sc.nextLine();
        System.out.print("Passengers new status: ");
        String status = sc.nextLine();

        String sql = "UPDATE tbl_passengers SET p_fname = ?, p_lname = ?, p_email = ?, p_status = ? WHERE p_id = ?";
        conf.updateRecord(sql, fname, lname, email, status, id);
        System.out.println("Passenger updated successfully.");
    }

    public void deletePassengers(Scanner sc) {
        config conf = new config();
        int id = getIntInput(sc, "Enter the ID of passenger to delete: ");

        String sql = "DELETE FROM tbl_passengers WHERE p_id = ?";
        conf.deleteRecord(sql, id);
        System.out.println("Passenger deleted successfully.");
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