/* MainMenu.java
 * 
 * Author           : Alexander J. Yee
 * Date Created     : 03/15/2018
 * Last Modified    : 03/16/2018
 * 
 */

package SampleGUI;

import java.io.IOException;

import org.json.JSONObject;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainMenu implements Connection.Listener{
    private final Connection connection;
    private final Stage stage;
    private final Label labelTitle;
    private final Label labelVersion;
    private final Label labelTuning;
    private final Label labelISA;
    private final Button buttonStressTest;
    private final Button buttonCustomCompute;

    public MainMenu(Connection connection, Stage stage) throws IOException{
        this.connection = connection;
        this.stage = stage;
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setTitle(Settings.PROGRAM_NAME);
        stage.setHeight(220);
        stage.setWidth(400);

        labelTitle = new Label("Main Menu");
        labelTitle.setFont(new Font("Arial", 20));
//        labelMenu.setAlignment(Pos.CENTER_LEFT);

        labelVersion = new Label("y-cruncher Version:  Loading...");
        labelTuning = new Label("Tuning:  Loading...");
        labelISA = new Label("Instruction Set:  Loading...");

        buttonStressTest = makeStressTestButton();
        buttonCustomCompute = makeCustomComputeButton();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 10, 0, 10));
        vbox.getChildren().addAll(
                labelTitle, labelVersion, labelTuning, labelISA,
            buttonStressTest, buttonCustomCompute
        );
        vbox.setAlignment(Pos.CENTER);

        VBox.setMargin(labelTitle, new Insets(0, 0, 5, 0));
        VBox.setMargin(labelISA, new Insets(0, 0, 7, 0));
        VBox.setMargin(buttonStressTest, new Insets(5, 0, 5, 0));
        VBox.setMargin(buttonCustomCompute, new Insets(5, 0, 5, 0));

        connection.addListener(this);
        sendInfoRequest();

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    public static void runOnUiThread(Runnable runnable){
        if (Platform.isFxApplicationThread()){
            runnable.run();
        }else{
            Platform.runLater(runnable);
        }
    }

    public boolean onObject(JSONObject obj){
        if (obj.has("Response")){
            switch ((String)obj.getString("Response")){
            case "BinaryInfo":
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        JSONObject info = obj.getJSONObject("BinaryInfo");
                        labelVersion.setText("y-cruncher Version:  " + info.getString("VersionString"));
                        labelTuning.setText("Tuning:  " + info.getString("BuildName"));
                        labelISA.setText("Instruction Set:  " + info.getString("InstructionSet"));
                    }
                });
                return true;
            default:
                return false;
            }
        }
        return false;
    }

    private void sendInfoRequest() throws IOException{
        JSONObject object = new JSONObject();
        object.put("Action", "BinaryInfo");
        connection.sendObject(object);
    }

    private Button makeStressTestButton(){
        Button button = new Button();
        button.setText("Component Stress Tester");
        button.setStyle("-fx-font-weight: bold");
        button.setPadding(new Insets(5, 10, 5, 10));
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                stage.close();
                try{
                    new StressTest(connection, stage);
                }catch (IOException e){
                    DialogBoxes.errorBox(e, false);
                }
            }
        });
        return button;
    }
    private Button makeCustomComputeButton(){
        Button button = new Button();
        button.setText("Custom Compute a Constant");
        button.setStyle("-fx-font-weight: bold");
        button.setPadding(new Insets(5, 10, 5, 10));
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                stage.close();
                //  TODO
            }
        });
        return button;
    }
}
