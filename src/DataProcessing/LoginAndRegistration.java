package DataProcessing;
import DatabaseConnectivity.sqlConnection;

import java.util.Scanner;

public class LoginAndRegistration                           //Log in and registration class
{
    userData loginUD;
    sqlConnection register;
    Scanner sc=new Scanner(System.in);
    public userData login()                                 //log in method
    {
            register = new sqlConnection();
            loginUD = new userData();

            System.out.println("Enter Your Email Address");
            loginUD.setEmail(sc.nextLine());
            System.out.println("Enter Password");
            loginUD.setPassword(sc.nextLine());
            System.out.println("Checking for Authentication");
            if (register.checkUserData(loginUD)) {          //checking data from the server
                return loginUD;
            }
        return null;
    }
    public void Registration()                              //Registration method
    {
        loginUD=new userData();
        System.out.println("Enter your name");
        loginUD.setName(sc.nextLine());

        System.out.println("Enter your Email Address");
        loginUD.setEmail(sc.nextLine());

        System.out.println("Enter your Date Of Birth(DD/MM/YYYY)");
        loginUD.setDateOfBirth(sc.nextLine());

        System.out.println("Enter your Phone Number");
        loginUD.setPhoneNumber(sc.nextLine());

        while(true)
        {
            System.out.println("Enter your Password");
            String password = sc.nextLine();

            System.out.println("Re-Enter your Password to Confirm");
            String confirmPassword = sc.nextLine();

            if (password.equals(confirmPassword))
            {
                loginUD.setPassword(password);
                break;
            }
            System.out.println("Mismatch Password");
        }
        register=new sqlConnection();
        if(register.InsertUserData(loginUD))                //store data in the database
        System.out.println("Registration Successful");
        else {
            System.out.println("fail to register");
        }
    }
}
