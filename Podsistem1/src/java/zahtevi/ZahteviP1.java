/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zahtevi;

import main.Main;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import podsistem1.Filijala;
import podsistem1.Komitent;
import podsistem1.Mesto;

/**
 *
 * @author Jana
 */
public class ZahteviP1 {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
    private EntityManager em = emf.createEntityManager();
    
    public void kreirajMesto(JSONObject jObject){
        String naziv = (String)jObject.get("naziv");
        long postanskiBroj = (Long)jObject.get("postanskiBroj");
        Mesto mesto = new Mesto();
        mesto.setNaziv(naziv);
        mesto.setPostanskiBroj((int)postanskiBroj);
        em.getTransaction().begin();
        em.persist(mesto);
        em.getTransaction().commit();
    }
    
    public void kreirajFilijalu(JSONObject jObject){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMsg = context.createTextMessage();
        
        String naziv = (String)jObject.get("naziv");
        String adresa = (String)jObject.get("adresa");
        String nazivMesta = (String)jObject.get("mesto");
        Mesto mesto;
        try{
            mesto = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", nazivMesta).getSingleResult();
        }
        catch(Exception e){
            mesto = null;
        }
        Filijala filijala = new Filijala();
        filijala.setNaziv(naziv);
        filijala.setAdresa(adresa);
        if (mesto != null) {
            filijala.setIDMes(mesto);
            try {
                textMsg.setText("1");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }

            em.getTransaction().begin();
            em.persist(filijala);
            em.getTransaction().commit();
            context.close();
        }
        else {
            try {
                textMsg.setText("2");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
        }
        
    }
    
    public void kreirajKomitenta(JSONObject jObject){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMsg = context.createTextMessage();
        
        String naziv = (String)jObject.get("naziv");
        String adresa = (String)jObject.get("adresa");
        String nazivMesta = (String)jObject.get("mesto");
        Mesto mesto;
        try{
            mesto = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", nazivMesta).getSingleResult();
        }
        catch(Exception e){
            mesto = null;
        }
        if (mesto != null) {
            Komitent komitent = new Komitent();
            komitent.setNaziv(naziv);
            komitent.setAdresa(adresa);
            komitent.setIDMes(mesto);
            try {
                textMsg.setText("1");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }

            em.getTransaction().begin();
            em.persist(komitent);
            em.getTransaction().commit();
            
            JSONObject jObj = new JSONObject();
            jObj.put("number", 3);
            jObj.put("naziv", naziv);
            jObj.put("adresa", adresa);
            jObj.put("mesto", mesto.getIDMes());
            TextMessage txtMssg = context.createTextMessage();
            try {
                txtMssg.setText(jObj.toString());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueP2, txtMssg);
            context.close();
        }
        else {
            try {
                textMsg.setText("2");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
        }
    }
    
    public void promenaSedistaKomitenta(JSONObject jObject){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        TextMessage textMsg = context.createTextMessage();
        
        String naziv = (String)jObject.get("naziv");
        String nazivMesta = (String)jObject.get("mesto");
        Komitent komitent;
        try {
            komitent = em.createNamedQuery("Komitent.findByNaziv", Komitent.class).setParameter("naziv", naziv).getSingleResult();
        }
        catch (Exception e){
            komitent = null;
        }
        Mesto mesto;
        try{
            mesto = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", nazivMesta).getSingleResult();
        }
        catch(Exception e){
            mesto = null;
        }
        if (komitent != null && mesto != null) {
            em.getTransaction().begin();
            komitent.setIDMes(mesto);
            em.getTransaction().commit();
            try {
                textMsg.setText("1");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
        }
        else if (komitent == null) {
            try {
                textMsg.setText("2");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
        }
        else if (mesto == null){
            try {
                textMsg.setText("3");
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            producer.send(Main.queueCS, textMsg);
            try {
                System.out.println("Sent: " + textMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
            }
            context.close();
        }
    }
    
    public void svaMesta(){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        JSONArray jArray = new JSONArray();
       
        for (Mesto mesto : mesta) {
            JSONObject jObj = new JSONObject();
            jObj.put("idMes", mesto.getIDMes());
            jObj.put("posBroj", mesto.getPostanskiBroj());
            jObj.put("naziv", mesto.getNaziv());
            jArray.add(jObj);
        }
        
        System.out.println(jArray.toString());
        TextMessage textMsg = context.createTextMessage();
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
        }
       producer.send(Main.queueCS, textMsg);
       System.out.println("Sent: " + textMsg);
       context.close();
    }
    
    public void sveFilijale(){
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
        JSONArray jArray = new JSONArray();
       
        for (Filijala fil : filijale) {
            JSONObject jObj = new JSONObject();
            jObj.put("idFil", fil.getIDFil());
            jObj.put("naziv", fil.getNaziv());
            jObj.put("adresa", fil.getAdresa());
            jObj.put("mesto", fil.getIDMes().getIDMes());
            jArray.add(jObj);
        }
        
        System.out.println(jArray.toString());
        TextMessage textMsg = context.createTextMessage();
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
        }
       producer.send(Main.queueCS, textMsg);
       System.out.println("Sent: " + textMsg);
       context.close();
    }
    
    public void sviKomitenti(){
        
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Komitent> komitenti = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
        JSONArray jArray = new JSONArray();
       
        for (Komitent komitent : komitenti) {
            JSONObject jObject = new JSONObject();
            jObject.put("idKom", komitent.getIDKom());
            jObject.put("adresa", komitent.getAdresa());
            jObject.put("naziv", komitent.getNaziv());
            jObject.put("mesto", komitent.getIDMes().getIDMes());
            jArray.add(jObject);
        }
        
        System.out.println(jArray.toString());
        TextMessage textMsg = context.createTextMessage();
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
        }
       producer.send(Main.queueCS, textMsg);
       System.out.println("Sent: " + textMsg);
       context.close();
        
    }
    
    public void idMesta(JSONObject jObject) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        String nazivMesta = (String)jObject.get("mesto");
        Mesto mesto;
        try{
            mesto = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", nazivMesta).getSingleResult();
        }
        catch(Exception e){
            mesto = null;
        }
        
        TextMessage textMsg = context.createTextMessage();
        JSONObject jObj = new JSONObject();
        jObj.put("number", 17);
        if (mesto != null) jObj.put("mesto", mesto.getIDMes());
        else jObj.put("mesto", -1);
        try {
            textMsg.setText(jObj.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP2, textMsg);
        context.close();
    }
    
    public void idFilijale(JSONObject jObject) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        String nazivFilijale = (String)jObject.get("filijala");
        Filijala filijala;
        try {
           filijala = em.createNamedQuery("Filijala.findByNaziv", Filijala.class).setParameter("naziv", nazivFilijale).getSingleResult();
        } catch(Exception e){
           filijala = null;
        }
       
        
        TextMessage textMsg = context.createTextMessage();
        JSONObject jObj = new JSONObject();
        jObj.put("number", 18);
        if (filijala != null) jObj.put("filijala", filijala.getIDFil());
        else jObj.put("filijala", -1);
        try {
            textMsg.setText(jObj.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP2, textMsg);
        context.close();
    }

    public void svaMestaKaPodsistemu3(Queue queue) {
        
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        JSONArray jArray = new JSONArray();
       
        for (Mesto mesto : mesta) {
            JSONObject jObject = new JSONObject();
            jObject.put("idMes", mesto.getIDMes());
            jObject.put("posBroj", mesto.getPostanskiBroj());
            jObject.put("naziv", mesto.getNaziv());
            jArray.add(jObject);
        }
        
        TextMessage textMsg = context.createTextMessage();
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queue, textMsg);
        context.close();
    }

    public void sveFilijaleKaPodsistemu3(Queue queue) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
        JSONArray jArray = new JSONArray();
       
        for (Filijala filijala : filijale) {
            JSONObject jObject = new JSONObject();
            jObject.put("idFil", filijala.getIDFil());
            jObject.put("adresa", filijala.getAdresa());
            jObject.put("naziv", filijala.getNaziv());
            jObject.put("mesto", filijala.getIDMes().getIDMes());
            jArray.add(jObject);
        }
        
        TextMessage textMsg = context.createTextMessage();
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queue, textMsg);
        context.close();
    }
    
    public void sviKomitentiKaPodsistemu3(Queue queue) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Komitent> komitenti = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
        JSONArray jArray = new JSONArray();
       
        for (Komitent komitent : komitenti) {
            JSONObject jObject = new JSONObject();
            jObject.put("idKom", komitent.getIDKom());
            jObject.put("adresa", komitent.getAdresa());
            jObject.put("naziv", komitent.getNaziv());
            jObject.put("mesto", komitent.getIDMes().getIDMes());
            jArray.add(jObject);
        }
        
        TextMessage textMsg = context.createTextMessage();
        
        try {
            textMsg.setText(jArray.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP1.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(queue, textMsg);
        context.close();
    }
}
