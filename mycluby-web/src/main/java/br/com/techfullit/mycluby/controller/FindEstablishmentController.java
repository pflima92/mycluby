package br.com.techfullit.mycluby.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.services.establishment.business.EstablishmentService;

@Controller
@ViewScoped
@ManagedBean(name = "findEstablishments")
public class FindEstablishmentController extends ContexController {

    public FindEstablishmentController() {
    }

    private static final Log LOG = LogFactory.getLog(FindEstablishmentController.class);

    private List<Establishment> establishments;

    private String name;

    public void loadData() {
	if (establishments == null) {
	    establishments = findAllEstablishments();
	}
    }

    @ManagedProperty(value = "#{establishmentService}")
    private EstablishmentService establishmentService;

    @PostConstruct
    public void init() {
    }

    private List<Establishment> findAllEstablishments() {
	ResponseEntity response = null;
	try {
	    response = establishmentService.findAllEstablishment();
	    if (response.isSuccess()) {
		return (List<Establishment>) response.getObject();
	    }
	} catch (Exception e) {
	    LOG.error(e.getMessage());
	    MessageViewer.showError("Deu erro ao recuperar os usuário do MyClub");
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    public void findByName(ActionEvent event) {
	LOG.debug("Call findByName");
	ResponseEntity response = null;
	try {
	    response = establishmentService.findEstablishmentByName(name);
	    if (response.isSuccess()) {
		establishments = (ArrayList<Establishment>) response.getObject();
	    }
	} catch (Exception e) {
	    LOG.error(e.getMessage());
	    MessageViewer.showError("Deu erro ao recuperar os estabelecimentos do MyCluby");
	}
    }

    public List<Establishment> getTop10Establishments() {
	SortedMap<Integer, Establishment> topEstablishments = new TreeMap<Integer, Establishment>();
	for (Establishment establishment : this.establishments) {
	    topEstablishments.put(establishment.getAllSessions().size(), establishment);
	}
	ArrayList<Establishment> list = new ArrayList<Establishment>();
	list.addAll(topEstablishments.values());
	return list;
    }

    public List<Establishment> getEstablishments() {
	return findAllEstablishments();
    }

    public void setEstablishments(List<Establishment> establishments) {
	this.establishments = establishments;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public EstablishmentService getEstablishmentService() {
	return establishmentService;
    }

    public void setEstablishmentService(EstablishmentService establishmentService) {
	this.establishmentService = establishmentService;
    }

}
