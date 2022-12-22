package khai.edu.com.classes;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SPCRUDTest {

    int n = 1;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    SPCRUDTest() throws SQLException {
        ResultSet rs = statement.executeQuery("Select * from software_products");
        while (!rs.isLast()) rs.next();
        n = rs.getInt("SPId");
    }

    @Test @Order(1)
    void insertSP() {
        Assertions.assertEquals("Successful insert", SPCRUD.insertSP(statement, "newSP"));
        n++;
    }
    @Test
    void selectSP() throws SQLException {
        String result = "";
        ResultSet rs = statement.executeQuery("Select * from software_products");
        while (rs.next()) {
            result += "Id = " + rs.getInt("SPId") + " " + rs.getString("name") + "\n";
        }
        Assertions.assertEquals(result, SPCRUD.selectSP(statement));
    }
    @Test @Order(2)
    void SPIdExist() {
        Assertions.assertEquals(true, SPCRUD.SPIdExist(statement, n));
    }
    @Test @Order(3)
    void updateSP() {
        Assertions.assertEquals("Successful update", SPCRUD.updateSP(statement, n, "newSP2", n));
    }

    @Test @Order(4)
    void deleteSP() {
        Assertions.assertEquals("Successful delete", SPCRUD.deleteSP(statement, n));
    }
}