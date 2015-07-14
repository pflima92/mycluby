package br.com.techfullit.mycluby.services.employee.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.techfullit.mycluby.common.models.Employee;
import br.com.techfullit.mycluby.services.employee.dao.EmployeeDAO;
import br.com.techfullit.mycluby.services.generic.GenericDAO;

@Repository
public class EmployeeDAOImpl extends GenericDAO<Integer, Employee> implements EmployeeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
