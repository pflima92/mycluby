package br.com.techfullit.mycluby.services.employee.business.impl;

import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Employee;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Portal;
import br.com.techfullit.mycluby.common.models.ResponseCode;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.services.employee.business.EmployeeService;
import br.com.techfullit.mycluby.services.employee.dao.EmployeeDAO;

public class EmployeeServiceImpl implements EmployeeService {

	private static final Log LOG = LogFactory.getLog(EmployeeServiceImpl.class);

	@Resource(name = "resourcesProperties")
	private Properties props;

	@Autowired
	private EmployeeDAO dao;

	@Override
	@Transactional
	public ResponseEntity insert(Employee employee) throws Exception {
		Portal portal = ContextHelper.getPortalData();
		Establishment establishment = portal.getEstablishment();
		for (Employee emp : establishment.getEmployers()) {
		    if(emp.getLogin().equals(employee.getLogin())){
			return new ResponseEntity(ResponseCode.WARNING, "Já existe um login para o login digitado");
		    }
		}
		employee.setEstablishment(establishment);
		establishment.getEmployers().add(employee);
		dao.insert(employee);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY, portal);
	}

	@Override
	@Transactional
	public ResponseEntity update(Employee employee) {
		dao.update(employee);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity delete(Employee employee) {
		dao.delete(employee);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

}
