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
 * The Enum ResponseCode.
 */
public enum ResponseCode {

	/** The success. */
	SUCCESS(0), /** The error. */
	ERROR(1), /** The warning. */
	WARNING(2);

	/**
	 * Instantiates a new response code.
	 * 
	 * @param valor
	 *            the valor
	 */
	ResponseCode(final Integer valor) {
		retornoCode = valor;
	}

	/** The retorno code. */
	private final Integer retornoCode;

	/**
	 * Gets the retorno code.
	 * 
	 * @return the retorno code
	 */
	public Integer getRetornoCode() {
		return retornoCode;
	}

}
