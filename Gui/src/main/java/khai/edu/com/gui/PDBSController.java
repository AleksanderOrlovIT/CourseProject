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
import khai.edu.com.classes.DBCRUD;
import khai.edu.com.classes.PDBSCRUD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PDBSController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextArea output;
    @FXML
    private TextField SPId;
    @FXML
    private TextField DBId;
    @FXML
    private TextField newSPId;
    @FXML
    private TextField newDBId;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    public PDBSController() throws SQLException {
    }

    public void selectPDBS(ActionEvent actionEvent) {
        output.setText(PDBSCRUD.selectPDB(statement));
    }

    public void insertPDBS(ActionEvent actionEvent) {
        try{
            output.setText(PDBSCRUD.insertPDB(statement, Integer.parseInt(SPId.getText()),
                    Integer.parseInt(DBId.getText())));
        }catch (NumberFormatException e){
            output.setText("Server id has to be a number");
        }
    }

    public void updatePDBS(ActionEvent actionEvent) {
        try {
            output.setText(PDBSCRUD.updatePDB(statement, Integer.parseInt(SPId.getText()),
                    Integer.parseInt(DBId.getText()), Integer.parseInt(newSPId.getText()),
                    Integer.parseInt(newDBId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong input");
        }
    }

    public void deletePDBS(ActionEvent actionEvent) {
        try {
            output.setText(PDBSCRUD.deletePDB(statement, Integer.parseInt(SPId.getText()),
                    Integer.parseInt(DBId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong Input");
        }
    }
    public void goBackPDBS(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
