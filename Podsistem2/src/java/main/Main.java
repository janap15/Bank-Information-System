/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import zahtevi.ZahteviP2;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Jana
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    public static ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueueDom2")
    public static Queue myQueue;
    
    @Resource(lookup="myQueueDom1")
    public static Queue queueP1;
    
    @Resource(lookup="myQueueDom3")
    public static Queue queueP3tajmer;
    
    @Resource(lookup="myQueueDom")
    public static Queue queueP3;
    
    @Resource(lookup="myQueueDomCS")
    public static Queue queueCS;
    
    public static void main(String[] args) {
        
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue);
        
        ZahteviP2 zahtevi = new ZahteviP2();
        
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
                        case 3: zahtevi.dodajKomitenta(jObject); break;
                        case 5: zahtevi.otvaranjeRacuna(jObject, consumer); break;
                        case 6: zahtevi.ugasiRacun(jObject); break;
                        case 7: zahtevi.prenesiNovac(jObject); break;
                        case 8: zahtevi.uplataNovca(jObject, consumer); break;
                        case 9: zahtevi.isplataNovca(jObject, consumer); break;
                        case 13: zahtevi.sviRacuniKomitenta(jObject); break;
                        case 14: zahtevi.sveTransakcijeRacuna(jObject); break;
                        case 22: zahtevi.sviRacuniKaPodsistemu3(queueP3tajmer); break;
                        case 23: zahtevi.sveTransakcijeKaPodsistemu3(queueP3tajmer); break;
                        case 24: zahtevi.sviRacuniKaPodsistemu3(queueP3); break;
                        case 25: zahtevi.sveTransakcijeKaPodsistemu3(queueP3); break;
                    }
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
