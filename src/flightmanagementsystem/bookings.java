package flightmanagementsystem;

import java.util.Scanner;

public class bookings {

    public void bManagement() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("--------------------------------------");
            System.out.println("|                                    |");
            System.out.println("|        WELCOME TO BOOKINGS         |");
            System.out.println("|               MENU                 |");
            System.out.println("|                                    |");
            System.out.println("--------------------------------------");
            System.out.println("| 1. ADD BOOKINGS                    |");
            System.out.println("| 2. VIEW BOOKINGS                   |");
            System.out.println("| 3. UPDATE BOOKINGS                 |");
            System.out.println("| 4. DELETE BOOKINGS                 |");
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

            bookings bs = new bookings();

            switch (choice) {
                case 1:
                    bs.addBookings();
                    bs.viewBookings();
                    break;
                case 2:
                    bs.viewBookings();
                    break;
                case 3:
                    bs.viewBookings();
                    bs.updateBookings();
                    bs.viewBookings();
                    break;
                case 4:
                    bs.viewBookings();
                    bs.deleteBookings();
                    bs.viewBookings();
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

    public void addBookings() {
    Scanner sc = new Scanner(System.in);
    config conf = new config();
    flights fs = new flights();
    fs.viewFlights();

    int fid = 0;
    boolean validFlightId = false;
    while (!validFlightId) {
        System.out.print("Enter ID of the flight: ");
        if (sc.hasNextInt()) {
            fid = sc.nextInt();
            sc.nextLine();
            String fsql = "SELECT f_status FROM tbl_flights WHERE f_id = ?";
            String flightStatus = conf.getSingleStringValue(fsql, fid);

            if (flightStatus == null) {
                System.out.println("Selected flight ID doesn't exist, Select again.");
            } else if (flightStatus.equalsIgnoreCase("cancelled")) {
                System.out.println("The selected flight is cancelled and cannot be booked.");
            } else {
                validFlightId = true;
            }
        } else {
            System.out.println("Invalid input, Please enter a valid flight ID.");
            sc.next();
        }
    }

    passengers ps = new passengers();
    ps.viewPassengers();

    int pid = 0;
    boolean validPassengerId = false;
    while (!validPassengerId) {
        System.out.print("Enter ID of the passenger: ");
        if (sc.hasNextInt()) {
            pid = sc.nextInt();
            sc.nextLine();
            String psql = "SELECT p_id FROM tbl_passengers WHERE p_id = ?";
            if (conf.getSingleValue(psql, pid) != 0) {
                validPassengerId = true;
            } else {
                System.out.println("Selected passenger ID doesn't exist, Select again.");
            }
        } else {
            System.out.println("Invalid input, Please enter a valid passenger ID.");
            sc.next();
        }
    }

    String checkBookingSql = "SELECT COUNT(*) FROM tbl_bookings WHERE p_id = ? AND f_id = ?";
    int existingBookings = (int) conf.getSingleValue(checkBookingSql, pid, fid);

    if (existingBookings > 0) {
        System.out.println("This passenger is already booked on this flight.");
        return;
    }

    String bdate = "";
    boolean validDate = false;
    while (!validDate) {
        System.out.print("Enter booking date (YYYY-MM-DD): ");
        bdate = sc.nextLine();
        if (bdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            validDate = true;
        } else {
            System.out.println("Invalid date format, Please enter the date in YYYY-MM-DD format.");
        }
    }

    String btprice = "";
    boolean validPrice = false;
    while (!validPrice) {
        System.out.println("Select ticket class:");
        System.out.println("1. Economy (666.66)");
        System.out.println("2. First Class (999.99)");

        int classChoice = 0;
        boolean validClassChoice = false;
        while (!validClassChoice) {
            System.out.print("Enter your choice (1 or 2): ");
            if (sc.hasNextInt()) {
                classChoice = sc.nextInt();
                sc.nextLine();
                if (classChoice == 1) {
                    btprice = "666.66";
                    validClassChoice = true;
                } else if (classChoice == 2) {
                    btprice = "999.99";
                    validClassChoice = true;
                } else {
                    System.out.println("Invalid choice, Please select 1 for Economy or 2 for First Class.");
                }
            } else {
                System.out.println("Invalid input, Please enter a valid number (1 or 2).");
                sc.next();
            }
        }

        validPrice = true;
    }

    String bstatus = "";
    boolean validStatus = false;
    while (!validStatus) {
        System.out.print("Enter booking status (confirmed/cancelled): ");
        bstatus = sc.nextLine().toLowerCase();
        if (bstatus.equals("confirmed") || bstatus.equals("cancelled")) {
            validStatus = true;
        } else {
            System.out.println("Invalid booking status, Please enter 'confirmed' or 'cancelled'.");
        }
    }

    String sql = "INSERT INTO tbl_bookings (f_id, p_id, b_date, b_tprice, b_status) VALUES (?, ?, ?, ?, ?)";
    conf.addRecord(sql, fid, pid, bdate, btprice, bstatus);
    System.out.println("Booking added successfully.");
}

    public void viewBookings() {
        config conf = new config();
        String sqlQuery = "SELECT tbl_bookings.b_id, tbl_flights.f_number, tbl_passengers.p_pnumber, tbl_bookings.b_date, tbl_bookings.b_status, tbl_bookings.b_tprice " +
                          "FROM tbl_bookings " +
                          "LEFT JOIN tbl_flights ON tbl_flights.f_id = tbl_bookings.f_id " +
                          "LEFT JOIN tbl_passengers ON tbl_passengers.p_id = tbl_bookings.p_id";
        String[] columnHeaders = {"BOOKINGS ID", "FLIGHTS NUMBER", "PASSPORTS NUMBER", "BOOKINGS DATE", "TICKETS PRICE", "BOOKINGS STATUS"};
        String[] columnNames = {"b_id", "f_number", "p_pnumber", "b_date", "b_tprice", "b_status"};
        conf.viewRecord(sqlQuery, columnHeaders, columnNames);
    }

    public void updateBookings() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = 0;
        boolean validId = false;
        while (!validId) {
            System.out.print("Enter ID of the booking to update: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine();
                String checkSql = "SELECT b_status FROM tbl_bookings WHERE b_id = ?";
                String currentStatus = conf.getSingleStringValue(checkSql, id);
                if (currentStatus != null) {
                    if (currentStatus.equals("confirmed")) {
                        System.out.println("Booking with status confirmed cannot be updated.");
                        return;
                    } else {
                        validId = true;
                    }
                } else {
                    System.out.println("Selected booking ID doesn't exist, Select again.");
                }
            } else {
                System.out.println("Invalid input, Please enter a valid booking ID.");
                sc.next();
            }
        }

        String bdate = "";
        boolean validDate = false;
        while (!validDate) {
            System.out.print("Enter new booking date (YYYY-MM-DD): ");
            bdate = sc.nextLine();
            if (bdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                validDate = true;
            } else {
                System.out.println("Invalid date format, Please enter the date in YYYY-MM-DD format.");
            }
        }

        String btprice = "";
boolean validPrice = false;
while (!validPrice) {
    System.out.println("Select ticket class:");
    System.out.println("1. Economy (666.66)");
    System.out.println("2. First Class (999.99)");

    int classChoice = 0;
    boolean validClassChoice = false;
    while (!validClassChoice) {
        System.out.print("Enter your choice (1 or 2): ");
        if (sc.hasNextInt()) {
            classChoice = sc.nextInt();
            sc.nextLine();  
            if (classChoice == 1) {
                btprice = "666.66";  
                validClassChoice = true;
            } else if (classChoice == 2) {
                btprice = "999.99";  
                validClassChoice = true;
            } else {
                System.out.println("Invalid choice, Please select 1 for Economy or 2 for First Class.");
            }
        } else {
            System.out.println("Invalid input, Please enter a valid number (1 or 2).");
            sc.next();  
        }
    }

    validPrice = true; 
}

        String bstatus = "";
        boolean validStatus = false;
        while (!validStatus) {
            System.out.print("Enter new booking status (confirmed/cancelled): ");
            bstatus = sc.nextLine().toLowerCase();
            if (bstatus.equals("confirmed") || bstatus.equals("cancelled")) {
                validStatus = true;
            } else {
                System.out.println("Invalid booking status, Please enter 'confirmed' or 'cancelled'.");
            }
        }

        String sql = "UPDATE tbl_bookings SET b_date = ?, b_tprice = ?, b_status = ? WHERE b_id = ?";
        conf.updateRecord(sql, bdate, btprice, bstatus, id);
        System.out.println("Booking updated successfully.");
    }

    public void deleteBookings() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        int id = 0;
        boolean validId = false;
        while (!validId) {
            System.out.print("Enter ID of the booking to delete: ");
            if (sc.hasNextInt()) {
                id = sc.nextInt();
                sc.nextLine();
                String checkSql = "SELECT b_id FROM tbl_bookings WHERE b_id = ?";
                if (conf.getSingleValue(checkSql, id) != 0) {
                    validId = true;
                } else {
                    System.out.println("Selected booking ID doesn't exist, Select again.");
                }
            } else {
                System.out.println("Invalid input, Please enter a valid booking ID.");
                sc.next();
            }
        }

        String sql = "DELETE FROM tbl_bookings WHERE b_id = ?";
        conf.deleteRecord(sql, id);
        System.out.println("Booking deleted successfully.");
    }
}