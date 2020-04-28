package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Transition {

    public static void openWindow(URL pathToFxml) throws IOException {
        Parent root = FXMLLoader.load(pathToFxml);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void hideWindow(Node node){
        node.getScene().getWindow().hide();
    }
}
