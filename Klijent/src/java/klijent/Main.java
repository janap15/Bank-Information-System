/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.Pair;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author Jana
 */
public class Main extends JFrame implements ActionListener {
    
    private static final String zahtevi[] = { 
        "Kreiranje mesta", 
        "Kreiranje filijale u mestu", 
        "Kreiranje komitenta", 
        "Promena sedišta za zadatog komitenta", 
        "Otvaranje računa", 
        "Zatvaranje računa", 
        "Transakcija prenosa sume sa jednog računa na drugi račun",
        "Transakcija uplate novca na račun", 
        "Transakcija isplate novca sa računa",
        "Dohvatanje svih mesta", 
        "Dohvatanje svih filijala",
        "Dohvatanje svih komitenata", 
        "Dohvatanje svih računa za komitenta",
        "Dohvatanje svih transakcija za račun", 
        "Dohvatanje svih podataka iz rezervne kopije",
        "Dohvatanje razlike u podacima u originalnim podacima i u rezervnoj kopiji"};
    
    private JPanel bigPanel = new JPanel();
    private JComboBox c1 = new JComboBox<>(zahtevi);
    
    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    } 
    
    public Main(){
        try {
            // UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            // JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) { 
        }
        setUIFont(new javax.swing.plaf.FontUIResource("Arial",Font.PLAIN,14));
      
        setBounds(500, 150, 800, 700);
        setLayout(new BorderLayout());
        
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(Box.createRigidArea(new Dimension(0, 25)));
        
        JPanel pan = new JPanel();
        c1.addActionListener(this);
        c1.setFont(new Font("Arial",Font.PLAIN,14));
        JLabel l1 = new JLabel("Odaberite zahtev:   ");
        pan.add(l1);
        pan.add(c1);
        p.add(pan);
        
        p.add(Box.createRigidArea(new Dimension(0, 25)));
        
        add(p, BorderLayout.NORTH);
        add(bigPanel, BorderLayout.CENTER);
        
        setTitle("Informacioni sistem banke");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    
    private void kreiranjeMestaForma() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2, 10, 10));
        p1.setMaximumSize(new Dimension(300, 60));
        JLabel nazivLabela = new JLabel("Unesite naziv mesta");
        JTextField naziv = new JTextField();
        JLabel postanskiBrLabela = new JLabel("Unesite postanski broj");
        JTextField postanskiBr = new JTextField();
        p1.add(nazivLabela);
        p1.add(naziv);
        p1.add(postanskiBrLabela);
        p1.add(postanskiBr);
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Kreiraj");
        b.addActionListener(al->{
            String regex = "[0-9]+";
            
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(postanskiBr.getText());
            
            if (m.matches() && !naziv.getText().isEmpty()){
                if (HTCL.kreirajMesto(Integer.parseInt(postanskiBr.getText()), naziv.getText())){
                    ispisValidnosti.setText("Uspešno ste kreirali mesto!");
                }
                else{
                    ispisValidnosti.setText("Mesto nije kreirano.");
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
           
        });
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void kreiranjeFilijale() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2, 10, 10));
        p1.setMaximumSize(new Dimension(300, 60));
        JLabel nazivLabela = new JLabel("Unesite naziv filijale");
        JTextField naziv = new JTextField();
        JLabel adresaLabela = new JLabel("Unesite adresu filijale");
        JTextField adresa = new JTextField();
        JLabel nazivMestaLabela = new JLabel("Unesite naziv mesta");
        JTextField nazivMesta = new JTextField();
        p1.add(nazivLabela);
        p1.add(naziv);
        p1.add(adresaLabela);
        p1.add(adresa);
        p1.add(nazivMestaLabela);
        p1.add(nazivMesta);
        
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Kreiraj");
        b.addActionListener(al->{
            if (!adresa.getText().isEmpty() && !naziv.getText().isEmpty() && !nazivMesta.getText().isEmpty()){
                int ret = HTCL.kreirajFilijalu(naziv.getText(), adresa.getText(), nazivMesta.getText());
                switch (ret) {
                    case 1:
                        ispisValidnosti.setText("Uspešno ste kreirali filijalu!");
                        break;
                    case 2:
                        ispisValidnosti.setText("Ne postoji mesto.");
                        break;
                    case -1:
                        ispisValidnosti.setText("Neuspešno kreiranje!");
                        break;
                    default:
                        break;
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
           
        });
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    
    private void kreiranjeKomitenta() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2, 10, 10));
        p1.setMaximumSize(new Dimension(400, 60));
        JLabel nazivLabela = new JLabel("Unesite naziv komitenta");
        JTextField naziv = new JTextField();
        JLabel adresaLabela = new JLabel("Unesite adresu komitenta");
        JTextField adresa = new JTextField();
        JLabel nazivMestaLabela = new JLabel("Unesite naziv mesta");
        JTextField nazivMesta = new JTextField();
        p1.add(nazivLabela);
        p1.add(naziv);
        p1.add(adresaLabela);
        p1.add(adresa);
        p1.add(nazivMestaLabela);
        p1.add(nazivMesta);
        
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Kreiraj");
        b.addActionListener(al->{
            if (!adresa.getText().isEmpty() && !naziv.getText().isEmpty() && !nazivMesta.getText().isEmpty()){
                int ret = HTCL.kreirajKomitenta(naziv.getText(), adresa.getText(), nazivMesta.getText());
                switch (ret) {
                    case 1:
                        ispisValidnosti.setText("Uspešno ste kreirali komitenta!"); break;
                    case 2:
                        ispisValidnosti.setText("Ne postoji mesto.");
                        break;
                    case -1:
                        ispisValidnosti.setText("Neuspešno kreiranje!");
                        break;
                    default:
                        break;
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
           
        });
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void promenaSedistaKomitenta() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2, 10, 10));
        p1.setMaximumSize(new Dimension(400, 60));
        JLabel nazivLabela = new JLabel("Unesite naziv komitenta");
        JTextField naziv = new JTextField();
        JLabel nazivMestaLabela = new JLabel("Unesite naziv mesta");
        JTextField nazivMesta = new JTextField();
        p1.add(nazivLabela);
        p1.add(naziv);
        p1.add(nazivMestaLabela);
        p1.add(nazivMesta);
        
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Kreiraj");
        b.addActionListener(al->{
            if (!naziv.getText().isEmpty() && !nazivMesta.getText().isEmpty()){
                int ret = HTCL.promenaSedistaKomitenta(naziv.getText(), nazivMesta.getText());
                switch (ret) {
                    case 1:
                        ispisValidnosti.setText("Uspešno ste promenili sedište komitentu!");
                        break;
                    case 2:
                        ispisValidnosti.setText("Ne postoji komitent.");
                        break;
                    case 3:
                        ispisValidnosti.setText("Ne postoji mesto.");
                        break;
                    case -1:
                        ispisValidnosti.setText("Neuspešno kreiranje!");
                        break;
                    default:
                        break;
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
           
        });
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void kreiranjeRacuna(){
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2, 10, 10));
        p1.setMaximumSize(new Dimension(400, 60));
        JLabel nazivLabela = new JLabel("Unesite naziv komitenta");
        JTextField naziv = new JTextField();
        JLabel nazivMestaLabela = new JLabel("Unesite naziv mesta");
        JTextField nazivMesta = new JTextField();
        JLabel dozvoljeniMinusLabela = new JLabel("Unesite dozvoljeni minus");
        JTextField dozvoljeniMinus = new JTextField();
        p1.add(nazivLabela);
        p1.add(naziv);
        p1.add(nazivMestaLabela);
        p1.add(nazivMesta);
        p1.add(dozvoljeniMinusLabela);
        p1.add(dozvoljeniMinus);
        
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Kreiraj");
        b.addActionListener(al->{
            String regex = "[0-9]+(\\.[0-9]+)?";
            
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dozvoljeniMinus.getText());
            
            if (m.matches() && !naziv.getText().isEmpty() && !nazivMesta.getText().isEmpty() && !dozvoljeniMinus.getText().isEmpty()){
                int ret = HTCL.otvaranjeRacuna(naziv.getText(), nazivMesta.getText(), Double.parseDouble(dozvoljeniMinus.getText()));
                switch (ret) {
                    case 1:
                        ispisValidnosti.setText("Uspešno ste otvorili račun!");
                        break;
                    case 2:
                        ispisValidnosti.setText("Ne postoji komitent.");
                        break;
                    case 3:
                        ispisValidnosti.setText("Ne postoji mesto.");
                        break;
                    case -1:
                        ispisValidnosti.setText("Neuspešno kreiranje!");
                        break;
                    default:
                        break;
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
        });
        
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void gasenjeRacuna(){
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 2, 10, 10));
        p1.setMaximumSize(new Dimension(400, 60));
        JLabel racunLabela = new JLabel("Unesite ID računa");
        JTextField racun = new JTextField();
        p1.add(racunLabela);
        p1.add(racun);
 
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Zatvori račun");
        b.addActionListener(al->{
            String regex = "[0-9]+";
            
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(racun.getText());
            
            if (m.matches() && !racun.getText().isEmpty()){
                int ret = HTCL.gasenjeRacuna(Integer.parseInt(racun.getText()));
                switch (ret) {
                    case 1:
                        ispisValidnosti.setText("Uspešno ste zatvorili račun!");
                        break;
                    case 2:
                        ispisValidnosti.setText("Ne postoji račun.");
                        break;
                    case -1:
                        ispisValidnosti.setText("Neuspešno zatvaranje računa!");
                        break;
                    default:
                        break;
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
        });
        
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void prenosNovca(){
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2, 10, 10));
        p1.setMaximumSize(new Dimension(600, 60));
        JLabel racunSaLabela = new JLabel("Unesite ID računa sa koga prenosite novac");
        JTextField racunSa = new JTextField();
        JLabel racunKaLabela = new JLabel("Unesite ID računa na koji prenosite novac");
        JTextField racunKa = new JTextField();
        JLabel iznosLabela = new JLabel("Unesite iznos novca za prenos");
        JTextField iznos = new JTextField();
        p1.add(racunSaLabela);
        p1.add(racunSa);
        p1.add(racunKaLabela);
        p1.add(racunKa);
        p1.add(iznosLabela);
        p1.add(iznos);
 
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Prenesi novac");
        b.addActionListener(al->{
            String regex1 = "[0-9]+";
            String regex2 = "[0-9]+(\\.[0-9]+)?";
            Pattern patt1 = Pattern.compile(regex1);
            Pattern patt2 = Pattern.compile(regex2);
            Matcher m1 = patt1.matcher(racunSa.getText());
            Matcher m2 = patt1.matcher(racunKa.getText());
            Matcher m3 = patt2.matcher(iznos.getText());
            
            if (m1.matches() && m2.matches() && m3.matches() && !racunSa.getText().isEmpty() && !racunKa.getText().isEmpty()){
                int ret = HTCL.prenosNovca(Integer.parseInt(racunSa.getText()), Integer.parseInt(racunKa.getText()), Double.parseDouble(iznos.getText()));
                switch (ret) {
                    case 1:
                        ispisValidnosti.setText("Uspešno ste preneli novac!");
                        break;
                    case 2:
                        ispisValidnosti.setText("Ne postoji račun sa kojeg želite prenos novca.");
                        break;
                    case 3:
                        ispisValidnosti.setText("Ne postoji račun na koji želite da prenosete novac.");
                        break;
                    case 4:
                        ispisValidnosti.setText("Nije moguć prenos novca.");
                        break;
                    default:
                        break;
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
        });
        
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void uplataNovca(){
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2, 10, 10));
        p1.setMaximumSize(new Dimension(500, 60));
        JLabel racunLabela = new JLabel("Unesite ID računa za uplatu novca");
        JTextField racun = new JTextField();
        JLabel filijalaLabela = new JLabel("Unesite naziv filijale");
        JTextField filijala = new JTextField();
        JLabel iznosLabela = new JLabel("Unesite iznos novca za uplatu");
        JTextField iznos = new JTextField();
        p1.add(racunLabela);
        p1.add(racun);
        p1.add(filijalaLabela);
        p1.add(filijala);
        p1.add(iznosLabela);
        p1.add(iznos);
 
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Uplati novac");
        b.addActionListener(al->{
            String regex1 = "[0-9]+";
            String regex2 = "[0-9]+(\\.[0-9]+)?";
            Pattern patt1 = Pattern.compile(regex1);
            Pattern patt2 = Pattern.compile(regex2);
            Matcher m1 = patt1.matcher(racun.getText());
            Matcher m2 = patt2.matcher(iznos.getText());
            
            if (m1.matches() && m2.matches() && !racun.getText().isEmpty() && !iznos.getText().isEmpty()){
                int ret = HTCL.uplata(Integer.parseInt(racun.getText()), filijala.getText(), Double.parseDouble(iznos.getText()));
                switch (ret) {
                    case 1:
                        ispisValidnosti.setText("Uspešno ste uplatili novac!");
                        break;
                    case 2:
                        ispisValidnosti.setText("Ne postoji račun na koji želite da uplatite novac.");
                        break;
                    case 3:
                        ispisValidnosti.setText("Ne postoji filijala.");
                        break;  
                    default:
                        break;
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
        });
        
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void isplataNovca(){
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        bigPanel.add(Box.createVerticalGlue());
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(3, 2, 10, 10));
        p1.setMaximumSize(new Dimension(500, 60));
        JLabel racunLabela = new JLabel("Unesite ID računa za isplatu novca");
        JTextField racun = new JTextField();
        JLabel filijalaLabela = new JLabel("Unesite naziv filijale");
        JTextField filijala = new JTextField();
        JLabel iznosLabela = new JLabel("Unesite iznos novca za uplatu");
        JTextField iznos = new JTextField();
        p1.add(racunLabela);
        p1.add(racun);
        p1.add(filijalaLabela);
        p1.add(filijala);
        p1.add(iznosLabela);
        p1.add(iznos);
 
        bigPanel.add(p1);
        
        bigPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        
        JLabel ispisValidnosti = new JLabel(" ");
        ispisValidnosti.setAlignmentX(CENTER_ALIGNMENT);
        JButton b = new JButton("Isplati novac");
        b.addActionListener(al->{
            String regex1 = "[0-9]+";
            String regex2 = "[0-9]+(\\.[0-9]+)?";
            Pattern patt1 = Pattern.compile(regex1);
            Pattern patt2 = Pattern.compile(regex2);
            Matcher m1 = patt1.matcher(racun.getText());
            Matcher m2 = patt2.matcher(iznos.getText());
            
            if (m1.matches() && m2.matches() && !racun.getText().isEmpty() && !iznos.getText().isEmpty()){
                int ret = HTCL.isplata(Integer.parseInt(racun.getText()), filijala.getText(), Double.parseDouble(iznos.getText()));
                switch (ret) {
                    case 1:
                        ispisValidnosti.setText("Uspešno ste isplatili novac!");
                        break;
                    case 2:
                        ispisValidnosti.setText("Ne postoji račun sa kog želite da isplatite novac.");
                        break;
                    case 3:
                        ispisValidnosti.setText("Nije moguće izvršiti isplatu.");
                        break;
                    case 4:
                        ispisValidnosti.setText("Ne postoji filijala.");
                        break;  
                    default:
                        break;
                }
            }
            else  ispisValidnosti.setText("Niste uneli dobre podatke.");
        });
        
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(ispisValidnosti);
        
        bigPanel.add(Box.createVerticalGlue());
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void dohvatanjeSvihMesta() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setMinimumSize(new Dimension(660, 500));
        panel.setMaximumSize(new Dimension(660, 500));
        
        JButton b = new JButton("Dohvati");
        b.addActionListener(al->{
            JSONArray jArray = HTCL.svaMesta();
            
            panel.removeAll();
            if(jArray != null){
                panel.add(new JScrollPane(TableHelper.tabelaMesta(jArray)));
            }
            else {
                JLabel label = new JLabel("Došlo je do greške!");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.TOP);
                panel.add(label);
            }
           
            panel.repaint();
            panel.revalidate();
        });
        bigPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(panel);
        
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void dohvatanjeSvihFilijala() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setMinimumSize(new Dimension(660, 500));
        panel.setMaximumSize(new Dimension(660, 500));
        
        JButton b = new JButton("Dohvati");
        b.addActionListener(al->{
            JSONArray jArray = HTCL.sveFilijale();
            
            panel.removeAll();
            if(jArray != null){
                panel.add(new JScrollPane(TableHelper.tabelaFilijale(jArray)));
            }
            else {
                JLabel label = new JLabel("Došlo je do greške!");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.TOP);
                panel.add(label);
            }
           
            panel.repaint();
            panel.revalidate();
        });
        bigPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(panel);
        
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void dohvatanjeSvihKomitenata() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setMinimumSize(new Dimension(660, 500));
        panel.setMaximumSize(new Dimension(660, 500));
        
        JButton b = new JButton("Dohvati");
        b.addActionListener(al->{
            JSONArray jArray = HTCL.sviKomitenti();
            
            panel.removeAll();
            if(jArray != null){
                panel.add(new JScrollPane(TableHelper.tabelaKomitenti(jArray)));
            }
            else {
                JLabel label = new JLabel("Došlo je do greške!");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.TOP);
                panel.add(label);
            }
           
            panel.repaint();
            panel.revalidate();
        });
        bigPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(panel);
        
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void dohvatanjeSvihRacunaZaKomitenata() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setMinimumSize(new Dimension(660, 450));
        panel.setMaximumSize(new Dimension(660, 450));
        
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.setMaximumSize(new Dimension(360, 30));
        JLabel nazivLabela = new JLabel("Unesite naziv komitenta:");
        JTextField nazivTF = new JTextField();
        p.add(nazivLabela);
        p.add(nazivTF);
        
        JButton b = new JButton("Dohvati");
        b.addActionListener(al->{
            String n = nazivTF.getText();
            if (!n.isEmpty()){
                n = n.replaceAll(" ", "%20");
                Pair<Integer, JSONArray> pair = HTCL.sviRacuniKomitenta(n);

                panel.removeAll();

                int statusCode = pair.getKey();
                if(statusCode == 1){
                    JSONArray jArray = pair.getValue();
                    panel.add(new JScrollPane(TableHelper.tabelaRacuni(jArray)));
                }
                else if (statusCode == 2){
                    JLabel label = new JLabel("Ne postoji komitent!");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.TOP);
                    panel.add(label);
                }
                else {
                    JLabel label = new JLabel("Došlo je do greške!");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.TOP);
                    panel.add(label);
                }
            }
            else{
                JLabel label = new JLabel("Niste uneli dobre podatke.");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.TOP);
                panel.add(label);
            }
           
            panel.repaint();
            panel.revalidate();
        });
        bigPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        bigPanel.add(p);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(panel);
        
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void dohvatanjeSvihTransakcijaZaRacun() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setMinimumSize(new Dimension(660, 450));
        panel.setMaximumSize(new Dimension(660, 450));
        
        JPanel p = new JPanel(new GridLayout(1, 2));
        p.setMaximumSize(new Dimension(360, 30));
        JLabel idLabela = new JLabel("Unesite id računa:");
        JTextField idTF = new JTextField();
        p.add(idLabela);
        p.add(idTF);
        
        JButton b = new JButton("Dohvati");
        b.addActionListener(al->{
            String regex = "[0-9]+";
            
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(idTF.getText());
            
            if(matcher.matches()){
                int id = Integer.parseInt(idTF.getText());
                Pair<Integer, JSONArray> pair = HTCL.sveTransakcijeRacuna(id);

                panel.removeAll();

                int statusCode = pair.getKey();
                if(statusCode == 1){
                    JSONArray jArray = pair.getValue();
                    panel.add(new JScrollPane(TableHelper.tabelaTransakcije(jArray)));
                }
                else if (statusCode == 2){
                    JLabel label = new JLabel("Ne postoji račun!");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.TOP);
                    panel.add(label);
                }
                else {
                    JLabel label = new JLabel("Došlo je do greške!");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.TOP);
                    panel.add(label);
                }
            }
            else{
                JLabel label = new JLabel("Niste uneli dobre podatke.");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.TOP);
                panel.add(label);
            }
            panel.repaint();
            panel.revalidate();
        });
        bigPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        bigPanel.add(p);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(panel);
        
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void dohvatanjeSvihPodataka() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setMinimumSize(new Dimension(660, 500));
        panel.setMaximumSize(new Dimension(660, 500));
        
        JButton b = new JButton("Dohvati");
        b.addActionListener(al->{
            JSONObject jObject = HTCL.sviPodaci();
            
            panel.removeAll();
            if(jObject != null){
                JPanel tmpPanel = new JPanel();
                tmpPanel.setLayout(new BoxLayout(tmpPanel, BoxLayout.Y_AXIS));
                
                JSONArray jArrayMesta = (JSONArray) jObject.get("mesta");
                JTable t1 = TableHelper.tabelaMesta(jArrayMesta);
                JScrollPane sp1 = new JScrollPane(t1);
                sp1.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp1);
                tmpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                
                JSONArray jArrayFilijale = (JSONArray) jObject.get("filijale");
                JTable t2 = TableHelper.tabelaFilijale(jArrayFilijale);
                JScrollPane sp2 = new JScrollPane(t2);
                sp2.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp2);
                tmpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                
                JSONArray jArrayKomitenti = (JSONArray) jObject.get("komitenti");
                JTable t3 = TableHelper.tabelaKomitenti(jArrayKomitenti);
                JScrollPane sp3 = new JScrollPane(t3);
                sp3.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp3);
                tmpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                
                JSONArray jArrayRacuni = (JSONArray) jObject.get("racuni");
                JTable t4 = TableHelper.tabelaRacuni(jArrayRacuni);
                JScrollPane sp4 = new JScrollPane(t4);
                sp4.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp4);
                tmpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                
                JSONArray jArrayTransakcije = (JSONArray) jObject.get("transakcije");
                JTable t5 = TableHelper.tabelaTransakcije(jArrayTransakcije);
                JScrollPane sp5 = new JScrollPane(t5);
                sp5.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp5);
                
                panel.add(new JScrollPane(tmpPanel));
                panel.setPreferredSize(new Dimension(660, 500));
            }
            else {
                JLabel label = new JLabel("Došlo je do greške!");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.TOP);
                panel.add(label);
            }
           
            panel.repaint();
            panel.revalidate();
        });
        bigPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(panel);
        
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    private void dohvatanjeRazlikeUPodacima() {
        bigPanel.removeAll();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        
        JPanel panel = new JPanel(new GridLayout(1, 1));
        panel.setMinimumSize(new Dimension(660, 500));
        panel.setMaximumSize(new Dimension(660, 500));
        
        JButton b = new JButton("Dohvati");
        b.addActionListener(al->{
            JSONObject jObject = HTCL.razlikaUPodacima();
            
            panel.removeAll();
            if(jObject != null){
                JPanel tmpPanel = new JPanel();
                tmpPanel.setLayout(new BoxLayout(tmpPanel, BoxLayout.Y_AXIS));
                
                JSONArray jArrayMesta = (JSONArray) jObject.get("mesta");
                JTable t1 = TableHelper.tabelaMesta(jArrayMesta);
                JScrollPane sp1 = new JScrollPane(t1);
                sp1.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp1);
                tmpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                
                JSONArray jArrayFilijale = (JSONArray) jObject.get("filijale");
                JTable t2 = TableHelper.tabelaFilijale(jArrayFilijale);
                JScrollPane sp2 = new JScrollPane(t2);
                sp2.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp2);
                tmpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                
                JSONArray jArrayKomitenti = (JSONArray) jObject.get("komitenti");
                JTable t3 = TableHelper.tabelaKomitenti(jArrayKomitenti);
                JScrollPane sp3 = new JScrollPane(t3);
                sp3.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp3);
                tmpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                
                JSONArray jArrayRacuni = (JSONArray) jObject.get("racuni");
                JTable t4 = TableHelper.tabelaRacuni(jArrayRacuni);
                JScrollPane sp4 = new JScrollPane(t4);
                sp4.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp4);
                tmpPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                
                JSONArray jArrayTransakcije = (JSONArray) jObject.get("transakcije");
                JTable t5 = TableHelper.tabelaTransakcije(jArrayTransakcije);
                JScrollPane sp5 = new JScrollPane(t5);
                sp5.setPreferredSize(new Dimension(630, 200));
                tmpPanel.add(sp5);
                
                panel.add(new JScrollPane(tmpPanel));
                panel.setPreferredSize(new Dimension(660, 500));
            }
            else {
                JLabel label = new JLabel("Došlo je do greške!");
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.TOP);
                panel.add(label);
            }
           
            panel.repaint();
            panel.revalidate();
        });
        bigPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        b.setAlignmentX(CENTER_ALIGNMENT);
        bigPanel.add(b);
        bigPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bigPanel.add(panel);
        
        bigPanel.repaint();
        bigPanel.revalidate();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == c1) {
            switch(c1.getSelectedIndex()) {
                case 0: kreiranjeMestaForma(); break;
                case 1: kreiranjeFilijale(); break;
                case 2: kreiranjeKomitenta(); break;
                case 3: promenaSedistaKomitenta(); break;
                case 4: kreiranjeRacuna(); break;
                case 5: gasenjeRacuna(); break;
                case 6: prenosNovca(); break;
                case 7: uplataNovca(); break;
                case 8: isplataNovca(); break;
                case 9: dohvatanjeSvihMesta(); break;
                case 10: dohvatanjeSvihFilijala(); break;
                case 11: dohvatanjeSvihKomitenata(); break;
                case 12: dohvatanjeSvihRacunaZaKomitenata(); break;
                case 13: dohvatanjeSvihTransakcijaZaRacun(); break;
                case 14: dohvatanjeSvihPodataka(); break;
                case 15: dohvatanjeRazlikeUPodacima(); break;
            }
        }
    }

    
    public static void main(String[] args) {
        new Main();
    }

    
}
