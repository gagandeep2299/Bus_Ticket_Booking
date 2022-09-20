package BusTicketManagementSystem;

import Admin.AdminManagement;
import Customer.CustomerManagement;
import DataProcessing.LoginAndRegistration;
import DataProcessing.userData;

import java.util.Scanner;

public class BusTicketBooking {
    private static boolean finish=false;
    static userData usr;
    static AdminManagement admin;
    static CustomerManagement customer;
    public static void main(String[] args)         //Main Method
    {

        Scanner sc=new Scanner(System.in);
        admin=new AdminManagement();
        customer=new CustomerManagement();
        LoginAndRegistration userLogin=new LoginAndRegistration();

        System.out.println("\n--------------------------  Welcome to the Bus Ticket Booking System  --------------------------\n");

        while(!finish) {
            System.out.println("""
                    ---- Please Login or Registration to Continue ----
                    1.Login
                    2.Registration
                    3.Exit""");
                    switch (sc.nextInt())         //condition to Log in or registration
                    {
                        case 1 ->
                        {
                            usr=userLogin.login();
                            if (usr!=null)
                            {
                                String user= usr.getUserType();
                                System.out.println("Login Successful");
                                if(user.equals("Admin"))
                                    admin.mainMenu(usr);
                                else
                                    customer.mainMenu(usr);
                            }
                            else System.out.println("Login failed");
                        }
                        case 2 -> userLogin.Registration();
                        case 3 -> finish = true;

                        default -> System.out.println("Please Enter valid option");
                    }
        }
    }
}
