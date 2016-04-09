import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class SignIn  {
    TableView tableView;
    Stage window;
    Customer customer;
    Label wrongLabel = new Label("");
    CompleteReservation completeReservation;
    ReservationSystemGui reservationSystemGui;
    Controller controller;
    SignUpCustomer signUpCustomer = new SignUpCustomer(controller);
    Label titleLabel;

    TextField nameInput = new TextField();
    PasswordField passwordInput = new PasswordField();


    public SignIn(Controller controller){


        this.controller = controller;
    }


    public SignIn(CompleteReservation completeReservation, Controller controller){
        this.completeReservation = completeReservation;
        this.controller = controller;
    }
    public SignIn(){

    }
    public void start() {

        Stage primaryStage = new Stage();
        window = primaryStage;
        window.setTitle("Sign In");

        primaryStage.setOnCloseRequest(event1 -> {

            event1.consume();
            boolean result = ConfimBox.display("Warning", "Are you sure you want to close?");
            if (result) {
                primaryStage.close();
            }

        });

        GridPane grid = new GridPane();
        grid.setGridLinesVisible(false);
        grid.setId("reservationSystem");

        grid.setVgap(5);
        grid.setHgap(5);


        //TITLE LABEL
        titleLabel = new Label("Type your username and password \n        to sign in to your account");
        titleLabel.setId("mainLabelSignIn");
        GridPane.setConstraints(titleLabel, 1, 0, 1, 3, HPos.CENTER, VPos.CENTER);//column, row, columnSpan, rowSpan
        grid.setMargin(titleLabel, new Insets(50,10,10,50));//top right bottom left

        //ERROR LABEL

        GridPane.setConstraints(wrongLabel, 1, 2, 2, 1, HPos.CENTER, VPos.CENTER);
        grid.setMargin(wrongLabel, new Insets(100, 10, 5, 50));//top right bottom left


        //USER NAME INPUT

        nameInput.setPromptText("username");
        nameInput.setId("textFieldInputCRS");
        GridPane.setConstraints(nameInput, 1, 3, 1, 1, HPos.CENTER, VPos.CENTER);
        grid.setMargin(nameInput, new Insets(10, 10, 0, 50));//top right bottom left

        //PASSWORD INPUT

        passwordInput.setPromptText("password");
        passwordInput.setId("textFieldInputCRS");
        GridPane.setConstraints(passwordInput, 1, 4, 1, 1, HPos.CENTER, VPos.CENTER);//column, row, columnspan, rowspan
        grid.setMargin(passwordInput, new Insets(0, 10, 10, 50));//top right bottom left


        //SIGN IN BUTTON
        Button signIn = new Button();
        signIn.setId("signInButton");
        GridPane.setConstraints(signIn, 1,5,1,1, HPos.CENTER, VPos.CENTER);//column, row, columnspan, rowspan
        grid.setMargin(signIn, new Insets(10, 10, 10, 60));//top right bottom left

        //SIGN UP BUTTON
        Button signUp = new Button ();
        signUp.setId("signUpButton");
        GridPane.setConstraints(signUp, 1,6,1,1, HPos.CENTER,VPos.CENTER);//column, row, columnspan, rowspan
        grid.setMargin(signUp, new Insets(50,10,20,60));//top right bottom left

        //ADDING CHILDREN TO GRID-PANE.
        grid.getChildren().addAll(titleLabel, nameInput, passwordInput, signIn, wrongLabel, signUp);

        //ACTION TO SIGN IN & SIGN UP BUTTON
        signUp.setOnAction(event -> {
            signUpCustomer.signUp();
        });

        signIn.setOnAction(event2 -> {
            controller.logIn(this,completeReservation);

        });

        Scene scene = new Scene(grid,364,386);
        scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
        window.setScene(scene);
        window.show();
    }




    private void closeProgram(){

        // Boolean answer = ConfirmBox.display("Warning","Are you sure, you want to close the program");

        // if(answer){
        //      window.close();
        //  }

    }
    private void showAllTable(){

        Stage window = new Stage();
        VBox vBox = new VBox(10);
        createTableOfAppoitment();
        Button addButton = new Button("add");

        addButton.setOnAction(event1 -> {
            window.close();
            signUpCustomer.signUp();


        });
        Button deleteButton = new Button("delete");
        deleteButton.setOnAction(event -> {

            ObservableList<Customer> customerList = FXCollections.observableArrayList();
            customerList.addAll(tableView.getSelectionModel().getSelectedItems());

            for(Customer c: customerList){
                controller.getDatabaseCustomer().deleteFromDB(c.user);
                tableView.getItems().remove(c);
            }

        });



        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(addButton,deleteButton);
        hbox.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(tableView,hbox);
        Scene scene = new Scene(vBox, 300,600);
        window.setScene(scene);
        window.setTitle("DoctorBooking");
        window.show();


    }


    private void createTableOfAppoitment(){
        tableView= new TableView();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        javafx.scene.control.TableColumn<Customer,String> customerUser = new javafx.scene.control.TableColumn<>("user:");
        customerUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        customerUser.setMinWidth(100);


        javafx.scene.control.TableColumn<Customer,String> customerName = new javafx.scene.control.TableColumn<>("name:");
        customerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        customerName.setMinWidth(100);

        javafx.scene.control.TableColumn<Customer,String> customerSurname = new javafx.scene.control.TableColumn<>("surname:");
        customerSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        customerSurname.setMinWidth(100);

        javafx.scene.control.TableColumn<Customer,String> customerTelephon = new javafx.scene.control.TableColumn<>("telephone:");
        customerTelephon.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        customerTelephon.setMinWidth(100);

        javafx.scene.control.TableColumn<Customer,String> customerMail = new javafx.scene.control.TableColumn<>("mail:");
        customerMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        customerMail.setMinWidth(100);


        javafx.scene.control.TableColumn<Customer,String> customerPassword = new javafx.scene.control.TableColumn<>("password:");
        customerPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        customerPassword.setMinWidth(100);
        tableView.getColumns().addAll(customerUser, customerName, customerSurname,customerTelephon,customerMail,customerPassword);

        tableView.setItems(controller.getDatabaseCustomer().getCustomers());

    }

}