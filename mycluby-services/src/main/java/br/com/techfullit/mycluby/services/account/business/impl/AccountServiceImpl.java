package br.com.techfullit.mycluby.services.account.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.AccountInfo;
import br.com.techfullit.mycluby.common.models.Card;
import br.com.techfullit.mycluby.common.models.Event;
import br.com.techfullit.mycluby.common.models.Picture;
import br.com.techfullit.mycluby.common.models.ResponseCode;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.Role;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.Crypto;
import br.com.techfullit.mycluby.common.utils.MessageUtils;
import br.com.techfullit.mycluby.services.account.business.AccountService;
import br.com.techfullit.mycluby.services.account.dao.AccountDAO;

public class AccountServiceImpl implements AccountService {

    private static final Log LOG = LogFactory.getLog(AccountServiceImpl.class);

    @Autowired
    private AccountDAO dao;

    @Resource(name = "resourcesProperties")
    private Properties props;

    @Override
    @Transactional
    public ResponseEntity createUserAccount(User user) throws Exception {

	user.getRoles().add(Role.ROLE_USER);

	LOG.debug("Crypt Password to save user");
	user.setPassword(Crypto.getInstance().encrypt(user.getPassword()));
	LOG.debug("Crypted with success");

	Locale browserLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	AccountInfo accountInfo = new AccountInfo();
	accountInfo.setAccountActive(false);
	accountInfo.setAccountCreation(new Date());
	accountInfo.setActiveEmailReceiver(true);
	accountInfo.setFirstStep(true);
	accountInfo.setTimezone(browserLocale.getLanguage());
	accountInfo.setLanguage(browserLocale.getLanguage());
	try {
	    accountInfo.setUserkey(Crypto.getInstance().encrypt(user.getMails().get(0).getValue()));
	} catch (Exception e) {
	    throw new Exception("Erro ao gerar nova chave do usuário.");
	}

	user.setImage(Base64.decode((String) props.get(Constants.DEFAUL_PROFILE_IMAGE)));
	user.setAccountInfo(accountInfo);
	accountInfo.setUser(user);

	dao.insert(accountInfo);
	return new ResponseEntity(ResponseCode.SUCCESS, accountInfo.getUserkey());
    }

    @Override
    @Transactional
    public ResponseEntity updateUser(Object user) {

	if (user instanceof AccountInfo) {
	    dao.update((AccountInfo) user);
	} else {
	    dao.updateUser((User) user);
	}
	return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
    }

    public AccountDAO getDao() {
	return dao;
    }

    public void setDao(AccountDAO dao) {
	this.dao = dao;
    }

    @Override
    public ResponseEntity findAllUsers() throws Exception {
	ResponseEntity response = new ResponseEntity();
	List<AccountInfo> accounts = dao.findAll(AccountInfo.class);
	List<User> userResponse = new ArrayList<User>();
	if (accounts != null) {
	    for (AccountInfo accountInfo : accounts) {
		userResponse.add(accountInfo.getUser());
	    }

	    response.setObject(userResponse);
	    response.setStatus(ResponseCode.SUCCESS);
	} else {
	    response.setStatus(ResponseCode.ERROR);
	    response.setDescStatus(MessageUtils.getMessage(Constants.LOGIN_INVALID));
	}
	return response;

    }

    @Override
    public ResponseEntity findUsersByMail(String mail) throws Exception {
	ResponseEntity response = new ResponseEntity();
	List<User> userResponse = dao.findUsersByMail(mail);
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
    public ResponseEntity findUserByMail(String mail) throws Exception {
	ResponseEntity response = new ResponseEntity();
	User userResponse = dao.findUserByMail(mail);
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
    public ResponseEntity verifyEmailRegistered(String mail) throws Exception {
	ResponseEntity response = new ResponseEntity();
	boolean userResponse = dao.verifyMailRegistered(mail);
	response.setObject(userResponse);
	response.setStatus(ResponseCode.SUCCESS);
	return response;
    }

    @Override
    @Transactional
    public ResponseEntity deletePicture(Picture picture) throws Exception {
	ResponseEntity response = new ResponseEntity();
	dao.deletePicture(picture);
	response.setStatus(ResponseCode.SUCCESS);
	return response;
    }

    @Override
    public ResponseEntity updateEvent(Event event) {
	ResponseEntity response = new ResponseEntity();
	dao.updateEvent(event);
	response.setStatus(ResponseCode.SUCCESS);
	return response;
	
    }

    @Override
    public ResponseEntity deleteEvent(Event event) {
	ResponseEntity response = new ResponseEntity();
	dao.deleteEvent(event);
	response.setStatus(ResponseCode.SUCCESS);
	return response;
    }

	@Override
	public ResponseEntity deleteCard(Card card) {
		ResponseEntity response = new ResponseEntity();
		dao.deleteCard(card);
		response.setStatus(ResponseCode.SUCCESS);
		return response;
	}

}
