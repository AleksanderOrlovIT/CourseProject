package khai.edu.com.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import khai.edu.com.classes.OSCRUD;
import khai.edu.com.classes.ServerCrud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextArea output;
    @FXML
    private TextField ServerId;
    @FXML
    private TextField ServerName;
    @FXML
    private TextField newServerId;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    public ServerController() throws SQLException {
    }

    public void SelectServer(ActionEvent actionEvent) {
        output.setText(ServerCrud.selectServers(statement));
    }

    public void insertServer(ActionEvent actionEvent) {
        output.setText(ServerCrud.insertServer(statement, ServerName.getText()));
    }

    public void UpdateServer(ActionEvent actionEvent) {
        try {
            output.setText(ServerCrud.updateServer(statement, Integer.parseInt(ServerId.getText()),
                    ServerName.getText(), Integer.parseInt(newServerId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong input");
        }
    }

    public void DeleteServer(ActionEvent actionEvent) {
        try {
            output.setText(ServerCrud.deleteServer(statement, Integer.parseInt(ServerId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong Input");
        }
    }

    public void goBackServer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
