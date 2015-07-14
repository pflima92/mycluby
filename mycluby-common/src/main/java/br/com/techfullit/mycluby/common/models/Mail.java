package br.com.techfullit.mycluby.common.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({ @NamedQuery(name = "Mail.findByValue", query = "from Mail m WHERE m.value = :mail") })
@Entity
@Table(name = "MAIL")
public class Mail {

	@Id
	@Column(name = "ID_MAIL")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "VALUE")
	private String value;

	@Column(name = "ACTIVE_LOGIN")
	private boolean activeLogin;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isActiveLogin() {
		return activeLogin;
	}

	public void setActiveLogin(boolean activeLogin) {
		this.activeLogin = activeLogin;
	}

	public Establishment getEstablishment() {
		return establishment;
	}

	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}

	public boolean isEstablishment() {
		if (establishment == null)
			return false;
		else
			return true;
	}

	public boolean isUser() {
		if (user == null)
			return false;
		else
			return true;
	}

	public boolean isValidEntity() {
		if (null != user || null != establishment) {
			return true;
		} else
			return false;
	}

	public Object getEntity() {
		if (user != null)
			return user;
		else if (establishment != null)
			return establishment;
		else
			return null;
	}

}
