package br.com.techfullit.mycluby.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Card;
import br.com.techfullit.mycluby.common.models.Picture;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.common.utils.StringUtils;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@Controller
@ViewScoped
@ManagedBean(name = "updateMobileAccount")
public class UpdateMobileAccountController extends ContexController {

	public UpdateMobileAccountController() {
	}

	private static final Log LOG = LogFactory.getLog(UpdateMobileAccountController.class);

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	private String limitValue;

	private Card card;

	public void loadData() {
		if (user == null)
			user = (User) ContextHelper.getCurrentEntity();
		card = new Card();
		if (user.getAccountInfo().getLimitConsumeValue() != null) {
			limitValue = user.getAccountInfo().getLimitConsumeValue().toString();
		}
	}

	private User user;

	public String updateAccountContinue() {
		LOG.debug("Called method [updateAccountContinue]");
		if (!StringUtils.isBlank(limitValue)) {
			user.getAccountInfo().setLimitConsumeValue(new Double(limitValue));
		}
		ResponseEntity response = null;
		try {
			response = accountService.updateUser(user.getAccountInfo());
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

	public String addCard() {
card.setUser(user);
		user.getCards().add(card);

		ResponseEntity response = null;
		try {
			response = accountService.updateUser(user);
			updateUserContext();
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

	public void deleteCard(ActionEvent event) {
		try {
			card = (Card) event.getComponent().getAttributes().get("action");
			LOG.debug("Called method [addImage]");
			ResponseEntity response = null;
			try {
				accountService.deleteCard(card);
				updateUserContext();
			} catch (Exception e) {
				LOG.error(e.getMessage());
				MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Ops, tivemos um erro ao processar sua foto, tente novamente.");
		}
		card = new Card();
	}
	
	private void updateUserContext() throws Exception {
		ResponseEntity userUpdated = accountService.findUserByMail(user.getMails().get(0).getValue());
		user = (User) userUpdated.getObject();
		ContextHelper.setCurrentEntity(userUpdated.getObject());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getLimitValue() {
		return limitValue;
	}

	public void setLimitValue(String limitValue) {
		this.limitValue = limitValue;
	}

}
