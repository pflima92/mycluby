package br.com.techfullit.mycluby.common.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Portal;
import br.com.techfullit.mycluby.common.models.Role;
import br.com.techfullit.mycluby.common.models.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ContextHelper {

	public static boolean userIsLogged() {
		Object obj = getCurrentEntity();
		if (obj != null) {
			if (obj instanceof User) {
				User u = (User) obj;
				if (u.getFirstname().equals("Annonymous")) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;

			}
		} else {
			return false;
		}
	}
	
	public static Portal getPortalData(){
		Portal portal = (Portal) SessionHandler.get(Constants.PORTAL_DATA);
		if(portal == null){
			portal = new Portal();
		}
		return portal;
	}
	
	public static void setPortalData(HttpServletRequest servletRequest, Portal portal){
		HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
		session.setAttribute(Constants.PORTAL_DATA, portal);
	}


	public static Object getCurrentEntity() {
		Object entity = null;
		String currentEntity = (String) SessionHandler.get(Constants.CURRENT_ENTITY_LOGGED);
		if (currentEntity == null) {
			return setUserAnnonymous();
		}
		if (currentEntity.equals(Constants.CURRENT_USER)) {
			entity = SessionHandler.get(Constants.CURRENT_USER);
			if (entity == null) {
				String cookieRemember = CookieHandler.get(Constants.COOKIE_REMEMBER);
				User user = null;
				if (cookieRemember != null ? (cookieRemember.equals("true") ? true : false) : false) {
					try {
						ObjectMapper mapper = new ObjectMapper();
						user = mapper.readValue(CookieHandler.get(Constants.COOKIE_USERDATA),
								new TypeReference<User>() {
								});
					} catch (Exception e) {
						return setUserAnnonymous();
					}
					if (user != null) {
						SessionHandler.put(Constants.CURRENT_USER, user);
						return user;
					}
				}
			}
		} else {
			entity = SessionHandler.get(Constants.CURRENT_ESTABLISHMET);
			if (entity == null) {
				String cookieRemember = CookieHandler.get(Constants.COOKIE_REMEMBER);
				Establishment establishment = null;
				if (cookieRemember != null ? (cookieRemember.equals("true") ? true : false) : false) {
					try {
						ObjectMapper mapper = new ObjectMapper();
						establishment = mapper.readValue(CookieHandler.get(Constants.COOKIE_USERDATA),
								new TypeReference<User>() {
								});
					} catch (Exception e) {
						return setUserAnnonymous();
					}
					if (establishment != null) {
						SessionHandler.put(Constants.CURRENT_USER, establishment);
						return establishment;
					}
				}
			}
		}
		return entity;
	}

	public static void setCurrentEntity(Object obj) {
		if (obj instanceof User) {
			SessionHandler.put(Constants.CURRENT_ENTITY_LOGGED, Constants.CURRENT_USER);
			SessionHandler.put(Constants.CURRENT_USER, obj);
		} else {
			SessionHandler.put(Constants.CURRENT_ENTITY_LOGGED, Constants.CURRENT_USER);
			SessionHandler.put(Constants.CURRENT_USER, obj);
		}
	}

	public static User setUserAnnonymous() {
		User user = new User();
		user.setFirstname("Annonymous");
		user.getRoles().add(Role.ROLE_UNSIGNED);
		return user;
	}

	public static List<Role> getEntityRoles() {
		Object response = getCurrentEntity();
		if (response != null) {
			if (response instanceof User) {
				User u = (User) response;
				return u.getRoles();
			} else {
				Establishment e = (Establishment) response;
				return e.getRoles();
			}
		} else {
			ArrayList<Role> roles = new ArrayList<Role>();
			roles.add(Role.ROLE_UNSIGNED);
			return roles;
		}
	}

}
