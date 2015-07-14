/*
na * COPYRIGHT. TECHFULL - IT SERVICES 2013. ALL RIGHTS RESERVED.
 *
 * Este software � de propriedade e de uso exclusivo da corpora��o
 * TechFull. N�o sendo permitida reprodu��o, distribui��o, 
 * armazenados num sistema de recupera��o nem traduzida por qualquer
 * humano ou computador, idioma em qualquer forma ou para quaisquer 
 * outros fins sem a pr�via autoriza��o escrita da TechFull IT Services.
 *
 */
package br.com.techfullit.mycluby.common.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.techfullit.mycluby.common.utils.Base64;

/**
 * The Class UserBean.
 */
@NamedQueries({ @NamedQuery(name = "Establishment.findAll", query = "from Establishment e"),
	@NamedQuery(name = "Establishment.findByName", query = "from Establishment e WHERE e.name = :name") })
@Entity
@Table(name = "ESTABLISHMENT")
public class Establishment {

    @Id
    @Column(name = "ID_ESTABLISHMENT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "ESTABLISHMENT_NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDATE")
    private Date birthDate;

    @Column(name = "COUNTRY")
    private String country;

    /** The state. */
    @Column(name = "STATE")
    private String state;

    /** The city. */
    @Column(name = "CITY")
    private String city;

    /** The adress. */
    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE")
    private String phone;

    @Transient
    private String profileImage;

    @Column(name = "IMAGE")
    @Lob
    private byte[] image;

    @Column(name = "ABOUT")
    private String about;

    @OneToMany(mappedBy = "establishment", targetEntity = Mail.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Mail> mails;
    
    @JsonIgnore
    @OneToMany(mappedBy = "establishment", targetEntity = Event.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Event> events;

    @OneToMany(mappedBy = "establishment", targetEntity = Employee.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Employee> employers;
    
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ESTABLISHMENT", joinColumns = { @JoinColumn(name = "ID_ESTABLISHMENT") }, inverseJoinColumns = { @JoinColumn(name = "ID_USER") })
    @Fetch(FetchMode.SUBSELECT)
    private List<User> favUsers;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "ESTABLISHMENT_ROLE", joinColumns = { @JoinColumn(name = "ID_ESTABLISHMENT") }, inverseJoinColumns = { @JoinColumn(name = "ID_ROLE") })
    @Fetch(FetchMode.SUBSELECT)
    private List<Role> roles;
    
    @OneToMany(mappedBy = "establishment", targetEntity = Category.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Category> categories;

    @OneToOne
    @JoinColumn(name = "ID_ESTABLISHMENT_INFO")
    private EstablishmentInfo establishmentInfo;
    
    @OneToMany(mappedBy = "establishment", targetEntity = OnSite.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<OnSite> allSessions;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public Date getBirthDate() {
	return birthDate;
    }

    public void setBirthDate(Date birthDate) {
	this.birthDate = birthDate;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getProfileImage() {
	Base64 b64 = new Base64();
	if (image != null)
	    profileImage = b64.encodeBytes(image);
	return profileImage;
    }

    public void setProfileImage(String profileImage) {
	this.profileImage = profileImage;
    }

    public byte[] getImage() {
	return image;
    }

    public void setImage(byte[] image) {
	this.image = image;
    }

    public String getAbout() {
	return about;
    }

    public void setAbout(String about) {
	this.about = about;
    }

    public List<Mail> getMails() {
	if (mails == null)
	    mails = new ArrayList<Mail>();
	return mails;
    }

    public void setMails(List<Mail> mails) {
	this.mails = mails;
    }

    public EstablishmentInfo getEstablishmentInfo() {
	return establishmentInfo;
    }

    public void setEstablishmentInfo(EstablishmentInfo establishmentInfo) {
	this.establishmentInfo = establishmentInfo;
    }

    public List<Role> getRoles() {
	if (roles == null)
	    roles = new ArrayList<Role>();
	return roles;
    }

    public void setRoles(List<Role> roles) {
	this.roles = roles;
    }

    public List<Employee> getEmployers() {
	if (employers == null)
	    employers = new ArrayList<Employee>();
	return employers;
    }

    public void setEmployers(List<Employee> employers) {
	this.employers = employers;
    }

    public List<Category> getCategories() {
	if(categories == null)
	    categories = new ArrayList<Category>();
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<User> getFavUsers() {
        return favUsers;
    }

    public void setFavUsers(List<User> favUsers) {
        this.favUsers = favUsers;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<OnSite> getAllSessions() {
	if(allSessions == null){
	    allSessions = new ArrayList<OnSite>();
	}
        return allSessions;
    }

    public void setAllSessions(List<OnSite> allSessions) {
        this.allSessions = allSessions;
    }

}