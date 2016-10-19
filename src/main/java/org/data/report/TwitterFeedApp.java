package org.data.report;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.data.crawler.TwitterFeedCustomProcessor;
import org.data.mail.EMailSender;
import org.data.mail.TLSAuthenticationEmail;
import org.data.query.CustomQuery;
import org.data.query.QueryHelper;

import twitter4j.Status;

/**
 * @author drajavel
 * Gets email address as user input and sends the Twitter Data from San Francisco
 */
public class TwitterFeedApp {

	final static Logger logger = Logger.getLogger(TwitterFeedApp.class);
	
	public static void main(String[] args) {
		loadMenu();
	}

	public static void loadMenu() {
		String city;
		String emailAddress;
		
	    System.out.println("Enter emailaddress to receive tweets for San Francisco:\t");  
	    Scanner input = new Scanner(System.in);

	     while(true){
	    	 System.out.println("Enter City:");
	    	 city = input.nextLine();
		     System.out.println("Enter email Address:");
		     emailAddress = input.nextLine();
	     
		     if(TwitterAppUtility.isEmailValid(emailAddress)){
		    	 TwitterFeedCustomProcessor customProcessor = new TwitterFeedCustomProcessor();
		    	 QueryHelper queryHelper = new QueryHelper();
		    	 CustomQuery customQuery = queryHelper.getQuery();
		    	 //Get Latitude and Longitude of the city
		    	 String[] LatLong = TwitterAppUtility.getLatLongPositions(city);
		    	 customQuery.setLatitude(Double.parseDouble(LatLong[0]));
		    	 customQuery.setLongitude(Double.parseDouble(LatLong[1]));
		    	 
		    	 customProcessor.setQuery(customQuery);
		    	 customProcessor.queryData();
		    	 List<Status> twitterData = customProcessor.sendData();
		    	
			    if(twitterData != null && twitterData.size() > 0){
			    	EMailSender sendEmail = new TLSAuthenticationEmail();
					try {
						sendEmail.sendEmail(emailAddress, TwitterAppUtility.getEmailContent(twitterData, city));
					} catch (Exception ex) {
						logger.error(ex.getMessage());
					}
			    }
			    else{
			    	System.out.println("There is no tweet now from your city. Try after a while: \t");
			    }
				
		     }
		     else{
		    	 System.out.println("Email address is invalid. Enter valid email address to receive tweet for your city: \t"); 
		     }
		     
	     }
	     
	   }
}
