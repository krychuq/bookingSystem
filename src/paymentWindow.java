import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;

/**
 * Created by krystian on 2015-12-03.
 */
public class paymentWindow {
    Label labelPayment = new Label("Payment with Table4you");
    Image image = new Image("pictures/paymentLine.png");
    javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
    Label labelFee = new Label("Total fee for reservation");
    Label labelAccept = new Label("We accept the following credit/debit cards. Please select a card \n" +
            "type, complete the information below and click Continue.");
    Label labelNote = new Label("(Note: for security puposes, we will not save any of your credit card date.)");
    Image image1 = new Image("pictures/underThePayment.png");
    javafx.scene.image.ImageView imageView1 = new javafx.scene.image.ImageView(image1);
    Image image2 = new Image("pictures/visa.png");
    javafx.scene.image.ImageView visa = new javafx.scene.image.ImageView(image2);
    Image image3 = new Image("pictures/visaDebit.png");
    javafx.scene.image.ImageView visaDebit = new javafx.scene.image.ImageView(image3);
    Image image4 = new Image("pictures/masterCard.png");
    javafx.scene.image.ImageView masterCard = new javafx.scene.image.ImageView(image4);
    Image image5 = new Image("pictures/visaElectron.png");
    javafx.scene.image.ImageView visaElectron = new javafx.scene.image.ImageView(image5);
    Image image6 = new Image("pictures/americanExpress.png");
    javafx.scene.image.ImageView americanExpress = new javafx.scene.image.ImageView(image6);
    CheckBox visaCheckBox = new CheckBox();
    CheckBox visaDebitCheckBox;
    CheckBox masterCardCheckBox;
    CheckBox visaElectronCheckBox;
    CheckBox americanExpressCheckBox;
    Image image7 = new Image("pictures/underInfo.png");
    Label cardNumber = new Label("Card number:");
    Label expirationDate = new Label("Expiration Date");
    Label securityCode = new Label("Security Code");
    Button continueButton = new Button();
    AttributeTextField cardNumberTextField = new AttributeTextField();
    AttributeTextField securityCodeTextField = new AttributeTextField();
    AttributeTextField expirationMonth = new AttributeTextField();
    AttributeTextField expirationYear = new AttributeTextField();
    Stage primaryStage;
    int number;
    String name;


