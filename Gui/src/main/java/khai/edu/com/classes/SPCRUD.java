package khai.edu.com.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SPCRUD {
    public static String deleteSP(Statement statement, int SPId) {
        if(!SPIdExist(statement, SPId)){
            return "Software product id doesn`t exist exception";
        }
        try {
            statement.executeUpdate("delete from software_products where SPId=" + SPId);
        } catch (SQLException e) {
            return "Sql exception in delete";
        }
        return "Successful delete";
    }
    public static String updateSP(Statement statement, int SPId, String newName, int newSPId) {
        if (!SPIdExist(statement, SPId)) {
            return SPId + " id doesn`t exist in software products";
        }
        try {
            newName = String.valueOf('\'') + newName + String.valueOf('\'');
            statement.executeUpdate("update software_products set SPId = " + newSPId +
                    ", name=" + newName + " where SPId=" + SPId);
        } catch (SQLException e) {
            if (SPIdExist(statement, newSPId)) {
                return newSPId + " id already exists in software products exception";
            }
        }
        return "Successful update";
    }

    public static String insertSP(Statement statement, String name){
        try{
            name = String.valueOf('\'') + name + String.valueOf('\'');
            statement.execute("insert into software_products(name) values(" + name+")");
        }catch (SQLException e){
            return "Insert error";
        }
        return "Successful insert";
    }
    public static String selectSP(Statement statement) {
        String result = "";
        try {
            ResultSet rs = statement.executeQuery("Select * from software_products");
            while (rs.next()) {
                result += "Id = " + rs.getInt("SPId") + " " + rs.getString("name") + "\n";
            }
        } catch (SQLException e) {
            return "SqlException selectSp";
        }
        return result;
    }
    public static boolean SPIdExist(Statement statement, Integer SPId) {
        try {
            ResultSet rs = statement.executeQuery("Select SPId from software_products");
            while (rs.next()) {
                if (rs.getInt("SPId") == SPId) return true;
            }
        } catch (SQLException e) {
            System.out.println("SQl exception spIdExist");
        }
        return false;
    }
}
