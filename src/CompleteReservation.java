import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by krystian on 2015-11-19.
 */
public class CompleteReservation {
    BorderPane borderPane;
    VBox vBox;
    VBox vBox1;
    HBox hBox;
    GridPane gridPane1;
    StackPane bottomStackPane;
    StackPane middleStackPane;
    GridPane gridPane;
    HBox titleLabelHBox = new HBox();
    Label hoverLabels = new Label();
    Label numberOfPeople = new Label();
    Label dateButton = new Label();
    Label hourButton = new Label();
    Label restaurantName = new Label();
    String numberOfPeople1;
    String dateButton1;
    String hourButton1;
    String restaurantName1;
    Label logIn;
    DatabaseCustomer databaseCustomer;
    Booking booking;
    TextField nameTextField = new TextField();
    TextField surnameTextField = new TextField();
    AttributeTextField telephoneTextField = new AttributeTextField();
    TextField mailTextField = new TextField();
    Image image = new Image("pictures/boxUnderSignup.png");
    javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
    Controller controller;
    Image image1 = new Image("pictures/shape.png");
    javafx.scene.image.ImageView imageView1 = new javafx.scene.image.ImageView(image1);
    Image pic = new Image("pictures/pic.jpg");
    javafx.scene.image.ImageView pictureButton = new javafx.scene.image.ImageView(pic);
    Image picBig = new Image("pictures/picBig.jpg");
    Label distriptionLabel;
    Label wrong = new Label();
    int deposit;
    Label depositLabel = new Label();
    Label checkMore = new Label("[check more]");
    Customer customer;
    Label member;
    Label signingIn;
    Stage window;

    public CompleteReservation(String numberOfPeople1, String dateButton1, String hourButton1,String restaurantName1,Booking booking,Controller controller){
        this.numberOfPeople1 =numberOfPeople1;
        this.dateButton1 = dateButton1;
        this.hourButton1 = hourButton1;
        this.restaurantName1 = restaurantName1;
        this.booking = booking;
        this.controller = controller;
    }
    public CompleteReservation(String numberOfPeople1, String dateButton1, String hourButton1,String restaurantName1,Booking booking,String name,
                               String surname, String telephone, String mail,Controller controller) {
        this.numberOfPeople1 = numberOfPeople1;
        this.dateButton1 = dateButton1;
        this.hourButton1 = hourButton1;
        this.restaurantName1 = restaurantName1;
        this.booking = booking;
        nameTextField.setText(name);
        surnameTextField.setText(surname);
        telephoneTextField.setText(telephone);
        mailTextField.setText(mail);
        this.controller = controller;

    }
    public CompleteReservation(String numberOfPeople1, String dateButton1, String hourButton1,String restaurantName1,Booking booking,String name,
                               String surname, String telephone, String mail,Controller controller,int deposit) {
        this.numberOfPeople1 = numberOfPeople1;
        this.dateButton1 = dateButton1;
        this.hourButton1 = hourButton1;
        this.restaurantName1 = restaurantName1;
        this.booking = booking;
        nameTextField.setText(name);
        surnameTextField.setText(surname);
        telephoneTextField.setText(telephone);
        mailTextField.setText(mail);
        this.controller = controller;
        this.deposit = deposit;

    }



