package br.com.techfullit.mycluby.services.authentication.business;

import org.springframework.transaction.annotation.Transactional;

import br.com.techfullit.mycluby.common.models.Portal;
import br.com.techfullit.mycluby.common.models.ResponseEntity;

public interface AuthenticationService {

    @Transactional
    public ResponseEntity validateDatabaseAuthentication(String login, String password) throws Exception;

    @Transactional
    public ResponseEntity validatePortalAuthentication(Portal portal, String login, String password) throws Exception;

    @Transactional
    public ResponseEntity requestForgivePass(String login) throws Exception;

}
