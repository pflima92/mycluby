package br.com.techfullit.mycluby.common.models;

public class Search {
	
	public Search() {
		// TODO Auto-generated constructor stub
	}

	public Search(String label, String image, Object entity) {
		this.label = label;
		this.image = image;
		this.entity = entity;
	}
	
	private String label;
	
	private String image;
	
	private Object entity;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}
	
	
	
}
