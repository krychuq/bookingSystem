import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by lucia on 11/26/15.
 */
public class Controller {


    ReservationSystemGui reservationSystemGui;
    CompleteReservation completeReservation;
    ChangeReservation changeReservation;
    Customer customer;
    Booking booking;
    //SignUpCustomer signUpCustomer;
    SignIn signIn;
    int cvr;
    Conformition conformition = new Conformition();
    int deposit=0;

    private final String pattern = "dd-MM-yyyy";
    private int bookingId;
    private Timestamp desiredDayTime;
    private int numberOfPeople1;
    public static boolean input;
    CancelReservation cancelReservation;
    private int numberOfPastBookings;
    private int currentReservationNo;
    DatabaseCustomer databaseCustomer = new DatabaseCustomer();
   CustomerBookings customerBookings = null;
    public Controller(){
    }

    public  Controller(ReservationSystemGui reservationSystemGui){
        this.reservationSystemGui = reservationSystemGui;
        databaseCustomer.conectToDb();
    }
    public  Controller(ChangeReservation changeReservation){
        this.changeReservation = changeReservation;
        databaseCustomer.conectToDb();
    }
    public void displayConmformition(int number,String name) {
        if(deposit>0) {
            paymentWindow paymentWindow = new paymentWindow(number, name);
            paymentWindow.displayPayment(deposit);
        }else{
            conformition.displayConformition(number,name);

        }

    }
    //CUSTOMER BOOKINGS================================================================================================>

    public void changeBooking(CustomerBookings object){
        changeReservation = new ChangeReservation(this, customer, object);
        changeReservation.changeReservationMethod();
    }
    public void cancelReservation(){
        cancelReservation = new CancelReservation(this, customer);
        cancelReservation.cancelReservationWindow();
    }
    //current bookings
    public int totalCurrentReservations(){
        currentReservationNo=databaseCustomer.getCustomerCurrentBookings(getCurrentTimeStamp(), customer.getMail()).size();
        return currentReservationNo;
    }
    public void updateLabel(String mail){
        ArrayList<Booking> currentBookingsArray = new ArrayList<>();
        for(int i =0;i< currentReservationNo;i++){
            currentBookingsArray.addAll(databaseCustomer.getCustomerCurrentBookings(getCurrentTimeStamp(), mail));
        }
        String stringCurrent="";
        for(int i =0; i<currentReservationNo;i++) {
            stringCurrent+=("Reservation with number: "+currentBookingsArray.get(i).getBookingID()+
                    " at "+currentBookingsArray.get(i).getRestaurantName()+", "+currentBookingsArray.get(i).getBookingDate()+
                    " for "+currentBookingsArray.get(i).getNumberOfPeople()+" guests\n");
        }
        customerBookings.getCurrentReservations().setText(stringCurrent);
    }

    public String pastBookingsPlace(String mail){
        numberOfPastBookings = databaseCustomer.getCustomerPastBookings(getCurrentTimeStamp(), customer.getMail()).size();
        String stringPast = "";
        ArrayList<Booking> pastBookings = new ArrayList<>();
        for(int i =0;i< numberOfPastBookings;i++){
            pastBookings.addAll(databaseCustomer.getCustomerPastBookings(getCurrentTimeStamp(),mail));
        }
        for(int i =0; i<numberOfPastBookings;i++) {
            stringPast+=(" "+pastBookings.get(i).getRestaurantName()+"\n");
        }
        return stringPast;
    }
    public String pastBookingsDate(String mail){
        String stringPast = "";
        ArrayList<Booking> pastBookings = new ArrayList<>();
        for(int i =0;i< numberOfPastBookings;i++){
            pastBookings.addAll(databaseCustomer.getCustomerPastBookings(getCurrentTimeStamp(),mail));
        }
        for (int i = 0; i < numberOfPastBookings; i++) {
            java.util.Date date = new java.sql.Date(pastBookings.get(i).getBookingDate().getTime());
            stringPast+=(" " + date + "\n");
        }
        return stringPast;
    }

    //=================================================================================================================.
    //CHANGE RESERVATION==============================================================================================>
    public Timestamp returnBookingTimestamp(String string){
        java.sql.Timestamp sq = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date utilDate = format.parse(string);
            sq = new java.sql.Timestamp(utilDate.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Couldn't get booking date!");
        }
        return sq;
    }

