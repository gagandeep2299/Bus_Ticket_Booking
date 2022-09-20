package Customer;

import java.util.Scanner;
import DataProcessing.bookingsData;
import DataProcessing.busData;
import DataProcessing.userData;
import DatabaseConnectivity.sqlConnection;

public class CustomerManagement                     //customer class
{
    Scanner sc=new Scanner(System.in);
    bookingsData b1;
    sqlConnection sql;
    public void mainMenu(userData usr)              //main menu for customer
    {
        boolean Break = false;
        while (!Break) {
            System.out.println("""
                                                
                    -------Welcome to the Main Menu--------
                    Please Select your choice to Continue......
                    1.Start New Journey
                    2.Your journies
                    3.Update Account
                    4.Logout
                    """);
            switch (sc.nextInt()) {                 //options to use customer services
                case 1 -> bookNewTicket(usr);
                case 2 -> History(usr);
                case 3 -> UpdateAccount(usr);
                case 4 -> Break = true;
                default -> System.out.println("Select Valid option");
            }

        }
    }
    public void bookNewTicket(userData usr)         //method to book new ticket
    {
        sc.nextLine();
        b1=new bookingsData();
        sql=new sqlConnection();
        System.out.println("Enter Date of Journey");
        b1.setDateOfJourney(sc.nextLine());
        System.out.println("Enter Starting location of the Journey");
        b1.setSourceLocation(sc.nextLine());
        System.out.println("Enter Destination Location of the Journey");
        b1.setDestination(sc.nextLine());
        sql.checkBusesForJourney(b1);               //method to check buses available for that route
        if(b1.getBusList().size()!=0) {
            System.out.println("Select any bus to continue...........");
            System.out.printf("%-11s %-10s %-10s %-12s %-20s %-17s %-23s %-7s \n", "Bus Number ", "Bus Name", "Bus Type", "Bus Source", "Bus Departure time", "Bus Destination", "Bus Destination time", "Price");
            int i = 1;
            for (busData b : b1.getBusList()) {
                System.out.print(i + ". ");
                System.out.printf("%-11s %-10s %-10s %-12s %-20s %-17s %-23s %-7s \n", b.getBusNumber(), b.getBusName(), b.getBusType(), b.getSource(), b.getStartingTime(), b.getDestination(), b.getDestinationTime(), b.getPrice());
                i++;
            }
            int bus = sc.nextInt();
            System.out.println("Please confirm to continue\n1.Yes\n2.No");
            int confirm = sc.nextInt();
            if (confirm == 1) {
                b1.setSelectedBus(b1.getBusList().get(bus - 1));
                sql.booking(b1, usr.getCustomer());                     //insert booking details in the database
                System.out.println("......Seat booked......");
            }
        }
        else System.out.println("Bus Not Found");
    }
    public void History(userData usr)                   //check all history of the customer journey
    {
        sql=new sqlConnection();
        sql.showJournies(usr.getCustomer());
    }
    public void UpdateAccount(userData usr)             //method to update account for the customer
    {
        sql=new sqlConnection();
        sc.nextLine();
        System.out.println("Enter Name to update");
        usr.setName(sc.nextLine());


        System.out.println("Enter Phone_Number to update");
        usr.setPhoneNumber(sc.nextLine());

        System.out.println("Enter Password to update");
        usr.setPassword(sc.nextLine());

        sql.UpdateAccount(usr);
    }

}
