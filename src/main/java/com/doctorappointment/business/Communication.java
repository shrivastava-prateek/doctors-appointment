package com.doctorappointment.business;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class Communication {
	
	static Properties mailProperties = null;
	static final String password = "official@123";
	static final String mailId = "bookmydoc123@gmail.com";
	static Authenticator auth = null;
	
			static {
				mailProperties = System.getProperties();
				mailProperties.put("mail.smtp.host", "smtp.gmail.com");
				mailProperties.put("mail.smtp.port", "587");
				mailProperties.put("mail.smtp.socketFactory.port", "465");
				mailProperties.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				mailProperties.put("mail.smtp.auth", "true"); //enable authentication
				mailProperties.put("mail.smtp.starttls.enable", "true");
				auth = new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailId, password);
					}
				};
				
			}
			
			public boolean sendMail(String toMailAddress,String body,String subject){
				Session session = Session.getInstance(mailProperties,auth);
				
				
				try{
					MimeMessage msg = new MimeMessage(session);
					//set message headers
					 msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
				      msg.addHeader("format", "flowed");
				      msg.addHeader("Content-Transfer-Encoding", "8bit");
				      
				      msg.setFrom(new InternetAddress(mailId, "NoReply-JD"));
				      
				      msg.setReplyTo(InternetAddress.parse(mailId, false));
				      
				      msg.setSubject(subject, "UTF-8");

				      msg.setText(body, "UTF-8");

				      msg.setSentDate(new Date());

				      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toMailAddress, false));
				      System.out.println("Message is ready");
			    	  Transport.send(msg);  
			    	  System.out.println("sent/......");
			    	  
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				
				return false;
			}
	

}
