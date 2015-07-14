package br.com.techfullit.mycluby.services.onsite.business;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.OnSite;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.Sale;
import br.com.techfullit.mycluby.common.models.User;

public interface OnSiteService {

	public ResponseEntity checkIn(OnSite onSite) throws Exception;
	
	public ResponseEntity checkOut(OnSite onSite) throws Exception;

	public ResponseEntity update(OnSite onSite) throws Exception;
	
	public ResponseEntity refreshOnSiteSession(OnSite onSite) throws Exception;
	
	public ResponseEntity retrieveActiveOnSiteSession(User user) throws Exception;
	
	public ResponseEntity retrieveActivesOnSiteSession(Establishment establishment) throws Exception;

	public ResponseEntity insertSale(OnSite currentSession, Sale sale) throws Exception;

	public ResponseEntity closeConsume(OnSite currentSession) throws Exception;

	public ResponseEntity reversalSale(Sale sale) throws Exception;


}
