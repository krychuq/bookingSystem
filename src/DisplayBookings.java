import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lucia on 12/1/15.
 */
public class DisplayBookings {


    TableView tableView = new TableView();

    Button profile = new Button();
    Button logOut = new Button();
    Button delete = new Button("Delete");
    Button add = new Button("add");
    ControllerRestaurant controllerRestaurant;



    public DisplayBookings(ControllerRestaurant controllerRestaurant) {
        this.controllerRestaurant = controllerRestaurant;

    }


    public void start( ) {
        Stage primaryStage = new Stage();
        GridPane gridPane = new GridPane();

        Scene scene = new Scene(gridPane, 800, 400);
        showBookings();
        gridPane.getRowConstraints().add(new RowConstraints(30));
        gridPane.getRowConstraints().add(new RowConstraints(280));
        gridPane.getColumnConstraints().add(new ColumnConstraints(800));
        profile.setText("Profile");
        logOut.setText("log out");
        GridPane.setConstraints(profile, 0, 0, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(logOut, 0, 0, 1, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(tableView, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(delete,0,2,1,1,HPos.RIGHT,VPos.BOTTOM);
        GridPane.setConstraints(add,0,2,1,1,HPos.RIGHT,VPos.BOTTOM);

        gridPane.setMargin(profile, new Insets(3, 0, 0, 635));
        gridPane.setMargin(logOut, new Insets(3, 0, 0, 700));
        gridPane.setMargin(tableView, new Insets(0, 10, 10, 0));
        gridPane.setMargin(delete,new Insets(0,370,30,270));

        gridPane.setMargin(add,new Insets(0,320,30,300));


        add.setOnAction(event2 -> {
            controllerRestaurant.addBooking();
        });

        logOut.setOnAction(event1 -> {

            primaryStage.close();

        });

        profile.setOnAction(event -> {

           controllerRestaurant.profile(this);


        });
        gridPane.setGridLinesVisible(false);

        gridPane.getChildren().addAll(profile,logOut, tableView,delete,add);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().addAll(this.getClass().getResource("Restaurant.css").toExternalForm());
        gridPane.setId("runRestaurant");
        delete.setOnAction(event -> {

        controllerRestaurant.deleteBooking(this);

        });

    }

    public void showBookings() {


        controllerRestaurant.addBookingByRest();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        javafx.scene.control.TableColumn < Booking, Integer > bookingNo = new javafx.scene.control.TableColumn<>("Booking no");
        bookingNo.setCellValueFactory(new PropertyValueFactory<>("bookingNo"));
        bookingNo.setMinWidth(100);

        javafx.scene.control.TableColumn<Booking, Timestamp> bookingDate = new javafx.scene.control.TableColumn<>("Date:");
        bookingDate.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        bookingDate.setMinWidth(200);

        javafx.scene.control.TableColumn<Booking, Integer> numberOfPeople = new javafx.scene.control.TableColumn<>("Number of people:");
        numberOfPeople.setCellValueFactory(new PropertyValueFactory<>("numberOfPeople"));
        numberOfPeople.setMinWidth(100);


        javafx.scene.control.TableColumn<Customer, String> userName = new javafx.scene.control.TableColumn<>("Name:");
        userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userName.setMinWidth(150);


        tableView.getColumns().addAll(bookingNo, bookingDate, userName, numberOfPeople);

        tableView.setItems(controllerRestaurant.showRestauarantBookings);

    }

    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }






}