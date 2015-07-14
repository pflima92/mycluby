package br.com.techfullit.mycluby.controller;

import java.text.ParseException;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Mail;
import br.com.techfullit.mycluby.common.models.MailConfig;
import br.com.techfullit.mycluby.common.models.MailTemplate;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.account.business.AccountService;
import br.com.techfullit.mycluby.services.mail.business.MailService;

@Controller
@SessionScoped
@ManagedBean(name = "createAccount")
public class CreateAccountController extends ContexController {

    public CreateAccountController() {
    }

    private static final Log LOG = LogFactory.getLog(CreateAccountController.class);

    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;

    @ManagedProperty(value = "#{mailService}")
    private MailService mailService;

    private String mail;

    private String confirmPassword;

    @PostConstruct
    public void init() {
	user = new User();
    }

    private User user;

    public String createAccount() {
	try {
	    ResponseEntity response = accountService.verifyEmailRegistered(mail);
	    if ((boolean) response.getObject()) {
		mail = "";
		MessageViewer.showWarning("Ops!",
			"Alguém já está utilizando este email, por favor selecione outra opção!");
		return Events.EMPTY;
	    }
	} catch (Exception e) {
	    MessageViewer.showError("Ops, ocorreu um erro.");
	}

	LOG.debug("Called method [createAccount]");
	LOG.debug("Verify passwords confirm");
	if (!user.getPassword().equals(confirmPassword)) {
	    MessageViewer.showError("As senha digitadas são diferentes");
	    return Events.EMPTY;
	}

	Mail mailModel = new Mail();
	mailModel.setValue(mail);
	mailModel.setActiveLogin(true);
	mailModel.setUser(user);
	user.getMails().add(mailModel);

	ResponseEntity response = null;
	try {
	    response = accountService.createUserAccount(user);
	} catch (Exception e) {
	    LOG.error(e.getMessage());
	    MessageViewer.showError("Ops, ocorreu um erro ao criar sua conta, tente novamente mais tarde.");
	    return Events.EMPTY;
	}

	if (!response.isSuccess()) {
	    LOG.debug(response.getDescStatus());
	    MessageViewer.showError("Ops, ocorreu um erro ao criar sua conta, tente novamente mais tarde.");
	    return Events.EMPTY;
	} else {
	    MailConfig mailConfig = new MailConfig();
	    mailConfig.setTo(mail);
	    mailConfig.setHtmlBody(true);
	    mailConfig.setSubject("Bem Vindo ao MyCluby");
	    
	    MailTemplate template = new MailTemplate(
			user.getFirstname(),
			"Sua conta foi criada com sucesso! <br/><br/> "
			+ "Agora você faz parte da rede do MyCluby, aproveite e chame seus amigos para fazer parte também. <br/><br/>"
			+ "Acesse o portal Social do MyCluby: http://www.mycluby.com.br <br/><br/>"
			+ "Não esqueça de baixar o nosso aplicativo para seu Smartphone e fique conectado com o MyCluby o tempo todo!<br/> Acesse: https://play.google.com/store/search?q=MyCLuby&hl=pt_BR");
	    
	    mailConfig.setBodyContent(template.getTemplate());
	    try {
		mailService.sendMail(mailConfig);
	    } catch (MessagingException e) {
		MessageViewer.showError("Ops, sua conta foi criada mas não consegui te mandar um email.");
	    }
	    LOG.debug("USER KEY GENERATED: " + response.getDescStatus());
	    user = new User();
	    return Events.CREATE_ACCOUNT_PENDING;
	}
    }

    public void verifyMail(AjaxBehaviorEvent event) {
	ResponseEntity response = null;
	try {
	    response = accountService.verifyEmailRegistered(mail);
	    if ((boolean) response.getObject()) {
		mail = "";
		MessageViewer.showWarning("Ops!",
			"Alguém já está utilizando este email, por favor selecione outra opção!");
	    }
	} catch (Exception e) {
	    MessageViewer.showError("Ops, ocorreu um erro.");
	}
    }

    public void verifyDate(AjaxBehaviorEvent event) throws ParseException {
	Calendar dataNasc = Calendar.getInstance();
	dataNasc.setTime(getUser().getBirthDate());
	dataNasc.add(Calendar.YEAR, 16);
	Calendar dataAtual = Calendar.getInstance();
	if (!dataNasc.before(dataAtual)) {
	    MessageViewer.showError("Ops, o usuário precisa ter mais que 16 anos!");
	    getUser().setBirthDate(null);
	}

    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	System.out.println("verifyMail");
	this.user = user;
    }

    public AccountService getAccountService() {
	return accountService;
    }

    public void setAccountService(AccountService accountService) {
	this.accountService = accountService;
    }

    public MailService getMailService() {
	return mailService;
    }

    public void setMailService(MailService mailService) {
	this.mailService = mailService;
    }

    public String getConfirmPassword() {
	return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
    }

    public String getMail() {
	return mail;
    }

    public void setMail(String mail) {
	this.mail = mail;
    }

}
