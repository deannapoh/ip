package boo.misc;

import java.io.IOException;

import boo.Boo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Boo using FXML.
 */
public class Main extends Application {

    private Boo boo = new Boo();

    public Main() throws BooException {
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBoo(boo);  // inject the Boo instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
