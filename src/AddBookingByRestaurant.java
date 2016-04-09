import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by lucia on 12/7/15.
 */
public class AddBookingByRestaurant {


    public TextField customerName = new TextField();
    public  TextField customerSurname = new TextField();
    public TextField customerPhone = new TextField();
    public  TextField customerMail = new TextField();
    public TextField bookingDate = new  TextField();
    public TextField numberOfPeople = new TextField();
    int phoneNumber;
    ControllerRestaurant controllerRestaurant;
    Timestamp time;
    int numberOfPeopleInt;

    public AddBookingByRestaurant(ControllerRestaurant controllerRestaurant){

        this.controllerRestaurant = controllerRestaurant;


    }

    public void start(){

        Stage primaryStage = new Stage();
        GridPane gridPane = new GridPane();

        Label customerNameLabel = new Label("Customer name:");
        Label customerSurnameLabel = new Label("Customer surname:");
        Label customerPhoneLabel = new Label("Customer phone:");
        Label customerMailLabel = new Label("Customer mail:");
        Label bookingDateLabel = new Label("Booking date:");
        Label numberOfPeopleLabel = new Label("Number of people:");

        Button save = new Button();
        save.setText("Save");

        save.setOnAction(event -> {


          phoneNumber = Integer.parseInt(customerPhone.getText());
            time = Timestamp.valueOf(bookingDate.getText());
            numberOfPeopleInt = Integer.parseInt(numberOfPeople.getText());

            controllerRestaurant.checkAvailableSeats(this);

            primaryStage.close();
        });
        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        GridPane.setConstraints(customerNameLabel,0,0);
        GridPane.setConstraints(customerName,1,0);
        GridPane.setConstraints(customerSurnameLabel,0,1);
        GridPane.setConstraints(customerSurname,1,1);
        GridPane.setConstraints(customerMailLabel,0,2);
        GridPane.setConstraints(customerMail,1,2);
        GridPane.setConstraints(customerPhoneLabel,0,3);
        GridPane.setConstraints(customerPhone,1,3);
        GridPane.setConstraints(bookingDateLabel,0,4);
        GridPane.setConstraints(bookingDate,1,4);
        GridPane.setConstraints(numberOfPeopleLabel,0,5);
        GridPane.setConstraints(numberOfPeople,1,5);
        GridPane.setConstraints(save,1,6);

        Scene scene = new Scene(gridPane,400,250);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().addAll(this.getClass().getResource("Restaurant.css").toExternalForm());
        gridPane.setId("runRestaurant");

        gridPane.getChildren().addAll(customerNameLabel,customerName,customerSurnameLabel,customerSurname,customerMailLabel,customerMail,
                customerPhoneLabel,customerPhone,bookingDateLabel,bookingDate,numberOfPeopleLabel,numberOfPeople,save);
    }
}
