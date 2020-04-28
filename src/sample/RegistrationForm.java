package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegistrationForm {

    DBServer.Users users;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInBt;

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordFieldRepeat;

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

    boolean checkPasswordFields(TextField pass1, TextField pass2){
        if (pass1.getText().equals(
                pass2.getText()
        )){
            pass1.setStyle("-fx-border-color:green");
            pass2.setStyle("-fx-border-color:green");
            return true;
        } else {
            pass1.setStyle("-fx-border-color:red");
            pass2.setStyle("-fx-border-color:red");
            return false;
        }
    }

    @FXML
    void signUpBtClick(MouseEvent event) throws IOException {
        if (checkFillTextField(nameField) &&
                checkFillTextField(lastNameField) &&
                checkFillTextField(loginField) &&
                checkFillTextField(passwordField) &&
                checkFillTextField(passwordFieldRepeat) &&
                checkPasswordFields(passwordField, passwordFieldRepeat)
        ){

            if (users.checkFreeLogin(loginField.getText())) {
                users.insert(nameField.getText(), lastNameField.getText(),
                        loginField.getText(), passwordField.getText());

                Transition.hideWindow(signInBt);
                Transition.openWindow(getClass().getResource("sample.fxml"));
            } else {
                loginField.setStyle("-fx-border-color:red");
            }
        }
    }

    @FXML
    void initialize() {
        DBServer dbServer = new DBServer();
        users = dbServer.new Users();
    }
}
