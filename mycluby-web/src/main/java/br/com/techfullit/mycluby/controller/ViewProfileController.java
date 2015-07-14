package br.com.techfullit.mycluby.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Event;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.common.utils.SessionHandler;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@Controller
@ViewScoped
@ManagedBean(name = "viewProfile")
public class ViewProfileController extends ContexController {

	public ViewProfileController() {
	}

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	private static final Log LOG = LogFactory.getLog(ViewProfileController.class);

	public void loadData() {
		user = (User) SessionHandler.get(Constants.USER_TO_VIEW);
	}
	public void loadUserContext() {
		user = (User) ContextHelper.getCurrentEntity();
	}

	private boolean currentUser;

	private boolean currentUserFriend;
	
	private List<Event> currentUserEvents;

	private User user;

	public User getUser() {
		return user;
	}

	public void addFriend(ActionEvent event) {
		User currentUser = (User) ContextHelper.getCurrentEntity();
		currentUser.getFriends().add(user);
		user.getFriends().add(currentUser);

		ResponseEntity response = null;
		try {
			response = accountService.updateUser(currentUser);
			response = accountService.updateUser(user);
			updateUserContext(currentUser);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Erro ao adicionar " + user.getFirstname() + " como seu amigo");
		}

		if (response.isSuccess()) {
			LOG.debug("Friend added with success");
			MessageViewer.showSuccess("", user.getFirstname() + " agora faz parte da sua rede de amigos.");
		} else {
			LOG.error("Erro ao adicionar " + user.getFirstname() + " como seu amigo");
			MessageViewer.showError("Erro ao adicionar " + user.getFirstname() + " como seu amigo");
		}
	}

	public void removeFriend(ActionEvent event) {
		User currentUser = (User) ContextHelper.getCurrentEntity();

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
			MessageViewer.showError("Erro ao remover " + user.getFirstname() + " da sua lista de amigos.");
		}

		if (response.isSuccess()) {
			LOG.debug("Friend added with success");
			MessageViewer.showSuccess("", user.getFirstname() + " agora não faz mais parte da sua rede de amigos.");
		} else {
			MessageViewer.showError("Erro ao remover " + user.getFirstname() + " como seu amigo");
		}
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isCurrentUser() {
		if (user.getId().equals(((User) ContextHelper.getCurrentEntity()).getId())) {
			currentUser = true;
		} else {
			currentUser = false;
		}
		return currentUser;
	}

	public void setCurrentUser(boolean isCurrentUser) {
		this.currentUser = isCurrentUser;
	}

	public boolean isCurrentUserFriend() {
		currentUserFriend = false;
		if (!user.getId().equals(((User) ContextHelper.getCurrentEntity()).getId())) {
			User currentUser = (User) ContextHelper.getCurrentEntity();
			for (User friend : currentUser.getFriends()) {
				if (friend.getId().equals(user.getId())) {
					currentUserFriend = true;
					break;
				} else {
					currentUserFriend = false;
				}
			}

		} else {
			currentUserFriend = false;
		}
		return currentUserFriend;
	}

	private void updateUserContext(User currentUser) throws Exception {
		ResponseEntity userUpdated = accountService.findUserByMail(currentUser.getMails().get(0).getValue());
		currentUser = (User) userUpdated.getObject();
		ContextHelper.setCurrentEntity(userUpdated.getObject());
	}

	public void setCurrentUserFriend(boolean isCurrentUserFriend) {
		this.currentUserFriend = isCurrentUserFriend;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public List<Event> getCurrentUserEvents() {
		if(user != null){
			currentUserEvents = new ArrayList<Event>();
			for (User u : user.getFriends()) {
				currentUserEvents.addAll(u.getEvents());
			}
			for (Establishment establishment : user.getFavEstablishments()) {
			    currentUserEvents.addAll(establishment.getEvents());
			}
		}
		
		Collections.sort(currentUserEvents, new Comparator<Event>() {
		        @Override
		        public int compare(Event  event1, Event  event2)
		        {
		            return  event1.compareTo(event2);
		        }
		    });
		
		return currentUserEvents;
	}

	public void setCurrentUserEvents(List<Event> currentUserEvents) {
		this.currentUserEvents = currentUserEvents;
	}
	
	
}
