package br.com.techfullit.mycluby.services.onsite.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Consume;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.OnSite;
import br.com.techfullit.mycluby.common.models.ResponseCode;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.Sale;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.services.onsite.business.OnSiteService;
import br.com.techfullit.mycluby.services.onsite.dao.OnSiteDAO;

public class OnSiteServiceImpl implements OnSiteService {

	private static final Log LOG = LogFactory.getLog(OnSiteServiceImpl.class);

	@Resource(name = "resourcesProperties")
	private Properties props;

	@Autowired
	private OnSiteDAO dao;

	@Override
	@Transactional
	public ResponseEntity checkIn(OnSite onSite) throws Exception {
		Consume consume = new Consume();
		consume.setOnSite(onSite);
		onSite.setConsume(consume);
		onSite.setStatus(OnSite.OPEN);
		dao.insert(onSite);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity checkOut(OnSite onSite) throws Exception {
		LOG.debug("Validate Rules for Checkout");

		if (onSite.getConsume().getStatus().equals(Consume.ACTIVE)
				|| onSite.getConsume().getStatus().equals(Consume.PENDING_PAYMENT)) {
			return new ResponseEntity(ResponseCode.WARNING, "Não é possível efetuar Checkout. Consuma Aberta.");
		}

		onSite.setStatus(OnSite.CLOSED);
		onSite.setCheckOut(new Date());

		dao.update(onSite);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity update(OnSite onSite) throws Exception {
		dao.update(onSite);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	@Transactional
	public ResponseEntity retrieveActiveOnSiteSession(User user) throws Exception {
		List<OnSite> list = dao.findOnSiteSessionsByUser(user);
		if (list != null) {
			OnSite selected = null;
			for (OnSite onSite : list) {
				if (onSite.getStatus().equals(OnSite.OPEN)) {
					selected = onSite;
				}
			}
			if (selected != null) {
				return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY, selected);
			}
		}
		return new ResponseEntity(ResponseCode.WARNING, Constants.EMPTY, new OnSite());
	}

	@Override
	@Transactional
	public ResponseEntity retrieveActivesOnSiteSession(Establishment establishment) throws Exception {
		List<OnSite> list = dao.findOnSiteSessionsByEstablishment(establishment);
		if (list != null) {
			List<OnSite> selected = new ArrayList<OnSite>();
			for (OnSite onSite : list) {
				if (onSite.getStatus().equals(OnSite.OPEN)) {
					selected.add(onSite);
				}
			}
			if (selected != null) {
				return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY, selected);
			}
		}
		return new ResponseEntity(ResponseCode.WARNING, Constants.EMPTY, new ArrayList<OnSite>());
	}

	@Override
	@Transactional
	public ResponseEntity insertSale(OnSite currentSession, Sale sale) throws Exception {
	    sale.setReversalIndicative(false);
	    sale.setDate(new Date());
		if(currentSession.getConsume().getStatus().equals(Consume.INACTIVE)){
			currentSession.getConsume().setStatus(Consume.ACTIVE);
			dao.update(currentSession);
		}
		sale.setConsume(currentSession.getConsume());
		dao.insertSale(sale);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}
	
	@Override
	@Transactional
	public ResponseEntity reversalSale(Sale sale) throws Exception {
	    	sale.setReversalDate(new Date());
		sale.setReversalIndicative(true);
		dao.updateSale(sale);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}

	@Override
	public ResponseEntity refreshOnSiteSession(OnSite onSite) throws Exception {
		List<OnSite> list = dao.findOnSiteSessionsByEstablishment(onSite.getEstablishment());
		if (list != null) {
			OnSite selected = onSite;
			for (OnSite oS : list) {
				if (oS.getId().equals(onSite.getId())) {
					selected = oS;
				}
			}
			if (selected != null) {
				return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY, selected);
			}
		}
		return new ResponseEntity(ResponseCode.WARNING, Constants.EMPTY, new ArrayList<OnSite>());
	}

	@Override
	public ResponseEntity closeConsume(OnSite currentSession) {
		currentSession.getConsume().setStatus(Consume.CLOSED);
		currentSession.setCheckOut(new Date());
		dao.update(currentSession);
		return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
	}
}
