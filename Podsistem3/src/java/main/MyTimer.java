/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import zahtevi.ZahteviP3;
import podsistem3.Filijala;
import podsistem3.Komitent;
import podsistem3.Mesto;
import podsistem3.Racun;
import podsistem3.Transakcija;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static main.Main.connectionFactory;

/**
 *
 * @author Jana
 */
public class MyTimer extends TimerTask{
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
    private EntityManager em = emf.createEntityManager();
    
    private JMSConsumer consumer;
 
    public MyTimer(){
        JMSContext context = connectionFactory.createContext();
        this.consumer = context.createConsumer(Main.queueP3);
    }

    @Override
    public void run() {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Mesto").executeUpdate();
        em.getTransaction().commit();
       
        TextMessage textMsg1 = context.createTextMessage();
        JSONObject jObj1 = new JSONObject();
        jObj1.put("number", 19);
        try {
            textMsg1.setText(jObj1.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg1);
        
        Message msg1 = consumer.receive();
        System.out.println("Received msg");
        if (msg1 instanceof TextMessage) {
            try {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg1).getText();
                System.out.println(messageText);
                JSONArray jArray = (JSONArray)parser.parse(messageText);
                System.out.println("jArray:" + jArray);
                
                for (int i =0; i<jArray.size(); i++){
                    JSONObject jobj =  (JSONObject)jArray.get(i);
                    Mesto mesto = new Mesto();
                    mesto.setIDMes((int)(long)jobj.get("idMes"));
                    mesto.setNaziv((String)jobj.get("naziv"));
                    mesto.setPostanskiBroj((int)(long)jobj.get("posBroj"));
                    em.getTransaction().begin();
                    em.persist(mesto);
                    em.getTransaction().commit();
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Filijala").executeUpdate();
        em.getTransaction().commit();
        
        TextMessage textMsg2 = context.createTextMessage();
        JSONObject jObj2 = new JSONObject();
        jObj2.put("number", 20);
        try {
            textMsg2.setText(jObj2.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg2);
        
        Message msg2 = consumer.receive();
        System.out.println("Received msg");
        if (msg2 instanceof TextMessage) {
            try {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg2).getText();
                System.out.println(messageText);
                JSONArray jArray = (JSONArray)parser.parse(messageText);
                System.out.println("jArray:" + jArray);
                
                for (int i =0; i<jArray.size(); i++){
                    JSONObject jobj =  (JSONObject)jArray.get(i);
                    Filijala filijala = new Filijala();
                    filijala.setIDFil((int)(long)jobj.get("idFil"));
                    filijala.setIDMes((int)(long)jobj.get("mesto"));
                    filijala.setNaziv((String)jobj.get("naziv"));
                    filijala.setAdresa((String)jobj.get("adresa"));
                    em.getTransaction().begin();
                    em.persist(filijala);
                    em.getTransaction().commit();
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Komitent").executeUpdate();
        em.getTransaction().commit();
        
        TextMessage textMsg3 = context.createTextMessage();
        JSONObject jObj3 = new JSONObject();
        jObj3.put("number", 21);
        try {
            textMsg3.setText(jObj3.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg3);
        
        Message msg3 = consumer.receive();
        System.out.println("Received msg");
        if (msg3 instanceof TextMessage) {
            try {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg3).getText();
                System.out.println(messageText);
                JSONArray jArray = (JSONArray)parser.parse(messageText);
                System.out.println("jArray:" + jArray);
                
                for (int i =0; i<jArray.size(); i++){
                    JSONObject jobj =  (JSONObject)jArray.get(i);
                    Komitent komitent = new Komitent();
                    komitent.setIDKom((int)(long)jobj.get("idKom"));
                    komitent.setIDMes((int)(long)jobj.get("mesto"));
                    komitent.setNaziv((String)jobj.get("naziv"));
                    komitent.setAdresa((String)jobj.get("adresa"));
                    em.getTransaction().begin();
                    em.persist(komitent);
                    em.getTransaction().commit();
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Racun").executeUpdate();
        em.getTransaction().commit();
        
        
        TextMessage textMsg4 = context.createTextMessage();
        JSONObject jObj4 = new JSONObject();
        jObj4.put("number", 22);
        try {
            textMsg4.setText(jObj4.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP2, textMsg4);
        
        Message msg4 = consumer.receive();
        System.out.println("Received msg");
        if (msg4 instanceof TextMessage) {
            try {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg4).getText();
                System.out.println(messageText);
                JSONArray jArray = (JSONArray)parser.parse(messageText);
                System.out.println("jArray:" + jArray);
                
                for (int i =0; i<jArray.size(); i++){
                    JSONObject jobj =  (JSONObject)jArray.get(i);
                    Racun racun = new Racun();
                    racun.setIDRac((int)(long)jobj.get("idRac"));
                    racun.setBrojTransakcija((int)(long)jobj.get("brTransakcija"));
                    racun.setIDKom((int)(long)jobj.get("idKom"));
                    racun.setIDMes((int)(long)jobj.get("idMes"));
                    racun.setStanje((double)jobj.get("stanje"));
                    racun.setStatus((String)jobj.get("status"));
                    racun.setDozvoljeniMinus((double)jobj.get("dozvoljeniMinus"));
                    String date = (String)jobj.get("vremeDatum");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
                    try {
                        racun.setDatumVremeOtvaranja(sdf.parse(date));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    em.getTransaction().begin();
                    em.persist(racun);
                    em.getTransaction().commit();
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Transakcija").executeUpdate();
        em.getTransaction().commit();
        
        TextMessage textMsg5 = context.createTextMessage();
        JSONObject jObj5 = new JSONObject();
        jObj5.put("number", 23);
        try {
            textMsg5.setText(jObj5.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP2, textMsg5);
        
        Message msg5 = consumer.receive();
        System.out.println("Received msg");
        if (msg5 instanceof TextMessage) {
            try {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg5).getText();
                System.out.println(messageText);
                JSONArray jArray = (JSONArray)parser.parse(messageText);
                System.out.println("jArray:" + jArray);
                
                for (int i =0; i<jArray.size(); i++){
                    JSONObject jobj =  (JSONObject)jArray.get(i);
                    Transakcija transakcija = new Transakcija();
                    transakcija.setIDTra((int)(long)jobj.get("idTra"));
                    transakcija.setRedniBroj((int)(long)jobj.get("redniBroj"));
                    transakcija.setIDRac((int)(long)jobj.get("racun"));
                    if (jobj.get("filijala") != null) transakcija.setIDFil((int)(long)jobj.get("filijala"));
                    transakcija.setIznos((double)jobj.get("iznos"));
                    transakcija.setTip((String)jobj.get("tip"));
                    String date = (String)jobj.get("vremeDatum");
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
                    try {
                        transakcija.setDatumVreme(sdf.parse(date));
                    } catch (java.text.ParseException ex) {
                        Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    em.getTransaction().begin();
                    em.persist(transakcija);
                    em.getTransaction().commit();
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
}
