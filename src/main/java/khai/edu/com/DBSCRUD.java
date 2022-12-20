package khai.edu.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static khai.edu.com.Main.printSQLException;

public class DBSCRUD {

    public static boolean deleteDB(Statement statement, int DBId){
        if(!DBIdExist(statement, DBId)){
            System.out.println(DBId + " data base id doesn`t exist exception");
            return false;
        }
        try {
            statement.executeUpdate("delete from dbs where DBId=" + DBId);
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }
    public static boolean updateDbsNameAndId(Statement statement, int DBId, String newName, int newDBId){
        if(!DBIdExist(statement, DBId)){
            System.out.println(DBId + " id doesn`t exist in dbs");
            return false;
        }
        try{
            newName = String.valueOf('\'') + newName + String.valueOf('\'');
            statement.executeUpdate("update dbs set DBId = " + newDBId +
                    ", name=" + newName + " where DBId=" + DBId);
        }catch (SQLException e){
            if(DBIdExist(statement, newDBId)){
                System.out.println(newDBId + " new id already exists");
                return false;
            }
        }
        return true;
    }

    public static boolean updateDbsServer(Statement statement, int DBId, int newServersId){
        if (!ServersCRUD.serversIdExist(statement, newServersId)) {
            System.out.println(newServersId + " id doesn`t exist in servers");
            return false;
        }
        else if(!DBIdExist(statement, DBId)){
            System.out.println(DBId + " id doesn`t exist in dbs");
            return false;
        }
        try {
            statement.executeUpdate("update dbs set serversId = " + newServersId + " where DBId=" + DBId);
        } catch (SQLException e) {
            printSQLException(e);
        }
        return true;
    }

    public static boolean insertDB(Statement statement, String name, int serversId){
        try{
            name = String.valueOf('\'') + name + String.valueOf('\'');
            statement.execute("insert into dbs(name, serversId) values(" + name+"," + serversId +")");
        }catch (SQLException e){
            if(!ServersCRUD.serversIdExist(statement, serversId))
                System.out.println("No such server id exception");
            return false;
        }
        return true;
    }

    public static boolean selectDBs(Statement statement){
        try {
            ResultSet rs = statement.executeQuery("select  dbs.DBId, dbs.name, servers.serversId, servers.name" +
                    " from dbs inner join servers on servers.serversId = dbs.serversId;");
            while (rs.next()) {
                System.out.println("DBId = " + rs.getInt("DBId") + " " + rs.getString("dbs.name") +
                        " serversId = " + rs.getString("servers.serversId") + " " + rs.getString("servers.name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }
    public static boolean DBIdExist(Statement statement, Integer DBId) {
        try {
            ResultSet rs = statement.executeQuery("Select DBId from dbs");
            while (rs.next()) {
                if (rs.getInt("DBId") == DBId) return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
}
