/* StressTest.java
 * 
 * Author           : Alexander J. Yee
 * Date Created     : 03/16/2018
 * Last Modified    : 03/17/2018
 * 
 */

package SampleGUI;

import java.io.IOException;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StressTest implements Connection.Listener{
    private final Connection connection;
    private final Stage stage;
    private final Label labelTitle;

    //  Renderers
    private CheckBox rAllocateLocally;
    private TextField rSeconds;
    private CheckBox rStopOnError;

    //  Parameters
    private boolean pAllocateLocally;
    private TreeSet<Integer> pLogicalCores;
    private long pTotalMemory;
    private int pSeconds;
    private boolean pStopOnError;
    private TreeSet<String> pTests;

    private boolean updating = false;

    public StressTest(Connection connection, Stage stage) throws IOException{
        this.connection = connection;
        this.stage = stage;
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setTitle(Settings.PROGRAM_NAME);
        stage.setHeight(220);
        stage.setWidth(400);

        labelTitle = new Label("Component Stress Tester");
        labelTitle.setFont(new Font("Arial", 20));

        rAllocateLocally = makeAllocateLocally();
        HBox seconds = makeSeconds();
        rStopOnError = makeStopOnError();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.getChildren().addAll(
            labelTitle,
            rAllocateLocally,
            seconds,
            rStopOnError
        );
        vbox.setAlignment(Pos.CENTER);

        connection.addListener(this);
        sendDefaultsRequest();

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    public boolean onObject(JSONObject obj){
        if (obj.has("Response")){
            switch ((String)obj.getString("Response")){
            case "StressTestDefaults":
            case "StressTestQuery":
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        set(
                            obj.getJSONObject("Parameters"),
                            obj.getJSONObject("Renderer")
                        );
                    }
                });
                return true;
            }
        }
        return false;
    }

    private void sendDefaultsRequest() throws IOException{
        JSONObject object = new JSONObject();
        object.put("Action", "StressTest");
        object.put("Option", "Defaults");
        connection.sendObject(object);
    }
    private void sendParamsRequest(){
        JSONObject request = new JSONObject();
        request.put("Action", "StressTest");
        request.put("Option", "Query");
        JSONObject obj = new JSONObject();
        Connection.putStringBoolean(obj, "AllocateLocally", pAllocateLocally);
        {
            JSONArray cores = new JSONArray();
            for (int core : pLogicalCores){
                cores.put(core);
            }
            obj.put("LogicalCores", cores);
        }
        obj.put("TotalMemory", pTotalMemory);
        obj.put("Seconds", pSeconds);
        Connection.putStringBoolean(obj, "StopOnError", pStopOnError);
        {
            JSONArray tests = new JSONArray();
            for (String test : pTests){
                tests.put(test);
            }
            obj.put("Tests", tests);
        }
        request.put("StressTest", obj);
        try{
            connection.sendObject(request);
        }catch (IOException e){
            DialogBoxes.errorBox(e, true);
        }
    }

    private void set(JSONObject params, JSONObject renderer){
        //  This MUST be called on the UI thread.
        updating = true;
        {
            boolean value = Connection.getStringBoolean(params, "AllocateLocally");
            if (pAllocateLocally != value){
                pAllocateLocally = value;
                rAllocateLocally.selectedProperty().set(value);
            }
        }
        {
            pLogicalCores = new TreeSet<Integer>();
            JSONArray cores = params.getJSONArray("LogicalCores");
            for (int c = 0; c < cores.length(); c++){
                pLogicalCores.add(cores.getInt(c));
            }
        }
        {
            long value = params.getLong("TotalMemory");
            if (pTotalMemory != value){
                pTotalMemory = value;
            }
        }
        {
            int value = params.getInt("Seconds");
            if (pSeconds != value){
                pSeconds = value;
                rSeconds.setText(pSeconds + "");
            }
        }
        {
            boolean value = Connection.getStringBoolean(params, "StopOnError");
            if (pStopOnError != value){
                pStopOnError = value;
                rStopOnError.selectedProperty().set(value);
            }
        }
        {
            pTests = new TreeSet<String>();
            JSONArray tests = params.getJSONArray("Tests");
            for (int c = 0; c < tests.length(); c++){
                pTests.add(tests.getString(c));
            }
        }
        updating = false;
    }

    private CheckBox makeAllocateLocally(){
        final CheckBox checkBox = new CheckBox();
        checkBox.setText("Allocate memory on local thread.");
        checkBox.allowIndeterminateProperty().set(false);
        checkBox.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0){
                if (updating){
                    return;
                }
                pAllocateLocally = checkBox.isSelected();
                sendParamsRequest();
            }
        });
        checkBox.selectedProperty().set(true);
        pAllocateLocally = true;
        return checkBox;
    }
    private HBox makeSeconds(){
        Text label = new Text("Time per Test (seconds): ");

        rSeconds = new TextField();
        rSeconds.setMaxWidth(75);
        rSeconds.setEditable(true);
        rSeconds.setText(pSeconds + "");
        rSeconds.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue){
                if (updating){
                    return;
                }
                //  Taken from: https://stackoverflow.com/a/30796829/922184
                if (!newValue.matches("\\d*")) {
                    newValue = newValue.replaceAll("[^\\d]", "");
                }
                try{
                    pSeconds = Integer.parseInt(newValue);
                }catch (NumberFormatException e){
                    pSeconds = 120;
                }
                rSeconds.setText(pSeconds + "");
                sendParamsRequest();
            }
        });

        HBox row = new HBox();
        row.getChildren().addAll(label, rSeconds);
        row.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(label, new Insets(0, 10, 0, 10));
        return row;
    }
    private CheckBox makeStopOnError(){
        final CheckBox checkBox = new CheckBox();
        checkBox.setText("Stop on Error");
        checkBox.allowIndeterminateProperty().set(false);
        checkBox.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg0){
                if (updating){
                    return;
                }
                pStopOnError = checkBox.isSelected();
                sendParamsRequest();
            }
        });
        checkBox.selectedProperty().set(true);
        pStopOnError = true;
        return checkBox;
    }
}
