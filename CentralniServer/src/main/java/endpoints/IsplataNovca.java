/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Jana
 */

@Path("isplata")
public class IsplataNovca {
    
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueueDomCS")
    private Queue myQueue;
    
    @Resource(lookup="myQueueDom2")
    private Queue queueP2;
    
    @POST
    public Response isplataNovca(String body){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(myQueue);
        
        try{
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject)parser.parse(body);
            TextMessage textMsg = context.createTextMessage();
            jObject.put("number", 9);
            textMsg.setText(jObject.toString());
            producer.send(queueP2, textMsg);
            System.out.println("Sent msg");
            
            Message msg = consumer.receive();
            System.out.println("Received msg");
            if (msg instanceof TextMessage) {
                String messageText = ((TextMessage)msg).getText();
                context.close();
                consumer.close();
                if (messageText.equals("1")) return Response.ok().build();
                else if (messageText.equals("2")) return Response.status(Response.Status.BAD_REQUEST).build();
                else if (messageText.equals("3")) return Response.status(Response.Status.NOT_ACCEPTABLE).build();
                else if (messageText.equals("4")) return Response.status(Response.Status.NOT_IMPLEMENTED).build();
            }
        } catch (JMSException | ParseException ex) {
            Logger.getLogger(DohvatanjeSvihMesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        context.close();
        consumer.close();
        return Response.serverError().build();
        }
    }
    

