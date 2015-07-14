/*
 * COPYRIGHT. TECHFULL - IT SERVICES 2013. ALL RIGHTS RESERVED.
 *
 * Este software � de propriedade e de uso exclusivo da corpora��o
 * TechFull. N�o sendo permitida reprodu��o, distribui��o, 
 * armazenados num sistema de recupera��o nem traduzida por qualquer
 * humano ou computador, idioma em qualquer forma ou para quaisquer 
 * outros fins sem a pr�via autoriza��o escrita da TechFull IT Services.
 *
 */
package br.com.techfullit.mycluby.common.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({ @NamedQuery(name = "EstablishmentInfo.findByPattern", query = "from EstablishmentInfo e WHERE e.portalPattern = :pattern") })
@Entity
@Table(name = "ESTABLISHMENT_INFO")
public class EstablishmentInfo {

    @Id
    @Column(name = "ID_ESTABLISHMENT_INFO")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION",columnDefinition="DATETIME")
    private Date accountCreation;

    @Column(name = "ACTIVE")
    private boolean accountActive;

    @Column(name = "FIRST_STEP")
    private boolean firstStep;

    @Column(name = "KEY_RECOVERY")
    private String keyRecovery;

    @Column(name = "PORTAL_ACTIVE")
    private boolean portalActive;

    @Column(name = "PORTAL_NAME")
    private String portalName;

    @Column(name = "PORTAL_PATTERN")
    private String portalPattern;
    
    @Column(name="CURRENT_NOTIFICATION")
    private String notification;

    @Column(name = "GEOLOCATION_LATITUDE")
    private Double latitude;

    @Column(name = "GEOLOCATION_LONGITUDE")
    private Double longitude;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "establishmentInfo")
    private Establishment establishment;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Date getAccountCreation() {
	return accountCreation;
    }

    public void setAccountCreation(Date accountCreation) {
	this.accountCreation = accountCreation;
    }

    public boolean isAccountActive() {
	return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
	this.accountActive = accountActive;
    }

    public boolean isFirstStep() {
	return firstStep;
    }

    public void setFirstStep(boolean firstStep) {
	this.firstStep = firstStep;
    }

    public Establishment getEstablishment() {
	return establishment;
    }

    public void setEstablishment(Establishment establishment) {
	this.establishment = establishment;
    }

    public String getKeyRecovery() {
	return keyRecovery;
    }

    public void setKeyRecovery(String keyRecovery) {
	this.keyRecovery = keyRecovery;
    }

    public boolean isPortalActive() {
	return portalActive;
    }

    public void setPortalActive(boolean portalActive) {
	this.portalActive = portalActive;
    }

    public String getPortalName() {
	return portalName;
    }

    public void setPortalName(String portalName) {
	this.portalName = portalName;
    }

    public String getPortalPattern() {
	if (this.portalName != null) {
	    String pattern = this.portalName;
	    pattern = pattern.trim().replaceAll(" ", "-").toLowerCase();
	    this.portalPattern = pattern;
	}
	return portalPattern;
    }

    public void setPortalPattern(String portalPattern) {
	this.portalPattern = portalPattern;
    }

    public Double getLatitude() {
	return latitude;
    }

    public void setLatitude(Double latitude) {
	this.latitude = latitude;
    }

    public Double getLongitude() {
	return longitude;
    }

    public void setLongitude(Double longitude) {
	this.longitude = longitude;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    @Transient
    public boolean isPlaceSetted() {
	if (!isPortalActive())
	    return false;
	if (this.longitude == null || this.latitude == null || this.latitude == 0 || this.longitude == 0) {
	    return false;
	} else {
	    return true;
	}
    }
}