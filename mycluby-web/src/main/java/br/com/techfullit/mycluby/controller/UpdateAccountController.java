package br.com.techfullit.mycluby.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.Base64;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.Crypto;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@Controller
@SessionScoped
@ManagedBean(name = "updateAccount")
public class UpdateAccountController extends ContexController {

	public UpdateAccountController() {
	}

	private static final Log LOG = LogFactory.getLog(UpdateAccountController.class);

	@ManagedProperty(value = "#{base64}")
	private Base64 base64;

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	private UploadedFile img;

	private String password;

	private String confirmPassword;

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

	public String changePassword() {

		LOG.debug("Verify passwords confirm");
		if (!password.equals(confirmPassword)) {
			MessageViewer.showError("Ops, confira suas senhas porque elas não estão iguais.");
			return Events.EMPTY;
		} else {
			ResponseEntity response = null;
			try {
				user.setPassword(Crypto.getInstance().encrypt(password));
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
				MessageViewer.showSuccess("Ok", "Seu perfil foi atualizado com sucesso.");
				return Events.EMPTY;
			}
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
			MessageViewer.showSuccess("Ok", "Seu perfil foi atualizado com sucesso.");
			return Events.EMPTY;
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

	public Base64 getBase64() {
		return base64;
	}

	public void setBase64(Base64 base64) {
		this.base64 = base64;
	}

	public UploadedFile getImg() {
		return img;
	}

	public void setImg(UploadedFile img) {
		this.img = img;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
