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
import khai.edu.com.classes.ServerCrud;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextArea output;
    @FXML
    private TextField DBId;
    @FXML
    private TextField DBName;
    @FXML
    private TextField newDBId;
    @FXML
    private TextField ServerId;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    public DBController() throws SQLException {
    }

    public void SelectDBS(ActionEvent actionEvent) {
        output.setText(DBCRUD.selectDBs(statement));
    }

    public void insertDB(ActionEvent actionEvent) {
        try{
            output.setText(DBCRUD.insertDB(statement, DBName.getText(), Integer.parseInt(ServerId.getText())));
        }catch (NumberFormatException e){
            output.setText("Server id has to be a number");
        }
    }

    public void UpdateDB(ActionEvent actionEvent) {
        try {
            output.setText(DBCRUD.updateDbsNameAndId(statement, Integer.parseInt(DBId.getText()), DBName.getText(),
                    Integer.parseInt(newDBId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong input");
        }
    }

    public void DeleteDB(ActionEvent actionEvent) {
        try {
            output.setText(DBCRUD.deleteDB(statement, Integer.parseInt(DBId.getText())));
        }catch (NumberFormatException e){
            output.setText("Wrong Input");
        }
    }

    public void updateDBSServer(ActionEvent actionEvent) {
        try {
            output.setText(DBCRUD.updateDbsServer(statement, Integer.parseInt(DBId.getText())
                    ,Integer.parseInt(ServerId.getText())));
        }catch (NumberFormatException e){
            output.setText("Server id has to be Integer");
        }
    }
    public void goBackDB(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
