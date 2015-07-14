package br.com.techfullit.mycluby.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.MailConfig;
import br.com.techfullit.mycluby.common.models.MailTemplate;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.Role;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.CookieHandler;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.common.utils.SessionHandler;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.account.business.AccountService;
import br.com.techfullit.mycluby.services.authentication.business.AuthenticationService;
import br.com.techfullit.mycluby.services.establishment.business.EstablishmentService;
import br.com.techfullit.mycluby.services.mail.business.MailService;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ViewScoped
@ManagedBean(name = "loginController")
public class AuthenticationController extends ContexController {

	public AuthenticationController() {
	}

	private static final Log LOG = LogFactory.getLog(AuthenticationController.class);

	@ManagedProperty(value = "#{authenticationService}")
	private AuthenticationService authenticationService;

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	@ManagedProperty(value = "#{establishmentService}")
	private EstablishmentService establishmentService;

	@ManagedProperty(value = "#{mailService}")
	private MailService mailService;

	private String login;

	private String password;

	private boolean remember;

	@PostConstruct
	public void init() {

	}

	public String forgivePass() {
		LOG.debug("Called method [forgivePass] for User: " + login);
		return Events.FORGIVE_PASS;
	}

	public String requestForgivePass() {
		LOG.debug("Called method [forgivePass] for User: " + login);
		try {
			ResponseEntity response = accountService.verifyEmailRegistered(login);
			if (!(boolean) response.getObject()) {
				login = "";
				MessageViewer
						.showWarning("Ops!",
								"Não consegui encontrar seu email em nosso banco de dados, verifique se o que você digitou está correto.");
				return Events.EMPTY;
			}
		} catch (Exception e) {
			MessageViewer.showError("Ops, ocorreu um erro.");
		}

		try {
			ResponseEntity userResponse = accountService.findUserByMail(login);
			String name;
			if (userResponse.getObject() instanceof User) {
				User user = (User) userResponse.getObject();
				name = user.getFirstname();
			} else {
				ResponseEntity establishmentResp = establishmentService.findEstablishmentByMail(login);
				Establishment e = (Establishment) establishmentResp.getObject();
				name = e.getName();
			}

			ResponseEntity responseEntity = authenticationService.requestForgivePass(login);

			MailConfig mailConfig = new MailConfig();
			mailConfig.setTo(login);
			mailConfig.setHtmlBody(true);
			mailConfig.setSubject("MyCLuby - Recuperação de Senha");

			MailTemplate template = new MailTemplate(name, "Recebemos um pedido para reset da sua senha! <br/><br/> "
					+ "Sua nova senha é: <b>" + responseEntity.getObject().toString() + "</b><br/><br/>"
					+ "Caso não tenha feito essa requisição entre em contato pelo fale conosco.");

			mailConfig.setBodyContent(template.getTemplate());
			try {
				mailService.sendMail(mailConfig);
			} catch (MessagingException e) {
				MessageViewer.showError("Ops, sua conta foi criada mas não consegui te mandar um email.");
			}

		} catch (Exception e) {
			MessageViewer.showError("Ops",
					"Não conseguimos recuperar sua senha, por favor entre em contato pelo fale conosco.");
			return Events.EMPTY;
		}

		return Events.FORGIVE_PASS_SUCCESS;
	}

	public String createAccount() {
		LOG.debug("Called method [createAccount] for User: " + login);
		return Events.CREATE_ACCOUNT;
	}

	public String createEstablishment() {
		LOG.debug("Called method [createEstablishment] for User: " + login);
		return Events.CREATE_ESTABLISHMENT;
	}

	public String loginWeb() {
		LOG.debug("Called method [callLogin] for User: " + login);
		ResponseEntity responseEntity;
		try {
			responseEntity = authenticationService.validateDatabaseAuthentication(login, password);
			LOG.debug("Method called with success");
			if (responseEntity.isSuccess()) {
				LOG.debug("Method return success");

				LOG.debug("Verify cookies options");
				if (remember) {
					try {
						LOG.debug("Write COOKIE_LOGGED.");
						CookieHandler.put(Constants.COOKIE_LOGGED, login);
						LOG.debug("Write COOKIE_REMEMBER.");
						CookieHandler.put(Constants.COOKIE_REMEMBER, remember ? "true" : "false");
						LOG.debug("Convert User from JsonData");

						ObjectMapper mapper = new ObjectMapper();
						String jsonData = mapper.writeValueAsString(responseEntity.getObject());
						LOG.debug("JsonData : " + jsonData);
						LOG.debug("Write COOKIE_USERDATA.");
						CookieHandler.put(Constants.COOKIE_USERDATA, jsonData);

					} catch (Exception e) {
						LOG.error("Ignore cookie options because processe of write cookies return error.");
						LOG.error(e.getMessage());
						CookieHandler.delete(Constants.COOKIE_LOGGED);
						CookieHandler.delete(Constants.COOKIE_REMEMBER);
						CookieHandler.delete(Constants.COOKIE_USERDATA);
					}
				} else {
					CookieHandler.delete(Constants.COOKIE_LOGGED);
					CookieHandler.delete(Constants.COOKIE_REMEMBER);
					CookieHandler.delete(Constants.COOKIE_USERDATA);
				}

				LOG.debug("Put entity in Context");
				ContextHelper.setCurrentEntity(responseEntity.getObject());
				if (responseEntity.getObject() instanceof User) {
					User user = (User) responseEntity.getObject();
					user.getRoles().add(Role.ROLE_LOGGED);

					LOG.debug("Verify if user make first steps");
					if (user.getAccountInfo().isFirstStep()) {
						return Events.FIRST_STEP;
					} else {
						return Events.INDEX;
					}
				} else {
					return Events.ESTABLISHMENT_MAIN;
				}

			} else {
				MessageViewer.showError(responseEntity.getDescStatus());
				return null;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Erro ao efetuar login");
			return null;
		}
	}

	public String logoutWeb() {
		LOG.debug("Called method [logout] for User: " + ContextHelper.getCurrentEntity());
		SessionHandler.destroySession();
		LOG.debug("Delete cookie user information");
		CookieHandler.delete(Constants.COOKIE_LOGGED);
		CookieHandler.delete(Constants.COOKIE_REMEMBER);
		CookieHandler.delete(Constants.COOKIE_USERDATA);
		LOG.debug("Logout realized");
		return Events.LOGOUT;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public EstablishmentService getEstablishmentService() {
		return establishmentService;
	}

	public void setEstablishmentService(EstablishmentService establishmentService) {
		this.establishmentService = establishmentService;
	}
}