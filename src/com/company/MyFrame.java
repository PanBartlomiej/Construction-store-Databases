package com.company;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

/**
 * @author Bartłomiej Leśnicki
 * @version 1.4
 * Klasa MyFrame jest główną klasą aplikacji
 * Tworzy i zarządza Ramkę opartą o JFrame
 * Obsułguje Wszystkie przyciski oraz Baze danych
 *
 *
 */

public class MyFrame extends JFrame implements ActionListener {


    Connection c = null;
    ArrayList<JButton> przyciski = new ArrayList<JButton>();
    ArrayList<JPanel> panels = new ArrayList<JPanel>();
    JPanel panelMenu = new JPanel();

    JTextField imie = new JTextField();
    JTextField nazwisko = new JTextField();
    JTextField adres = new JTextField();
    JButton wyslij = new JButton("Dodaj Klienta");
    JButton wyslijDostawa = new JButton("Dodaj");
    JButton wyslijDostawce = new JButton("Dodaj Dostawce");
    JButton wyslijMagazyn = new JButton("Dodaj Magazyn");
    JTextField szukaj = new JTextField();
    JLabel szukajLabel = new JLabel("Szukaj Produktu:");
    JButton szukajPrzycisk = new JButton("Szukaj");
    JButton dodajPrzycisk;
    JComboBox wybierzRodzaj = new JComboBox();
    JTextField ileSztuk;
    JTextField cena;
    JTextField id_dostawy;
    JComboBox magazyn;
    JButton sprzedaj;
    int iloscS = 0;  // ilosc produktów o danej nazwie
    int iter = 1;  //iterator do insertowania
    String nazwaP = "";//nazwa produktu przy sprzedazy
    JComboBox rodzaj1; // wybór rodzaju produktu przy wypisaniu
    JButton przyciskProdukty;
    JPanel panelTabelka2;
    int flagaListaProduktow;
    JButton zalogowanyPrzycisk;
    JButton loguj;
    JButton wyloguj;
    JButton zapytanie = new JButton("Potwierdź");
    ArrayList<String> loginy;
    ArrayList<String> hasla;
    JTextField login;
    JPasswordField haslo;
    boolean flagaLogowania = false;
    JComboBox sortuj;
    JComboBox sortuj2;
    JTextField id;
    JTextField klient;
    String tyle_sprzed="0";

