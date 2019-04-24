/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTS.dbutil;

import FTS.ServerConfig.ServerConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author Akash Patel
 */
public class DBConnection {
    private static Connection conn;
    static
    {
        try {
            ServerConfig config=new ServerConfig();
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(config.getConnectionLink(), config.getMySQlUser(), config.getMySQlPassword());
            JOptionPane.showMessageDialog(null,"Connected successfully to the database!" , "Connected!", JOptionPane.INFORMATION_MESSAGE);    
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConnection()
    {
        return conn;
    }
    public static void closeConnection()
    {
        if(conn!=null)
        {
           try
           {
               conn.close();
               JOptionPane.showMessageDialog(null, "Successfully disconnected the Database !", "SUCCESS !", JOptionPane.INFORMATION_MESSAGE);
           }
           catch(SQLException e)
                   {
                   JOptionPane.showMessageDialog(null, "Unable to disconnect Database !", "ERROR !", JOptionPane.ERROR_MESSAGE);
                   }
        }
    }   
}
