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
    void registrationTxtClick(MouseEvent event) {
        goToRegistration.getScene().getWindow().hide();
        try {
            Parent root = FXMLLoader.load(getClass()
                    .getResource("registrationForm.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void signInBtClick(MouseEvent event) throws FileNotFoundException {
        if (checkFillTextField(loginField) &&
                checkFillTextField(passwordField)) {
            File config = new File("config.txt");
            if (!config.exists()) {
                showErrorText("Необходимо зарегистрироваться!");
            } else {
                Scanner in = new Scanner(config);
                String login = in.nextLine(), password = in.nextLine();
                if (loginField.getText().equals(login) &&
                        passwordField.getText().equals(password)){
                    ObservableList <Node> children = form.getChildren();
                    children.remove(children.size()-1);
                    System.out.println("Пользователь найден");
                } else {
                    showErrorText("Пользователь не найден!");
                }
            }
        }
    }

    @FXML
    void initialize() {
        errorText.setFill(Paint.valueOf("#fd0000"));
        form.getChildren().add(errorText);
    }
}
