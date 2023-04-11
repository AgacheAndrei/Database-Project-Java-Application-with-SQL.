package com.raven.main;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.sql.*;
import java.util.Random;
import javax.swing.JOptionPane;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import javaswingdev.chart.*;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import proiect_bd_netbeans.*;


import java.awt.Desktop;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main extends javax.swing.JFrame {

    private Animator animatorLogin;
    private Animator animatorBody;
    private boolean signIn;
    Database database;
    String dbURL = "jdbc:mysql://localhost:3306/bd";
    String dbusername = "root";    //username de logare la baza de date
    String dbpassword = "qwerty";
    Random rand = new Random();
    String utilizator_actual="";
    private Color getColor()
    {
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        
        
        return new Color(r, g, b);
    }
   public void openFile(String file){
        try{
            File path = new File(file);
            Desktop.getDesktop().open(path);
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }
 DefaultTableModel model;
  class myTableCellRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

//          Change Image column minimum width and maximum width
            TableColumn tb = tabele_rapoarte1.getColumn("Image");
            tb.setMaxWidth(60);
            tb.setMinWidth(60);
            tabele_rapoarte1.setRowHeight(60);
            return (Component) value;

        }

    }
  void import_excel()
  {
        
        model = (DefaultTableModel) tabele_rapoarte1.getModel();
        Object[] newIdentifiers = new Object[]{"Nr matr", "CNP", "Nume", "Prenume","Telefon","Adresa","Medie","Acte Adi","Codclasa","Image"};
        model.setColumnIdentifiers(newIdentifiers);
        //Get Image column and override  TableCellRenderer class component method (getTableCellRendererComponent)
        tabele_rapoarte1.getColumn("Image").setCellRenderer(new myTableCellRenderer());
        File excelFile;
        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS = null;
        XSSFWorkbook excelImportToJTable = null;
        String defaultCurrentDirectoryPath = "C:\\Users\\Authentic\\Desktop";
        JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
        excelFileChooser.setDialogTitle("Select Excel File");
        FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls", "xlsx", "xlsm");
        excelFileChooser.setFileFilter(fnef);
        int excelChooser = excelFileChooser.showOpenDialog(null);
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            try {
                excelFile = excelFileChooser.getSelectedFile();
                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);
                excelImportToJTable = new XSSFWorkbook(excelBIS);
                XSSFSheet excelSheet = excelImportToJTable.getSheetAt(0);
 
                for (int row = 1; row < excelSheet.getLastRowNum(); row++) {
                    XSSFRow excelRow = excelSheet.getRow(row);
 
                    XSSFCell excelName = excelRow.getCell(0);
                    XSSFCell excelGender = excelRow.getCell(1);
                    XSSFCell excelProgrammingLanguage = excelRow.getCell(2);
                    XSSFCell excelSubject = excelRow.getCell(3);
                    XSSFCell excelnr_tel = excelRow.getCell(4);
                    XSSFCell exceladresa = excelRow.getCell(5);
                    XSSFCell excelmedie = excelRow.getCell(6);
                    XSSFCell excelacte_adi = excelRow.getCell(7);
                    XSSFCell excelImage = excelRow.getCell(8);
 
                    JLabel excelJL = new JLabel(new ImageIcon(new ImageIcon(excelImage.getStringCellValue()).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
                    model.addRow(new Object[]{excelName, excelGender, excelProgrammingLanguage, excelSubject,excelnr_tel,exceladresa,excelmedie,excelacte_adi,excelImage,excelJL});
                    
                }
                
                JOptionPane.showMessageDialog(null, "Import realizat cu succes!");
            } catch (IOException iOException) {
                JOptionPane.showMessageDialog(null, iOException.getMessage());
            } finally {
                try {
                    if (excelFIS != null) {
                        excelFIS.close();
                    }
                    if (excelBIS != null) {
                        excelBIS.close();
                    }
                    if (excelImportToJTable != null) {
                        excelImportToJTable.close();
                    }
                } catch (IOException iOException) {
                    JOptionPane.showMessageDialog(null, iOException.getMessage());
                }
            }
        }
  }
   
   
 void salvare_excel(JTable jTable1)
         {
             try{
           JFileChooser jFileChooser = new JFileChooser();
           jFileChooser.showSaveDialog(this);
           File saveFile = jFileChooser.getSelectedFile();
           
           if(saveFile != null){
               saveFile = new File(saveFile.toString()+".xlsx");
               Workbook wb = new XSSFWorkbook();
               Sheet sheet = wb.createSheet("customer");
               
               Row rowCol = sheet.createRow(0);
               for(int i=0;i<jTable1.getColumnCount();i++){
                   Cell cell = rowCol.createCell(i);
                   cell.setCellValue(jTable1.getColumnName(i));
               }
               
               for(int j=0;j<jTable1.getRowCount();j++){
                   Row row = sheet.createRow(j+1);
                   for(int k=0;k<jTable1.getColumnCount();k++){
                       Cell cell = row.createCell(k);
                       if(jTable1.getValueAt(j, k)!=null){
                           cell.setCellValue(jTable1.getValueAt(j, k).toString());
                       }
                   }
               }
               FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
               wb.write(out);
               wb.close();
               out.close();
               openFile(saveFile.toString());
           }else{
               JOptionPane.showMessageDialog(null,"Eroare salvare fisier excel");
           }
       }catch(FileNotFoundException e){
           System.out.println(e);
       }catch(IOException io){
           System.out.println(io);
       }
         }
 void pie_chart()
 {
      try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "select clasa.nume as 'Nume Clase', sum(tip_bursa.valoare) as 'Suma per Clasa' from clasa, elev ,bursieri, tip_bursa where bursieri.codbur=tip_bursa.codbur and elev.nr_matricol=bursieri.nr_matricol and elev.codclasa = clasa.codclasa group by clasa.nume";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            int index=0;
            while (result.next()) {
                String clasa = result.getString(1);
                int suma=result.getInt(2);
                pie_valoare_per_clase.addData(new ModelPieChart(clasa, suma, getColor()));
                
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
         try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "select  tip_bursa.nume as 'Tip Bursa',sum(tip_bursa.valoare) as 'Suma per Categorie' from tip_bursa, bursieri where tip_bursa.codbur=bursieri.codbur group by tip_bursa.nume;";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            int index=0;
            while (result.next()) {
                String clasa = result.getString(1);
                int suma=result.getInt(2);
                pie_valoare_burse_tip_bursa.addData(new ModelPieChart(clasa, suma, getColor()));
                
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
         
         try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "select specializare.nume_sp as 'Nume Specializare', sum(tip_bursa.valoare) as 'Suma per Spercializare' from specializare,clasa, elev ,bursieri, tip_bursa where bursieri.codbur=tip_bursa.codbur and elev.nr_matricol=bursieri.nr_matricol and elev.codclasa = clasa.codclasa and specializare.codsp=clasa.codsp group by specializare.nume_sp";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            int index=0;
            while (result.next()) {
                String clasa = result.getString(1);
                int suma=result.getInt(2);
                pie_valoare_per_specializare.addData(new ModelPieChart(clasa, suma, getColor()));
                
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
 }
    public Main() {
        this.database=new Database();
        
        initComponents();
        pie_valoare_per_clase.setChartType(PieChart.PeiChartType.DONUT_CHART);
        pie_valoare_burse_tip_bursa.setChartType(PieChart.PeiChartType.DONUT_CHART);
        pie_valoare_per_specializare.setChartType(PieChart.PeiChartType.DONUT_CHART);

        pie_valoare_burse_tip_bursa.clearData();
        pie_valoare_per_clase.clearData();
        pie_valoare_per_specializare.clearData();
        pie_chart();//pt pie
         
        getContentPane().setBackground(new Color(245, 245, 245));
        TimingTarget targetLogin = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (signIn) {
                    background1.setAnimate(fraction);
                } else {
                    background1.setAnimate(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (signIn) {
                    panelLogin.setVisible(false);
                    background1.setShowPaint(true);
                    panelBody.setAlpha(0);
                    panelBody.setVisible(true);
                    animatorBody.start();
                } else {
                    enableLogin(true);
                    txtUser.grabFocus();
                }
            }
        };
        TimingTarget targetBody = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (signIn) {
                    panelBody.setAlpha(fraction);
                } else {
                    panelBody.setAlpha(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (signIn == false) {
                    panelBody.setVisible(false);
                    background1.setShowPaint(false);
                    background1.setAnimate(1);
                    panelLogin.setVisible(true);
                    animatorLogin.start();
                }
            }
        };
        animatorLogin = new Animator(1500, targetLogin);
        animatorBody = new Animator(500, targetBody);
        animatorLogin.setResolution(0);
        animatorBody.setResolution(0);
//        jScrollPane1.getViewport().setOpaque(false);
//        jScrollPane1.setViewportBorder(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tip_medie = new javax.swing.ButtonGroup();
        background1 = new com.raven.swing.Background();
        panelLogin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmdSignIn = new com.raven.swing.Button();
        txtUser = new com.raven.swing.TextField();
        txtPass = new com.raven.swing.PasswordField();
        panelBody = new com.raven.swing.PanelTransparent();
        header = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        elevi = new javax.swing.JPanel();
        scroll1 = new javax.swing.JScrollPane();
        tabela_elevi = new javax.swing.JTable();
        refresh_elevi = new com.raven.swing.Button();
        adauga_elevi = new com.raven.swing.Button();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        nr_matricol = new javax.swing.JTextField();
        cnp = new javax.swing.JTextField();
        nume = new javax.swing.JTextField();
        prenume = new javax.swing.JTextField();
        tel = new javax.swing.JTextField();
        adresa = new javax.swing.JTextField();
        codclasa = new javax.swing.JTextField();
        acteadi = new javax.swing.JTextField();
        medie = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        nr_matricol_sters = new javax.swing.JTextField();
        sterge_elev = new com.raven.swing.Button();
        jLabel12 = new javax.swing.JLabel();
        nr_matricol_update = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cnp_update = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        nume_update = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        prenume_update = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        medie_update = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        acteadi_update = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        codclasa_update = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        adresa_update = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        tel_update = new javax.swing.JTextField();
        update_elev = new com.raven.swing.Button();
        export_to_excel_elevi = new com.raven.swing.Button();
        secretariat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        elevi_sec = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        aplicare_filtre = new com.raven.swing.Button();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        medie_filtru = new javax.swing.JTextField();
        sec_bursieri_but = new javax.swing.JCheckBox();
        Cresc = new javax.swing.JRadioButton();
        desc = new javax.swing.JRadioButton();
        refresh_elevi_sec = new com.raven.swing.Button();
        jLabel25 = new javax.swing.JLabel();
        nr_matricol_elev_cautat = new javax.swing.JTextField();
        cautare_elev = new com.raven.swing.Button();
        nr_total_elevi = new com.raven.swing.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabele_rapoarte = new javax.swing.JTable();
        jLabel26 = new javax.swing.JLabel();
        nr_total_bursieri = new com.raven.swing.Button();
        nr_total_nebursieri = new com.raven.swing.Button();
        valoare_toatala_burse = new com.raven.swing.Button();
        valoare_burse_per_clasa = new com.raven.swing.Button();
        valaore_burse_per_specializare = new com.raven.swing.Button();
        valaore_burse_per_profil = new com.raven.swing.Button();
        valaore_bursa_per_tip_bursa = new com.raven.swing.Button();
        valaore_bursa_per_student = new com.raven.swing.Button();
        tip_burse_per_clase = new com.raven.swing.Button();
        export_to_excel_raport = new com.raven.swing.Button();
        export_to_excel_date_extrase = new com.raven.swing.Button();
        expoert_pdf_date_extrase = new com.raven.swing.Button();
        import_from_excel = new com.raven.swing.Button();
        clasa_filtru1 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabele_rapoarte1 = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        all_legat_bursieri = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        bursieri = new javax.swing.JPanel();
        scroll3 = new javax.swing.JScrollPane();
        tabela_burse = new javax.swing.JTable();
        scroll2 = new javax.swing.JScrollPane();
        tabela_bursieri = new javax.swing.JTable();
        refresh_bursieri = new com.raven.swing.Button();
        refresh_burse = new com.raven.swing.Button();
        grafice = new javax.swing.JPanel();
        pie_valoare_per_clase = new javaswingdev.chart.PieChart();
        pie_valoare_burse_tip_bursa = new javaswingdev.chart.PieChart();
        pie_valoare_per_specializare = new javaswingdev.chart.PieChart();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        refresh_grafice = new com.raven.swing.Button();
        istoric = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tab_istoric = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background1.setLayout(new java.awt.CardLayout());

        panelLogin.setOpaque(false);

        jPanel1.setOpaque(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/raven/icon/logo_1.png"))); // NOI18N

        cmdSignIn.setBackground(new java.awt.Color(157, 153, 255));
        cmdSignIn.setForeground(new java.awt.Color(255, 255, 255));
        cmdSignIn.setText("Sign In");
        cmdSignIn.setEffectColor(new java.awt.Color(199, 196, 255));
        cmdSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSignInActionPerformed(evt);
            }
        });

        txtUser.setBackground(new java.awt.Color(245, 245, 245));
        txtUser.setLabelText("User Name");
        txtUser.setLineColor(new java.awt.Color(131, 126, 253));
        txtUser.setSelectionColor(new java.awt.Color(157, 153, 255));
        txtUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserActionPerformed(evt);
            }
        });

        txtPass.setBackground(new java.awt.Color(245, 245, 245));
        txtPass.setLabelText("Password");
        txtPass.setLineColor(new java.awt.Color(131, 126, 253));
        txtPass.setSelectionColor(new java.awt.Color(157, 153, 255));
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(446, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(447, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addContainerGap(216, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(303, Short.MAX_VALUE))
        );

        background1.add(panelLogin, "card2");

        header.setBackground(new java.awt.Color(157, 153, 255));

        jButton1.setText("Sign Out");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton1)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        elevi.setBackground(new java.awt.Color(255, 255, 255));

        scroll1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scroll1MouseClicked(evt);
            }
        });

        tabela_elevi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabela_elevi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabela_eleviMouseClicked(evt);
            }
        });
        scroll1.setViewportView(tabela_elevi);

        refresh_elevi.setBackground(new java.awt.Color(157, 153, 255));
        refresh_elevi.setForeground(new java.awt.Color(255, 255, 255));
        refresh_elevi.setText("Refresh");
        refresh_elevi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh_eleviMouseClicked(evt);
            }
        });

        adauga_elevi.setBackground(new java.awt.Color(157, 153, 255));
        adauga_elevi.setForeground(new java.awt.Color(255, 255, 255));
        adauga_elevi.setText("Adauga Elev");
        adauga_elevi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adauga_eleviMouseClicked(evt);
            }
        });
        adauga_elevi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adauga_eleviActionPerformed(evt);
            }
        });

        jLabel2.setText("Nr Matricol");

        jLabel3.setText("CNP");

        jLabel4.setText("Nume");

        jLabel5.setText("Prenume");

        jLabel6.setText("Nr Tel");

        jLabel7.setText("Adresa");

        jLabel8.setText("Medie");

        jLabel9.setText("Acte Aditionale");

        jLabel10.setText("Cod Clasa");

        nr_matricol.setBackground(new java.awt.Color(205, 180, 219));
        nr_matricol.setForeground(new java.awt.Color(255, 255, 255));
        nr_matricol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nr_matricolActionPerformed(evt);
            }
        });

        cnp.setBackground(new java.awt.Color(205, 180, 219));
        cnp.setForeground(new java.awt.Color(255, 255, 255));
        cnp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cnpActionPerformed(evt);
            }
        });

        nume.setBackground(new java.awt.Color(205, 180, 219));
        nume.setForeground(new java.awt.Color(255, 255, 255));
        nume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numeActionPerformed(evt);
            }
        });

        prenume.setBackground(new java.awt.Color(205, 180, 219));
        prenume.setForeground(new java.awt.Color(255, 255, 255));
        prenume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenumeActionPerformed(evt);
            }
        });

        tel.setBackground(new java.awt.Color(205, 180, 219));
        tel.setForeground(new java.awt.Color(255, 255, 255));
        tel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telActionPerformed(evt);
            }
        });

        adresa.setBackground(new java.awt.Color(205, 180, 219));
        adresa.setForeground(new java.awt.Color(255, 255, 255));
        adresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adresaActionPerformed(evt);
            }
        });

        codclasa.setBackground(new java.awt.Color(205, 180, 219));
        codclasa.setForeground(new java.awt.Color(255, 255, 255));
        codclasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codclasaActionPerformed(evt);
            }
        });

        acteadi.setBackground(new java.awt.Color(205, 180, 219));
        acteadi.setForeground(new java.awt.Color(255, 255, 255));
        acteadi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acteadiActionPerformed(evt);
            }
        });

        medie.setBackground(new java.awt.Color(205, 180, 219));
        medie.setForeground(new java.awt.Color(255, 255, 255));
        medie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medieActionPerformed(evt);
            }
        });

        jLabel11.setText("Nr Matricol Elev - de eliminat");

        nr_matricol_sters.setBackground(new java.awt.Color(205, 180, 219));
        nr_matricol_sters.setForeground(new java.awt.Color(255, 255, 255));
        nr_matricol_sters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nr_matricol_stersActionPerformed(evt);
            }
        });

        sterge_elev.setBackground(new java.awt.Color(157, 153, 255));
        sterge_elev.setForeground(new java.awt.Color(255, 255, 255));
        sterge_elev.setText("Sterge Elev");
        sterge_elev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sterge_elevMouseClicked(evt);
            }
        });
        sterge_elev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sterge_elevActionPerformed(evt);
            }
        });

        jLabel12.setText("Nr Matricol");

        nr_matricol_update.setBackground(new java.awt.Color(205, 180, 219));
        nr_matricol_update.setForeground(new java.awt.Color(255, 255, 255));
        nr_matricol_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nr_matricol_updateActionPerformed(evt);
            }
        });

        jLabel13.setText("CNP");

        cnp_update.setBackground(new java.awt.Color(205, 180, 219));
        cnp_update.setForeground(new java.awt.Color(255, 255, 255));
        cnp_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cnp_updateActionPerformed(evt);
            }
        });

        jLabel14.setText("Nume");

        nume_update.setBackground(new java.awt.Color(205, 180, 219));
        nume_update.setForeground(new java.awt.Color(255, 255, 255));
        nume_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nume_updateActionPerformed(evt);
            }
        });

        jLabel15.setText("Prenume");

        prenume_update.setBackground(new java.awt.Color(205, 180, 219));
        prenume_update.setForeground(new java.awt.Color(255, 255, 255));
        prenume_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenume_updateActionPerformed(evt);
            }
        });

        jLabel16.setText("Medie");

        medie_update.setBackground(new java.awt.Color(205, 180, 219));
        medie_update.setForeground(new java.awt.Color(255, 255, 255));
        medie_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medie_updateActionPerformed(evt);
            }
        });

        jLabel17.setText("Acte Aditionale");

        acteadi_update.setBackground(new java.awt.Color(205, 180, 219));
        acteadi_update.setForeground(new java.awt.Color(255, 255, 255));
        acteadi_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acteadi_updateActionPerformed(evt);
            }
        });

        jLabel18.setText("Cod Clasa");

        codclasa_update.setBackground(new java.awt.Color(205, 180, 219));
        codclasa_update.setForeground(new java.awt.Color(255, 255, 255));
        codclasa_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codclasa_updateActionPerformed(evt);
            }
        });

        jLabel19.setText("Adresa");

        adresa_update.setBackground(new java.awt.Color(205, 180, 219));
        adresa_update.setForeground(new java.awt.Color(255, 255, 255));
        adresa_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adresa_updateActionPerformed(evt);
            }
        });

        jLabel20.setText("Nr Tel");

        tel_update.setBackground(new java.awt.Color(205, 180, 219));
        tel_update.setForeground(new java.awt.Color(255, 255, 255));
        tel_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tel_updateActionPerformed(evt);
            }
        });

        update_elev.setBackground(new java.awt.Color(157, 153, 255));
        update_elev.setForeground(new java.awt.Color(255, 255, 255));
        update_elev.setText("Modifica Elev");
        update_elev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                update_elevMouseClicked(evt);
            }
        });
        update_elev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_elevActionPerformed(evt);
            }
        });

        export_to_excel_elevi.setBackground(new java.awt.Color(0, 204, 0));
        export_to_excel_elevi.setForeground(new java.awt.Color(255, 255, 255));
        export_to_excel_elevi.setText("Export Elevi to EXCEL");
        export_to_excel_elevi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                export_to_excel_eleviMouseClicked(evt);
            }
        });
        export_to_excel_elevi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                export_to_excel_eleviActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout eleviLayout = new javax.swing.GroupLayout(elevi);
        elevi.setLayout(eleviLayout);
        eleviLayout.setHorizontalGroup(
            eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll1)
            .addGroup(eleviLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(export_to_excel_elevi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, eleviLayout.createSequentialGroup()
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nr_matricol)
                            .addComponent(cnp)
                            .addComponent(nume)
                            .addComponent(prenume)
                            .addComponent(medie)
                            .addComponent(acteadi)
                            .addComponent(codclasa)
                            .addComponent(adresa)
                            .addComponent(tel, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(adauga_elevi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(eleviLayout.createSequentialGroup()
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nr_matricol_update)
                            .addComponent(cnp_update)
                            .addComponent(nume_update)
                            .addComponent(prenume_update)
                            .addComponent(medie_update)
                            .addComponent(acteadi_update)
                            .addComponent(codclasa_update)
                            .addComponent(adresa_update)
                            .addComponent(tel_update, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(update_elev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(174, 174, 174)
                .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sterge_elev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nr_matricol_sters)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 103, Short.MAX_VALUE)
                .addComponent(refresh_elevi, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        eleviLayout.setVerticalGroup(
            eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(eleviLayout.createSequentialGroup()
                .addComponent(scroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(eleviLayout.createSequentialGroup()
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(refresh_elevi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nr_matricol_sters, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sterge_elev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(eleviLayout.createSequentialGroup()
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nr_matricol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cnp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(nume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(prenume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(medie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(acteadi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(codclasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(adresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(tel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(eleviLayout.createSequentialGroup()
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(nr_matricol_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cnp_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(nume_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(prenume_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(medie_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(acteadi_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(codclasa_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(adresa_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(tel_update, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(eleviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adauga_elevi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update_elev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(export_to_excel_elevi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Elevi", elevi);

        secretariat.setBackground(new java.awt.Color(255, 255, 255));

        elevi_sec.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(elevi_sec);

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel21.setText("Date Extrase");

        aplicare_filtre.setBackground(new java.awt.Color(157, 153, 255));
        aplicare_filtre.setForeground(new java.awt.Color(255, 255, 255));
        aplicare_filtre.setText("Aplicare Filtre");
        aplicare_filtre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aplicare_filtreMouseClicked(evt);
            }
        });
        aplicare_filtre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicare_filtreActionPerformed(evt);
            }
        });

        jLabel22.setText("Clasa:");

        jLabel23.setText("Bursieri ON/OFF");

        jLabel24.setText("Medie:");

        medie_filtru.setBackground(new java.awt.Color(205, 180, 219));
        medie_filtru.setForeground(new java.awt.Color(255, 255, 255));
        medie_filtru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medie_filtruActionPerformed(evt);
            }
        });

        sec_bursieri_but.setBackground(new java.awt.Color(205, 180, 219));

        Cresc.setBackground(new java.awt.Color(255, 255, 255));
        tip_medie.add(Cresc);
        Cresc.setText("Crescator");
        Cresc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrescActionPerformed(evt);
            }
        });

        desc.setBackground(new java.awt.Color(255, 255, 255));
        tip_medie.add(desc);
        desc.setSelected(true);
        desc.setText("Descrescator");
        desc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descActionPerformed(evt);
            }
        });

        refresh_elevi_sec.setBackground(new java.awt.Color(157, 153, 255));
        refresh_elevi_sec.setForeground(new java.awt.Color(255, 255, 255));
        refresh_elevi_sec.setText("ALL");
        refresh_elevi_sec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh_elevi_secMouseClicked(evt);
            }
        });
        refresh_elevi_sec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresh_elevi_secActionPerformed(evt);
            }
        });

        jLabel25.setText("Nr matricol al elevului cautat:");

        nr_matricol_elev_cautat.setBackground(new java.awt.Color(205, 180, 219));
        nr_matricol_elev_cautat.setForeground(new java.awt.Color(255, 255, 255));
        nr_matricol_elev_cautat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nr_matricol_elev_cautatActionPerformed(evt);
            }
        });

        cautare_elev.setBackground(new java.awt.Color(157, 153, 255));
        cautare_elev.setForeground(new java.awt.Color(255, 255, 255));
        cautare_elev.setText("Cauta");
        cautare_elev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cautare_elevMouseClicked(evt);
            }
        });
        cautare_elev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cautare_elevActionPerformed(evt);
            }
        });

        nr_total_elevi.setBackground(new java.awt.Color(157, 153, 255));
        nr_total_elevi.setForeground(new java.awt.Color(255, 255, 255));
        nr_total_elevi.setText("Nr total elevi");
        nr_total_elevi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nr_total_eleviMouseClicked(evt);
            }
        });
        nr_total_elevi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nr_total_eleviActionPerformed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(153, 153, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 255), null, null));

        tabele_rapoarte.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tabele_rapoarte);

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 14)); // NOI18N
        jLabel26.setText("Rapoarte");

        nr_total_bursieri.setBackground(new java.awt.Color(157, 153, 255));
        nr_total_bursieri.setForeground(new java.awt.Color(255, 255, 255));
        nr_total_bursieri.setText("Nr total de bursieri");
        nr_total_bursieri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nr_total_bursieriMouseClicked(evt);
            }
        });
        nr_total_bursieri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nr_total_bursieriActionPerformed(evt);
            }
        });

        nr_total_nebursieri.setBackground(new java.awt.Color(157, 153, 255));
        nr_total_nebursieri.setForeground(new java.awt.Color(255, 255, 255));
        nr_total_nebursieri.setText("Nr total de nebursieri");
        nr_total_nebursieri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nr_total_nebursieriMouseClicked(evt);
            }
        });
        nr_total_nebursieri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nr_total_nebursieriActionPerformed(evt);
            }
        });

        valoare_toatala_burse.setBackground(new java.awt.Color(157, 153, 255));
        valoare_toatala_burse.setForeground(new java.awt.Color(255, 255, 255));
        valoare_toatala_burse.setText("Valoare totala burse ");
        valoare_toatala_burse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                valoare_toatala_burseMouseClicked(evt);
            }
        });
        valoare_toatala_burse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valoare_toatala_burseActionPerformed(evt);
            }
        });

        valoare_burse_per_clasa.setBackground(new java.awt.Color(157, 153, 255));
        valoare_burse_per_clasa.setForeground(new java.awt.Color(255, 255, 255));
        valoare_burse_per_clasa.setText("Valoare burse per clasa ");
        valoare_burse_per_clasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                valoare_burse_per_clasaMouseClicked(evt);
            }
        });
        valoare_burse_per_clasa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valoare_burse_per_clasaActionPerformed(evt);
            }
        });

        valaore_burse_per_specializare.setBackground(new java.awt.Color(157, 153, 255));
        valaore_burse_per_specializare.setForeground(new java.awt.Color(255, 255, 255));
        valaore_burse_per_specializare.setText("Valoare burse per Specializare");
        valaore_burse_per_specializare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                valaore_burse_per_specializareMouseClicked(evt);
            }
        });
        valaore_burse_per_specializare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valaore_burse_per_specializareActionPerformed(evt);
            }
        });

        valaore_burse_per_profil.setBackground(new java.awt.Color(157, 153, 255));
        valaore_burse_per_profil.setForeground(new java.awt.Color(255, 255, 255));
        valaore_burse_per_profil.setText("Valoare burse per Profil");
        valaore_burse_per_profil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                valaore_burse_per_profilMouseClicked(evt);
            }
        });
        valaore_burse_per_profil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valaore_burse_per_profilActionPerformed(evt);
            }
        });

        valaore_bursa_per_tip_bursa.setBackground(new java.awt.Color(157, 153, 255));
        valaore_bursa_per_tip_bursa.setForeground(new java.awt.Color(255, 255, 255));
        valaore_bursa_per_tip_bursa.setText("Valoare burse per Tip Bursa");
        valaore_bursa_per_tip_bursa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                valaore_bursa_per_tip_bursaMouseClicked(evt);
            }
        });
        valaore_bursa_per_tip_bursa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valaore_bursa_per_tip_bursaActionPerformed(evt);
            }
        });

        valaore_bursa_per_student.setBackground(new java.awt.Color(157, 153, 255));
        valaore_bursa_per_student.setForeground(new java.awt.Color(255, 255, 255));
        valaore_bursa_per_student.setText("Valoare burse per Elev");
        valaore_bursa_per_student.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                valaore_bursa_per_studentMouseClicked(evt);
            }
        });
        valaore_bursa_per_student.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valaore_bursa_per_studentActionPerformed(evt);
            }
        });

        tip_burse_per_clase.setBackground(new java.awt.Color(157, 153, 255));
        tip_burse_per_clase.setForeground(new java.awt.Color(255, 255, 255));
        tip_burse_per_clase.setText("Tip bursa per Clase");
        tip_burse_per_clase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tip_burse_per_claseMouseClicked(evt);
            }
        });
        tip_burse_per_clase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tip_burse_per_claseActionPerformed(evt);
            }
        });

        export_to_excel_raport.setBackground(new java.awt.Color(0, 204, 0));
        export_to_excel_raport.setForeground(new java.awt.Color(255, 255, 255));
        export_to_excel_raport.setText("Export Raport to EXCEL");
        export_to_excel_raport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                export_to_excel_raportMouseClicked(evt);
            }
        });
        export_to_excel_raport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                export_to_excel_raportActionPerformed(evt);
            }
        });

        export_to_excel_date_extrase.setBackground(new java.awt.Color(0, 204, 0));
        export_to_excel_date_extrase.setForeground(new java.awt.Color(255, 255, 255));
        export_to_excel_date_extrase.setText("Export Date Extrase to EXCEL");
        export_to_excel_date_extrase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                export_to_excel_date_extraseMouseClicked(evt);
            }
        });
        export_to_excel_date_extrase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                export_to_excel_date_extraseActionPerformed(evt);
            }
        });

        expoert_pdf_date_extrase.setBackground(new java.awt.Color(0, 204, 0));
        expoert_pdf_date_extrase.setForeground(new java.awt.Color(255, 255, 255));
        expoert_pdf_date_extrase.setText("Export Date Extrase to PDF");
        expoert_pdf_date_extrase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expoert_pdf_date_extraseMouseClicked(evt);
            }
        });
        expoert_pdf_date_extrase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expoert_pdf_date_extraseActionPerformed(evt);
            }
        });

        import_from_excel.setBackground(new java.awt.Color(0, 204, 255));
        import_from_excel.setForeground(new java.awt.Color(255, 255, 255));
        import_from_excel.setText("Import Date Extrase from Excel");
        import_from_excel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                import_from_excelMouseClicked(evt);
            }
        });
        import_from_excel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                import_from_excelActionPerformed(evt);
            }
        });

        clasa_filtru1.setBackground(new java.awt.Color(205, 180, 219));
        clasa_filtru1.setForeground(new java.awt.Color(255, 255, 255));
        clasa_filtru1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clasa_filtru1ActionPerformed(evt);
            }
        });

        jScrollPane5.setBackground(new java.awt.Color(153, 153, 255));
        jScrollPane5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 255), null, null));

        tabele_rapoarte1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tabele_rapoarte1);

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        jLabel30.setText("Date Importate");

        all_legat_bursieri.setBackground(new java.awt.Color(205, 180, 219));
        all_legat_bursieri.setSelected(true);
        all_legat_bursieri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                all_legat_bursieriActionPerformed(evt);
            }
        });

        jLabel31.setText("Toti din clasa ON/OFF");

        javax.swing.GroupLayout secretariatLayout = new javax.swing.GroupLayout(secretariat);
        secretariat.setLayout(secretariatLayout);
        secretariatLayout.setHorizontalGroup(
            secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secretariatLayout.createSequentialGroup()
                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(secretariatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(secretariatLayout.createSequentialGroup()
                                .addComponent(sec_bursieri_but)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(secretariatLayout.createSequentialGroup()
                                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(medie_filtru, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(desc)
                                    .addComponent(Cresc)))
                            .addComponent(refresh_elevi_sec, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(aplicare_filtre, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(secretariatLayout.createSequentialGroup()
                                .addComponent(clasa_filtru1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(all_legat_bursieri)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(87, 87, 87)
                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cautare_elev, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nr_matricol_elev_cautat, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(import_from_excel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(secretariatLayout.createSequentialGroup()
                                .addComponent(valaore_burse_per_specializare, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nr_total_elevi, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(export_to_excel_raport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(export_to_excel_date_extrase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(expoert_pdf_date_extrase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(secretariatLayout.createSequentialGroup()
                                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(valaore_burse_per_profil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(valaore_bursa_per_tip_bursa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(valaore_bursa_per_student, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tip_burse_per_clase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nr_total_bursieri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nr_total_nebursieri, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(valoare_toatala_burse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(valoare_burse_per_clasa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secretariatLayout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addContainerGap())))
            .addGroup(secretariatLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 591, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        secretariatLayout.setVerticalGroup(
            secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secretariatLayout.createSequentialGroup()
                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secretariatLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel21)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, secretariatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secretariatLayout.createSequentialGroup()
                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(4, 4, 4)
                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(secretariatLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(nr_matricol_elev_cautat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(valaore_burse_per_specializare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(nr_total_elevi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cautare_elev, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(secretariatLayout.createSequentialGroup()
                                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(nr_total_bursieri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valaore_burse_per_profil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(nr_total_nebursieri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valaore_bursa_per_tip_bursa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(valoare_toatala_burse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(valaore_bursa_per_student, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(valoare_burse_per_clasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tip_burse_per_clase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(export_to_excel_raport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(export_to_excel_date_extrase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(expoert_pdf_date_extrase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(import_from_excel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(secretariatLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(4, 4, 4)
                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(secretariatLayout.createSequentialGroup()
                                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(secretariatLayout.createSequentialGroup()
                                        .addComponent(clasa_filtru1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(secretariatLayout.createSequentialGroup()
                                        .addComponent(all_legat_bursieri, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(16, 16, 16)))
                                .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(secretariatLayout.createSequentialGroup()
                                        .addComponent(jLabel24)
                                        .addGap(6, 6, 6)
                                        .addComponent(medie_filtru, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(secretariatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(sec_bursieri_but, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel23))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(aplicare_filtre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(refresh_elevi_sec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(secretariatLayout.createSequentialGroup()
                                        .addComponent(Cresc)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(desc)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(secretariatLayout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addGap(266, 266, 266)))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Secretariat", secretariat);

        bursieri.setBackground(new java.awt.Color(255, 255, 255));

        tabela_burse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scroll3.setViewportView(tabela_burse);

        tabela_bursieri.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scroll2.setViewportView(tabela_bursieri);

        refresh_bursieri.setBackground(new java.awt.Color(157, 153, 255));
        refresh_bursieri.setForeground(new java.awt.Color(255, 255, 255));
        refresh_bursieri.setText("Refresh Bursieri");
        refresh_bursieri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh_bursieriMouseClicked(evt);
            }
        });

        refresh_burse.setBackground(new java.awt.Color(157, 153, 255));
        refresh_burse.setForeground(new java.awt.Color(255, 255, 255));
        refresh_burse.setText("Refresh Burse");
        refresh_burse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh_burseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bursieriLayout = new javax.swing.GroupLayout(bursieri);
        bursieri.setLayout(bursieriLayout);
        bursieriLayout.setHorizontalGroup(
            bursieriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bursieriLayout.createSequentialGroup()
                .addComponent(refresh_bursieri, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(bursieriLayout.createSequentialGroup()
                .addComponent(scroll2, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bursieriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll3, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refresh_burse, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );
        bursieriLayout.setVerticalGroup(
            bursieriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bursieriLayout.createSequentialGroup()
                .addGroup(bursieriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(bursieriLayout.createSequentialGroup()
                        .addComponent(scroll3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(refresh_burse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refresh_bursieri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 138, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Bursieri", bursieri);

        grafice.setBackground(new java.awt.Color(255, 255, 255));

        pie_valoare_per_clase.setBackground(new java.awt.Color(204, 204, 255));
        pie_valoare_per_clase.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        pie_valoare_burse_tip_bursa.setBackground(new java.awt.Color(204, 204, 255));
        pie_valoare_burse_tip_bursa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        pie_valoare_per_specializare.setBackground(new java.awt.Color(204, 204, 255));
        pie_valoare_per_specializare.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel27.setText("Pie Burse per Clase");

        jLabel28.setText("Pie Valoare burse per tip Bursa");

        jLabel29.setText("Pie Valoare burse per Specializare");

        refresh_grafice.setBackground(new java.awt.Color(157, 153, 255));
        refresh_grafice.setForeground(new java.awt.Color(255, 255, 255));
        refresh_grafice.setText("Tip bursa per Clase");
        refresh_grafice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh_graficeMouseClicked(evt);
            }
        });
        refresh_grafice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresh_graficeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout graficeLayout = new javax.swing.GroupLayout(grafice);
        grafice.setLayout(graficeLayout);
        graficeLayout.setHorizontalGroup(
            graficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graficeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pie_valoare_per_clase, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pie_valoare_burse_tip_bursa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(pie_valoare_per_specializare, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(graficeLayout.createSequentialGroup()
                .addGap(169, 169, 169)
                .addComponent(jLabel27)
                .addGap(304, 304, 304)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(108, 108, 108))
            .addGroup(graficeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(refresh_grafice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        graficeLayout.setVerticalGroup(
            graficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graficeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(graficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pie_valoare_per_clase, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addComponent(pie_valoare_burse_tip_bursa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pie_valoare_per_specializare, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(graficeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(refresh_grafice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Grafice", grafice);

        istoric.setBackground(new java.awt.Color(255, 255, 255));

        tab_istoric.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tab_istoric.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tab_istoricMouseMoved(evt);
            }
        });
        tab_istoric.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tab_istoricMouseEntered(evt);
            }
        });
        jScrollPane3.setViewportView(tab_istoric);

        javax.swing.GroupLayout istoricLayout = new javax.swing.GroupLayout(istoric);
        istoric.setLayout(istoricLayout);
        istoricLayout.setHorizontalGroup(
            istoricLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1238, Short.MAX_VALUE)
        );
        istoricLayout.setVerticalGroup(
            istoricLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Istoric", istoric);

        javax.swing.GroupLayout panelBodyLayout = new javax.swing.GroupLayout(panelBody);
        panelBody.setLayout(panelBodyLayout);
        panelBodyLayout.setHorizontalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        panelBodyLayout.setVerticalGroup(
            panelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        background1.add(panelBody, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSignInActionPerformed
        
       
        
        if (!animatorLogin.isRunning()) {
            signIn = true;
            String user = txtUser.getText().trim();
            String pass = String.valueOf(txtPass.getPassword());
            jTabbedPane1.removeAll();
            boolean action = true;
            if (user.equals("")) {
                txtUser.setHelperText("Please input user name");
                txtUser.grabFocus();
                action = false;
            }
            if (pass.equals("")) {
                txtPass.setHelperText("Please input password");
                if (action) {
                    txtPass.grabFocus();
                }
                action = false;
            }
            boolean check = database.loginCheck(user, pass);
            String rol = database.getRol(user);
             if (check == false)
            {
                txtUser.setHelperText("Please enter valid email");
                txtPass.setHelperText("Please enter valid password");
                action = false;
            }
            
            if (action) {
                animatorLogin.start();
                utilizator_actual=user;
                if (rol.equals("admin")) {
                    
                    jTabbedPane1.add("Elevi",elevi);
                    jTabbedPane1.addTab("Bursieri",bursieri);
                    jTabbedPane1.addTab("Grafice", grafice);
                    jTabbedPane1.addTab("Istoric", istoric);
                    tabela_elevi.setModel(database.afisareElevi());
                    tabela_bursieri.setModel(database.afisareBursieri());
                    tabela_burse.setModel(database.afisare_burse());
                    tab_istoric.setModel(database.afisare_istoric());
                }
                if (rol.equals("secretariat")) {
                    jTabbedPane1.add("Secretariat",secretariat);
                    jTabbedPane1.addTab("Bursieri",bursieri);
                    jTabbedPane1.addTab("Grafice", grafice);
                    elevi_sec.setModel(database.afisareElevi());
                    tabela_bursieri.setModel(database.afisareBursieri());
                    tabela_burse.setModel(database.afisare_burse());
                    
                }
                
                enableLogin(false);
            }
        }
         database.adaugaEveniment(utilizator_actual, "Eveniment: sing in");
    }//GEN-LAST:event_cmdSignInActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        signIn = false;
        clearLogin();
        animatorBody.start();
        database.adaugaEveniment(utilizator_actual, "Eveniment: sing out");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void refresh_eleviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh_eleviMouseClicked
        // TODO add your handling code here:
        tabela_elevi.setModel(database.afisareElevi());
        database.adaugaEveniment(utilizator_actual, "Eveniment: Refresh elevi");
    }//GEN-LAST:event_refresh_eleviMouseClicked

    private void refresh_bursieriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh_bursieriMouseClicked
        // TODO add your handling code here:
        tabela_bursieri.setModel(database.afisareBursieri());
         database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton refresh_bursieriMouse elev");
        
    }//GEN-LAST:event_refresh_bursieriMouseClicked

    private void adauga_eleviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adauga_eleviMouseClicked
       int nr_matricol= Integer.parseInt(this.nr_matricol.getText().trim());
       String cnp = this.cnp.getText().trim();
       String nume =this.nume.getText().trim();
       String prenume =this.prenume.getText().trim();
       String nr_tel=this.tel.getText().trim();
       String adresa = this.adresa.getText().trim();
       float medie =Float.parseFloat(this.medie.getText().trim());
       String acte_adi =this.acteadi.getText().trim();
       int codclasa=Integer.parseInt(this.codclasa.getText().trim());
       
       
       int ok = database.adaugaElev(nr_matricol, nume, prenume, cnp, nr_tel, adresa, medie, acte_adi, codclasa);
       if(ok>=1)
       {
           JOptionPane.showMessageDialog(this, "Elevul a fost adaugat cu succes!");
           database.updateBursieri();
       }
       else{
           JOptionPane.showMessageDialog(this, "Elevul nu a putut sa fie adaugat!");
       }
       database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton insert elev");
               
       tabela_elevi.setModel(database.afisareElevi());
       tabela_bursieri.setModel(database.afisareBursieri());
       pie_valoare_burse_tip_bursa.clearData();
        pie_valoare_per_clase.clearData();
        pie_valoare_per_specializare.clearData();
         pie_chart();//pt pie
       
    }//GEN-LAST:event_adauga_eleviMouseClicked

    private void adauga_eleviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adauga_eleviActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adauga_eleviActionPerformed

    private void nr_matricolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nr_matricolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nr_matricolActionPerformed

    private void cnpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cnpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cnpActionPerformed

    private void numeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numeActionPerformed

    private void prenumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenumeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenumeActionPerformed

    private void telActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telActionPerformed

    private void adresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adresaActionPerformed

    private void codclasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codclasaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codclasaActionPerformed

    private void acteadiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acteadiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_acteadiActionPerformed

    private void medieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_medieActionPerformed

    private void nr_matricol_stersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nr_matricol_stersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nr_matricol_stersActionPerformed

    private void sterge_elevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sterge_elevMouseClicked
        // TODO add your handling code here:
        
        int nr_matricol = Integer.parseInt(this.nr_matricol_sters.getText().trim());
        int rez = database.eliminaElev(nr_matricol);
        
        if(rez>=1)
        {
            JOptionPane.showMessageDialog(this, "Elevul a fost sters cu succes!");
            tabela_elevi.setModel(database.afisareElevi());
            tabela_bursieri.setModel(database.afisareBursieri());
        }
        else{
            JOptionPane.showMessageDialog(this, "Elevul nu a fost sters!");
        }
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton sterge elev");
        pie_valoare_burse_tip_bursa.clearData();
        pie_valoare_per_clase.clearData();
        pie_valoare_per_specializare.clearData();
         pie_chart();//pt pie
 
    }//GEN-LAST:event_sterge_elevMouseClicked

    private void sterge_elevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sterge_elevActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sterge_elevActionPerformed

    private void nr_matricol_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nr_matricol_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nr_matricol_updateActionPerformed

    private void cnp_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cnp_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cnp_updateActionPerformed

    private void nume_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nume_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nume_updateActionPerformed

    private void prenume_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenume_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenume_updateActionPerformed

    private void medie_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medie_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_medie_updateActionPerformed

    private void acteadi_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acteadi_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_acteadi_updateActionPerformed

    private void codclasa_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codclasa_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codclasa_updateActionPerformed

    private void adresa_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adresa_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adresa_updateActionPerformed

    private void tel_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tel_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tel_updateActionPerformed

    private void update_elevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_update_elevMouseClicked


       int nr_matricol= Integer.parseInt(this.nr_matricol_update.getText().trim());
       String cnp = this.cnp_update.getText().trim();
       String nume =this.nume_update.getText().trim();
       String prenume =this.prenume_update.getText().trim();
       String nr_tel=this.tel_update.getText().trim();
       String adresa = this.adresa_update.getText().trim();
       float medie =Float.parseFloat(this.medie_update.getText().trim());
       String acte_adi =this.acteadi_update.getText().trim();
       int codclasa=Integer.parseInt(this.codclasa_update.getText().trim());
       
       
       int ok = database.modificaElev(nr_matricol, nume, prenume, cnp, nr_tel, adresa, medie, acte_adi, codclasa);
       if(ok>=1)
       {
           JOptionPane.showMessageDialog(this, "Elevul a fost mod cu succes!");
           database.updateBursieri();
       }
       else{
           JOptionPane.showMessageDialog(this, "Elevul nu a putut sa fie mod adaugat!");
       }
                
       database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton update elev");        
       tabela_elevi.setModel(database.afisareElevi());
       tabela_bursieri.setModel(database.afisareBursieri());
       
        pie_valoare_burse_tip_bursa.clearData();
        pie_valoare_per_clase.clearData();
        pie_valoare_per_specializare.clearData();
         pie_chart();//pt pie



        // TODO add your handling code here:
    }//GEN-LAST:event_update_elevMouseClicked

    private void update_elevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_elevActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_elevActionPerformed

    private void txtUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserActionPerformed

    private void aplicare_filtreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aplicare_filtreMouseClicked
        // TODO add your handling code here:
        int codcla; 
        float medie;
 
        if(clasa_filtru1.getText().trim()!=null && !clasa_filtru1.getText().trim().equals(""))
        {
            codcla= Integer.parseInt(clasa_filtru1.getText().trim());
            elevi_sec.setModel(database.filtru_clasa(codcla));
        }
        
        if(medie_filtru.getText().trim()!=null && !medie_filtru.getText().trim().equals(""))
        {
            medie =Float.parseFloat(medie_filtru.getText().trim());
            elevi_sec.setModel(database.filtru_medie(medie,Cresc.isSelected()));
        }
        
        if (sec_bursieri_but.isSelected()==true &&all_legat_bursieri.isSelected()==false) {
            elevi_sec.setModel(database.filtru_bursieri());
        }
        
        if (sec_bursieri_but.isSelected()==false &&all_legat_bursieri.isSelected()==false) {
            elevi_sec.setModel(database.filtru_nu_bursieri());
        }
        
        //clasa+bursier
         if(clasa_filtru1.getText().trim()!=null && !clasa_filtru1.getText().trim().equals("") && sec_bursieri_but.isSelected()==true&&all_legat_bursieri.isSelected()==false)
        {
            codcla= Integer.parseInt(clasa_filtru1.getText().trim());
            elevi_sec.setModel(database.filtru_clasa_bursieri(codcla));
        }
        if(clasa_filtru1.getText().trim()!=null && !clasa_filtru1.getText().trim().equals("") && sec_bursieri_but.isSelected()==false&&all_legat_bursieri.isSelected()==false) 
        {
            codcla= Integer.parseInt(clasa_filtru1.getText().trim());
            elevi_sec.setModel(database.filtru_clasa_nu_bursieri(codcla));
        }
        //clasa+medie
         if(clasa_filtru1.getText().trim()!=null && !clasa_filtru1.getText().trim().equals("") && medie_filtru.getText().trim()!=null && !medie_filtru.getText().trim().equals("") )
        {
            codcla= Integer.parseInt(clasa_filtru1.getText().trim());
            medie =Float.parseFloat(medie_filtru.getText().trim());
            elevi_sec.setModel(database.filtru_clasa_medie(codcla, medie, Cresc.isSelected()));
            
        }
         
        //bursier+medi
        if (medie_filtru.getText().trim()!=null && !medie_filtru.getText().trim().equals("")&&sec_bursieri_but.isSelected()&&all_legat_bursieri.isSelected()==false) {
            medie =Float.parseFloat(medie_filtru.getText().trim());
            elevi_sec.setModel(database.filtru_bursier_medie(medie, Cresc.isSelected(), sec_bursieri_but.isSelected()));
        }
        if (medie_filtru.getText().trim()!=null && !medie_filtru.getText().trim().equals("")&&!sec_bursieri_but.isSelected()&&all_legat_bursieri.isSelected()==false) {
            medie =Float.parseFloat(medie_filtru.getText().trim());
            elevi_sec.setModel(database.filtru_bursier_medie(medie, Cresc.isSelected(), sec_bursieri_but.isSelected()));
        }
        
        //clasa medie bursier
        if(clasa_filtru1.getText().trim()!=null && !clasa_filtru1.getText().trim().equals("") && medie_filtru.getText().trim()!=null && !medie_filtru.getText().trim().equals("") && sec_bursieri_but.isSelected() &&all_legat_bursieri.isSelected()==false)
        {
            codcla= Integer.parseInt(clasa_filtru1.getText().trim());
            medie =Float.parseFloat(medie_filtru.getText().trim());
            elevi_sec.setModel(database.filtru_bursier_medie_clasa(codcla, medie, Cresc.isSelected(), sec_bursieri_but.isSelected()));
        }
        if (clasa_filtru1.getText().trim()!=null && !clasa_filtru1.getText().trim().equals("") && medie_filtru.getText().trim()!=null && !medie_filtru.getText().trim().equals("") && !sec_bursieri_but.isSelected()&&all_legat_bursieri.isSelected()==false) {
            codcla= Integer.parseInt(clasa_filtru1.getText().trim());
            medie =Float.parseFloat(medie_filtru.getText().trim());
             elevi_sec.setModel(database.filtru_bursier_medie_clasa(codcla, medie, Cresc.isSelected(), sec_bursieri_but.isSelected()));
            
        }
        
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton filtrare elevi");
      
        
        
    }//GEN-LAST:event_aplicare_filtreMouseClicked

    private void aplicare_filtreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aplicare_filtreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_aplicare_filtreActionPerformed

    private void medie_filtruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medie_filtruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_medie_filtruActionPerformed

    private void CrescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CrescActionPerformed

    private void descActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descActionPerformed

    private void refresh_elevi_secMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh_elevi_secMouseClicked
        // TODO add your handling code here:
        
        elevi_sec.setModel(database.afisareElevi());
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton all elevi");
        
    }//GEN-LAST:event_refresh_elevi_secMouseClicked

    private void refresh_elevi_secActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresh_elevi_secActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refresh_elevi_secActionPerformed

    private void nr_matricol_elev_cautatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nr_matricol_elev_cautatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nr_matricol_elev_cautatActionPerformed

    private void cautare_elevMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cautare_elevMouseClicked
        // TODO add your handling code here:
        
       int nrmatricol;
       if(nr_matricol_elev_cautat.getText().trim()!=null && !nr_matricol_elev_cautat.getText().trim().equals(""))
        {
            nrmatricol= Integer.parseInt(nr_matricol_elev_cautat.getText().trim());
            elevi_sec.setModel(database.cautare_elev(nrmatricol));
        }
       database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton cautare elev");
        
        
    }//GEN-LAST:event_cautare_elevMouseClicked

    private void cautare_elevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cautare_elevActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cautare_elevActionPerformed

    private void nr_total_eleviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nr_total_eleviMouseClicked
        // TODO add your handling code here:

       tabele_rapoarte.setModel(database.nr_total_elevi());
       database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton nr_total_elevi elevi");



        
    }//GEN-LAST:event_nr_total_eleviMouseClicked

    private void nr_total_eleviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nr_total_eleviActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nr_total_eleviActionPerformed

    private void nr_total_bursieriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nr_total_bursieriMouseClicked

        tabele_rapoarte.setModel(database.nr_total_bursieri());
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton nr_total_bursieri elevi");

        // TODO add your handling code here:
    }//GEN-LAST:event_nr_total_bursieriMouseClicked

    private void nr_total_bursieriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nr_total_bursieriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nr_total_bursieriActionPerformed

    private void nr_total_nebursieriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nr_total_nebursieriMouseClicked
        // TODO add your handling code here:
        
        tabele_rapoarte.setModel(database.nr_total_ne_bursieri());
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton nr_total_nebursieri elevi");
    }//GEN-LAST:event_nr_total_nebursieriMouseClicked

    private void nr_total_nebursieriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nr_total_nebursieriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nr_total_nebursieriActionPerformed

    private void valoare_toatala_burseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valoare_toatala_burseMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_valoare_toatala_burseMouseClicked

    private void valoare_toatala_burseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valoare_toatala_burseActionPerformed


        tabele_rapoarte.setModel(database.total_valoare_burse());
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton valoare_toatala_burse elevi");
        // TODO add your handling code here:
    }//GEN-LAST:event_valoare_toatala_burseActionPerformed

    private void valoare_burse_per_clasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valoare_burse_per_clasaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_valoare_burse_per_clasaMouseClicked

    private void valoare_burse_per_clasaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valoare_burse_per_clasaActionPerformed
        // TODO add your handling code here:
        
       tabele_rapoarte.setModel(database.valoare_burse_per_clasa());
       database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton valoare_burse_per_clasa elevi");
        
    }//GEN-LAST:event_valoare_burse_per_clasaActionPerformed

    private void refresh_burseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh_burseMouseClicked
        // TODO add your handling code here:
        
        tabela_burse.setModel(database.afisare_burse());
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton refresh_burse elevi");
        
        
        
    }//GEN-LAST:event_refresh_burseMouseClicked

    private void valaore_burse_per_specializareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valaore_burse_per_specializareMouseClicked
        // TODO add your handling code here:
        tabele_rapoarte.setModel(database.valoare_burspe_per_specializare());
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton valaore_burse_per_specializare elev");
        
        
    }//GEN-LAST:event_valaore_burse_per_specializareMouseClicked

    private void valaore_burse_per_specializareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valaore_burse_per_specializareActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valaore_burse_per_specializareActionPerformed

    private void valaore_burse_per_profilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valaore_burse_per_profilMouseClicked

    tabele_rapoarte.setModel(database.valoare_burspe_per_profil());
    database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton valoare_burse_per_specializare elev");


        // TODO add your handling code here:
    }//GEN-LAST:event_valaore_burse_per_profilMouseClicked

    private void valaore_burse_per_profilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valaore_burse_per_profilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valaore_burse_per_profilActionPerformed

    private void valaore_bursa_per_tip_bursaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valaore_bursa_per_tip_bursaMouseClicked

    tabele_rapoarte.setModel(database.valoare_burspe_per_tip_bursa());
    database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton valaore_bursa_per_tip_bursa elev");

        // TODO add your handling code here:
    }//GEN-LAST:event_valaore_bursa_per_tip_bursaMouseClicked

    private void valaore_bursa_per_tip_bursaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valaore_bursa_per_tip_bursaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valaore_bursa_per_tip_bursaActionPerformed

    private void valaore_bursa_per_studentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valaore_bursa_per_studentMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_valaore_bursa_per_studentMouseClicked

    private void valaore_bursa_per_studentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valaore_bursa_per_studentActionPerformed


        tabele_rapoarte.setModel(database.valoare_burse_per_student());
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton valaore_bursa_per_student elev");


        // TODO add your handling code here:
    }//GEN-LAST:event_valaore_bursa_per_studentActionPerformed

    private void tip_burse_per_claseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tip_burse_per_claseMouseClicked
        
        tabele_rapoarte.setModel(database.tip_burse_per_clase());
        database.adaugaEveniment(utilizator_actual, "Eveniment: apsare buton tip_burse_per_clase elev");
        // TODO add your handling code here:
    }//GEN-LAST:event_tip_burse_per_claseMouseClicked

    private void tip_burse_per_claseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tip_burse_per_claseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tip_burse_per_claseActionPerformed

    private void refresh_graficeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh_graficeMouseClicked
        
        database.adaugaEveniment(utilizator_actual, "Eveniment: apsare buton refresh_grafice elev");
        
        pie_valoare_burse_tip_bursa.clearData();
        pie_valoare_per_clase.clearData();
        pie_valoare_per_specializare.clearData();
     try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "select clasa.nume as 'Nume Clase', sum(tip_bursa.valoare) as 'Suma per Clasa' from clasa, elev ,bursieri, tip_bursa where bursieri.codbur=tip_bursa.codbur and elev.nr_matricol=bursieri.nr_matricol and elev.codclasa = clasa.codclasa group by clasa.nume";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            int index=0;
            while (result.next()) {
                String clasa = result.getString(1);
                int suma=result.getInt(2);
                pie_valoare_per_clase.addData(new ModelPieChart(clasa, suma, getColor()));
                
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        
         try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "select  tip_bursa.nume as 'Tip Bursa',sum(tip_bursa.valoare) as 'Suma per Categorie' from tip_bursa, bursieri where tip_bursa.codbur=bursieri.codbur group by tip_bursa.nume;";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            int index=0;
            while (result.next()) {
                String clasa = result.getString(1);
                int suma=result.getInt(2);
                pie_valoare_burse_tip_bursa.addData(new ModelPieChart(clasa, suma, getColor()));
                
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
         
         try (Connection conn = DriverManager.getConnection(dbURL, dbusername, dbpassword))
        {
            String sql = "select specializare.nume_sp as 'Nume Specializare', sum(tip_bursa.valoare) as 'Suma per Spercializare' from specializare,clasa, elev ,bursieri, tip_bursa where bursieri.codbur=tip_bursa.codbur and elev.nr_matricol=bursieri.nr_matricol and elev.codclasa = clasa.codclasa and specializare.codsp=clasa.codsp group by specializare.nume_sp";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            int index=0;
            while (result.next()) {
                String clasa = result.getString(1);
                int suma=result.getInt(2);
                pie_valoare_per_specializare.addData(new ModelPieChart(clasa, suma, getColor()));
                
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }


        // TODO add your handling code here:
    }//GEN-LAST:event_refresh_graficeMouseClicked

    private void refresh_graficeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresh_graficeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_refresh_graficeActionPerformed

    private void tab_istoricMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_istoricMouseEntered


        tab_istoric.setModel(database.afisare_istoric());

  // TODO add your handling code here:
    }//GEN-LAST:event_tab_istoricMouseEntered

    private void tab_istoricMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_istoricMouseMoved
        // TODO add your handling code here:
        tab_istoric.setModel(database.afisare_istoric());
        
    }//GEN-LAST:event_tab_istoricMouseMoved

    private void export_to_excel_raportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_export_to_excel_raportMouseClicked
        // TODO add your handling code here:
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton export_excel_button");
        salvare_excel(tabele_rapoarte);
        
        
    }//GEN-LAST:event_export_to_excel_raportMouseClicked

    private void export_to_excel_raportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_export_to_excel_raportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_export_to_excel_raportActionPerformed

    private void export_to_excel_date_extraseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_export_to_excel_date_extraseMouseClicked
    database.adaugaEveniment(utilizator_actual, "Eveniment: apasare export_to_excel_date_extrase");

        salvare_excel(elevi_sec);

        // TODO add your handling code here:
    }//GEN-LAST:event_export_to_excel_date_extraseMouseClicked

    private void export_to_excel_date_extraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_export_to_excel_date_extraseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_export_to_excel_date_extraseActionPerformed

    private void export_to_excel_eleviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_export_to_excel_eleviMouseClicked
    database.adaugaEveniment(utilizator_actual, "Eveniment: export_to_excel_elevi");
        salvare_excel(tabela_elevi);


        // TODO add your handling code here:
    }//GEN-LAST:event_export_to_excel_eleviMouseClicked

    private void export_to_excel_eleviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_export_to_excel_eleviActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_export_to_excel_eleviActionPerformed

    private void expoert_pdf_date_extraseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expoert_pdf_date_extraseMouseClicked
        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare expoert_pdf_date_extrase");



        try {
            elevi_sec.print();
            
            
            
            
            // TODO add your handling code here:
        } catch (PrinterException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_expoert_pdf_date_extraseMouseClicked

    private void expoert_pdf_date_extraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expoert_pdf_date_extraseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expoert_pdf_date_extraseActionPerformed

    private void scroll1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scroll1MouseClicked
        // TODO add your handling code here:
        
        
        
        
        
    }//GEN-LAST:event_scroll1MouseClicked

    private void tabela_eleviMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabela_eleviMouseClicked

        int index=tabela_elevi.getSelectedRow();
        nr_matricol_update.setText(tabela_elevi.getModel().getValueAt(index,0).toString());
        cnp_update.setText(tabela_elevi.getModel().getValueAt(index,1).toString());
        nume_update.setText(tabela_elevi.getModel().getValueAt(index,2).toString());
        prenume_update.setText(tabela_elevi.getModel().getValueAt(index,3).toString());
        medie_update.setText(tabela_elevi.getModel().getValueAt(index,6).toString());
        acteadi_update.setText(tabela_elevi.getModel().getValueAt(index,7).toString());
        codclasa_update.setText(tabela_elevi.getModel().getValueAt(index,8).toString());
        adresa_update.setText(tabela_elevi.getModel().getValueAt(index,5).toString());
        tel_update.setText(tabela_elevi.getModel().getValueAt(index,4).toString());
        
        nr_matricol_sters.setText(tabela_elevi.getModel().getValueAt(index,0).toString());



        // TODO add your handling code here:
    }//GEN-LAST:event_tabela_eleviMouseClicked

    private void import_from_excelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_import_from_excelMouseClicked

        database.adaugaEveniment(utilizator_actual, "Eveniment: apasare buton import_excel");
        import_excel();


        // TODO add your handling code here:
    }//GEN-LAST:event_import_from_excelMouseClicked

    private void import_from_excelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_import_from_excelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_import_from_excelActionPerformed

    private void clasa_filtru1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clasa_filtru1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clasa_filtru1ActionPerformed

    private void all_legat_bursieriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_all_legat_bursieriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_all_legat_bursieriActionPerformed

    private void enableLogin(boolean action) {
        txtUser.setEditable(action);
        txtPass.setEditable(action);
        cmdSignIn.setEnabled(action);
    }

    public void clearLogin() {
        txtUser.setText("");
        txtPass.setText("");
        txtUser.setHelperText("");
        txtPass.setHelperText("");
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Cresc;
    private javax.swing.JTextField acteadi;
    private javax.swing.JTextField acteadi_update;
    private com.raven.swing.Button adauga_elevi;
    private javax.swing.JTextField adresa;
    private javax.swing.JTextField adresa_update;
    private javax.swing.JCheckBox all_legat_bursieri;
    private com.raven.swing.Button aplicare_filtre;
    private com.raven.swing.Background background1;
    private javax.swing.JPanel bursieri;
    private com.raven.swing.Button cautare_elev;
    private javax.swing.JTextField clasa_filtru1;
    private com.raven.swing.Button cmdSignIn;
    private javax.swing.JTextField cnp;
    private javax.swing.JTextField cnp_update;
    private javax.swing.JTextField codclasa;
    private javax.swing.JTextField codclasa_update;
    private javax.swing.JRadioButton desc;
    private javax.swing.JPanel elevi;
    private javax.swing.JTable elevi_sec;
    private com.raven.swing.Button expoert_pdf_date_extrase;
    private com.raven.swing.Button export_to_excel_date_extrase;
    private com.raven.swing.Button export_to_excel_elevi;
    private com.raven.swing.Button export_to_excel_raport;
    private javax.swing.JPanel grafice;
    private javax.swing.JPanel header;
    private com.raven.swing.Button import_from_excel;
    private javax.swing.JPanel istoric;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField medie;
    private javax.swing.JTextField medie_filtru;
    private javax.swing.JTextField medie_update;
    private javax.swing.JTextField nr_matricol;
    private javax.swing.JTextField nr_matricol_elev_cautat;
    private javax.swing.JTextField nr_matricol_sters;
    private javax.swing.JTextField nr_matricol_update;
    private com.raven.swing.Button nr_total_bursieri;
    private com.raven.swing.Button nr_total_elevi;
    private com.raven.swing.Button nr_total_nebursieri;
    private javax.swing.JTextField nume;
    private javax.swing.JTextField nume_update;
    private com.raven.swing.PanelTransparent panelBody;
    private javax.swing.JPanel panelLogin;
    private javaswingdev.chart.PieChart pie_valoare_burse_tip_bursa;
    private javaswingdev.chart.PieChart pie_valoare_per_clase;
    private javaswingdev.chart.PieChart pie_valoare_per_specializare;
    private javax.swing.JTextField prenume;
    private javax.swing.JTextField prenume_update;
    private com.raven.swing.Button refresh_burse;
    private com.raven.swing.Button refresh_bursieri;
    private com.raven.swing.Button refresh_elevi;
    private com.raven.swing.Button refresh_elevi_sec;
    private com.raven.swing.Button refresh_grafice;
    private javax.swing.JScrollPane scroll1;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JScrollPane scroll3;
    private javax.swing.JCheckBox sec_bursieri_but;
    private javax.swing.JPanel secretariat;
    private com.raven.swing.Button sterge_elev;
    private javax.swing.JTable tab_istoric;
    private javax.swing.JTable tabela_burse;
    private javax.swing.JTable tabela_bursieri;
    private javax.swing.JTable tabela_elevi;
    private javax.swing.JTable tabele_rapoarte;
    private javax.swing.JTable tabele_rapoarte1;
    private javax.swing.JTextField tel;
    private javax.swing.JTextField tel_update;
    private com.raven.swing.Button tip_burse_per_clase;
    private javax.swing.ButtonGroup tip_medie;
    private com.raven.swing.PasswordField txtPass;
    private com.raven.swing.TextField txtUser;
    private com.raven.swing.Button update_elev;
    private com.raven.swing.Button valaore_bursa_per_student;
    private com.raven.swing.Button valaore_bursa_per_tip_bursa;
    private com.raven.swing.Button valaore_burse_per_profil;
    private com.raven.swing.Button valaore_burse_per_specializare;
    private com.raven.swing.Button valoare_burse_per_clasa;
    private com.raven.swing.Button valoare_toatala_burse;
    // End of variables declaration//GEN-END:variables
}
