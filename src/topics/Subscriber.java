package topics;
import java.util.Hashtable;
import java.awt.Color;


import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;


public class Subscriber implements javax.jms.MessageListener{

    private javax.jms.Connection connect = null;
    private javax.jms.Session receiveSession = null;
    InitialContext context = null;
    private final static int MAXRECEIVE =100;
    int hashCode;

	public void lancerLecture(String[] hashtags) throws JMSException {
        
        try
        {	// Create a connection
        	Hashtable properties = new Hashtable();
        	properties.put(Context.INITIAL_CONTEXT_FACTORY, 
        	    "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        	properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

        	context = new InitialContext(properties);

        	javax.jms.ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        	connect = factory.createConnection();
        
        	this.configurerSouscripteur(hashtags); 
            connect.start(); // on peut activer la connection. 
        } catch (javax.jms.JMSException jmse){
            jmse.printStackTrace();
        } catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	private void configurerSouscripteur(String[] hashtags) throws JMSException, NamingException{
    	// Pour consommer, il faudra simplement ouvrir une session 
        receiveSession = connect.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);  
        
        
        // et dire dans cette session quelle queue(s) et topic(s) on accèdera et dans quel mode
        Topic ts[] = new Topic[MAXRECEIVE];
        javax.jms.MessageConsumer trs[] = new javax.jms.MessageConsumer[MAXRECEIVE];
        
        //Abonnement aux topics
        for(int i=0;i<hashtags.length && i<MAXRECEIVE;i++)
        {
        	ts[i] = (Topic) context.lookup("dynamicTopics/"+hashtags[i]);
        	trs[i] = receiveSession.createConsumer(ts[i]);
        	trs[i].setMessageListener(this);
        	System.out.println("Abonné à # " + ts[i].getTopicName());        	
        }
        
        connect.start();       
    }
    

	@Override
	public void onMessage(Message message) {
		// Methode permettant au souscripteur de consommer effectivement chaque msg recu
		// via le topic auquel il a souscrit
		try {
			if(((TextMessage) message).getBooleanProperty("duplica"))
			{
				System.err.println("@"+((TextMessage) message).getStringProperty("@")+":");
				System.err.println(((TextMessage) message).getText());			
				System.err.println("#"+((TextMessage) message).getStringProperty("#")+" ");
				System.out.println("----------------------------------------------------");
			}
			else {
				System.out.println("@"+((TextMessage) message).getStringProperty("@")+":");
				System.out.println(((TextMessage) message).getText());			
				System.out.println("#"+((TextMessage) message).getStringProperty("#")+" ");
				System.out.println("----------------------------------------------------");
				
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

	public void abonner(String hashtag) {
    	Topic topic;
		try {
			topic = (Topic) context.lookup("dynamicTopics/"+hashtag);
			javax.jms.MessageConsumer topicReceiver = receiveSession.createConsumer(topic);
			topicReceiver.setMessageListener(this); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		
	}
	public void desabonner(String hashtag) {
		
		try {
			receiveSession.unsubscribe("dynamicTopics/"+hashtag);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}