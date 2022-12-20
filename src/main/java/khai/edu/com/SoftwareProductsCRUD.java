package khai.edu.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static khai.edu.com.Main.printSQLException;

public class SoftwareProductsCRUD {
    public static boolean deleteSP(Statement statement, int SPId) {
        if(!SPIdExist(statement, SPId)){
            System.out.println("Software product id doesn`t exist exception");
            return false;
        }
        try {
            statement.executeUpdate("delete from software_products where SPId=" + SPId);
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }
    public static boolean updateSP(Statement statement, int SPId, String newName, int newSPId) {
        if (!SPIdExist(statement, SPId)) {
            System.out.println(SPId + " id doesn`t exist in software products");
            return false;
        }
        try {
            newName = String.valueOf('\'') + newName + String.valueOf('\'');
            statement.executeUpdate("update software_products set SPId = " + newSPId +
                    ", name=" + newName + " where SPId=" + SPId);
        } catch (SQLException e) {
            if (SPIdExist(statement, newSPId)) {
                System.out.println(newSPId + " id already exists in software products exception");
                return false;
            }
        }
        return true;
    }

    public static void insertSP(Statement statement, String name){
        try{
            name = String.valueOf('\'') + name + String.valueOf('\'');
            statement.execute("insert into software_products(name) values(" + name+")");
        }catch (SQLException e){
            printSQLException(e);
        }
    }
    public static boolean selectSP(Statement statement) {
        try {
            ResultSet rs = statement.executeQuery("Select * from software_products");
            while (rs.next()) {
                System.out.println("Id = " + rs.getInt("SPId") + " " + rs.getString("name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }
    public static boolean SPIdExist(Statement statement, Integer SPId) {
        try {
            ResultSet rs = statement.executeQuery("Select SPId from software_products");
            while (rs.next()) {
                if (rs.getInt("SPId") == SPId) return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
}
