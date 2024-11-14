package flightmanagementsystem;

import java.util.Scanner;

public class flights {

    public void fManagement() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("--------------------------------------");
            System.out.println("|                                    |");
            System.out.println("|        WELCOME TO FLIGHTS          |");
            System.out.println("|               MENU                 |");
            System.out.println("|                                    |");
            System.out.println("--------------------------------------");
            System.out.println("| 1. ADD FLIGHTS                     |");
            System.out.println("| 2. VIEW FLIGHTS                    |");
            System.out.println("| 3. UPDATE FLIGHTS                  |");
            System.out.println("| 4. DELETE FLIGHTS                  |");
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

            flights fs = new flights();

            switch (choice) {
                case 1:
                    fs.addFlights();
                    fs.viewFlights();
                    break;
                case 2:
                    fs.viewFlights();
                    break;
                case 3:
                    fs.viewFlights();
                    fs.updateFlights();
                    fs.viewFlights();
                    break;
                case 4:
                    fs.viewFlights();
                    fs.deleteFlights();
                    fs.viewFlights();
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

    public void addFlights() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter flight number: ");
        String fnumber = sc.nextLine().trim();
        System.out.print("Enter departure location: ");
        String fdeparture = sc.nextLine().trim();
        System.out.print("Enter arrival location: ");
        String farrival = sc.nextLine().trim();
        
        String fdtime = "";
        boolean validDTime = false;
        while (!validDTime) {
            System.out.print("Enter departure time (HH:MM AM/PM): ");
            fdtime = sc.nextLine().trim();
            if (fdtime.matches("^(0[1-9]|1[0-2]):[0-5][0-9] [APap][Mm]$")) {
                validDTime = true;
            } else {
                System.out.println("Invalid time format, Please enter time in the format HH:MM AM/PM.");
            }
        }

        String fatime = "";
        boolean validATime = false;
        while (!validATime) {
            System.out.print("Enter arrival time (HH:MM AM/PM): ");
            fatime = sc.nextLine().trim();
            if (fatime.matches("^(0[1-9]|1[0-2]):[0-5][0-9] [APap][Mm]$")) {
                validATime = true;
            } else {
                System.out.println("Invalid time format, Please enter time in the format HH:MM AM/PM.");
            }
        }

        System.out.print("Enter aircraft type: ");
        String faircraft = sc.nextLine().trim();

        String fstatus = "";
        boolean validStatus = false;
        while (!validStatus) {
            System.out.print("Enter flight status (scheduled/cancelled): ");
            fstatus = sc.nextLine().trim().toLowerCase();
            if (fstatus.equals("scheduled") || fstatus.equals("cancelled")) {
                validStatus = true;
            } else {
                System.out.println("Invalid status, Please enter either 'scheduled' or 'cancelled'.");
            }
        }

        if (fnumber.isEmpty() || fdeparture.isEmpty() || farrival.isEmpty() || fdtime.isEmpty() || fatime.isEmpty() || faircraft.isEmpty() || fstatus.isEmpty()) {
            System.out.println("All fields are required, Flight not added.");
            return;
        }

        String sql = "INSERT INTO tbl_flights (f_number, f_departure, f_arrival, f_departuretime, f_arrivaltime, f_aircraft, f_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        config conf = new config();
        conf.addRecord(sql, fnumber, fdeparture, farrival, fdtime, fatime, faircraft, fstatus);
        System.out.println("Flight added successfully.");
    }

    public void viewFlights() {
        String sqlQuery = "SELECT * FROM tbl_flights";
        String[] columnHeaders = {"FLIGHTS ID", "FLIGHTS NUMBER", "DEPARTURES", "ARRIVALS", "DEPARTURES TIME", "ARRIVALS TIME", "AIRCRAFTS TYPE", "FLIGHTS STATUS"};
        String[] columnNames = {"f_id", "f_number", "f_departure", "f_arrival", "f_departuretime", "f_arrivaltime", "f_aircraft", "f_status"};
        config conf = new config();
        conf.viewRecord(sqlQuery, columnHeaders, columnNames);
    }

    public void updateFlights() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = 0;
        boolean validId = false;

        while (!validId) {
            System.out.print("Enter ID of the flight to update: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine(); 
                if (conf.getSingleValue("SELECT f_id FROM tbl_flights WHERE f_id = ?", id) != 0) {
                    validId = true;
                } else {
                    System.out.println("Selected ID doesn't exist, Please enter a valid flight ID.");
                }
            } else {
                System.out.println("Invalid input, Please enter a valid flight ID.");
                sc.next(); 
            }
        }

            String currentStatus = conf.getSingleStringValue("SELECT f_status FROM tbl_flights WHERE f_id = ?", id);

           if (currentStatus != null && currentStatus.toLowerCase().equals("scheduled")) {
            System.out.println("This flight is scheduled and cannot be updated.");
            return;
}

        System.out.print("Enter new flight number: ");
        String fnumber = sc.nextLine().trim();
        System.out.print("Enter new departure location: ");
        String fdeparture = sc.nextLine().trim();
        System.out.print("Enter new arrival location: ");
        String farrival = sc.nextLine().trim();

        String fdtime = "";
        boolean validDTime = false;
        while (!validDTime) {
            System.out.print("Enter new departure time (HH:MM AM/PM): ");
            fdtime = sc.nextLine().trim();
            if (fdtime.matches("^(0[1-9]|1[0-2]):[0-5][0-9] [APap][Mm]$")) {
                validDTime = true;
            } else {
                System.out.println("Invalid time format, Please enter time in the format HH:MM AM/PM.");
            }
        }

        String fatime = "";
        boolean validATime = false;
        while (!validATime) {
            System.out.print("Enter new arrival time (HH:MM AM/PM): ");
            fatime = sc.nextLine().trim();
            if (fatime.matches("^(0[1-9]|1[0-2]):[0-5][0-9] [APap][Mm]$")) {
                validATime = true;
            } else {
                System.out.println("Invalid time format, Please enter time in the format HH:MM AM/PM.");
            }
        }

        System.out.print("Enter new aircraft type: ");
        String faircraft = sc.nextLine().trim();

        String fstatus = "";
        boolean validStatus = false;
        while (!validStatus) {
            System.out.print("Enter new flight status (scheduled/cancelled): ");
            fstatus = sc.nextLine().trim().toLowerCase();
            if (fstatus.equals("scheduled") || fstatus.equals("cancelled")) {
                validStatus = true;
            } else {
                System.out.println("Invalid status, Please enter either 'scheduled' or 'cancelled'.");
            }
        }

        String sql = "UPDATE tbl_flights SET f_number = ?, f_departure = ?, f_arrival = ?, f_departuretime = ?, f_arrivaltime = ?, f_aircraft = ?, f_status = ? WHERE f_id = ?";
        conf.updateRecord(sql, fnumber, fdeparture, farrival, fdtime, fatime, faircraft, fstatus, id);
        System.out.println("Flight updated successfully.");
    }

    public void deleteFlights() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = 0;
        boolean validId = false;

        while (!validId) {
            System.out.print("Enter ID of the flight to delete: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine(); 
                if (conf.getSingleValue("SELECT f_id FROM tbl_flights WHERE f_id = ?", id) != 0) {
                    validId = true;
                } else {
                    System.out.println("Selected ID doesn't exist, Please enter a valid flight ID.");
                }
            } else {
                System.out.println("Invalid input, Please enter a valid flight ID.");
                sc.next(); 
            }
        }

        String sql = "DELETE FROM tbl_flights WHERE f_id = ?";
        conf.deleteRecord(sql, id);
        System.out.println("Flight deleted successfully.");
    }
}