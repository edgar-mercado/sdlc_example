package com.ecme.jms.adapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

@Component
public class ConsumerAdapter {
	private static Logger log = LogManager.getLogger(ConsumerAdapter.class); 

	public void sendToMongo(String json) {
		log.info("Sending to MongoDB");
		MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		MongoDatabase db= client.getDatabase("vendor");
		MongoCollection collection=db.getCollection("contact");
		log.info("converting JSON to DBObject");
		Document dbObject=Document.parse(json);
		collection.insertOne(dbObject);
		log.info("inserted into MongoDB");
		
	}

}
