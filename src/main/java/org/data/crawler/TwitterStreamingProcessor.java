package org.data.crawler;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.data.constants.TweetConstants;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;


/**
 * @author drajavel
 * Streaming processor 
 */
public class TwitterStreamingProcessor extends TwitterDataProcessor 
											implements Callable<String>{
    
	final static Logger logger = Logger.getLogger(TwitterStreamingProcessor.class);
	
    private final TwitterStream twitterStream;
    private final TwitterStatusListenerImpl twitterStatusListenerImpl = new TwitterStatusListenerImpl();
    
    private FilterQuery query;
    /**
     * Configure Twitter Stream
     */
    public TwitterStreamingProcessor(){
    	configBuilder.setJSONStoreEnabled(true);
    	twitterStream = new TwitterStreamFactory(configBuilder.build()).getInstance();
    }
    /**
     * @param none
     * @return String
     * Sets the listener to Twitter Stream
     * Sets the required Filter
     */
    public String call () throws Exception {
        twitterStream.addListener(twitterStatusListenerImpl);
        twitterStream.filter(this.query);
        return "success";
    }
    /**
     * @param Twitter query Topic
     * Default filter
     */
    private void setFilterQuery(String topic) {
        String track[] =  new String[1];
        track[0] =   topic ;       
        query = new FilterQuery(0,null,track);
    }
    
    @Override
	public void queryData() {
		// Listener handles the get data part.
		
	}
	
	@Override
	public void processData() {
		// No processing is required
		
	}
	
    //Client program for now
    public static void main(String[] args) {
    	TwitterStreamingProcessor processor = new TwitterStreamingProcessor();
    	try {
    		processor.setFilterQuery(TweetConstants.ELECTION_STREAM);
			processor.call();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
	}

}
