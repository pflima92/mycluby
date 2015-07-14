package br.com.techfullit.mycluby.phaselistener;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.techfullit.faces.permission.context.springframework.PermissionContext;
import br.com.techfullit.faces.permission.models.ResponseCode;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Role;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContextHelper;

public class AuthenticationPhaseListener implements PhaseListener {

	private static final Log LOG = LogFactory.getLog(AuthenticationPhaseListener.class);

	private static final long serialVersionUID = 7120075478878931668L;

	public AuthenticationPhaseListener() {
		if (permissionContext == null) {
			permissionContext = new PermissionContext("br/com/techfullit/mycluby/permission/permissions.xml");
			permissionContext.setIgnoreResourceNotFound(false);
		}
	}

	@Resource(name = "permissionContext")
	private PermissionContext permissionContext;

	@Override
	public void afterPhase(PhaseEvent event) {
		LOG.debug("END PHASE " + event.getPhaseId());
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		FacesContext fc = event.getFacesContext();
		LOG.debug("START PHASE " + event.getPhaseId());
		String currentRequest = fc.getViewRoot().getViewId().startsWith("//") ? fc.getViewRoot().getViewId()
				.substring(1, fc.getViewRoot().getViewId().length()) : fc.getViewRoot().getViewId();
		LOG.debug("CURRENT PAGE: " + currentRequest);
		
		if(currentRequest.contains("service/")){
		    return;
		}

		NavigationHandler nh = fc.getApplication().getNavigationHandler();

		ArrayList<Role> iterateRoles = new ArrayList<Role>();
		if(ContextHelper.getCurrentEntity() instanceof User){
			User u = (User) ContextHelper.getCurrentEntity();
			iterateRoles.addAll(u.getRoles());
		}else{
			Establishment e = (Establishment)  ContextHelper.getCurrentEntity();
			iterateRoles.addAll(e.getRoles());
		}
		LOG.debug("Create list of roles to authenticate");
		ArrayList<String> userRoles = new ArrayList<String>();
		for (Role role :iterateRoles) {
			LOG.debug("Role added: " + role.getTitle());
			userRoles.add(role.getTitle());
		}

		LOG.debug("Call process to authenticate and validate url and target pages");
		String response = permissionContext.validateUrl(currentRequest, userRoles);
		LOG.debug("PermissionContext return fallow response: " + response);

		if (!response.equals(ResponseCode.SUCCESS)) {
			LOG.debug("HandleNavigation for " + response);
			if(response.equals("start")){
				if(ContextHelper.getCurrentEntity() instanceof User){
					response = "index.xhtml";
				}else{
					response = "/establishment/main.xhtml";
				}
			}
			//nh.handleNavigation(fc, null, response);
			try {
			    event.getFacesContext().getExternalContext().redirect(fc.getExternalContext().getRequestContextPath() + response);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}

		LOG.debug("Continue process...");
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public PermissionContext getPermissionContext() {
		return permissionContext;
	}

	public void setPermissionContext(PermissionContext permissionContext) {
		this.permissionContext = permissionContext;
	}

}
