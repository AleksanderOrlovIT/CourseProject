package khai.edu.com.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserProgramsCRUD {
    public static String deleteUserProgram(Statement statement, int userId, int SPId, int OSId){
        if(!isUserProgramExists(statement,userId, SPId , OSId)){
            return "User program key doesn`t exist exception";
        }
        try {
            statement.executeUpdate("delete from user_programs where userId=" + userId
                    + " and SPId = " + SPId + " and OSId = " + OSId);
        } catch (SQLException e) {
            return "SQLException delete";
        }
        return "Successful delete";
    }
    public static String updateUserProgram(Statement statement,int userId, int SPId, int OSId,int newUserId, int newSPId, int newOSId){
        if (!isUserProgramExists(statement, userId, SPId, OSId)) {
            return "Key doesn`t exist";
        }
        try {
            statement.executeUpdate("update user_programs set userId = " + newUserId
                    +" , SPId = " + newSPId + " , OSId = " + newOSId
                    + " where userId=" + userId + " and SPId = " + SPId + " and OSId = " + OSId);
        } catch (SQLException e) {
            if(!UsersCRUD.userIdExist(statement, newUserId)){
                return newUserId + " id doesn`t exist in users";
            }else if(!SPCRUD.SPIdExist(statement, newSPId)){
                return newSPId + " id doesn`t exist in software products";
            } else if(!OSCRUD.OSIdExist(statement, newOSId)){
                return newOSId + " id doesn`t exist in operating systems";
            } else if(isUserProgramExists(statement, newUserId, newSPId,newOSId)){
                return "Key with new parameters already exists";
            }
        }
        return "Successful update";
    }

    public static String insertUserPrograms(Statement statement, int userId, int SPId, int OSId){
        try{
            statement.execute("insert into user_programs(userId, SPId, OSId) " +
                    "values(" + userId+"," + SPId +"," + OSId + ")");
        }catch (SQLException e){
            if(!UsersCRUD.userIdExist(statement, userId)){
                return userId + " No such user id exception";
            }
            if(!SPCRUD.SPIdExist(statement, SPId)) {
                return SPId + " No such software product id exception";
            }
            else if(!OSCRUD.OSIdExist(statement, OSId)) {
                return OSId + " No such operating system id exception";
            }
            else if(isUserProgramExists(statement, userId, SPId, OSId)){
                return "Duplicate key entries exception";
            }
        }
        return "Successful insert";
    }
    public static String selectUserPrograms(Statement statement){
        String result = "";
        try {
            ResultSet rs = statement.executeQuery("select user_programs.userId, users.userName, user_programs.SPID," +
                    " software_products.name, user_programs.OSId, operating_systems.name from user_programs " +
                    "inner join users on users.userId = user_programs.userId " +
                    "inner join software_products on software_products.SPId = user_programs.SPId " +
                    "inner join operating_systems on operating_systems.OSId = user_programs.OSId;");
            while (rs.next()) {
                result += "UserId = " + rs.getInt("userId") + " " + rs.getString("users.userName")
                        + " SPId = " + rs.getInt("SPId") + " " + rs.getString("software_products.name") + " OSId = "
                        + rs.getInt("OSId") + " " + rs.getString("operating_systems.name") + "\n";
            }
        } catch (SQLException e) {
            return "SQL Exception select";
        }
        return result;
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
            System.out.println("SQL exeption isUserProgramExists");;
        }
        return false;
    }
}
