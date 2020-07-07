package pl.maciek_rychlinski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


public class Controller {

    public static String txtCase = "C:/";

    @FXML
    private ComboBox choose1;
    @FXML
    private Button button1;
    @FXML
    private TextField path1;


    public Controller() {
    }

    @FXML
    void initialize() {

        ObservableList<String> options = FXCollections.observableArrayList(
                ".mp3",
                ".pdf",
                ".zip",
                ".txt"
        );
        choose1.setItems(options);
    }

    // Set the path to listen:

    @FXML
    public void find(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File chosenFile = directoryChooser.showDialog(null);
        if (chosenFile != null) {
            Object source = event.getSource();
            if (source == button1) {
                path1.setText(chosenFile.getAbsolutePath());
            }

        }

        // Run the listener in a separate thread:

        Listener listener = new Listener();
        Thread thread = new Thread(() -> {
            try {
                listener.listen(Path.of(path1.getText()));
            } catch (IOException exc) {
                System.out.println(exc.getMessage());
            }

        });
        thread.start();
    }

}
