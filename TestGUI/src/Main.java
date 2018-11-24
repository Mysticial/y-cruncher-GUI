/* Main.java
 * 
 * Author           : Alexander J. Yee
 * Date Created     : 03/15/2018
 * Last Modified    : 03/15/2018
 * 
 */

import javafx.application.Application;
import javafx.stage.Stage;

import SampleGUI.*;


public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage){
        try{
            new Selector(stage);
        }catch(Exception e){
            DialogBoxes.errorBox(e, false);
        }
    }
}