package khai.edu.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static khai.edu.com.Main.printSQLException;

public class UserPrograms {
    public static boolean deleteUserProgram(Statement statement, int userId, int SPId, int OSId){
        if(!isUserProgramExists(statement,userId, SPId , OSId)){
            System.out.println("User program key doesn`t exist exception");
            return false;
        }
        try {
            statement.executeUpdate("delete from user_programs where userId=" + userId
                    + " and SPId = " + SPId + " and OSId = " + OSId);
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }
    public static boolean updateUserProgram(Statement statement,int userId, int SPId, int OSId,int newUserId, int newSPId, int newOSId){
        if (!isUserProgramExists(statement, userId, SPId, OSId)) {
            System.out.println("Key doesn`t exist");
            return false;
        }
        try {
            statement.executeUpdate("update user_programs set userId = " + newUserId
                    +" , SPId = " + newSPId + " , OSId = " + newOSId
                    + " where userId=" + userId + " and SPId = " + SPId + " and OSId = " + OSId);
        } catch (SQLException e) {
            if(!UsersCRUD.userIdExist(statement, newUserId)){
                System.out.println(newUserId + " id doesn`t exist in users");
                return false;
            }else if(!SoftwareProductsCRUD.SPIdExist(statement, newSPId)){
                System.out.println(newSPId + " id doesn`t exist in software products");
                return false;
            } else if(!OperatingSystemsCRUD.OSIdExist(statement, newOSId)){
                System.out.println(newOSId + " id doesn`t exist in operating systems");
                return false;
            } else if(isUserProgramExists(statement, newUserId, newSPId,newOSId)){
                System.out.println("Key with new parameters already exists");
                return false;
            }
        }
        return true;
    }

    public static boolean insertUserPrograms(Statement statement, int userId, int SPId, int OSId){
        try{
            statement.execute("insert into user_programs(userId, SPId, OSId) " +
                    "values(" + userId+"," + SPId +"," + OSId + ")");
        }catch (SQLException e){
            if(!UsersCRUD.userIdExist(statement, userId)){
                System.out.println(userId + " No such user id exception");
                return false;
            }
            if(!SoftwareProductsCRUD.SPIdExist(statement, SPId)) {
                System.out.println(SPId + " No such software product id exception");
                return false;
            }
            else if(!OperatingSystemsCRUD.OSIdExist(statement, OSId)) {
                System.out.println(OSId + " No such operating system id exception");
                return false;
            }
            else if(isUserProgramExists(statement, userId, SPId, OSId)){
                System.out.println("Duplicate key entries exception");
                return false;
            }
        }
        return true;
    }
    public static boolean selectUserPrograms(Statement statement){
        try {
            ResultSet rs = statement.executeQuery("select user_programs.userId, users.userName, user_programs.SPID," +
                    " software_products.name, user_programs.OSId, operating_systems.name from user_programs " +
                    "inner join users on users.userId = user_programs.userId " +
                    "inner join software_products on software_products.SPId = user_programs.SPId " +
                    "inner join operating_systems on operating_systems.OSId = user_programs.OSId;");
            while (rs.next()) {
                System.out.println("UserId = " + rs.getInt("userId") + " " + rs.getString("users.userName")
                        + " SPId = " + rs.getInt("SPId") + " " + rs.getString("software_products.name") + " OSId = "
                        + rs.getInt("OSId") + " " + rs.getString("operating_systems.name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    public static boolean isUserProgramExists(Statement statement,int userId, int SPId, int OSId){
        try {
            ResultSet rs = statement.executeQuery("Select * from user_programs");
            while (rs.next()) {
                if (rs.getInt("userId") == userId &&
                        rs.getInt("SPId") == SPId &&
                        rs.getInt("OSId") == OSId)
                    return true;
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }
}
