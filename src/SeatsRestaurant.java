import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by lucia on 12/8/15.
 */
public class SeatsRestaurant {

    int availableSeatsInt;
    Restaurant restaurant;
    ControllerRestaurant controllerRestaurant;

    public SeatsRestaurant(ControllerRestaurant controllerRestaurant){
        this.controllerRestaurant = controllerRestaurant;
    }

    public TextField availableText = new TextField();


    public void start(){

        Stage primaryStage = new Stage();
        GridPane gridPane = new GridPane();
        Button ok = new Button();
        ok.setText("Confirm");
        ok.setOnAction(event -> {
            availableSeatsInt = Integer.parseInt(availableText.getText());

            controllerRestaurant.changeSeatsAvailable(this);
            primaryStage.close();

        });

        Label availableSeat = new Label("Available seats:");

        gridPane.setGridLinesVisible(false);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        GridPane.setConstraints(availableSeat,0,0);
        GridPane.setConstraints(availableText,0,1);
        GridPane.setConstraints(ok,1,1);

        gridPane.getChildren().addAll(availableSeat,availableText,ok);
        Scene primaryScene = new Scene(gridPane,300,140);
        primaryStage.setScene(primaryScene);
        primaryStage.show();


    }
}
