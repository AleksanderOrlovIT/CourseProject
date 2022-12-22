package khai.edu.com.classes;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PDBSCRUDTest {
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    PDBSCRUDTest() throws SQLException {
    }

    @Test
    void selectPDB() throws SQLException {
        String result = "";
        ResultSet rs = statement.executeQuery("select program_databases.SpId, software_products.name," +
                " program_databases.DBId, dbs.name from program_databases " +
                "inner join software_products on software_products.SPId = program_databases.SPId " +
                "inner join dbs on dbs.DBId = program_databases.DBId;");
        while (rs.next()) {
            result += "SPId = " + rs.getInt("program_databases.SPId") + " "
                    + rs.getString("software_products.name") + " DBId = "
                    + rs.getString("program_databases.DBId") + " " + rs.getString("dbs.name") + "\n";
        }
        Assertions.assertEquals(result, PDBSCRUD.selectPDB(statement));
    }

    @Test @Order(1)
    void insertPDB() {
        Assertions.assertEquals("Successful insert", PDBSCRUD.insertPDB(statement, 1, 2));
    }

    @Test @Order(2)
    void isPDBExists() {
        Assertions.assertEquals(true, PDBSCRUD.isPDBExists(statement, 1, 2));
    }

    @Test @Order(3)
    void updatePDB() {
        Assertions.assertEquals("Successful update",
                PDBSCRUD.updatePDB(statement, 1, 2, 1, 3));
    }

    @Test @Order(4)
    void deletePDB() {
        Assertions.assertEquals("Successful delete", PDBSCRUD.deletePDB(statement, 1, 3));
    }
}