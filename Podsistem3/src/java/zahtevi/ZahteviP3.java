/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zahtevi;

import podsistem3.Filijala;
import podsistem3.Komitent;
import podsistem3.Mesto;
import podsistem3.Racun;
import podsistem3.Transakcija;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import main.Main;

/**
 *
 * @author Jana
 */
public class ZahteviP3 {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
    private EntityManager em = emf.createEntityManager();

    public void dohvatanjePodataka() {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        JSONArray jArrayM = new JSONArray();
        JSONObject jObjSend = new JSONObject();
        
        for(Mesto mesto: mesta){
            JSONObject jObj = new JSONObject();
            jObj.put("idMes", mesto.getIDMes());
            jObj.put("posBroj", mesto.getPostanskiBroj());
            jObj.put("naziv", mesto.getNaziv());
            jArrayM.add(jObj);
        }
        jObjSend.put("mesta", jArrayM);
        
        
        List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
        JSONArray jArrayF = new JSONArray();
        for (Filijala fil : filijale) {
            JSONObject jObj = new JSONObject();
            jObj.put("idFil", fil.getIDFil());
            jObj.put("naziv", fil.getNaziv());
            jObj.put("adresa", fil.getAdresa());
            jObj.put("mesto", fil.getIDMes());
            jArrayF.add(jObj);
        }
       jObjSend.put("filijale", jArrayF);
        
        List<Komitent> komitenti = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
        JSONArray jArrayK = new JSONArray();
        for (Komitent komitent : komitenti) {
            JSONObject jObj = new JSONObject();
            jObj.put("idKom", komitent.getIDKom());
            jObj.put("adresa", komitent.getAdresa());
            jObj.put("naziv", komitent.getNaziv());
            jObj.put("mesto", komitent.getIDMes());
            jArrayK.add(jObj);
        }
        jObjSend.put("komitenti", jArrayK);
        
        List<Racun> racuni = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
        JSONArray jArrayR = new JSONArray();
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
            jObj.put("idKom", racun.getIDKom());
            jObj.put("idMes", racun.getIDMes());
            jArrayR.add(jObj);
        }
        jObjSend.put("racuni", jArrayR);
        
        List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
        JSONArray jArrayT = new JSONArray();
        for (Transakcija transakcija : transakcije) {
            JSONObject jObj = new JSONObject();
            jObj.put("idTra", transakcija.getIDTra());
            jObj.put("racun", transakcija.getIDRac());
            jObj.put("redniBroj", transakcija.getRedniBroj());
            jObj.put("iznos", transakcija.getIznos());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
            String formatDate = sdf.format(transakcija.getDatumVreme());
            jObj.put("vremeDatum", formatDate);
            jObj.put("filijala", transakcija.getIDFil());
            jObj.put("tip", transakcija.getTip());
            jArrayT.add(jObj);
        }
        jObjSend.put("transakcije", jArrayT);
        
        TextMessage textMsg = context.createTextMessage();
        try {
            textMsg.setText(jObjSend.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueCS, textMsg);
        context.close();
    
    }

