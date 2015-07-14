package br.com.techfullit.mycluby.services.account.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.techfullit.mycluby.common.models.AccountInfo;
import br.com.techfullit.mycluby.common.models.Card;
import br.com.techfullit.mycluby.common.models.Event;
import br.com.techfullit.mycluby.common.models.Mail;
import br.com.techfullit.mycluby.common.models.Picture;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.services.account.dao.AccountDAO;
import br.com.techfullit.mycluby.services.generic.GenericDAO;

@Repository
public class AccountDAOImpl extends GenericDAO<Integer, AccountInfo> implements AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /*
     * (non-Javadoc)
     * 
     * @see br.com.techfullit.mycluby.services.account.dao.impl.AccountDAO#
     * findUserByLogin(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public User findUserByMail(String login) {
	org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	Transaction tx = ss.beginTransaction();
	Query query = ss.getNamedQuery("Mail.findByValue");
	query.setParameter("mail", login);
	List<Mail> list = query.list();
	Mail mail = null;
	if (list.size() == 1) {
	    mail = list.get(0);
	    if (mail.isActiveLogin()) {
		tx.commit();
		if (ss.isOpen())
		    ss.close();
		return mail.getUser();
	    }
	}
	tx.commit();
	if (ss.isOpen())
	    ss.close();
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.com.techfullit.mycluby.services.account.dao.impl.AccountDAO#
     * findUserByLogin(java.lang.String)
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> findUsersByMail(String login) {
	org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	Transaction tx = ss.beginTransaction();
	Query query = ss.getNamedQuery("Mail.findByValue");
	query.setParameter("mail", login);
	List<Mail> list = query.list();
	tx.commit();
	ArrayList<User> users = new ArrayList<User>();
	for (Mail mail : list) {
	    users.add(mail.getUser());
	}
	if (ss.isOpen())
	    ss.close();
	return users;
    }

    @Override
    public void updateUser(User user) {
	org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	Transaction tx = ss.beginTransaction();
	ss.merge(user);
	tx.commit();
	if (ss.isOpen())
	    ss.close();
    }

    public SessionFactory getSessionFactory() {
	return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean verifyMailRegistered(String mail) {
	org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	Transaction tx = ss.beginTransaction();
	Query query = ss.getNamedQuery("Mail.findByValue");
	query.setParameter("mail", mail);
	List<Mail> list = query.list();
	tx.commit();
	if (ss.isOpen())
	    ss.close();
	if (list.size() > 0) {
	    return true;
	} else {
	    return false;
	}

    }

    @Override
    public void deletePicture(Picture picture) {
	org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	Transaction tx = ss.beginTransaction();
	ss.delete(picture);
	tx.commit();
	if (ss.isOpen())
	    ss.close();

    }

    @Override
    public void updateEvent(Event event) {
	org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	Transaction tx = ss.beginTransaction();
	ss.merge(event);
	tx.commit();
	if (ss.isOpen())
	    ss.close();
	
    }

    @Override
    public void deleteEvent(Event event) {
	org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	Transaction tx = ss.beginTransaction();
	ss.delete(event);
	tx.commit();
	if (ss.isOpen())
	    ss.close();
	
    }

	@Override
	public void deleteCard(Card card) {
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		ss.delete(card);
		tx.commit();
		if (ss.isOpen())
		    ss.close();
		
		
	}
}
