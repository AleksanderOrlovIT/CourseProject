package khai.edu.com.classes;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBCRUDTest {
    int n = 0;
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    DBCRUDTest() throws SQLException {
        ResultSet rs = statement.executeQuery("select * from dbs");
        while(!rs.isLast()) rs.next();
        n = rs.getInt("DBId");
    }

    @Test
    void selectDBs() throws SQLException {
        String result = "";
        ResultSet rs = statement.executeQuery("select  dbs.DBId, dbs.name, servers.serversId, servers.name" +
                " from dbs inner join servers on servers.serversId = dbs.serversId;");
        while (rs.next()) {
            result += "DBId = " + rs.getInt("DBId") + " " + rs.getString("dbs.name") +
                    " serversId = " + rs.getString("servers.serversId") + " " + rs.getString("servers.name")
                    + "\n";
        }
        Assertions.assertEquals(result, DBCRUD.selectDBs(statement));
    }

    @Test @Order(1)
    void insertDB() {
        Assertions.assertEquals("Successful insert", DBCRUD.insertDB(statement, "newDB", 1));
        n++;
    }

    @Test @Order(2)
    void DBIdExist() {
        Assertions.assertEquals(true, DBCRUD.DBIdExist(statement, n));
    }

    @Test @Order(3)
    void updateDbsServer() {
        Assertions.assertEquals("Successful dbs servers id update",
                DBCRUD.updateDbsServer(statement, n, 2));
    }

    @Test @Order(4)
    void updateDbsNameAndId() {
        Assertions.assertEquals("Successful update DB name and id",
                DBCRUD.updateDbsNameAndId(statement, n,"newDBName", n));
    }

    @Test @Order(5)
    void deleteDB() {
        Assertions.assertEquals("Successful delete", DBCRUD.deleteDB(statement, n));
    }
}