/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTS.dao;

import FTS.dbutil.DBConnection;
import FTS.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author DELL
 */
public class UsersDAO {
    public static String[] getNameByUserID(int ID) throws SQLException{
        String name="x";
        String pos="x";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select name,position from users where id=?");
        ps.setInt(1,ID);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
        name = rs.getString("name");
        pos = rs.getString("position");
        }
        String[] namepos = {name,pos};
        return namepos;
    }
    public static int verifyUser(User user) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select id from users where (email=? OR phone=?) and password=?");
        ps.setString(1, user.getEmail());
        ps.setString(2, String.valueOf(user.getMobileNumber()));
        ps.setString(3, user.getPassword());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            //System.out.println("Helloworld");
            return rs.getInt(1);
        }
        else
            return 0;
    }
    public static boolean verifyPassword(String pwd) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select id from users where password=?");
        ps.setString(1, pwd);
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return true;
        else
            return false;
    }
    public static boolean changePassword(User user) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update users set password=? where email=?");
        ps.setString(1, user.getPassword());
        ps.setString(2, user.getEmail());
        int x=ps.executeUpdate();
        if(x==1){
            System.out.println("password updated");
            return true;
        }
        else
            return false;
    }
    public static int getUsertypeByUserID(int userID) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("select privileges from users where id=?");
        ps.setInt(1, userID);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        else{
        return -1;
        }
    }
}
