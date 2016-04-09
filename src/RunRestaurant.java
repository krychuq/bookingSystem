import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Timestamp;


/**
 * Created by lucia on 11/30/15.
 */
public class RunRestaurant extends Application {

    ControllerRestaurant controllerRestaurant = new ControllerRestaurant(this);
    TextField cvrText = new TextField("25171888");
    TextField passwordTextField = new TextField();
    int cvrNumb;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        GridPane gridPane = new GridPane();
        Label cvr = new Label("CVR:");
        Label password = new Label("Password:");
        Button signIn = new Button("sign in");

        signIn.setOnAction(event -> {

            cvrNumb = Integer.parseInt(cvrText.getText());
            controllerRestaurant.logIn(this);
            

        });
        Button enter = new Button("sign up");


        enter.setOnAction(event -> {

            controllerRestaurant.displaySignUp();


        });

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        gridPane.setConstraints(cvr, 0, 0);
        gridPane.setConstraints(cvrText, 1, 0);
        gridPane.setConstraints(password, 0, 1);
        gridPane.setConstraints(passwordTextField, 1, 1);
        gridPane.setConstraints(signIn, 1, 2);
        gridPane.setConstraints(enter, 1, 3);

        gridPane.getChildren().addAll(cvr, cvrText, password, passwordTextField, signIn, enter);

        Scene scene = new Scene(gridPane, 300, 200);

        primaryStage.setScene(scene);
        scene.getStylesheets().addAll(this.getClass().getResource("Restaurant.css").toExternalForm());
        gridPane.setId("runRestaurant");
        primaryStage.show();


    }




}