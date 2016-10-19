package org.data.report;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.data.exception.MessageException;
import org.w3c.dom.Document;

import twitter4j.Status;


/**
 * @author drajavel
 * Utility that supports Twitter App
 */
public class TwitterAppUtility {
	
	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static Pattern pattern;
	private static Matcher matcher;
	
	/**
	 * 
	 * @param List of Status - Twitter Data
	 * @param location
	 * @return email content
	 * gets the Twitter data, process and creates email content
	 */
	public static String getEmailContent(List<Status> twitterData, String location){
		StringBuilder sb = new StringBuilder();
		int counter = 1;
		if(null != twitterData){
			Iterator<Status> iterate = twitterData.iterator();
			while(iterate.hasNext()){
				Status each = iterate.next();
				if(each.getUser().getLocation().contains(location)){
					sb.append(""+counter + ":" + "\t");
					sb.append(each.getUser().getName() != null ? each.getUser().getName()+ " tweeted as: " 
														: " Someone tweeted as: " + ":" + "\t");
					sb.append(each.getText());
					sb.append("<br> <br>");
					counter++;
				}
			}
		}
		return sb.toString();	
	}
	/**
	 * 
	 * @param emailAddress
	 * @return boolean
	 * Validates the email address supplied
	 */
	public static  boolean isEmailValid(final String emailAddress) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(emailAddress);
		return matcher.matches();

	}
	/**
	 * 
	 * @param City Name 
	 * @return Longitude and Latitude of the provided location
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws Exception
	 */
	public static String[] getLatLongPositions(String address)  {
	    int responseCode = 0;
	    try{
		    String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
		    System.out.println("URL : "+api);
		    URL url = new URL(api);
		    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
		    httpConnection.connect();
		    responseCode = httpConnection.getResponseCode();
		    if(responseCode == 200)
		    {
		      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
		      Document document = builder.parse(httpConnection.getInputStream());
		      XPathFactory xPathfactory = XPathFactory.newInstance();
		      XPath xpath = xPathfactory.newXPath();
		      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
		      String status = (String)expr.evaluate(document, XPathConstants.STRING);
		      if(status.equals("OK"))
		      {
		         expr = xpath.compile("//geometry/location/lat");
		         String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
		         expr = xpath.compile("//geometry/location/lng");
		         String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
		         return new String[] {latitude, longitude};
		      }
		      else
		      {
		         throw new MessageException("Error from the API - response status: "+status);
		      }
		    }
		    
			}catch(Exception ex){
				try {
					throw new MessageException("Error from the API - response status");
				} catch (MessageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			    return null;
		}
}
