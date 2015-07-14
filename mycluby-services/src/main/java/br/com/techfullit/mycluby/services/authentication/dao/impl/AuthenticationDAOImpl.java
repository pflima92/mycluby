package br.com.techfullit.mycluby.services.authentication.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Mail;
import br.com.techfullit.mycluby.services.authentication.dao.AuthenticationDAO;

@Repository
public class AuthenticationDAOImpl implements AuthenticationDAO {

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
	public Mail findEntityByMail(String login) {
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
				return mail;
			}
		}
		if (ss.isOpen())
			ss.close();
		return null;
	}

	@Override
	public Establishment findEstablishmentByPattern(String pattern) {
		return null;
	}

}
