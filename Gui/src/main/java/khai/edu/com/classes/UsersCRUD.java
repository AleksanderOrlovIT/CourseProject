package khai.edu.com.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersCRUD {
    public static String deleteUsers(Statement statement, int userId) {
        if(!userIdExist(statement, userId)){
            return "User id doesn`t exist exception";
        }
        try {
            statement.executeUpdate("delete from users where userId=" + userId);
        } catch (SQLException e) {
            return "Delete Error";
        }
        return "Successful delete";
    }

    public static String updateUsers(Statement statement, int userId, String newUserName, int newUserId) {
        if (!userIdExist(statement, userId)) {
            return userId + " id doesn`t exist in users";
        }
        try {
            newUserName = String.valueOf('\'') + newUserName + String.valueOf('\'');
            statement.executeUpdate("update users set userId = " + newUserId +
                    ", username=" + newUserName + " where userId=" + userId);
        } catch (SQLException e) {
            if (userIdExist(statement, newUserId)) {
                return newUserId + " id already exists in users exception";
            }
        }
        return "Successful update";
    }

    public static String insertUsers(Statement statement, String userName) {
        try {
            userName = String.valueOf('\'') + userName + String.valueOf('\'');
            statement.execute("insert into users(username) values(" + userName + ")");
        } catch (SQLException e) {
            return "Insert error";
        }
        return "Insert correct";
    }

    public static String selectUsers(Statement statement) {
        String result = "";
        try {
            ResultSet rs = statement.executeQuery("Select * from users");
            while (rs.next()) {
                result += "Id = " + rs.getString("userId") + " " + rs.getString("username") + "\n";
            }
        } catch (SQLException e) {
            return "error";
        }
        return result;
    }

    public static boolean userIdExist(Statement statement, Integer UserId) {
        try {
            String usersSelect = "Select UserId from users";
            ResultSet rs = statement.executeQuery(usersSelect);
            while (rs.next()) {
                if (rs.getInt("UserId") == UserId) return true;
            }
        } catch (SQLException e) {
            System.out.println("Error userIdExists");
        }
        return false;
    }
}
