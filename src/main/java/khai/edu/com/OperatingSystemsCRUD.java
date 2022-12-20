package khai.edu.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static khai.edu.com.Main.printSQLException;

public class OperatingSystemsCRUD {
    public static boolean deleteOS(Statement statement, int OSId){
        if(!OSIdExist(statement, OSId)){
            System.out.println("Software product id doesn`t exist exception");
            return false;
        }
        try {
            statement.executeUpdate("delete from operating_systems where OSId=" + OSId);
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public static boolean updateOS(Statement statement, int OSId, String newName, int newOSId){
        if (!OSIdExist(statement, OSId)) {
            System.out.println(OSId + " id doesn`t exist in software products");
            return false;
        }
        try {
            newName = String.valueOf('\'') + newName + String.valueOf('\'');
            statement.executeUpdate("update operating_systems set OSId = " + newOSId +
                    ", name=" + newName + " where OsId=" + OSId);
        } catch (SQLException e) {
            if (OSIdExist(statement, newOSId)) {
                System.out.println(newOSId + " id already exists in software products exception");
                return false;
            }
        }
        return true;
    }

    public static void insertOS(Statement statement, String name){
        try{
            name = String.valueOf('\'') + name + String.valueOf('\'');
            statement.execute("insert into operating_systems(name) values(" + name+")");
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    public static boolean selectOS(Statement statement){
        try {
            ResultSet rs = statement.executeQuery("Select * from operating_systems");
            while (rs.next()) {
                System.out.println("Id = " + rs.getInt("OSId") + " " + rs.getString("name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }
    public static boolean OSIdExist(Statement statement, Integer OSId) {
        try {
            ResultSet rs = statement.executeQuery("Select OSId from operating_systems");
            while (rs.next()) {
                if (rs.getInt("OSId") == OSId) return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
}
