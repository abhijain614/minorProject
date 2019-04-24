/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTS.pojo;

/**
 *
 * @author DELL
 */
public class UserProfile {
    private static String emailID;

    public static String getEmailID() {
        return emailID;
    }

    public static void setEmailID(String emailID) {
        UserProfile.emailID = emailID;
    }
    private static String password;
    private static int userType;

    public static int getUserType() {
        return userType;
    }

    public static void setUserType(int userType) {
        UserProfile.userType = userType;
    }

    

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserProfile.password = password;
    }

     private static int ID;

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        UserProfile.ID = ID;
    }
}