    /**
     * Konstuktor
     * Definiuje część zmiennych
     * Ustawia połączenie z bazą danych
     * Definiuje wielkość ramki i większość przycisków
     * w aplikacji
     * Ustawia wygląd aplikacji
     */
    MyFrame() {
        try {
            c = DriverManager.getConnection("jdbc:postgresql://castor.db.elephantsql.com:5432/yejsgmdp", "yejsgmdp", "dfP6IuQT1hpbfUpS6P_-2jx3lwH5Z7_T");
        } catch (SQLException se) {
            System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
            se.printStackTrace();
            System.exit(1);
        }


        this.setLayout(null);
        this.setSize(800, 800);
        this.setLocation(400, 20);
        this.setTitle("Sklep Budowlany Bartek");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        this.setVisible(true);


        this.getContentPane().setBackground(new Color(30, 80, 30));

        JLabel tytul = new JLabel("Manager sklepu");
        tytul.setForeground(new Color(229, 184, 1));
        tytul.setBounds(300, 10, 300, 30);
        tytul.setFont(new Font("Arial", Font.PLAIN, 30));
        this.add(tytul);

        przyciski.add(new JButton("Dodaj klienkta"));
        przyciski.get(0).setBounds(200, 100, 200, 20);

        przyciski.add(new JButton("Sprzedaj"));
        przyciski.get(1).setBounds(400, 100, 200, 20);

        przyciski.add(new JButton("Dodaj Dostawcę"));
        przyciski.get(2).setBounds(200, 121, 200, 20);

        przyciski.add(new JButton("Pokaż Dostawców"));
        przyciski.get(3).setBounds(400, 121, 200, 20);

        przyciski.add(new JButton("Dodaj Produkt"));
        przyciski.get(4).setBounds(200, 143, 200, 20);

        przyciski.add(new JButton("Pokaż Klientów"));
        przyciski.get(5).setBounds(400, 143, 200, 20);

        przyciski.add(new JButton("Lista Produktów"));
        przyciski.get(6).setBounds(200, 165, 200, 20);

        zalogowanyPrzycisk = new JButton("zaloguj");
        przyciski.add(zalogowanyPrzycisk);
        przyciski.get(7).setBounds(650, 10, 100, 30);

        wyloguj = new JButton("wyloguj");
        wyloguj.setBounds(650, 42, 100, 30);
        wyloguj.addActionListener(this);
        wyloguj.setVisible(false);
        this.add(wyloguj);


        przyciski.add(new JButton("Dodaj Magazyn"));
        przyciski.get(8).setBounds(400, 165, 200, 20);
        przyciski.get(8).setVisible(false);

        przyciski.add(new JButton("Nowa Dostawa"));
        przyciski.get(9).setBounds(200, 187, 200, 20);
        przyciski.get(9).setVisible(false);

        przyciski.add(new JButton("Zapytanie SQL"));
        przyciski.get(10).setBounds(400,187,200,20);
        przyciski.get(10).setVisible(false);

        for (JButton button : przyciski) {
            button.addActionListener(this);
            button.setFocusable(false);
            this.add(button);
        }
        for (int i = 0; i < przyciski.size(); i++) {
            panels.add(new JPanel());
            this.add(panels.get(i));
        }
        wyslij.addActionListener(this);
        wyslij.setBounds(400, 40, 120, 40);

        wyslijDostawce.addActionListener(this);
        wyslijDostawce.setBounds(400, 40, 130, 40);

        wyslijMagazyn.addActionListener(this);
        wyslijMagazyn.setBounds(400, 40, 120, 40);

        wyslijDostawa.addActionListener(this);
        wyslijDostawa.setBounds(400, 40, 120, 40);

        zapytanie.addActionListener(this);
        zapytanie.setBounds(400, 75, 120, 40);

        sprzedaj = new JButton("Sprzedaj");
        sprzedaj.setBounds(400, 260, 120, 40);
        sprzedaj.addActionListener(this);

        szukajPrzycisk.setBounds(440, 70, 120, 40);
        szukajPrzycisk.addActionListener(this);

        //stworzenie tabeli tymczasowej
        if (c != null) {
            System.out.println("Polaczenie z baza danych OK ! ");
            try {
                PreparedStatement pst1 = c.prepareStatement("CREATE TEMPORARY TABLE projekt1_tempo(ile INT);");
                pst1.executeQuery();
                pst1.close();

            } catch (SQLException e1) {
                System.out.println("Blad podczas przetwarzania danych:" + e1);
            }

        }
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                if (c != null) {
                    System.out.println("Polaczenie z baza danych OK ! ");
                    try {
                        PreparedStatement pst1 = c.prepareStatement("SELECT COUNT(*) AS ile FROM projekt1_tempo ");
                        ResultSet rs = pst1.executeQuery();
                        if(rs.next())
                        tyle_sprzed= rs.getString("ile");
                        System.out.println(tyle_sprzed);
                        rs.close();
                        pst1.close();

                    } catch (SQLException e1) {
                        System.out.println("Blad podczas przetwarzania danych:" + e1);
                    }

                }
                int result = JOptionPane.showConfirmDialog(null, "sprzedales dziś: "+tyle_sprzed+" produktow", " wyjdz",
                        JOptionPane.DEFAULT_OPTION);


            } });

    }

    /**
     * Metoda Buduje tabele typu DefaultTableModel
     * na podstawie zwróconego ResultSet'u
     * zróconego przez zapytanie SQL
     * @param rs ResultSet zwórcony przez dane zapytanie SQL
     * @return Zwraca tabele typu DefaultTableModel
     * @throws SQLException
     */
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

    /**
     * Metoda sprawdza czy podany napis strNum jest wartością numeryczną
     * zwraca true jeśli jest wartością numeryczną
     * zwraca false jeśli nie jest wartością numeryczną
     * wykorzystuje do tego metode parseInt z klasy Integer
     * która może parsować tylko String który jest liczbą
     * @param strNum napis typu String
     * @return true jeżeli strNum jest wartością numeryczną zwraca false jeśli nie jest wartością numeryczną
     */
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
     * Funkcja z interfajsu ActionListener
     * obsuługuje wszystkie zdarzenia zdefiniowane w programie
     * (przyciski, Comboboxy itp)
     *
     * @param e ActionEvent
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        /**
         * część kodu odpowiedzialna za przycisk zaloguj
         * ustawia panel do wyświetlenia formularza do logowania
         * Pobiera tabele administratorów (osób które mogą się zalogować)
         */
        if (e.getSource() == przyciski.get(7)) {
            for (JPanel panel : panels)
                panel.setVisible(false);

            panels.get(7).removeAll();
            panels.get(7).setLayout(null);
            panels.get(7).setVisible(true);
            panels.get(7).setBounds(200, 250, 400, 100);

            JLabel loginText = new JLabel("podaj login");
            JLabel hasloText = new JLabel("podaj haslo");


            haslo = new JPasswordField();
            login = new JTextField();
            loguj = new JButton("loguj");
            loginText.setBounds(10, 10, 100, 20);
            hasloText.setBounds(10, 40, 100, 20);
            login.setBounds(100, 10, 100, 20);
            haslo.setBounds(100, 40, 100, 20);
            loguj.setBounds(240, 40, 100, 20);


            panels.get(7).add(loginText);
            panels.get(7).add(login);
            panels.get(7).add(hasloText);
            panels.get(7).add(haslo);
            panels.get(7).add(loguj);
            loguj.addActionListener(this);
            if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst1 = c.prepareStatement("SELECT login,haslo FROM projekt1.admin", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs1 = pst1.executeQuery();

                    loginy = new ArrayList<>();
                    hasla = new ArrayList<>();
                    while (rs1.next()) {
                        loginy.add(rs1.getString("login"));
                        hasla.add(rs1.getString("haslo"));
                    }
                    rs1.close();
                    pst1.close();

                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }

            }
        }

        /**
         * część kodu odpoiwedzialna za przycisk zaloguj przy formularzu
         * weryfikuje czy podany login i hasło są poprawne, jeśli są to
         * Dodaje dodatkowe możliwości do aplikacji dostępne tylko dla Administratora
         *
         */

        if (e.getSource() == loguj) {
            if ((loginy.contains((String) login.getText())) && hasla.contains((String) haslo.getText())) {
                flagaLogowania = true;
                JOptionPane.showMessageDialog(this, "Zalogowano poprawnie");
                przyciski.get(8).setVisible(true);
                przyciski.get(9).setVisible(true);
                przyciski.get(10).setVisible(true);
                wyloguj.setVisible(true);
            } else {
                flagaLogowania = false;
                JOptionPane.showMessageDialog(this, "Błędne  dane logowania");
            }

            this.setVisible(true);
        }
        /**
         * obsługuje przycisk wylogowania
         * przywraca możliwości dotępne dla zwykłego urzytkownika
         */
        if (e.getSource() == wyloguj) {
            if (flagaLogowania == true) {
                JOptionPane.showMessageDialog(this, "Wylogowałeś się");
                wyloguj.setVisible(false);
            }
            flagaLogowania = false;
            przyciski.get(8).setVisible(false);
            przyciski.get(9).setVisible(false);
            przyciski.get(10).setVisible(false);
        }

        /**
         * obusłga przycisku Dodaj klienta
         * tworzy formularz potrzebny do dodania klienta
         */
        if (e.getSource() == przyciski.get(0)) {
            for (JPanel panel : panels)
                panel.setVisible(false);
            panels.get(0).removeAll();
            panels.get(0).setVisible(true);
            panels.get(0).setBounds(100, 250, 600, 300);

            JLabel imieLabel = new JLabel("Podaj Imie:");
            JLabel nazwskoLabel = new JLabel("Podaj Nazwsko:");
            JLabel adresLabel = new JLabel("Podaj Adres");

            imieLabel.setBounds(10, 20, 100, 20);
            nazwskoLabel.setBounds(10, 40, 100, 20);
            adresLabel.setBounds(10, 60, 100, 20);

            panels.get(0).add(imieLabel);
            panels.get(0).add(nazwskoLabel);
            panels.get(0).add(adresLabel);

            imie.setBackground(new Color(220, 220, 220));
            imie.setPreferredSize(new Dimension(200, 20));
            imie.addActionListener(this);
            imie.setBounds(120, 20, 200, 20);

            nazwisko.setBackground(new Color(220, 220, 220));
            nazwisko.setPreferredSize(new Dimension(200, 20));
            nazwisko.addActionListener(this);
            nazwisko.setBounds(120, 40, 200, 20);

            adres.setBackground(new Color(220, 220, 220));
            adres.setPreferredSize(new Dimension(200, 20));
            adres.addActionListener(this);
            adres.setBounds(120, 60, 200, 20);

            panels.get(0).add(imie);
            panels.get(0).add(nazwisko);
            panels.get(0).add(adres);

            panels.get(0).add(wyslij);
            panels.get(0).setVisible(true);
            panels.get(0).setLayout(null);
        }

        /**
         * Obusłga przycisku Dodaj klienta w polu dodawania klientów
         * Dodawanie klienta do bazy Danych do tabeli klienci
         */
        if (e.getSource() == wyslij) {

            panels.get(0).setVisible(false);
            if (imie.getText().equals("") || nazwisko.getText().equals("") || adres.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Podaj prawidłowe dane");
            } else if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst1 = c.prepareStatement("SELECT COUNT(*) AS id FROM projekt1.klienci", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs1 = pst1.executeQuery();
                    if (rs1.next())
                        System.out.println("OK");
                    iter = Integer.parseInt(rs1.getString("id"));

                    rs1.close();
                    pst1.close();
                    iter += 1;
                    PreparedStatement pst = c.prepareStatement("INSERT INTO projekt1.klienci (id_klient,imie,nazwisko,adres) VALUES (?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    pst.setInt(1, iter);
                    System.out.println(imie.getText());
                    pst.setString(2, imie.getText());
                    pst.setString(3, nazwisko.getText());
                    pst.setString(4, adres.getText());

                    imie.setText("");
                    nazwisko.setText("");
                    adres.setText("");
                pst.executeQuery();


                    pst.close();

                    panels.get(0).setVisible(true);
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }
        }
        /**
         * Obsluga przycisku sprzedaj
         * Tworzy formularz wysuzkiwania Produktu
         */
        if (e.getSource() == przyciski.get(1)) {

            for (JPanel panel : panels)
                panel.setVisible(false);
            szukaj.setText("");
            panels.get(1).removeAll();
            panels.get(1).setVisible(true);
            panels.get(1).setBounds(100, 250, 600, 400);

            szukaj.setPreferredSize(new Dimension(200, 20));
            szukaj.setBounds(120, 20, 200, 20);
            szukajLabel.setText("Nazwa Produkt");

            szukajLabel.setBounds(10, 20, 100, 20);
            panels.get(1).add(szukaj);
            panels.get(1).add(szukajLabel);

            if (c != null) {
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT typ FROM projekt1.Typy_produktu", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = pst.executeQuery();

                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    Vector<Object> data = new Vector<Object>();
                    while (rs.next()) {
                        data.add(rs.getObject("typ"));
                    }
                    wybierzRodzaj = new JComboBox(data);
                    rs.close();
                    pst.close();

                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }

            }


            wybierzRodzaj.setBounds(120, 50, 95, 20);

            JLabel wybierzRodzajLabel = new JLabel("Rodzaj produktu");
            wybierzRodzajLabel.setBounds(10, 50, 100, 20);
            JLabel magazynLabel = new JLabel("wybierz magazyn");
            Array tempoMagazyny = null;
            magazyn = new JComboBox(new String[]{"1", "2", "3"});
            if (c != null) {
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT id_magazynu FROM projekt1.Magazyny", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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

            magazynLabel.setBounds(10, 80, 100, 20);
            magazyn.setBounds(120, 80, 100, 20);
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
        /**
         * Obsluga przycisku szukaj w fromularzu sprzedaży
         * wyszukuje produkt w bazie danych i wypisuje
         * w postaci tabeli zwrócony wynik dodaje
         * możliwość sprzedaży wybranego produktu
         *
         */
        if (e.getSource() == szukajPrzycisk) {
            panels.get(1).setVisible(false);
            if (szukaj.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Podaj prawidłowe dane");
            } else if (c != null) {

                try {
                    PreparedStatement pst1 = null;
                    pst1 = c.prepareStatement("SELECT *  FROM projekt1.Produkty WHERE nazwa =  ? AND typ = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    pst1.setString(1,szukaj.getText());
                    pst1.setString(2,wybierzRodzaj.getSelectedItem().toString());
                    ResultSet rs1 = pst1.executeQuery();


                    JTable tabelka = new JTable(buildTableModel(rs1));

                    rs1.beforeFirst();
                    if (rs1.next()) System.out.println("OK");
                    else JOptionPane.showMessageDialog(this, "NIE ZNALEZIONO PRODUKTU");
                    iloscS = rs1.getInt("ilosc");
                    nazwaP = rs1.getString("nazwa");


                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    JTableHeader h1 = tabelka.getTableHeader();

                    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
                    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
                    JScrollPane scroller = new javax.swing.JScrollPane(tabelka, v, h);


                    JPanel tabelkaPanle = new JPanel();
                    tabelkaPanle.setOpaque(true);
                    tabelkaPanle.setLayout(new BorderLayout());
                    tabelkaPanle.setPreferredSize(new Dimension(600, 60));


                    tabelkaPanle.setBounds(0, 130, 600, 60);
                    JLabel text = new JLabel("Znaleziono:");
                    text.setBounds(20, 110, 200, 20);
                    tabelkaPanle.add(scroller, BorderLayout.CENTER);
                    tabelkaPanle.setVisible(true);

                    panels.get(1).add(tabelkaPanle);
                    panels.get(1).add(text);

                    ileSztuk = new JTextField();
                    ileSztuk.setBounds(120, 200, 40, 20);
                    cena = new JTextField();
                    cena.setBounds(120, 230, 40, 20);
                    JLabel iloscLabel = new JLabel("ilość");
                    JLabel cenaLabel = new JLabel("Cena");
                    iloscLabel.setBounds(10, 200, 100, 20);
                    cenaLabel.setBounds(10, 230, 100, 20);
                    JLabel idLabel = new JLabel("id produktu");
                    id = new JTextField("");
                    id.setBounds(120,260,40,20);
                    idLabel.setBounds(10,260,100,20);
                    klient = new JTextField();
                    klient.setBounds(120,290,40,20);
                    JLabel klientLabel = new JLabel("id klienta");
                    klientLabel.setBounds(10,290,100,20);
                    panels.get(1).add(cenaLabel);
                    panels.get(1).add(iloscLabel);
                    panels.get(1).add(id);
                    panels.get(1).add(idLabel);
                    panels.get(1).add(klient);
                    panels.get(1).add(klientLabel);
                    //panels.get(1).setBackground(new Color(30,80,30));
                    panels.get(1).add(cena);
                    panels.get(1).add(ileSztuk);



                    panels.get(1).add(sprzedaj);
                    rs1.close();
                    pst1.close();
                    panels.get(1).setVisible(true);
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }

        }
        /**
         * Obsługa przycisku Sprzedaj w formularzu sprzedaży produktu
         * Sprzedaje produkt klientowi to jest zmienia ilość produktów w odpowiedniej tabeli
         * Po wcześniejszej walidacji wpisanych do formularza danych
         * Dodaje produkty do tabeli sprzedaży projekt1.faktury
         * Sprzedasz odbywa się za pomcą funkcji setfac napisanej po stronie serwera
         */
        if (e.getSource() == sprzedaj) {
            System.out.println("Przycisk Działa");
            if (c != null) {

                    if (klient.getText().equals("") || ileSztuk.getText().equals("") || cena.getText().equals("") || id.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "Podaj prawidłowe dane");
                    }
                    System.out.println(iter + "," + Integer.parseInt(klient.getText()) + "," + Integer.parseInt(id.getText()) + ")");

                try {
                    PreparedStatement pst3 = c.prepareStatement("CALL projekt1.setfac1(?,?,?)");
                    pst3.setInt(2,Integer.parseInt(klient.getText()));
                    pst3.setInt(3,Integer.parseInt(id.getText()));
                    pst3.setInt(1,Integer.parseInt(ileSztuk.getText()));
                    pst3.executeQuery();
                    pst3.close();
                }catch (SQLException e1){System.out.println(" PST 3 "+ e1);}

                JOptionPane.showMessageDialog(this, "Poprawnie Sprzedano");
                panels.get(1).setVisible(false);

            }
        }
        /**
         * obusłga przycisku Dodaj Dostawce
         * Tworzy formularz potrzbeny do dodania dostawcy
         */
        if (e.getSource() == przyciski.get(2)) {
            for (JPanel panel : panels)
                panel.setVisible(false);
            panels.get(2).removeAll();
            panels.get(2).setVisible(true);
            panels.get(2).setBounds(100, 250, 600, 300);

            JLabel imieLabel = new JLabel("Podaj NIP:");
            JLabel nazwskoLabel = new JLabel("Podaj Nazwę:");
            JLabel adresLabel = new JLabel("Podaj Adres");

            imieLabel.setBounds(10, 20, 100, 20);
            nazwskoLabel.setBounds(10, 40, 100, 20);
            adresLabel.setBounds(10, 60, 100, 20);

            panels.get(2).add(imieLabel);
            panels.get(2).add(nazwskoLabel);
            panels.get(2).add(adresLabel);

            imie.setBackground(new Color(220, 220, 220));
            imie.setPreferredSize(new Dimension(200, 20));
            imie.addActionListener(this);
            imie.setBounds(120, 20, 200, 20);

            nazwisko.setBackground(new Color(220, 220, 220));
            nazwisko.setPreferredSize(new Dimension(200, 20));
            nazwisko.addActionListener(this);
            nazwisko.setBounds(120, 40, 200, 20);

            adres.setBackground(new Color(220, 220, 220));
            adres.setPreferredSize(new Dimension(200, 20));
            adres.addActionListener(this);
            adres.setBounds(120, 60, 200, 20);

            panels.get(2).add(imie);
            panels.get(2).add(nazwisko);
            panels.get(2).add(adres);

            panels.get(2).add(wyslijDostawce);

            panels.get(2).setVisible(true);
            panels.get(2).setLayout(null);
        }
        /**
         * Obsluguje przycisk dodaj dostawce w formularzu dodawania dostawców
         * dodanie dostawcy do bazy danych
         * Po wcześniejszej walidacji wpisanych do formularza danych
         *
         */
        if (e.getSource() == wyslijDostawce) {


            if (imie.getText().equals("") || nazwisko.getText().equals("") || adres.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Podaj prawidłowe dane");
            } else if (!isNumeric(imie.getText())) {
                JOptionPane.showMessageDialog(this, "NIP musi być liczbą całkowitą");
            } else if (c != null) {

                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst1 = c.prepareStatement("SELECT COUNT(*) AS id FROM projekt1.Dostawcy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs1 = pst1.executeQuery();
                    if (rs1.next())
                        System.out.println("OK");
                    iter = Integer.parseInt(rs1.getString("id"));
                    rs1.close();
                    pst1.close();
                    iter += 1;
                    PreparedStatement pst = c.prepareStatement("INSERT INTO projekt1.Dostawcy (NIP,nazwa,adres) VALUES (?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    pst.setInt(1, Integer.parseInt(imie.getText()));
                    pst.setString(2, nazwisko.getText());
                    pst.setString(3, adres.getText());

                    imie.setText("");
                    nazwisko.setText("");
                    adres.setText("");
                    pst.executeQuery();



                    pst.close();
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }
        }

        /**
         * Obsługa przycisków pokaż Dostawców
         * wypisanie wszystkich dostawców w postaci tabeli
         */
        if (e.getSource() == przyciski.get(3)) {

            for (JPanel panel : panels)
                panel.setVisible(false);

            panels.get(3).removeAll();
            panels.get(3).setVisible(true);
            panels.get(3).setBounds(100, 250, 600, 600);
            panels.get(3).setBackground(new Color(30, 80, 30));
            JLabel text = new JLabel("Dostawcy:");
            text.setForeground(new Color(229, 184, 1));
            text.setBounds(20, 0, 200, 20);
            String[] sortujTempo = {"NIP", "nazwa", "adres"};
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
                    PreparedStatement pst = c.prepareStatement("SELECT NIP,nazwa,adres FROM projekt1.Dostawcy ORDER BY  " + sortuj.getSelectedItem(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rs;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    tabelka.addNotify();

                    JTableHeader h1 = tabelka.getTableHeader();

                    h1.setBounds(0, 20, 600, 20);
                    tabelka.setBounds(0, 40, 600, 300);
                    tabelka.setAutoResizeMode(1);
                    tabelka.setEnabled(false);
                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(198);

                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    panelTabelka2.setOpaque(true);
                    JScrollPane scroller = new JScrollPane(tabelka, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller,BorderLayout.CENTER);
                    panelTabelka2.setVisible(true);
                    panels.get(3).setVisible(true);

                    //panels.get(3).setLayout(null);
                    rs.close();
                    pst.close();
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }

        }
        /**
         * Obsługa ComboBox'a sortującego w Panelu wyświetlającym Dostawców
         * Dodaje możliwość sortowania wyników zwróconych przez pokaż dostawców
         */
        if (e.getSource() == sortuj) {

            sortuj.removeActionListener(this);
            panels.get(3).setVisible(false);
            panelTabelka2.removeAll();
            if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT NIP,nazwa,adres FROM projekt1.Dostawcy ORDER BY  " + sortuj.getSelectedItem(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rs;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    tabelka.addNotify();
                    JTableHeader h1 = tabelka.getTableHeader();

                    h1.setBounds(0, 20, 600, 20);
                    tabelka.setBounds(0, 40, 600, 300);
                    tabelka.setAutoResizeMode(1);
                    tabelka.setEnabled(false);
                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(198);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(198);

                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    panelTabelka2.setOpaque(true);
                    JScrollPane scroller = new JScrollPane(tabelka, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller,BorderLayout.CENTER);
                    panelTabelka2.setVisible(true);
                    panels.get(3).setVisible(true);
                    rs.close();
                    pst.close();
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }
        }
        /**
         * Obsługa przycisku dodaj Produkt
         * Tworzy formularz potrzebny do dodania produktu do bazy danych
         */
        if (e.getSource() == przyciski.get(4)) {

            for (JPanel panel : panels)
                panel.setVisible(false);
            szukaj.setText("");

            panels.get(4).removeAll();
            panels.get(4).setVisible(true);
            panels.get(4).setBounds(100, 250, 600, 300);

            szukaj.setPreferredSize(new Dimension(200, 20));
            szukaj.setBounds(120, 20, 200, 20);
            szukajLabel.setText("Nazwa Produkt");

            szukajLabel.setBounds(10, 20, 100, 20);
            panels.get(4).add(szukaj);
            panels.get(4).add(szukajLabel);

            dodajPrzycisk = new JButton("Dodaj Produkt");
            dodajPrzycisk.setBounds(440, 130, 120, 40);
            dodajPrzycisk.addActionListener(this);
            if (c != null) {
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT typ FROM projekt1.Typy_produktu", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = pst.executeQuery();

                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    Vector<Object> data = new Vector<Object>();
                    while (rs.next()) {
                        data.add((rs.getObject("typ")));
                    }
                    wybierzRodzaj = new JComboBox(data);

                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }

            wybierzRodzaj.setBounds(120, 120, 95, 20);
            ileSztuk = new JTextField();
            ileSztuk.setBounds(120, 50, 40, 20);
            cena = new JTextField();
            cena.setBounds(120, 80, 40, 20);
            JLabel iloscLabel = new JLabel("ilość");
            JLabel cenaLabel = new JLabel("Cena");
            iloscLabel.setBounds(10, 50, 100, 20);
            cenaLabel.setBounds(10, 80, 100, 20);
            JLabel wybierzRodzajLabel = new JLabel("Rodzaj produktu");
            wybierzRodzajLabel.setBounds(10, 120, 100, 20);
            JLabel magazynLabel = new JLabel("wybierz magazyn");
            Array tempoMagazyny = null;
            JTable tab = null;
            magazyn = new JComboBox();
            if (c != null) {
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT id_magazynu FROM projekt1.Magazyny", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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

            magazynLabel.setBounds(10, 150, 100, 20);
            magazyn.setBounds(120, 150, 100, 20);
          //  id_dostawy = new JTextField();
           // id_dostawy.setBounds(120, 180, 100, 20);
            //JLabel id_dostawyLabel = new JLabel("numer dostawy");
            //id_dostawyLabel.setBounds(10, 180, 100, 20);

            //panels.get(4).add(id_dostawyLabel);
            //panels.get(4).add(id_dostawy);
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
        /**
         * Obłsuga przycisku Dodaj Produkt w fomrularzu dodawania produktu
         * Dodaje produkt do odpowiedniej tabeli w bazie danych
         * Po wcześniejszej walidacji wpisanych do formularza danych
         */
        if (e.getSource() == dodajPrzycisk) {

            if (szukaj.getText().equals("") || ileSztuk.getText().equals("") || cena.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Podaj prawidłowe dane");
            } else if (c != null) {

                try {
                    PreparedStatement pst1 = null;
                    PreparedStatement pst = null;
                        pst1 = c.prepareStatement("SELECT COUNT(*) AS id FROM projekt1.Produkty", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst = c.prepareStatement("INSERT INTO projekt1.Produkty (nazwa,ilosc,cena,id_magazynu,id_produktu,typ) VALUES (?,?,?,?,?,?)");

                    ResultSet rs1 = pst1.executeQuery();
                    if (rs1.next())
                        System.out.println("OK");
                    iter = Integer.parseInt(rs1.getString("id"));

                    iter += 1;

                    pst.setString(1, szukaj.getText());
                    pst.setInt(2, Integer.parseInt(ileSztuk.getText()));
                    pst.setDouble(3, Double.parseDouble(cena.getText()));
                    pst.setInt(4, Integer.parseInt(magazyn.getSelectedItem().toString()));
                    pst.setInt(5, iter);
                    pst.setString(6,wybierzRodzaj.getSelectedItem().toString());

                    szukaj.setText("");
                    ileSztuk.setText("");
                    cena.setText("");

                    pst.executeQuery();

                    pst.close();
                    rs1.close();
                    pst1.close();

                    dodajPrzycisk.removeActionListener(this);
                    dodajPrzycisk.removeAll();
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }
        }

        /**
         * Obsługa przycisku pokaż klientów
         * wypisanie wszystkich klientów w postaci tabeli z możliwością sortowania
         * Pobiera klientów z bazy danych tworzy z nich tabelę a następnie wyspisuje
         */
        if (e.getSource() == przyciski.get(5)) {

            for (JPanel panel : panels)
                panel.setVisible(false);

            panels.get(5).removeAll();
            panels.get(5).setVisible(true);
            panels.get(5).setBounds(100, 250, 600, 600);

            panels.get(5).setBackground(new Color(30, 80, 30));

            String[] sortujTempo = {"id_klient", "imie", "nazwisko", "adres"};
            sortuj2 = new JComboBox(sortujTempo);
            sortuj2.addActionListener(this);
            JLabel text = new JLabel("Klienci:");
            text.setForeground(new Color(229, 184, 1));
            text.setBounds(20, 0, 200, 20);

            panels.get(5).add(sortuj2);
            panels.get(5).add(text);

            panelTabelka2 = new JPanel();
            panelTabelka2.removeAll();
            if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT id_klient,imie,nazwisko,adres FROM projekt1.klienci ORDER BY  " + sortuj2.getSelectedItem(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rs;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    JTableHeader h1 = tabelka.getTableHeader();

                    h1.setBounds(0, 20, 600, 20);
                    tabelka.setBounds(0, 40, 600, 300);

                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(49);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(180);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(180);
                    tabelka.getColumnModel().getColumn(3).setPreferredWidth(190);

                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    panelTabelka2.setOpaque(true);

                    JScrollPane scroller = new JScrollPane(tabelka, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller,BorderLayout.CENTER);
                    panelTabelka2.setVisible(true);

                    panels.get(5).add(panelTabelka2);
                    rs.close();
                    pst.close();
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }

        }
        /**
         * Obsługa ComboBox'a odpowiedzilanego za sotrowanie wypisanej tabeli Klientów
         * Sortuje tabele klientów w zależności od wybranego atrybutu w ComboBox'ie
         */
        if (e.getSource() == sortuj2) {

            panels.get(5).setVisible(false);
            panelTabelka2.removeAll();
            if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT id_klient,imie,nazwisko,adres FROM projekt1.klienci ORDER BY " + sortuj2.getSelectedItem(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    ResultSet rs;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    JTableHeader h1 = tabelka.getTableHeader();

                    h1.setBounds(0, 20, 600, 20);
                    tabelka.setBounds(0, 40, 600, 300);

                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(49);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(180);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(180);
                    tabelka.getColumnModel().getColumn(3).setPreferredWidth(190);


                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    panelTabelka2.setOpaque(true);
                    JScrollPane scroller = new JScrollPane(tabelka, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller,BorderLayout.CENTER);
                    panelTabelka2.setVisible(true);

                    panels.get(5).setVisible(true);
                    rs.close();
                    pst.close();
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }
        }

        /**
         * Obsługa przycisku Lista Produktów
         * wypisanie wszystkich produktów w postaci Tabeli
         * w zależności od wybranej kategori produktu (domyślnie narzędzia)
         */
        if (e.getSource() == przyciski.get(6)) {
            for (JPanel panel : panels)
                panel.setVisible(false);

            panels.get(6).removeAll();
            panels.get(6).setVisible(true);
            panels.get(6).setBounds(100, 250, 600, 600);

            if (c != null) {
                try {
                    PreparedStatement pst = c.prepareStatement("SELECT typ FROM projekt1.Typy_produktu", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = pst.executeQuery();

                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    Vector<Object> data = new Vector<Object>();
                    while (rs.next()) {
                        data.add((rs.getObject("typ")));
                    }
                    rodzaj1 = new JComboBox(data);

                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }
            }
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

        /**
         * Obsługa ComboBox'a odpowiedzialnego za wybór rodzaju produktów
         * Obsługuje zmiane wyświetlanych produktów w zależności od rodzaju
         * Wypisuje tabele produktów odpoowiedniego rodzaju
         */
        if (e.getSource() == przyciskProdukty) {

            panels.get(6).setVisible(false);
            panels.get(6).remove(panelTabelka2);
            if (c != null) {
                try {
                    PreparedStatement pst;
                        pst = c.prepareStatement("SELECT id_produktu,nazwa,ilosc,cena,typ,id_magazynu FROM projekt1.Produkty WHERE typ = ? ", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        pst.setString(1,rodzaj1.getSelectedItem().toString());
                    ResultSet rs;
                    rs = pst.executeQuery();
                    JTable tabelka = new JTable(buildTableModel(rs));
                    JTableHeader h1 = tabelka.getTableHeader();
                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                    tabelka.getColumnModel().getColumn(0).setPreferredWidth(100);
                    tabelka.getColumnModel().getColumn(1).setPreferredWidth(150);
                    tabelka.getColumnModel().getColumn(2).setPreferredWidth(100);
                    tabelka.getColumnModel().getColumn(3).setPreferredWidth(100);
                    tabelka.getColumnModel().getColumn(4).setPreferredWidth(100);
                    tabelka.getColumnModel().getColumn(5).setPreferredWidth(50);

                    panelTabelka2.removeAll();
                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 400));
                    JScrollPane scroller = new JScrollPane(tabelka, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller,BorderLayout.CENTER);
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
        /**
         * Obsługa przycisku Dodaj Magazyn dostępnego tylko dla zalogowanego urzytkownika
         * Tworzy formularz potrzbny do dodania magazynu do bazy danych
         */
        if (e.getSource() == przyciski.get(8) && flagaLogowania == true) {
            for (JPanel panel : panels)
                panel.setVisible(false);
            panels.get(8).removeAll();
            panels.get(8).setVisible(true);
            panels.get(8).setBounds(100, 250, 600, 300);

            JLabel imieLabel = new JLabel("Podaj nazwe:");
            JLabel nazwskoLabel = new JLabel("Podaj adres:");
            JLabel adresLabel = new JLabel("Podaj id zarządcy ");

            imieLabel.setBounds(10, 20, 100, 20);
            nazwskoLabel.setBounds(10, 40, 100, 20);
            adresLabel.setBounds(10, 60, 160, 20);

            panels.get(8).add(imieLabel);
            panels.get(8).add(nazwskoLabel);
            panels.get(8).add(adresLabel);

            imie.setBackground(new Color(220, 220, 220));
            imie.setPreferredSize(new Dimension(200, 20));
            imie.addActionListener(this);
            imie.setBounds(160, 20, 200, 20);

            nazwisko.setBackground(new Color(220, 220, 220));
            nazwisko.setPreferredSize(new Dimension(200, 20));
            nazwisko.addActionListener(this);
            nazwisko.setBounds(160, 40, 200, 20);

            adres.setBackground(new Color(220, 220, 220));
            adres.setPreferredSize(new Dimension(200, 20));
            adres.addActionListener(this);
            adres.setBounds(160, 60, 200, 20);


            panels.get(8).add(imie);
            panels.get(8).add(nazwisko);
            panels.get(8).add(adres);

            panels.get(8).add(wyslijMagazyn);
            panels.get(8).setVisible(true);
            panels.get(8).setLayout(null);
        }
        /**
         * Obsluga przycisku Dodaj Magazyn w formularzu dodawania magazynu
         * Dodaje magazyn do bazy danych po wcześniejszej waldacji
         * danych wpisanych do formularza
         */
        if (e.getSource() == wyslijMagazyn) {

            if (imie.getText().equals("") || nazwisko.getText().equals("") || adres.getText().equals(""))
                JOptionPane.showMessageDialog(this, "Podaj prawidłowe dane");

            else if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {


                    PreparedStatement pst1 = c.prepareStatement("SELECT COUNT(*) AS id FROM projekt1.magazyny", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs1 = pst1.executeQuery();
                    if (rs1.next())
                        System.out.println("OK");
                    iter = Integer.parseInt(rs1.getString("id"));
                    rs1.close();
                    pst1.close();
                    iter += 1;
                    PreparedStatement pst = c.prepareStatement("INSERT INTO projekt1.magazyny (id_magazynu,nazwa,adres,id_admin) VALUES (?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    pst.setString(1, Integer.toString(iter));
                    pst.setString(2, imie.getText());
                    pst.setString(3,nazwisko.getText());
                    pst.setInt(4,Integer.parseInt( adres.getText()));

                    imie.setText("");
                    nazwisko.setText("");
                    adres.setText("");
                    pst.executeQuery();


                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }

            }
        }
        /**
         * Obsługa Przycisku Nowa Dostawa dostępnego tylko dla zalogowanych urzytkowników
         * Tworzy formularz potrzebny do dodania nowej dostawy
         */
        if (e.getSource() == przyciski.get(9) && flagaLogowania == true) {
            /**
             * obusłga przycisku Dodaj Dostawe
             */
            for (JPanel panel : panels)
                panel.setVisible(false);
            panels.get(9).removeAll();
            panels.get(9).setVisible(true);
            panels.get(9).setBounds(100, 250, 600, 300);

            JLabel imieLabel = new JLabel("Podaj nr magazynui:");
            JLabel nazwskoLabel = new JLabel("Podaj NIP:");
            JLabel adresLabel = new JLabel("Podaj date RRRR-MM-DD");

            imieLabel.setBounds(10, 20, 160, 20);
            nazwskoLabel.setBounds(10, 40, 100, 20);
            adresLabel.setBounds(10, 60, 160, 20);

            panels.get(9).add(imieLabel);
            panels.get(9).add(nazwskoLabel);
            panels.get(9).add(adresLabel);

            imie.setBackground(new Color(220, 220, 220));
            imie.setPreferredSize(new Dimension(200, 20));
            imie.addActionListener(this);
            imie.setBounds(160, 20, 200, 20);

            nazwisko.setBackground(new Color(220, 220, 220));
            nazwisko.setPreferredSize(new Dimension(200, 20));
            nazwisko.addActionListener(this);
            nazwisko.setBounds(160, 40, 200, 20);

            adres.setBackground(new Color(220, 220, 220));
            adres.setPreferredSize(new Dimension(200, 20));
            adres.addActionListener(this);
            adres.setBounds(160, 60, 200, 20);


            panels.get(9).add(imie);
            panels.get(9).add(nazwisko);
            panels.get(9).add(adres);

            panels.get(9).add(wyslijDostawa);
            panels.get(9).setVisible(true);
            panels.get(9).setLayout(null);
        }
        /**
         * Obsługa przycisku  Dodaj w formularzu dodawania dostawy
         * Dodaje dostawię do bazy danych
         * Po wcześniejszej walidacji wpisanych do formularza danych
         */
        if (e.getSource() == wyslijDostawa) {

            if (imie.getText().equals("") || nazwisko.getText().equals("") || adres.getText().equals(""))
                JOptionPane.showMessageDialog(this, "Podaj prawidłowe dane");
            else if (adres.getText().length() != 10 || !adres.getText().matches("[1-9][0-9][0-9][0-9][-][0-1][0-9][-][0-3][0-9]"))
                JOptionPane.showMessageDialog(this, "Podaj date");

            else if (c != null) {

                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst1 = c.prepareStatement("SELECT COUNT(*) AS id FROM projekt1.dostawy", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs1 = pst1.executeQuery();
                    if (rs1.next())
                    if((iter = Integer.parseInt(rs1.getString("id"))) != 0);
                    rs1.close();
                    pst1.close();
                    iter += 1;

                    int r = Integer.parseInt(adres.getText().substring(0,3));
                    int m = Integer.parseInt(adres.getText().substring(5, 6));
                    int n = Integer.parseInt(adres.getText().substring(7, 9));
                    java.util.Date utilDate = new java.util.Date(r, m, n);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    PreparedStatement pst = c.prepareStatement("INSERT INTO projekt1.dostawy (id_magazynu,NIP,data,id_dostawy) VALUES (?,?,?,?)", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    pst.setString(1, (imie.getText()));
                    pst.setInt(2, Integer.parseInt(nazwisko.getText()));
                    pst.setDate(3,sqlDate);
                    pst.setInt(4,iter);

                    imie.setText("");
                    nazwisko.setText("");
                    adres.setText("");
                    pst.executeQuery();


                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                }

            }
        }
        /**
         * Obsługa przycisku Zapytanie SQL dostępnego dla zalogowanego urzytkownika
         * Tworzy formluarz pozwalający wpisać dowolne zapytanie SQL dla polecenia
         * SELECT tworzy tabele ze zwróconych wyników
         */
        if (e.getSource() == przyciski.get(10) && flagaLogowania == true) {

            for (JPanel panel : panels)
                panel.setVisible(false);
            panels.get(10).removeAll();
            panels.get(10).setVisible(true);
            panels.get(10).setBounds(100, 250, 600, 500);

            JLabel imieLabel = new JLabel("Wpisz zapytanie SQL któr  chcesz wykonać:");
            imieLabel.setBounds(10, 20, 400, 20);
            panels.get(10).add(imieLabel);

            imie.setBackground(new Color(220, 220, 220));
            imie.setPreferredSize(new Dimension(200, 20));
            imie.addActionListener(this);
            imie.setBounds(10, 50, 500, 20);



            panels.get(10).add(imie);

            panels.get(10).add(zapytanie);
            panels.get(10).setVisible(true);
            panels.get(10).setLayout(null);
            panelTabelka2 = new JPanel();
            panelTabelka2.removeAll();

            panels.get(10).add(panelTabelka2);

        }

        /**
         * Obsługa przycisku potwierdź w formularzu Zapytanie SQL
         * Obusłga Dowolnego zapytania SQL tylko dla amina
         * Wykonuje dowolne zapytanie SQL dla SELECT tworzy tabele wyników
         */
        if(e.getSource()==zapytanie){
            panelTabelka2.removeAll();
            panels.get(10).setVisible(false);
            if (imie.getText().equals("") ) {
                JOptionPane.showMessageDialog(this, "Zapytanie jest puste XD");
            } else if (c != null) {
                System.out.println("Polaczenie z baza danych OK ! ");
                try {
                    PreparedStatement pst1 = c.prepareStatement(imie.getText(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs1 = pst1.executeQuery();

                    imie.setText("");
                    JTable tabelka = new JTable(buildTableModel(rs1));
                    JTableHeader h1 = tabelka.getTableHeader();

                    h1.setBounds(0, 20, 600, 20);
                    tabelka.setBounds(0, 40, 600, 300);

                    tabelka.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                    tabelka.setAutoCreateColumnsFromModel(true);
                    panelTabelka2.setBounds(0,120,600,350);
                    panelTabelka2.setLayout(new BorderLayout());
                    panelTabelka2.setPreferredSize(new Dimension(600, 350));
                    panelTabelka2.setOpaque(true);

                    JScrollPane scroller = new JScrollPane(tabelka, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                    panelTabelka2.add(scroller,BorderLayout.CENTER);
                    panelTabelka2.setVisible(true);



                    panels.get(10).add(panelTabelka2);
                    panels.get(10).setVisible(true);
                    rs1.close();
                    pst1.close();
                } catch (SQLException e1) {
                    System.out.println("Blad podczas przetwarzania danych:" + e1);
                    JOptionPane.showMessageDialog(this, e1);
                }
            }
        }
    }
}

