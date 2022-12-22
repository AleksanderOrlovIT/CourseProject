package khai.edu.com.classes;

import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsersCRUDTest {
    Connection connection = DriverManager
            .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
    Statement statement = connection.createStatement();
    int n = 1;

    UsersCRUDTest() throws SQLException {
        ResultSet rs = statement.executeQuery("select * from users");
        while(!rs.isLast()) rs.next();
        n = rs.getInt("userId");
    }

    @Test
    void selectUsersTrue() throws SQLException {
        String result = "";
        ResultSet rs = statement.executeQuery("Select * from users");
        while (rs.next()) {
            result += "Id = " + rs.getInt("userId") + " " + rs.getString("username") + "\n";
        }
        Assertions.assertEquals(result, UsersCRUD.selectUsers(statement));
    }
    @Test @Order(1)
    void insertUsers() {
        Assertions.assertEquals("Insert correct", UsersCRUD.insertUsers(statement, "user"));
        n++;
    }
    @Test @Order(2)
    void userIdExistTrue() {
        Assertions.assertEquals(true, UsersCRUD.userIdExist(statement, n));
    }
    @Test
    void userIdExistFalse() {
        Assertions.assertEquals(false, UsersCRUD.userIdExist(statement, 10000));
    }

    @Test @Order(3)
    void updateUsers() {
        Assertions.assertEquals("Successful update",
                UsersCRUD.updateUsers(statement, n, "Sasha", n));
    }
    @Test @Order(4)
    void deleteUsers() {
        Assertions.assertEquals("Successful delete", UsersCRUD.deleteUsers(statement, n));
    }
}