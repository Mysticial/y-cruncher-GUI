/* DialogBoxes.java
 * 
 * Author           : Alexander J. Yee
 * Date Created     : 03/15/2018
 * Last Modified    : 03/15/2018
 * 
 */

package SampleGUI;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogBoxes{
    public static void errorBox(String text, boolean fatal){
        showGenericBox("Error", text, "Ok", fatal);
    }
    public static void errorBox(Exception e, boolean fatal){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        errorBox("Oops... And internal error has occurred...\n\n" + sw.toString(), fatal);
    }

    public static void showGenericBox(String title, String body, String buttonText, boolean fatal){
        if (!Platform.isFxApplicationThread()){
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    showGenericBox(title, body, buttonText, fatal);
                }
            });
            return;
        }

        Stage stage = new Stage();
        if (fatal){
            stage.setOnCloseRequest(event -> System.exit(1));
        }
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);

        Text bodyText = new Text(body);
        Button button = new Button(buttonText);

        bodyText.setFont(new Font("Arial", 15));
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                stage.close();
                if (fatal){
                    System.exit(1);
                }
            }
        });

        final VBox vbox = new VBox();
        VBox.setMargin(bodyText, new Insets(5, 0, 10, 0));
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(bodyText, button);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
}
