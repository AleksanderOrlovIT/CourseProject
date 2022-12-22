package khai.edu.com.classes;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OSCRUDTest {
    int n = 1;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    OSCRUDTest() throws SQLException {
        ResultSet rs = statement.executeQuery("Select * from operating_systems");
        while(!rs.isLast()) rs.next();
        n = rs.getInt("OSId");
    }

    @Test @Order(1)
    void insertOS() {
        Assertions.assertEquals("Successful insert", OSCRUD.insertOS(statement, "newOS"));
        n++;
    }

    @Test @Order(2)
    void selectOS() throws SQLException {
        String result = "";
        ResultSet rs = statement.executeQuery("Select * from operating_systems");
        while (rs.next()) {
            result += "Id = " + rs.getInt("OSId") + " " + rs.getString("name") + "\n";

        }
        Assertions.assertEquals(result, OSCRUD.selectOS(statement));
    }
    @Test @Order(3)
    void OSIdExist() {
        Assertions.assertEquals(true, OSCRUD.OSIdExist(statement, n));
    }
    @Test @Order(4)
    void updateOS() {
        Assertions.assertEquals("Successful update", OSCRUD.updateOS(statement, n, "newOs2", n));
    }
    @Test @Order(5)
    void deleteOS() {
        Assertions.assertEquals("Successful delete", OSCRUD.deleteOS(statement, n));
    }
}