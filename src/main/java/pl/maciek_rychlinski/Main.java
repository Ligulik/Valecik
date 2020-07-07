package pl.maciek_rychlinski;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        // Load template from FXML:

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/pl/maciek_rychlinski/sample.fxml"));


        // Download controller from FXML:

        Controller controller = loader.getController();

        // Load i set the scene:

        StackPane stackPane = loader.load();
        Scene scene = new Scene(stackPane);


        Parent root = FXMLLoader.load(getClass().getResource("/pl/maciek_rychlinski/sample.fxml"));
        primaryStage.setTitle("Valet");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
