import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RestaurantWindow  {

    Stage stage = new Stage();
    Controller controller;
    String websiteaddress;
    int cvr;
    public RestaurantWindow(Controller controller, int cvr){
        this.controller = controller;
        this.cvr = cvr;

    }
    public void displayRestaurantWindow() {
        stage.setWidth(1000);
        stage.setHeight(700);
        Scene scene = new Scene(new Group());
        VBox root = new VBox();
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

            websiteaddress = controller.getDatabaseCustomer().getWebSite(cvr);

        webEngine.load(websiteaddress);
        System.out.println(websiteaddress);



        root.getChildren().addAll(browser);
        scene.setRoot(root);

        stage.setScene(scene);
        stage.show();
    }


}