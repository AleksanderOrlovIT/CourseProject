package khai.edu.com.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import khai.edu.com.classes.UsersCRUD;

import java.io.IOException;
import java.sql.*;

public class UsersController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextArea output;
    @FXML
    private TextField userId;
    @FXML
    private TextField userName;
    @FXML
    private TextField newUserId;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();


    public UsersController() throws SQLException {
    }

    public void SelectUser(ActionEvent actionEvent) {
        output.setText(UsersCRUD.selectUsers(statement));
    }

    public void InsertUser(ActionEvent actionEvent) {
        output.setText(UsersCRUD.insertUsers(statement, userName.getText()));
    }

    public void UpdateUser(ActionEvent actionEvent) {
        try {
            output.setText(UsersCRUD.updateUsers(statement, Integer.parseInt(userId.getText()),
                    userName.getText(), Integer.parseInt(newUserId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong Input");
        }
    }

    public void DeleteUser(ActionEvent actionEvent) {
        try {
            output.setText(UsersCRUD.deleteUsers(statement, Integer.parseInt(userId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong Input");
        }
    }

    public void goBackUsers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
