import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.*;

/**
 * Created by lucia on 11/10/15.
 */
public class DatabaseCustomer {
    private Connection conn=null;
    Customer customer;
    Restaurant restaurant;
    Booking booking;
    int availableseats = 0;

    public DatabaseCustomer(){


    }

    public void conectToDb(){
        System.out.println("***********Welcome to connections**************");
        try {

            // String DB_URL = "jdbc:mysql://luftbike.dk.mysql:3306/luftbike_dk";
            String DB_URL = "jdbc:mysql://sql4.freesqldatabase.com:3306/sql496304";

            String USER = "sql496304";
            String PASS = "JnfWuYHb7F";
            // nameOfDbOnline https://dbadmin.one.com/index.php
            //luftbike.dk
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("conn obj created" + conn + " message: ");


            //deleteFromDB("personse", 4);
            //  updateInDB("persons", 3, "Ragnhild", "goodday");
            // System.out.println(getPerson("anna"));

        }
        catch (SQLException e)
        {
            System.out.println("db error" + e.getMessage());
        }
    }




    public void insertCustomer(Customer insertCustomer1) {
        Customer insertCustomer = insertCustomer1;
        String sql = "INSERT INTO customer VALUES ( ?, ?, ?,?,?,?)";


        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,insertCustomer.getMail());
            preparedStatement.setString(2, insertCustomer.getUser());
            preparedStatement.setString(3, insertCustomer.getName());
            preparedStatement.setString(4, insertCustomer.getSurname());
            preparedStatement.setInt(5, insertCustomer.getTelephone());
            preparedStatement.setString(6, insertCustomer.getPassword());


            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void insertCustomerWithoutReservation(Customer customer2) {
        Customer insertCustomerWithoutReservation = customer2;
        String sql = "INSERT INTO customer VALUES ( ?, NULL , ?,?,?,NULL )";


        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, insertCustomerWithoutReservation.getMail());
            preparedStatement.setString(2, insertCustomerWithoutReservation.getName());
            preparedStatement.setString(3, insertCustomerWithoutReservation.getSurname());
            preparedStatement.setInt(4, insertCustomerWithoutReservation.getTelephone());


            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void insertBooking(Booking booking) {
        String sql = "INSERT INTO booking VALUES (null, ?, ?,?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, booking.getMail());
            preparedStatement.setInt(2, booking.getCvr());
            preparedStatement.setTimestamp(3, booking.getBookingDate());
            preparedStatement.setInt(4, booking.getNumberOfPeople());
            preparedStatement.setTimestamp(5, booking.getCurrent());


            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
       public boolean cheackPeople(int cvr, Timestamp timestamp, Timestamp endTime, int numberOfPeople){
           String sql = "SELECT sum( booking.numberOfPeople ) , booking.bookingDate, totalSeats FROM restaurantStatus " +
                   "INNER JOIN booking ON restaurantStatus.cvr = booking.CVR " +
                   "WHERE booking.bookingDate >= ? AND booking.bookingDate <= ? AND booking.CVR = ?" ;
           try {
               PreparedStatement preparedStatement = conn.prepareStatement(sql);
               preparedStatement.setTimestamp(1, timestamp);
               preparedStatement.setTimestamp(2, endTime);
               preparedStatement.setInt(3, cvr);
               ResultSet resultSet = preparedStatement.executeQuery();

               while (resultSet.next()) {
                   int seats=   resultSet.getInt(3)-resultSet.getInt(1)-numberOfPeople;
                   availableseats = resultSet.getInt(3)-resultSet.getInt(1);
                   if(seats>=0){
                       System.out.println("availbale number of seats: "+seats +" after your book");
                       return  true;
                   }else {
                       System.out.println(" no more space for this hour ;D");
                       System.out.println("you can book for " + availableseats);
                       return  false;
                   }
              }
           } catch (SQLException e) {
               e.printStackTrace();

           }
           return true;
       }
        public int getBookingNumber(Timestamp time){

            String sql = "SELECT bookingNO FROM booking WHERE currentBooking=?";
            int number = 0;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setTimestamp(1, time);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {



                    number = resultSet.getInt(1);


                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
            return  number;

        }



    //==============================================================================================================================================

    //GET CUSTOMER'S CURRENT BOOKINGS
    public ObservableList<Booking> getCustomerCurrentBookings(Timestamp bookingDate, String mail){
        ObservableList<Booking> reservations = FXCollections.observableArrayList();
        String sql = "SELECT * FROM booking INNER JOIN restaurant ON restaurant.CVR = booking.CVR WHERE booking.bookingDate >=? AND booking.mail =?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, bookingDate);
            preparedStatement.setString(2, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                booking = new Booking(resultSet.getInt(1),resultSet.getString(8),resultSet.getTimestamp(4), resultSet.getInt(5));
                reservations.add(booking);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Couldn't getCustomerCurrentBookings!");
        }
        return reservations;
    }
    public ObservableList<String> getCustomerCurrentBookingId(Timestamp bookingDate, String mail){
        ObservableList<String> bookingIds = FXCollections.observableArrayList();
        String sql = "SELECT bookingNo FROM booking INNER JOIN restaurant ON restaurant.CVR = booking.CVR WHERE booking.bookingDate >=? AND booking.mail =?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, bookingDate);
            preparedStatement.setString(2, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookingIds.add(String.valueOf(resultSet.getInt(1)));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Couldn't getCustomerCurrentBookingId!");
        }
        return bookingIds;
    }
    public void updateCustomerBooking(int bookingNo,Timestamp bookingDate, int numberOfPeople){
        String sql = "UPDATE booking SET bookingDate =?, numberOfPeople=? WHERE bookingNo =?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, bookingDate);
            preparedStatement.setInt(2, numberOfPeople);
            preparedStatement.setInt(3,bookingNo);
            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected for updateCustomerBooking:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't UPDATE the reservation!");
        }
    }
    //GET CUSTOMER'S PAST BOOKINGS - ONLY DATE AND RESTAURANT
    public  ObservableList<Booking> getCustomerPastBookings(Timestamp bookingDate, String mail){
        ObservableList<Booking> pastReservations = FXCollections.observableArrayList();
        String sql = "SELECT restaurant.nameOfRestaurant, bookingDate FROM booking INNER JOIN restaurant ON restaurant.CVR = booking.CVR WHERE booking.bookingDate <? AND booking.mail = ? LIMIT 0, 4;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, bookingDate);
            preparedStatement.setString(2, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                booking = new Booking(resultSet.getString(1), resultSet.getTimestamp(2));
                pastReservations.add(booking);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Couldn't getCustomerPASTBookings!");
        }
        return pastReservations;
    }
    public Booking getOneCurrentBooking(int id){

        String sql = "SELECT * FROM booking INNER JOIN restaurant ON restaurant.CVR = booking.CVR WHERE booking.bookingNo=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                booking = new Booking(resultSet.getInt(1),resultSet.getString(8),resultSet.getTimestamp(4), resultSet.getInt(5));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Couldn't getCustomerBookings!");
        }
        return booking;
    }

    public int fetchRestaurantCVR(int bookingId){
        int cvr=0;
        String sql = "SELECT restaurant.cvr FROM restaurant INNER JOIN booking ON booking.CVR = restaurant.CVR WHERE booking.bookingNo=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                cvr = resultSet.getInt(1);
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Couldn't fetchRestaurantCVR!");
        }
        return cvr;
    }

    //CANCEL A BOOKING
    public void deleteBooking(int bookingNo){
        String sql = "DELETE FROM booking WHERE bookingNo = ?";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookingNo);
            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Your reservation has been canceled. No of rows affected" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Couldn't CANCEL the reservation!");
        }
    }


    /////======================//////////////////////=========================/////////////////////=============================////////////////////=============////
    public int getDeposit(int cvr){

            String sql = "SELECT deposit FROM restaurant WHERE cvr=?";
            int deposit = 0;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, cvr);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {



                    deposit = resultSet.getInt(1);


                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
            return  deposit;

        }
    public int checkMailExistence(String mail){
        int count=0;
        String sql = "SELECT count(*) from customer WHERE mail = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mail);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getInt(1);
            }

        }catch(SQLException e){
            System.out.println("Couldn't check for existing customer!");
        }
        return count;
    }
    public void updateCustomer(Customer customer) {
        Customer cus=customer;
        String sql = "UPDATE customer SET user=?, name=?, surname=?, telefon=?, password=? WHERE mail=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, cus.getUser());
            preparedStatement.setString(2, cus.getName());
            preparedStatement.setString(3, cus.getSurname());
            preparedStatement.setInt(4, cus.getTelephone());
            preparedStatement.setString(5, cus.getPassword());
            preparedStatement.setString(6, cus.getMail());

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }







    public void insertRestaurant(int cvr, String nameOfRestaurant, String companyName, String address, int phone,String mail, String password) {
        String sql = "INSERT INTO restaurant VALUES (?, ?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, cvr);
            preparedStatement.setString(2, nameOfRestaurant);
            preparedStatement.setString(3, companyName);
            preparedStatement.setString(4, address);
            preparedStatement.setInt(5, phone);
            preparedStatement.setString(6, mail);
            preparedStatement.setString(7, password);


            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public String getDescriptionOfRestaurant(int cvr) throws SQLException {
        String description ="";
        String sql = "SELECT description FROM restaurant WHERE cvr =?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, cvr);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {


            description = resultSet.getString(1);


        }
        return  description;

    }

     public String getWebSite(int cvr){
         String sql = "SELECT website FROM restaurant WHERE cvr =?";
         String webstie = "";

         try {
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             preparedStatement.setInt(1, cvr);

             ResultSet resultSet = preparedStatement.executeQuery();

             while (resultSet.next()) {



                 webstie = resultSet.getString(1);


             }
         } catch (SQLException e) {
             e.printStackTrace();

         }
         return  webstie;

     }


    public ObservableList<String> getRetaurants(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        Customer customer;

        String out = "";
        String sql = "SELECT nameOfRestaurant FROM restaurant";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {



                observableList.add(resultSet.getString(1));


            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return  observableList;

    }
    public ObservableList<Customer> getCustomers(){
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        Customer customer;

        String out = "";
        String sql = "SELECT * FROM customer";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                customer = new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),
                        resultSet.getInt(5),resultSet.getString(6));

                observableList.add(customer);


            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return  observableList;

    }

    public Customer checkLoginAndPassword(String login, String password){
        try {
            String sql = "SELECT * FROM customer WHERE user= ? AND password= ?";

            //Statement statement=conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                customer = new Customer(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),
                        resultSet.getInt(5),resultSet.getString(6));


            } else {
                customer = null;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  customer;
    }

    private void updateAppointmentCount(){
        try
        {
            String sql =
                    "SELECT user,password FROM doctorBooking\n" +
                            "INNER JOIN doctor ON \n" +
                            "doctor.iD = doctorBooking.doctorID\n" +
                            "GROUP BY doctorBooking.doctorID;";


            //Statement statement=conn.createStatement();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            Label label = new Label("Name: \t\t\t\tNumber of appointments:");
            while(resultSet.next()) {
                label = new Label(resultSet.getString(1)+" \t\t\t\t" + resultSet.getInt(2));




            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public int getRestaurant(String name){
        String sql ="SELECT cvr FROM restaurant WHERE nameOfRestaurant = ?";
        int cvr = 0;
        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                cvr = resultSet.getInt(1);

            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
       return  cvr;
    }
    public void deleteFromDB(String username)
    {
        String sql="DELETE FROM customer WHERE user = ?";
        try
        {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            int numberOfRows= preparedStatement.executeUpdate();
            System.out.println("Completed delete. Number of rows affected:"+numberOfRows);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public ObservableList<String> numberOfpeopleComboBox(){
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("1 person");
        observableList.add("2 people");
        observableList.add("3 people");
        observableList.add("4 people");
        observableList.add("5 people");
        observableList.add("6 people");
        observableList.add("7 people");
        observableList.add("8 people");

        return observableList;
    }


    public ObservableList<String> hourInCombobox(){
        ObservableList<String> observableList1 = FXCollections.observableArrayList();
        observableList1.addAll("15:00", "16:00", "17:00", "18:00" ,
                "19:00", "20:00");

        return  observableList1;

    }

    public int getAvailableseats(){
        return  availableseats;
    }








}
