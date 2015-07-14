package br.com.techfullit.mycluby.services.mail.business;

import javax.mail.MessagingException;

import br.com.techfullit.mycluby.common.models.MailConfig;

public interface MailService {

	public void sendMail(MailConfig mailConfig) throws MessagingException;

}
