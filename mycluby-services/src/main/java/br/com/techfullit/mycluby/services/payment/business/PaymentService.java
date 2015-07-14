package br.com.techfullit.mycluby.services.payment.business;

import br.com.techfullit.mycluby.common.models.OnSite;
import br.com.techfullit.mycluby.common.models.ResponseEntity;

public interface PaymentService {

	public ResponseEntity performPayment(OnSite onSite) throws Exception;

}
