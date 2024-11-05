package flightmanagementsystem;

import java.util.Scanner;

public class bookings {
    
    public void bManagement(){
        
                   Scanner sc = new Scanner(System.in);
            String response;
            do {
                 System.out.println("\n--- BOOKINGS MENU ---");
                System.out.println("1. ADD BOOKINGS");
                System.out.println("2. VIEW BOOKINGS");
                System.out.println("3. UPDATE BOOKINGS");
                System.out.println("4. DELETE BOOKINGS");
                System.out.println("5. EXIT");


                System.out.print("Enter your choice: ");
                  int act = sc.nextInt();
                 bookings bs = new bookings();

                switch (act) {
                    case 1:
                        bs.addBookings();
                        bs.viewBookings();
                        break;
                    case 2:
                        bs.viewBookings();
                        break;
                    case 3:
                        
                        break;
                    case 4:
                        
                    break;
                    case 5:
                        
                        break;
                    
                }
                System.out.print("Do you want to continue? (yes/no): ");
                response = sc.next();
            } while (response.equalsIgnoreCase("yes"));
            
        }
                   
                   public void addBookings(){
                           Scanner sc = new Scanner(System.in);
                           config conf = new config();
                           flights fs = new flights();
                           fs.viewFlights();
                           
                           System.out.print("Enter the ID of the flight: ");
                           int fid = sc.nextInt();
                           
                           String fsql = "SELECT f_id FROM tbl_flights WHERE f_id = ?";
                           while(conf.getSingleValue(fsql, fid) == 0){
                               System.out.print("Flight does not exist choose again: ");
                               fid = sc.nextInt();
                           }
                           
                           passengers ps = new passengers();
                           ps.viewPassengers();
                           
                           System.out.print("Enter the ID of the passenger: ");
                           int pid = sc.nextInt();
                           
                           String psql = "SELECT p_id FROM tbl_passengers WHERE p_id = ?";
                           while(conf.getSingleValue(psql, pid) == 0){
                               System.out.print("Passenger does not exist choose again: ");
                               pid = sc.nextInt();
                           }
                           
                           System.out.print("Booking date: ");
                           String bdate = sc.next(); 
                           System.out.print("Booking status: ");
                           String bstatus = sc.next();
                           
                           String sql = "INSERT INTO tbl_bookings (b_date, b_status) VALUES (?, ?)";
        conf.addRecord(sql, bdate, bstatus);
    }
                   
                   public void viewBookings() {
        config conf = new config();
        String sqlQuery = "SELECT b_id, f_departure, f_destination, p_fname, p_lname, b_date, b_status FROM tbl_bookings "
                + "LEFT JOIN tbl_flights ON tbl_flights.f_id = tbl_bookings.f_id "
                + "LEFT JOIN tbl_passengers ON tbl_passengers.p_id = tbl_bookings.p_id";
        String[] columnHeaders = {"ID", "DEPARTURE", "DESTINATION", "FIRST NAME", "LAST NAME", "DATE", "STATUS"};
        String[] columnNames = {"b_id", "f_departure", "f_destination", "p_fname", "p_lname", "b_date", "b_status"};
        conf.viewRecord(sqlQuery, columnHeaders, columnNames);
            }
      }