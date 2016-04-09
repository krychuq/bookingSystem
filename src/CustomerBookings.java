import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Monica on 07-12-2015.
 */
public class CustomerBookings {
    private Customer customer;
    private Controller controller;
    Stage primaryStage;
    private ScrollPane scrollPane;
    private Label previousBookingsLabel;
    private Label line;
    private Label currentReservations;
    private Label title2;
    private Label title1;
    private String stringCurrent ="";
    private String stringPast ="";
    private String stringPast2 ="";

    private Label welcome;
    private Label picture;
    private Label mainLabel;

    private int currentReservationNo;
    private Button logOut;
    private Button makeNewReservation;
    private Button changeReservation;
    private Button cancel;
    private Image image;
    private Image image1;
    private  Image image2;
    private ImageView imageView = new ImageView();
    private ImageView imageView2 = new ImageView();
    private ImageView imageView1 = new ImageView();

    public CustomerBookings(Customer customer, Controller controller){
        this.controller = controller;
        this.customer = customer;
    }
    public void bookingsOfCustomer() {
        currentReservationNo = controller.totalCurrentReservations();

        //CREATING STAGE AND GRID-PANE;
        primaryStage = new Stage();
        primaryStage.setTitle("Account Information");
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        gridPane.setMaxSize(599, 680);

        //CLICK ON FACE-PICTURE TO SHOW ACCOUNT INFO
        picture = new Label();
        picture.setId("defaultAvatarLabel");
        picture.setOnMouseClicked(event -> {
            controller.showAccount(customer, this);
            primaryStage.close();

        });
        gridPane.setConstraints(picture, 1, 0, 1, 3, HPos.RIGHT, VPos.TOP);
        gridPane.setMargin(picture, new Insets(0, 30, 10, 0));

        //HELLO USERNAME LABEL
        welcome = new Label("Hi, "+customer.getName());
        welcome.setId("greetingsLabel");
        gridPane.setConstraints(welcome, 1,0,1,1,HPos.LEFT,VPos.BOTTOM);
        gridPane.setMargin(welcome, new Insets(30,0,0,45));

        //LOG OUT BUTTON
        logOut = new Button();
        logOut.setId("logOutCustomerBookings");
        logOut.setOnAction(event1 -> {
            primaryStage.close();
            controller.logOut();
        });
        gridPane.setConstraints(logOut, 1, 1, 1, 1,HPos.LEFT,VPos.TOP);
        gridPane.setMargin(logOut, new Insets(10, 0, 0, 55));

        //CURRENT RESERVATIONS ========================================================================================>

        // MAIN LABEL;
        mainLabel = new Label("You have "+ currentReservationNo +" active reservations");
        mainLabel.setId("mainLabelStyle");
        gridPane.setConstraints(mainLabel, 0, 4, 3, 1, HPos.LEFT, VPos.BOTTOM);
        gridPane.setMargin(mainLabel, new Insets(20, 0, 0, 66));
        //IMAGE
        image = new Image("pictures/backUnderReservation.png");
        imageView.setImage(image);
        gridPane.setConstraints(imageView, 0, 5, 2, 2, HPos.LEFT, VPos.CENTER);
        gridPane.setMargin(imageView, new Insets(5, 0, 25, 65));
        //LABEL CURRENT RESERVATIONS
        currentReservations=new Label();
        currentReservations.setId("currentReservations");
        controller.updateLabel(customer.getMail());
        gridPane.setConstraints(currentReservations, 0, 5, 2, 2, HPos.LEFT, VPos.CENTER);
        gridPane.setMargin(currentReservations, new Insets(5, 0, 25, 70));

        //BUTTONS======================================================================================================>

        //NEW RESERVATION BUTTON;
        makeNewReservation = new Button();
        makeNewReservation.setId("makeReservationCustomerBookings");
        makeNewReservation.setOnAction(event -> {
                primaryStage.close();

        });
        gridPane.setConstraints(makeNewReservation, 0, 8, 1, 1, HPos.RIGHT, VPos.BOTTOM);
        gridPane.setMargin(makeNewReservation, new Insets(10, 55, 0, 0));//top right bottom left

        //CHANGE RESERVATION BUTTON; ----> method to update reservation
        changeReservation = new Button();
        changeReservation.setId("changeReservationCustomerBookings");
        changeReservation.setOnAction(event -> {
            controller.changeBooking(this);
        });
        gridPane.setConstraints(changeReservation, 0, 9, 1, 1, HPos.RIGHT, VPos.TOP);
        gridPane.setMargin(changeReservation, new Insets(20, 55, 0, 0));

        //LEAVE COMMENT; ------> cancel this and go back
        cancel = new Button();
        cancel.setId("cancelButton");
        cancel.setOnAction(event -> {
           controller.cancelReservation();
        });
        gridPane.setConstraints(cancel, 0, 9, 1, 1, HPos.RIGHT, VPos.TOP);
        gridPane.setMargin(cancel, new Insets(90,55, 30, 0));

        //PAST RESERVATION OBJECTS=====================================================================================>


        //PAST-BOOKINGS LABEL
        previousBookingsLabel = new Label("Past bookings");
        previousBookingsLabel.setId("pastBookingsLabel");
        gridPane.setConstraints(previousBookingsLabel, 1, 7, 1, 1, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(previousBookingsLabel, new Insets(20, 0, 20, 0));

        //IMAGES - BACKGROUNDS - PASTBOOKINGS

        image1 = new Image("pictures/secondBackgrounds.png");
        imageView1.setImage(image1);
        image2 = new Image("pictures/secondBackgrounds.png");
        imageView2.setImage(image2);
        gridPane.setConstraints(imageView1, 1, 8, 1, 2, HPos.LEFT, VPos.CENTER);
        gridPane.setConstraints(imageView2, 1, 8, 1, 2, HPos.LEFT, VPos.CENTER);
        gridPane.setMargin(imageView1, new Insets(0,60,50,0));
        gridPane.setMargin(imageView2, new Insets(0, 60, 50, 140));

        //PAST RESERVATIONS RESTAURANTS

        Label title1 = new Label("Restaurant: ");
        title1.setId("pastReservations");//-------------------------------
        gridPane.setConstraints(title1, 1, 8, 1, 2, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(title1, new Insets(5, 60, 50, 5));
        Label pastReservations = new Label();
        pastReservations.setId("pastReservations");//-------------------------

        pastReservations.setText(controller.pastBookingsPlace(customer.getMail()));
        gridPane.setConstraints(pastReservations, 1, 8, 1, 2, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(pastReservations, new Insets(35, 60, 50, 0));

        //PAST RESERVATIONS DATES

        title2 = new Label ("Date:");
        title2.setId("pastReservations");//----------------------------------------
        gridPane.setConstraints(title2, 1, 8, 1, 2, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(title2, new Insets(5, 60, 40, 150));

        Label pastReservations2 = new Label();
        pastReservations2.setId("pastReservations");//-----------------------------------

        pastReservations2.setText(controller.pastBookingsDate(customer.getMail()));
        gridPane.setConstraints(pastReservations2, 1, 8, 1, 2, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(pastReservations2, new Insets(35, 60, 50, 145));

        //ADD ELEMENTS TO GRID=========================================================================================>
        gridPane.getChildren().addAll(picture, welcome, logOut, mainLabel, imageView, currentReservations,makeNewReservation, changeReservation, cancel, previousBookingsLabel, imageView1,imageView2,title1, title2,pastReservations,pastReservations2);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.getColumnConstraints().add(new ColumnConstraints(299));


        //SCENE AND PRIMARY STAGE======================================================================================>
        Scene scene = new Scene(gridPane,599,680);
        scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
        gridPane.setId("backgroundCustomerBookings");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    public Label getCurrentReservations(){
        return currentReservations;
    }

}
