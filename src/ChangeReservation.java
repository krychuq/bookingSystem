
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;

    import java.time.LocalDate;
    import java.util.ArrayList;

    /**
     * Created by Monica on 07-12-2015.
     */
    public class ChangeReservation {
        private Controller controller;
        private Customer customer;
        private CustomerBookings object;
        private Booking booking;
        private CustomerBookings customerBookings;
        private Label bookingIdLabel;
        private Label bookingDateLabel;
        private Label bookingTimeLabel;
        private Label numberLabel;
        private Label errorLabel;
        private Label mainLabel;
        Label wrongLabel = new Label("");
        private Stage primaryStage;
        private VBox vBox;
        private Button save;
        public ComboBox<String> hour = new ComboBox<>();
        public ComboBox<String>  idBox= new ComboBox<>();
        public ComboBox<String> numberOfPeople = new ComboBox();
        public DatePicker dateSelector;
        private boolean answer;
        private final String pattern = "dd-MM-yyyy";


        private ArrayList reservations;

        public ChangeReservation(Controller controller,Customer customer, CustomerBookings object){
            this.controller = controller;
            this.customer = customer;
            this.object = object;
        }

        public void setWrongLabel(String s){
            wrongLabel.setText(s);

        }
        public void changeReservationMethod() {
            primaryStage = new Stage();
            primaryStage.setTitle("Account Information");
            vBox = new VBox();

            //LABELS===========================================================>
            wrongLabel.setId("errorLabel");
            bookingIdLabel = new Label("Booking Number");
            bookingIdLabel.setId("mainLabelCB");
            bookingDateLabel = new Label("Booking Date");
            bookingTimeLabel = new Label("Booking Time");
            bookingTimeLabel.setId("mainLabelCB");
            bookingDateLabel.setId("mainLabelCB");
            numberLabel = new Label("Number of guests");
            numberLabel.setId("mainLabelCB");

            //INPUT AREAS=====================================================================================>

            //BOOKING ID BOX
            idBox.setItems(controller.databaseCustomer.getCustomerCurrentBookingId(controller.getCurrentTimeStamp(), customer.getMail()));
            idBox.setValue("Select");
            idBox.setVisibleRowCount(5);
            idBox.setId("combo-box");
            //DatePicker
            dateSelector = new DatePicker();
            dateSelector.setValue(LocalDate.now());
            dateSelector.setEditable(false);
            dateSelector.setPrefSize(50, 50);
            dateSelector.setConverter(controller.datePickerFormatter());
            dateSelector.setPromptText(pattern.toLowerCase());
            //HOUR
            controller.getDatabaseCustomer().hourInCombobox();
            hour.setItems(controller.getDatabaseCustomer().hourInCombobox());
            hour.setVisibleRowCount(5);
            hour.setValue("17:00");
            hour.setId("combo-box");
            //GUESTS
            numberOfPeople.setItems(controller.getDatabaseCustomer().numberOfpeopleComboBox());
            numberOfPeople.setValue("2 people");
            numberOfPeople.setVisibleRowCount(5);
            numberOfPeople.setId("combo-box");

            //SET ON ACTION --------- GUI FUNCTIONALITIES
            save = new Button();
            save.setId("saveButton");
            save.setOnMouseClicked(event -> {
                if (controller.checkReservationChanges(this, customer)) {
                    controller.implementingUpdates(true);
                    controller.updateLabel(customer.getMail());
                    primaryStage.close();
                }
            });

            //SET MARGINS ----- SET ALIGNMENT ----- GET && ADD CHILDREN

            vBox.setMargin(save, new Insets(10, 0, 10, 0));
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(wrongLabel, bookingIdLabel,idBox, bookingDateLabel,dateSelector,bookingTimeLabel,hour,numberLabel,numberOfPeople,save);

            //DEFINE SCENE AND PRIMARY STAGE
            Scene scene = new Scene(vBox,364,386);
            scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
            vBox.setId("reservationSystem");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

    }

