package org.data.exception;

import org.apache.log4j.Logger;


/**
 * @author drajavel
 * Custom Exception Handler for checked exception
 */
public class MessageException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1054449264933268574L;
	
	final Logger logger = Logger.getLogger(MessageException.class);

	public MessageException(){
		
	}
	
	public MessageException(String message, Throwable issue){
		super(message, issue);
		//logger.error(message);
		
	}
	
	public MessageException(String message){
		super(message);
		//logger.error(message);
	}
}
