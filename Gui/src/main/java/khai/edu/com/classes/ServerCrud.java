package khai.edu.com.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerCrud {
    public static String deleteServer(Statement statement, int serversId){
        if(!serversIdExist(statement, serversId)){
            return serversId + " servers id doesn`t exist exception";
        }
        try {
            statement.executeUpdate("delete from servers where serversId=" + serversId);
        } catch (SQLException e) {
            return "Sql exception Server Delete";
        }
        return "Successful delete";
    }

    public static String updateServer(Statement statement, int serversId, String newName, int newServersId){
        if (!serversIdExist(statement, serversId)) {
            return serversId + " id doesn`t exist in servers";
        }
        try {
            newName = String.valueOf('\'') + newName + String.valueOf('\'');
            statement.executeUpdate("update servers set serversId = " + newServersId +
                    ", name=" + newName + " where serversId=" + serversId);
        } catch (SQLException e) {
            if (serversIdExist(statement, newServersId)) {
                return newServersId + " id already exists in servers exception";
            }
        }
        return "Successful update";
    }

    public static String insertServer(Statement statement, String name){
        try{
            name = String.valueOf('\'') + name + String.valueOf('\'');
            statement.execute("insert into servers(name) values(" + name+")");
        }catch (SQLException e){
            System.out.println("Exception insert");
        }
        return "Successful insert";
    }

    public static String selectServers(Statement statement){
        String result = "";
        try {
            ResultSet rs = statement.executeQuery("Select * from servers");
            while (rs.next()) {
                result += "Id = " + rs.getInt("serversId") + " " + rs.getString("name") + "\n";
            }
        } catch (SQLException e) {
            return "Select exception";
        }
        return result;
    }
    public static boolean serversIdExist(Statement statement, Integer serversId) {
        try {
            ResultSet rs = statement.executeQuery("Select serversId from servers");
            while (rs.next()) {
                if (rs.getInt("serversId") == serversId) return true;
            }
        } catch (SQLException e) {
            System.out.println("Exception serversIdExists");
        }
        return false;
    }
}
