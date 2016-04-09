import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Monica on 15-12-2015.
 */
public class CancelReservation {
        private Controller controller;
        private Customer customer;
        private Label bookingIdLabel;
        Label wrongLabel = new Label("");
        private Stage primaryStage;
        private VBox vBox;
        private Button save;
        public ComboBox<String>  idBox= new ComboBox<>();

        public CancelReservation(Controller controller,Customer customer){
            this.controller = controller;
            this.customer = customer;
        }

        public void setWrongLabel(String s){
            wrongLabel.setText(s);

        }
        public void cancelReservationWindow() {
            primaryStage = new Stage();
            primaryStage.setTitle("Account Information");
            vBox = new VBox();

            //LABELS===========================================================>
            wrongLabel.setId("errorLabel");
            bookingIdLabel = new Label("Booking Number");
            bookingIdLabel.setId("mainLabelCB");

            //INPUT AREAS=====================================================================================>

            //BOOKING ID BOX
            idBox.setItems(controller.databaseCustomer.getCustomerCurrentBookingId(controller.getCurrentTimeStamp(), customer.getMail()));
            idBox.setValue("Select");
            idBox.setVisibleRowCount(5);
            idBox.setId("combo-box");

            //SET ON ACTION --------- GUI FUNCTIONALITIES
            save = new Button();
            save.setId("saveButton");
            save.setOnMouseClicked(event -> {
                if (idBox.getSelectionModel().getSelectedItem() !=null) {
                    controller.deleteReservation(Integer.parseInt(idBox.getSelectionModel().getSelectedItem()));
                    controller.updateLabel(customer.getMail());
                    primaryStage.close();
                    //controller.showCustomerBookings(customer);
                }
            });

            //SET MARGINS ----- SET ALIGNMENT ----- GET && ADD CHILDREN

            vBox.setMargin(save, new Insets(10, 0, 10, 0));
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(wrongLabel, bookingIdLabel,idBox,save);

            //DEFINE SCENE AND PRIMARY STAGE
            Scene scene = new Scene(vBox,364,386);
            scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
            vBox.setId("reservationSystem");
            primaryStage.setScene(scene);
            primaryStage.show();
        }




}
