/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zahtevi.*;
import java.sql.Connection;
import zahtevi.ZahteviP1;

/**
 *
 * @author Jana
 */
public class Main {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    public static ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueueDom1")
    public static Queue myQueue;
    
    @Resource(lookup="myQueueDom2")
    public static Queue queueP2;
    
    @Resource(lookup="myQueueDom3")
    public static Queue queueP3tajmer;
    
    @Resource(lookup="myQueueDom")
    public static Queue queueP3;
    
    @Resource(lookup="myQueueDomCS")
    public static Queue queueCS;
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue);
        ZahteviP1 zahtevi = new ZahteviP1();
        
        while (true) {
            try{
                Message msg = consumer.receive();
                System.out.println("Received msg");
                if (msg instanceof TextMessage) {
                    JSONParser parser = new JSONParser();
                    String messageText = ((TextMessage)msg).getText();
                    System.out.println(messageText);
                    JSONObject jObject = (JSONObject)parser.parse(messageText);
                    long number = (Long)jObject.get("number");
                    switch((int)number){
                        case 1: zahtevi.kreirajMesto(jObject); break;
                        case 2: zahtevi.kreirajFilijalu(jObject); break;
                        case 3: zahtevi.kreirajKomitenta(jObject); break;
                        case 4: zahtevi.promenaSedistaKomitenta(jObject); break;
                        case 10: zahtevi.svaMesta(); break;
                        case 11: zahtevi.sveFilijale(); break;
                        case 12: zahtevi.sviKomitenti(); break;
                        case 17: zahtevi.idMesta(jObject); break;
                        case 18: zahtevi.idFilijale(jObject); break;
                        case 19: zahtevi.svaMestaKaPodsistemu3(queueP3tajmer); break;
                        case 20: zahtevi.sveFilijaleKaPodsistemu3(queueP3tajmer); break;
                        case 21: zahtevi.sviKomitentiKaPodsistemu3(queueP3tajmer);break;
                        case 22: zahtevi.svaMestaKaPodsistemu3(queueP3); break;
                        case 23: zahtevi.sveFilijaleKaPodsistemu3(queueP3); break;
                        case 24: zahtevi.sviKomitentiKaPodsistemu3(queueP3);break;
                    }
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
