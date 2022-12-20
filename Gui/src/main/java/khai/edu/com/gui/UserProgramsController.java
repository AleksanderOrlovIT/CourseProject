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
import khai.edu.com.classes.PDBSCRUD;
import khai.edu.com.classes.UserProgramsCRUD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserProgramsController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextArea output;
    @FXML
    private TextField userId;
    @FXML
    private TextField SPId;
    @FXML
    private TextField OSId;
    @FXML
    private TextField newUserId;
    @FXML
    private TextField newSPId;
    @FXML
    private TextField newOSId;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    public UserProgramsController() throws SQLException {
    }

    public void selectUserPrograms(ActionEvent actionEvent) {
        output.setText(UserProgramsCRUD.selectUserPrograms(statement));
    }

    public void insertUserPrograms(ActionEvent actionEvent) {
        try{
            output.setText(UserProgramsCRUD.insertUserPrograms(statement, Integer.parseInt(userId.getText()),
                    Integer.parseInt(SPId.getText()), Integer.parseInt(OSId.getText())));
        }catch (NumberFormatException e){
            output.setText("Server id has to be a number");
        }
    }

    public void updateUserPrograms(ActionEvent actionEvent) {
        try {
            output.setText(UserProgramsCRUD.updateUserProgram(statement, Integer.parseInt(userId.getText()),
                    Integer.parseInt(SPId.getText()), Integer.parseInt(OSId.getText()),
                    Integer.parseInt(newUserId.getText()), Integer.parseInt(newSPId.getText()),
                    Integer.parseInt(newOSId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong input");
        }
    }

    public void deleteUserPrograms(ActionEvent actionEvent) {
        try {
            output.setText(UserProgramsCRUD.deleteUserProgram(statement, Integer.parseInt(userId.getText()),
                    Integer.parseInt(SPId.getText()), Integer.parseInt(OSId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong Input");
        }
    }
    public void goBackUserPrograms(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
