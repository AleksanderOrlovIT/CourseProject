package khai.edu.com.classes;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserProgramsCRUDTest {
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();

    UserProgramsCRUDTest() throws SQLException {
    }

    @Test
    void selectUserPrograms() throws SQLException {
       String result = "";
        ResultSet rs = statement.executeQuery("select user_programs.userId, users.userName, user_programs.SPID," +
                " software_products.name, user_programs.OSId, operating_systems.name from user_programs " +
                "inner join users on users.userId = user_programs.userId " +
                "inner join software_products on software_products.SPId = user_programs.SPId " +
                "inner join operating_systems on operating_systems.OSId = user_programs.OSId;");
        while (rs.next()) {
            result += "UserId = " + rs.getInt("userId") + " " + rs.getString("users.userName")
                    + " SPId = " + rs.getInt("SPId") + " " + rs.getString("software_products.name") + " OSId = "
                    + rs.getInt("OSId") + " " + rs.getString("operating_systems.name") + "\n";
        }
        Assertions.assertEquals(result, UserProgramsCRUD.selectUserPrograms(statement));
    }

    @Test @Order(1)
    void insertUserPrograms() {
        Assertions.assertEquals("Successful insert",
                UserProgramsCRUD.insertUserPrograms(statement, 1, 2, 3));
    }

    @Test @Order(2)
    void isUserProgramExists() {
        Assertions.assertEquals(true,
                UserProgramsCRUD.isUserProgramExists(statement, 1, 2, 3));
    }

    @Test @Order(3)
    void updateUserProgram() {
        Assertions.assertEquals("Successful update",
                UserProgramsCRUD.updateUserProgram(statement, 1, 2, 3,
                        1, 2, 2));
    }

    @Test @Order(4)
    void deleteUserProgram() {
        Assertions.assertEquals("Successful delete",
                UserProgramsCRUD.deleteUserProgram(statement,1,2, 2));
    }
}