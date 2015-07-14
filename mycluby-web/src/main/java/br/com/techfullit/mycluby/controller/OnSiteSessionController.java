package br.com.techfullit.mycluby.controller;

import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.mail.MessagingException;

import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.models.Consume;
import br.com.techfullit.mycluby.common.models.MailConfig;
import br.com.techfullit.mycluby.common.models.MailTemplate;
import br.com.techfullit.mycluby.common.models.OnSite;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.Sale;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.MessageViewer;
import br.com.techfullit.mycluby.common.utils.StringUtils;
import br.com.techfullit.mycluby.events.Events;
import br.com.techfullit.mycluby.services.establishment.business.EstablishmentService;
import br.com.techfullit.mycluby.services.mail.business.MailService;
import br.com.techfullit.mycluby.services.onsite.business.OnSiteService;
import br.com.techfullit.mycluby.services.payment.business.PaymentService;

@ViewScoped
@ManagedBean(name = "onSite")
@Controller
public class OnSiteSessionController extends ContexController {

    @ManagedProperty(value = "#{establishmentService}")
    private EstablishmentService establishmentService;

    @ManagedProperty(value = "#{onSiteService}")
    private OnSiteService onSiteService;

    @ManagedProperty(value = "#{paymentService}")
    private PaymentService paymentService;
    
    @ManagedProperty(value = "#{mailService}")
    private MailService mailService;

    private OnSite currentSession;

    private boolean closeConsumeAvailable;
    
    private boolean paymentAvailable;
    
    public void paymentMessage(){
	refreshSession();
	if (isPaymentAvailable()) {
	    MessageViewer.showWarning("Informação", "Seu comando está ativo, você pode efetuar o pagamento via Pague Seguro, ou pedir a um atendente uma forma de encerrar sua consumação.");
	} else {
	    MessageViewer.showWarning("Informação", "Seu comando não está ativo, você pode fechar sua consumação sem efetivar nenhum pagamento.");
	}
    }

    public void refreshSession() {
	ResponseEntity onSiteResponse = null;
	try {
	    onSiteResponse = onSiteService.retrieveActiveOnSiteSession((User) ContextHelper.getCurrentEntity());
	    if (onSiteResponse.isSuccess()) {
		currentSession = (OnSite) onSiteResponse.getObject();
	    } else {
		currentSession = new OnSite();
	    }
	} catch (Exception e) {
	    MessageViewer.showError(e.getMessage());
	    currentSession = new OnSite();
	}
    }

    public void refreshNotification() {
	User currentUser = (User) ContextHelper.getCurrentEntity();
	if (currentUser.getAccountInfo().isMobileNotification()) {
	    if (!StringUtils.isBlank(getCurrentSession().getEstablishment().getEstablishmentInfo().getNotification())) {
		MessageViewer.showWarning("", getCurrentSession().getEstablishment().getEstablishmentInfo()
			.getNotification());
	    }
	}
    }

    public String checkOut() {
	ResponseEntity response = null;
	try {
	    response = onSiteService.checkOut(currentSession);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if (response.isWarning() || response.isError()) {
	    MessageViewer.showError(response.getDescStatus());
	} else {
		sendConsume(null);
	    return Events.PLACE_CEHCKIN;
	}
	return Events.EMPTY;

    }

    public void callPayment(ActionEvent event) {
	ResponseEntity response = null;
	try {
	    response = paymentService.performPayment(currentSession);
	} catch (Exception e) {
	    MessageViewer.showError("Ops, ocorreu um erro ao processar seu pagamento, tente novamente mais tarde");
	    return;
	}
	if (response.isWarning() || response.isError()) {
	    MessageViewer.showError(response.getDescStatus());
	} else {
	    MessageViewer.showSuccess("Ok", "Seu pagamento foi registrado com sucesso");
	}
    }

    public EstablishmentService getEstablishmentService() {
	return establishmentService;
    }

    public void setEstablishmentService(EstablishmentService establishmentService) {
	this.establishmentService = establishmentService;
    }

    public OnSiteService getOnSiteService() {
	return onSiteService;
    }

    public void setOnSiteService(OnSiteService onSiteService) {
	this.onSiteService = onSiteService;
    }

    public OnSite getCurrentSession() {
	if (currentSession == null) {
	    refreshSession();
	}
	return currentSession;
    }

    public void setCurrentSession(OnSite currentSession) {
	this.currentSession = currentSession;
    }

    public boolean isCloseConsumeAvailable() {
	if (currentSession.getConsume().getStatus().equals(Consume.ACTIVE)) {
	    closeConsumeAvailable = true;
	} else {
	    closeConsumeAvailable = false;
	}
	return closeConsumeAvailable;
    }
    
    

    public boolean isPaymentAvailable() {
	if (currentSession.getConsume().getStatus().equals(Consume.ACTIVE) || currentSession.getConsume().getStatus().equals(Consume.PENDING_PAYMENT)) {
	    paymentAvailable = true;
	} else {
	    paymentAvailable = false;
	}
	return paymentAvailable;
    }

    public void setPaymentAvailable(boolean paymentAvailable) {
        this.paymentAvailable = paymentAvailable;
    }

    public void setCloseConsumeAvailable(boolean closeConsumeAvailable) {
	this.closeConsumeAvailable = closeConsumeAvailable;
    }

    public PaymentService getPaymentService() {
	return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
	this.paymentService = paymentService;
    }
    
    
    
    public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public void sendConsume(ActionEvent event){
    
		
		  MailConfig mailConfig = new MailConfig();
		    mailConfig.setTo(currentSession.getUser().getMails().get(0).getValue());
		    mailConfig.setHtmlBody(true);
		    mailConfig.setSubject("Extrato da sua Consumação MyCluby Social");
		    
		    MailTemplate template = new MailTemplate(
		    		currentSession.getUser().getFirstname(),  		prepareMailContent());
		    
		    mailConfig.setBodyContent(template.getTemplate());
		    try {
			mailService.sendMail(mailConfig);
			MessageViewer.showSuccess("Ok", "Foi enviado um email com seu extrato de consumação.");
		    } catch (MessagingException e) {
			MessageViewer.showError("Ops, não consegui te mandar um email.");
		    }
		
    }
	
	private String prepareMailContent(){
		
		StringBuffer mail = new StringBuffer();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		mail.append("<p>Obrigado por utilizar o MyCluby Social<p>");
		mail.append("<p>Estamos enviando um extrato do seu consumo.<p>");
		
		mail.append("<p>Horario do Check-in:  "+sdf.format(currentSession.getCheckIn())+"<p>");
		if(currentSession.getStatus().equals(Consume.CLOSED)){
			mail.append("<p>Horario do Check-out:  "+sdf.format(currentSession.getCheckOut())+"<p>");
		}
		mail.append("Nome do estabelecimento: <p>"+currentSession.getEstablishment().getName()+"<p>");
		mail.append("<p>Consuma ID:  "+currentSession.getConsume().getId()+"<p>");
		
		mail.append("<table>"); 
		
		for (Sale sale : currentSession.getConsume().getSales()) {
			mail.append("<tr>");
			mail.append("<td>Item: "+sale.getProduct().getName()+" </td>");
			if(!sale.isPromotional()){
				mail.append("<td>Valor R$:"+sale.getProduct().getPrice()+"</td>");
			}else{
				mail.append("<td>Valor R$:"+sale.getProduct().getPromotional()+"</td>");
			}
			mail.append("</tr>");
		}
		
		mail.append("</table>");
		
		mail.append("<p>Valor total da Consumação  R$:"+currentSession.getConsume().getTotalAmount()+"<p>");
		
		return mail.toString();
		
	}

}