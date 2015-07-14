package br.com.techfullit.mycluby.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.UploadedFile;
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
@ManagedBean(name = "firstStep")
public class FirstStepController extends ContexController {

	public FirstStepController() {
	}

	private static final Log LOG = LogFactory.getLog(FirstStepController.class);

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	private UploadedFile img;

	public void loadData() {
		if (user == null)
			user = (User) ContextHelper.getCurrentEntity();
	}

	private User user;

	public void uploadFile(ActionEvent event) {
		try {
			LOG.debug("Get file of upload filter");
			byte[] content = img.getContents();
			LOG.debug("Conver file in base64");
			user.setImage(content);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Ops, tivemos um erro ao processar sua foto, tente novamente.");
		}
	}

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
			return Events.SECOND_STEP;
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

	public UploadedFile getImg() {
		return img;
	}

	public void setImg(UploadedFile img) {
		this.img = img;
	}
}
