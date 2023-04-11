/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proiect_bd_netbeans;

import com.sun.xml.internal.ws.api.server.SDDocument;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ioio
 */

    public class Database
{

    String dbURL = "jdbc:mysql://localhost:3306/bd";
    String dbusername = "root";    //username de logare la baza de date
    String dbpassword = "qwerty";    //parola de logare la baza de date

    public boolean loginCheck(String loginUsername, String loginPassword)
    {
        boolean check = false;
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "SELECT email,password FROM users where email=? and password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, loginUsername);
            statement.setString(2, loginPassword);
            ResultSet result = statement.executeQuery();
            if (result.next())
            {
                check = true;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return check;
    }
    public TableModel afisareElevi()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "SELECT * FROM elev";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
     public TableModel afisareBursieri()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "SELECT * FROM bursieri";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String getRol(String loginUsername)
    {
        String rol = "";
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "SELECT rol FROM users where email=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, loginUsername);
            ResultSet result = statement.executeQuery();
            result.next();
            rol = result.getString(1);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return rol;
    }
    
    public int modificaElev(int nr_matricol, String nume, String prenume, String cnp, String telefon, String adresa, Float medie, String acte_adi, int codclasa)
    {
        int rowsInserted=0;
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "UPDATE elev SET CNP=?, nume=?, prenume=?, nr_telefon=?, adresa=?, medie=?, acte_adi=?,codclasa=? WHERE nr_matricol=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(9, nr_matricol);
            statement.setString(1, cnp);
            statement.setString(2, nume);
            statement.setString(3, prenume);
            statement.setString(4, telefon);
            statement.setString(5, adresa);
            statement.setFloat(6, medie);
            statement.setString(7, acte_adi);
            statement.setInt(8, codclasa);
            rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
            {
                System.out.println("Un elev a fost modificat!");
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return rowsInserted;
    }
    public int adaugaElev(int nr_matricol, String nume, String prenume, String cnp, String telefon, String adresa, Float medie, String acte_adi, int codclasa)
    {
        int rowsInserted=0;
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "INSERT INTO elev () VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, nr_matricol);
            statement.setString(2, cnp);
            statement.setString(3, nume);
            statement.setString(4, prenume);
            statement.setString(5, telefon);
            statement.setString(6, adresa);
            statement.setFloat(7, medie);
            statement.setString(8, acte_adi);
            statement.setInt(9, codclasa);
            rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0)
            {
                System.out.println("Un nou elev a fost adaugat!");
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return rowsInserted;
    }
    
    public void updateBursieri()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "delete from bursieri where codbur<1000";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            
            String sql2 = "insert into bursieri(codbur,nr_matricol) select tip_bursa.codbur,elev.nr_matricol from tip_bursa,elev where tip_bursa.acte_br=elev.acte_adi and tip_bursa.medie<=elev.medie;";
            PreparedStatement statement2 = conn.prepareStatement(sql2);
            
            int rowInserted =statement2.executeUpdate();
            if (rowInserted>0) {
                System.out.println("update in bursieri");
                
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public int eliminaElev(int nr_matricol)
    {
        int nr_rows=0;
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "DELETE FROM elev WHERE nr_matricol=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, nr_matricol);
            nr_rows=statement.executeUpdate();
            if(nr_rows>0)
            {
                System.out.println("ai sters un elev");
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return nr_rows;
    }
    //, boolean bursieri, float medie,boolean crescator
     public TableModel filtru_clasa(int codclasa)
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
            String sql = "SELECT * FROM elev where codclasa=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, codclasa);
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
     public TableModel filtru_medie(float medie,boolean crescator)
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
          
            String sql="";
            if(crescator==true)
            {
               sql="SELECT * FROM elev where medie >= ? order by medie";
                
            }
            else
            {
                sql="SELECT * FROM elev where medie >= ? order by medie desc";
            }
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setFloat(1, medie);
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
     
    public TableModel filtru_bursieri()
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
          
            String sql="";
           
            sql="SELECT * FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    public TableModel filtru_nu_bursieri()
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
          
            String sql="";
           
            sql="select * from elev where nr_matricol not in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol)";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    public TableModel filtru_clasa_bursieri(int codclasa)
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
          
            String sql="";
           
            sql="SELECT * FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol and codclasa=?";
            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, codclasa);
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public TableModel filtru_clasa_nu_bursieri(int codclasa)
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
          
            String sql="";
           
            sql="select * from elev where nr_matricol not in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and codclasa=?";

            
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, codclasa);
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public TableModel filtru_clasa_medie(int codclasa,float medie,boolean crescator)
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
          
            String sql="";
            if(crescator==true)
            {
               sql="SELECT * FROM elev where codclasa=? and medie >= ?  order by medie";
                
            }
            else
            {
               sql="SELECT * FROM elev where codclasa=? and medie >= ?  order by medie desc";
            }
            
            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setInt(1, codclasa);
            statement.setFloat(2, medie);
           
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
            
          
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
     public TableModel filtru_bursier_medie(float medie,boolean crescator,boolean bursier)
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
          
            String sql="";
            if(crescator==true && bursier==true)//nuy este ord cresc
            {
               sql="select * from elev where nr_matricol  in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and medie>=? order by medie";
            }
            else if(crescator==false && bursier==true)//nu e ord desc
            {
               sql="select * from elev where nr_matricol  in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and medie>=? order by medie desc";
            }
            else if(crescator==true && bursier==false)//e ord cresc
            {
                sql="select * from elev where nr_matricol  not in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and medie>=? order by medie ";
            }
            else  if(crescator==false && bursier==false){//e or desc
                sql="select * from elev where nr_matricol  not in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and medie>=? order by medie desc ";
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            
            
            statement.setFloat(1, medie);
           
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
            
          
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
      public TableModel filtru_bursier_medie_clasa(int clasa,float medie,boolean crescator,boolean bursier)
    {
       
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {   
          
            String sql="";
            if(crescator==true && bursier==true)//nuy este ord cresc
            {
               sql="select * from elev where nr_matricol  in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and medie>=? and codclasa=? order by medie";
            }
            else if(crescator==false && bursier==true)//nu e ord desc
            {
               sql="select * from elev where nr_matricol  in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and medie>=? and codclasa=? order by medie desc";
            }
            else if(crescator==true && bursier==false)//e ord cresc
            {
                sql="select * from elev where nr_matricol  not in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and medie>=? and codclasa=? order by medie ";
            }
            else  if(crescator==false && bursier==false){//e or desc
                sql="select * from elev where nr_matricol  not in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol) and medie>=? and codclasa=? order by medie desc ";
            }

            PreparedStatement statement = conn.prepareStatement(sql);
            
            
            statement.setFloat(1, medie);
            statement.setInt(2, clasa);
           
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
            
          
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
     public TableModel cautare_elev(int matricol)
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "SELECT * FROM elev where nr_matricol=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            
           
            statement.setInt(1, matricol);
           
            ResultSet result = statement.executeQuery();
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    public TableModel nr_total_elevi()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "select count(*) as 'Numar total de elevi' from elev; ";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public TableModel nr_total_bursieri()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "SELECT count(*) as 'Numar total de elevi bursieri' FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
     public TableModel nr_total_ne_bursieri()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select count(*) as 'Numar total de elevi nebursieri' from elev where nr_matricol not in (SELECT bd.elev.nr_matricol FROM bd.elev,bd.bursieri where elev.nr_matricol=bursieri.nr_matricol)";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
      public TableModel valoare_burse_per_clasa()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select clasa.nume as 'Nume Clase', sum(tip_bursa.valoare) as 'Suma per Clasa' from clasa, elev ,bursieri, tip_bursa where bursieri.codbur=tip_bursa.codbur and elev.nr_matricol=bursieri.nr_matricol and elev.codclasa = clasa.codclasa group by clasa.nume";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
       public TableModel total_valoare_burse()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select sum(tip_bursa.valoare) as 'Valoare toatala a burselor' from bursieri, tip_bursa where bursieri.codbur=tip_bursa.codbur";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
       public TableModel afisare_burse()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select * from tip_bursa";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
       public TableModel valoare_burspe_per_specializare()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select specializare.nume_sp as 'Nume Specializare', sum(tip_bursa.valoare) as 'Suma per Spercializare' from specializare,clasa, elev ,bursieri, tip_bursa where bursieri.codbur=tip_bursa.codbur and elev.nr_matricol=bursieri.nr_matricol and elev.codclasa = clasa.codclasa and specializare.codsp=clasa.codsp group by specializare.nume_sp";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
       
     public TableModel valoare_burspe_per_profil()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select profil.nume_profil as 'Nume Profil', sum(tip_bursa.valoare) as 'Suma per Profil' from profil,specializare,clasa, elev ,bursieri, tip_bursa where bursieri.codbur=tip_bursa.codbur and elev.nr_matricol=bursieri.nr_matricol and elev.codclasa = clasa.codclasa and specializare.codsp=clasa.codsp and profil.codpr=specializare.codpr group by profil.nume_profil";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public TableModel valoare_burspe_per_tip_bursa()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select  tip_bursa.nume as 'Tip Bursa',sum(tip_bursa.valoare) as 'Suma per Categorie' from tip_bursa, bursieri where tip_bursa.codbur=bursieri.codbur group by tip_bursa.nume;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    public TableModel valoare_burse_per_student()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select  elev.nume,elev.prenume, sum(tip_bursa.valoare) as 'Suma per Elev' from tip_bursa, bursieri,elev where tip_bursa.codbur=bursieri.codbur and elev.nr_matricol=bursieri.nr_matricol group by elev.nume,elev.prenume;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public TableModel tip_burse_per_clase()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select  clasa.nume, tip_bursa.nume,count(bursieri.nr_matricol) from clasa, tip_bursa, bursieri,elev where tip_bursa.codbur=bursieri.codbur and elev.nr_matricol=bursieri.nr_matricol and clasa.codclasa=elev.codclasa group by clasa.nume,tip_bursa.nume;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    public int adaugaEveniment(String user, String eveniment)
    {
        int rowsInserted=0;
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "INSERT INTO `bd`.`istoric` (`user`, `eveniment`) VALUES (?, ?);";
            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setString(1, user);
            statement.setString(2, eveniment);
            
            rowsInserted = statement.executeUpdate();
            
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return rowsInserted;
    }
    public TableModel afisare_istoric()
    {
        try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            
            String sql="select * from istoric";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            return DbUtils.resultSetToTableModel(result);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
} 




