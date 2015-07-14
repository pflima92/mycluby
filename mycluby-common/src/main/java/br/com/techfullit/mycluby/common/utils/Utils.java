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

package br.com.techfullit.mycluby.common.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * The Class Utils.
 */
public class Utils {

	/**
	 * Removes the trelling.
	 * 
	 * @param string
	 *            the string
	 * @return the string
	 */
	public static String removeTrelling(final String string) {
		return string.replaceAll(" ", "");
	}

	public static String generateTokenId() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

}
