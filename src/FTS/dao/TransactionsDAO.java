/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTS.dao;

import FTS.dbutil.DBConnection;
import FTS.pojo.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author DELL
 */
public class TransactionsDAO {
     public static boolean addTransaction(Transaction transaction) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Insert INTO Transactions(file_id, sender_id, receiver_id, status, remark) VALUES(?,?,?,?,?)");
        ps.setInt(1,transaction.getFile_id());
        ps.setInt(2,transaction.getSender_id());
        ps.setInt(3,transaction.getReceiver_id());
        ps.setInt(4,transaction.getStatus());
        ps.setString(5,transaction.getRemark());
        int x = ps.executeUpdate();
        return (x==1?true:false); //cross-check it by testing
    }
     
     public static boolean changeTransactionStatus(Transaction transaction) throws SQLException{
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("update transactions set status=1,receive_time=current_timestamp where file_id=? and status=0");
        ps.setInt(1, transaction.getFile_id());
        int x= ps.executeUpdate();
        return (x==1?true:false);
     }
     public static int getSenderByFileID(int fileID){
         try {
             int x=0;
             Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("select sender_id from transactions where file_id=? order by receive_time desc limit 1");
             ps.setInt(1,fileID);
             ResultSet rs = ps.executeQuery();
             System.out.println("In before sender ID");
             if(rs.next()){
                 x = rs.getInt(1);
             }
             System.out.println(x+"In after sender ID");
             return (x);
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
         return 0;
     }
     public static int getReceiverByFileID(int fileID){
         try {
             int x=0;
             Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(" select receiver_id from transactions where file_id=? order by dispatch_time desc limit 1");
             ps.setInt(1,fileID);
             ResultSet rs = ps.executeQuery();
             System.out.println("In before rec ID");
             if(rs.next()){
                 x = rs.getInt(1);
             }
             System.out.println(x+"In after receiver ID");
             return (x);
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
         return 0;
     }
}
