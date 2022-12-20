package khai.edu.com;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/CourseWorkDB", "root", "root");
            Statement statement = connection.createStatement();
            UserPrograms.deleteUserProgram(statement, 1,1,2);
            UserPrograms.selectUserPrograms(statement);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}