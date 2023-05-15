/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

/**
 *
 * @author Jana
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HTCL {
    
   
    public static boolean kreirajMesto(int postanskiBroj, String naziv){
        try {
            String url = "http://localhost:8080/CentralniServer/app/kreirajMesto";
            
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");
            httpClient.setRequestMethod("POST");
            
            JSONObject requestObject = new JSONObject();
            
            requestObject.put("postanskiBroj", postanskiBroj);
            requestObject.put("naziv",naziv);
            
            OutputStream os = httpClient.getOutputStream();
            os.write(requestObject.toString().getBytes("UTF-8"));
            os.close();
            
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            if (responseCode==200){ //zahtev se obradjuje
                System.out.println("OK");
                httpClient.disconnect();
                return true;
            }
            else{
                System.out.println("NOT OK");
                httpClient.disconnect();
                return false;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static int kreirajFilijalu(String naziv, String adresa, String mesto){
        try {
            String url = "http://localhost:8080/CentralniServer/app/kreirajFilijalu";
            
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");
            httpClient.setRequestMethod("POST");
            
            JSONObject requestObject = new JSONObject();
            
            requestObject.put("naziv", naziv);
            requestObject.put("adresa", adresa);
            requestObject.put("mesto", mesto);
            
            OutputStream os = httpClient.getOutputStream();
            os.write(requestObject.toString().getBytes("UTF-8"));
            os.close();
            
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            if (responseCode == 200){ //zahtev se obradjuje
                System.out.println("OK");
                httpClient.disconnect();
                return 1;
            }
            else if (responseCode == 400){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 2;
            }
            else  {
                System.out.println("NOT OK");
                httpClient.disconnect();
                return -1;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    
    public static int kreirajKomitenta(String naziv, String adresa, String mesto){
        try {
            String url = "http://localhost:8080/CentralniServer/app/kreirajKomitenta";
            
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");
            httpClient.setRequestMethod("POST");
            
            JSONObject requestObject = new JSONObject();
            
            requestObject.put("naziv", naziv);
            requestObject.put("adresa", adresa);
            requestObject.put("mesto", mesto);
            
            OutputStream os = httpClient.getOutputStream();
            os.write(requestObject.toString().getBytes("UTF-8"));
            os.close();
            
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            if (responseCode==200){ //zahtev se obradjuje
                System.out.println("OK");
                httpClient.disconnect();
                return 1;
            }
            else if (responseCode == 400){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 2;
            }
            else  {
                System.out.println("NOT OK");
                httpClient.disconnect();
                return -1;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public static int promenaSedistaKomitenta(String naziv, String mesto){
        try {
            String url = "http://localhost:8080/CentralniServer/app/promenaSedista";
            
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");
            httpClient.setRequestMethod("POST");
            
            JSONObject requestObject = new JSONObject();
            
            requestObject.put("naziv", naziv);
            requestObject.put("mesto", mesto);
            
            OutputStream os = httpClient.getOutputStream();
            os.write(requestObject.toString().getBytes("UTF-8"));
            os.close();
            
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            if (responseCode==200){ //zahtev se obradjuje
                System.out.println("OK");
                httpClient.disconnect();
                return 1;
            }
            else if (responseCode == 400){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 2;
            }
            else {
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 3;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static int otvaranjeRacuna(String komitent, String mesto, double minus){
        try {
            String url = "http://localhost:8080/CentralniServer/app/otvaranjeRacuna";
            
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");
            httpClient.setRequestMethod("POST");
            
            JSONObject requestObject = new JSONObject();
            
            requestObject.put("komitent", komitent);
            requestObject.put("mesto", mesto);
            requestObject.put("dozvoljeniMinus", minus);
            
            OutputStream os = httpClient.getOutputStream();
            os.write(requestObject.toString().getBytes("UTF-8"));
            os.close();
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            if (responseCode == 200){ //zahtev se obradjuje
                System.out.println("OK");
                httpClient.disconnect();
                return 1;
            }
            else if (responseCode == 400){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 2;
            }
            else {
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 3;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static int gasenjeRacuna(int racun){
        try {
            String url = "http://localhost:8080/CentralniServer/app/ugasiRacun";
            
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");
            httpClient.setRequestMethod("POST");
            
            JSONObject requestObject = new JSONObject();
            
            requestObject.put("racun", racun);
            
            OutputStream os = httpClient.getOutputStream();
            os.write(requestObject.toString().getBytes("UTF-8"));
            os.close();
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            if (responseCode == 200){ //zahtev se obradjuje
                System.out.println("OK");
                httpClient.disconnect();
                return 1;
            }
            else{
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 2;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static int prenosNovca(int racunSa, int racunKa, double iznos){
        try {
            String url = "http://localhost:8080/CentralniServer/app/prenos";
            
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");
            httpClient.setRequestMethod("POST");
            
            JSONObject requestObject = new JSONObject();
            
            requestObject.put("racunSa", racunSa);
            requestObject.put("racunKa", racunKa);
            requestObject.put("iznos", iznos);
            
            OutputStream os = httpClient.getOutputStream();
            os.write(requestObject.toString().getBytes("UTF-8"));
            os.close();
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
            if (responseCode == 200){ //zahtev se obradjuje
                System.out.println("OK");
                httpClient.disconnect();
                return 1;
            }
            else if (responseCode == 400){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 2;
            }
            else if (responseCode == 406){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 3;
            }
            else {
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 4;
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static int uplata(int racun, String filijala, double iznos){
        try {
            String url = "http://localhost:8080/CentralniServer/app/uplata";
            
            
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoInput(true);
            httpClient.setDoOutput(true);
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/json");
            httpClient.setRequestMethod("POST");
            
            JSONObject requestObject = new JSONObject();
            
            requestObject.put("racun", racun);
            requestObject.put("iznos", iznos);
            requestObject.put("filijala", filijala);
            
            OutputStream os = httpClient.getOutputStream();
            os.write(requestObject.toString().getBytes("UTF-8"));
            os.close();
            
            int responseCode = httpClient.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            
        
            if (responseCode == 200){ //zahtev se obradjuje
                System.out.println("OK");
                httpClient.disconnect();
                return 1;
            }
            else if (responseCode == 400){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 2;
            }
            else {
                System.out.println("NOT OK");
                httpClient.disconnect();
                return 3;
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public static int isplata(int racun, String filijala, double iznos){
            try {
                String url = "http://localhost:8080/CentralniServer/app/isplata";

                HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

                httpClient.setDoInput(true);
                httpClient.setDoOutput(true);
                httpClient.setRequestProperty("Content-Type", "application/json");
                httpClient.setRequestProperty("Accept", "application/json");
                httpClient.setRequestMethod("POST");

                JSONObject requestObject = new JSONObject();

                requestObject.put("racun", racun);
                requestObject.put("iznos", iznos);
                requestObject.put("filijala", filijala);

                OutputStream os = httpClient.getOutputStream();
                os.write(requestObject.toString().getBytes("UTF-8"));
                os.close();

                int responseCode = httpClient.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                 if (responseCode == 200){ //zahtev se obradjuje
                    System.out.println("OK");
                    httpClient.disconnect();
                    return 1;
                }
                else if (responseCode == 400){
                    System.out.println("NOT OK");
                    httpClient.disconnect();
                    return 2;
                }
                else if (responseCode == 406) {
                    System.out.println("NOT OK");
                    httpClient.disconnect();
                    return 3;
                }
                else {
                    System.out.println("NOT OK");
                    httpClient.disconnect();
                    return 4;
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ProtocolException ex) {
                Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return -1;
        }
    
    public static JSONArray svaMesta() {

        try {
            String url =  "http://localhost:8080/CentralniServer/app/svaMesta";
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoOutput(true);
            httpClient.setRequestMethod("GET");
            
            int responseCode = httpClient.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        
            if (responseCode == 200){
                
                JSONParser parse = new JSONParser();

                JSONArray jArray;
                try (BufferedReader input = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
                    jArray = (JSONArray) parse.parse(input);
                }
                System.out.println(jArray.toString());
                httpClient.disconnect();
                return jArray;
            }
            
            httpClient.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static JSONArray sveFilijale() {

        try {
            String url =  "http://localhost:8080/CentralniServer/app/sveFilijale";
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoOutput(true);
            httpClient.setRequestMethod("GET");
            
            int responseCode = httpClient.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        
            if (responseCode == 200){
                
                JSONParser parse = new JSONParser();

                JSONArray jArray;
                try (BufferedReader input = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
                    jArray = (JSONArray) parse.parse(input);
                }
                System.out.println(jArray.toString());
                httpClient.disconnect();
                return jArray;
            }
            
            httpClient.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static JSONArray sviKomitenti() {

        try {
            String url =  "http://localhost:8080/CentralniServer/app/sviKomitenti";
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoOutput(true);
            httpClient.setRequestMethod("GET");
            
            int responseCode = httpClient.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        
            if (responseCode == 200){
                
                JSONParser parse = new JSONParser();

                JSONArray jArray;
                try (BufferedReader input = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
                    jArray = (JSONArray) parse.parse(input);
                }
                System.out.println(jArray.toString());
                httpClient.disconnect();
                return jArray;
            }
            
            httpClient.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static Pair<Integer, JSONArray> sviRacuniKomitenta(String naziv) {

        try {
            String url =  "http://localhost:8080/CentralniServer/app/sviRacuni";
            url += "?naziv=" + naziv;
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoOutput(true);
            httpClient.setRequestMethod("GET");
            
            int responseCode = httpClient.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        
            if (responseCode == 200){
                
                JSONParser parse = new JSONParser();

                JSONArray jArray;
                try (BufferedReader input = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
                    jArray = (JSONArray) parse.parse(input);
                }
                System.out.println(jArray.toString());
                httpClient.disconnect();
                return new Pair(1, jArray);
            }
            else if (responseCode == 400){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return new Pair(2, null);
            }
            
            httpClient.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new Pair(-1, null);
    }
   
    public static Pair<Integer, JSONArray> sveTransakcijeRacuna(int idRac) {

        try {
            String url =  "http://localhost:8080/CentralniServer/app/sveTransakcije";
            url += "?idRac=" + idRac;
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoOutput(true);
            httpClient.setRequestMethod("GET");
            
            int responseCode = httpClient.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        
            if (responseCode == 200){
                
                JSONParser parse = new JSONParser();

                JSONArray jArray;
                try (BufferedReader input = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
                    jArray = (JSONArray) parse.parse(input);
                }
                System.out.println(jArray.toString());
                httpClient.disconnect();
                return new Pair(1, jArray);
            }
            else if (responseCode == 400){
                System.out.println("NOT OK");
                httpClient.disconnect();
                return new Pair(2, null);
            }
            
            httpClient.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new Pair(-1, null);
    }
    
    public static JSONObject sviPodaci() {

        try {
            String url =  "http://localhost:8080/CentralniServer/app/sviPodaci";
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoOutput(true);
            httpClient.setRequestMethod("GET");
            
            int responseCode = httpClient.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        
            if (responseCode == 200){
                
                JSONParser parse = new JSONParser();

                JSONObject jObject;
                try (BufferedReader input = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
                    jObject = (JSONObject) parse.parse(input);
                }
                System.out.println(jObject.toString());
                httpClient.disconnect();
                return jObject;
            }
            
            httpClient.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static JSONObject razlikaUPodacima() {

        try {
            String url =  "http://localhost:8080/CentralniServer/app/razlika";
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            
            httpClient.setDoOutput(true);
            httpClient.setRequestMethod("GET");
            
            int responseCode = httpClient.getResponseCode();
            
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
        
            if (responseCode == 200){
                
                JSONParser parse = new JSONParser();

                JSONObject jObject;
                try (BufferedReader input = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
                    jObject = (JSONObject) parse.parse(input);
                }
                System.out.println(jObject.toString());
                httpClient.disconnect();
                return jObject;
            }
            
            httpClient.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HTCL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}