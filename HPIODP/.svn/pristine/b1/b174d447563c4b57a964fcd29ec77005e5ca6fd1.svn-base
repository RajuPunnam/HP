package com.io.utill;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import com.techouts.io.exceptions.HPIOException;

public class Mail {

	private static Properties properties = new Properties();
	private static Logger LOGGER = Logger.getLogger(Mail.class);

	/**
	 * Method sends email to the recipient as configured
	 * 
	 * @param properties
	 * @param mailBody
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private static int i=0;
	public static void sendMail(String mailBody) {
		LOGGER.info("Inside getFtpMail method");
		if (mailBody.contains("DOI")) {
			LOGGER.info("DOI Mail thread");
		}

		final String from = "no-reply@techouts.com"; // TODO

		// Setting gmail properties
		// Properties props = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, "Techouts123"); // TODO
					}
				});

		// Creating the email message and attachment
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("hpsupport@techouts.com")); // TODO
			/*
			 * message.setRecipients(Message.RecipientType.CC,
			 * InternetAddress.parse("")); //TODO
			 */
			message.setSubject("HPIODP Automation Process Status");// TODO
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			// message.setText(msg);
			Multipart multipart = new MimeMultipart();
			// TO-DO:Preeti

			messageBodyPart.setHeader("Content-type", "text/html");
			messageBodyPart.setText(mailBody);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			message.setText(mailBody);

			// email send call
			Transport.send(message);
			LOGGER.info("Email sent to hpsupport@techouts.com");
			LOGGER.info("Exiting getFtpMail method");
		} catch (Exception e) {
			i++;
			if(i<2)
				sendMail("Error in MAIL \t"+e);
			else
			throw new HPIOException("Error in MAIL \t"+e);
		}
	}


}
