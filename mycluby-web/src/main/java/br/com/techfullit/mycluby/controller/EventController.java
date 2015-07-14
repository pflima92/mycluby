package br.com.techfullit.mycluby.controller;

import java.util.Date;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Event;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.Base64;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.services.account.business.AccountService;
import br.com.techfullit.mycluby.services.establishment.business.EstablishmentService;

@ViewScoped
@ManagedBean(name = "events")
@Controller
public class EventController extends ContexController {

    @ManagedProperty(value = "#{resourcesProperties}")
    private Properties props;

    @ManagedProperty(value = "#{base64}")
    private Base64 base64;

    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;
    
    @ManagedProperty(value="#{establishmentService}")
    private EstablishmentService establishmentService;

    private UploadedFile img;

    private Event currentEvent;

    private Object user;

    private static final Log LOG = LogFactory.getLog(EventController.class);

    public EventController() {
	resetEvent();
    }

    public void loadData() {
	if (user == null)
	    user = ContextHelper.getCurrentEntity();
    }

    private void resetEvent() {
	currentEvent = new Event();
	currentEvent.setTitle("Título do Evento");
	currentEvent.setType("Local do Evento");
	currentEvent.setDescription("Descrição");
	currentEvent.setDate(new Date());
    }

    public void createEvent(ActionEvent event) {
	try {
	    LOG.debug("Get file of upload filter");
	    byte[] content = img.getContents();
	    LOG.debug("Conver file in base64");

	    currentEvent.setImage(content);
	    if(user instanceof User){
		currentEvent.setUser((User)user);
		((User) user).getEvents().add(currentEvent);
	    }
	    else{
		currentEvent.setEstablishment((Establishment) user);
		((Establishment)user).getEvents().add(currentEvent);
	    }

	    LOG.debug("Called method [addImage]");
	    ResponseEntity response = null;
	    try {
		if(user instanceof User){
		    response = accountService.updateUser(user);
		}else{
		    response = establishmentService.updateEstablishment(user);
		}
	    } catch (Exception e) {
		LOG.error(e.getMessage());
		MessageViewer.showError("Ops, ocorreu um erro ao criar evento.");
	    }

	    if (!response.isSuccess()) {
		LOG.debug(response.getDescStatus());
		MessageViewer.showError("Ops, ocorreu um erro criar evento.");
	    } else {
		MessageViewer.showSuccess("Ok", "Seu evento foi criado com sucesso");
	    }
	    updateUserContext();
	    resetEvent();

	} catch (Exception e) {
	    LOG.error(e.getMessage());
	    MessageViewer.showError("Ops, tivemos um erro ao processar sua imagem, tente novamente.");
	}
    }

    public void deleteEvent(ActionEvent event) {
	try {
	    currentEvent = (Event) event.getComponent().getAttributes().get("action");
	    LOG.debug("Called method [addImage]");
	    ResponseEntity response = null;
	    try {
		response = accountService.deleteEvent(currentEvent);
	    } catch (Exception e) {
		LOG.error(e.getMessage());
		MessageViewer.showError("Ops, ocorreu um erro deletar este evento.");
	    }

	    if (!response.isSuccess()) {
		LOG.debug(response.getDescStatus());
		MessageViewer.showError("Ops, ocorreu um erro criar evento.");
	    } else {
		MessageViewer.showSuccess("Ok", "Seu evento foi removido com sucesso");
	    }

	    updateUserContext();

	} catch (Exception e) {
	    LOG.error(e.getMessage());
	    MessageViewer.showError("Ops, tivemos um erro ao processar seu evento, tente novamente.");
	}
	resetEvent();
    }

    private void updateUserContext() throws Exception {
	if(user instanceof User){
	ResponseEntity userUpdated = accountService.findUserByMail(((User)user).getMails().get(0).getValue());
	user = (User) userUpdated.getObject();
	ContextHelper.setCurrentEntity(userUpdated.getObject());
	}
    }

    public Properties getProps() {
	return props;
    }

    public void setProps(Properties props) {
	this.props = props;
    }

    public Base64 getBase64() {
	return base64;
    }

    public void setBase64(Base64 base64) {
	this.base64 = base64;
    }

    public AccountService getAccountService() {
	return accountService;
    }

    public void setAccountService(AccountService accountService) {
	this.accountService = accountService;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public UploadedFile getImg() {
	return img;
    }

    public void setImg(UploadedFile img) {
	this.img = img;
    }

    public Object getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public EstablishmentService getEstablishmentService() {
        return establishmentService;
    }

    public void setEstablishmentService(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

	public void setUser(Object user) {
		this.user = user;
	}
    
    
}
