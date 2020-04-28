package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class MainForm {

    @FXML
    private Circle circleFrame;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text firstnameFied;

    @FXML
    private Text lastnameField;

    @FXML
    private Text loginField;

    @FXML
    void initialize() {
        assert firstnameFied != null : "fx:id=\"firstnameFied\" was not injected: check your FXML file 'MainForm.fxml'.";
        assert lastnameField != null : "fx:id=\"lastnameField\" was not injected: check your FXML file 'MainForm.fxml'.";
        assert loginField != null : "fx:id=\"loginField\" was not injected: check your FXML file 'MainForm.fxml'.";

        Image im = new Image(getClass().getResource("/assets/man.png").toString(),
                false);
        circleFrame.setFill(new ImagePattern(im));


    }
}
