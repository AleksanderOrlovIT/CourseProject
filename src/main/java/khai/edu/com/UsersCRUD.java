package khai.edu.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static khai.edu.com.Main.printSQLException;

public class UsersCRUD {
    public static boolean deleteUsers(Statement statement, int userId) {
        if(!userIdExist(statement, userId)){
            System.out.println("User id doesn`t exist exception");
            return false;
        }
        try {
            statement.executeUpdate("delete from users where userId=" + userId);
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public static boolean updateUsers(Statement statement, int userId, String newUserName, int newUserId) {
        if (!userIdExist(statement, userId)) {
            System.out.println(userId + " id doesn`t exist in users");
            return false;
        }
        try {
            newUserName = String.valueOf('\'') + newUserName + String.valueOf('\'');
            statement.executeUpdate("update users set userId = " + newUserId +
                    ", username=" + newUserName + " where userId=" + userId);
        } catch (SQLException e) {
            if (userIdExist(statement, newUserId)) {
                System.out.println(newUserId + " id already exists in users exception");
                return false;
            }
        }
        return true;
    }

    public static boolean insertUsers(Statement statement, String userName) {
        try {
            userName = String.valueOf('\'') + userName + String.valueOf('\'');
            statement.execute("insert into users(username) values(" + userName + ")");
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public static boolean selectUsers(Statement statement) {
        try {
            String usersSelect = "Select * from users";
            ResultSet rs = statement.executeQuery(usersSelect);
            while (rs.next()) {
                System.out.println("Id = " + rs.getString("userId") + " " + rs.getString("username"));
            }
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public static boolean userIdExist(Statement statement, Integer UserId) {
        try {
            String usersSelect = "Select UserId from users";
            ResultSet rs = statement.executeQuery(usersSelect);
            while (rs.next()) {
                if (rs.getInt("UserId") == UserId) return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
}
