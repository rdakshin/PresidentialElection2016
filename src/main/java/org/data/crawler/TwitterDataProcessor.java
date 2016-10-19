package org.data.crawler;


import org.apache.log4j.Logger;
import org.data.configs.TwitterConfig;
import org.data.exception.DataFlowException;

import twitter4j.FilterQuery;
import twitter4j.conf.ConfigurationBuilder;


/**
 * @author drajavel
 * Abstract class TwitterDataProcessor sets the ConfigurationBuilder
 * Provides methods to query and process twitter data
 */

public abstract class TwitterDataProcessor {

	final static Logger logger = Logger.getLogger(TwitterDataProcessor.class);
	ConfigurationBuilder configBuilder = new ConfigurationBuilder();
	
	/** TwitterDataProcessor constructs the ConfigurationBuilder that
	 * that will be used by the derived class
	 */
	
	public TwitterDataProcessor(){
		configBuilder.setDebugEnabled(Boolean.TRUE).setOAuthConsumerKey(TwitterConfig.TWITTER_OAUTH_CONSUMER_KEY)
		.setOAuthConsumerSecret(TwitterConfig.TWITTER_OAUTH_CONSUMER_SECRET)
		.setOAuthAccessToken(TwitterConfig.TWITTER_ACCESS_TOKEN)
		.setOAuthAccessTokenSecret(TwitterConfig.TWITTER_TOKEN_SECRET);
	}
	
	public FilterQuery getFilter(){
		String track[] = null;     
        FilterQuery query = new FilterQuery(0,null,track);
        return query;
	}
	/** abstract method queryData() queries the twitter feed
	 * throws custom exception DataFlowException
	 */
	public abstract void queryData() throws DataFlowException;
	
	/** abstract method processData() save the twitter feed to database
	 * throws custom exception DataFlowException
	 */
	public abstract void processData() throws DataFlowException;
}
