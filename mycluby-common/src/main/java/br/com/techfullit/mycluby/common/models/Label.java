package br.com.techfullit.mycluby.common.models;

import java.io.Serializable;

public class Label implements Serializable {

	public Label() {
	}

	public Label(String value) {
		this.value = value;
	}

	/**
     * 
     */
	private static final long serialVersionUID = 5611419218070262659L;

	private String key;

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
