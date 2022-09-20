package DatabaseConnectivity;
import DataProcessing.bookingsData;
import DataProcessing.busData;
import DataProcessing.userData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class sqlConnection {            //class to read,insert,update and delete data in the database
    private final String dbuURL = "jdbc:mysql://localhost:3306/bus_ticket_booking";
    private final String username = "root";
    private final String Password = "Netge@r1";
    private final Scanner sc=new Scanner(System.in);
    public boolean InsertUserData(userData usr)     //insert user data while registration
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String query = "INSERT INTO LOGIN_REGISTRATION(UserName,PhoneNumber,Date_Of_Birth,Email,Password) VALUES (?,?,?,?,?)";
            java.util.Date myDate = new java.util.Date(usr.getDateOfBirth());
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, usr.getName());
            statement.setString(2, usr.getPhoneNumber());
            statement.setDate(3, sqlDate);
            statement.setString(4, usr.getEmail());
            statement.setString(5, usr.getPassword());

            int rowInserted = statement.executeUpdate();
            statement.close();
            if (rowInserted > 0) return true;
        } catch (SQLException ex) {
            // ex.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean checkUserData(userData usr)      //check user data while login
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String query = "SELECT * FROM LOGIN_REGISTRATION WHERE Email='" + usr.getEmail() + "' AND Password='" + usr.getPassword() + "'";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            usr.setName(result.getString("UserName"));
            usr.setPhoneNumber(result.getString("PhoneNumber"));
            usr.setDateOfBirth(result.getDate("Date_Of_Birth").toString());
            usr.setUserType(result.getString("userType"));
            usr.setCustomerId(result.getInt("userId"));
            return true;
        } catch (SQLException ex) {
            //ex.printStackTrace();
            return false;
        }
    }

    public boolean insertBusDetails(busData bsd)        //insert new bus in the database
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String query = "INSERT INTO BUS_DETAIL (Bus_id,Bus_Name,Bus_Type,Bus_Starting_Point,Bus_Destination_Point,No_Of_Seats,bus_Stoppages,Price,bus_Destination_Time,bus_Departure_Time) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, bsd.getBusNumber());
            statement.setString(2, bsd.getBusName());
            statement.setString(3, bsd.getBusType());
            statement.setString(4, bsd.getSource());
            statement.setString(5, bsd.getDestination());
            statement.setInt(6, bsd.getNumberOfSeats());
            statement.setString(7, bsd.getStoppages());
            statement.setDouble(8, bsd.getPrice());
            statement.setTime(9, Time.valueOf(bsd.getDestinationTime() + ":00"));
            statement.setTime(10, Time.valueOf(bsd.getStartingTime() + ":00"));
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            //ex.printStackTrace();
        }
        return false;
    }

    public void updateBusDetail(busData bsd)        //update bus details in the database
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String query = "UPDATE BUS_DETAIL SET Bus_Starting_Point=?,Bus_Destination_Point=?,bus_Departure_Time=?,bus_Destination_Time=?,Price=?,bus_Stoppages=? WHERE Bus_id=" + bsd.getBusNumber();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, bsd.getSource());
            statement.setString(2, bsd.getDestination());
            statement.setTime(3, Time.valueOf(bsd.getStartingTime() + ":00"));
            statement.setTime(4, Time.valueOf(bsd.getDestinationTime() + ":00"));
            statement.setDouble(5, bsd.getPrice());
            statement.setString(6, bsd.getStoppages());
            statement.executeUpdate();
        } catch (SQLException ex) {
            //ex.printStackTrace();
        }
    }

    public boolean searchBus(busData bsd)           //check bus with the bus number
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String qur = "SELECT * FROM BUS_DETAIL WHERE Bus_id=" + bsd.getBusNumber();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(qur);
            result.next();
            bsd.setBusName(result.getString("Bus_Name"));
            bsd.setBusType(result.getString("Bus_Type"));
            bsd.setSource(result.getString("Bus_Starting_Point"));
            bsd.setDestination(result.getString("Bus_Destination_Point"));
            bsd.setNumberOfSeats(result.getInt("No_Of_Seats"));
            bsd.setStoppages(result.getString("bus_Stoppages"));
            bsd.setStartingTime(result.getTime("bus_Departure_Time").toString());
            bsd.setDestinationTime(result.getTime("bus_Destination_Time").toString());
            bsd.setPrice(result.getDouble("Price"));
            return true;
        } catch (SQLException ex) {
            //ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteBusDetail(busData bsd)         //delete bus from the database
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String qur = "DELETE FROM BUS_DETAIL WHERE Bus_id=" + bsd.getBusNumber();
            Statement statement = con.createStatement();
            statement.executeUpdate(qur);
            return true;
        } catch (SQLException ex) {
           // ex.printStackTrace();
            return false;
        }
    }

    public void checkBusesForJourney(bookingsData bookingD)     //method to check available buses for journey
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String query = "SELECT * FROM BUS_DETAIL WHERE Bus_Starting_Point='" + bookingD.getSourceLocation() + "' AND Bus_Destination_Point='" + bookingD.getDestination() + "'";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            ArrayList<busData> busList = new ArrayList<>();
            while (result.next()) {
                busData BSD = new busData();
                BSD.setBusNumber(result.getInt("Bus_id"));
                BSD.setBusName(result.getString("Bus_Name"));
                BSD.setSource(bookingD.getSourceLocation());
                BSD.setDestination(bookingD.getDestination());
                BSD.setStartingTime(result.getTime("bus_Departure_Time").toString());
                BSD.setDestinationTime(result.getTime("bus_Destination_Time").toString());
                BSD.setBusType(result.getString("Bus_Type"));
                BSD.setPrice(result.getDouble("Price"));
                BSD.setStoppages(result.getString("bus_Stoppages"));
                busList.add(BSD);
            }
            bookingD.setBusList(busList);
        } catch (SQLException ex) {
            //ex.printStackTrace();
        }
    }

    public void booking(bookingsData b1, int customerId)            //Method to book bus
    {

        java.util.Date date = new java.util.Date();
        java.sql.Date sqldate = new java.sql.Date(date.getTime());
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String query = "INSERT INTO BOOKINGS (Customer_Id,Date_Of_Journey,Booking_Date,Source_Location,Destination_Loaction,Bus_Number,Journey_Activity,Departure_Time,Destination_Time,Price) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, customerId);
            java.util.Date myDate = new java.util.Date(b1.getDateOfJourney());
            java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
            statement.setDate(2, sqlDate);
            statement.setDate(3, sqldate);
            statement.setString(4, b1.getSourceLocation());
            statement.setString(5, b1.getDestination());
            statement.setInt(6, b1.getSelectedBus().getBusNumber());
            statement.setString(7, "Active");
            statement.setTime(8, Time.valueOf(b1.getSelectedBus().getStartingTime()));
            statement.setTime(9, Time.valueOf(b1.getSelectedBus().getDestinationTime()));
            statement.setDouble(10, b1.getSelectedBus().getPrice());
            statement.executeUpdate();
        } catch (SQLException ex) {
           // ex.printStackTrace();
        }
    }

    public void ViewAvailableBuses()                //read all available buses detail
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password))
            {

                String query = "SELECT * FROM BUS_DETAIL ";


                Statement statement = con.createStatement();

                ResultSet result = statement.executeQuery(query);
                System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n", "bus_number", "bus_name","bus_type" ,"source", "destination", "total_seats", "departure_time", "destination_time", "price");
                while (result.next()) {
                    System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",result.getInt("Bus_id"),result.getString("Bus_Name"),result.getString("Bus_Type"),result.getString("Bus_Starting_Point"),result.getString("Bus_Destination_Point"),result.getInt("No_Of_Seats"),result.getTime("bus_Departure_Time"),result.getTime("bus_Destination_Time"),result.getDouble("Price"));
                }
            } catch(SQLException ex){
               // ex.printStackTrace();
            }
        }
    public void UpdateAccount(userData usr)            //update user account detail

    {

        try (Connection con = DriverManager.getConnection(dbuURL, username, Password))
        {
            String query = "UPDATE LOGIN_REGISTRATION SET UserName=?,PhoneNumber=?,Password=? WHERE Email='"+usr.getEmail()+"' AND Password='"+usr.getPassword()+"'";

            PreparedStatement statement=con.prepareStatement(query);
            statement.setString(1,usr.getName());
            statement.setString(2,usr.getPhoneNumber());
            statement.setString(3,usr.getPassword());
            statement.executeUpdate();
        }
        catch (SQLException ex)
        {
           // ex.printStackTrace();
        }
    }

    public void ViewCustomer()              //read customer detail with customer id
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password))
        {
            String query="SELECT * FROM LOGIN_REGISTRATION WHERE userType='customer'";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            System.out.printf("%-11s %-18s %-30s %-15s %-20s\n","Customer Id","User Name","Email","Phone number","DOB");
            while(result.next())
            {
            System.out.printf("%-11s %-18s %-30s %-15s %-20s\n",result.getInt("userId"),result.getString("UserName"),result.getString("Email"),result.getString("PhoneNumber"),result.getDate("Date_Of_Birth"));
            }
            System.out.println("1.Get trip detail customer wise\n2.cancel");
            if(sc.nextInt()==1) {
                System.out.println("Enter customer id to get trip details");
                showJournies(sc.nextInt());
            }
        }
        catch(SQLException ex)
        {
           // ex.printStackTrace();
        }
    }
    public void showJournies(int usr)           //read all journies by customer
    {
        try (Connection con = DriverManager.getConnection(dbuURL, username, Password)) {
            String query = "SELECT * FROM BOOKINGS WHERE Customer_Id='"+usr+"'";
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            System.out.printf("%-20s %-20s %-15s %20s %-20s %-20s %-20s %-20s\n" ,"Bus Number","Booking Date","Source Location","Departure At","Destination Location","Arrival at","Price","Activity");
            while(result.next())
            {
                System.out.printf("%-20s %-20s %-15s %20s %-20s %-20s %-20s %-20s\n" ,result.getInt("Bus_Number"),result.getDate("Booking_Date"),result.getString("Source_Location"),result.getTime("Departure_Time"),result.getString("Destination_Loaction"),result.getTime("Destination_Time"),result.getDouble("Price"),result.getString("Journey_Activity"));
            }
        }
        catch (SQLException ex)
        {
            //ex.printStackTrace();
        }
    }
}

