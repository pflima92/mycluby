package teste;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import br.com.techfullit.mycluby.common.models.MailConfig;
import br.com.techfullit.mycluby.common.models.MailTemplate;
import br.com.techfullit.mycluby.services.mail.business.impl.MailServiceImpl;

public class MailTest {

    static MailServiceImpl service = new MailServiceImpl();

    private static void setup() {

	Properties props = new Properties();
	try {
	    InputStream resource = MailServiceImpl.class.getClass().getResourceAsStream(
		    "/br/com/techfullit/mycluby/services/config/mail.properties");
	    props.load(resource);
	    resource.close();
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	    ex.printStackTrace();
	}
	service.setProps(props);

    }

    public static void main(String[] args) {

	setup();
	MailConfig mailConfig = new MailConfig();
	mailConfig.setTo("pflima92@gmail.com");
	mailConfig.setHtmlBody(true);
	mailConfig.setSubject("Bem vindo ao MyCLuby Social");

	MailTemplate template = new MailTemplate(
		"Paulo Henrique",
		"Sua conta foi criada com sucesso! <br/><br/> "
		+ "Agora você faz parte da rede do MyCluby, aproveite e chame seus amigos para fazer parte também. <br/><br/>"
		+ "Acesse o portal Social do MyCluby: http://www.mycluby.com.br <br/><br/>"
		+ "Não esqueça de baixar o nosso aplicativo para seu Smartphone e fique conectado com o MyCluby o tempo todo!");
	
	mailConfig.setBodyContent(template.getTemplate());
	try {
	    service.sendMail(mailConfig);
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

}
