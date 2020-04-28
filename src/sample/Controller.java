package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller {

    DBServer.Users users;

    Text errorText = new Text();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane form;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInBt;

    @FXML
    private Text goToRegistration;

    @FXML
    void registrationTxtClick(MouseEvent event) throws IOException {
        Transition.hideWindow(goToRegistration);
        Transition.openWindow(getClass()
                .getResource("registrationForm.fxml"));
    }

    boolean checkFillTextField(TextField tf) {
        if (tf != null && tf.getText().equals("")) {
            tf.setStyle("-fx-border-color:red");
            return false;
        } else if (tf != null) {
            tf.setStyle("-fx-border-color:green");
            return true;
        }
        return false;
    }

    void showErrorText(String txt) {
        errorText.setText(txt);
        double etWidth = errorText.getLayoutBounds().getWidth();
        errorText.setLayoutY(150.0);
        errorText.setLayoutX((form.getWidth() - etWidth) / 2);
    }

    @FXML
    void signInBtClick(MouseEvent event) throws IOException {
        if (checkFillTextField(loginField) &&
                checkFillTextField(passwordField)) {
            if (users.select(loginField.getText(), passwordField.getText()) != null){
                ObservableList <Node> children = form.getChildren();
                children.remove(children.size()-1);
                Transition.hideWindow(signInBt);
                Transition.openWindow(getClass().getResource("MainForm.fxml"));
            } else {
                showErrorText("Пользователь не найден!");
            }
        }
    }

    @FXML
    void initialize() {
        errorText.setFill(Paint.valueOf("#fd0000"));
        form.getChildren().add(errorText);
        DBServer dbServer = new DBServer();
        users = dbServer.new Users();
    }
}