    public void razlikaUPodacima(JMSConsumer consumer) {
        JMSContext context = Main.connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JSONObject jObjSend = new JSONObject();
        
        List<Mesto> mestaRazlika = new ArrayList<>();
        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
        TextMessage textMsg1 = context.createTextMessage();
        JSONObject jObj1 = new JSONObject();
        jObj1.put("number", 22);
        try {
            textMsg1.setText(jObj1.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg1);
        
        Message msg1 = consumer.receive();
        System.out.println("Received msg1");
        if (msg1 instanceof TextMessage) {
            try {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg1).getText();
                System.out.println(messageText);
                JSONArray jArray = (JSONArray)parser.parse(messageText);
                
                for (int i =0; i< jArray.size(); i++){
                    JSONObject jobj =  (JSONObject)jArray.get(i);
                    Mesto mesto = new Mesto();
                    mesto.setIDMes((int)(long)jobj.get("idMes"));
                    mesto.setNaziv((String)jobj.get("naziv"));
                    mesto.setPostanskiBroj((int)(long)jobj.get("posBroj"));
                    
                    boolean found = false;
                    for(Mesto m: mesta){
                        if (m.getIDMes().equals(mesto.getIDMes()) && m.getNaziv().equals(mesto.getNaziv()) && m.getPostanskiBroj() == mesto.getPostanskiBroj()){
                            found = true;
                            break;
                        }
                    }
                    
                    if (!found){
                        mestaRazlika.add(mesto);
                    }
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        JSONArray jArrayM = new JSONArray();
        for (Mesto mesto : mestaRazlika) {
            JSONObject jObj = new JSONObject();
            jObj.put("idMes", mesto.getIDMes());
            jObj.put("posBroj", mesto.getPostanskiBroj());
            jObj.put("naziv", mesto.getNaziv());
            jArrayM.add(jObj);
        }
        jObjSend.put("mesta", jArrayM);
        
        List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
        List<Filijala> filijaleRazlika = new ArrayList<>();
        
        TextMessage textMsg2 = context.createTextMessage();
        JSONObject jObj2 = new JSONObject();
        jObj2.put("number", 23);
        try {
            textMsg2.setText(jObj2.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg2);
        
        Message msg2 = consumer.receive();
        System.out.println("Received msg2");
        if (msg2 instanceof TextMessage) {
            try {
                JSONParser parser = new JSONParser();
                String messageText = ((TextMessage)msg2).getText();
                System.out.println(messageText);
                JSONArray jArray = (JSONArray)parser.parse(messageText);
                
                
                for (int i =0; i<jArray.size(); i++){
                    JSONObject jobj =  (JSONObject)jArray.get(i);
                    Filijala filijala = new Filijala();
                    filijala.setIDFil((int)(long)jobj.get("idFil"));
                    filijala.setIDMes((int)(long)jobj.get("mesto"));
                    filijala.setNaziv((String)jobj.get("naziv"));
                    filijala.setAdresa((String)jobj.get("adresa"));
                    
                    boolean found = false;
                    for(Filijala f: filijale){
                        if (f.getIDFil().equals(filijala.getIDFil()) && f.getAdresa().equals(filijala.getAdresa()) && f.getIDMes() == filijala.getIDMes()
                                && f.getNaziv().equals(filijala.getNaziv())){
                            found = true;
                            break;
                        }
                    }
                    if (!found) 
                        filijaleRazlika.add(filijala);
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        JSONArray jArrayF = new JSONArray();
        for (Filijala fil : filijaleRazlika) {
            JSONObject jObj = new JSONObject();
            jObj.put("idFil", fil.getIDFil());
            jObj.put("naziv", fil.getNaziv());
            jObj.put("adresa", fil.getAdresa());
            jObj.put("mesto", fil.getIDMes());
            jArrayF.add(jObj);
        }
        jObjSend.put("filijale", jArrayF);
        
        
        List<Komitent> komitenti = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
        List<Komitent> komitentiRazlika = new ArrayList<>();
        
        TextMessage textMsg3 = context.createTextMessage();
        JSONObject jObj3 = new JSONObject();
        jObj3.put("number", 24);
        try {
            textMsg3.setText(jObj3.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP1, textMsg3);
        
        Message msg3 = consumer.receive();
        System.out.println("Received msg3");
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
                    
                    boolean found = false;
                    for (Komitent k : komitenti) {
                        if (k.getIDKom().equals(komitent.getIDKom()) && k.getAdresa().equals(komitent.getAdresa()) && k.getIDMes() == komitent.getIDMes() 
                                 && k.getNaziv().equals(komitent.getNaziv())) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) komitentiRazlika.add(komitent);
                    
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        JSONArray jArrayK = new JSONArray();
        for (Komitent komitent : komitentiRazlika) {
            JSONObject jObj = new JSONObject();
            jObj.put("idKom", komitent.getIDKom());
            jObj.put("adresa", komitent.getAdresa());
            jObj.put("naziv", komitent.getNaziv());
            jObj.put("mesto", komitent.getIDMes());
            jArrayK.add(jObj);
        }
        jObjSend.put("komitenti", jArrayK);
        
        List<Racun> racuni = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
        List<Racun> racuniRazlika = new ArrayList<>();
        
        TextMessage textMsg4 = context.createTextMessage();
        JSONObject jObj4 = new JSONObject();
        jObj4.put("number", 24);
        try {
            textMsg4.setText(jObj4.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP2, textMsg4);
        
        Message msg4 = consumer.receive();
        System.out.println("Received msg4");
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
                    
                    boolean found = false;
                    for (Racun r : racuni) {
                        if (r.getIDRac().equals(racun.getIDRac()) && r.getStanje() == racun.getStanje() && r.getStatus().equals(racun.getStatus()) &&
                                r.getDozvoljeniMinus() == racun.getDozvoljeniMinus() && r.getBrojTransakcija() == racun.getBrojTransakcija() &&
                                r.getIDKom() == racun.getIDKom() && r.getIDMes() == racun.getIDMes()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) racuniRazlika.add(racun);
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        JSONArray jArrayR = new JSONArray();
        for (Racun racun : racuniRazlika) {
            JSONObject jObj = new JSONObject();
            jObj.put("idRac", racun.getIDRac());
            jObj.put("stanje", racun.getStanje());
            jObj.put("status", racun.getStatus());
            jObj.put("dozvoljeniMinus", racun.getDozvoljeniMinus());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
            String formatDate = sdf.format(racun.getDatumVremeOtvaranja());
            jObj.put("vremeDatum", formatDate);
            jObj.put("brTransakcija", racun.getBrojTransakcija());
            jObj.put("idKom", racun.getIDKom());
            jObj.put("idMes", racun.getIDMes());
            jArrayR.add(jObj);
        }
        jObjSend.put("racuni", jArrayR);
     
        
        List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
        List<Transakcija> transakcijeRazlika = new ArrayList<>();
        
        TextMessage textMsg5 = context.createTextMessage();
        JSONObject jObj5 = new JSONObject();
        jObj5.put("number", 25);
        try {
            textMsg5.setText(jObj5.toString());
        } catch (JMSException ex) {
            Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        producer.send(Main.queueP2, textMsg5);
        
        Message msg5 = consumer.receive();
        System.out.println("Received msg5");
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
                    
                    boolean found = false;
                    for (Transakcija t : transakcije) {
                        if (t.getIDTra().equals(transakcija.getIDTra()) && t.getIDRac() == transakcija.getIDRac() && t.getIznos() == transakcija.getIznos()
                                && t.getRedniBroj() == transakcija.getRedniBroj() && ((t.getIDFil() == null && transakcija.getIDFil() == null) || 
                                (t.getIDFil().equals(transakcija.getIDFil()))) && t.getTip().equals(transakcija.getTip())){
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        transakcijeRazlika.add(transakcija);
                    
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(ZahteviP3.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
        
        JSONArray jArrayT = new JSONArray();
        for (Transakcija transakcija : transakcijeRazlika) {
            JSONObject jObj = new JSONObject();
            jObj.put("idTra", transakcija.getIDTra());
            jObj.put("racun", transakcija.getIDRac());
            jObj.put("redniBroj", transakcija.getRedniBroj());
            jObj.put("iznos", transakcija.getIznos());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"); 
            String formatDate = sdf.format(transakcija.getDatumVreme());
            jObj.put("vremeDatum", formatDate);
            jObj.put("filijala", transakcija.getIDFil());
            jObj.put("tip", transakcija.getTip());
            jArrayT.add(jObj);
        }
        jObjSend.put("transakcije", jArrayT);
        
        producer.send(Main.queueCS, jObjSend.toString());
        context.close();
    }
        
}
