package br.com.techfullit.mycluby.common.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ROLE")
public class Role {

	/** Roles Constants */
	public static final Role ROLE_USER = new Role("ROLE_USER", "Default User");
	public static final Role ROLE_ESTABLISHMENT = new Role("ROLE_ESTABLISHMENT", "Default Establishment");
	public static final Role ROLE_UNSIGNED = new Role("UNSIGNED", "No roles defined");
	public static final Role ROLE_LOGGED = new Role("LOGGED", "User Logged");

	public Role() {
	}

	public Role(String roleId, String roleDescription) {
		this.title = roleId;
		this.description = roleDescription;
	}

	@Id
	@Column(name = "ID_ROLE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private List<Establishment> establishment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String roleId) {
		this.title = roleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String roleDescription) {
		this.description = roleDescription;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