    /*   public boolean checkBookingId(Customer customer, String bookingId){
           if (bookingId != null) {
               for (int i = 0; i < currentNoReservations(customer.getMail()); i++) {
                   if (bookingId!= String.valueOf(currentBookingsArrayList(customer.getMail()).get(i).getBookingID())) {
                       return false;
                   }
               }
           }
           return true;
       }*/
    public java.sql.Timestamp getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        java.util.Date now = new java.util.Date();
        String strDate = sdfDate.format(now);
        return java.sql.Timestamp.valueOf(strDate);
    }
    public int currentNoReservations(String mail){
        int currentReservationNo = databaseCustomer.getCustomerCurrentBookings(getCurrentTimeStamp(), mail).size();
        return currentReservationNo;
    }
    /*public ArrayList<Booking> currentBookingsArrayList(String mail){
        ArrayList<Booking> currentBookings = new ArrayList<>();

        for (int i = 0; i < currentNoReservations(mail); i++) {
            currentBookings.addAll(databaseCustomer.getCustomerCurrentBookings(getCurrentTimeStamp(), customer.getMail()));
        }
        return currentBookings;
    }*/

    public boolean checkReservationChanges(ChangeReservation changeReservation, Customer customer){
        this.customer =customer;
        this.changeReservation = changeReservation;

        if(changeReservation.idBox.getSelectionModel().getSelectedItem().equals("Select")) {
            changeReservation.setWrongLabel("Please select a booking number!");
        }else{
            bookingId = Integer.parseInt(changeReservation.idBox.getSelectionModel().getSelectedItem());
            numberOfPeople1 = Integer.parseInt(changeReservation.numberOfPeople.getSelectionModel().getSelectedItem().substring(0, 1));
            //--time for newBooking --\\
            String bookingTimeString = changeReservation.dateSelector.getValue().toString() +
                    " " + changeReservation.hour.getSelectionModel().getSelectedItem() + ":" + "00.0";
            desiredDayTime = Timestamp.valueOf(bookingTimeString);
            //time format
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //booking time
            Timestamp bookingTime = null;
            //booking time +1 hour
            Timestamp bookingTimeHour = null;
            try {
                java.util.Date utilDate = format.parse(bookingTimeString);
                bookingTime = new Timestamp(utilDate.getTime());
                bookingTimeHour = bookingTime;
                //add one hour
                bookingTimeHour.setTime(bookingTime.getTime() + 60 * 60 * 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //CHECK IF INPUT FROM USER ACTUALLY ADDS CHANGES TO THE CURRENT BOOKING
            Booking b = databaseCustomer.getOneCurrentBooking(bookingId);
            if (b.getNumberOfPeople() == numberOfPeople1 && b.getBookingDate().equals(desiredDayTime)) { //ONLY PRIMITIVES GET =, THE REST GET .equals!!!
                changeReservation.setWrongLabel("Please input new values if you want to change this reservation!");
                return false;
            }

            //CHECK IF THERE ARE AVAILABLE SEATS FOR THE SELECTED NEW RESERVATION
            if (databaseCustomer.cheackPeople(databaseCustomer.fetchRestaurantCVR(bookingId), bookingTime, bookingTimeHour, numberOfPeople1)) {
                changeReservation.setWrongLabel("");
                return true;
            } else if (databaseCustomer.getAvailableseats() == 0) {
                changeReservation.setWrongLabel("No seats available!");
            } else if (databaseCustomer.getAvailableseats() < numberOfPeople1) {
                changeReservation.setWrongLabel("Only " + databaseCustomer.getAvailableseats() + " available!");
            }
        }
        return false;
    }
    public void implementingUpdates(boolean answer){
        if(answer){
            databaseCustomer.updateCustomerBooking(bookingId, desiredDayTime, numberOfPeople1);
        }
    }

    public StringConverter datePickerFormatter() {
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return " ";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        return converter;
    }
    //==================================================================================================================
    //AFTER CLICKING ON USERNAME LABEL IN RSGUI;
    public void showCustomerAccountInfo(Customer customer){ //called in Reservation system gui
        if(reservationSystemGui.welcomUser.getText()!= null) {
            customerBookings = new CustomerBookings(customer,this);
            customerBookings.bookingsOfCustomer();
        }
    }
    //
    public void showAccount(Customer customer, CustomerBookings customerBookings){
        this.customerBookings = customerBookings;
        SignUpCustomer signUpCustomer = new SignUpCustomer(this, customer,customerBookings, reservationSystemGui);
        signUpCustomer.signUp();
    }
    public void showCustomerBookings(Customer customer){
        customerBookings = new CustomerBookings(customer, this);
        customerBookings.bookingsOfCustomer();

    }
    public void deleteReservation(int bookingId){
        databaseCustomer.deleteBooking(bookingId);
        if(currentReservationNo>0){
            currentReservationNo= currentReservationNo-1;
        }

        //fix it
    }



    public void signUp(){

        SignUpCustomer signUpCustomer = new SignUpCustomer(this);



        signUpCustomer.signUp();

    }

    public DatabaseCustomer getDatabaseCustomer(){
        return databaseCustomer;
    }
    public void saveUser(SignUpCustomer signUpCustomer){

    }

    public void logIn(SignIn signIn,CompleteReservation completeReservation){
        this.signIn = signIn;
        this.completeReservation = completeReservation;
        customer = databaseCustomer.checkLoginAndPassword(signIn.nameInput.getText(), signIn.passwordInput.getText());
        this.reservationSystemGui = reservationSystemGui;
        if (customer != null) {
            System.out.println("login succesed");
            signIn.wrongLabel.setText("");
            signIn.window.close();
            reservationSystemGui.setCustomer(customer);
            reservationSystemGui.setWelcomUser("Welcome " + customer.getName() + " " + customer.getSurname());
            reservationSystemGui.changeToInvisible(reservationSystemGui.singIn);
            reservationSystemGui.singUp.setDisable(true);
            reservationSystemGui.changeToInvisible(reservationSystemGui.singUp);
            reservationSystemGui.changeToInvisible(reservationSystemGui.text4);
            reservationSystemGui.changeToVisible(reservationSystemGui.logOut);
            if(completeReservation!=null){
                String telephone = Integer.toString(customer.getTelephone());

                completeReservation.nameTextField.setText(customer.getName());
                completeReservation.surnameTextField.setText(customer.getSurname());
                completeReservation.telephoneTextField.setText(telephone);
                completeReservation.mailTextField.setText(customer.getMail());
                completeReservation.member.setText("");
                completeReservation.signingIn.setText("");
                completeReservation.mailTextField.setDisable(true);
                completeReservation.surnameTextField.setDisable(true);
                completeReservation.nameTextField.setDisable(true);
                completeReservation.telephoneTextField.setDisable(true);
            }


        } else {
            signIn.wrongLabel.setText("wrong user name or password");
            signIn.wrongLabel.setTextFill(Color.RED);

            System.out.println("wrong user name or password");
        }

    }



    public void startSignIn(ReservationSystemGui reservationSystemGui){

        SignIn signIn = new SignIn(this);


        signIn.start();
    }
    public void signIn(CompleteReservation completeReservation){
        SignIn signIn = new SignIn(completeReservation,this);

        signIn.start();
    }

    public void setCompleteReservationWindow(int cvr, Timestamp timestamp){
        String numberOfPeople = reservationSystemGui.numberOfPeople.getSelectionModel().getSelectedItem();
        int numberOfPeopleInteger = 1;
        numberOfPeopleInteger +=reservationSystemGui.numberOfPeople.getSelectionModel().getSelectedIndex();
        String date = reservationSystemGui.dateSelector.getValue().toString();
        String hour =  reservationSystemGui.hour.getSelectionModel().getSelectedItem();
        String restaurantName = reservationSystemGui.restaurants.getSelectionModel().getSelectedItem();

        booking = new Booking(cvr, timestamp, numberOfPeopleInteger);
        deposit = databaseCustomer.getDeposit(booking.getCvr());


        if(customer!=null) {
            String customerName = customer.getName();
            String customerSurname = customer.getSurname();
            String telephone = Integer.toString(customer.getTelephone());
            String customerMail = customer.getMail();

            CompleteReservation completeReservation = new CompleteReservation(numberOfPeople,
                    date, hour, restaurantName, booking, customerName,
                    customerSurname, telephone, customerMail, this);

            if (deposit > 0)
                completeReservation.setDepositLabel("This restaurant requires deposit: " + deposit + " dkk");
            try {
                completeReservation.showCompleteReservation(databaseCustomer);
                completeReservation.member.setText("");
                completeReservation.signingIn.setText("");
                completeReservation.mailTextField.setDisable(true);
                completeReservation.surnameTextField.setDisable(true);
                completeReservation.nameTextField.setDisable(true);
                completeReservation.telephoneTextField.setDisable(true);
                System.out.println(deposit);
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }else{

            CompleteReservation completeReservation = new CompleteReservation(numberOfPeople,
                    date, hour, restaurantName, booking,this);
            if (deposit > 0)
                completeReservation.setDepositLabel("This restaurant requires deposit: " + deposit + " dkk");
            try {
                completeReservation.showCompleteReservation(databaseCustomer);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }


    public void displaySearchedFromReservation(ReservationSystemGui reservationSystemGui, Customer customer){

        this.customer =customer;
        this.reservationSystemGui =reservationSystemGui;
        String restaurantName = reservationSystemGui.restaurants.getSelectionModel().getSelectedItem();
        int numberOfPeople1 = 1;
        numberOfPeople1 += reservationSystemGui.numberOfPeople.getSelectionModel().getSelectedIndex();
        cvr = getDatabaseCustomer().getRestaurant(restaurantName);
        //--time for booking --\\
        String bookingTimeString = reservationSystemGui.dateSelector.getValue().toString() + " " + reservationSystemGui.hour.getSelectionModel().getSelectedItem() + ":" + "00";
        //time format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //booking time
        Timestamp bookingTime = null;
        //booking time +1 hour
        Timestamp bookingTimeHour = null;
        try {
            java.util.Date utilDate = format.parse(bookingTimeString);
            bookingTime = new Timestamp(utilDate.getTime());
            bookingTimeHour= bookingTime;
            //add one hour
            bookingTimeHour.setTime(bookingTime.getTime()+60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(restaurantName!=null) {

            //check if there is enough place
            if (databaseCustomer.cheackPeople(cvr, bookingTime, bookingTimeHour, numberOfPeople1) == true) {
                reservationSystemGui.setWrongLabel("");
                setCompleteReservationWindow(cvr,bookingTime);
            } else {
                reservationSystemGui.setWrongLabel("Unfortunately you can't make reservation for  " + reservationSystemGui.numberOfPeople.getSelectionModel().getSelectedItem() + ". For" +
                        " this hour we have " + databaseCustomer.getAvailableseats() + " available seats");
            }

        }else {
            reservationSystemGui.setWrongLabel("Please specify restaurant");
        }
    }





    public void displayRestaurantWindow(int cvr){
        this.cvr = cvr;
        RestaurantWindow restaurantWindow = new RestaurantWindow(this,cvr);
        restaurantWindow.displayRestaurantWindow();
    }

    public Customer getCustomer(){
        return customer;
    }

    public void logOut(){
        reservationSystemGui.setWelcomUser(" ");
        reservationSystemGui.singIn.setVisible(true);
        reservationSystemGui.singIn.setVisible(true);
        reservationSystemGui.text4.setVisible(true);
        reservationSystemGui.singIn.setDisable(false);
        reservationSystemGui.singUp.setDisable(false);
        reservationSystemGui.singUp.setVisible(true);

        reservationSystemGui.logOut.setVisible(false);
        reservationSystemGui.customer = null;
    }

    public void makeBooking(CompleteReservation completeReservation) {
        if ((completeReservation.mailTextField.getText().equals("")) || (completeReservation.nameTextField.getText().equals("")) || (completeReservation.surnameTextField.getText().equals("")) ||
                (completeReservation.telephoneTextField.getText().equals(""))) {
            System.out.println("Please insert data into fields!");
        } else {
            int telephonNo = Integer.parseInt(completeReservation.telephoneTextField.getText());


            if (customer != null) {
                booking.setMail(customer.getMail());


            } else {
                booking.setMail(completeReservation.mailTextField.getText());
                int telephon = Integer.parseInt(completeReservation.telephoneTextField.getText());
                customer = new Customer(completeReservation.mailTextField.getText(), completeReservation.nameTextField.getText(), completeReservation.surnameTextField.getText(), telephon);
                databaseCustomer.insertCustomerWithoutReservation(customer);

            }
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
            System.out.println(currentTimestamp);
            booking.setCurrent(currentTimestamp);
            databaseCustomer.insertBooking(booking);
            completeReservation.controller.displayConmformition(databaseCustomer.getBookingNumber(booking.getCurrent()), completeReservation.nameTextField.getText());
            completeReservation.window.close();

        }
    }



}