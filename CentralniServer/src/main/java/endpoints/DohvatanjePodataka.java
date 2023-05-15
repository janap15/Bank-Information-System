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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Jana
 */
@Path("sviPodaci")
public class DohvatanjePodataka {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueueDom")
    private Queue queueP3;
    
    @Resource(lookup="myQueueDomCS")
    private Queue myQueue;
    
    @GET
    public Response sviPodaci(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(myQueue);
        try {
            JSONObject jObject = new JSONObject();
            TextMessage textMsg = context.createTextMessage();
            jObject.put("number", 15);
            textMsg.setText(jObject.toString());
            producer.send(queueP3, textMsg);

            Message msg = consumer.receive();
            if (msg instanceof TextMessage) {
                try {
                    String messageText = ((TextMessage)msg).getText();
                    context.close();
                    consumer.close();
                    System.out.println(messageText);
                    return Response.status(Response.Status.OK).entity(messageText).build();
                } catch (JMSException ex) {
                    Logger.getLogger(DohvatanjePodataka.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(DohvatanjePodataka.class.getName()).log(Level.SEVERE, null, ex);
       
        }
        context.close();
        consumer.close();
        return Response.serverError().build();
    }
}
