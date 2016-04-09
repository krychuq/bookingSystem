import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.xml.soap.Text;

/**
 * Created by lucia on 12/1/15.
 */
public class SignUp {


    public ControllerRestaurant controllerRestaurant;
    public int cvrInt;
    public TextField cvrText = new TextField();
    public TextField nameOfRestaurantTextField = new TextField();
    public TextField companyNameTextField = new TextField();
    public TextField addressText = new  TextField();
    public TextField phoneText = new TextField();
    public TextField emailText = new TextField();
    public TextField passwordTextField = new TextField();
    public TextField websiteText = new TextField();
    public TextField depositText = new TextField();
    public TextArea descriptionText  = new TextArea();
    public TextField availableSeatsText = new TextField();
    public int phoneInt;
    public int depositInt;
    public int availableSeatInt;

    
    public  SignUp(ControllerRestaurant controllerRestaurant){
        this.controllerRestaurant = controllerRestaurant;


    }
    public void start(){

        Stage primaryStage = new Stage();
        GridPane gridPane = new GridPane();

        Label cvr = new Label("CVR:");
        Label nameOfRestaurant = new Label("Restaurant name:");
        Label companyName = new Label("Company nane:");
        Label address = new Label("Address:");
        Label phone = new Label("Phone");
        Label email = new Label("Email:");
        Label password = new Label("Password:");
        Label website = new Label("Website");
        Label description = new Label("Description:");
        Label deposit = new Label("Deposit:");
        Label availableSeats = new Label("Available seats:");
        Button signUp = new Button();
        signUp.setText("sign up");

        signUp.setOnAction(event -> {

            cvrInt = Integer.parseInt(cvrText.getText());
            phoneInt = Integer.parseInt(phoneText.getText());
            depositInt = Integer.parseInt(depositText.getText());
            availableSeatInt = Integer.parseInt(availableSeatsText.getText());
            controllerRestaurant.writeRestaurant(this);
            controllerRestaurant.inserAvailableSeats(this);
        });
        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        GridPane.setConstraints(cvr,0,0);
        GridPane.setConstraints(cvrText,1,0);
        GridPane.setConstraints(nameOfRestaurant,0,1);
        GridPane.setConstraints(nameOfRestaurantTextField,1,1);
        GridPane.setConstraints(companyName,0,2);
        GridPane.setConstraints(companyNameTextField,1,2);
        GridPane.setConstraints(address,0,3);
        GridPane.setConstraints(addressText,1,3);
        GridPane.setConstraints(phone,0,4);
        GridPane.setConstraints(phoneText,1,4);
        GridPane.setConstraints(email,0,5);
        GridPane.setConstraints(emailText,1,5);
        GridPane.setConstraints(password,0,6);
        GridPane.setConstraints(passwordTextField,1,6);
        GridPane.setConstraints(website,0,7);
        GridPane.setConstraints(websiteText,1,7);
        GridPane.setConstraints(deposit,0,8);
        GridPane.setConstraints(depositText,1,8);
        GridPane.setConstraints(description,0,9);
        GridPane.setConstraints(descriptionText,1,9);
        GridPane.setConstraints(availableSeats,0,10);
        GridPane.setConstraints(availableSeatsText,1,10);
        GridPane.setConstraints(signUp,1,11);

        gridPane.getChildren().addAll(cvr,cvrText,nameOfRestaurant,nameOfRestaurantTextField,companyName,companyNameTextField,address,addressText,
                phone,phoneText,email,emailText,password,passwordTextField,website,websiteText,deposit,depositText,description,descriptionText,signUp,availableSeats,availableSeatsText);

        Scene scene = new Scene(gridPane,780,500);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.getStylesheets().addAll(this.getClass().getResource("Restaurant.css").toExternalForm());
        gridPane.setId("runRestaurant");








    }
}