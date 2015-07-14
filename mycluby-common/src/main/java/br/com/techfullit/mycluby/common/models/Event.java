package br.com.techfullit.mycluby.common.models;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.techfullit.mycluby.common.utils.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({ @NamedQuery(name = "Event.findByUser", query = "from Event e WHERE e.user = :user") })
@Entity
@Table(name = "Event")
public class Event {

    @Id
    @Column(name = "ID_EVENT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NEW")
    private boolean newEvent;

    @Column(name = "IMAGE")
    @Lob
    private byte[] image;

    @Transient
    private String imageConverted;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_ESTABLISHMENT")
    private Establishment establishment;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getAction() {
	return action;
    }

    public void setAction(String action) {
	this.action = action;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public boolean isNewEvent() {
	return newEvent;
    }

    public void setNewEvent(boolean newEvent) {
	this.newEvent = newEvent;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public byte[] getImage() {
	return image;
    }

    public void setImage(byte[] image) {
	this.image = image;
    }

    public String getImageConverted() {
	Base64 b64 = new Base64();
	if (image != null)
	    imageConverted = b64.encodeBytes(image);
	return imageConverted;
    }

    public void setImageConverted(String imageConverted) {
	this.imageConverted = imageConverted;
    }

    public Establishment getEstablishment() {
	return establishment;
    }

    public void setEstablishment(Establishment establishment) {
	this.establishment = establishment;
    }

    public int compareTo(Event ev) {
	return getData(ev.getDate()).compareTo(getData(this.getDate()));
    }

    private Date getData(Date aDate) {
	final Calendar myCalendar = Calendar.getInstance();
	myCalendar.setTime(aDate);
	myCalendar.set(Calendar.HOUR_OF_DAY, 0);
	myCalendar.set(Calendar.MINUTE, 0);
	myCalendar.set(Calendar.SECOND, 0);
	myCalendar.set(Calendar.MILLISECOND, 0);
	return myCalendar.getTime();
    }
}
