package org.data.persistence;


import static com.mongodb.client.model.Filters.regex;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.data.exception.DataFlowException;

import com.mongodb.client.MongoCollection;

import twitter4j.Status;

/**
 * @author drajavel
 * MongoDB helper class provides utilities to persist 
 * and fetch data
 */
public class MongoDBHelper {
	/**
	 * Saves the single document to database
	 * @param document
	 * @param table
	 */
	public static void saveTweets(Document document, MongoCollection<Document> table){
		if(null != table){
			try{
				table.insertOne(document);
			}catch(Exception ex){
				throw new DataFlowException(ex.getMessage());
			}
		}
	}
	/**
	 * persist the list of Tweets
	 * @param List of Tweets
	 * @param Collection
	 */
	public static void saveTweets(List<Status> theTweets, MongoCollection<Document> table){
		if(null != theTweets){
			List<Document> listOfDocuments = new ArrayList<Document>();
			for(Status eachTweets: theTweets){
				Document document = new Document();
				document.put("tweetID", eachTweets.getId());
				document.put("tweetLanguage", eachTweets.getIsoLanguageCode());
				document.put("tweetDate", eachTweets.getCreatedAt());
				document.put("tweetSource", eachTweets.getSource());
				document.put("tweetText",eachTweets.getText());
				document.put("tweetUser", eachTweets.getUser().getName());
				document.put("tweetLocation", eachTweets.getUser().getLocation());
				document.put("tweetReTweetCount", eachTweets.getRetweetCount());
				listOfDocuments.add(document);
			}
			try{
				table.insertMany(listOfDocuments);
			}catch(Exception ex){
				throw new DataFlowException(ex.getMessage());
			}
		}
	}
	/**
	 * Fetches the tweets based on where clause
	 * @param Colection
	 * @param location
	 * @return
	 */
	public static List<Document> find(MongoCollection<Document> table, String location){
		List<Document> locationData = null;
		try{
			 locationData = table.find(regex("tweetLocation", location)).into(new ArrayList<Document>()); 
		}catch(Exception ex){
			throw new DataFlowException(ex.getMessage());
		}	  
		return locationData;
	}
}
