package br.com.techfullit.mycluby.services.account.dao;

import java.util.List;

import br.com.techfullit.mycluby.common.models.AccountInfo;
import br.com.techfullit.mycluby.common.models.Card;
import br.com.techfullit.mycluby.common.models.Event;
import br.com.techfullit.mycluby.common.models.Picture;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.services.generic.DAO;

public interface AccountDAO extends DAO<Integer, AccountInfo> {

	public abstract User findUserByMail(String login);

	public abstract List<User> findUsersByMail(String login);

	public abstract void updateUser(User user);
	
	public abstract void deletePicture(Picture picture);

	public abstract void updateEvent(Event event);
	
	public abstract void deleteEvent(Event picture);
	
	public abstract boolean verifyMailRegistered(String mail);

	public abstract void deleteCard(Card card);

}