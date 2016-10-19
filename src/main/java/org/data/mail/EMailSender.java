package org.data.mail;

import java.util.Properties;

import org.data.exception.MessageException;

public abstract class EMailSender {
	
	public Properties mailServerProperties;
	
	public EMailSender(){
		mailServerProperties = System.getProperties();
	}
	/**
	 *
	 * @param toAddress
	 * @param content
	 * @throws MessageException
	 */
	public abstract void sendEmail(String toAddress, String content) throws MessageException;
}
