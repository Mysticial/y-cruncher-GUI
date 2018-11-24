/* Selector.java
 * 
 * Author           : Alexander J. Yee
 * Date Created     : 03/15/2018
 * Last Modified    : 03/15/2018
 * 
 */

package SampleGUI;

import java.lang.NumberFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Selector{
    private BinaryOption binary;
    private int port = 30000;
    private boolean enableDebugPrinting = true;
    private String command;

    private final Stage stage;
    private final HBox portRow;
    private final HBox binarySelectRow;
    private final HBox debugPrintingRow;
    private final TextField runCommand;
    private final Button runButton;

    public Selector(Stage stage){
        this.stage = stage;
        stage.setTitle(Settings.PROGRAM_NAME);
        stage.setHeight(250);
        stage.setWidth(400);

        final Label label = new Label("Configure y-cruncher Server");
        label.setFont(new Font("Arial", 20));

        portRow = makePortRow();
        binarySelectRow = makeBinarySelectRow();
        debugPrintingRow = makeDebugPrintingRow();
        runCommand = makeRunCommand();
        runButton = makeRunButton();

        updateCommand();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.getChildren().addAll(
            label,
            binarySelectRow,
            portRow,
            debugPrintingRow,
            runCommand,
            runButton
        );
        vbox.setAlignment(Pos.CENTER);
        VBox.setMargin(label, new Insets(0, 0, 10, 0));
        VBox.setMargin(binarySelectRow, new Insets(5, 0, 5, 0));
        VBox.setMargin(runCommand, new Insets(5, 0, 10, 0));

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private void updateCommand(){
        String cmd = this.binary.path;
        if (cmd.indexOf(" ") != -1){
            cmd = "\"" + cmd + "\"";
        }
        cmd += " slave:0 -port:" + port + " -ctype:json";
        if (enableDebugPrinting){
            cmd += " -printtraffic:1";
        }
        command = cmd;
        runCommand.setText(cmd);
    }

    private static class BinaryOption{
        public final String name;
        public final String path;

        public BinaryOption(String name, String path){
            this.name = name;
            this.path = path;
        }

        public String toString(){
            return name;
        }
    }

    private static final String[] BINARIES_LIST = {
        "00-x86",
        "04-P4P",
        "05-A64 ~ Kasumi",
        "07-PNR ~ Nagisa",
        "08-NHM ~ Ushio",
        "11-SNB ~ Hina",
        "11-BD1 ~ Miyu",
        "13-HSW ~ Airi",
        "14-BDW ~ Kurumi",
        "16-KNL",
        "17-ZN1 ~ Yukina",
        "17-SKX ~ Kotori",
        "18-CNL",
    };
    public static BinaryOption[] findBinaries(){
        ArrayList<BinaryOption> binaries = new ArrayList<BinaryOption>();
        binaries.add(new BinaryOption("Auto-Select", "y-cruncher"));

        File directory = new File("Binaries/");
        File[] files = directory.listFiles();

        if (files != null){
            HashMap<String, File> fileMap = new HashMap<String, File>();
            for (File file : files){
                fileMap.put(file.getName(), file);
            }

            for (String binary : BINARIES_LIST){
                String name = binary;
                File file = fileMap.get(name);
                if (file != null){
                    binaries.add(new BinaryOption(binary, file.getPath()));
                }

                name += ".exe";
                file = fileMap.get(name);
                if (file != null){
                    binaries.add(new BinaryOption(binary, file.getPath()));
                }
            }
        }

        BinaryOption[] out = new BinaryOption[binaries.size()];
        binaries.toArray(out);
        return out;
    }

    private HBox makeBinarySelectRow(){
        Text label = new Text("Binary Select: ");

        ObservableList<BinaryOption> binaries = FXCollections.observableArrayList();
        for (BinaryOption option : findBinaries()){
            binaries.add(option);
        }
        ChoiceBox<BinaryOption> box = new ChoiceBox<BinaryOption>(binaries);
        box.setValue(binaries.get(0));
        binary = binaries.get(0);
        box.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number newValue){
                binary = box.getItems().get(newValue.intValue());
                updateCommand();
            }
        });

        HBox row = new HBox();
        row.getChildren().addAll(label, box);
        row.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(label, new Insets(0, 10, 0, 10));
        return row;
    }
    private HBox makePortRow(){
        Text label = new Text("Port Number: ");

        TextField field = new TextField();
        field.setMaxWidth(75);
        field.setEditable(true);
        field.setText(port + "");
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                //  Taken from: https://stackoverflow.com/a/30796829/922184
                if (!newValue.matches("\\d*")) {
                    newValue = newValue.replaceAll("[^\\d]", "");
                }
                try{
                    port = Integer.parseInt(newValue);
                    if (port > 65535){
                        port = 65535;
                    }
                }catch (NumberFormatException e){
                    port = 30000;
                }
                field.setText(port + "");
                updateCommand();
            }
        });

        HBox row = new HBox();
        row.getChildren().addAll(label, field);
        row.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(label, new Insets(0, 10, 0, 10));
        return row;
    }
    private HBox makeDebugPrintingRow(){
        Text label = new Text("Enable Debug Printing: ");

        final CheckBox checkBox = new CheckBox();
//        checkBox.setText("Enable Debug Printing");
        checkBox.allowIndeterminateProperty().set(false);
        checkBox.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0){
                enableDebugPrinting = checkBox.isSelected();
                updateCommand();
            }
        });
        checkBox.selectedProperty().set(enableDebugPrinting);

        HBox row = new HBox();
        row.getChildren().addAll(label, checkBox);
        row.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(label, new Insets(5, 10, 5, 10));
        return row;
    }
    private TextField makeRunCommand(){
        TextField field = new TextField();
        field.setEditable(false);
        return field;
    }
    private Button makeRunButton(){
        Button button = new Button();
        button.setText("Start y-cruncher");
//        button.setFont(new Font("Arial", 15));
        button.setStyle("-fx-font-weight: bold");
        button.setPadding(new Insets(5, 10, 5, 10));
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                stage.close();
                Connection connection;
                try{
                    connection = new Connection(command, port, true);
                    new MainMenu(connection, stage);
                }catch (IOException e){
                    DialogBoxes.errorBox(e, false);
                }
            }
        });
        return button;
    }
}
