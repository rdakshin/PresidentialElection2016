package org.data.mail;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.data.exception.MessageException;

/**
 * @author drajavel
 * Sends the TLS Authentication Email
 */
public class TLSAuthenticationEmail extends EMailSender{
	
	public Session getMailSession;
	public MimeMessage generateMailMessage;
	/**
	 * @param none
	 * Sets the mail server properties
	 */
	public  TLSAuthenticationEmail(){
		mailServerProperties.put("mail.smtp.port", EmailConstants.MAIL_SMTP_PORT);
		mailServerProperties.put("mail.smtp.auth", EmailConstants.MAIL_SMTP_AUTH);
		mailServerProperties.put("mail.smtp.starttls.enable", EmailConstants.MAIL_SMTP_STARTTLS_ENABLE);
	}
	/**
	 * Sends the email 
	 * @param email toAddress
	 * @param email content
	 * @throws MessageException
	 */
	@Override
	public void sendEmail(String toAddress, String content) throws MessageException {
		try {
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			generateMailMessage.setSubject(EmailConstants.TWEET_PRESIDENTIAL_ELECTION_SUBJECT);
			generateMailMessage.setContent(content, "text/html");
			
			Transport transport = getMailSession.getTransport("smtp");
			transport.connect(EmailConstants.MAIL_SMTP_HOST, EmailConstants.MAIL_SMTP_USER, EmailConstants.MAIL_SMTP_PASSWORD);
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();

		} catch (Exception ex) {
			throw new MessageException(ex.getMessage());
		}
	}

}
