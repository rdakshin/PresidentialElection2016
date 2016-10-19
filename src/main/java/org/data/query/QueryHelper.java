package org.data.query;

import org.data.constants.TweetConstants;

/**
 * @author drajavel
 * QueryHelper sets few default values
 */
public class QueryHelper {
	
	CustomQuery query;
	
	public CustomQuery getQuery() {
		return query;
	}
	public QueryHelper(){
		query = new CustomQuery();
		query.setCount(100);
		query.setLanguage("en");
		query.setLatitude(37.7890183); //Latitude for San Francisco
		query.setLongitude(-122.3915063); // Longitude for San Francisco
		query.setLocationRadius(10.0);
		query.setRadiusUnit("mi");
		query.setTopic(TweetConstants.ELECTION_FILTER);
	}
}
