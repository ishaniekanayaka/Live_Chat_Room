package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.server.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class loginController {

    @FXML
    private JFXButton btnlogin;

    @FXML
    private TextField txtUserName;

    private Server server;

    public  static List<String> names = new ArrayList<>();
    public void initialize() {
        new Thread(() -> {
            try {
                server = Server.getInstance();
                server.makeSocket();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        if (!txtUserName.getText().isEmpty()) {
            names.add(txtUserName.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/View/client_form.fxml"))));

            stage.centerOnScreen();
            stage.setTitle(txtUserName.getText() + "'s Chat Room");
            stage.setOnCloseRequest(windowEvent -> {
                Iterator<String> iterator = names.iterator();
                while (iterator.hasNext()) {
                    String nm = iterator.next();
                    if (stage.getTitle().startsWith(nm)) {
                        iterator.remove();
                        System.out.println(nm + " left from the chat !!!");
                        return;
                    }
                }
            });
            stage.show();

            txtUserName.setText(" ");
        } else {
            new Alert(Alert.AlertType.ERROR, "User Name Is Required !!!").show();
        }
    }

    @FXML
    void userNameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

}
