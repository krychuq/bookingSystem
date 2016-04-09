import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

/**
 * Created by krystian on 2015-12-02.
 */
public class Conformition {
     Label labelConformition = new Label("Confirmation");
    Image image = new Image("pictures/line.png");
    javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
    String name;
    String makingReservation= "for making the reservation with Table4You";
    Label reservationNmber = new Label();
    Label reservation = new Label("Your reservation number is ");
    Label labelMail = new Label("Check your email for more details.");


    public void displayConformition(int number,String name){
         this.name = name;
        Label labelName = new Label("Thank you "+name+" "+ makingReservation);

        Stage stage = new Stage();
        VBox vBox = new VBox();
        labelConformition.setId("conformitionLebel");
        labelName.setId("conformitionLebelRest");
        reservation.setId("conformitionLebelRest");
        labelMail.setId("conformitionLebelRest");
        reservationNmber.setText(String.valueOf(number));
        reservationNmber.setId("conformitionLebelNumber");
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(reservation, reservationNmber);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setId("comformition");
        Button button = new Button();

        vBox.setMargin(labelConformition, new Insets(30, 0, 0, 0));
        vBox.setMargin(labelName, new Insets(30,0,0,0));
        vBox.setMargin(hBox, new Insets(30,0,0,0));
        vBox.setMargin(labelMail, new Insets(30,0,0,0));
        vBox.setMargin(button, new Insets(40,0,0,0));



        button.setId("conformitionButtton");
        button.setOnAction(event -> {
            stage.close();
        });
        vBox.getChildren().addAll(labelConformition,
                imageView,labelName,hBox,labelMail,button);
        Scene scene = new Scene(vBox,599,404);
        scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();


    }
}
