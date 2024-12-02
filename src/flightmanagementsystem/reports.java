package flightmanagementsystem;

import java.sql.*;
import java.util.Scanner;

public class reports {
    Scanner input = new Scanner(System.in);
    Config conf = new Config();
    Flights flights = new Flights();
    Passengers passengers = new Passengers();
    Bookings bookings = new Bookings();

    public void rManagement() {
        boolean exit = true;
        do {
            System.out.println("--------------------------------------");
            System.out.println("|                                    |");
            System.out.println("|        WELCOME TO REPORTS          |");
            System.out.println("|               MENU                 |");
            System.out.println("|                                    |");
            System.out.println("--------------------------------------");
            System.out.println("| 1. VIEW ALL REPORTS                |");
            System.out.println("| 2. INDIVIDUAL REPORTS              |");
            System.out.println("| 3. EXIT                            |");
            System.out.println("--------------------------------------");
            
            System.out.print("Enter your choice (1-3): ");

            int choice;
            while (true) {
                try {
                    choice = input.nextInt();
                    if (choice >= 1 && choice <= 3) {
                        break;
                    } else {
                        System.out.print("Invalid choice, Please select a number between 1 and 3: ");
                    }
                } catch (Exception e) {
                    input.next();
                    System.out.print("Invalid input, Please enter a valid number: ");
                }
            }

            switch (choice) {
                case 1:
                    viewAllReports();
                    break;
                case 2:
                    individualReport();
                    break;
                case 3:
                    break;
            }

            System.out.print("Do you want to continue? (yes/no): ");
            String response = input.next().toLowerCase();
            while (!response.equals("yes") && !response.equals("no")) {
                System.out.println("Invalid input, please enter 'yes' or 'no'.");
                System.out.print("Do you want to continue? (yes/no): ");
                response = input.next().toLowerCase();
            }

            if (response.equals("no")) {
                exit = false;
            }

        } while (exit);
    }

    public void viewAllReports() {
    boolean continueReports = true;
    do {
        System.out.println("--------------------------------------");
        System.out.println("|           ALL REPORTS MENU         |");
        System.out.println("--------------------------------------");
        System.out.println("| 1. VIEW FLIGHTS REPORT             |");
        System.out.println("| 2. VIEW PASSENGERS REPORT          |");
        System.out.println("| 3. VIEW BOOKINGS REPORT            |");
        System.out.println("| 4. EXIT                            |");
        System.out.println("--------------------------------------");

        System.out.print("Enter your choice (1-4): ");

        int choice;
        while (true) {
            try {
                choice = input.nextInt();
                if (choice >= 1 && choice <= 4) {
                    break;
                } else {
                    System.out.print("Invalid choice, Please select a number between 1 and 4: ");
                }
            } catch (Exception e) {
                input.next(); 
                System.out.print("Invalid input, Please enter a valid number: ");
            }
        }

        switch (choice) {
            case 1:
                System.out.println("\n--- FLIGHTS REPORT ---");
                flights.viewAllFlights();
                break;
            case 2:
                System.out.println("\n--- PASSENGERS REPORT ---");
                passengers.viewAllPassengers();
                break;
            case 3:
                System.out.println("\n--- BOOKINGS REPORT ---");
                bookings.viewAllBookings();
                break;
            case 4:
                continueReports = false; // 
                break;
        }

        if (choice != 4) {
            System.out.print("Do you want to view another report? (yes/no): ");
            String response = input.next().toLowerCase();
            while (!response.equals("yes") && !response.equals("no")) {
                System.out.println("Invalid input, please enter 'yes' or 'no'.");
                System.out.print("Do you want to view another report? (yes/no): ");
                response = input.next().toLowerCase();
            }

            if (response.equals("no")) {
                continueReports = false;
            }
        }

    } while (continueReports);
}

    public void individualReport() {
        System.out.println("\n--------------------------------------");
        System.out.println("|        INDIVIDUAL REPORTS         |");
        System.out.println("--------------------------------------");

        System.out.println("\n--- AVAILABLE PASSENGERS ---");
        passengers.viewAllPassengers();  

        int passengerId = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter the Passenger ID to view report: ");
            if (input.hasNextInt()) {
                passengerId = input.nextInt();
                if (passengerId > 0) {
                    if (passengers.doesIDExist(passengerId)) {
                        validInput = true;  
                    } else {
                        System.out.println("No Passenger found with this ID. Please try again.");
                    }
                } else {
                    System.out.println("Please enter a positive integer for Passenger ID.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric Passenger ID.");
                input.next();  
            }
        }

        System.out.println("\n--- ASSOCIATED FLIGHTS ---");
        flights.viewFlightsByPassengerId(passengerId);

        System.out.println("\n--- ASSOCIATED BOOKINGS ---");
        bookings.viewBookingsByPassengerId(passengerId);
    }

    public class Flights {
        public void viewAllFlights() {
            String sqlQuery = "SELECT * FROM tbl_flights";
            String[] columnHeaders = {"FLIGHT ID", "FLIGHT NUMBER", "DEPARTURE", "ARRIVAL", "DEPARTURE TIME", "ARRIVAL TIME", "AIRCRAFT TYPE", "FLIGHT STATUS"};
            String[] columnNames = {"f_id", "f_number", "f_departure", "f_arrival", "f_departuretime", "f_arrivaltime", "f_aircraft", "f_status"};
            conf.viewRecord(sqlQuery, columnHeaders, columnNames);
        }

