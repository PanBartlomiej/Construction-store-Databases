package com.company;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class MyFrame extends JFrame implements ActionListener {


    Connection c = null;
    ArrayList<JButton> przyciski = new ArrayList<JButton>();
    ArrayList<JPanel> panels = new ArrayList<JPanel>();
    JPanel panelMenu = new JPanel();

    JTextField imie = new JTextField();
    JTextField nazwisko = new JTextField();
    JTextField adres = new JTextField();
    JButton wyslij = new JButton("Dodaj Klienta");
    JButton wyslijDostawce = new JButton("Dodaj Dostawce");
    JTextField szukaj = new JTextField();
    JLabel  szukajLabel = new JLabel("Szukaj Produktu:");
    JButton szukajPrzycisk = new JButton("Szukaj");
    JButton dodajPrzycisk ;
    JComboBox wybierzRodzaj =new JComboBox();
    JTextField ileSztuk;
    JTextField cena;
    JTextField id_dostawy;
    JComboBox magazyn;
    JButton sprzedaj;
    int iloscS =0;  // ilosc produktów o danej nazwie
    int iter=1;  //iterator do insertowania
    String nazwaP = "";//nazwa produktu przy sprzedazy
    JComboBox rodzaj1; // wybór rodzaju produktu przy wypisaniu
    JButton przyciskProdukty;
    JPanel panelTabelka2;
    int flagaListaProduktow;
    JButton zalogowanyPrzycisk;
    JButton loguj;
    JButton wyloguj;
    String loginPoprawny = "Bartek";
    String hasloPoprawny ="123abc";
    JTextField login;
    JPasswordField haslo;
    boolean flagaLogowania = false;
    JComboBox sortuj;
    JComboBox sortuj2;
    MyFrame(){
        try {
            c = DriverManager.getConnection("jdbc:postgresql://abul.db.elephantsql.com:5432/lworthdg","lworthdg","e84EiOBZhRvQ-a0gE19g470LvdUJOjzm");
        } catch (SQLException se) {
            System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
            se.printStackTrace();
            System.exit(1);
        }




        this.setLayout(null);
        this.setSize(800,800);
        this.setLocation(400,20);
        this.setTitle("Sklep Budowlany Bartek");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);



        this.getContentPane().setBackground(new Color(30,80,30));

        JLabel tytul = new JLabel("Manager sklepu");
        tytul.setForeground(new Color(229, 184, 1));
        tytul.setBounds(300,10,300,30);
        tytul.setFont(new Font("Arial",Font.PLAIN,30));
        this.add(tytul);

        przyciski.add(new JButton("Dodaj klienkta"));
        przyciski.get(0).setBounds(200,100,200,20);

        przyciski.add(new JButton("Sprzedaj"));
        przyciski.get(1).setBounds(400,100,200,20);

        przyciski.add(new JButton("Dodaj Dostawcę"));
        przyciski.get(2).setBounds(200,121,200,20);

        przyciski.add(new JButton("Pokaż Dostawców"));
        przyciski.get(3).setBounds(400,121,200,20);

        przyciski.add(new JButton("Dodaj Produkt"));
        przyciski.get(4).setBounds(200,143,200,20);

        przyciski.add(new JButton("Pokaż Klientów"));
        przyciski.get(5).setBounds(400,143,200,20);

        przyciski.add(new JButton("Lista Produktów"));
        przyciski.get(6).setBounds(200,165,200,20);

        zalogowanyPrzycisk = new JButton("zaloguj");
        przyciski.add(zalogowanyPrzycisk);
        przyciski.get(7).setBounds(650,10,100,30);

        wyloguj = new JButton("wyloguj");
        wyloguj.setBounds(650,42,100,30);
        wyloguj.addActionListener(this);
        this.add(wyloguj);



        przyciski.add(new JButton("Dodaj Magazyn"));
        przyciski.get(8).setBounds(400,165,200,20);
        przyciski.get(8).setVisible(false);

        przyciski.add(new JButton("Nowa Dostawa"));
        przyciski.get(9).setBounds(200,187,200,20);
        przyciski.get(9).setVisible(false);

        for(JButton button : przyciski)
        {
            button.addActionListener(this);
            button.setFocusable(false);
            this.add(button);
        }
        for(int i=0; i<przyciski.size(); i++)
        {
            panels.add(new JPanel());
            this.add(panels.get(i));
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        // nazwy kolumn
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        // zawartość tabeli
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    /**
     * obsługa przycisków menu
     *
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==przyciski.get(7))
        {
            for(JPanel panel: panels)
                panel.setVisible(false);

            panels.get(7).removeAll();
            panels.get(7).setLayout(null);
            panels.get(7).setVisible(true);
            panels.get(7).setBounds(200,250,400,100);

            JLabel loginText =  new JLabel("podaj login");
            JLabel hasloText = new JLabel("podaj haslo");

            haslo = new JPasswordField();
            login = new JTextField();
            loguj = new JButton("loguj");
            loginText.setBounds(10,10,100,20);
            hasloText.setBounds(10,40,100,20);
            login.setBounds(100,10,100,20);
            haslo.setBounds(100,40,100,20);
            loguj.setBounds(240,40,100,20);


            panels.get(7).add(loginText);
            panels.get(7).add(login);
            panels.get(7).add(hasloText);
            panels.get(7).add(haslo);
            panels.get(7).add(loguj);
            loguj.addActionListener(this);
        }

        if(e.getSource()==loguj){

            if(login.getText().equals(loginPoprawny) && haslo.getText().equals(hasloPoprawny))
            {flagaLogowania = true;
                JOptionPane.showMessageDialog(this,"Zalogowano poprawnie");
                przyciski.get(8).setVisible(true);
                przyciski.get(9).setVisible(true);}
            else {flagaLogowania = false;
                JOptionPane.showMessageDialog(this,"Błędne  dane logowania");}

            this.setVisible(true);
        }
        if(e.getSource() == wyloguj)
        {
            if(flagaLogowania==true){
                JOptionPane.showMessageDialog(this,"Wylogowałeś się");}
            flagaLogowania=false;
            przyciski.get(8).setVisible(false);
            przyciski.get(9).setVisible(false);
        }
        if(e.getSource()==przyciski.get(0))
        {
            /**
             * obusłga przycisku Dodaj klienta
             */
            for(JPanel panel: panels)
                panel.setVisible(false);
            panels.get(0).removeAll();
            panels.get(0).setVisible(true);
            panels.get(0).setBounds(100,250,600,300);

            JLabel imieLabel = new JLabel("Podaj Imie:");
            JLabel nazwskoLabel = new JLabel("Podaj Nazwsko:");
            JLabel adresLabel = new JLabel("Podaj Adres");

            imieLabel.setBounds(10,20,100,20);
            nazwskoLabel.setBounds(10,40,100,20);
            adresLabel.setBounds(10,60,100,20);

            panels.get(0).add(imieLabel);
            panels.get(0).add(nazwskoLabel);
            panels.get(0).add(adresLabel);

            imie.setBackground(new Color(220,220,220));
            imie.setPreferredSize(new Dimension(200,20));
            imie.addActionListener(this);
            imie.setBounds(120,20,200,20);

            nazwisko.setBackground(new Color(220,220,220));
            nazwisko.setPreferredSize(new Dimension(200,20));
            nazwisko.addActionListener(this);
            nazwisko.setBounds(120,40,200,20);

            adres.setBackground(new Color(220,220,220));
            adres.setPreferredSize(new Dimension(200,20));
            adres.addActionListener(this);
            adres.setBounds(120,60,200,20);

            panels.get(0).add(imie);
            panels.get(0).add(nazwisko);
            panels.get(0).add(adres);


            wyslij.addActionListener(this);
            wyslij.setBounds(400,40,120,40);


            panels.get(0).add(wyslij);

            panels.get(0).setVisible(true);
            panels.get(0).setLayout(null);
        }
        if(e.getSource()==wyslij)
        {
            /**
             * Obusłga przycisku wyślij w polu dodawania klientów
             * Dodawanie klienta do bazy Danych do tabeli klienci
             */
            if(imie.getText().equals("") ||nazwisko.getText().equals("") || adres.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Podaj prawidłowe dane");
            }
            else
            if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst1 =c.prepareStatement("SELECT MAX(id_klient) AS id FROM projekt.klienci", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs1 = pst1.executeQuery();
                        if(rs1.next())
                                System.out.println("OK");
                    iter= Integer.parseInt(rs1.getString("id"));
                    rs1.close();
                    pst1.close();
                    iter +=1;
                    PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.klienci (id_klient,imie,nazwisko,adres) VALUES (?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    pst.setInt(1,iter);
                    System.out.println(imie.getText());
                    pst.setString(2,imie.getText());
                    pst.setString(3,nazwisko.getText());
                    pst.setString(4,adres.getText());
                    pst.executeQuery();

                    imie.setText("");
                    nazwisko.setText("");
                    adres.setText("");

                    pst.close();    }
                catch(SQLException e1)  {
                    System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
            }
        }
        if(e.getSource()==przyciski.get(1)) {

            for(JPanel panel: panels)
                panel.setVisible(false);
            szukaj.setText("");
            panels.get(1).removeAll();
            panels.get(1).setVisible(true);
            panels.get(1).setBounds(100,250,600,300);

            szukaj.setPreferredSize(new Dimension(200,20));
            szukaj.setBounds(120,20,200,20);
            szukajLabel.setText("Nazwa Produkt");

            szukajLabel.setBounds(10,20,100,20);
            panels.get(1).add(szukaj);
            panels.get(1).add(szukajLabel);

            szukajPrzycisk.setBounds(440,70,120,40);
            szukajPrzycisk.addActionListener(this);

            String[] opcje  ={"Chemia","Elektronika","Narzędzia"};
            wybierzRodzaj = new JComboBox(opcje);
            wybierzRodzaj.setBounds(120,50,95,20);

            JLabel wybierzRodzajLabel  =  new JLabel("Rodzaj produktu");
            wybierzRodzajLabel.setBounds(10,50,100,20);
            JLabel magazynLabel = new JLabel("wybierz magazyn");
            Array tempoMagazyny =null;
            magazyn=new JComboBox(new String[]{"1", "2","3"});
            if (c != null) {
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT id_magazynu FROM projekt.Magazyny", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = pst.executeQuery();

                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    Vector<Object> data = new Vector<Object>();
                    while (rs.next()) {
                        data.add(rs.getObject("id_magazynu"));
                    }
                    magazyn = new JComboBox(data);
                    rs.close();
                    pst.close();

                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }

            }

            magazynLabel.setBounds(10,80,100,20);
            magazyn.setBounds(120,80,100,20);
            panels.get(1).add(magazyn);
            panels.get(1).add(magazynLabel);
            panels.get(1).add(wybierzRodzajLabel);

            panels.get(1).add(szukaj);
            panels.get(1).add(szukajLabel);
            panels.get(1).add(wybierzRodzaj);
            panels.get(1).add(szukajPrzycisk);
            panels.get(1).setVisible(true);
            panels.get(1).setLayout(null);

            panels.get(1).setPreferredSize(new Dimension(500, 500));
        }
        if(e.getSource()==szukajPrzycisk){
            /**
             * dodanie produktów do bazy danych
             *
             */

            panels.get(1).setVisible(false);
            if(szukaj.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Podaj prawidłowe dane");
            }

            else if (c != null) {

                try {
                    PreparedStatement pst1 =null;
                    if(wybierzRodzaj.getSelectedItem().equals("Chemia")) {
                        pst1 =c.prepareStatement("SELECT *  FROM projekt.Chemia", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    }
                    else if(wybierzRodzaj.getSelectedItem().equals("Elektronika")){
                        pst1= c.prepareStatement("SELECT *  FROM projekt.Elektronika", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    }
                    else if(wybierzRodzaj.getSelectedItem().equals("Narzędzia")){
                        pst1 = c.prepareStatement("SELECT *  FROM projekt.Narzdzia", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    }
                    ResultSet rs1 = pst1.executeQuery();


                    JTable tabelka = new JTable(buildTableModel(rs1));

                    rs1.beforeFirst();
                    if(rs1.next())   System.out.println("OK");
                    else  JOptionPane.showMessageDialog(this,"NIE ZNALEZIONO PRODUKTU");
                    iloscS = rs1.getInt("ilosc");
                    nazwaP = rs1.getString("nazwa");


                    tabelka.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

                    JTableHeader h1 = tabelka.getTableHeader();

                    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
                    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
                    JScrollPane scroller = new javax.swing.JScrollPane(tabelka, v, h);


                    JPanel tabelkaPanle = new JPanel();
                    tabelkaPanle.setOpaque(true);
                    tabelkaPanle.setLayout(new BorderLayout());
                    tabelkaPanle.setPreferredSize(new Dimension(600, 60));


                    tabelkaPanle.setBounds(0,130,600,60);
                    JLabel text = new JLabel("Znaleziono:");
                    text.setBounds(20,110,200,20);
                    tabelkaPanle.add(scroller, BorderLayout.CENTER);
                    tabelkaPanle.setVisible(true);

                    panels.get(1).add(tabelkaPanle);
                    panels.get(1).add(text);

                    ileSztuk= new JTextField();
                    ileSztuk.setBounds(120,200,40,20);
                    cena = new JTextField();
                    cena.setBounds(120,230,40,20);
                    JLabel iloscLabel = new JLabel("ilość");
                    JLabel cenaLabel = new JLabel("Cena");
                    iloscLabel.setBounds(10,200,100,20);
                    cenaLabel.setBounds(10,230,100,20);

                    panels.get(1).add(cenaLabel);
                    panels.get(1).add(iloscLabel);
                    //panels.get(1).setBackground(new Color(30,80,30));
                    panels.get(1).add(cena);
                    panels.get(1).add(ileSztuk);


                    sprzedaj = new JButton("Sprzedaj");
                    sprzedaj.setBounds(400,260,120,40);
                    sprzedaj.addActionListener(this);
                    panels.get(1).add(sprzedaj);
                    rs1.close();
                    pst1.close();
                    panels.get(1).setVisible(true);
                }
                catch(SQLException e1)  {
                    System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
            }

        }
        if(e.getSource()==sprzedaj)
        {

            System.out.println("Przycisk Działa");
             if (c != null) {
            try {
                PreparedStatement pst = null;
                PreparedStatement pst2 = null;
                System.out.println("iloscS = " +iloscS);
                if(wybierzRodzaj.getSelectedItem().equals("Chemia")) {
                    if(iloscS==0)
                        pst = c.prepareStatement("DELETE FROM projekt.Chemia WHERE nazwa= ' ? '", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    else
                        pst2 = c.prepareStatement("UPDATE  projekt.Chemia SET ilosc= ? WHERE nazwa=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                }
                else if(wybierzRodzaj.getSelectedItem().equals("Elektronika")){
                    if(iloscS==0)
                        pst = c.prepareStatement("DELETE FROM projekt.Elektronika WHERE nazwa= ' ? '", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    else
                        pst2 = c.prepareStatement("UPDATE  projekt.Elektronika SET ilosc=? WHERE nazwa=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                }
                else if(wybierzRodzaj.getSelectedItem().equals("Narzędzia")){
                    if(iloscS==0)
                        pst = c.prepareStatement("DELETE FROM projekt.Narzdzia WHERE nazwa= ?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    else
                        pst2 = c.prepareStatement("UPDATE  projekt.Narzdzia SET ilosc= ? WHERE nazwa=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                }

                if(pst != null)    {
                    pst.setString(1, szukaj.getText());
                    pst.executeQuery();}
               if(pst2 != null) {
                   if (iloscS - Integer.parseInt(ileSztuk.getText()) < 0 || Integer.parseInt(ileSztuk.getText()) < 0)
                       JOptionPane.showMessageDialog(this, "Podaj poprawną ilość");
                   else
                       pst2.setInt(1, iloscS - Integer.parseInt(ileSztuk.getText()));

                   pst2.setString(2, nazwaP);
                   pst2.executeQuery();
               }
                szukaj.setText("");
                ileSztuk.setText("");
                cena.setText("");
                pst.close();
                pst2.close();

                JOptionPane.showMessageDialog(this, "Poprawnie Sprzedano");
                panels.get(1).setVisible(false);
            }
            catch(SQLException e1)  {
                System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
        }
        }
        if(e.getSource()==przyciski.get(2))
        {
            /**
             * obusłga przycisku Dodaj klienta
             */
            for(JPanel panel: panels)
                panel.setVisible(false);
            panels.get(2).removeAll();
            panels.get(2).setVisible(true);
            panels.get(2).setBounds(100,250,600,300);

            JLabel imieLabel = new JLabel("Podaj NIP:");
            JLabel nazwskoLabel = new JLabel("Podaj Nazwę:");
            JLabel adresLabel = new JLabel("Podaj Adres");

            imieLabel.setBounds(10,20,100,20);
            nazwskoLabel.setBounds(10,40,100,20);
            adresLabel.setBounds(10,60,100,20);

            panels.get(2).add(imieLabel);
            panels.get(2).add(nazwskoLabel);
            panels.get(2).add(adresLabel);

            imie.setBackground(new Color(220,220,220));
            imie.setPreferredSize(new Dimension(200,20));
            imie.addActionListener(this);
            imie.setBounds(120,20,200,20);

            nazwisko.setBackground(new Color(220,220,220));
            nazwisko.setPreferredSize(new Dimension(200,20));
            nazwisko.addActionListener(this);
            nazwisko.setBounds(120,40,200,20);

            adres.setBackground(new Color(220,220,220));
            adres.setPreferredSize(new Dimension(200,20));
            adres.addActionListener(this);
            adres.setBounds(120,60,200,20);

            panels.get(2).add(imie);
            panels.get(2).add(nazwisko);
            panels.get(2).add(adres);


            wyslijDostawce.addActionListener(this);
            wyslijDostawce.setBounds(400,40,130,40);

            panels.get(2).add(wyslijDostawce);

            panels.get(2).setVisible(true);
            panels.get(2).setLayout(null);
        }
        if(e.getSource()==wyslijDostawce){
            /**
             * dodanie dostawcy do bazy danych
             *
             */

            if(imie.getText().equals("") ||nazwisko.getText().equals("") || adres.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Podaj prawidłowe dane");
            }
            else if(!isNumeric(imie.getText())){JOptionPane.showMessageDialog(this,"NIP musi być liczbą całkowitą"); }
            else if (c != null) {

                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst1 =c.prepareStatement("SELECT COUNT(*) AS id FROM projekt.Dostawcy", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs1 = pst1.executeQuery();
                    if(rs1.next())
                        System.out.println("OK");
                    iter= Integer.parseInt(rs1.getString("id"));
                    rs1.close();
                    pst1.close();
                    iter +=1;
                    PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.Dostawcy (NIP,nazwa,adres) VALUES (?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                    pst.setInt(1,Integer.parseInt(imie.getText()));
                    pst.setString(2,nazwisko.getText());
                    pst.setString(3,adres.getText());
                    pst.executeQuery();

                    imie.setText("");
                    nazwisko.setText("");
                    adres.setText("");

                    pst.close();    }
                catch(SQLException e1)  {
                    System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
            }
        }

        if(e.getSource()==przyciski.get(3)) {
            /**
             * wypisanie wszystkich dostawców
             */
            for(JPanel panel: panels)
                panel.setVisible(false);

            panels.get(3).removeAll();
            panels.get(3).setVisible(true);
            panels.get(3).setBounds(100,250,600,300);
            panels.get(3).setBackground(new Color(30,80,30));
            JLabel text = new JLabel("Dostawcy:");
            text.setForeground(new Color(229, 184, 1));
            text.setBounds(20,0,200,20);
            String[] sortujTempo = {"NIP","nazwa","adres"};
            sortuj = new JComboBox(sortujTempo);
            sortuj.addActionListener(this);
            panels.get(3).add(sortuj);
            panels.get(3).add(text);

            panelTabelka2 = new JPanel();
            panelTabelka2.removeAll();
            panels.get(3).add(panelTabelka2);
            if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT NIP,nazwa,adres FROM projekt.Dostawcy ORDER BY  "+sortuj.getSelectedItem(), ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

                    ResultSet rs ;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    tabelka.addNotify();
                    JTableHeader h1 = tabelka.getTableHeader();

                    h1.setBounds(0,20,600,20);
                    tabelka.setBounds(0,40,600,300);
                    tabelka.setAutoResizeMode(1);
                    tabelka.setEnabled(false);
                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(198);

                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    JScrollPane scroller = new JScrollPane(tabelka,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller);
                    panelTabelka2.setVisible(true);

                    panels.get(3).setVisible(true);

                    //panels.get(3).setLayout(null);
                    rs.close();
                    pst.close();    }
                catch(SQLException e1)  {
                    System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
            }

    }
        if(e.getSource()==sortuj)
        {
            panels.get(3).setVisible(false);
            panelTabelka2.removeAll();
            if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT NIP,nazwa,adres FROM projekt.Dostawcy ORDER BY  "+sortuj.getSelectedItem(), ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

                    ResultSet rs ;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    tabelka.addNotify();
                    JTableHeader h1 = tabelka.getTableHeader();

                    h1.setBounds(0,20,600,20);
                    tabelka.setBounds(0,40,600,300);
                    tabelka.setAutoResizeMode(1);
                    tabelka.setEnabled(false);
                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(198);

                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    JScrollPane scroller = new JScrollPane(tabelka,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller);
                    panelTabelka2.setVisible(true);
                    panels.get(3).setVisible(true);
                    rs.close();
                    pst.close();    }
                catch(SQLException e1)  {
                    System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
            }
        }
            if(e.getSource()==przyciski.get(4)) {

                for(JPanel panel: panels)
                    panel.setVisible(false);
                szukaj.setText("");

                panels.get(4).removeAll();
                panels.get(4).setVisible(true);
                panels.get(4).setBounds(100,250,600,300);

                szukaj.setPreferredSize(new Dimension(200,20));
                szukaj.setBounds(120,20,200,20);
                szukajLabel.setText("Nazwa Produkt");

                szukajLabel.setBounds(10,20,100,20);
                panels.get(4).add(szukaj);
                panels.get(4).add(szukajLabel);

                dodajPrzycisk = new JButton("Dodaj Produkt");
                dodajPrzycisk.setBounds(440,130,120,40);
                dodajPrzycisk.addActionListener(this);

                String[] opcje  ={"Chemia","Elektronika","Narzędzia"};
                wybierzRodzaj = new JComboBox(opcje);
                wybierzRodzaj.setBounds(120,120,95,20);
                ileSztuk= new JTextField();
                ileSztuk.setBounds(120,50,40,20);
                cena = new JTextField();
                cena.setBounds(120,80,40,20);
                JLabel iloscLabel = new JLabel("ilość");
                JLabel cenaLabel = new JLabel("Cena");
                iloscLabel.setBounds(10,50,100,20);
                cenaLabel.setBounds(10,80,100,20);
                JLabel wybierzRodzajLabel  =  new JLabel("Rodzaj produktu");
                wybierzRodzajLabel.setBounds(10,120,100,20);
                JLabel magazynLabel = new JLabel("wybierz magazyn");
                Array tempoMagazyny =null;
                JTable tab=null;
                magazyn=new JComboBox();
                if (c != null) {
                    try {
                        PreparedStatement pst = c.prepareStatement("SELECT id_magazynu FROM projekt.Magazyny", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = pst.executeQuery();

                        ResultSetMetaData metaData = rs.getMetaData();
                        int columnCount = metaData.getColumnCount();
                        Vector<Object> data = new Vector<Object>();
                        while (rs.next()) {
                            data.add(rs.getObject("id_magazynu"));
                        }
                        magazyn = new JComboBox(data);

                    } catch (SQLException e1) {
                        System.out.println("Blad podczas przetwarzania danych:" + e1);
                    }

                }

                magazynLabel.setBounds(10,150,100,20);
                magazyn.setBounds(120,150,100,20);
                id_dostawy = new JTextField();
                id_dostawy.setBounds(120,180,100,20);
                JLabel id_dostawyLabel = new JLabel("numer dostawy");
                id_dostawyLabel.setBounds(10,180,100,20);

                panels.get(4).add(id_dostawyLabel);
                panels.get(4).add(id_dostawy);
                panels.get(4).add(magazyn);
                panels.get(4).add(magazynLabel);
                panels.get(4).add(wybierzRodzajLabel);
                panels.get(4).add(cenaLabel);
                panels.get(4).add(iloscLabel);
                panels.get(4).add(cena);
                panels.get(4).add(ileSztuk);
                panels.get(4).add(wybierzRodzaj);
                panels.get(4).add(dodajPrzycisk);
                panels.get(4).setVisible(true);
                panels.get(4).setLayout(null);
        }
        if(e.getSource()==dodajPrzycisk){
            /**
             * dodanie produktów do bazy danych
             *
             */

            if(szukaj.getText().equals("") || ileSztuk.getText().equals("") || cena.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Podaj prawidłowe dane");
            }

            else if (c != null) {

                try {
                    PreparedStatement pst1 =null;
                    PreparedStatement pst = null;
                    if(wybierzRodzaj.getSelectedItem().equals("Chemia")) {
                        pst1 =c.prepareStatement("SELECT COUNT(*) AS id FROM projekt.Chemia", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                        pst = c.prepareStatement("INSERT INTO projekt.Chemia (nazwa,id_dostawy,ilosc,cena,id_magazynu,id_chemia) VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    }
                    else if(wybierzRodzaj.getSelectedItem().equals("Elektronika")){
                       pst1= c.prepareStatement("SELECT COUNT(*) AS id FROM projekt.Elektronika", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                        pst = c.prepareStatement("INSERT INTO projekt.Elektronika (nazwa,id_dostawy,ilosc,id_magazynu,id_elektronika) VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    }
                    else if(wybierzRodzaj.getSelectedItem().equals("Narzędzia")){
                        pst1 = c.prepareStatement("SELECT COUNT(*) AS id FROM projekt.Narzdzia", ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                        pst = c.prepareStatement("INSERT INTO projekt.Narzdzia (nazwa,id_dostawy,ilosc,cena,id_magazynu,id_narz) VALUES (?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    }
                    ResultSet rs1 = pst1.executeQuery();
                    if(rs1.next())
                        System.out.println("OK");
                    iter= Integer.parseInt(rs1.getString("id"));

                    iter +=1;

                    pst.setString(1, szukaj.getText());
                    pst.setInt(2,Integer.parseInt(id_dostawy.getText()));
                    pst.setInt(3, Integer.parseInt(ileSztuk.getText()));
                    pst.setDouble(4, Double.parseDouble(cena.getText()));
                    pst.setInt(5,Integer.parseInt(magazyn.getSelectedItem().toString()));
                    pst.setInt(6,iter);
                    pst.executeQuery();
                    szukaj.setText("");
                    ileSztuk.setText("");
                    cena.setText("");
                    pst.close();
                    rs1.close();
                    pst1.close();

                    dodajPrzycisk.removeActionListener(this);
                    dodajPrzycisk.removeAll();
                }
                catch(SQLException e1)  {
                    System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
            }
        }

            if(e.getSource()==przyciski.get(5)) {
            /**
             * wypisanie wszystkich klientów
             */
            for(JPanel panel: panels)
                panel.setVisible(false);

            panels.get(5).removeAll();
            panels.get(5).setVisible(true);
            panels.get(5).setBounds(100,250,600,300);

                panels.get(5).setBackground(new Color(30,80,30));

            String[] sortujTempo = {"id_klient","imie","nazwisko","adres"};
            sortuj2 = new JComboBox(sortujTempo);
            sortuj2.addActionListener(this);
                JLabel text = new JLabel("Klienci:");
                text.setForeground(new Color(229, 184, 1));
                text.setBounds(20,0,200,20);

            panels.get(5).add(sortuj2);
            panels.get(5).add(text);

            panelTabelka2 = new JPanel();
            panelTabelka2.removeAll();
            panels.get(5).add(panelTabelka2);
            if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT id_klient,imie,nazwisko,adres FROM projekt.klienci ORDER BY  "+sortuj2.getSelectedItem(), ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

                    ResultSet rs ;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    tabelka.addNotify();
                    JTableHeader h1 = tabelka.getTableHeader();

                    h1.setBounds(0,20,600,20);
                    tabelka.setBounds(0,40,600,300);
                    tabelka.setAutoResizeMode(1);
                    tabelka.setEnabled(false);
                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(198);

                    panelTabelka2.setLayout(new BorderLayout());

                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    JScrollPane scroller = new JScrollPane(tabelka,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller);
                    panelTabelka2.setVisible(true);

                    rs.close();
                    pst.close();    }
                catch(SQLException e1)  {
                    System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
            }

        }
            if(e.getSource()==sortuj2)
            {

                panels.get(5).setVisible(false);
                panelTabelka2.removeAll();
                if (c != null) {
                    System.out.println("Polaczenie z baza danych OK ! ");
                    try {
                        PreparedStatement pst = c.prepareStatement("SELECT id_klient,imie,nazwisko,adres FROM projekt.klienci ORDER BY "+sortuj2.getSelectedItem(), ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

                        ResultSet rs ;
                        rs = pst.executeQuery();
                        JTable tabelka = new JTable(buildTableModel(rs));
                        tabelka.addNotify();
                        JTableHeader h1 = tabelka.getTableHeader();

                        h1.setBounds(0,20,600,20);
                        tabelka.setBounds(0,40,600,300);
                        tabelka.setAutoResizeMode(1);
                        tabelka.setEnabled(false);
                        tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                        tabelka.getColumnModel().getColumn(0).setPreferredWidth(198);
                        tabelka.getColumnModel().getColumn(1).setPreferredWidth(198);
                        tabelka.getColumnModel().getColumn(2).setPreferredWidth(198);

                        panelTabelka2.setLayout(new BorderLayout());
                        panelTabelka2.setPreferredSize(new Dimension(600, 400));
                        JScrollPane scroller = new JScrollPane(tabelka,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        panelTabelka2.add(scroller);
                        panelTabelka2.setVisible(true);

                        panels.get(5).setVisible(true);
                        rs.close();
                        pst.close();    }
                    catch(SQLException e1)  {
                        System.out.println("Blad podczas przetwarzania danych:"+e1) ;   }
                }
            }
        if(e.getSource()==przyciski.get(6) ) {
            /**
             * wypisanie wszystkich produktów
             */
            for(JPanel panel: panels)
                panel.setVisible(false);

            panels.get(6).removeAll();
            panels.get(6).setVisible(true);
            panels.get(6).setBounds(100,250,600,500);

            String []rodzaj1S = {"Narzędzia","Chemia","Elektronika"};
            rodzaj1 = new JComboBox(rodzaj1S);

            panels.get(6).add(rodzaj1);
            rodzaj1.setEditable(false);
           // rodzaj1.addActionListener(this);
            przyciskProdukty = new JButton("POKAŻ");
            przyciskProdukty.addActionListener(this);

             panelTabelka2 = new JPanel();

            panels.get(6).add(panelTabelka2);
            panels.get(6).add(przyciskProdukty);

            JLabel text = new JLabel("Lista Produktów:");
            text.setForeground(new Color(229, 184, 1));
            text.setBounds(20, 0, 200, 20);

            panels.get(6).add(text);
            panels.get(6).setBackground(new Color(30, 80, 30));

        }

        if(e.getSource()==przyciskProdukty) {

            panels.get(6).setVisible(false);
            panels.get(6).remove(panelTabelka2);
            if (c != null) {
                try {
                    PreparedStatement pst ;
                    if (rodzaj1.getSelectedItem().equals("Chemia"))
                        pst = c.prepareStatement("SELECT nazwa,ilosc,cena,id_chemia FROM projekt.Chemia ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    else if (rodzaj1.getSelectedItem().equals("Elektronika"))
                        pst = c.prepareStatement("SELECT nazwa,ilosc,cena,id_elektronika FROM projekt.Elektronika ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    else  pst = c.prepareStatement("SELECT nazwa,ilosc,cena,id_magazynu FROM projekt.Narzdzia ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rs;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    JTableHeader h1 = tabelka.getTableHeader();
                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(150);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(150);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(150);
                    tabelka.getColumnModel().getColumn(3).setPreferredWidth(150);
                    panelTabelka2.removeAll();
                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    JScrollPane scroller = new JScrollPane(tabelka, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller);
                    panelTabelka2.setVisible(true);


                    panels.get(6).add(panelTabelka2, BorderLayout.CENTER);
                    panels.get(6).setVisible(true);

                    //panels.get(6).setLayout(null);
                    rs.close();
                    pst.close();
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }
        }
        if(e.getSource()==przyciski.get(8) && flagaLogowania==true)
        {

            for(JPanel panel: panels)
                panel.setVisible(false);

            panels.get(8).removeAll();
            panels.get(8).setVisible(true);
            panels.get(8).setBounds(100,250,600,300);

        }
        if(e.getSource()==przyciski.get(9) && flagaLogowania==true)
        {

            for(JPanel panel: panels)
                panel.setVisible(false);

            panels.get(9).removeAll();
            panels.get(9).setVisible(true);
            panels.get(9).setBounds(100,250,600,300);

        }
    }
}
