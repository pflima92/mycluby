package br.com.techfullit.mycluby.common.models;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

public class Map {

    public final static Map DEFAULT_MAP = new Map(-25.444334, -49.292377, new DefaultMapModel());

    public Map() {
    }

    public Map(Double latitude, Double longitude, MapModel model) {
	this.latitude = latitude;
	this.longitude = longitude;
	this.model = model;
    }

    private MapModel model;

    private Double longitude;

    private Double latitude;

    public Double getLongitude() {
	return longitude;
    }

    public void setLongitude(Double longitude) {
	this.longitude = longitude;
    }

    public Double getLatitude() {
	return latitude;
    }

    public void setLatitude(Double latitude) {
	this.latitude = latitude;
    }

    public MapModel getModel() {
	return model;
    }

    public void setModel(MapModel model) {
	this.model = model;
    }

    public void setModel(Establishment establishment) {
	getModel().addOverlay(
		new Marker(new LatLng(establishment.getEstablishmentInfo().getLatitude(), establishment
			.getEstablishmentInfo().getLongitude()), establishment.getName()));
	setLatitude(establishment.getEstablishmentInfo().getLatitude());
	setLongitude(establishment.getEstablishmentInfo().getLongitude());
    }
    

}
