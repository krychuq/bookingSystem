import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by krystian on 2015-11-19.
 */
public class Booking {
    String userName;
    String restaurantName;
    Timestamp bookingDate;
    int bookingID;
    int cvr;
    String mail;
    Timestamp current;
    int numberOfPeople;
    int bookingNo;



    public int getCvr() {
        return cvr;
    }

    public void setCvr(int cvr) {
        this.cvr = cvr;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }




    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName(){
        return userName;
    }

    public int getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(int bookingNo) {
        this.bookingNo = bookingNo;
    }

    public Timestamp getCurrent() {
        return current;
    }

    public void setCurrent(Timestamp current) {
        this.current = current;
    }

    public Booking(int cvr, String userName, int numberOfPeople, Timestamp bookingDate) {
        this.cvr = cvr;
        this.mail = userName;
        this.bookingDate = bookingDate;
        this.numberOfPeople = numberOfPeople;
    }
    public Booking(int bookingID, String restaurantName, Timestamp bookingDate, int numberOfPeople){//MONI CONSTRUCTOR
        this.bookingID = bookingID;
        this.restaurantName = restaurantName;
        this.bookingDate = bookingDate;
        this.numberOfPeople = numberOfPeople;
    }
    public Booking(String restaurantName, Timestamp bookingDate){//past reservations
        this.restaurantName = restaurantName;
        this.bookingDate = bookingDate;
    }
    public Booking(int cvr,  Timestamp bookingDate,int numberOfPeople) {
        this.cvr = cvr;
        this.bookingDate = bookingDate;
        this.numberOfPeople = numberOfPeople;
    }
    public Booking(int bookingNo,  Timestamp bookingDate,String userName,int numberOfPeople) {
        this.bookingNo = bookingNo;
        this.bookingDate = bookingDate;
        this.userName = userName;
        this.numberOfPeople = numberOfPeople;
    }





}