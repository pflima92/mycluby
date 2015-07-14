package br.com.techfullit.mycluby.common.models;

public class Portal {

	private Establishment establishment;

	private boolean logged;
	
	private Employee currentStaff;

	public Establishment getEstablishment() {
		return establishment;
	}

	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public Employee getCurrentStaff() {
		if(currentStaff == null){
			currentStaff = new Employee();
			currentStaff.setName(establishment.getName());
			currentStaff.getRoles().add(Role.ROLE_UNSIGNED);
		}
		return currentStaff;
	}

	public void setCurrentStaff(Employee currentStaff) {
		this.currentStaff = currentStaff;
	}
	
	

}
