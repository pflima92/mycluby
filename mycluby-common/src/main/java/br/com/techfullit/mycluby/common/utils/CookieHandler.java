package br.com.techfullit.mycluby.common.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.techfullit.mycluby.common.constants.Constants;

public class CookieHandler {

	public static void put(String name, String value) {
		// FacesContext
		FacesContext context = FacesContext.getCurrentInstance();
		// Make cookie
		Cookie ck = new Cookie(name, value);
		// Timelife
		ck.setMaxAge(-1);
		((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(ck);
	}

	public static void delete(String parameter) {
		FacesContext context = FacesContext.getCurrentInstance();
		// Make cookie
		Cookie ck = new Cookie(parameter, Constants.EMPTY);
		// Timelife
		ck.setMaxAge(0);
		((HttpServletResponse) context.getExternalContext().getResponse()).addCookie(ck);
	}

	public static String get(String parameter) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();

		// obtem a lista de cookies
		Cookie[] cookies = request.getCookies();

		// foreach
		if (cookies != null)
			for (Cookie cookie : cookies) {
				if (cookie.getName().trim().equalsIgnoreCase(parameter)) {
					return cookie.getValue();
				}
			}
		return null;

	}

}
