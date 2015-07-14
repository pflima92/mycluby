package br.com.techfullit.mycluby.services.onsite.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.OnSite;
import br.com.techfullit.mycluby.common.models.Sale;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.services.generic.GenericDAO;
import br.com.techfullit.mycluby.services.onsite.dao.OnSiteDAO;

@Repository
public class OnSiteDAOImpl extends GenericDAO<Integer, OnSite> implements OnSiteDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
    public List<OnSite> findOnSiteSessionsByUser(User u) {
	    org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	    Transaction tx = ss.beginTransaction();
		Query query = ss.getNamedQuery("OnSite.findOnSiteSessionsByUser");
		query.setParameter("user_id", u.getId());
		List<OnSite> list = query.list();
		tx.commit();
		if (ss.isOpen())
			ss.close();
		return list;
	}

	@Override
	public List<OnSite> findOnSiteSessionsByEstablishment(Establishment e) {
	    org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
	    Transaction tx = ss.beginTransaction();
		Query query = ss.getNamedQuery("OnSite.findOnSiteSessionsByEstablishment");
		query.setParameter("establishment_id", e.getId());
		List<OnSite> list = query.list();
		tx.commit();
		if (ss.isOpen())
			ss.close();
		return list;
	}

	@Override
	public void insertSale(Sale sale) {
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		ss.save(sale);
		tx.commit();
		if (ss.isOpen())
		    ss.close();
	    }

	@Override
	public void updateSale(Sale sale) {
	    org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		Transaction tx = ss.beginTransaction();
		ss.merge(sale);
		tx.commit();
		if (ss.isOpen())
		    ss.close();
	    
	}

}
