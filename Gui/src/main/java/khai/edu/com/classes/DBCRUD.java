package khai.edu.com.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCRUD {
    public static String deleteDB(Statement statement, int DBId){
        if(!DBIdExist(statement, DBId)){
            return DBId + " data base id doesn`t exist exception";
        }
        try {
            statement.executeUpdate("delete from dbs where DBId=" + DBId);
        } catch (SQLException e) {
            return "SQLException delete";
        }
        return "Successful delete";
    }
    public static String updateDbsNameAndId(Statement statement, int DBId, String newName, int newDBId){
        if(!DBIdExist(statement, DBId)){
              return  DBId + " id doesn`t exist in dbs";
        }
        try{
            newName = String.valueOf('\'') + newName + String.valueOf('\'');
            statement.executeUpdate("update dbs set DBId = " + newDBId +
                    ", name=" + newName + " where DBId=" + DBId);
        }catch (SQLException e){
            if(DBIdExist(statement, newDBId)){
                return newDBId + " new id already exists";
            }
        }
        return "Successful update DB name and id";
    }

    public static String updateDbsServer(Statement statement, int DBId, int newServersId){
        if (!ServerCrud.serversIdExist(statement, newServersId)) {
            return newServersId + " id doesn`t exist in servers";
        }
        else if(!DBIdExist(statement, DBId)){
            return DBId + " id doesn`t exist in dbs";
        }
        try {
            statement.executeUpdate("update dbs set serversId = " + newServersId + " where DBId=" + DBId);
        } catch (SQLException e) {
            return "SQL exception updateDbsServer";
        }
        return "Successful dbs servers id update";
    }

    public static String insertDB(Statement statement, String name, int serversId){
        try{
            name = String.valueOf('\'') + name + String.valueOf('\'');
            statement.execute("insert into dbs(name, serversId) values(" + name+"," + serversId +")");
        }catch (SQLException e){
            if(!ServerCrud.serversIdExist(statement, serversId))
                return "No such server id exception";
            return "SQL exception insertDB";
        }
        return "Successful insert";
    }

    public static String selectDBs(Statement statement){
        String result = "";
        try {
            ResultSet rs = statement.executeQuery("select  dbs.DBId, dbs.name, servers.serversId, servers.name" +
                    " from dbs inner join servers on servers.serversId = dbs.serversId;");
            while (rs.next()) {
                result += "DBId = " + rs.getInt("DBId") + " " + rs.getString("dbs.name") +
                        " serversId = " + rs.getString("servers.serversId") + " " + rs.getString("servers.name")
                        + "\n";
            }
        } catch (SQLException e) {
            return "SQL Exception select";
        }
        return result;
    }
    public static boolean DBIdExist(Statement statement, Integer DBId) {
        try {
            ResultSet rs = statement.executeQuery("Select DBId from dbs");
            while (rs.next()) {
                if (rs.getInt("DBId") == DBId) return true;
            }
        } catch (SQLException e) {
            System.out.println("SQL excption DBIDExists");
        }
        return false;
    }
}
