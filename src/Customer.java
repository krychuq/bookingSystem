/**
 * Created by krystian on 2015-11-12.
 */
public class Customer {



    String name;
    String surname;
    int telephone;
    String mail;
    String user;
    String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String userName) {
        this.user = userName;
    }
    public  String getPassword(){
        return  password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Customer(String mail, String user, String name,String surname, int telephone,String password){

        this.user = user;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.mail = mail;
        this.password = password;
    }

    public Customer(String mail,String name, String surname, int telephone){
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
        this.mail = mail;
    }


}
