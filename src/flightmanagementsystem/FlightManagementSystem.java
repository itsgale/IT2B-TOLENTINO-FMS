package flightmanagementsystem;

import java.util.Scanner;

public class FlightManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exit = true;
        do{
       System.out.println("\n   + FLIGHT MANAGEMENT SYSTEM +\n");
                System.out.println("1. FLIGHTS");
                System.out.println("2. PASSENGERS");
                System.out.println("3. BOOKINGS");
                System.out.println("4. REPORTS");
                System.out.println("5. EXIT");
                
                System.out.print("Enter your choice: ");
                int action = sc.nextInt();
                
                switch(action){
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
                        
                    case 5:
                        System.out.print("Exiting selection, type 'yes' to exit 'no to continue: ");
                        String resp = sc.next();
                        if(resp.equalsIgnoreCase("yes")){
                        exit = false;
                        }
                        break;
                }
            }while(exit);
    }
}