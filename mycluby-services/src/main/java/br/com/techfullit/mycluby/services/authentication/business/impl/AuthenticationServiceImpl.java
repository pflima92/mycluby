package br.com.techfullit.mycluby.services.authentication.business.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Employee;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Mail;
import br.com.techfullit.mycluby.common.models.Portal;
import br.com.techfullit.mycluby.common.models.ResponseCode;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.Role;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.Crypto;
import br.com.techfullit.mycluby.common.utils.MessageUtils;
import br.com.techfullit.mycluby.common.utils.SessionHandler;
import br.com.techfullit.mycluby.services.account.dao.AccountDAO;
import br.com.techfullit.mycluby.services.authentication.business.AuthenticationService;
import br.com.techfullit.mycluby.services.authentication.dao.AuthenticationDAO;
import br.com.techfullit.mycluby.services.establishment.dao.EstablishmentDAO;

public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationDAO authenticationDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private EstablishmentDAO establishmentDAO;


	private static final Log LOG = LogFactory
			.getLog(AuthenticationService.class);

	public ResponseEntity validateDatabaseAuthentication(String login,
			String password) throws Exception {

		LOG.debug("Called validateDatabaseAuthentication");

		ResponseEntity response = new ResponseEntity();
		Mail daoResponse = authenticationDAO.findEntityByMail(login);

		if (daoResponse != null) {
			if (daoResponse.isUser()) {
				User userResponse = (User) daoResponse.getEntity();
				if (userResponse != null
						&& userResponse.getPassword().equals(
								Crypto.getInstance().encrypt(password))) {
					response.setObject(userResponse);
					response.setStatus(ResponseCode.SUCCESS);
				} else {
					response.setStatus(ResponseCode.ERROR);
					response.setDescStatus(MessageUtils
							.getMessage(Constants.LOGIN_INVALID));
				}
			} else if (daoResponse.isEstablishment()) {
				Establishment establishmentResponse = (Establishment) daoResponse
						.getEntity();
				if (establishmentResponse != null
						&& establishmentResponse.getPassword().equals(
								Crypto.getInstance().encrypt(password))) {
					response.setObject(establishmentResponse);
					response.setStatus(ResponseCode.SUCCESS);
				} else {
					response.setStatus(ResponseCode.ERROR);
					response.setDescStatus(MessageUtils
							.getMessage(Constants.LOGIN_INVALID));
				}
			} else {
				response.setStatus(ResponseCode.ERROR);
				response.setDescStatus(MessageUtils.getMessage(Constants.ERROR));
			}
		} else {
			response.setStatus(ResponseCode.ERROR);
			response.setDescStatus(MessageUtils.getMessage(Constants.LOGIN_INVALID));
		}

		return response;
	}

	@Override
	public ResponseEntity validatePortalAuthentication(Portal portal,
			String login, String password) throws Exception {

		password = Crypto.getInstance().encrypt(password);

		LOG.debug("Validate login with principal Portal User");
		String loginAdmin = portal.getEstablishment().getMails().get(0)
				.getValue();
		String passAdmin = portal.getEstablishment().getPassword();

		ResponseEntity responseEntity = null;
		if (login.equals(loginAdmin) && password.equals(passAdmin)) {
			LOG.debug("Using Staff with Admin user");
			Employee staff = new Employee();
			staff.getRoles().add(Role.ROLE_LOGGED);
			staff.setLogin(loginAdmin);
			staff.setName(loginAdmin);
			portal.setCurrentStaff(staff);
			portal.setLogged(true);
			SessionHandler.put(Constants.PORTAL_DATA, portal);
			responseEntity = new ResponseEntity(ResponseCode.SUCCESS,
					Constants.EMPTY);
			return responseEntity;
		}
		for (Employee e : portal.getEstablishment().getEmployers()) {
			if(e.getLogin().equals(login) && e.getPassword().equals(password)){
				portal.setCurrentStaff(e);
				portal.setLogged(true);
				SessionHandler.put(Constants.PORTAL_DATA, portal);
				responseEntity = new ResponseEntity(ResponseCode.SUCCESS,
						Constants.EMPTY);
				return responseEntity;
			}
		}
		
		responseEntity = new ResponseEntity(ResponseCode.ERROR, "Usuário ou Senha incorreto.");
		return responseEntity;
	}

	public AuthenticationDAO getAuthenticationDAO() {
		return authenticationDAO;
	}

	public void setAuthenticationDAO(AuthenticationDAO authenticationDAO) {
		this.authenticationDAO = authenticationDAO;
	}

	@Override
	public ResponseEntity requestForgivePass(String login) throws Exception {
		LOG.debug("Called validateDatabaseAuthentication");

		ResponseEntity response = new ResponseEntity();
		Mail daoResponse = authenticationDAO.findEntityByMail(login);

		if (daoResponse.isValidEntity()) {
			if (daoResponse.isUser()) {
				User userResponse = (User) daoResponse.getEntity();
				String password = userResponse.getAccountInfo().getUserkey().substring(3,12);
				userResponse.setPassword(Crypto.getInstance().encrypt(password));
				System.out.println(password);
				accountDAO.updateUser(userResponse);
				response.setObject(password);
				response.setStatus(ResponseCode.SUCCESS);
			} else if (daoResponse.isEstablishment()) {
				Establishment establishmentResponse = (Establishment) daoResponse
						.getEntity();
				String password = establishmentResponse.getEstablishmentInfo().getKeyRecovery().substring(3,12);
				establishmentResponse.setPassword(Crypto.getInstance().encrypt(password));
				establishmentDAO.updateEstablishment(establishmentResponse);
				response.setObject(password);
				response.setStatus(ResponseCode.SUCCESS);
			} else {
				response.setStatus(ResponseCode.ERROR);
				response.setDescStatus(MessageUtils.getMessage(Constants.ERROR));
			}
		} else {
			response.setStatus(ResponseCode.ERROR);
			response.setDescStatus(MessageUtils.getMessage(Constants.ERROR));
		}

		return response;
	}
	
	

}
