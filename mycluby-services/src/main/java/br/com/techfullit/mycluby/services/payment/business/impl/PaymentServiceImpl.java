package br.com.techfullit.mycluby.services.payment.business.impl;

import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Consume;
import br.com.techfullit.mycluby.common.models.OnSite;
import br.com.techfullit.mycluby.common.models.ResponseCode;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.services.onsite.dao.OnSiteDAO;
import br.com.techfullit.mycluby.services.payment.business.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    private static final Log LOG = LogFactory.getLog(PaymentServiceImpl.class);

    @Resource(name = "resourcesProperties")
    private Properties props;

    @Autowired
    private OnSiteDAO dao;

    @Override
    public ResponseEntity performPayment(OnSite onSite) throws Exception {
	//TODO Efetuar chamada para Serviço Web Service de Pagamento da Operadora, módulo funcionara para o Web Client.
	onSite.getConsume().setStatus(Consume.CLOSED);
	onSite.setCheckOut(new Date());
	dao.update(onSite);
	return new ResponseEntity(ResponseCode.SUCCESS, Constants.EMPTY);
    }
}
