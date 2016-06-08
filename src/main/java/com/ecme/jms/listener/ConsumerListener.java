package com.ecme.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.ecme.jms.adapter.ConsumerAdapter;

@Component
public class ConsumerListener implements MessageListener {
	private static Logger log = LogManager.getLogger(ConsumerListener.class); 
	
	@Autowired
	JmsTemplate jsmTemplate;
	@Autowired
	ConsumerAdapter consumerAdapter;
	
	public ConsumerListener(){}
	
	public void onMessage(Message msg) {
		log.info("In onMessage()");
		String json=null;
		if(msg instanceof TextMessage){
			try {
				json=((TextMessage) msg).getText();
				log.info("Sending json to DB "+json);

				consumerAdapter.sendToMongo(json);
			} catch (JMSException e) {
				log.error("Message: "+json);
				jsmTemplate.convertAndSend(json);
			}
		}
			
	}

}
