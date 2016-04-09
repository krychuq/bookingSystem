import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

/**
 * Created by lucia on 11/30/15.
 */
public class ModelRestaurant {
    private Connection conn = null;
    Restaurant restaurant;
    int availableseats = 0;
    String seats;


    public void conectToDb() {

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

        } catch (SQLException e) {
            System.out.println("db error" + e.getMessage());
        }
    }


    //CHECK LOGIN OF THE RESTAURANT IF CORRECT, CREATES RESTAURANT OBJECT
        public Restaurant checkLoginAndPassword(int cvr, String password) {
        String sql = "SELECT * FROM restaurant WHERE cvr= ? AND password= ?";
        try {

            //Statement statement=conn.createStatement();
            PreparedStatement preparedStatement1 = conn.prepareStatement(sql);
            preparedStatement1.setInt(1, cvr);
            preparedStatement1.setString(2, password);
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {

                restaurant = new Restaurant(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                        resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9),resultSet.getInt(10));

                System.out.println(restaurant.getCvr());


            } else {
                restaurant = null;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurant;
    }

    //Returns booking after current time(actual bookings)
    public ObservableList<Booking> getBookings(Timestamp bookingDate, int cvr) {
        ObservableList<Booking> observableList = FXCollections.observableArrayList();
        Booking booking;
        String out = "";
        String sql = "SELECT  bookingNo,bookingDate,numberOfPeople,customer.name FROM booking INNER JOIN customer ON booking.mail = customer.mail WHERE bookingDate >=? and CVR =?";


        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, bookingDate);
            preparedStatement.setInt(2, cvr);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                booking = new Booking(resultSet.getInt(1), resultSet.getTimestamp(2), resultSet.getString(4), resultSet.getInt(3));


                observableList.add(booking);

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return observableList;
    }

    //save restaurant information
    public void writeRestaurant(int cvr, String nameOfRestaurant, String companyName, String address, int phone, String mail, String password, String description,String webSite,int deposit) {
        String sql = "INSERT INTO restaurant VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, cvr);
            preparedStatement.setString(2, nameOfRestaurant);
            preparedStatement.setString(3, companyName);
            preparedStatement.setString(4, address);
            preparedStatement.setInt(5, phone);
            preparedStatement.setString(6, mail);
            preparedStatement.setString(7, password);
            preparedStatement.setString(8, description);
            preparedStatement.setString(9, webSite);
            preparedStatement.setInt(10,deposit);


            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //UPDATE RESTAURANT INFORMATION
    public void updateRestaurantProfile(String restaurant, int cvr, String nameOfRestaurant, String companyName, String address,
                                        int phone, String email, String password,String description, String website,int deposit) {
        String sql = "UPDATE restaurant SET cvr=?, nameOfRestaurant=?, companyName=?, address=?, phone=?, email=?, password=?, description=?,website=?,deposit =? WHERE cvr=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, cvr);
            preparedStatement.setString(2, nameOfRestaurant);
            preparedStatement.setString(3, companyName);
            preparedStatement.setString(4, address);
            preparedStatement.setInt(5, phone);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, password);
            preparedStatement.setString(8, description);
            preparedStatement.setString(9, website);
            preparedStatement.setInt(10,deposit);
            preparedStatement.setInt(11, cvr);
            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed update. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Deletes bookings from TableView and Database-booking
    public void deleteReservation(int bookingNo) {
        String sql = "DELETE FROM booking WHERE bookingNo = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, bookingNo);
            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed delete. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Save customer details entered by restaurant
    public void insertCustomerByRestaurant(String mail, String name, String surName, int phoneNumber) {
        String sql = "INSERT INTO customer VALUES ( ?, NULL , ?,?,?,NULL )";


        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surName);
            preparedStatement.setInt(4, phoneNumber);


            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //Saves bookings entered by restaurant
    public void insertBookingByRestaurant(String mail, int CVR, Timestamp bookingDate, int numberOfPeople, Timestamp currentBooking) {
        String sql = "INSERT INTO booking VALUES ( NULL,?,?,?,?,?)";


        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mail);
            preparedStatement.setInt(2, CVR);
            preparedStatement.setTimestamp(3, bookingDate);
            preparedStatement.setInt(4, numberOfPeople);
            preparedStatement.setTimestamp(5, currentBooking);


            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    //allows process the booking done by restaurant if enough available seats, advise how many seats are available after booking
    public boolean cheackPeople(int cvr, Timestamp timestamp, Timestamp endTime, int numberOfPeople) {
        String sql = "SELECT sum( booking.numberOfPeople ) , booking.bookingDate, totalSeats FROM restaurantStatus INNER JOIN booking ON restaurantStatus.cvr = booking.CVR WHERE booking.bookingDate >= ? AND booking.bookingDate <= ? AND booking.CVR = ?";
        try {

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setTimestamp(2, endTime);
            preparedStatement.setInt(3, cvr);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int seats = resultSet.getInt(3) - resultSet.getInt(1) - numberOfPeople;
                availableseats = resultSet.getInt(3) - resultSet.getInt(1);
                if (seats >= 0) {
                    System.out.println("availbale number of seats: " + seats + " after your book");
                    return true;
                } else {
                    System.out.println(" no more space for this hour ;D");
                    System.out.println("you can book for " + availableseats);
                    return false;
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return true;
    }

    //Saves total number of seats
    public void insertAvailableSeats(int cvr,int availableSeats) {
        String sql = "INSERT INTO restaurantStatus VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);




            preparedStatement.setInt(1, cvr);
            preparedStatement.setInt(2, availableSeats);


            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed insert. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //return number of seats
    public String getNumberOfSeats(int cvr){


        String sql = "SELECT totalSeats  FROM restaurantStatus  WHERE cvr =?";


        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, cvr);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                seats = resultSet.getString(1);



            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return seats;
    }


    // Update available seats
    public void updateAvailableSeats(String r, int cvr, int totalSeats){

        String sql = "UPDATE restaurantStatus SET cvr=?, totalSeats=? WHERE cvr=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, cvr);
            preparedStatement.setInt(2, totalSeats);
            preparedStatement.setInt(3, cvr);

            int numberOfRows = preparedStatement.executeUpdate();
            System.out.println("Completed update. Number of rows affected:" + numberOfRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}








