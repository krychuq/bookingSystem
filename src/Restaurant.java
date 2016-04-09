import javafx.scene.image.Image;

/**
 * Created by krystian on 2015-11-14.
 */
public class Restaurant {


    int cvr;
    String companyName;
    String address;
    int phone;
    String mail;
    String password;
    String restaurantName;


    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    int deposit;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

    public String getWebSide() {
        return webSide;
    }

    public void setWebSide(String webSide) {
        this.webSide = webSide;
    }

    String webSide;

    public int getCvr() {
        return cvr;
    }

    public void setCvr(int cvr) {
        this.cvr = cvr;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Restaurant(int cvr, String restaurantName, String companyName, String address, int phone, String mail, String password, String description, String webSite, int deposit) {
        this.cvr = cvr;
        this.restaurantName = restaurantName;
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
        this.description = description;
        this.webSide = webSite;
        this.deposit = deposit;

    }



        }