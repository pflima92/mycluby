package br.com.techfullit.mycluby.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Mail;
import br.com.techfullit.mycluby.common.models.MailConfig;
import br.com.techfullit.mycluby.common.models.MailTemplate;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.account.business.AccountService;
import br.com.techfullit.mycluby.services.establishment.business.EstablishmentService;
import br.com.techfullit.mycluby.services.mail.business.MailService;

@Controller
@SessionScoped
@ManagedBean(name = "createEstablishment")
public class CreateEstablishmentController extends ContexController {

    public CreateEstablishmentController() {
    }

    private static final Log LOG = LogFactory.getLog(CreateEstablishmentController.class);

    @ManagedProperty(value = "#{establishmentService}")
    private EstablishmentService establishmentService;
    
    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;

    @ManagedProperty(value = "#{mailService}")
    private MailService mailService;

    private String mail;

    private String confirmPassword;

    @PostConstruct
    public void init() {
	establishment = new Establishment();
    }

    private Establishment establishment;

    public String createAccount() {
	LOG.debug("Called method [createAccount]");
	LOG.debug("Verify passwords confirm");
	if (!establishment.getPassword().equals(confirmPassword)) {
	    MessageViewer.showError("Ops, seguinte confira suas senhas que elas não estão iguais");
	    return Events.EMPTY;
	}

	Mail mailModel = new Mail();
	mailModel.setValue(mail);
	mailModel.setActiveLogin(true);
	mailModel.setEstablishment(establishment);
	establishment.getMails().add(mailModel);

	ResponseEntity response = null;
	try {
	    response = establishmentService.createEstablishmentAccount(establishment);
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
		    establishment.getName(),
		    "A conta para seu estabelecimento foi criada com sucesso! <br/><br/> "
			    + "Agora você faz parte da rede do MyCluby, aproveite e chame seus clientes e amigos para fazer parte também. <br/><br/>"
			    + "Acesse o portal Social do MyCluby: http://www.mycluby.com.br <br/><br/>"
			    + "Não esqueça de baixar o nosso aplicativo para seu Smartphone e fique conectado com o MyCluby o tempo todo!<br/> Acesse: https://play.google.com/store/search?q=MyCLuby&hl=pt_BR");

	    mailConfig.setBodyContent(template.getTemplate());
	    try {
		mailService.sendMail(mailConfig);
	    } catch (MessagingException e) {
		MessageViewer.showError("Ops, sua conta foi criada mas não consegui te mandar um email.");
	    }
	    LOG.debug("USER KEY GENERATED: " + response.getDescStatus());
	    establishment = new Establishment();
	    return Events.CREATE_ESTABLISHMENT_PENDING;
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

    public Establishment getEstablishment() {
	return establishment;
    }

    public void setEstablishment(Establishment establishment) {
	this.establishment = establishment;
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

    public EstablishmentService getEstablishmentService() {
	return establishmentService;
    }

    public void setEstablishmentService(EstablishmentService establishmentService) {
	this.establishmentService = establishmentService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

}
