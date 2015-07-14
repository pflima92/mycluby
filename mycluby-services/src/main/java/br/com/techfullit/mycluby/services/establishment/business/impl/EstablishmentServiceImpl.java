package br.com.techfullit.mycluby.services.establishment.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Category;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.EstablishmentInfo;
import br.com.techfullit.mycluby.common.models.Product;
import br.com.techfullit.mycluby.common.models.ResponseCode;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.Role;
import br.com.techfullit.mycluby.common.utils.Crypto;
import br.com.techfullit.mycluby.common.utils.MessageUtils;
import br.com.techfullit.mycluby.services.establishment.business.EstablishmentService;
import br.com.techfullit.mycluby.services.establishment.dao.EstablishmentDAO;

public class EstablishmentServiceImpl implements EstablishmentService {

	private static final Log LOG = LogFactory.getLog(EstablishmentServiceImpl.class);

	@Resource(name = "resourcesProperties")
	private Properties props;

	@Autowired
	private EstablishmentDAO dao;

	@Override
	@Transactional
	public ResponseEntity createEstablishmentAccount(Establishment establishment) throws Exception {

		LOG.debug("Crypt Password to save user");
		establishment.setPassword(Crypto.getInstance().encrypt(establishment.getPassword()));
		LOG.debug("Crypted with success");
		establishment.getRoles().add(Role.ROLE_ESTABLISHMENT);
		establishment.setImage(Base64.decode((String) props.get(Constants.DEFAUL_PROFILE_IMAGE)));

		EstablishmentInfo establishmentInfo = new EstablishmentInfo();
		establishmentInfo.setAccountActive(false);
		establishmentInfo.setAccountCreation(new Date());
		establishmentInfo.setFirstStep(true);
		establishmentInfo.setKeyRecovery(Crypto.getInstance().encrypt(
				establishment.getName() + establishment.getMails().get(0).getValue()));
		establishmentInfo.setPortalActive(false);
		establishmentInfo.setPortalName(establishment.getName());
		establishmentInfo.setPortalActive(false);

		establishment.setEstablishmentInfo(establishmentInfo);
		

		// Create default Categories
		Category svce = new Category();
		svce.setDescription("Categoria de Serviços");
		svce.setName("Serviços");
		svce.setEstablishment(establishment);

		// Create default Categories
		Category drink = new Category();
		drink.setDescription("Categoria de Bebidas e Drinks");
		drink.setName("Bebidas e Drinks");
		drink.setEstablishment(establishment);

		// Create default Categories
		Category food = new Category();
		food.setDescription("Categoria de Alimentos");
		food.setName("Alimentos");
		food.setEstablishment(establishment);

		establishment.getCategories().add(svce);
		establishment.getCategories().add(food);
		establishment.getCategories().add(drink);
		
		establishmentInfo.setEstablishment(establishment);

		dao.insert(establishmentInfo);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity updateEstablishment(Object establishment) {

		if (establishment instanceof EstablishmentInfo) {
			dao.update((EstablishmentInfo) establishment);
		} else {
			dao.updateEstablishment((Establishment) establishment);
		}
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity findAllEstablishment() throws Exception {
		ResponseEntity response = new ResponseEntity();
		List<EstablishmentInfo> accounts = dao.findAll(EstablishmentInfo.class);
		List<Establishment> establishmentResponse = new ArrayList<Establishment>();
		if (accounts != null) {
			for (EstablishmentInfo accountInfo : accounts) {
				establishmentResponse.add(accountInfo.getEstablishment());
			}

			response.setObject(establishmentResponse);
			response.setStatus(ResponseCode.SUCCESS);
		} else {
			response.setStatus(ResponseCode.ERROR);
			response.setDescStatus(MessageUtils.getMessage(Constants.LOGIN_INVALID));
		}
		return response;

	}

	@Override
	@Transactional
	public ResponseEntity findEstablishmentByPattern(String pattern) throws Exception {
		ResponseEntity response = new ResponseEntity();
		EstablishmentInfo establishment = dao.findEstablishmentByPattern(pattern);
		if (establishment != null) {
			response.setObject(establishment);
			response.setStatus(ResponseCode.SUCCESS);
		} else {
			response.setStatus(ResponseCode.ERROR);
			response.setDescStatus(MessageUtils.getMessage(Constants.LOGIN_INVALID));
		}
		return response;
	}

	@Override
	@Transactional
	public ResponseEntity findEstablishmentByMail(String mail) throws Exception {
		ResponseEntity response = new ResponseEntity();
		Establishment userResponse = dao.findEstablishmentByMail(mail);
		if (userResponse != null) {
			response.setObject(userResponse);
			response.setStatus(ResponseCode.SUCCESS);
		} else {
			response.setStatus(ResponseCode.ERROR);
			response.setDescStatus(MessageUtils.getMessage(Constants.LOGIN_INVALID));
		}
		return response;
	}

	@Override
	@Transactional
	public ResponseEntity findEstablishmentByName(String name) throws Exception {
		ResponseEntity response = new ResponseEntity();
		Establishment userResponse = dao.findEstablishmentByMail(name);
		if (userResponse != null) {
			response.setObject(userResponse);
			response.setStatus(ResponseCode.SUCCESS);
		} else {
			response.setStatus(ResponseCode.ERROR);
			response.setDescStatus(MessageUtils.getMessage(Constants.LOGIN_INVALID));
		}
		return response;
	}

	@Override
	@Transactional
	public ResponseEntity updateCategory(Category category) throws Exception {
		dao.updateCategory(category);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity deleteCategory(Category category) throws Exception {
		dao.deleteCategory(category);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity updateProduct(Product product) throws Exception {
		dao.updateProduct(product);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity deleteProduct(Product product) throws Exception {
		dao.deleteProduct(product);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity refreshEstablishment(Establishment establishment) throws Exception {
		ResponseEntity response = new ResponseEntity();
		EstablishmentInfo eInfo = dao.findEstablishmentByPattern(establishment.getEstablishmentInfo()
				.getPortalPattern());
		if (eInfo != null) {
			response.setObject(eInfo.getEstablishment());
			response.setStatus(ResponseCode.SUCCESS);
		} else {
			response.setStatus(ResponseCode.ERROR);
			response.setDescStatus(MessageUtils.getMessage(Constants.LOGIN_INVALID));
		}
		return response;
	}

}
