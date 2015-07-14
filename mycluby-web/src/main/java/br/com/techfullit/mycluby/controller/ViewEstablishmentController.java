package br.com.techfullit.mycluby.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.common.utils.SessionHandler;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@Controller
@SessionScoped
@ManagedBean(name = "viewEstablishment")
public class ViewEstablishmentController extends ContexController {

	public ViewEstablishmentController() {
	}

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	private static final Log LOG = LogFactory.getLog(ViewEstablishmentController.class);

	public void loadData() {
		establishment = (Establishment) SessionHandler.get(Constants.ESTABLISHMENT_TO_VIEW);
	}

	public void loadCurrentEstablishment() {
		establishment = (Establishment) ContextHelper.getCurrentEntity();
	}

	private boolean currentEstablishment;

	private boolean currentEstablishmentFriend;

	private Establishment establishment;

	public Establishment getEstablishment() {
		return establishment;
	}

	public void addEstablishment(ActionEvent event) {
		User user = (User) ContextHelper.getCurrentEntity();
		user.getFavEstablishments().add(establishment);

		ResponseEntity response = null;
		try {
			response = accountService.updateUser(user);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Erro ao adicionar " + establishment.getName() + " aos seus favoritos.");
		}

		if (response.isSuccess()) {
			LOG.debug("Friend added with success");
			MessageViewer.showSuccess("", establishment.getName() + " foi adicionaod aos favoritos.");
		} else {
			LOG.error("Erro ao adicionar " + establishment.getName());
			MessageViewer.showError("Erro ao adicionar " + establishment.getName() + " aos seus favoritos.");
		}
	}

	public void removeEstablishment(ActionEvent event) {
		User user = (User) ContextHelper.getCurrentEntity();
		List<Establishment> favEstablishments = new ArrayList<Establishment>();
		for (Establishment eUser : user.getFavEstablishments()) {
			if (!eUser.getId().equals(establishment.getId())) {
				favEstablishments.add(eUser);
			}
		}
		user.setFavEstablishments(favEstablishments);

		ResponseEntity response = null;
		try {
			response = accountService.updateUser(user);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Erro ao adicionar " + establishment.getName() + " como seu amigo");
		}

		if (response.isSuccess()) {
			LOG.debug("Friend added with success");
			MessageViewer.showSuccess("", establishment.getName() + " foi removido dos favoritos.");
		} else {
			LOG.error("Erro ao adicionar " + establishment.getName() + " como seu amigo");
			MessageViewer.showError("Erro ao remover " + establishment.getName() + " aos seus favoritos.");
		}
	}

	public void setEstablishment(Establishment Establishment) {
		this.establishment = Establishment;
	}

	public boolean isCurrentEstablishment() {
		currentEstablishment = false;
		if (ContextHelper.getCurrentEntity() instanceof Establishment) {
			if (establishment.getId().equals(((Establishment) ContextHelper.getCurrentEntity()).getId())) {
				currentEstablishment = true;
			} else {
				currentEstablishment = false;
			}
		}
		return currentEstablishment;
	}

	public void setCurrentEstablishment(boolean isCurrentEstablishment) {
		this.currentEstablishment = isCurrentEstablishment;
	}

	public boolean isCurrentEstablishmentFriend() {
		currentEstablishmentFriend = false;
		if (ContextHelper.getCurrentEntity() instanceof User) {
			User user = (User) ContextHelper.getCurrentEntity();
			for (Establishment eUser : user.getFavEstablishments()) {
				if (establishment.getId().equals(eUser.getId())) {
					return true;
				}
			}
		}
		return currentEstablishmentFriend;
	}

	public void setCurrentEstablishmentFriend(boolean isCurrentEstablishmentFriend) {
		this.currentEstablishmentFriend = isCurrentEstablishmentFriend;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
}
