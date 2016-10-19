package org.data.persistence;

import org.bson.Document;
import org.data.configs.MongoDBConfig;
import org.data.exception.DataFlowException;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author drajavel
 * Provides connection to database
 * Provides Document 
 */
public final class MongoDatabaseConnectionProvider {
	
	private final MongoDatabase db;
	private final MongoClient mongoClient;
	/**
	 * Constructs the MongoClient and MongoDatabase
	 */
	 public MongoDatabaseConnectionProvider(){
		try{
			mongoClient = new MongoClient(MongoDBConfig.SERVER_HOST, MongoDBConfig.SERVER_PORT);
			db = mongoClient.getDatabase(MongoDBConfig.DATABASE);
		}catch(Exception ex){
			throw new DataFlowException(ex.getMessage());
		}
	}
	/**
	 * Provides the Collection required for onDemand Twitter Feed Processor
	 * @return Collection
	 */
	public MongoCollection<Document> getTweetOnDemandCollection(){
		return db.getCollection(MongoDBConfig.TABLE);
	}
	/**
	 * Provides the Collection required for Twitter Stream Processor
	 * @return Collection
	 */
	public MongoCollection<Document> getTweetStreamCollection(){
		return db.getCollection(MongoDBConfig.TABLE_STREAM);
	}
	/**
	 * Close the connection
	 * @return void
	 */
	public void closeConnection(){
		mongoClient.close();
	}

}
