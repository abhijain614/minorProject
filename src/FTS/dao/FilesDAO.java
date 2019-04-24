/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTS.dao;

import FTS.dbutil.DBConnection;
import FTS.pojo.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class FilesDAO {

    public static boolean addFile(File f) throws SQLException {

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("insert into files(reference_no, creator_id, current_fileholder,file_type, subject,remark) values(?,?,?,?,?,?)");
        ps.setString(1, f.getReferenceNumber());
        ps.setInt(2, f.getCreatorID());
        ps.setInt(3, f.getCurrentFileholder());
        ps.setString(4, String.valueOf(f.getFileType()));
        ps.setString(5, f.getSubject());
        ps.setString(6, f.getRemark());

        int count = ps.executeUpdate();
        if (count == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static int getNewId() throws SQLException {
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        int id = 1;
        ResultSet rs = st.executeQuery("Select count(*)from files");
        if (rs.next()) {
            id = id + rs.getInt(1);
        }
 
        return id;
    }

    public static ArrayList<File> getFilesByUserID(int user_id) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement("Select * from files where current_fileholder=?");
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();

        ArrayList<File> fList = new ArrayList<>();
        while (rs.next()) {
            File f = new File();
            f.setFileID(rs.getInt("id"));
            f.setReferenceNumber(rs.getString("reference_no"));
            f.setCreatorID(rs.getInt("creator_id"));
            f.setFileType(rs.getString("file_type").charAt(0));
            f.setSubject(rs.getString("subject"));
            f.setRemark(rs.getString("remark"));
            f.setCurrentFileholder(rs.getInt("current_fileholder"));
            fList.add(f);
        }

        return fList;

    }

    public static ArrayList<File> getSentFilesByUserID(int user_id) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement("Select * from files where id in (select file_id from transactions where sender_id=? and status=1 ) ");
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();

        ArrayList<File> fList = new ArrayList<>();
        while (rs.next()) {
            File f = new File();
            f.setFileID(rs.getInt("id"));
            f.setReferenceNumber(rs.getString("reference_no"));
            f.setCreatorID(rs.getInt("creator_id"));
            f.setFileType(rs.getString("file_type").charAt(0));
            f.setSubject(rs.getString("subject"));
            f.setRemark(rs.getString("remark"));
            f.setCurrentFileholder(rs.getInt("current_fileholder"));
            fList.add(f);
        }

        return fList;

    }
    public static ArrayList<File> getQueuedFilesByUserID(int user_id) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement("Select * from files where id in (select file_id from transactions where sender_id=? and status=0 ) ");
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();

        ArrayList<File> fList = new ArrayList<>();
        while (rs.next()) {
            File f = new File();
            f.setFileID(rs.getInt("id"));
            f.setReferenceNumber(rs.getString("reference_no"));
            f.setCreatorID(rs.getInt("creator_id"));
            f.setFileType(rs.getString("file_type").charAt(0));
            f.setSubject(rs.getString("subject"));
            f.setRemark(rs.getString("remark"));
            f.setCurrentFileholder(rs.getInt("current_fileholder"));
            fList.add(f);
        }

        return fList;

    }

    public static ArrayList<File> getIncomingFilesByUserID(int user_id) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement("Select * from files where id in (select file_id from transactions where receiver_id=? and status=0 ) ");
        ps.setInt(1, user_id);
        ResultSet rs = ps.executeQuery();

        ArrayList<File> fList = new ArrayList<>();
        while (rs.next()) {
            File f = new File();
            f.setFileID(rs.getInt("id"));
            f.setReferenceNumber(rs.getString("reference_no"));
            f.setCreatorID(rs.getInt("creator_id"));
            f.setFileType(rs.getString("file_type").charAt(0));
            f.setSubject(rs.getString("subject"));
            f.setRemark(rs.getString("remark"));
            f.setCurrentFileholder(rs.getInt("current_fileholder"));
            fList.add(f);
        }

        return fList;

    }
    
    public static ArrayList<File> getAllFilesByReferenceNo(String referenceID) throws SQLException{
    Connection conn=DBConnection.getConnection();
    ResultSet rs = null;
    PreparedStatement ps=conn.prepareStatement("select * from files where reference_no=?");
    ps.setString(1, referenceID);
    rs = ps.executeQuery();
    ArrayList<File> fList= new ArrayList<>();
    if(rs!=null){
    while(rs.next()){
    File f=new File();
    f.setReferenceNumber(rs.getString("reference_no"));
    f.setCreatorID(rs.getInt("creator_id"));
    f.setSubject(rs.getString("subject"));
    f.setRemark(rs.getString("remark"));
    f.setCurrentFileholder(rs.getInt("current_fileholder"));
    f.setDate(rs.getDate("date"));
    fList.add(f);
    } 
    return fList;
    }
    else{
    return null;
    }
    }
  
 
    public static boolean changeCurrentFileholder(int file_id) throws SQLException{
    Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update files set current_fileholder=? where id=(select file_id from transactions where file_id=? and status=0)");
        ps.setInt(1, file_id);
        int x= ps.executeUpdate();
        return (x==1?true:false);
    }
}
