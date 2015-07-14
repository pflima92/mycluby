package br.com.techfullit.mycluby.services.establishment.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.techfullit.mycluby.common.models.Category;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.EstablishmentInfo;
import br.com.techfullit.mycluby.common.models.Mail;
import br.com.techfullit.mycluby.common.models.Product;
import br.com.techfullit.mycluby.services.establishment.dao.EstablishmentDAO;
import br.com.techfullit.mycluby.services.generic.GenericDAO;

@Repository
public class EstablishmentDAOImpl extends GenericDAO<Integer, EstablishmentInfo> implements EstablishmentDAO {

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
	public Establishment findEstablishmentByMail(String login) {
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
				return mail.getEstablishment();
			}
		}
		if (ss.isOpen())
			ss.close();
		return null;
	}

	@Override
	public EstablishmentInfo findEstablishmentByPattern(String pattern) {
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		Query query = ss.getNamedQuery("EstablishmentInfo.findByPattern");
		query.setParameter("pattern", pattern);
		List<EstablishmentInfo> list = query.list();
		EstablishmentInfo establishment = null;
		if (list.size() == 1) {
			establishment = list.get(0);
			if (establishment.getPortalPattern().equals(pattern)) {
				tx.commit();
				if (ss.isOpen())
					ss.close();
				return establishment;
			}
		}
		if (ss.isOpen())
			ss.close();
		return null;
	}

	@Override
	public void updateEstablishment(Establishment user) {
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
	public List<Establishment> findEstablishmentByName(String name) {
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		Query query = ss.getNamedQuery("Establishment.findByName");
		query.setParameter("name", name);
		List<Establishment> list = query.list();
		tx.commit();
		if (ss.isOpen())
			ss.close();
		return list;
	}

	@Override
	public void updateCategory(Category category) throws Exception {
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		ss.merge(category);
		tx.commit();
		if (ss.isOpen())
			ss.close();
	}

	@Override
	public void deleteCategory(Category category) throws Exception {
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		ss.delete(category);
		tx.commit();
		if (ss.isOpen())
			ss.close();
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		ss.merge(product);
		tx.commit();
		if (ss.isOpen())
			ss.close();
	}

	@Override
	public void deleteProduct(Product product) throws Exception {
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		ss.delete(product);
		tx.commit();
		if (ss.isOpen())
			ss.close();
	}

}
