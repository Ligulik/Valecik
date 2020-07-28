package pl.maciek_rychlinski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public class Controller {

    // parameters

    public static String txtCase;
    public static String mp3Case;
    DirectoryChooser directoryChooser = new DirectoryChooser();


    List<Memory> memoryList = new ArrayList<>();

    List<TextField> textFieldsList = new ArrayList<>();
    List<ComboBox<String>> comboBoxList = new ArrayList<>();


    @FXML
    private StackPane stackPane;
    @FXML
    private VBox layout;
    @FXML
    private ComboBox<String> choose1, choose2;
    @FXML
    private TextField path1, path2, path3;
    @FXML
    private Button testButton, saveButton;


    int queue = 4;

    Listener listener = new Listener();

    ObservableList<String> options = FXCollections.observableArrayList(
            ".mp3",
            ".pdf",
            ".zip",
            ".txt"
    );


    //threads

    //TODO uporządkować sprawę nowego i starego wątku


    Thread thread = new Thread(() -> {
        try {
            listener.listen(Path.of(path1.getText()));
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }

    });


    public Controller() throws IOException {
    }

    @FXML
    void initialize() {
        choose1.setItems(options);
        choose2.setItems(options);
    }


    // Set the path to listen:

    @FXML
    public void find(ActionEvent event) {
        File chosenFile = directoryChooser.showDialog(null);
        if (chosenFile != null) {
            Button button = (Button) event.getSource();
            String source = button.getId();
            switch (source) {
                case ("button1"):
                    path1.setText(chosenFile.getAbsolutePath());
                    memoryList.add(new Memory(path1.getText(), ""));
                    break;
                case ("button2"):
                    path2.setText(chosenFile.getAbsolutePath());
                    checkAndChose(choose1, path2);
                    memoryList.add(new Memory(path2.getText(), choose1.getValue()));
                    break;
                case ("button3"):
                    path3.setText(chosenFile.getAbsolutePath());
                    checkAndChose(choose2, path3);
                    memoryList.add(new Memory(path3.getText(), choose2.getValue()));
                    break;
            }

        }

    }


    private void checkAndChose(ComboBox<String> comboBox, TextField textField) {
        String extension = (String) comboBox.getValue();
        switch (extension) {
            case ".txt":
                txtCase = textField.getText();
                break;
            case ".mp3":
                mp3Case = textField.getText();
                break;
            default:
                System.out.println("Error");

        }
    }


    @FXML
    public void createNewButton() {


        TextField textField = new TextField();
        layout.getChildren().add(textField);
        textField.setId("path" + queue);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText("Rodzaj pliku");
        layout.getChildren().add(comboBox);
        comboBox.setId("choose" + queue);
        comboBox.setItems(options);

        EventHandler<ActionEvent> buttonEvent = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                File chosenFile = directoryChooser.showDialog(null);
                if (chosenFile != null) {
                    textField.setText(chosenFile.getAbsolutePath());
                    checkAndChose(comboBox, textField);

                    // save (serialization)

                    memoryList.add(new Memory(textField.getText(), comboBox.getValue()));

                    // save extra options in extra lists:

                    textFieldsList.add(textField);
                    comboBoxList.add(comboBox);

                }

            }
        };


        Button exmButton = new Button("Wybierz lokalizację");
        layout.getChildren().add(exmButton);
        exmButton.setId("button" + queue);
        exmButton.addEventHandler(ActionEvent.ACTION, buttonEvent);

        queue += 1;
    }


    // save the chosen options


    public void saveMemory() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("memory.bin");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(memoryList);
        fileOutputStream.close();
        objectOutputStream.close();

    }


    // load the chosen options together with start the app


    public void checkMemory() throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream("memory.bin");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        List<Memory> memoryList = (List<Memory>) objectInputStream.readObject();
        fileInputStream.close();
        objectInputStream.close();

        path1.setText(memoryList.get(0).path);
        thread.start();
        path2.setText(memoryList.get(1).path);
        choose1.setPromptText(memoryList.get(1).extension);
        choose1.setValue(memoryList.get(1).extension);
        checkAndChose(choose1, path2);
        path3.setText(memoryList.get(2).path);
        choose2.setPromptText(memoryList.get(2).extension);
        choose2.setValue(memoryList.get(2).extension);
        checkAndChose(choose2, path3);


        // Add extra options

        if (memoryList.size() >= 3) {
            for (int i = 3; i < memoryList.size(); i++) {
                TextField textField = new TextField();
                textField.setText(memoryList.get(i).path);
                layout.getChildren().add(textField);
            }
        }

        for (Memory memor : memoryList) {
            System.out.println(memor.getPath());
        }

    }

}

