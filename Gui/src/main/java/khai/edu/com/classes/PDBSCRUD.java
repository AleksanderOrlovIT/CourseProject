package khai.edu.com.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PDBSCRUD {
    public static String deletePDB(Statement statement, int SPId, int DBId){
        if(!isPDBExists(statement, SPId , DBId)){
            return "Program data base key doesn`t exist exception";
        }
        try {
            statement.executeUpdate("delete from program_databases where DBId=" + DBId + " and SPId = " + SPId);
        } catch (SQLException e) {
            return "SQL exception delete";
        }
        return "Successful delete";
    }

    public static String updatePDB(Statement statement,int SPId, int DBId, int newSPId, int newDBId){
        if (!isPDBExists(statement, SPId, DBId)) {
            return "Key doesn`t exist";
        }
        try {
            statement.executeUpdate("update program_databases" +
                    " set SPId = " + newSPId + ", DBId = " + newDBId
                    + " where DBId=" + DBId + " and SPId = " + SPId);
        } catch (SQLException e) {
            if(!SPCRUD.SPIdExist(statement, newSPId)){
                return newSPId + " id doesn`t exist in software products";
            }
            else if(!DBCRUD.DBIdExist(statement, newDBId)){
                return newDBId + " id doesn`t exist in databases table";
            } else if(isPDBExists(statement, newSPId, newDBId)){
                return "Key already exists";
            }
        }
        return "Successful update";
    }
    public static String insertPDB(Statement statement, int SPId, int DBId){
        try{
            statement.execute("insert into program_databases(SPId, DBId) values(" + SPId+"," + DBId +")");
        }catch (SQLException e){
            if(!SPCRUD.SPIdExist(statement, SPId)) {
                return SPId + " No such software product id exception";
            }
            else if(!DBCRUD.DBIdExist(statement, DBId)) {
                return DBId + " No such database id exception";
            }
            else if(isPDBExists(statement, SPId, DBId)){
                return "Duplicate key entries exception";
            }
        }
        return "Successful insert";
    }
    public static String selectPDB(Statement statement){
        String result = "";
        try {
            ResultSet rs = statement.executeQuery("select program_databases.SpId, software_products.name," +
                    " program_databases.DBId, dbs.name from program_databases " +
                    "inner join software_products on software_products.SPId = program_databases.SPId " +
                    "inner join dbs on dbs.DBId = program_databases.DBId;");
            while (rs.next()) {
                result += "SPId = " + rs.getInt("program_databases.SPId") + " "
                        + rs.getString("software_products.name") + " DBId = "
                        + rs.getString("program_databases.DBId") + " " + rs.getString("dbs.name") + "\n";
            }
        } catch (SQLException e) {
            return "SQL exception in select";
        }
        return result;
    }

    public static boolean isPDBExists(Statement statement, int SPId, int DBId){
        try {
            ResultSet rs = statement.executeQuery("Select * from program_databases");
            while (rs.next()) {
                if (rs.getInt("SPId") == SPId && rs.getInt("DBId") == DBId) return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL exception in isPDBEXISTS");
        }
        return false;
    }
}
