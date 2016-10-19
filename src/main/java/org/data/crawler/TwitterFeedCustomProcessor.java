package org.data.crawler;

import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.data.constants.TweetConstants;
import org.data.exception.DataFlowException;
import org.data.persistence.MongoDBHelper;
import org.data.persistence.MongoDatabaseConnectionProvider;
import org.data.query.CustomQuery;

import com.mongodb.client.MongoCollection;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;


/**
 * @author drajavel
 * This custom processor gets the twitter data based on custom filter provided
 * 
 */
public class TwitterFeedCustomProcessor extends TwitterDataProcessor{
	
	final static Logger logger = Logger.getLogger(TwitterFeedCustomProcessor.class);
	
	private final TwitterFactory twitterFactory;
	private final Twitter twitter;
	private Query query;
	private QueryResult result;
	
	public TwitterFeedCustomProcessor(){
		twitterFactory = new TwitterFactory(configBuilder.build());
        twitter = twitterFactory.getInstance();
          
	}
	/**
	 * @param CustomQuery
	 * @return void
	 * Set the custom filter that is provided by client system.
	 * 
	 */
	public void setQuery(CustomQuery customQuery){
		query = new Query(customQuery.getTopic());
        query.setCount(customQuery.getCount());
        query.setLang(customQuery.getLanguage());
        query.geoCode(new GeoLocation(customQuery.getLatitude(), customQuery.getLongitude()),
        			customQuery.getLocationRadius(),customQuery.getRadiusUnit());
	}
	
	/**
	 * @param Topic to Search
	 * @param record count
	 * @param language
	 * @return void
	 * Set the default filter that is provided by client system.
	 * 
	 */
	public void setQuery(String topic, int count, String language){
		query = new Query(topic);
        query.setCount(count);
        query.setLang(language);
	}
	
	/**
	 * @param none
	 * @return void
	 * Gets the twitter feed for the given query
	 * 
	 */
	
	@Override
	public void queryData() {
		logger.error("Getting Tweets...");
        try {
			result = twitter.search(query);
		} catch (Exception ex) {
			new DataFlowException(ex.getMessage());
		}
	}
	
	/**
	 * @param none
	 * @return void
	 * Process twitter feed that was selected
	 * 
	 */
	
	@Override
	public void processData() {
		List<Status> theTweets = null;
		MongoDatabaseConnectionProvider dbManager = null;
        try { 
        	if(null != result){
        		theTweets = result.getTweets();
        	}
			if(null != theTweets){
				MongoCollection<Document> document;
				dbManager = new MongoDatabaseConnectionProvider();
				document = dbManager.getTweetOnDemandCollection();
				MongoDBHelper.saveTweets(theTweets, document);
				logger.error("Got Tweets...");
			}	
		} catch (Exception ex) {
			throw new DataFlowException(ex.getMessage());
		}
        finally{
        	dbManager.closeConnection();
        }
	}
	
	/**
	 * @param none
	 * @return List<Status>
	 * 
	 */
	
	public List<Status> sendData(){
		List<Status> theTweets = null;
		if(null != result){
    		theTweets = result.getTweets();
    	}
		return theTweets;
	}
	
	// Client program for now
	public static void main(String[] args) {
		TwitterFeedCustomProcessor processor = new TwitterFeedCustomProcessor();
		processor.setQuery(TweetConstants.ELECTION_FILTER, 100, "en");
		try{
			processor.queryData();
			processor.processData();
		}catch (DataFlowException ex) {
			logger.error(ex.getMessage());
		}
	}

}
