package br.com.techfullit.mycluby.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import br.com.techfullit.mycluby.common.constants.Constants;
import br.com.techfullit.mycluby.common.models.ContextInformation;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Search;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.common.utils.ContexController;
import br.com.techfullit.mycluby.common.utils.ContextHelper;
import br.com.techfullit.mycluby.common.utils.SessionHandler;
import br.com.techfullit.mycluby.events.Events;

@ViewScoped
@ManagedBean(name = "core")
@Controller
public class CoreController extends ContexController {

    @ManagedProperty(value = "#{resourcesProperties}")
    private Properties props;

    private static final Log LOG = LogFactory.getLog(CoreController.class);

    private ContextInformation ctxInfo;

    private Integer miletimeGrowl = 9000;

    private String targetPage;

    private boolean userLogged;

    private Search magicItem;

    private String templateBase;

    private boolean mobile;

    private Date now;

    public CoreController() {
    }

    public String redirect() {
	LOG.debug("Redirect Page to target page: " + targetPage);
	LOG.debug("Verify roles to access.");
	if (targetPage == null || targetPage.equals(Constants.EMPTY)) {
	    return Constants.EMPTY;
	}
	return targetPage + "?faces-redirect=true";
    }

    public List<Search> magicSearch(String query) {
	List<Search> results = new ArrayList<Search>();
	List<Search> filter = new ArrayList<Search>();

	results.add(new Search("Paulo Henrique Ferreira de Lima", (String) props.get(Constants.DEFAUL_PROFILE_IMAGE),
		new User()));
	results.add(new Search("Luiz Fernando ", (String) props.get(Constants.DEFAUL_PROFILE_IMAGE), new User()));
	results.add(new Search("Jaqueline Talita ", (String) props.get(Constants.DEFAUL_PROFILE_IMAGE), new User()));
	results.add(new Search("Caribbean Disc CLub ", (String) props.get(Constants.DEFAUL_PROFILE_IMAGE), new User()));
	results.add(new Search("Bar ", (String) props.get(Constants.DEFAUL_PROFILE_IMAGE), new User()));
	for (Search search : results) {
	    if (search.getLabel().contains(query))
		filter.add(search);

	}
	return filter;
    }

    public void onMagicItemSelect() {
	LOG.debug("Select item: " + magicItem.getLabel());
    }

    public ContextInformation getCtxInfo() {
	LOG.debug("Init retrieve informations");
	ctxInfo = buildContextInformation();
	LOG.debug("Finish retrieve informations");
	return ctxInfo;
    }

    public String viewMyProfile() {
	SessionHandler.put(Constants.USER_TO_VIEW, ContextHelper.getCurrentEntity());
	return Events.PROFILE;
    }

    public String viewMyEstablishmentProfile() {
	SessionHandler.put(Constants.ESTABLISHMENT_TO_VIEW, ContextHelper.getCurrentEntity());
	return Events.ESTABLISHMENT_VIEW;
    }

    public void viewEstablishmentProfile(ActionEvent event) {
	Establishment establishment = (Establishment) event.getComponent().getAttributes().get("establishmentProfile");
	SessionHandler.put(Constants.ESTABLISHMENT_TO_VIEW, establishment);
	NavigationHandler nh = getContext().getApplication().getNavigationHandler();
	nh.handleNavigation(getContext(), null, Events.ESTABLISHMENT_VIEW);

    }

    public void viewUserProfile(ActionEvent event) {
	User user = (User) event.getComponent().getAttributes().get("userProfile");
	SessionHandler.put(Constants.USER_TO_VIEW, user);
	NavigationHandler nh = getContext().getApplication().getNavigationHandler();
	nh.handleNavigation(getContext(), null, Events.PROFILE);

    }

    public void setCtxInfo(ContextInformation ctxInfo) {
	this.ctxInfo = ctxInfo;
    }

    public String getTargetPage() {
	return targetPage;
    }

    public void setTargetPage(String targetPage) {
	this.targetPage = targetPage;
    }

    public boolean isUserLogged() {
	userLogged = ContextHelper.userIsLogged();
	return userLogged;
    }

    public void setUserLogged(boolean userLogged) {
	this.userLogged = userLogged;
    }

    public Integer getMiletimeGrowl() {
	return miletimeGrowl;
    }

    public void setMiletimeGrowl(Integer miletimeGrowl) {
	this.miletimeGrowl = miletimeGrowl;
    }

    public Search getMagicItem() {
	return magicItem;
    }

    public void setMagicItem(Search magicItem) {
	this.magicItem = magicItem;
    }

    public Properties getProps() {
	return props;
    }

    public void setProps(Properties props) {
	this.props = props;
    }

    public String getTemplateBase() {
	if (ContextHelper.getCurrentEntity() instanceof User) {
	    if (ContextHelper.userIsLogged()) {
		if (isMobile()) {
		    templateBase = "/template/webcontent/mlayout.xhtml";
		} else {
		    templateBase = "/template/webcontent/layout.xhtml";
		}
	    } else {
		templateBase = "/template/webcontent/simple.xhtml";
	    }
	} else {
	    templateBase = "/template/establishmentcontent/layout.xhtml";
	}
	return templateBase;
    }

    public void setTemplateBase(String templateBase) {
	this.templateBase = templateBase;
    }

    public boolean isMobile() {
	String deviceActive = System.getProperty("deviceMode");
	if (deviceActive != null && deviceActive.equals("true")) {

	    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
		    .getRequest();
	    String ua = request.getHeader("User-Agent").toLowerCase();
	    if (ua.matches("(?i).*((android|bb\\d+|meego).+mobile|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge|maemo|midp|mmp|netfront|opera m(ob|in)i|palm(os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\\.(browser|link)|vodafone|wap|windows(ce|phone)|xda|xiino).*")
		    || ua.substring(0, 4)
			    .matches(
				    "(?i)1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|awa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r|s)|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp(i|ip)|hs\\-c|ht(c(\\-||_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac(|\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt(|\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg(g|\\/(k|l|u)|50|54|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-||o|v)|zz)|mt(50|p1|v)|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v)|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-|)|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\\-|your|zeto|zte\\-")) {
		mobile = true;
	    } else {
		mobile = false;
	    }
	}else{
	    return true;
	}
	return mobile;
    }

    public void setMobile(boolean mobile) {
	this.mobile = mobile;
    }

    public Date getNow() {
	now = new Date();
	return now;
    }

    public void setNow(Date now) {
	this.now = now;
    }

}
