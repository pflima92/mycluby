package br.com.techfullit.mycluby.common.utils;

import javax.servlet.http.HttpSession;

public class SessionHandler extends ContexController {

	public static void createSession() {
		getContext().getExternalContext().getSession(true);
	}

	public static void destroySession() {
		HttpSession session = (HttpSession) getContext().getExternalContext().getSession(false);
		session.invalidate();
	}

	public static void put(String arg, Object obj) {
		HttpSession session = (HttpSession) getContext().getExternalContext().getSession(false);
		if (session == null) {
			createSession();
			session = (HttpSession) getContext().getExternalContext().getSession(false);
		}
		session.setAttribute(arg, obj);
	}

	public static Object get(String parameter) {
		Object obj = null;
		try {
			HttpSession session = (HttpSession) getContext().getExternalContext().getSession(false);
			obj = session.getAttribute(parameter);
		} catch (NullPointerException e) {
			obj = null;
		}

		return obj;
	}

}
