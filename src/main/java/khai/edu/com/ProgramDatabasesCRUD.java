package khai.edu.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static khai.edu.com.Main.printSQLException;

public class ProgramDatabasesCRUD {

    public static boolean deletePDB(Statement statement, int SPId, int DBId){
        if(!isPDBExists(statement, SPId , DBId)){
            System.out.println("Program data base key doesn`t exist exception");
            return false;
        }
        try {
            statement.executeUpdate("delete from program_databases where DBId=" + DBId + " and SPId = " + SPId);
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public static boolean updatePDB(Statement statement,int SPId, int DBId, int newSPId, int newDBId){
        if (!isPDBExists(statement, SPId, DBId)) {
            System.out.println("Key doesn`t exist");
            return false;
        }
        try {
            statement.executeUpdate("update program_databases" +
                    " set SPId = " + newSPId + ", DBId = " + newDBId
                    + " where DBId=" + DBId + " and SPId = " + SPId);
        } catch (SQLException e) {
            if(!SoftwareProductsCRUD.SPIdExist(statement, newSPId)){
                System.out.println(newSPId + " id doesn`t exist in software products");
                return false;
            }
             else if(!DBSCRUD.DBIdExist(statement, newDBId)){
                System.out.println(newDBId + " id doesn`t exist in databases table");
                return false;
            } else if(isPDBExists(statement, newSPId, newDBId)){
                System.out.println("Key already exists");
                return false;
            }
        }
        return true;
    }
    public static boolean insertPDB(Statement statement, int SPId, int DBId){
        try{
            statement.execute("insert into program_databases(SPId, DBId) values(" + SPId+"," + DBId +")");
        }catch (SQLException e){
            if(!SoftwareProductsCRUD.SPIdExist(statement, SPId)) {
                System.out.println(SPId + " No such software product id exception");
                return false;
            }
            else if(!DBSCRUD.DBIdExist(statement, DBId)) {
                System.out.println(DBId + " No such database id exception");
                return false;
            }
            else if(isPDBExists(statement, SPId, DBId)){
                System.out.println("Duplicate key entries exception");
                return false;
            }
        }
        return true;
    }
    public static boolean selectPDB(Statement statement){
        try {
            ResultSet rs = statement.executeQuery("select program_databases.SpId, software_products.name," +
                    " program_databases.DBId, dbs.name from program_databases " +
                    "inner join software_products on software_products.SPId = program_databases.SPId " +
                    "inner join dbs on dbs.DBId = program_databases.DBId;");
            while (rs.next()) {
                System.out.println("SPId = " + rs.getInt("program_databases.SPId") + " "
                        + rs.getString("software_products.name") + " DBId = "
                        + rs.getString("program_databases.DBId") + " " + rs.getString("dbs.name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public static boolean isPDBExists(Statement statement, int SPId, int DBId){
        try {
            ResultSet rs = statement.executeQuery("Select * from program_databases");
            while (rs.next()) {
                if (rs.getInt("SPId") == SPId && rs.getInt("DBId") == DBId) return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
}
