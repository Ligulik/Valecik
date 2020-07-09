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

    public static String txtCase;
    public static String mp3Case;

    @FXML
    private ComboBox choose1, choose2;
    @FXML
    private Button button1, button2, button3;
    @FXML
    private TextField path1, path2, path3;


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
        choose2.setItems(options);
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
            }else if(source==button2){
                path2.setText(chosenFile.getAbsolutePath());
                checkAndChose(choose1, path2);
            }else if(source==button3){
                path3.setText(chosenFile.getAbsolutePath());
                checkAndChose(choose2, path3);
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




    private void checkAndChose(ComboBox comboBox,TextField textField){
        String extension=(String)comboBox.getValue();
        switch (extension){
            case ".txt":
                txtCase=textField.getText();
                break;
            case ".mp3":
                mp3Case=textField.getText();
                break;
            default:
                System.out.println("Error");

        }
    }
}
