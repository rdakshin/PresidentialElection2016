package org.data.query;


/**
 * @author drajavel
 * CustomQuery needed querying twitter feed
 */
public class CustomQuery {
	
	private int count;
	private double latitude;
	private double longitude;
	private String language;
	private String topic;
	private double locationRadius;
	private String radiusUnit;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public double getLocationRadius() {
		return locationRadius;
	}
	public void setLocationRadius(double locationRadius) {
		this.locationRadius = locationRadius;
	}
	public String getRadiusUnit() {
		return radiusUnit;
	}
	public void setRadiusUnit(String radiusUnit) {
		this.radiusUnit = radiusUnit;
	}
}
