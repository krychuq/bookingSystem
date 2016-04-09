import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by lucia on 11/10/15.
 */
public class SignUpCustomer {
    Stage primaryStage;
    Label wrongLabel = new Label("");
    Label name = new Label("Name:");
    TextField nameTextField = new TextField();
    Label surname = new Label("Surname:");
    TextField surnameTextField = new TextField();
    Label telephone = new Label("Phone number:");
    AttributeTextField telephoneTextField = new AttributeTextField();
    Label mail = new Label("Mail");
    TextField mailTextField = new TextField();
    Label user = new Label("User name:");
    TextField userTextField = new TextField();
    Label password = new Label("Password:");
    PasswordField passwordTextField = new PasswordField();
    Customer customer;
    Controller controller;
    CustomerBookings customerBookings;
    ReservationSystemGui reservationSystemGui;

    public SignUpCustomer(Controller controller){
        this.controller = controller;
    }
    public SignUpCustomer(Controller controller,Customer customer,CustomerBookings customerBookings,ReservationSystemGui reservationSystemGui) {
        this.controller = controller;
        nameTextField.setText(customer.getName());
        surnameTextField.setText(customer.getSurname());
        telephoneTextField.setText(Integer.toString(customer.getTelephone()));
        mailTextField.setText(customer.getMail());
        userTextField.setText(customer.getUser());
        passwordTextField.setText(customer.getPassword());
        this.customerBookings = customerBookings;
        this.reservationSystemGui = reservationSystemGui;

    }

    public void signUp() {
        primaryStage = new Stage();
        primaryStage.setTitle("SIGN UP");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        name.setId("labelSignUp");
        GridPane.setConstraints(name, 0, 0, 1, 1, HPos.LEFT, VPos.BOTTOM);
        gridPane.getRowConstraints().add(new RowConstraints(40));
        nameTextField.setId("textFieldInputCRS");

        GridPane.setConstraints(nameTextField, 1, 0, 1, 1, HPos.CENTER, VPos.BOTTOM);

        surname.setId("labelSignUp");
        GridPane.setConstraints(surname, 0, 1);
        surnameTextField.setId("textFieldInputCRS");
        GridPane.setConstraints(surnameTextField, 1, 1);
        GridPane.setConstraints(wrongLabel, 0, 0, 1, 1, HPos.CENTER, VPos.TOP);
        GridPane.setColumnSpan(wrongLabel, 2);

        gridPane.getChildren().addAll(wrongLabel);
        telephone.setId("labelSignUp");
        GridPane.setConstraints(telephone, 0, 2);
        telephoneTextField.setId("textFieldInputCRS");
        telephoneTextField.setMaxWidth(150);
        GridPane.setConstraints(telephoneTextField, 1, 2);

        mail.setId("labelSignUp");
        GridPane.setConstraints(mail, 0, 3);
        mailTextField.setId("textFieldInputCRS");
        GridPane.setConstraints(mailTextField, 1, 3);

        user.setId("labelSignUp");
        GridPane.setConstraints(user, 0, 4);

        userTextField.setId("textFieldInputCRS");
        GridPane.setConstraints(userTextField, 1, 4);

        password.setId("labelSignUp");
        GridPane.setConstraints(password, 0, 5);

        passwordTextField.setId("textFieldInputCRS");
        GridPane.setConstraints(passwordTextField, 1, 5);

        gridPane.setGridLinesVisible(false);
        Button save = new Button();
        GridPane.setConstraints(save, 1, 7);
        primaryStage.setOnCloseRequest(event1 -> {

            event1.consume();
            boolean result = ConfimBox.display("Warning", "Are you sure you want to close?");
            if (result) {
                primaryStage.close();
            }

        });
        Button cancel = new Button();
        cancel.setId("cancel");
        cancel.setOnAction(event1 -> {
            cancel.setOnAction(event -> {

                boolean result = ConfimBox.display("Warning", "You sure you want to cancel now?");
                if (result) {

                    primaryStage.close();
                }
            });

        });
        GridPane.setConstraints(cancel, 1, 8);

        save.setId("saveButton");


        save.setOnAction(event -> {
            if ((nameTextField.getText().equals("")) || (surnameTextField.getText().equals("")) || (telephoneTextField.getText().equals(""))
                    || (mailTextField.getText().equals("")) && (userTextField.getText().equals("")) || (nameTextField.getText().equals("")) ||
                    (passwordTextField.getText().equals(""))) {


                System.out.println("empty textFields");
                wrongLabel.setText("Please fill all of textFields");
                wrongLabel.setTextFill(Color.RED);

            } else {

                int phoneNumber = Integer.valueOf(telephoneTextField.getText());
                customer = new Customer(mailTextField.getText(), userTextField.getText(), nameTextField.getText(), surnameTextField.getText(), phoneNumber,
                        passwordTextField.getText());
                if ((controller.databaseCustomer.checkMailExistence(customer.getMail())) != 0) {
                    controller.databaseCustomer.updateCustomer(customer);
                    //   customerBookings.setCustomer(customer);
                    reservationSystemGui.setCustomer(customer);
                    reservationSystemGui.setWelcomUser("Welcome " + customer.getName() + " " + customer.getSurname());
                } else {
                    controller.getDatabaseCustomer().insertCustomer(customer);
                }

            }
            primaryStage.close();

        });


        gridPane.getChildren().addAll(name, nameTextField, surname, surnameTextField, telephone, telephoneTextField, mail, mailTextField, user, userTextField, password, passwordTextField, save, cancel);


        Scene scene = new Scene(gridPane,364,386);
        scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
        gridPane.setId("reservationSystem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}