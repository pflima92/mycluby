package br.com.techfullit.mycluby.controller;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Picture;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.Base64;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.services.account.business.AccountService;

@ViewScoped
@ManagedBean(name = "galleria")
@Controller
public class GalleriaController extends ContexController {

    @ManagedProperty(value = "#{resourcesProperties}")
    private Properties props;

    @ManagedProperty(value = "#{base64}")
    private Base64 base64;

    @ManagedProperty(value = "#{accountService}")
    private AccountService accountService;

    private UploadedFile img;

    private Picture currentPicture;

    private List<Picture> galleria;

    private User user;

    private static final Log LOG = LogFactory.getLog(GalleriaController.class);

    public GalleriaController() {
	resetNewImg();
    }

    public void loadData() {
	if (user == null)
	    user = (User) ContextHelper.getCurrentEntity();
    }

    private void resetNewImg() {
	currentPicture = new Picture();
	currentPicture.setTitle("Título da Foto");
	currentPicture.setDescription("Descrição");
    }

    public void uploadFile(ActionEvent event) {
	try {
	    LOG.debug("Get file of upload filter");
	    byte[] content = img.getContents();
	    LOG.debug("Conver file in base64");

	    currentPicture.setDate(new Date());
	    currentPicture.setValue(content);
	    currentPicture.setUser(user);

	    user.getPictures().add(currentPicture);

	    LOG.debug("Called method [addImage]");
	    ResponseEntity response = null;
	    try {
		response = accountService.updateUser(user);
	    } catch (Exception e) {
		LOG.error(e.getMessage());
		MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
	    }

	    if (!response.isSuccess()) {
		LOG.debug(response.getDescStatus());
		MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
	    } else {
		MessageViewer.showSuccess("Ok", "Imagem adicionada à sua Galeria de Fotos.");
	    }
	    updateUserContext();
	    resetNewImg();

	} catch (Exception e) {
	    LOG.error(e.getMessage());
	    MessageViewer.showError("Ops, tivemos um erro ao processar sua foto, tente novamente.");
	}
    }

    public void deleteImg(ActionEvent event) {
	try {
	    currentPicture = (Picture) event.getComponent().getAttributes().get("action");
	    LOG.debug("Called method [addImage]");
	    ResponseEntity response = null;
	    try {
		response = accountService.deletePicture(currentPicture);
	    } catch (Exception e) {
		LOG.error(e.getMessage());
		MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
	    }

	    if (!response.isSuccess()) {
		LOG.debug(response.getDescStatus());
		MessageViewer.showError("Ops, ocorreu um erro ao atualizar seus dados.");
	    } else {
		MessageViewer.showSuccess("Ok", "Seu perfil foi atualizado com sucesso.");
	    }

	    updateUserContext();

	} catch (Exception e) {
	    LOG.error(e.getMessage());
	    MessageViewer.showError("Ops, tivemos um erro ao processar sua foto, tente novamente.");
	}
	resetNewImg();
    }

    private void updateUserContext() throws Exception {
	ResponseEntity userUpdated = accountService.findUserByMail(user.getMails().get(0).getValue());
	user = (User) userUpdated.getObject();
	ContextHelper.setCurrentEntity(userUpdated.getObject());
    }

    public Properties getProps() {
	return props;
    }

    public void setProps(Properties props) {
	this.props = props;
    }

    public List<Picture> getGalleria() {
	return galleria;
    }

    public void setGalleria(List<Picture> galleria) {
	this.galleria = galleria;
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

    public Picture getCurrentPicture() {
	return currentPicture;
    }

    public void setCurrentPicture(Picture currentPicture) {
	this.currentPicture = currentPicture;
    }

    public UploadedFile getImg() {
	return img;
    }

    public void setImg(UploadedFile img) {
	this.img = img;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }
}
