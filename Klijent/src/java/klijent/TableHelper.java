/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Jana
 */
public class TableHelper {
    
    public static JTable tabelaMesta(JSONArray jArray){
        String[] columnNames = { "ID mesta", "Postanski broj", "Naziv"};
        String[][] data = new String[jArray.size()][columnNames.length];
                
        for(int i = 0; i < jArray.size(); i++){
            JSONObject jObject = (JSONObject) jArray.get(i);
            int idMes = (int)(long)jObject.get("idMes");
            int posBroj = (int)(long)jObject.get("posBroj");
            String naziv = (String)jObject.get("naziv");
            data[i][0] = Integer.toString(idMes);
            data[i][1] = Integer.toString(posBroj);
            data[i][2] = naziv;
        }

        JTable table = new JTable(data, columnNames);
        table.getTableHeader().setReorderingAllowed(false);
        
        return table;
    }
    
    public static JTable tabelaFilijale(JSONArray jArray){
        String[] columnNames = { "ID filijale",  "Naziv", "Adresa", "Mesto"};
        String[][] data = new String[jArray.size()][columnNames.length];
                
        for(int i = 0; i < jArray.size(); i++){
            JSONObject jObject = (JSONObject) jArray.get(i);
            int idFil = (int)(long)jObject.get("idFil");
            String naziv = (String)jObject.get("naziv");
            String adresa = (String)jObject.get("adresa");
            int mesto = (int)(long)jObject.get("mesto");
            data[i][0] = Integer.toString(idFil);
            data[i][1] = naziv;
            data[i][2] = adresa;
            data[i][3] = Integer.toString(mesto);
        }

        JTable table = new JTable(data, columnNames);
        table.getTableHeader().setReorderingAllowed(false);
        
        return table;
    }
    
    public static JTable tabelaKomitenti(JSONArray jArray){
        String[] columnNames = { "ID komitenta",  "Naziv", "Adresa", "Mesto"};
        String[][] data = new String[jArray.size()][columnNames.length];
                
        for(int i = 0; i < jArray.size(); i++){
            JSONObject jObject = (JSONObject) jArray.get(i);
            int idFil = (int)(long)jObject.get("idKom");
            String naziv = (String)jObject.get("naziv");
            String adresa = (String)jObject.get("adresa");
            int mesto = (int)(long)jObject.get("mesto");
            data[i][0] = Integer.toString(idFil);
            data[i][1] = naziv;
            data[i][2] = adresa;
            data[i][3] = Integer.toString(mesto);
        }

        JTable table = new JTable(data, columnNames);
        table.getTableHeader().setReorderingAllowed(false);
        
        return table;
    }
    
    public static JTable tabelaRacuni(JSONArray jArray){
        String[] columnNames = { "ID računa",  "Stanje", "Status", "Dozvoljeni minus", "Datum vreme", "Broj transakcija", "ID komitenta", "ID mesta"};
        String[][] data = new String[jArray.size()][columnNames.length];
        
        for(int i = 0; i < jArray.size(); i++){
            JSONObject jObject = (JSONObject) jArray.get(i);
            int idRac = (int)(long)jObject.get("idRac");
            double stanje = (double)jObject.get("stanje");
            String status = (String)jObject.get("status");
            double dozvoljeniMinus = (double)jObject.get("dozvoljeniMinus");
            String vremeDatum = (String)jObject.get("vremeDatum");
            int brTransakcija = (int)(long)jObject.get("brTransakcija");
            int idKom = (int)(long)jObject.get("idKom");
            int idMes = (int)(long)jObject.get("idMes");
            data[i][0] = Integer.toString(idRac);
            data[i][1] = Double.toString(stanje);
            data[i][2] = status;
            data[i][3] = Double.toString(dozvoljeniMinus);
            data[i][4] = vremeDatum;
            data[i][5] = Integer.toString(brTransakcija);
            data[i][6] = Integer.toString(idKom);
            data[i][7] = Integer.toString(idMes);
        }

        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(int i = 0; i < columnNames.length; i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(80);
        }
        table.getColumnModel().getColumn(4).setPreferredWidth(180);
        table.getTableHeader().setReorderingAllowed(false);
        
        return table;
    }
    
    public static JTable tabelaTransakcije(JSONArray jArray){
        String[] columnNames = { "ID transakcije",  "Račun", "Datum vreme", "Redni broj", "Iznos", "Filijala", "Tip"};
        String[][] data = new String[jArray.size()][columnNames.length];

        for(int i = 0; i < jArray.size(); i++){
            JSONObject jObject = (JSONObject) jArray.get(i);
            int idTra = (int)(long)jObject.get("idTra");
            int racun = (int)(long)jObject.get("racun");
            String vremeDatum = (String)jObject.get("vremeDatum");
            int redniBroj = (int)(long)jObject.get("redniBroj");
            double iznos = (double)jObject.get("iznos");
            Long filijala = (Long)jObject.get("filijala");
            String tip = (String)jObject.get("tip");
            data[i][0] = Integer.toString(idTra);
            data[i][1] = Integer.toString(racun);
            data[i][2] = vremeDatum;
            data[i][3] = Integer.toString(redniBroj);
            data[i][4] = Double.toString(iznos);
            if (filijala != null){
                data[i][5] = Long.toString(filijala);
            }
            data[i][6] = tip;
        }

        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for(int i = 0; i < columnNames.length; i++){
            table.getColumnModel().getColumn(i).setPreferredWidth(80);
        }
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getTableHeader().setReorderingAllowed(false);
                    
        return table;
    }
}
