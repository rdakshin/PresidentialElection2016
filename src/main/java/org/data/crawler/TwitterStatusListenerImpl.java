package org.data.crawler;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.data.exception.DataFlowException;
import org.data.persistence.MongoDBHelper;
import org.data.persistence.MongoDatabaseConnectionProvider;

import com.mongodb.client.MongoCollection;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;


/**
 * @author drajavel
 * Listener for Twitter Stream
 */
public class TwitterStatusListenerImpl implements StatusListener{
	
	final static Logger logger = Logger.getLogger(TwitterStatusListenerImpl.class);
	private MongoDatabaseConnectionProvider connectionProvider = null;
	private MongoCollection<Document> collection = null;
	
	/**
	 * Sets the Connection Provider and Collection
	 */
	public TwitterStatusListenerImpl(){
		connectionProvider = new MongoDatabaseConnectionProvider();
		collection = connectionProvider.getTweetStreamCollection();
	}
	
	public void onException(Exception arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onDeletionNotice(StatusDeletionNotice arg0) {
		logger.error("onDeletionNotice");
		
	}

	public void onScrubGeo(long arg0, long arg1) {
		logger.error("onScrubGeo");
		
	}

	public void onStallWarning(StallWarning arg0) {
		logger.error("onStallWarning");
		
	}
	/**
	 * @param Status
	 * @return void
	 * Gets the stream of data and save to database
	 */
	public void onStatus(Status status) {
		if(null == status){
			throw new DataFlowException("There is some issue in getting data from Twitter");
		}
		Document document = new Document();
		document.put("tweetID", status.getId());
		document.put("tweetLanguage", status.getIsoLanguageCode());
		document.put("tweetSource", status.getSource());
		document.put("tweetText", status.getText());
		document.put("tweetLocation", status.getUser().getLocation());
		document.put("tweetReTweetCount", status.getRetweetCount());
		try{
			MongoDBHelper.saveTweets(document, collection);
		}catch(Exception ex){
			throw new DataFlowException(ex.getMessage());
		}
	}
	
	public void onTrackLimitationNotice(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
