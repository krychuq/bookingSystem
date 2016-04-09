import javafx.collections.ObservableList;
import javafx.scene.control.TableView;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lucia on 11/30/15.
 */
public class ControllerRestaurant {

    ModelRestaurant modelRestaurant = new ModelRestaurant();
    RunRestaurant runRestaurant;
    SignUp signUp;
    Restaurant restaurant;
    ProfileRestaurant profileRestaurant;
    AddBookingByRestaurant addBookingByRestaurant;
    ObservableList<Booking> showRestauarantBookings;
    Timestamp sq;
    DisplayBookings displayBookings;
    SeatsRestaurant seatsRestaurant;

    public ControllerRestaurant(RunRestaurant runRestaurant) {
        this.runRestaurant = runRestaurant;
        modelRestaurant.conectToDb();
    }


    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    //Checking the password and user name, if correct  creates restaurant with all the attributes
    public void logIn(RunRestaurant runRestaurant) {
        displayBookings = new DisplayBookings(this);
        this.runRestaurant = runRestaurant;

        restaurant = modelRestaurant.checkLoginAndPassword(runRestaurant.cvrNumb, runRestaurant.passwordTextField.getText());

        if (restaurant != null) {
            System.out.println("login succesed");

            displayBookings.start();

        } else {
            System.out.println("wrong user name or password");
        }

    }

    //Saves attributes of restaurant
    public void writeRestaurant(SignUp signUp) {
        this.signUp = signUp;


        modelRestaurant.writeRestaurant(signUp.cvrInt, signUp.nameOfRestaurantTextField.getText(), signUp.companyNameTextField.getText(), signUp.addressText.getText(),
                signUp.phoneInt, signUp.emailText.getText(), signUp.passwordTextField.getText(), signUp.descriptionText.getText(), signUp.websiteText.getText(), signUp.depositInt);
    }

    //Update restaurant attributes
    public void updateRestaurant(ProfileRestaurant profileRestaurant) {
        this.profileRestaurant = profileRestaurant;



        modelRestaurant.updateRestaurantProfile("restaurant", profileRestaurant.cvrInt, profileRestaurant.nameOfRestaurantTextField.getText(),
                profileRestaurant.companyNameTextField.getText(), profileRestaurant.addressText.getText(), profileRestaurant.phoneInt, profileRestaurant.emailText.getText(), profileRestaurant.passwordTextField.getText()
                , profileRestaurant.descriptionText.getText(), profileRestaurant.websiteText.getText(), profileRestaurant.depositInt);
    }
    //Inserts necessary customer information, if restaurant is to make booking for the customer
    public void insertCustomerByRestaurant(AddBookingByRestaurant addBookingByRestaurant) {

        this.addBookingByRestaurant = addBookingByRestaurant;

        modelRestaurant.insertCustomerByRestaurant(addBookingByRestaurant.customerMail.getText(), addBookingByRestaurant.customerName.getText(), addBookingByRestaurant.customerSurname.getText(), addBookingByRestaurant.phoneNumber);
    }


    //Inserts necessary information of booking, if restaurant is to make booking for customer
    public void insertBookingByRestaurant(AddBookingByRestaurant addBookingByRestaurant) {
        this.addBookingByRestaurant = addBookingByRestaurant;

        modelRestaurant.insertBookingByRestaurant(addBookingByRestaurant.customerMail.getText(), restaurant.getCvr(), addBookingByRestaurant.time, addBookingByRestaurant.numberOfPeopleInt, java.sql.Timestamp.valueOf((getCurrentTimeStamp())));
    }

    //Inserts available seats restaurant has
    public void inserAvailableSeats(SignUp signUp) {
        this.signUp = signUp;

        modelRestaurant.insertAvailableSeats(signUp.cvrInt, signUp.availableSeatInt);
    }


    //Display SignUP window with information to be filled in by restaurant, in order to create the account
    public void displaySignUp(){

        SignUp signUp = new SignUp(this);

        signUp.start();
    }

