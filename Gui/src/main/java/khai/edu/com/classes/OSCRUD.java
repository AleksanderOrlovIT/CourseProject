package khai.edu.com.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OSCRUD {
    public static String deleteOS(Statement statement, int OSId){
        if(!OSIdExist(statement, OSId)){
            return "Operation system id doesn`t exist exception";
        }
        try {
            statement.executeUpdate("delete from operating_systems where OSId=" + OSId);
        } catch (SQLException e) {
            return "Sql exception in deleteOs exception";
        }
        return "Successful delete";
    }

    public static String updateOS(Statement statement, int OSId, String newName, int newOSId){
        if (!OSIdExist(statement, OSId)) {
            return OSId + " id doesn`t exist in software products";
        }
        try {
            newName = String.valueOf('\'') + newName + String.valueOf('\'');
            statement.executeUpdate("update operating_systems set OSId = " + newOSId +
                    ", name=" + newName + " where OsId=" + OSId);
        } catch (SQLException e) {
            if (OSIdExist(statement, newOSId)) {
                return newOSId + " id already exists in software products exception";
            }
        }
        return "Successful update";
    }

    public static String insertOS(Statement statement, String name){
        try{
            name = String.valueOf('\'') + name + String.valueOf('\'');
            statement.execute("insert into operating_systems(name) values(" + name+")");
        }catch (SQLException e){
            return "Error insertOs";
        }
        return "Successful insert";
    }

    public static String selectOS(Statement statement){
        String result = "";
        try {
            ResultSet rs = statement.executeQuery("Select * from operating_systems");
            while (rs.next()) {
                result += "Id = " + rs.getInt("OSId") + " " + rs.getString("name") + "\n";
            }
        } catch (SQLException e) {
            return "Sql exception selecOS";
        }
        return result;
    }
    public static boolean OSIdExist(Statement statement, Integer OSId) {
        try {
            ResultSet rs = statement.executeQuery("Select OSId from operating_systems");
            while (rs.next()) {
                if (rs.getInt("OSId") == OSId) return true;
            }
        } catch (SQLException e) {
            System.out.println("Sql exception OSIdExists");
        }
        return false;
    }
}
