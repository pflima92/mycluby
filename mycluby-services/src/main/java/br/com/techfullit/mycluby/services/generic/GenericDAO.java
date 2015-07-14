package br.com.techfullit.mycluby.services.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericDAO<PK extends Serializable, T> implements DAO<PK, T> {

	private static final Log LOG = LogFactory.getLog(GenericDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.techfullit.cas.services.generic.dao.DAO#insert(java.lang.T)
	 */
	@Override
	public void insert(T obj) throws DataException {
		LOG.debug("Called insert to " + getTypeClass().getSimpleName());
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		LOG.debug("Begin transaction");
		Transaction tx = ss.beginTransaction();
		ss.save(obj);
		tx.commit();
		LOG.debug("End transaction");
		if (ss.isOpen())
			ss.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.techfullit.cas.services.generic.dao.DAO#update(java.lang.T)
	 */
	@Override
	public void update(T obj) throws DataException {
		LOG.debug("Called update to " + getTypeClass().getSimpleName());
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		LOG.debug("Begin transaction");
		Transaction tx = ss.beginTransaction();
		ss.merge(obj);
		tx.commit();
		LOG.debug("End transaction");
		if (ss.isOpen())
			ss.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.techfullit.cas.services.generic.dao.DAO#delete(java.lang.T)
	 */
	@Override
	public void delete(T obj) throws DataException {
		LOG.debug("Called insert to " + getTypeClass().getSimpleName());
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		LOG.debug("Begin transaction");
		Transaction tx = ss.beginTransaction();
		ss.delete(obj);
		tx.commit();
		LOG.debug("End transaction");
		if (ss.isOpen())
			ss.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.techfullit.cas.services.generic.dao.DAO#findById(PK)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T findById(PK pk) {
		LOG.debug("Find " + getTypeClass().getSimpleName() + " byId [" + pk + "]");
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		LOG.debug("Begin transaction");
		Transaction tx = ss.beginTransaction();
		T t = (T) ss.load(getTypeClass(), pk);
		LOG.debug("End transaction");
		tx.commit();
		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.techfullit.cas.services.generic.dao.DAO#findAll(java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> classe) {
		LOG.debug("Find all " + getTypeClass().getSimpleName());
		org.hibernate.Session ss = this.sessionFactory.getCurrentSession();
		LOG.debug("Begin transaction");
		Transaction tx = ss.beginTransaction();
		Query query = ss.createQuery("From " + classe.getSimpleName() + " o");
		List<T> list = query.list();
		tx.commit();
		if (ss.isOpen())
			ss.close();
		LOG.debug("End transaction");
		return list;
	}

	private Class<?> getTypeClass() {
		Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
		return clazz;
	}

}