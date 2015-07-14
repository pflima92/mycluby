package br.com.techfullit.mycluby.controller;

import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Map;
import br.com.techfullit.mycluby.common.models.OnSite;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.common.utils.SessionHandler;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.establishment.business.EstablishmentService;
import br.com.techfullit.mycluby.services.onsite.business.OnSiteService;

@ViewScoped
@ManagedBean(name = "checkIn")
@Controller
public class CheckInController extends ContexController {

    @ManagedProperty(value = "#{establishmentService}")
    private EstablishmentService establishmentService;

    @ManagedProperty(value = "#{onSiteService}")
    private OnSiteService onSiteService;

    private List<Establishment> establishments;

    private Map map = Map.DEFAULT_MAP;

    private Marker marker;

    private OnSite onSite;

    public void loadData() {

	ResponseEntity onSiteResponse = null;
	try {
	    onSiteResponse = onSiteService.retrieveActiveOnSiteSession((User) ContextHelper.getCurrentEntity());
	    if (onSiteResponse.isSuccess()) {
		onSite = (OnSite) onSiteResponse.getObject();
		if (onSite.getStatus().equals(OnSite.OPEN)) {
		    NavigationHandler nh = getContext().getApplication().getNavigationHandler();
		    nh.handleNavigation(getContext(), null, Events.PLACE_OPTIONS);
		}
	    } else {
		onSite = new OnSite();
	    }
	} catch (Exception e) {
	    MessageViewer.showError(e.getMessage());
	    onSite = new OnSite();
	}

	if (establishments == null) {
	    establishments = findAllEstablishments();
	}

	for (Establishment establishment : establishments) {
	    if (establishment.getEstablishmentInfo().isPlaceSetted()) {
		map.getModel().addOverlay(
			new Marker(new LatLng(establishment.getEstablishmentInfo().getLatitude(), establishment
				.getEstablishmentInfo().getLongitude()), establishment.getName(), establishment));
	    }
	}
    }

    public String makeCheckIn() {

	onSite.setUser((User) ContextHelper.getCurrentEntity());
	onSite.setEstablishment((Establishment) marker.getData());

	try {
	    onSiteService.checkIn(onSite);
	} catch (Exception e) {
	    MessageViewer.showError("Não foi possível fazer check-in em: " + onSite.getEstablishment().getName());
	    return Events.EMPTY;
	}

	return Events.PLACE_OPTIONS;
    }

    private List<Establishment> findAllEstablishments() {
	ResponseEntity response = null;
	try {
	    response = establishmentService.findAllEstablishment();
	    if (response.isSuccess()) {
		return (List<Establishment>) response.getObject();
	    }
	} catch (Exception e) {
	    MessageViewer.showError("Deu erro ao recuperar os usuário do MyClub");
	}
	return null;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
	System.out.println("onMarkerSelect");
	marker = (Marker) event.getOverlay();
    }

    public void onPointSelect(PointSelectEvent event) {
	LatLng latlng = event.getLatLng();
	System.out.println("Point Selected" + "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng());
    }

    private String targetPage;

    public String getTargetPage() {
	return targetPage;
    }

    public void setTargetPage(String targetPage) {
	this.targetPage = targetPage;
    }

    public Marker getMarker() {
	return marker;
    }

    public void setMarker(Marker marker) {
	this.marker = marker;
    }

    public EstablishmentService getEstablishmentService() {
	return establishmentService;
    }

    public void setEstablishmentService(EstablishmentService establishmentService) {
	this.establishmentService = establishmentService;
    }

    public List<Establishment> getEstablishments() {
	return establishments;
    }

    public void setEstablishments(List<Establishment> establishments) {
	this.establishments = establishments;
    }

    public Map getMap() {
	return map;
    }

    public void setMap(Map map) {
	this.map = map;
    }

    public OnSite getOnSite() {
	return onSite;
    }

    public void setOnSite(OnSite onSite) {
	this.onSite = onSite;
    }

    public OnSiteService getOnSiteService() {
	return onSiteService;
    }

    public void setOnSiteService(OnSiteService onSiteService) {
	this.onSiteService = onSiteService;
    }
}