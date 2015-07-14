package br.com.techfullit.mycluby.services.authentication.dao;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Mail;

public interface AuthenticationDAO {

	public abstract Mail findEntityByMail(String login);

	public abstract Establishment findEstablishmentByPattern(String pattern);

}