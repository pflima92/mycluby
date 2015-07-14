package br.com.techfullit.mycluby.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@Controller
@ViewScoped
@ManagedBean(name = "secondStep")
public class SecondStepController extends ContexController {

	public SecondStepController() {
	}

	private static final Log LOG = LogFactory.getLog(SecondStepController.class);

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	public void loadData() {
		if (user == null)
			user = (User) ContextHelper.getCurrentEntity();
	}

	private User user;

	public String updateAccountContinue() {
		LOG.debug("Called method [updateAccountContinue]");
		ResponseEntity response = null;
		try {
			response = accountService.updateUser(user);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
			return Events.EMPTY;
		}

		if (!response.isSuccess()) {
			LOG.debug(response.getDescStatus());
			MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
			return Events.EMPTY;
		} else {
			LOG.debug("Set user in session");
			ContextHelper.setCurrentEntity(user);
			return Events.THIRD_STEP;
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

}
