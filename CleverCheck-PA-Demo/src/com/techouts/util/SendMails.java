package com.techouts.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class SendMails {

	private static final Logger LOGGER = Logger.getLogger(SendMails.class
			.getName());
	static Session session;

	static {
		final String username = "clevercheck.admin@techouts.com";// change
																	// accordingly
		final String password = "techouts";// change accordingly

		// Assuming you are sending email through relay.jangosmtp.net
		String host = "smtp.gmail.com";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object.
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

	}

	public static void sendMail(String mailId, String subject, String msgBody) {
		InternetAddress[] myToList = null;
		;
		if (mailId.equals("bhuvanesh.m@techouts.com")
				&& subject.contains("Updated Av's")) {
			try {
				myToList = InternetAddress
						.parse("uday.k@techouts.com,ramakrishna.g@techouts.com");
			} catch (AddressException e1) {
				e1.printStackTrace();
			}
		}

		String to = mailId;
		Message message = new MimeMessage(session);
		// Sender's email ID needs to be mentioned
		String from = "clevercheck.admin@techouts.com";// change accordingly
		try {
			// Create a default MimeMessage object.

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			if (to.equals("bhuvanesh.m@techouts.com")) {
				message.setRecipients(Message.RecipientType.CC, myToList);
			}
			// Set Subject: header field
			message.setSubject(subject);

			message.setText(msgBody);

			// Send message
			Transport.send(message);

			// System.out.println("Sent message successfully....");
			LOGGER.info("Mail sent successfully to :" + to);

		} catch (Exception e) {
			try {
				Transport.send(message);
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				LOGGER.error("Mail has not sent successfully to :" + to);
			}
			LOGGER.error("Mail has not sent successfully to :" + to);
		}

	}// End sendMail method

}// End class