        public void viewFlightsByPassengerId(int passengerId) {
            String sqlQuery = "SELECT f.f_id, f.f_number, f.f_departure, f.f_arrival, f.f_departuretime, " +
                              "f.f_arrivaltime, f.f_aircraft, f.f_status " +
                              "FROM tbl_bookings b " +
                              "JOIN tbl_flights f ON b.f_id = f.f_id " +
                              "WHERE b.p_id = ?";
            String[] columnHeaders = {"FLIGHT ID", "FLIGHT NUMBER", "DEPARTURE", "ARRIVAL", "DEPARTURE TIME", "ARRIVAL TIME", "AIRCRAFT TYPE", "FLIGHT STATUS"};
            String[] columnNames = {"f_id", "f_number", "f_departure", "f_arrival", "f_departuretime", "f_arrivaltime", "f_aircraft", "f_status"};
            conf.viewRecordWithID(sqlQuery, columnHeaders, columnNames, passengerId);
        }

        public boolean doesIDExist(int id) {
            String query = "SELECT COUNT(*) FROM tbl_flights WHERE f_id = ?";
            return conf.doesIDExistInDatabase(query, id);
        }
    }

    public class Passengers {
        public void viewAllPassengers() {
            String sqlQuery = "SELECT * FROM tbl_passengers";
            String[] columnHeaders = {"PASSENGER ID", "PASSPORT NUMBER", "FIRST NAME", "LAST NAME", "GENDER", "AGE", "ADDRESS", "EMAIL"};
            String[] columnNames = {"p_id", "p_pnumber", "p_fname", "p_lname", "p_gender", "p_age", "p_address", "p_email"};
            conf.viewRecord(sqlQuery, columnHeaders, columnNames);
        }

        public boolean doesIDExist(int id) {
            String query = "SELECT COUNT(*) FROM tbl_passengers WHERE p_id = ?";
            return conf.doesIDExistInDatabase(query, id);
        }
    }

    public class Bookings {
        public void viewAllBookings() {
            String sqlQuery = "SELECT b.b_id, f.f_number, p.p_pnumber, b.b_date, b.b_tprice, b.b_status " +
                              "FROM tbl_bookings b " +
                              "JOIN tbl_flights f ON b.f_id = f.f_id " +
                              "JOIN tbl_passengers p ON b.p_id = p.p_id";
            String[] columnHeaders = {"BOOKING ID", "FLIGHT NUMBER", "PASSPORT NUMBER", "BOOKING DATE", "TICKET PRICE", "BOOKING STATUS"};
            String[] columnNames = {"b_id", "f_number", "p_pnumber", "b_date", "b_tprice", "b_status"};
            conf.viewRecord(sqlQuery, columnHeaders, columnNames);
        }

        public void viewBookingsByPassengerId(int passengerId) {
            String sqlQuery = "SELECT b.b_id, f.f_number, p.p_pnumber, b.b_date, b.b_tprice, b.b_status " +
                              "FROM tbl_bookings b " +
                              "JOIN tbl_flights f ON b.f_id = f.f_id " +
                              "JOIN tbl_passengers p ON b.p_id = p.p_id " +
                              "WHERE b.p_id = ?";
            String[] columnHeaders = {"BOOKING ID", "FLIGHT NUMBER", "PASSPORT NUMBER", "BOOKING DATE", "TICKET PRICE", "BOOKING STATUS"};
            String[] columnNames = {"b_id", "f_number", "p_pnumber", "b_date", "b_tprice", "b_status"};
            conf.viewRecordWithID(sqlQuery, columnHeaders, columnNames, passengerId);
        }

        public boolean doesIDExist(int id) {
            String query = "SELECT COUNT(*) FROM tbl_bookings WHERE b_id = ?";
            return conf.doesIDExistInDatabase(query, id);
        }
    }

    public class Config {
        private static final int COLUMN_WIDTH = 20;

        public void viewRecord(String query, String[] headers, String[] columns) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:tolentino.db");
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                printHeaders(headers);

                while (rs.next()) {
                    for (String column : columns) {
                        String value = rs.getString(column);

                        if (value == null) {
                            value = "N/A";
                        }

                        System.out.print(String.format("%-" + COLUMN_WIDTH + "s", value));
                    }
                    System.out.println();  
                }

            } catch (SQLException e) {
                System.out.println("Error accessing database: " + e.getMessage());
            }
        }

        public void viewRecordWithID(String query, String[] headers, String[] columns, int id) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:tolentino.db");
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {

                    printHeaders(headers);

                    while (rs.next()) {
                        for (String column : columns) {
                            String value = rs.getString(column);

                            if (value == null) {
                                value = "N/A";
                            }

                            System.out.print(String.format("%-" + COLUMN_WIDTH + "s", value));
                        }
                        System.out.println();  
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error accessing database: " + e.getMessage());
            }
        }

        private void printHeaders(String[] headers) {
            
            for (String header : headers) {
                System.out.print(String.format("%-" + COLUMN_WIDTH + "s", header));
            }
            System.out.println();  

            System.out.println(generateTableLine());
        }

        private String generateTableLine() {
            return new String(new char[COLUMN_WIDTH * 8]).replace("\0", "-");
        }

        public boolean doesIDExistInDatabase(String query, int id) {
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:tolentino.db");
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    rs.next();
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                System.out.println("Error accessing database: " + e.getMessage());
                return false;
            }
        }
    }
}