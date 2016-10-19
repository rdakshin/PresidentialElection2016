package org.data.configs;


/**
 * @author drajavel
 * Provided all the Config needed for Mongo database
 */
public class MongoDBConfig {
	public final static String SERVER_HOST = "127.0.0.1";
	public final static Integer SERVER_PORT = 27017;
	public final static String DATABASE = "twitterDB";
	public final static String TABLE = "publicTweets";
	public final static String TABLE_STREAM = "tweetStream";
}
