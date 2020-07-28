package pl.maciek_rychlinski;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.*;


public class Main extends Application {


    public Main() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        // Load template from FXML:

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/pl/maciek_rychlinski/sample.fxml"));


        // Load i set the scene:

        StackPane stackPane = loader.load();
        Scene scene = new Scene(stackPane);

        Parent root = FXMLLoader.load(getClass().getResource("/pl/maciek_rychlinski/sample.fxml"));
        primaryStage.setTitle("Valet");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Download controller from FXML:

        Controller controller = loader.getController();

        // Download the saved options;

        controller.checkMemory();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
