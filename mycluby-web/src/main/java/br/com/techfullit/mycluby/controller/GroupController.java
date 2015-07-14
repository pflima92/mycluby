package br.com.techfullit.mycluby.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Group;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@Controller
@ViewScoped
@ManagedBean(name = "groups")
public class GroupController extends ContexController {

	public void loadData() {
		if (user == null)
			user = (User) ContextHelper.getCurrentEntity();
	}

	public GroupController() {
		resetGroup();
	}

	private void resetGroup() {
		group = new Group();
		friendsSelected = new ArrayList<Integer>();
	}

	private User user;

	public void addGroup(ActionEvent event) {
		try {

			LOG.debug("Called method [addImage]");
			
			group.setCreation(new Date());

			for (Integer integer : friendsSelected) {
				for (User u : user.getFriends()) {
					if(u.getId().equals(integer)){
						group.getUsers().add(u);
						break;
					}
				}
			}
			user.getGroups().add(group);
			ResponseEntity response = null;
			try {
				response = accountService.updateUser(user);
			} catch (Exception e) {
				LOG.error(e.getMessage());
				MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
			}

			if (!response.isSuccess()) {
				LOG.debug(response.getDescStatus());
				MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
			} else {
				MessageViewer.showSuccess("Ok", "Seu perfil foi atualizado com sucesso.");
			}
			updateUserContext();
			resetGroup();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			MessageViewer.showError("Ops, tivemos um erro ao processar sua foto, tente novamente.");
		}
	}
	
	public List<User> findCurrentFriends(String query) {
		return user.getFriends();
	}

	private static final Log LOG = LogFactory.getLog(GroupController.class);

	private Group group;

	private List<Group> groups;

	private List<Integer> friendsSelected;

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public List<Integer> getFriendsSelected() {
		if (friendsSelected == null)
			friendsSelected = new ArrayList<Integer>();
		return friendsSelected;
	}

	public void setFriendsSelected(List<Integer> friendsSelected) {
		this.friendsSelected = friendsSelected;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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

}
