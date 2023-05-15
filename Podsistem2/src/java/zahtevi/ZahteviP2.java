/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zahtevi;

import podsistem2.Komitent;
import podsistem2.Transakcija;
import podsistem2.Racun;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.jms.Queue;
import main.Main;
import org.json.simple.JSONArray;
/**
 *
 * @author Jana
 */
public class ZahteviP2 {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("P2PU");
    private EntityManager em = emf.createEntityManager();
    
    public void otvaranjeRacuna(JSONObject jObject,  JMSConsumer consumer){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMssg = context.createTextMessage();
                
        String kom = (String)jObject.get("komitent");
        Komitent komitent;
        try {
            komitent = em.createNamedQuery("Komitent.findByNaziv", Komitent.class).setParameter("naziv", kom).getSingleResult();
        }
        catch (Exception e){
            komitent = null;
        }
        if (komitent == null) {
            try {
                textMssg .setText("2");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        double dozvoljeniMins = (Double)jObject.get("dozvoljeniMinus");
        
        String mesto = (String)jObject.get("mesto");
        
        TextMessage textMsg = context.createTextMessage();
        JSONObject jObj = new JSONObject();
        jObj.put("number", 17);
        jObj.put("mesto", mesto);
        try {
            textMsg.setText(jObj.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg);
        
        int idMes = 0;
        
        try{
            Message msg = consumer.receive();
            System.out.println("Received msg");
            if (msg instanceof TextMessage) {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg).getText();
                System.out.println(messageText);
                JSONObject jobj = (JSONObject)parser.parse(messageText);
                long number = (long)jobj.get("number");
                switch((int)number){
                    case 17: 
                        idMes = (int)(long)jobj.get("mesto");
                        break;
                    }
                }
        } catch (JMSException | ParseException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (idMes == -1) {
            try {
                textMssg .setText("3");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        Racun racun = new Racun();
        racun.setStanje(0);
        racun.setStatus("Aktivan");
        racun.setDozvoljeniMinus((double)dozvoljeniMins);
        racun.setDatumVremeOtvaranja(new Date());
        racun.setBrojTransakcija(0);
        racun.setIDKom(komitent);
        racun.setIDMes((int)idMes);
        em.getTransaction().begin();
        em.persist(racun);
        em.getTransaction().commit();
        
        try {
            textMssg .setText("1");
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueCS, textMssg );
        try {
            System.out.println("Sent: " + textMssg .getText());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        context.close();
    }
        
    public void ugasiRacun(JSONObject jObject){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMssg = context.createTextMessage();
        
        int idRac = (int)(long)jObject.get("racun");
        Racun racun = null;
        try {
            racun = em.createNamedQuery("Racun.findByIDRac", Racun.class).setParameter("iDRac", (int)idRac).getSingleResult();
        } catch(Exception e){
            racun = null;
        }
        if (racun == null){
            try {
                textMssg .setText("2");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
        }
        else {
        
            em.getTransaction().begin();
            racun.setStatus("Ugasen");
            em.getTransaction().commit();
            
            try {
                textMssg .setText("1");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
        }
        
    }
    
    public void prenesiNovac(JSONObject jObject){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMssg = context.createTextMessage();
        
        int idRacSa = (int)(long)jObject.get("racunSa");
        Racun racunSa;
        try {
            racunSa = em.createNamedQuery("Racun.findByIDRac", Racun.class).setParameter("iDRac", idRacSa).getSingleResult();
        } catch (Exception e){
            racunSa = null;
        }
        if (racunSa == null){
            try {
                textMssg .setText("2");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        int idRacKa = (int)(long)jObject.get("racunKa");
        Racun racunKa;
        try {
            racunKa = em.createNamedQuery("Racun.findByIDRac", Racun.class).setParameter("iDRac", (int)idRacKa).getSingleResult();
        } catch (Exception e){
            racunKa = null;
        }
        if (racunKa == null){
            try {
                textMssg .setText("3");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        double iznos = (Double)jObject.get("iznos");
        
        em.getTransaction().begin();
        if (!racunSa.getStatus().equals("Ugasen")) {
            Transakcija transakcija1 = new Transakcija();
            transakcija1.setIDRac(racunSa);
            Integer redniBrojTransakcije1 = em.createQuery("SELECT r.brojTransakcija + 1 FROM Racun r where R.iDRac = :idRac", Integer.class).setParameter("idRac", idRacSa).getSingleResult();
            transakcija1.setRedniBroj(redniBrojTransakcije1);
            transakcija1.setIznos(iznos);
            transakcija1.setDatumVreme(new Date());
            transakcija1.setTip("Isplata");
            em.persist(transakcija1);
            racunSa.setStanje(racunSa.getStanje() - iznos);
            if (racunSa.getStanje() < -racunSa.getDozvoljeniMinus()) racunSa.setStatus("Blokiran");
            racunSa.setBrojTransakcija(racunSa.getBrojTransakcija() + 1);
            
            Transakcija transakcija2 = new Transakcija();
            transakcija2.setIDRac(racunKa);
            Integer redniBrojTransakcije2 = em.createQuery("SELECT r.brojTransakcija + 1 FROM Racun r where R.iDRac = :idRac", Integer.class).setParameter("idRac", idRacKa).getSingleResult();
            transakcija2.setRedniBroj(redniBrojTransakcije2);
            transakcija2.setIznos(iznos);
            transakcija2.setDatumVreme(new Date());
            transakcija2.setTip("Uplata");
            em.persist(transakcija2);
            racunKa.setStanje(racunKa.getStanje() + iznos);
            if (racunKa.getStatus().equals("Blokiran") && racunKa.getStanje() >= -racunKa.getDozvoljeniMinus()) racunKa.setStatus("Aktivan");
            
            racunKa.setBrojTransakcija(racunKa.getBrojTransakcija() + 1); 
            try {
                textMssg .setText("1");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else {
            try {
                textMssg .setText("4");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        context.close();
        em.getTransaction().commit();
    }
    
    public void uplataNovca(JSONObject jObject, JMSConsumer consumer){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMssg = context.createTextMessage();
                
        long idRac = (Long)jObject.get("racun");
        Racun racun;
        try {
            racun = em.createNamedQuery("Racun.findByIDRac", Racun.class).setParameter("iDRac", (int)idRac).getSingleResult();
        } catch(Exception e){
            racun = null;
        }
        if (racun == null) {
            try {
                textMssg .setText("2");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        double iznos = (Double)jObject.get("iznos");
        
        String filijala = (String)jObject.get("filijala");
        TextMessage textMsg = context.createTextMessage();
        JSONObject jObj = new JSONObject();
        jObj.put("number", 18);
        jObj.put("filijala", filijala);
        try {
            textMsg.setText(jObj.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg);
        
        int idFil = 0;

        try{
            Message msg = consumer.receive();
            System.out.println("Received msg");
            if (msg instanceof TextMessage) {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg).getText();
                System.out.println(messageText);
                JSONObject jobj = (JSONObject)parser.parse(messageText);
                long number = (Long)jobj.get("number");
                switch((int)number){
                    case 18: 
                        idFil = (int)(long)jobj.get("filijala");
                        break;
                    }
                }
        } catch (JMSException | ParseException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        if (idFil == -1) {
            try {
                textMssg .setText("3");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        em.getTransaction().begin();
        
        Transakcija transakcija = new Transakcija();
        transakcija.setIDRac(racun);
        transakcija.setIznos(iznos);
        transakcija.setIdFil((int)idFil);
        transakcija.setRedniBroj(racun.getBrojTransakcija() + 1);
        transakcija.setDatumVreme(new Date());
        transakcija.setTip("Uplata");
        em.persist(transakcija);
        
        racun.setStanje(racun.getStanje() + iznos);
        if (racun.getStatus().equals("Blokiran") && racun.getStanje() >= -racun.getDozvoljeniMinus()) racun.setStatus("Aktivan");
        racun.setBrojTransakcija(racun.getBrojTransakcija() + 1);
        
        em.getTransaction().commit();
        
        try {
                textMssg .setText("1");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
        context.close();
    }
    
    public void isplataNovca(JSONObject jObject, JMSConsumer consumer){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMssg = context.createTextMessage();
         
        long idRac = (Long)jObject.get("racun");
        Racun racun;
        try {
            racun = em.createNamedQuery("Racun.findByIDRac", Racun.class).setParameter("iDRac", (int)idRac).getSingleResult();
        } catch(Exception e){
            racun = null;
        }
        if (racun == null) {
            try {
                textMssg .setText("2");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg);
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        if (racun.getStatus().equals("Ugasen") || racun.getStatus().equals("Blokiran")) {
            try {
                textMssg .setText("3");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg);
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        String filijala = (String)jObject.get("filijala");
        TextMessage textMsg = context.createTextMessage();
        JSONObject jObj = new JSONObject();
        jObj.put("number", 18);
        jObj.put("filijala", filijala);
        try {
            textMsg.setText(jObj.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg);
        
        long idFil = 0;
    
        try{
            Message msg = consumer.receive();
            System.out.println("Received msg");
            if (msg instanceof TextMessage) {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg).getText();
                System.out.println(messageText);
                JSONObject jobj = (JSONObject)parser.parse(messageText);
                long number = (Long)jobj.get("number");
                switch((int)number){
                    case 18: 
                        idFil = (Long)jobj.get("filijala");
                        break;
                    }
                }
        } catch (JMSException | ParseException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (idFil == -1) {
            try {
                textMssg .setText("4");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg );
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        double iznos = (Double)jObject.get("iznos");
        
        em.getTransaction().begin();
        
        Transakcija transakcija = new Transakcija();
        transakcija.setIDRac(racun);
        transakcija.setIznos(iznos);
        transakcija.setIdFil((int)idFil);
        transakcija.setRedniBroj(racun.getBrojTransakcija() + 1);
        transakcija.setDatumVreme(new Date());
        transakcija.setTip("Isplata");
        em.persist(transakcija);
        
        racun.setStanje(racun.getStanje() - iznos);
        if (racun.getStanje() < -racun.getDozvoljeniMinus()) racun.setStatus("Blokiran");
        racun.setBrojTransakcija(racun.getBrojTransakcija() + 1);
        
        em.getTransaction().commit();
        
        try {
                textMssg .setText("1");
            } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
             producer.send(Main.queueCS, textMssg);
            try {
                System.out.println("Sent: " + textMssg .getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
        
    }

    public void sviRacuniKomitenta(JSONObject jObject) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMsg = context.createTextMessage();
        
        JSONArray jArray = new JSONArray();
        
        String nazivKomitenta = (String)jObject.get("naziv");
        Komitent komitent;
        try {
            komitent = em.createNamedQuery("Komitent.findByNaziv", Komitent.class).setParameter("naziv", nazivKomitenta).getSingleResult();
        }
        catch (Exception e){
            komitent = null;
        }
        if (komitent == null) {
            try {
                textMsg.setText("2");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        List<Racun> racuni = em.createQuery("SELECT r FROM Racun r WHERE r.iDKom = :idKom", Racun.class).setParameter("idKom", komitent).getResultList();
        for (Racun racun : racuni) {
            JSONObject jObj = new JSONObject();
            jObj.put("idRac", racun.getIDRac());
            jObj.put("stanje", racun.getStanje());
            jObj.put("status", racun.getStatus());
            jObj.put("dozvoljeniMinus", racun.getDozvoljeniMinus());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
            String formatDate = sdf.format(racun.getDatumVremeOtvaranja());
            jObj.put("vremeDatum", formatDate);
            jObj.put("brTransakcija", racun.getBrojTransakcija());
            jObj.put("idKom", racun.getIDKom().getIDKom());
            jObj.put("idMes", racun.getIDMes());
            jArray.add(jObj);
        }
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueCS, textMsg);
        System.out.println("Sent: " + jArray.toString());
        context.close();
    }

    public void sveTransakcijeRacuna(JSONObject jObject) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMsg = context.createTextMessage();
        
        long idRac = (Long)jObject.get("racun");
        
        Racun racun;
        try {
            racun = em.createNamedQuery("Racun.findByIDRac", Racun.class).setParameter("iDRac", (int)idRac).getSingleResult();
        }
        catch (Exception e){
            racun = null;
        }
        if (racun == null) {
            try {
                textMsg.setText("2");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
            return;
        }
        
        List<Transakcija> transakcije = em.createQuery("SELECT t FROM Transakcija t WHERE t.iDRac = :iDRac", Transakcija.class).setParameter("iDRac", racun).getResultList();
     
        JSONArray jArray = new JSONArray();
       
        for (Transakcija transakcija : transakcije) {
            JSONObject jObj = new JSONObject();
            jObj.put("idTra", transakcija.getIDTra());
            jObj.put("racun", transakcija.getIDRac().getIDRac());
            jObj.put("redniBroj", transakcija.getRedniBroj());
            jObj.put("iznos", transakcija.getIznos());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
            String formatDate = sdf.format(transakcija.getDatumVreme());
            jObj.put("vremeDatum", formatDate);
            jObj.put("filijala", transakcija.getIdFil());
            jObj.put("tip", transakcija.getTip());
            jArray.add(jObj);
        }
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueCS, textMsg);
        System.out.println("Sent: " + jArray.toString());
        context.close();
    }

    public void sviRacuniKaPodsistemu3(Queue queue) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Racun> racuni = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
        JSONArray jArray = new JSONArray();
       
        for (Racun racun : racuni) {
            JSONObject jObject = new JSONObject();
            jObject.put("idRac", racun.getIDRac());
            jObject.put("stanje", racun.getStanje());
            jObject.put("status", racun.getStatus());
            jObject.put("dozvoljeniMinus", racun.getDozvoljeniMinus());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
            String formatDate = sdf.format(racun.getDatumVremeOtvaranja());
            jObject.put("vremeDatum", formatDate);
            jObject.put("brTransakcija", racun.getBrojTransakcija());
            jObject.put("idKom", racun.getIDKom().getIDKom());
            jObject.put("idMes", racun.getIDMes());
            jArray.add(jObject);
        }
        
        TextMessage textMsg = context.createTextMessage();
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queue, textMsg);
        context.close();
    }

    public void sveTransakcijeKaPodsistemu3(Queue queue) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
        JSONArray jArray = new JSONArray();
       
        for (Transakcija transakcija : transakcije) {
            JSONObject jObject = new JSONObject();
            jObject.put("idTra", transakcija.getIDTra());
            jObject.put("racun", transakcija.getIDRac().getIDRac());
            jObject.put("redniBroj", transakcija.getRedniBroj());
            jObject.put("iznos", transakcija.getIznos());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
            String formatDate = sdf.format(transakcija.getDatumVreme());
            jObject.put("vremeDatum", formatDate);
            jObject.put("filijala", transakcija.getIdFil());
            jObject.put("tip", transakcija.getTip());
            jArray.add(jObject);
        }
        
        TextMessage textMsg = context.createTextMessage();
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP2.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queue, textMsg);
        context.close();
    }

    public void dodajKomitenta(JSONObject jObject) {
        String naziv = (String)jObject.get("naziv");
        String adresa = (String)jObject.get("adresa");
        int idMes = (int)(long)jObject.get("mesto");
        Komitent komitent = new Komitent();
        komitent.setNaziv(naziv);
        komitent.setAdresa(adresa);
        komitent.setIDMes(idMes);
        em.getTransaction().begin();
        em.persist(komitent);
        em.getTransaction().commit();
    }
}
