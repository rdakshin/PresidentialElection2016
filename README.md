
Search and extract 2016 US Presidential Election related Tweets near your home, and share them with three friends
=================================================================================================================


* Twitter provides various API to get Feed - one time or Streaming

* Set up a developer account with Twitter and get the following attributes
	- CustomerKey
	- CustomerSecret
	- Customer OAuth Access Token
	- Customer OAuth Access Secret
* Build the ConfigurationBuilder using the above attributes

Simple Implementation - Custom Processor
========================================
	TwitterFeedCustomProcessor.java
	- Connects to Twitter using twitter4j.Twitter API
	- Extends the Abstract class TwitterDataProcessor - which instantiate the ConfigurationBuilder.
	- This API uses ConfigurationBuilder for Authentication with Twitter
	- This implementation uses a Custom Query to filter the data for "Presidential Election"
	- The location is hard coded as "San Francisco" (The Latitude and Longitude of the location).
	- setQuery(CustomQuery customQuery) - this method sets the custom query for filtering data
	- queryData() - this method queries the data from Twitter
	- processData() - this method process and saves the data to data source. In this case the default implementation is MongoDB data store
	- No parsing is needed as twitter4j provides the Tweets as Entity.

Streaming Implementation - Streaming Processor
==============================================
	TwitterStreamingProcessor.java
	- Connects to Twitter using twitter4j.TwitterStream API
	- Uses the listener TwitterStatusListenerImpl - the OnStatus() method saves the data to the data store - here it is MongoDB. In this case, the tweets are saved in a different 				collection. 
	- This implementation gets the Presidential Election Tweets irrespective of location.
	TO BE DONE
	==========
	* Implement Flume API to write the Streaming Data to logs. Then save the data in HDFS for further processing

Send Email - TLS Authentication Email - Uses JAVA mail API
=====================================
	EmailConstants.java - this class provides all the constants needed for sending email
	EmailSender - this sets configuration properties.
	TLSAuthenticationEmail.java 
	- Uses GMail SMTP server
	- Uses Javax.mail.Transport Layer to send mail
	TO BE DONE
	=========
	* Implement SSL Authentication email sender
	* Implement Email Sender with Attachment
	
Exception Handling
==================
All the Checked and Runtime (MongoDB and Twitter API) exceptions are caught and custom Exception is thrown

	DataFlowException.java
	- To Handle Runtime exception thrown by Twitter API or MongoDB
	MessageException.java
	- To Handle Checked Exception
Data Source
===========
	MongoDatabaseConnectionProvider.java
	- Connects to MongoDB and provides required Collection.
	MongoDBHelper.java
	- Provides methods to save List if documents (Tweets) to the DB
	- Provides methods to fetch records based on the user provided where clause.
	TO BE DONE
	=========
	* Runtime generation of select query
	* Runtime generation of Save query.
	
Test Clients
=============
	TwitterFeedApp.java
	- gets the email address input from user
	- Calls the Custom Tweets Processor
	- gets the Data, process in memory and call Email Sender to send the email
	- Does not save the data to database
	
	TwitterFeedApp.java
	- Provides few validation utility
	- getLatLongPositions(String address) method gives the Longitude and Latitude. This will be used in Custom Query.
	- Gets the Twitter feed and provide the content that goes in the email

Note:
=====
To run this program
 - Update TwitterConfig and EmailConstants with your Twitter and Gmail account info respectively.
 
Assumptions
============
* Twitter Feed needs to be streamed and to be persisted - at HDFS or some other data store. Think replication and parallel processing
* Process the data
* Collect Aggregation and Store in-memory - @ redis or some other in-memory processing tool
* Provide feature to generate few reports - 
	Example: Maximum retweets
			 People tweeting in favor of particular Presidential Candidate. 
			 Location Specific data that is favorable to a particular Presidential Candidate
			  
How to handle the above requirements - the frame work can be modified to and additional features can be implemented. 


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	