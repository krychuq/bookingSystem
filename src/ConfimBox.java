import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by lucia on 11/10/15.
 */
public class ConfimBox {

    static boolean answer;

    static boolean display(String title,String message){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label(message);
        Button yesButton = new Button("YES");
        yesButton.setOnAction(event -> {
            answer = true;
            window.close();
        });
        Button noButton = new Button("NO");

        noButton.setOnAction(event -> {
            answer = false;
            window.close();
        });
        VBox layout2 = new VBox(5);
        layout2.getChildren().addAll(label,yesButton,noButton);
        layout2.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout2);
        window.setScene(scene);
        window.showAndWait();


        return answer;

    }
}


