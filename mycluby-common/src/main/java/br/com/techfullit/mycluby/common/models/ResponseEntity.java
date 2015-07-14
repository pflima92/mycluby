/*
 * COPYRIGHT. TECHFULL - IT SERVICES 2013. ALL RIGHTS RESERVED.
 *
 * Este software � de propriedade e de uso exclusivo da corpora��o
 * TechFull. N�o sendo permitida reprodu��o, distribui��o, 
 * armazenados num sistema de recupera��o nem traduzida por qualquer
 * humano ou computador, idioma em qualquer forma ou para quaisquer 
 * outros fins sem a pr�via autoriza��o escrita da TechFull IT Services.
 *
 */
package br.com.techfullit.mycluby.common.models;

/**
 * The Class ResponseEntity.
 */
public class ResponseEntity {

	/**
	 * Instantiates a new response entity.
	 */
	public ResponseEntity() {
	}

	/**
	 * Instantiates a new response entity.
	 * 
	 * @param status
	 *            the status
	 * @param descStatus
	 *            the desc status
	 * @param responseObject
	 *            the response object
	 */
	public ResponseEntity(final ResponseCode status, final String descStatus, final Object responseObject) {
		this.status = status;
		this.descStatus = descStatus;
		this.responseObject = responseObject;
	}

	/**
	 * Instantiates a new response entity.
	 * 
	 * @param status
	 *            the status
	 * @param descStatus
	 *            the desc status
	 */
	public ResponseEntity(final ResponseCode status, final String descStatus) {
		this.status = status;
		this.descStatus = descStatus;
	}

	public ResponseEntity(Exception e) {
		this.status = ResponseCode.ERROR;
		this.descStatus = e.getMessage();
	}

	/**
	 * Checks if is success.
	 * 
	 * @return true, if is success
	 */
	public boolean isSuccess() {
		if (this.status != null) {
			if (this.status == ResponseCode.SUCCESS)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	/**
	 * Checks if is warning.
	 * 
	 * @return true, if is warning
	 */
	public boolean isWarning() {
		if (this.status != null) {
			if (this.status == ResponseCode.WARNING)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	/**
	 * Checks if is error.
	 * 
	 * @return true, if is error
	 */
	public boolean isError() {
		if (this.status != null) {
			if (this.status == ResponseCode.ERROR)
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	/** The status. */
	private ResponseCode status;

	/** The desc status. */
	private String descStatus;

	/** The response object. */
	private Object responseObject;

	/**
	 * Gets the object.
	 * 
	 * @return the object
	 */
	public Object getObject() {
		return responseObject;
	}

	/**
	 * Sets the object.
	 * 
	 * @param object
	 *            the new object
	 */
	public void setObject(final Object object) {
		this.responseObject = object;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public ResponseCode getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(final ResponseCode status) {
		this.status = status;
	}

	/**
	 * Gets the desc status.
	 * 
	 * @return the desc status
	 */
	public String getDescStatus() {
		return descStatus;
	}

	/**
	 * Sets the desc status.
	 * 
	 * @param descStatus
	 *            the new desc status
	 */
	public void setDescStatus(final String descStatus) {
		this.descStatus = descStatus;
	}

}
