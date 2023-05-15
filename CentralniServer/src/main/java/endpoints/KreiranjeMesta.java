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
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;

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

@Path("kreirajMesto")
public class KreiranjeMesta {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="myQueueDom1")
    private Queue myQueue;
    
    
    @POST
    public Response createMesto(String body){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        try{
            JSONParser parser = new JSONParser();
            JSONObject jObject = (JSONObject)parser.parse(body);
            TextMessage textMsg = context.createTextMessage();
            jObject.put("number", 1);
            textMsg.setText(jObject.toString());
            producer.send(myQueue, textMsg);
            context.close();
            return Response.ok().build();
        } catch (JMSException | ParseException ex) {
            Logger.getLogger(KreiranjeMesta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.serverError().build();
    }
}
