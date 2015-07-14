package br.com.techfullit.mycluby.services.account.business;

import org.springframework.transaction.annotation.Transactional;

import br.com.techfullit.mycluby.common.models.Card;
import br.com.techfullit.mycluby.common.models.Event;
import br.com.techfullit.mycluby.common.models.Picture;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.common.models.User;

public interface AccountService {

    @Transactional
    public ResponseEntity createUserAccount(User user) throws Exception;

    @Transactional
    public ResponseEntity updateUser(Object user) throws Exception;

    @Transactional
    public ResponseEntity deletePicture(Picture picture) throws Exception;

    @Transactional
    public ResponseEntity updateEvent(Event event);

    @Transactional
    public ResponseEntity deleteEvent(Event picture);

    @Transactional
    public ResponseEntity deleteCard(Card card);

    @Transactional
    public ResponseEntity findAllUsers() throws Exception;

    @Transactional
    public ResponseEntity findUsersByMail(String mail) throws Exception;

    @Transactional
    public ResponseEntity findUserByMail(String mail) throws Exception;

    @Transactional
    public ResponseEntity verifyEmailRegistered(String mail) throws Exception;

}
