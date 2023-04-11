/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiect_bd_netbeans;
import java.sql.*;
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;
*/ 
/**
 *
 * @author ioio
 */
public class jdbc_connect {
    
    
     private static jdbc_connect single_instance = null;
     private static Connection connection;
     public static Statement statement;
     public static jdbc_connect getInstance()
    {
        if (single_instance == null)
            single_instance = new jdbc_connect();
  
        return single_instance;
    }
     
     private jdbc_connect()
    {
        try {
             connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd","root","qwerty"); 
             statement = connection.createStatement();
        } catch (Exception e) {
        }
             
    }       
      
}

