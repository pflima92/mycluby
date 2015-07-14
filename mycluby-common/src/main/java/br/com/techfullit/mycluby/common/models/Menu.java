package br.com.techfullit.mycluby.common.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.utils.Utils;

public class Menu implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -825976871074992848L;

	private Integer id;

	private String uiId;

	private Label label;

	private String icon;

	private ArrayList<Menu> childs;

	private String target;

	private boolean rendered;

	private List<Role> roles;

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public ArrayList<Menu> getChilds() {
		if (childs == null) {
			childs = new ArrayList<>();
		}
		return childs;
	}

	public void setChilds(ArrayList<Menu> childs) {
		this.childs = childs;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<Role> getRole() {
		if (roles == null)
			roles = new ArrayList<>();
		return roles;
	}

	public void setRole(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUiId() {
		if (uiId == null || uiId.equals(Constants.EMPTY))
			return Utils.generateTokenId();
		return uiId;
	}

	public void setUiId(String uiId) {
		this.uiId = uiId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