    //Display addBookingByRestaurant window, where restaurant enters booking and customer information for the customer
    public void addBooking(){

        AddBookingByRestaurant addBookingByRestaurant = new AddBookingByRestaurant(this);


        addBookingByRestaurant.start();


    }

    //Returns current booking
    public void addBookingByRest(){

        showRestauarantBookings = modelRestaurant.getBookings(java.sql.Timestamp.valueOf(displayBookings.getCurrentTimeStamp()),restaurant.getCvr());

    }
    //Display profile information of the restaurant
    public void profile(DisplayBookings displayBookings){
        this.displayBookings = displayBookings;




        ProfileRestaurant profileRestaurant = new ProfileRestaurant(this);

        profileRestaurant.cvrText.setText(String.valueOf(restaurant.getCvr()));
        profileRestaurant. nameOfRestaurantTextField.setText(restaurant.getRestaurantName());
        profileRestaurant.companyNameTextField.setText(restaurant.getCompanyName());
        profileRestaurant.addressText.setText(restaurant.getAddress());
        profileRestaurant.phoneText.setText(String.valueOf(restaurant.getPhone()));
        profileRestaurant. emailText.setText(restaurant.getMail());
        profileRestaurant. passwordTextField.setText(restaurant.getPassword());
        profileRestaurant. descriptionText.setText(restaurant.getDescription());
        profileRestaurant. websiteText.setText(restaurant.getWebSide());
        profileRestaurant.depositText.setText(String.valueOf(restaurant.getDeposit()));

        profileRestaurant.start();
    }

    //Display available seats
    public void displaySeats(){

        SeatsRestaurant seatsRestaurant = new SeatsRestaurant(this);
        seatsRestaurant.availableText.setText(modelRestaurant.getNumberOfSeats(restaurant.getCvr()));
        seatsRestaurant.start();
    }

    //Delete the bookings(both restaurants and customers)
    public void deleteBooking(DisplayBookings displayBookings){
       Booking booking = (Booking) displayBookings.tableView.getSelectionModel().getSelectedItem();
        showRestauarantBookings.remove(displayBookings.tableView.getSelectionModel().getSelectedIndex());
         displayBookings.tableView.setItems(showRestauarantBookings);

        System.out.println("siz");
        modelRestaurant.deleteReservation(booking.getBookingNo());


    }


    //Adjust seats available
    public void changeSeatsAvailable(SeatsRestaurant seatsRestaurant){
        this.seatsRestaurant = seatsRestaurant;

        modelRestaurant.updateAvailableSeats("restaurantStatus",restaurant.getCvr(),seatsRestaurant.availableSeatsInt);

    }

    //check how many seats are available for the time, and display if available or not or for how many people are available
    public void checkAvailableSeats(AddBookingByRestaurant addBookingByRestaurant)  {

        this.addBookingByRestaurant = addBookingByRestaurant;


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date utilDate = null;
        String enterDate = addBookingByRestaurant.bookingDate.getText().toString();
        try {
            utilDate = format.parse(enterDate);
            sq = new java.sql.Timestamp(utilDate.getTime());
            sq.setTime(sq.getTime()+60*60*1000);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(modelRestaurant.cheackPeople(restaurant.getCvr(),addBookingByRestaurant.time,sq,addBookingByRestaurant.numberOfPeopleInt)==true) {

            insertCustomerByRestaurant(addBookingByRestaurant);
          insertBookingByRestaurant(addBookingByRestaurant);
            displayBookings.tableView.setItems(modelRestaurant.getBookings(java.sql.Timestamp.valueOf(displayBookings.getCurrentTimeStamp()), restaurant.getCvr()));
            showRestauarantBookings = modelRestaurant.getBookings(java.sql.Timestamp.valueOf(displayBookings.getCurrentTimeStamp()),restaurant.getCvr());
            System.out.println("size after adding booking"+ showRestauarantBookings.size());

        }


    }








}


