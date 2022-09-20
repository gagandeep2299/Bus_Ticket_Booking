package Admin;

import DataProcessing.busData;
import DataProcessing.userData;
import DatabaseConnectivity.sqlConnection;
import java.util.Scanner;

public class AdminManagement {                              //admin class
    Scanner sc=new Scanner(System.in);
    busData busdata;
    sqlConnection sqlConnect =new sqlConnection();
    public void mainMenu(userData usr) {                       //main menu for the admin
        boolean Break = false;
        while (!Break) {
            System.out.println("""
                                                
                    -------Welcome to the Admin menu--------
                    Please Select your choice to Continue......
                    1.Add Bus Detail
                    2.Update Bus Detail
                    3.Delete Bus Detail
                    4.View available Buses
                    5.View Customer Detail
                    6.Account Update (Admin)
                    7.Search by Bus Number
                    8.Logout
                    """);
            switch (sc.nextInt()) {                            //option for admin ti use services
                case 1 -> InsertBusDetail();
                case 2 -> UpdateBusDetail();
                case 3 -> DeleteBusDetail();
                case 4 -> ViewAvailableBuses();
                case 5 -> ViewCustomerDetail();
                case 6 -> UpdateAccount(usr);
                case 7 -> searchBus();
                case 8 -> Break = true;
                default -> System.out.println("Select Valid option");
            }

        }
    }
    public void InsertBusDetail()                               //Add new Bus
    {
        busdata=new busData();
        System.out.println("Enter bus Number");
        busdata.setBusNumber(sc.nextInt());
        sc.nextLine();
        System.out.println("Enter bus Name");
        busdata.setBusName(sc.nextLine());
        System.out.println("Enter bus Type(Ac/Non-Ac");
        busdata.setBusType(sc.nextLine().toLowerCase());
        System.out.println("Enter source location of the bus");
        busdata.setSource(sc.nextLine());
        System.out.println("Enter Departure Time");
        busdata.setStartingTime(sc.nextLine());
        System.out.println("Enter destination location");
        busdata.setDestination(sc.nextLine());
        System.out.println("Enter Destination time");
        busdata.setDestinationTime(sc.nextLine());
        System.out.println("Enter Number of seats");
        busdata.setNumberOfSeats(sc.nextInt());
        System.out.println("Enter Ticket Price");
        busdata.setPrice(sc.nextDouble());
        System.out.println("Enter number of stoppages");
        int stoppages=sc.nextInt();
        StringBuilder sStoppages= new StringBuilder();
        sc.nextLine();
        while(stoppages>0)
        {
            System.out.println("Enter next stoppage name");
            String name=sc.nextLine();
            System.out.println("Enter stoppage arrival and departure time (HH:MM-To-HH:MM)");
            String time=sc.nextLine();
            sStoppages.append(name).append(" ").append(time);
            stoppages--;
        }
        busdata.setStoppages(sStoppages.toString());
        if(sqlConnect.insertBusDetails(busdata))                    //method to insert bus in the database
            System.out.println("Bus Added successfully");

    }
    public void UpdateBusDetail()                                  //update data of available bus
    {
        busdata=new busData();
        System.out.println("Enter bus number to update the data");
        busdata.setBusNumber(sc.nextInt());
        sc.nextLine();
        if(sqlConnect.searchBus(busdata)) {                         //method to check weather the bus is available or not
            System.out.println("Enter source location of the bus");
            busdata.setSource(sc.nextLine());
            System.out.println("Enter Departure Time (HH:MM)");
            busdata.setStartingTime(sc.nextLine());
            System.out.println("Enter destination location");
            busdata.setDestination(sc.nextLine());
            System.out.println("Enter Destination time (HH:MM)");
            busdata.setDestinationTime(sc.nextLine());
            System.out.println("Enter Ticket Price (IND)");
            busdata.setPrice(sc.nextDouble());
            System.out.println("Enter number of stoppages");
            int stoppages=sc.nextInt();
            StringBuilder sStoppages= new StringBuilder();
            sc.nextLine();
            while(stoppages>0)
            {
                System.out.println("Enter next stoppage name");
                String name=sc.nextLine();
                System.out.println("Enter stoppage arrival and departure time (HH:MM-To-HH:MM)");
                String time=sc.nextLine();
                sStoppages.append(name).append(" ").append(time);
                stoppages--;
            }
            busdata.setStoppages(sStoppages.toString());
            sqlConnect.updateBusDetail(busdata);                        //Update bus to the database
        }
        else
            System.out.println("Bus Not Found!");
    }
    public void searchBus()                                            //search bus with bus number
    {
        busdata=new busData();
        System.out.println("Enter bus number");
        busdata.setBusNumber(sc.nextInt());
        if(sqlConnect.searchBus(busdata))                               //show bus detail in the console
            System.out.println("Bus Number : "+busdata.getBusNumber()+"\nBus Name : "+busdata.getBusName()+"\nBus Type : "+busdata.getBusType()+
                    "\nBus source location : "+busdata.getSource()+" at "+busdata.getStartingTime()+"\nBus destination location : "+busdata.getDestination()+" at "+busdata.getDestinationTime()+
                    "\nBus Seats : "+busdata.getNumberOfSeats()+"\nBus Ticket Price : "+busdata.getPrice()+"\nBus Stoppages :\n"+busdata.getStoppages());
        else
            System.out.println("Bus Not Found!");
    }
    public void DeleteBusDetail()                                       //method to delete bus data
    {
        busdata=new busData();
        System.out.println("Enter Bus number");
        busdata.setBusNumber(sc.nextInt());
        if(sqlConnect.searchBus(busdata)) {
            System.out.println("Bus Number : " + busdata.getBusNumber() + "\nBus Name : " + busdata.getBusName() + "\nBus Type : " + busdata.getBusType() +
                    "\nBus source location : " + busdata.getSource() + " at " + busdata.getStartingTime() + "\nBus destination location : " + busdata.getDestination() + " at " + busdata.getDestinationTime() +
                    "\nBus Seats : " + busdata.getNumberOfSeats() + "\nBus Ticket Price : " + busdata.getPrice() + "\nBus Stoppages :\n" + busdata.getStoppages() + "Please Confirm to delete this bus detail\n1.yes\n2.no");
        if(sc.nextInt()==1)
            if(sqlConnect.deleteBusDetail(busdata))
                System.out.println("Bus Deleted Successfully");
        }
        else
            System.out.println("Bus Not Found!");
    }
    public void ViewAvailableBuses() {                                  //method to get all bus details
        sqlConnect.ViewAvailableBuses();
    }
    public void ViewCustomerDetail(){                                   //method to get all customer details with trip details
        sqlConnect.ViewCustomer();
    }
    public void UpdateAccount(userData usr){                            //method to update admin account detail
        sc.nextLine();
        System.out.println("Enter Name to update");
        usr.setName(sc.nextLine());


        System.out.println("Enter Phone_Number to update");
        usr.setPhoneNumber(sc.nextLine());

        System.out.println("Enter Password to update");
        usr.setPassword(sc.nextLine());

        sqlConnect.UpdateAccount(usr);
    }
}