    public void showCompleteReservation(DatabaseCustomer databaseCustomer) throws SQLException {
        this.databaseCustomer = databaseCustomer;
        customer = controller.getCustomer();

         window = new Stage();
        vBox = new VBox();
        vBox1 = new VBox();
        hBox = new HBox();
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        gridPane.setVgap(15);

        borderPane = new BorderPane();
        borderPane.getStyleClass().add("pane");

        gridPane1=new GridPane();
        bottomStackPane = new StackPane();
        middleStackPane = new StackPane();

        //numberOfPeople.setId("buttonStyle");
        numberOfPeople.setText(numberOfPeople1);
        dateButton.setText(dateButton1);
        hourButton.setText(hourButton1);
        restaurantName.setText(restaurantName1);

        //Reserve Button with Position in GUI
        Button reserveButton = new Button();
        reserveButton.setId("buttonCRS");
        bottomStackPane.setAlignment(reserveButton, Pos.TOP_CENTER);
        bottomStackPane.setMargin(reserveButton, new Insets(30,0,0,0));

        logIn = new Label("");

        //TITLE LABEL
        Label labelTitle = new Label("COMPLETE YOUR RESERVATION");
        distriptionLabel = new Label();
        distriptionLabel.setMaxWidth(320);
        distriptionLabel.setMaxHeight(70);
        distriptionLabel.setWrapText(true);
        distriptionLabel.setText(controller.getDatabaseCustomer().getDescriptionOfRestaurant(booking.getCvr()));
        labelTitle.setId("completeReservationLabelTitle");
        labelTitle.setUnderline(true);
         member = new Label("Are you already member? ");
         signingIn = new Label("SIGN IN");

        member.setId("member");
        signingIn.setId("SignInCRS");
        pictureButton.setOnMouseClicked(event3 -> {

            controller.displayRestaurantWindow(booking.getCvr());

        });
        pictureButton.setOnMouseEntered(event2 -> {
            pictureButton.setImage(picBig);


        });
        pictureButton.setOnMouseExited(event2 -> {
            pictureButton.setImage(pic);
        });

        gridPane1.getRowConstraints().add(new RowConstraints(30));
        gridPane1.getRowConstraints().add(new RowConstraints(45));
        gridPane1.getRowConstraints().add(new RowConstraints(110));
        gridPane1.getRowConstraints().add(new RowConstraints(50));
        GridPane.setConstraints(labelTitle, 0, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(hoverLabels, 0, 1, 1, 1, HPos.CENTER, VPos.BOTTOM);
        gridPane1.setMargin(hoverLabels, new Insets(10, 0, 0, 0));
        GridPane.setConstraints(imageView1, 0, 2, 1, 1, HPos.CENTER, VPos.BOTTOM);
        GridPane.setConstraints(pictureButton, 0, 2, 1, 2);
        gridPane1.setMargin(pictureButton, new Insets(0, 0, 30, 58));

        GridPane.setConstraints(distriptionLabel, 0, 2, 1, 1, HPos.LEFT, VPos.TOP);
        gridPane1.setMargin(distriptionLabel, new Insets(20, 0, 0, 168));
        checkMore.setId("distriptionLabel");
        checkMore.setOnMouseClicked(event3 -> {
            controller.displayRestaurantWindow(booking.getCvr());

        });
        GridPane.setConstraints(checkMore, 0, 2, 1, 1, HPos.CENTER, VPos.BOTTOM);
        gridPane1.setMargin(checkMore, new Insets(0, 0, 5, 0));



        GridPane.setConstraints(member, 0, 3);
        GridPane.setConstraints(signingIn, 0, 3);
        gridPane1.setMargin(signingIn, new Insets(0, 0, 0, 300));
        gridPane1.setMargin(member, new Insets(0, 0, 0, 150));



        gridPane1.getColumnConstraints().add(new ColumnConstraints(570));
        gridPane1.setAlignment(Pos.CENTER);
        gridPane1.getChildren().addAll(labelTitle, hoverLabels, imageView1, pictureButton, distriptionLabel,member, signingIn,checkMore);
        gridPane1.setGridLinesVisible(false);

        //HOVER LABELS AND BACKGROUND
        hoverLabels.setText("      " + numberOfPeople.getText() + "      " + dateButton.getText() + "      " + hourButton.getText() + "      " + restaurantName.getText() + "     ");
        hoverLabels.setId("hoverLabelsCompleteReservation");
        hoverLabels.setMinSize(468, 43);


        //STUFF THAT SHOULD BE IN THE MIDDLE OF THE WINDOW ---- IN GRIDPANE --- BORDERPANE_CENTER.
        Label signUp = new Label("SIGN UP");

        Label name = new Label("Name:");
        name.setId("labelSignUpCRS");
        nameTextField.setId("textFieldInputCRS");

        Label surname = new Label("Surname:");
        surname.setId("labelSignUpCRS");
        surnameTextField.setId("textFieldInputCRS");

        Label telephone = new Label("Phone number:");
        telephone.setId("labelSignUpCRS");
        telephoneTextField.setId("textFieldInputCRS");

        Label mail = new Label("Mail");
        mail.setId("labelSignUpCRS");
        mailTextField.setId("textFieldInputCRS");

        reserveButton.setOnAction(event2 -> {
            controller.makeBooking(this);
        });


        signUp.setId("SignInCRS");
        signUp.setOnMouseClicked(event1 -> {
            controller.signUp();


        });

        signingIn.setId("SignInCRS");
        signingIn.setOnMouseClicked(event -> {

            controller.signIn(this);

        });


      gridPane.setGridLinesVisible(false);
      //  GridPane.setConstraints(member, 0, 0);
        //GridPane.setMargin(member, new Insets(0, 0, 0, 15));
        gridPane.getColumnConstraints().add(new ColumnConstraints(170));
        gridPane.getRowConstraints().add(new RowConstraints(35));
        gridPane.getRowConstraints().add(new RowConstraints(35));
        gridPane.getRowConstraints().add(new RowConstraints(35));
        gridPane.getRowConstraints().add(new RowConstraints(35));
        gridPane.getRowConstraints().add(new RowConstraints(35));

       // GridPane.setConstraints(signingIn, 1, 0);
        GridPane.setConstraints(imageView, 0, 0, 2, 4);
        GridPane.setMargin(imageView, new Insets(70, 0, 0, 0));


        GridPane.setConstraints(signUp, 0, 0);
        GridPane.setMargin(signUp, new Insets(15, 0, 0, 15));
        depositLabel.setId("totalFee");
                GridPane.setConstraints(depositLabel, 1, 0);
        GridPane.setMargin(depositLabel, new Insets(15, 0, 0, 15));


        GridPane.setConstraints(name, 0, 1);
        GridPane.setMargin(name, new Insets(0, 0, 0, 15));

        GridPane.setConstraints(nameTextField, 1, 1);// column, row, columnSpan, rowSpan.

        GridPane.setConstraints(surname, 0, 2);
        GridPane.setMargin(surname, new Insets(0, 0, 0, 15));

        GridPane.setConstraints(surnameTextField, 1, 2);

        GridPane.setConstraints(telephone, 0, 3);

        GridPane.setMargin(telephone, new Insets(0, 0, 0, 15));

        GridPane.setConstraints(telephoneTextField, 1, 3);

        GridPane.setConstraints(mail, 0, 4);
        GridPane.setMargin(mail, new Insets(0, 0, 0, 15));

        GridPane.setConstraints(mailTextField, 1, 4, 1, 1);

        GridPane.setMargin(mail, new Insets(0, 0, 0, 15));


        gridPane.getChildren().addAll(imageView, signUp, name, nameTextField, surname, surnameTextField, telephone, telephoneTextField,
                mail, mailTextField, reserveButton, logIn,depositLabel);
        gridPane.setMinSize(100, 100);


        //DEFINE BORDERPANE_TOP STACKPANE;
     //   stackpane.setMinSize(100, 100);

        //DEFINE BORDERPANE_BOTTOM STACKPANE = STACKPANEBOTTOM;
        bottomStackPane.getChildren().add(reserveButton);
        bottomStackPane.setMinSize(100, 100);

        //DEFINE MIDDLE BORDERPANE


        //ALLOCATE OBJECTS IN BORDERPANE
        borderPane.setTop(gridPane1);
        borderPane.setCenter(gridPane);
        borderPane.setMargin(gridPane, new Insets(0, 0, 0, 70));//top, right, bottom, left
        borderPane.setBottom(bottomStackPane);

        //just fillings to define margins of borderpane! (they are empty as they should be smile emoticon. )



        //the window and the scene
        window.setTitle("Complete Reservation");
        Scene scene = new Scene(borderPane,600,570);
        scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
        window.setScene(scene);
        window.show();


    }
    public void setLogIn(String login){
        logIn.setText(login);
        System.out.println(logIn.getText());
    }

    public void insertTextFields(String name,String surname,String mail,String telefon){
        nameTextField.setText(name);
        surnameTextField.setText(surname);
        mailTextField.setText(mail);
        telephoneTextField.setText(telefon);

    }
    public void setDepositLabel(String s){
        depositLabel.setText(s);

    }
    public void setCustomer(Customer customer){
        this.customer=customer;

    }



}