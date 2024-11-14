package flightmanagementsystem;

import java.util.Scanner;

public class passengers {

    public void pManagement() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("--------------------------------------");
            System.out.println("|                                    |");
            System.out.println("|        WELCOME TO PASSENGERS       |");
            System.out.println("|               MENU                 |");
            System.out.println("|                                    |");
            System.out.println("--------------------------------------");
            System.out.println("| 1. ADD PASSENGERS                  |");
            System.out.println("| 2. VIEW PASSENGERS                 |");
            System.out.println("| 3. UPDATE PASSENGERS               |");
            System.out.println("| 4. DELETE PASSENGERS               |");
            System.out.println("| 5. EXIT                            |");
            System.out.println("--------------------------------------");

            int choice = 0;
            boolean validChoice = false;

            while (!validChoice) {
                System.out.print("Enter your choice (1-5): ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice >= 1 && choice <= 5) {
                        validChoice = true;
                    } else {
                        System.out.println("Invalid choice, Please select a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input, Please enter a valid number.");
                    sc.next();
                }
            }

            passengers ps = new passengers();

            switch (choice) {
                case 1:
                    ps.addPassengers();
                    ps.viewPassengers();
                    break;
                case 2:
                    ps.viewPassengers();
                    break;
                case 3:
                    ps.viewPassengers();
                    ps.updatePassengers();
                    ps.viewPassengers();
                    break;
                case 4:
                    ps.viewPassengers();
                    ps.deletePassengers();
                    ps.viewPassengers();
                    break;
                case 5:
                    
                    break;
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next().toLowerCase();

            while (!response.equals("yes") && !response.equals("no")) {
                System.out.println("Invalid input, please enter 'yes' or 'no'.");
                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.next().toLowerCase();
            }

        } while (response.equals("yes"));
    }

    public void addPassengers() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter passport number: ");
        String pnumber = sc.nextLine().trim();
        System.out.print("Enter first name: ");
        String pfname = sc.nextLine().trim();
        System.out.print("Enter last name: ");
        String plname = sc.nextLine().trim();

        String pgender = "";
        boolean validGender = false;
        while (!validGender) {
            System.out.print("Enter gender (male/female): ");
            pgender = sc.nextLine().trim().toLowerCase();
            if (pgender.equals("male") || pgender.equals("female")) {
                validGender = true;
            } else {
                System.out.println("Invalid gender, Please enter 'male' or 'female'.");
            }
        }

        String page;
        int age = 0;
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter age: ");
            page = sc.nextLine().trim();
            try {
                age = Integer.parseInt(page);
                if (age > 0) {
                    validAge = true;
                } else {
                    System.out.println("Age must be a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter a valid number for age.");
            }
        }

        System.out.print("Enter address: ");
        String paddress = sc.nextLine().trim();

        String pemail = "";
        boolean validEmail = false;
        while (!validEmail) {
            System.out.print("Enter email (e.g., test@gmail.com): ");
            pemail = sc.nextLine().trim();
            if (pemail.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,3}$")) {
                validEmail = true;
            } else {
                System.out.println("Invalid email format, Please enter a valid email address.");
            }
        }

        if (pnumber.isEmpty() || pfname.isEmpty() || plname.isEmpty() || pgender.isEmpty() || paddress.isEmpty() || pemail.isEmpty()) {
            System.out.println("All fields are required, Passenger not added.");
            return;
        }

        String sql = "INSERT INTO tbl_passengers (p_pnumber, p_fname, p_lname, p_gender, p_age, p_address, p_email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        config conf = new config();
        conf.addRecord(sql, pnumber, pfname, plname, pgender, age, paddress, pemail);
        System.out.println("Passenger added successfully.");
    }

    public void viewPassengers() {
        String sqlQuery = "SELECT * FROM tbl_passengers";
        String[] columnHeaders = {"PASSENGER ID", "PASSPORT NUMBER", "FIRST NAME", "LAST NAME", "GENDER", "AGE", "ADDRESS", "EMAIL"};
        String[] columnNames = {"p_id", "p_pnumber", "p_fname", "p_lname", "p_gender", "p_age", "p_address", "p_email"};
        config conf = new config();
        conf.viewRecord(sqlQuery, columnHeaders, columnNames);
    }

    public void updatePassengers() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = 0;
        boolean validId = false;

        while (!validId) {
            System.out.print("Enter ID of the passenger to update: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine();
                if (conf.getSingleValue("SELECT p_id FROM tbl_passengers WHERE p_id = ?", id) != 0) {
                    validId = true;
                } else {
                    System.out.println("Selected ID doesn't exist, Please enter a valid passenger ID.");
                }
            } else {
                System.out.println("Invalid input, Please enter a valid passenger ID.");
                sc.next();
            }
        }

        System.out.print("Enter new passport number: ");
        String pnumber = sc.nextLine().trim();
        System.out.print("Enter new first name: ");
        String pfname = sc.nextLine().trim();
        System.out.print("Enter new last name: ");
        String plname = sc.nextLine().trim();

        String pgender = "";
        boolean validGender = false;
        while (!validGender) {
            System.out.print("Enter new gender (male/female): ");
            pgender = sc.nextLine().trim().toLowerCase();
            if (pgender.equals("male") || pgender.equals("female")) {
                validGender = true;
            } else {
                System.out.println("Invalid gender, Please enter 'male' or 'female'.");
            }
        }

        String page;
        int age = 0;
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter new age: ");
            page = sc.nextLine().trim();
            try {
                age = Integer.parseInt(page);
                if (age > 0) {
                    validAge = true;
                } else {
                    System.out.println("Age must be a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Please enter a valid number for age.");
            }
        }

        System.out.print("Enter new address: ");
        String paddress = sc.nextLine().trim();

        String pemail = "";
        boolean validEmail = false;
        while (!validEmail) {
            System.out.print("Enter new email (e.g., test@gmail.com): ");
            pemail = sc.nextLine().trim();
            if (pemail.matches("^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,3}$")) {
                validEmail = true;
            } else {
                System.out.println("Invalid email format, Please enter a valid email address.");
            }
        }

        String sql = "UPDATE tbl_passengers SET p_pnumber = ?, p_fname = ?, p_lname = ?, p_gender = ?, p_age = ?, p_address = ?, p_email = ? WHERE p_id = ?";
        conf.updateRecord(sql, pnumber, pfname, plname, pgender, age, paddress, pemail, id);
        System.out.println("Passenger updated successfully.");
    }

    public void deletePassengers() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = 0;
        boolean validId = false;

        while (!validId) {
            System.out.print("Enter ID of the passenger to delete: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine();
                if (conf.getSingleValue("SELECT p_id FROM tbl_passengers WHERE p_id = ?", id) != 0) {
                    validId = true;
                } else {
                    System.out.println("Selected ID doesn't exist, Please enter a valid passenger ID.");
                }
            } else {
                System.out.println("Invalid input, Please enter a valid passenger ID.");
                sc.next();
            }
        }

        String sql = "DELETE FROM tbl_passengers WHERE p_id = ?";
        conf.deleteRecord(sql, id);
        System.out.println("Passenger deleted successfully.");
    }
}