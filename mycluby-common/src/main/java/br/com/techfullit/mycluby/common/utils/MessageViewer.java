/*
 * 
 * Desenvolvido por: Paulo Henrique Ferreira de Lima - pflima
 * 
 * BRQ IT Services - 2013
 * 
 * AFS Tools Service
 * 
 * Proibida distribui��o sem autoriza��o do criador por qualquer meio.
 * 
 * 
 */
package br.com.techfullit.mycluby.common.utils;

import javax.faces.application.FacesMessage;

import br.com.techfullit.mycluby.common.constants.Constants;

/**
 * The Class MessageViewer.
 */
public class MessageViewer extends ContexController {

    /**
     * Show error.
     * 
     * @param title
     *            the title
     * @param message
     *            the message
     */
    public static void showError(String title, String message) {
	String titlePresentation = StringUtils.isBlank(title) ? Constants.EMPTY : title + ": ";
	getContext().addMessage(null,
		new FacesMessage(FacesMessage.SEVERITY_ERROR, titlePresentation + message, message));
    }

    /**
     * Show success.
     * 
     * @param title
     *            the title
     * @param message
     *            the message
     */
    public static void showSuccess(String title, String message) {
	String titlePresentation = StringUtils.isBlank(title) ? Constants.EMPTY : title + ": ";
	getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titlePresentation + message, message));
    }

    /**
     * Show warning.
     * 
     * @param title
     *            the title
     * @param message
     *            the message
     */
    public static void showWarning(String title, String message) {
	String titlePresentation = StringUtils.isBlank(title) ? Constants.EMPTY : title + ": ";
	getContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titlePresentation + message, message));
    }

    public static void showError(String message) {
	getContext().addMessage(
		null,
		new FacesMessage(FacesMessage.SEVERITY_ERROR,
			MessageUtils.getMessage(Constants.ERROR) + ": " + message, message));
    }

}
