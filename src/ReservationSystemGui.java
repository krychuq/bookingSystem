import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


/**
 * Created by lucia on 11/16/15.
 */
public class ReservationSystemGui extends Application{

    public DatePicker dateSelector;
    public final String pattern = "dd-MM-yyyy";
    Controller controller = new Controller(this);
    Label welcomUser = new Label("");
    Customer customer;
    public ComboBox<String> numberOfPeople = new ComboBox();
    public ComboBox<String> hour = new ComboBox<>();
    public ComboBox<String> restaurants = new ComboBox<>();
    String restaurantName;
    java.sql.Timestamp sq = null;
    public int cvr;
    int numberOfPeople1;
    Label logOut = new Label("log out");
    Label singIn = new Label("sign in");
    Label wrongLabel = new Label("");
    Label singUp;
    Label text2;
    Label text3;
    Label text4;
    Label text;

    public static void main(String[] args) {

        Locale.setDefault(Locale.ENGLISH);
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Gui elements
        GridPane gridPane = new GridPane();
        //scene and css for it
        Scene scene = new Scene(gridPane, 734, 406);
        scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
        gridPane.setId("reservationSystemGui");
        Image logo = new Image("pictures/logo.png");
        ImageView logoView = new ImageView(logo);
        wrongLabel.setTextFill(Color.RED);
        singUp = new Label("sign up");
        text2 = new Label("Welcome to");
        text3 = new Label("Table4You");
        text4 = new Label("|");
        text = new Label("\n" +"Here you can book a tabel in desired restaurant. You won't wait in line anymore.\n" +
                "Choose the date and hour below and click find a tabel. It is that easy!");
        //id for layouts
        singIn.setId("sign");
        logOut.setId("sign");
        singUp.setId("sign");
        text2.setId("label");
        text3.setId("label1");
        text.setId("label");
        //layout
        GridPane.setConstraints(logoView, 0, 0, 1, 1, HPos.LEFT, VPos.TOP);
        gridPane.getRowConstraints().add(new RowConstraints(230));
        gridPane.getColumnConstraints().add(new ColumnConstraints(173));
        gridPane.getColumnConstraints().add(new ColumnConstraints(109));
        gridPane.getColumnConstraints().add(new ColumnConstraints(109));
        gridPane.getColumnConstraints().add(new ColumnConstraints(145));
        gridPane.getColumnConstraints().add(new ColumnConstraints(200));
        gridPane.setMargin(logoView, new Insets(17, 0, 0, 45));
        gridPane.setConstraints(singUp, 4, 0, 1, 1, HPos.LEFT, VPos.TOP);
        gridPane.setConstraints(singIn, 4, 0, 1, 1, HPos.RIGHT, VPos.TOP);
        gridPane.setConstraints(logOut, 4, 0, 1, 1, HPos.RIGHT, VPos.TOP);
        gridPane.setConstraints(text4, 4, 0, 1, 1, HPos.RIGHT, VPos.TOP);
        gridPane.setMargin(singUp, new Insets(60, 0, 0, 45));
        gridPane.setMargin(singIn, new Insets(60, 45, 0, 0));
        gridPane.setMargin(logOut, new Insets(60, 45, 0, 0));
        gridPane.setMargin(text4, new Insets(60, 96, 0, 0));
        GridPane.setConstraints(welcomUser, 3, 0, 2, 1, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(welcomUser, new Insets(60, 0, 0, 0));
        GridPane.setConstraints(text, 0, 0, 1, 1, HPos.LEFT, VPos.BOTTOM);
        GridPane.setConstraints(text2, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(text3, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
        gridPane.setMargin(text3, new Insets(30, 0, 0, 140));
        gridPane.setMargin(text2, new Insets(30, 0, 0, 45));
        gridPane.setMargin(text, new Insets(0, 0, 40, 45));
        gridPane.setColumnSpan(text, 5);
        gridPane.setColumnSpan(text3, 2);

        singUp.setOnMouseClicked(event1 -> {
            controller.signUp();
        });


        //comboBox number of People set Itemns and so on
        numberOfPeople.setItems(controller.getDatabaseCustomer().numberOfpeopleComboBox());
        numberOfPeople.setValue("2 people");
        numberOfPeople.setVisibleRowCount(5);

        //DatePicker
        dateSelector = new DatePicker();
        dateSelector.setValue(LocalDate.now());
        dateSelector.setMaxSize(200, 200);
        dateSelector.setId("buttonStyle1");
        dateSelector.setConverter(controller.datePickerFormatter());
        dateSelector.setPromptText(pattern.toLowerCase());
        //hour comboBox
        hour.setItems(controller.getDatabaseCustomer().hourInCombobox());
        hour.setVisibleRowCount(5);
        hour.setValue("17:00");
        hour.setId("hour-box-base");
        //restaurantComboBox
        restaurants.setItems(controller.getDatabaseCustomer().getRetaurants());
        restaurants.setItems(controller.getDatabaseCustomer().getRetaurants());
        restaurants.setPromptText("restaurant");
        //findTable Button
        Button findTable = new Button();
        findTable.setId("buttonStyle5");

        findTable.setOnAction(event1 -> {

            controller.displaySearchedFromReservation(this, customer);

        });


        GridPane.setConstraints(numberOfPeople, 0, 1, 1, 1, HPos.RIGHT, VPos.BOTTOM);
        GridPane.setConstraints(wrongLabel, 0, 2, 5, 1, HPos.LEFT, VPos.BOTTOM);
        gridPane.setMargin(wrongLabel, new Insets(10, 0, 0, 45));


        gridPane.setMargin(numberOfPeople, new Insets(0,0,0,45));
        GridPane.setConstraints(dateSelector, 1, 1, 1, 1, HPos.RIGHT, VPos.BOTTOM);
        GridPane.setConstraints(hour, 2, 1, 1, 1, HPos.RIGHT, VPos.BOTTOM);
        GridPane.setConstraints(restaurants,3,1,1,1,HPos.RIGHT,VPos.BOTTOM);
        GridPane.setConstraints(findTable,4,1,1,1, HPos.RIGHT, VPos.BOTTOM);
        gridPane.setMargin(findTable,new Insets(0,45,0,0));
        logOut.setVisible(false);


        gridPane.setGridLinesVisible(false);

        welcomUser.setOnMouseClicked(event1 -> {
            controller.showCustomerAccountInfo(customer);
        });

        singIn.setOnMouseClicked(event -> {
            controller.startSignIn(this);
            logOut.setOnMouseClicked(event2 -> {
               controller.logOut();
            });
        });
        gridPane.getChildren().addAll(logoView,numberOfPeople,dateSelector,hour,restaurants,findTable, singUp, singIn,
                text,text2,text3,text4,welcomUser, logOut, wrongLabel);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public void setWelcomUser(String string){
        welcomUser.setText(string);
    }
    public void changeToVisible(Label label1){

        label1.setVisible(true);

    }
    public void changeToInvisible(Label label2) {

        label2.setVisible(false);
        label2.setDisable(true);


    }
    public void setWrongLabel(String s){
        wrongLabel.setText(s);

    }


}