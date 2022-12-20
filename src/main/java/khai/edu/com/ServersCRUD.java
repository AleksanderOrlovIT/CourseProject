package khai.edu.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static khai.edu.com.Main.printSQLException;

public class ServersCRUD {
    public static boolean deleteServer(Statement statement, int serversId){
        if(!serversIdExist(statement, serversId)){
            System.out.println(serversId + " servers id doesn`t exist exception");
            return false;
        }
        try {
            statement.executeUpdate("delete from servers where serversId=" + serversId);
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public static boolean updateServer(Statement statement, int serversId, String newName, int newServersId){
        if (!serversIdExist(statement, serversId)) {
            System.out.println(serversId + " id doesn`t exist in servers");
            return false;
        }
        try {
            newName = String.valueOf('\'') + newName + String.valueOf('\'');
            statement.executeUpdate("update servers set serversId = " + newServersId +
                    ", name=" + newName + " where serversId=" + serversId);
        } catch (SQLException e) {
            if (serversIdExist(statement, newServersId)) {
                System.out.println(newServersId + " id already exists in servers exception");
                return false;
            }
        }
        return true;
    }

    public static void insertServer(Statement statement, String name){
        try{
            name = String.valueOf('\'') + name + String.valueOf('\'');
            statement.execute("insert into servers(name) values(" + name+")");
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    public static boolean selectServers(Statement statement){
        try {
            ResultSet rs = statement.executeQuery("Select * from servers");
            while (rs.next()) {
                System.out.println("Id = " + rs.getInt("serversId") + " " + rs.getString("name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }
    public static boolean serversIdExist(Statement statement, Integer serversId) {
        try {
            ResultSet rs = statement.executeQuery("Select serversId from servers");
            while (rs.next()) {
                if (rs.getInt("serversId") == serversId) return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
}
