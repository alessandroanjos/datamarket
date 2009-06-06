package com.infinity.datamarket.infocomponent;

import java.util.Date;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.infinity.datamarket.comum.Fachada;

@MessageDriven(mappedName = "jms/InfoComponentServer", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), 
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/A")})
public class InfoComponentServer implements MessageListener {

	public void onMessage(Message msn) {
		try {
			if (msn instanceof ObjectMessage){
				ObjectMessage obj = (ObjectMessage) msn;
				if (obj.getObject() instanceof InfoComponent){
					InfoComponent info = (InfoComponent) obj.getObject();
					info.setDataAtualizacao(new Date());
					Fachada.getInstancia().salvarInfoComponent(info);
				}				
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
//	public static void main(String[] a){
//		ConnectionFactory connectionFactory = null;
//		Connection connection = null;
//		Session session = null;
//		Destination destination = null;
//		MessageProducer messageProduver = null;
//		ObjectMessage obj = null;
//		Context ctx = null;
//		
//		Hashtable prop = new Hashtable (); 
//		prop.put (InitialContext.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory"); 
//		prop.put (InitialContext.PROVIDER_URL,"jnp://localhost:1099");
//		try {
//			ctx = new InitialContext(prop);
//			connectionFactory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
//			connection = connectionFactory.createConnection();
//			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//			destination = (Destination) ctx.lookup("queue/A");
//			messageProduver = session.createProducer(destination);
//			InfoComponent info = new InfoComponent();
//			info.setComponente("1");
//			info.setEstado("DISPONIVEL");
//			info.setLote("20");
//			info.setVersao("01.01.01");
//			obj = session.createObjectMessage(info);
//			messageProduver.send(obj);
//			messageProduver.close();
//			session.close();
//			connection.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//		
//		
//	}


}
