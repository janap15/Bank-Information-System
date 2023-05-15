/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import zahtevi.ZahteviP3;
import java.util.Timer;
import java.util.TimerTask;
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
    
    private static class Helper extends TimerTask
    {
        public static int i = 0;
        public void run()
        {
            System.out.println("Timer ran " + ++i);
        }
    }

    @Resource(lookup="jms/__defaultConnectionFactory")
    public static ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueueDom")
    public static Queue myQueue;
    
    @Resource(lookup="myQueueDom1")
    public static Queue queueP1;

    @Resource(lookup="myQueueDom2")
    public static Queue queueP2;
    
    @Resource(lookup="myQueueDom3")
    public static Queue queueP3;
    
    @Resource(lookup="myQueueDomCS")
    public static Queue queueCS;
     
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue);
        
        Timer timer = new Timer();
        MyTimer task = new MyTimer();
        timer.schedule(task, 120000, 120000);
       
        ZahteviP3 zahtevi = new ZahteviP3();
        
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
                        case 15: zahtevi.dohvatanjePodataka(); break;
                        case 16: zahtevi.razlikaUPodacima(consumer); break;
                    }
                }
            } catch (JMSException | ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
