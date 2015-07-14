package br.com.techfullit.mycluby.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Map;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.establishment.business.EstablishmentService;

@Controller
@ViewScoped
@ManagedBean(name = "establishmentSettings")
public class EstablishmentSettingsController extends ContexController {

    public EstablishmentSettingsController() {
    }

    private static final Log LOG = LogFactory.getLog(EstablishmentSettingsController.class);

    @ManagedProperty(value = "#{establishmentService}")
    private EstablishmentService establishmentService;

    public void loadData() {
	map = Map.DEFAULT_MAP;

	if (establishment == null) {
	    establishment = (Establishment) ContextHelper.getCurrentEntity();

	    if (establishment.getEstablishmentInfo().isPlaceSetted()) {
		map.setModel(establishment);
	    }
	}
    }

    private Establishment establishment;

    private String currentUrl;

    private Map map;

    private UploadedFile img;

    public String updateEstablishmentContinue() {
	LOG.debug("Called method [updateEstablishmentContinue]");

	if (img != null && img.getSize() != 0) {
	    try {
		LOG.debug("Get file of upload filter");
		byte[] content = img.getContents();
		LOG.debug("Conver file in base64");
		establishment.setImage(content);
	    } catch (Exception e) {
		LOG.error(e.getMessage());
		MessageViewer.showError("Ops, tivemos um erro ao processar sua foto, tente novamente.");
	    }
	}

	if (establishment.getEstablishmentInfo().isPortalActive()) {

	    List<Marker> points = map.getModel().getMarkers();
	    if (!points.isEmpty()) {
		// xgh só podew ter 1 point
		Marker point = points.get(0);
		establishment.getEstablishmentInfo().setLatitude(point.getLatlng().getLat());
		establishment.getEstablishmentInfo().setLongitude(point.getLatlng().getLng());
	    }
	}

	ResponseEntity response = null;
	try {
	    response = establishmentService.updateEstablishment(establishment.getEstablishmentInfo());
	} catch (Exception e) {
	    LOG.error(e.getMessage());
	    MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
	    return Events.EMPTY;
	}

	if (response.isError()) {
	    LOG.debug(response.getDescStatus());
	    MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
	    return Events.EMPTY;
	} else {
	    LOG.debug("Set user in session");
	    MessageViewer.showSuccess("Ok", "O perfil do seu estabelecimento foi atualizado com sucesso");
	    ContextHelper.setCurrentEntity(establishment);
	    return Events.EMPTY;
	}
    }

    public void onPointSelect(PointSelectEvent event) {
	LatLng latlng = event.getLatLng();
	LOG.debug("Point Selected - Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng());
	MessageViewer.showWarning("Localização Alterada","Sua localização foi atualizada, para confirmar clique no botão Salvar");
	map.getModel().getMarkers().clear();
	map.getModel().addOverlay(new Marker(new LatLng(latlng.getLat(), latlng.getLng()), establishment.getName()));
    }

    public EstablishmentService getEstablishmentService() {
	return establishmentService;
    }

    public void setEstablishmentService(EstablishmentService establishmentService) {
	this.establishmentService = establishmentService;
    }

    public Establishment getEstablishment() {
	return establishment;
    }

    public void setEstablishment(Establishment establishment) {
	this.establishment = establishment;
    }

    public String getCurrentUrl() {
	String url = "http:\\\\www.mycluby.com.br\\mycluby-corporate-web\\p\\";
	if (establishment.getEstablishmentInfo().getPortalPattern() != null) {
	    currentUrl = url + establishment.getEstablishmentInfo().getPortalPattern();
	}
	return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
	this.currentUrl = currentUrl;
    }

    public UploadedFile getImg() {
	return img;
    }

    public void setImg(UploadedFile img) {
	this.img = img;
    }

    public Map getMap() {
	return map;
    }

    public void setMap(Map map) {
	this.map = map;
    }

}
