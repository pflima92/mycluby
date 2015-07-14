package br.com.techfullit.mycluby.common.models;

public class ContextInformation {

	private Object currentEntity;

	private boolean mobile;

	public boolean isUser() {
		if (currentEntity != null && currentEntity instanceof User)
			return true;
		else
			return false;
	}

	public boolean isEstablishment() {
		if (currentEntity != null && currentEntity instanceof Establishment)
			return true;
		else
			return false;
	}

	public boolean isValidEntity() {
		if (currentEntity != null) {
			return true;
		} else
			return false;
	}

	public Object getEntity() {
		if (currentEntity != null)
			return currentEntity;
		else
			return null;
	}

	public Object getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(Object currentEntity) {
		this.currentEntity = currentEntity;
	}

	public boolean isMobile() {
		return mobile;
	}

	public void setMobile(boolean mobile) {
		this.mobile = mobile;
	}
}
