package com.techouts.hp.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class FtpMailService {
	private final static Logger LOGGER = Logger.getLogger(FtpMailService.class);
	private static final String SUBJECT="Status of "+new SimpleDateFormat("MM-dd-yyyy").format(new Date())+" uploaded files";
	private static final String BODY="Hi,Please find the fallowing raw data status attachment.\n\n Thanks&Regards,\n";
	private JavaMailSenderImpl javaMailSenderImpl;
	private String toAddress;
	private String fromAddress;
	private String ccAddress;
	private String bccAddress;
	public JavaMailSenderImpl getJavaMailSenderImpl() {
		return javaMailSenderImpl;
	}
	public void setJavaMailSenderImpl(JavaMailSenderImpl javaMailSenderImpl) {
		this.javaMailSenderImpl = javaMailSenderImpl;
	}
	public String getToAddress() {
		return toAddress;
	}
	@Required
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	@Required
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getCcAddress() {
		return ccAddress;
	}
	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
	public String getBccAddress() {
		return bccAddress;
	}
	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}
	
	public void sendMail(String subject, String body) 
	{
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(getFromAddress());
		mailMessage.setTo(getToAddress());
		mailMessage.setCc(getCcAddress());
		mailMessage.setBcc(getBccAddress());
		mailMessage.setSubject(subject);
		mailMessage.setText("Hai All,\n\t"+body+"\n Thanks&Regards,\n"+getJavaMailSenderImpl().getUsername()+".");
		getJavaMailSenderImpl().send(mailMessage);
	}
	public void sendStatusMail(String fileLoca) throws MessagingException
	{
		    String body=BODY+getJavaMailSenderImpl().getUsername()+".";
		    MimeMessage message = getJavaMailSenderImpl().createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(getFromAddress());
			helper.setTo(getToAddress());
			helper.setCc(getCcAddress());
			helper.setBcc(getBccAddress());
			helper.setSubject(SUBJECT);
			helper.setText(body);
			FileSystemResource file = new FileSystemResource(fileLoca);
			helper.addAttachment(file.getFilename(), file);
			getJavaMailSenderImpl().send(message);
			LOGGER.info("mail send sucessfully..");
	}
}
