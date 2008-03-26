package com.infinity.datamarket.pdv.infocomponent;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TopicSession;

import com.infinity.datamarket.infocomponent.InfoComponent;
import com.infinity.datamarket.pdv.util.ServerConfig;
import com.infinity.datamarket.pdv.util.ServiceLocator;

public class ThreadEnviaInfoComponent extends Thread{
	private InfoComponent info;
	
	public ThreadEnviaInfoComponent(InfoComponent info){
		this.info = info;
	}
	
	public void run(){
		
		try{
			
			Queue queue = (Queue) ServiceLocator.getJNDIObject(ServerConfig.QUEUE_INFO_COMPONENT_SERVER_JNDI);  
	
			ConnectionFactory factory = (ConnectionFactory) ServiceLocator.getJNDIObject(ServerConfig.CONNECTION_FACTORY);
			
			Connection connection = factory.createConnection(); 
			
			Session session = connection.createSession(false, TopicSession.AUTO_ACKNOWLEDGE); 
			
			MessageProducer producer = session.createProducer(queue); 
			
			ObjectMessage objectMessage = session.createObjectMessage(); 
	
			objectMessage.setObject(info); 
	
			producer.send(objectMessage); 
			
			
			producer.close();
			session.close();
			connection.close();
			
			
		}catch(Throwable e){
			
		}
	
	}
	
}
