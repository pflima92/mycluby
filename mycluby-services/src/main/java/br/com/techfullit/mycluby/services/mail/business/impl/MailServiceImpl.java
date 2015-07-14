package br.com.techfullit.mycluby.services.mail.business.impl;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.techfullit.mycluby.common.models.MailConfig;
import br.com.techfullit.mycluby.services.mail.business.MailService;

public class MailServiceImpl implements MailService {

	@Resource(name = "mailProperties")
	private Properties props;

	private final static String CONTENT_CHARSET = "text/html; charset=utf-8";

	public void sendMail(MailConfig mailConfig) throws MessagingException {
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
			}
		});
		session.setDebug(true);
		session.getTransport("smtps");
		session.setProtocolForAddress("smtp.gmail.com", "smtps");
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress((String) props.get("fromAddress")));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailConfig.getTo()));
		message.setSubject(mailConfig.getSubject());

		if (mailConfig.isHtmlBody()) {
			message.setContent(mailConfig.getBodyContent(), CONTENT_CHARSET);
		} else {
			message.setText(mailConfig.getBodyContent());
		}
		if(props.get("sendMail").equals("true")){
		    Transport.send(message);
		}
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
}
