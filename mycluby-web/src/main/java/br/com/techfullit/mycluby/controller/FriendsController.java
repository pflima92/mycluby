package br.com.techfullit.mycluby.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.common.utils.SessionHandler;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@Controller
@ViewScoped
@ManagedBean(name = "friends")
public class FriendsController extends ContexController {

	public FriendsController() {
	}
	
	@PostConstruct
	private void init(){
	}

	private static final Log LOG = LogFactory.getLog(FriendsController.class);
	
	private User user;

	private List<User> friends;

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public List<User> getFriends() {
		user = (User) ContextHelper.getCurrentEntity();
		friends = new ArrayList<User>();
		friends.addAll(user.getFriends());
		return friends;
	}

	public void removeFriend(ActionEvent event) {
		User currentUser = (User) ContextHelper.getCurrentEntity();
		user = (User) event.getComponent().getAttributes().get("action");

		List<User> myFriends = new ArrayList<User>();
		myFriends.addAll(currentUser.getFriends());
		for (User u : myFriends) {
			if (u.getId().equals(user.getId())) {
				currentUser.getFriends().remove(u);
			}
		}

		List<User> friendFriends = new ArrayList<User>();
		friendFriends.addAll(user.getFriends());
		for (User u : friendFriends) {
			if (u.getId().equals(currentUser.getId())) {
				user.getFriends().remove(u);
			}
		}

		ResponseEntity response = null;
		try {
			response = accountService.updateUser(currentUser);
			response = accountService.updateUser(user);
			updateUserContext(currentUser);
			SessionHandler.put(Constants.USER_TO_VIEW,user);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Erro ao remover " + user.getFirstname() + " como seu amigo");
		}

		if (response.isSuccess()) {
			LOG.debug("Friend added with success");
			MessageViewer.showSuccess("", user.getFirstname() + " agora não faz mais parte da sua rede de amigos.");
		} else {
			LOG.error("Erro ao adicionar " + user.getFirstname() + " como seu amigo");
			MessageViewer.showError("Erro ao remover " + user.getFirstname() + " como seu amigo");
		}
	}
	
	private void updateUserContext(User currentUser) throws Exception {
		ResponseEntity userUpdated = accountService.findUserByMail(currentUser.getMails().get(0).getValue());
		currentUser = (User) userUpdated.getObject();
		ContextHelper.setCurrentEntity(userUpdated.getObject());
	}
	
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	private void updateUserContext() throws Exception {
		ResponseEntity userUpdated = accountService.findUserByMail(user.getMails().get(0).getValue());
		user = (User) userUpdated.getObject();
		ContextHelper.setCurrentEntity(userUpdated.getObject());
	}
}
