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
import khai.edu.com.classes.SPCRUD;
import khai.edu.com.classes.UsersCRUD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SPController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextArea output;
    @FXML
    private TextField SPId;
    @FXML
    private TextField SPName;
    @FXML
    private TextField newSPId;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    public SPController() throws SQLException {
    }

    public void SelectSP(ActionEvent actionEvent) {
        output.setText(SPCRUD.selectSP(statement));
    }

    public void insertSP(ActionEvent actionEvent) {
        output.setText(SPCRUD.insertSP(statement, SPName.getText()));
    }

    public void UpdateSP(ActionEvent actionEvent) {
        try {
            output.setText(SPCRUD.updateSP(statement, Integer.parseInt(SPId.getText()), SPName.getText(),
                    Integer.valueOf(newSPId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong input");
        }
    }

    public void DeleteSp(ActionEvent actionEvent) {
        try {
            output.setText(SPCRUD.deleteSP(statement, Integer.parseInt(SPId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong Input");
        }
    }

    public void goBackSP(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
