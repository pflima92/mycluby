package br.com.techfullit.mycluby.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@Controller
@ViewScoped
@ManagedBean(name = "findFriend")
public class FindFriendController extends ContexController {

	public FindFriendController() {
	}

	private static final Log LOG = LogFactory.getLog(FindFriendController.class);

	private List<User> friends;

	private String mail;

	public void loadData() {
		if (friends == null) {
			friends = findAllNotFriendsUsers();
		}
	}

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	private User userSelected;

	@PostConstruct
	public void init() {
	}

	private List<User> findAllNotFriendsUsers() {
		ResponseEntity response = null;
		try {
			response = accountService.findAllUsers();
			if (response.isSuccess()) {
				ArrayList<User> users = new ArrayList<>();
				for (User user : (ArrayList<User>) response.getObject()) {
					if (!user.getId().equals(((User) ContextHelper.getCurrentEntity()).getId())) {
						boolean isFriend = false;
						for (User friend : ((User) ContextHelper.getCurrentEntity()).getFriends()) {
							if (friend.getId().equals(user.getId())) {
								isFriend = true;
								break;
							}
						}
						if (!isFriend) {
							users.add(user);
						}
					}
				}
				return users;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Deu erro ao recuperar os usuário do MyClub");
		}
		return null;
	}

	public boolean userSelectedIsFriendCurrentUser(String id) {
		boolean isFriend = false;
		for (User friend : ((User) ContextHelper.getCurrentEntity()).getFriends()) {
			if (friend.getId().equals(new Integer(id))) {
				isFriend = true;
				break;
			}
		}
		return isFriend;
	}

	@SuppressWarnings("unchecked")
	public void findByMail(ActionEvent event) {
		LOG.debug("Call findByMail");
		ResponseEntity response = null;
		try {
			response = accountService.findUsersByMail(mail);
			if (response.isSuccess()) {
				friends = (ArrayList<User>) response.getObject();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Deu erro ao recuperar os usuário do MyClub");
		}

	}

	public void addFriend(ActionEvent event) {

		userSelected = (User) event.getComponent().getAttributes().get("action");

		User currentUser = (User) ContextHelper.getCurrentEntity();
		currentUser.getFriends().add(userSelected);
		ResponseEntity response = null;
		try {
			response = accountService.updateUser(currentUser);
			ContextHelper.setCurrentEntity(currentUser);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Erro ao adicionar " + userSelected.getFirstname() + " como seu amigo");
		}

		userSelected.getFriends().add(currentUser);
		try {
			response = accountService.updateUser(userSelected);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Erro ao adicionar " + userSelected.getFirstname() + " como seu amigo");
		}

		if (response.isSuccess()) {
			LOG.debug("Friend added with success");
			MessageViewer.showSuccess("", userSelected.getFirstname() + " agora faz parte da sua rede de amigos.");
		} else {
			LOG.error("Erro ao adicionar " + userSelected.getFirstname() + " como seu amigo");
			MessageViewer.showError("Erro ao adicionar " + userSelected.getFirstname() + " como seu amigo");
		}
	}

	public void verifyMail(AjaxBehaviorEvent event) {
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
