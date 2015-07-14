package br.com.techfullit.mycluby.common.utils;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.techfullit.mycluby.common.constants.Constants;

public class MessageUtils {

	private static final Log LOG = LogFactory.getLog(MessageUtils.class);

	public static String getMessage(String value) {
		try {

			LOG.debug("Init Get Message for value [" + value + "]");
			LOG.debug("Retrieve ResourceBundle for " + Constants.PACKAGE_LANGUAGE);
			ResourceBundle bundle = ResourceBundle.getBundle(Constants.PACKAGE_LANGUAGE, FacesContext
					.getCurrentInstance().getViewRoot().getLocale());
			String message = bundle.getString(value);
			if (StringUtils.notIsBlank(message)) {
				LOG.debug("Message recovered");
				LOG.debug("Value recovered [" + message + "]");
				return message;
			} else {
				LOG.debug("Doesn't possible retrive value");
				LOG.debug("Value returned [" + value + "]");
				return value;
			}
		} catch (Exception e) {
			LOG.debug("Doesn't possible retrive value");
			return value;
		}

	}

}
