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

/**
 * Created by @author Paulo
 */

package br.com.techfullit.mycluby.common.utils;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.techfullit.mycluby.common.models.ContextInformation;

/**
 * The Class AbstractController.
 */
public abstract class ContexController {

	/** The context. */
	private static FacesContext context;

	private static final Log LOG = LogFactory.getLog(ContexController.class);

	@ModelAttribute("contextInformation")
	public ContextInformation buildContextInformation() {

		LOG.debug("BuildContextInformation");

		ContextInformation ctxInfo = new ContextInformation();
		LOG.debug("Verify if user is logged in Portal");
		if (ContextHelper.userIsLogged()) {
			LOG.debug("User is logged");
			LOG.debug("Retrieve User Information");
			ctxInfo.setCurrentEntity(ContextHelper.getCurrentEntity());
			LOG.debug("User Informations is recovered");
		} else {
			LOG.debug("User Information not recoverd, user is not logged");
			ctxInfo.setCurrentEntity(ContextHelper.setUserAnnonymous());
		}

		return ctxInfo;
	}

	public static FacesContext getContext() {
		context = FacesContext.getCurrentInstance();
		return context;
	}

	public static void setContext(FacesContext context) {
		ContexController.context = context;
	}

}
