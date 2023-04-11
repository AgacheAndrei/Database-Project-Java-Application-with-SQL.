/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiect_bd_netbeans;
import java.sql.*;
import com.raven.main.* ;
/**
 *
 * @author ioio
 */
public class Proiect_BD_netbeans {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//test singleton
//        jdbc_connect jdbc = jdbc_connect.getInstance();   
//        try {
//            
//            
//            ResultSet resultSet = jdbc.statement.executeQuery("select * from users");
//             while (resultSet.next()) {
//                    System.out.println(resultSet.getString("rol"));
//        }
//        } catch (Exception e) {
//        }
// test class database --- asta ramane
        Database database = new Database();
//       database.elimina_user("marius@cuza");
        //System.out.println(database.loginCheck("andrei@cuza.com", "admin"));
        //System.err.println(database.adaugaElev(1166, "Matol", "Ghenea", "5221216017754", "0722445995", "strada Otel nr 12 Bl j5", 8.22f, "CI,CN", 190));
        //System.err.println(database.adaugaElev(2222, "Matol", "Giani", "5221216017755", "0722445996", "strada Otel nr 12 Bl j5", 9.55f, "CI,CN,FM", 190));
       // database.modificaElev(2222, "Matol", "Giani", "5221216017755", "0722445996", "strada Otel nr 12 Bl j5", 9.51f, "CI,CN,FM", 190);
        //database.updateBursieri();
        //database.eliminaElev(1169);
        //database.updateBursieri();
       // database.adaugaEveniment("admin", "data");
         
        
    }
    
}
