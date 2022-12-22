package khai.edu.com.classes;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerCrudTest {

    int n = 0;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    ServerCrudTest() throws SQLException {
        ResultSet rs = statement.executeQuery("Select * from servers");
        while (!rs.isLast()) rs.next();
        n = rs.getInt("serversId");
    }

    @Test
    void selectServers() throws SQLException {
        String result = "";
        ResultSet rs = statement.executeQuery("Select * from servers");
        while (rs.next()) {
            result += "Id = " + rs.getInt("serversId") + " " + rs.getString("name") + "\n";
        }
        Assertions.assertEquals(result, ServerCrud.selectServers(statement));
    }

    @Test @Order(1)
    void insertServer() {
        Assertions.assertEquals("Successful insert", ServerCrud.insertServer(statement, "newServer"));
        n++;
    }

    @Test @Order(2)
    void serversIdExist() {
        Assertions.assertEquals(true, ServerCrud.serversIdExist(statement, n));
    }

    @Test @Order(3)
    void updateServer() {
        Assertions.assertEquals("Successful update",
                ServerCrud.updateServer(statement, n, "newServer2", n));
    }

    @Test @Order(4)
    void deleteServer() {
        Assertions.assertEquals("Successful delete", ServerCrud.deleteServer(statement, n));
    }
}