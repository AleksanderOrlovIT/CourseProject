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
import khai.edu.com.classes.SPCRUD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OSController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextArea output;
    @FXML
    private TextField OSId;
    @FXML
    private TextField OSName;
    @FXML
    private TextField newOSId;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    public OSController() throws SQLException {
    }

    public void SelectOS(ActionEvent actionEvent) {
        output.setText(OSCRUD.selectOS(statement));
    }

    public void insertOS(ActionEvent actionEvent) {
        output.setText(OSCRUD.insertOS(statement, OSName.getText()));
    }

    public void UpdateOS(ActionEvent actionEvent) {
        try {
            output.setText(OSCRUD.updateOS(statement, Integer.valueOf(OSId.getText()), OSName.getText(),
                    Integer.valueOf(newOSId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong input");
        }
    }

    public void DeleteOS(ActionEvent actionEvent) {
        try {
            output.setText(OSCRUD.deleteOS(statement, Integer.valueOf(OSId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong Input");
        }
    }

    public void goBackOS(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
