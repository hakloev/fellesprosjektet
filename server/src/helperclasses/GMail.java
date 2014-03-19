package helperclasses;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class for sending email to users when they
 * Created by Håkon Ødegård Løvdal March 2014
 */
public class GMail {
	private String username = "fpgruppe7@gmail.com" ;
	private String password = "fp_gruppe7";
	private Properties props;

	public GMail(){
		
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}

	public boolean sendMail(String recipient, String subject, String text){

		System.out.println("GMail.sendMail: SENDING MAIL");
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		 
				try {
		 
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(recipient));
					message.setSubject(subject);
					message.setText(text);
		 
					Transport.send(message);
		 
					System.out.println("GMail.sendMail: MAIL SENT");

					return true;

				} catch (MessagingException e) {
					return false;
				}
	}
}
