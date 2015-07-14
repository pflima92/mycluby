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

import br.com.techfullit.mycluby.common.utils.Base64;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageUtils;
import br.com.techfullit.mycluby.common.utils.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class UserBean.
 */
@NamedQueries({ @NamedQuery(name = "User.findAllUsers", query = "from User u") })
@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "ID_USER")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "PASSWORD")
    private String password;

    /** The birth date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTHDATE")
    private Date birthDate;

    @Column(name = "GENDER")
    private String gender;

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

    @Column(name = "COMPLEMENT")
    private String complement;

    /** The phone. */
    @Column(name = "RES_PHONE")
    private String resPhone;

    /** The phone. */
    @Column(name = "CELL_PHONE")
    private String cellPhone;

    /** The phone. */
    @Column(name = "JOB_PHONE")
    private String jobPhone;

    @Transient
    private String profileImage;

    @Column(name = "PROFILE_IMAGE")
    @Lob
    private byte[] image;

    @Column(name = "ABOUT")
    private String about;

    @JsonIgnore
    @OneToMany(mappedBy = "user", targetEntity = Mail.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Mail> mails;

    @JsonIgnore
    @OneToMany(mappedBy = "user", targetEntity = Event.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Event> events;
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", targetEntity = Card.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Card> cards;

    @JsonIgnore
    @OneToMany(mappedBy = "user", targetEntity = Picture.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Picture> pictures;
    
    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ESTABLISHMENT", joinColumns = { @JoinColumn(name = "ID_USER") }, inverseJoinColumns = { @JoinColumn(name = "ID_ESTABLISHMENT") })
    @Fetch(FetchMode.SUBSELECT)
    private List<Establishment> favEstablishments;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_GROUPE", joinColumns = { @JoinColumn(name = "ID_USER") }, inverseJoinColumns = { @JoinColumn(name = "ID_GROUPE") })
    @Fetch(FetchMode.SUBSELECT)
    private List<Group> groups;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "ID_USER") }, inverseJoinColumns = { @JoinColumn(name = "ID_ROLE") })
    @Fetch(FetchMode.SUBSELECT)
    private List<Role> roles;

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_FRIENDS", joinColumns = { @JoinColumn(name = "ID_PRIMARY") }, inverseJoinColumns = { @JoinColumn(name = "ID_SECONDARY") })
    @Fetch(FetchMode.SUBSELECT)
    private List<User> friends;
    
    @OneToMany(mappedBy = "user", targetEntity = OnSite.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<OnSite> allSessions;

    @OneToOne
    @JoinColumn(name = "ID_ACCOUNT_INFO")
    private AccountInfo accountInfo;

    @Transient
    private String genderLabel;

    @Transient
    private String addressPresentation;

    @Transient
    private List<User> friendCommonCurrentUser;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getFirstname() {
	return firstname;
    }

    public void setFirstname(String firstname) {
	this.firstname = firstname;
    }

    public String getLastname() {
	return lastname;
    }

    public void setLastname(String lastname) {
	this.lastname = lastname;
    }

    public Date getBirthDate() {
	return birthDate;
    }

    public void setBirthDate(Date birthDate) {
	this.birthDate = birthDate;
    }

    public String getGender() {
	return gender;
    }

    public String getGenderLabel() {
	if(MessageUtils.getMessage("gender" + gender).equals("gendernull")){
	    return "";
	}
	return MessageUtils.getMessage("gender" + gender);
    }

    public void setGender(String gender) {
	this.gender = gender;
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

    public String getComplement() {
	return complement;
    }

    public void setComplement(String complement) {
	this.complement = complement;
    }

    public String getResPhone() {
	return resPhone;
    }

    public void setResPhone(String resPhone) {
	this.resPhone = resPhone;
    }

    public String getCellPhone() {
	return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
	this.cellPhone = cellPhone;
    }

    public String getJobPhone() {
	return jobPhone;
    }

    public void setJobPhone(String jobPhone) {
	this.jobPhone = jobPhone;
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

    public List<Role> getRoles() {
	if (roles == null)
	    roles = new ArrayList<Role>();
	return roles;
    }

    public void setRoles(List<Role> roles) {
	this.roles = roles;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public AccountInfo getAccountInfo() {
	return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
	this.accountInfo = accountInfo;
    }

    public List<User> getFriends() {
	if (friends == null)
	    friends = new ArrayList<User>();
	return friends;
    }

    public void setFriends(List<User> friends) {
	this.friends = friends;
    }

    public List<Event> getEvents() {
	if (events == null)
	    events = new ArrayList<Event>();
	return events;
    }

    public void setEvents(List<Event> events) {
	this.events = events;
    }

    public String getAddressPresentation() {
	StringBuilder builder = new StringBuilder();
	if (StringUtils.notIsBlank(city)) {
	    builder.append(city);
	    if (StringUtils.notIsBlank(state))
		builder.append(" , ");
	}
	if (StringUtils.notIsBlank(state)) {
	    builder.append(state);
	    if (StringUtils.notIsBlank(country))
		builder.append(" - ");
	}
	if (StringUtils.notIsBlank(country)) {
	    builder.append(country);
	}
	return builder.toString();
    }

    // TODO fazer em um Serviço
    public List<User> getFriendCommonCurrentUser() {
	friendCommonCurrentUser = new ArrayList<User>();
	User currentUser = (User) ContextHelper.getCurrentEntity();
	if (currentUser != null) {
	    for (User currentUserFriend : currentUser.getFriends()) {
		if (currentUserFriend.getId() == getId())
		    continue;
		for (User myFriend : getFriends()) {
		    if (currentUserFriend.getId() == myFriend.getId())
			friendCommonCurrentUser.add(myFriend);
		}
	    }
	}
	return friendCommonCurrentUser;
    }

    public byte[] getImage() {
	return image;
    }

    public void setImage(byte[] image) {
	this.image = image;
    }

    public List<Picture> getPictures() {
	if (pictures == null)
	    pictures = new ArrayList<Picture>();
	return pictures;
    }

    public void setPictures(List<Picture> pictures) {
	this.pictures = pictures;
    }

    public List<Group> getGroups() {
	if (groups == null)
	    groups = new ArrayList<Group>();
	return groups;
    }

    public void setGroups(List<Group> groups) {
	this.groups = groups;
    }

    public List<Establishment> getFavEstablishments() {
        return favEstablishments;
    }

    public void setFavEstablishments(List<Establishment> favEstablishments) {
        this.favEstablishments = favEstablishments;
    }

	public List<Card> getCards() {
		if(cards == null)
			cards = new ArrayList<Card>();
		return cards;
	}

	public void setCards(List<Card> cards) {
	this.cards = cards;
    }

	public List<OnSite> getAllSessions() {
	    return allSessions;
	}

	public void setAllSessions(List<OnSite> allSessions) {
	    this.allSessions = allSessions;
	}
	
	
}