    javafx.scene.image.ImageView underInfo = new javafx.scene.image.ImageView(image7);

public paymentWindow(int number,String name){
    this.name = name;
    this.number = number;

}
    public void displayPayment(int deposit)  {
        checkComboBoxes();
         labelFee.setText("Total fee for reservation          "+deposit + " dkk");
        primaryStage = new Stage();
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.getColumnConstraints().add(new ColumnConstraints(100)); // column 0 is 100 wide
        gridPane.setHgap(15);
        expirationMonth.setPromptText("Month");
        expirationYear.setPromptText("Year");
        labelPayment.setId("paymentLabel");
        labelFee.setId("totalFee");
        labelAccept.setId("totalFee");
        labelNote.setId("labelN ote");
        cardNumber.setId("cardInformation");
        continueButton.setId("continueButton");
        continueButton.setOnAction(event -> {
            checkFields();
            displayPaymentConformition();
        });
        expirationDate.setId("cardInformation");
        securityCode.setId("cardInformation");
        cardNumberTextField.setId("textFieldCard");
        securityCodeTextField.setId("textFieldCard");
        expirationMonth.setId("textFieldExpiration");
        expirationYear.setId("textFieldExpiration");
        GridPane.setConstraints(labelPayment, 0, 0, 5, 1, HPos.CENTER, VPos.TOP);
        gridPane.setMargin(labelPayment, new Insets(50, 0, 0, 0));
        GridPane.setConstraints(imageView, 0, 1, 5, 1, HPos.CENTER, VPos.TOP);
        gridPane.setMargin(imageView1, new Insets(15, 0, 0, 0));
        GridPane.setConstraints(imageView1, 0, 2, 5, 1, HPos.CENTER, VPos.TOP);
        GridPane.setConstraints(labelFee, 0, 2, 5, 1, HPos.LEFT, VPos.CENTER);
        gridPane.setMargin(labelFee, new Insets(15, 0, 0, 20));

        GridPane.setConstraints(labelAccept, 0, 3, 5, 1, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(labelAccept, new Insets(15, 0, 0, 0));
        GridPane.setConstraints(labelNote, 0, 4, 5, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(visa, 0, 5, 1, 1, HPos.RIGHT, VPos.BOTTOM);
        gridPane.setMargin(visa, new Insets(30, 0, 0, 0));

        GridPane.setConstraints(visaDebit, 1, 5);
        gridPane.setMargin(visaDebit, new Insets(30, 0, 0, 0));

        GridPane.setConstraints(masterCard, 2, 5);
        gridPane.setMargin(masterCard, new Insets(30, 0, 0, 0));

        GridPane.setConstraints(visaElectron, 3, 5);
        gridPane.setMargin(visaElectron, new Insets(30, 0, 0, 0));

        GridPane.setConstraints(americanExpress, 4, 5);
        gridPane.setMargin(americanExpress, new Insets(30, 0, 0, 0));
        GridPane.setConstraints(visaCheckBox, 0, 6, 1, 1, HPos.RIGHT, VPos.BOTTOM);
        gridPane.setMargin(visaCheckBox, new Insets(15, 20, 0, 0));

        GridPane.setConstraints(visaDebitCheckBox, 1, 6, 1, 1, HPos.CENTER, VPos.BOTTOM);
        gridPane.setMargin(visaDebitCheckBox, new Insets(15, 0, 0, 0));

        GridPane.setConstraints(masterCardCheckBox, 2, 6, 1, 1, HPos.CENTER, VPos.BOTTOM);
        gridPane.setMargin(masterCardCheckBox, new Insets(15, 0, 0, 0));


        GridPane.setConstraints(visaElectronCheckBox, 3, 6, 1, 1, HPos.CENTER, VPos.BOTTOM);
        gridPane.setMargin(visaElectronCheckBox, new Insets(15, 0, 0, 0));

        GridPane.setConstraints(americanExpressCheckBox, 4, 6, 1, 1, HPos.CENTER, VPos.BOTTOM);
        gridPane.setMargin(americanExpressCheckBox, new Insets(15, 30, 0, 0));
        GridPane.setConstraints(underInfo, 0, 7, 5, 3, HPos.CENTER, VPos.BOTTOM);
        gridPane.setMargin(underInfo, new Insets(40, 0, 0, 0));
        GridPane.setConstraints(cardNumber, 0, 7, 5, 1, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(cardNumber, new Insets(70, 0, 0, 20));
        GridPane.setConstraints(expirationDate, 0, 8, 5, 1, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(expirationDate, new Insets(30, 0, 0, 20));

        GridPane.setConstraints(securityCode, 0, 9, 5, 1, HPos.LEFT, VPos.TOP);
        GridPane.setConstraints(securityCodeTextField, 1, 9, 5, 1, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(securityCodeTextField, new Insets(20, 0, 0, 40));

        GridPane.setConstraints(continueButton, 0, 10, 5, 1, HPos.CENTER, VPos.TOP);
        gridPane.setMargin(continueButton, new Insets(30, 0, 0, 0));

        gridPane.setMargin(securityCode, new Insets(30, 0, 0, 20));

        GridPane.setConstraints(cardNumberTextField, 1, 7, 5, 1, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(cardNumberTextField, new Insets(64, 0, 0, 40));
        GridPane.setConstraints(expirationMonth, 1, 8, 5, 1, HPos.LEFT, VPos.TOP);
        gridPane.setMargin(expirationMonth, new Insets(20, 0, 0, 40));
        GridPane.setConstraints(expirationYear, 3, 8, 5, 1, HPos.RIGHT, VPos.TOP);
        gridPane.setMargin(expirationYear, new Insets(20, 75, 0, 0));

        gridPane.getChildren().addAll(labelPayment,imageView,imageView1,labelFee,labelAccept,labelNote,
                visa,visaDebit,masterCard,visaElectron,americanExpress,
                visaCheckBox,visaDebitCheckBox,masterCardCheckBox,visaElectronCheckBox,americanExpressCheckBox,
                underInfo,cardNumber,expirationDate,securityCode,continueButton,
                cardNumberTextField,securityCodeTextField,expirationMonth,expirationYear);
        Scene scene = new Scene(gridPane, 599,680);
        scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());
        gridPane.setId("paymentBackground");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void checkComboBoxes(){
         visaCheckBox = new CheckBox();
        visaCheckBox.setOnAction(event -> {
            if(visaCheckBox.isSelected()){
                visaDebitCheckBox.setSelected(false);
                masterCardCheckBox.setSelected(false);
                americanExpressCheckBox.setSelected(false);
                visaElectronCheckBox.setSelected(false);
            }
        });
         visaDebitCheckBox = new CheckBox();
        visaDebitCheckBox.setOnAction(event -> {
            if (visaDebitCheckBox.isSelected()) {
                visaCheckBox.setSelected(false);
                masterCardCheckBox.setSelected(false);
                americanExpressCheckBox.setSelected(false);
                visaElectronCheckBox.setSelected(false);
            }
        });
        masterCardCheckBox = new CheckBox();
        masterCardCheckBox.setOnAction(event -> {
            if (masterCardCheckBox.isSelected()) {
                visaCheckBox.setSelected(false);
                visaDebitCheckBox.setSelected(false);
                americanExpressCheckBox.setSelected(false);
                visaElectronCheckBox.setSelected(false);
            }
        });
         visaElectronCheckBox = new CheckBox();
        visaElectronCheckBox.setOnAction(event -> {
            if (visaElectronCheckBox.isSelected()) {
                visaCheckBox.setSelected(false);
                visaDebitCheckBox.setSelected(false);
                americanExpressCheckBox.setSelected(false);
                masterCardCheckBox.setSelected(false);
            }
        });
         americanExpressCheckBox = new CheckBox();
        americanExpressCheckBox.setOnAction(event -> {
            if (americanExpressCheckBox.isSelected()) {
                visaCheckBox.setSelected(false);
                visaDebitCheckBox.setSelected(false);
                visaElectronCheckBox.setSelected(false);
                masterCardCheckBox.setSelected(false);
            }
        });

    }
    public void checkFields(){
        if(cardNumberTextField.getText().equals("")||securityCodeTextField.equals("")||expirationMonth.equals("")||expirationYear.equals("")){
            System.out.println("please fill all of textFields");
        }else{
            primaryStage.close();
        }
    }
    public void displayPaymentConformition(){
        Label paymentConformition = new Label("Payment Confirmation");
        paymentConformition.setId("paymentLabelConformition");
        Image image = new Image("pictures/lineConfirmPayment.png");
        Label thankYou = new Label("Thank you "+name+" for your payment");
        Label reservationNumber = new Label("Your reservation number is "+number);
        thankYou.setId("paymentConformitionRest");
        reservationNumber.setId("reservationNumber");
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
        Button button = new Button();
        button.setId("buttonPaymentConformition");

        Stage stage = new Stage();
        button.setOnAction(event -> {
            stage.close();
        });
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getChildren().addAll(paymentConformition, imageView, thankYou, reservationNumber, button);
        Scene scene = new Scene(vBox,599,352);
        stage.setScene(scene);
        stage.show();
        vBox.setId("paymentBackgroundConformition");
        vBox.setMargin(paymentConformition, new Insets(40, 0, 0, 0));
        vBox.setMargin(imageView, new Insets(5, 0, 0, 0));
        vBox.setMargin(thankYou, new Insets(40,0,0,0));
        vBox.setMargin(reservationNumber, new Insets(19,0,0,0));
        vBox.setMargin(button,new Insets(40,0,0,0));
                scene.getStylesheets().addAll(this.getClass().getResource("Style.css").toExternalForm());

    }
}
