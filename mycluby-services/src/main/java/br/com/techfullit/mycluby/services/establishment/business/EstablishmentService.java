package br.com.techfullit.mycluby.services.establishment.business;

import org.springframework.transaction.annotation.Transactional;

import br.com.techfullit.mycluby.common.models.Category;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.Product;
import br.com.techfullit.mycluby.common.models.ResponseEntity;

public interface EstablishmentService {

    	@Transactional
	public ResponseEntity createEstablishmentAccount(Establishment establishment) throws Exception;

	@Transactional
	public ResponseEntity updateEstablishment(Object establishment) throws Exception;

	@Transactional
	public ResponseEntity findAllEstablishment() throws Exception;
	
	@Transactional
	public ResponseEntity refreshEstablishment(Establishment establishment) throws Exception;

	@Transactional
	public ResponseEntity findEstablishmentByPattern(String pattern) throws Exception;
	
	@Transactional
	public ResponseEntity findEstablishmentByMail(String mail) throws Exception;
	
	@Transactional
	public ResponseEntity findEstablishmentByName(String mail) throws Exception;
	
	@Transactional
	public ResponseEntity updateCategory(Category category) throws Exception;

	@Transactional
	public ResponseEntity deleteCategory(Category category) throws Exception;

	@Transactional
	public ResponseEntity updateProduct(Product product) throws Exception;
	
	@Transactional
	public ResponseEntity deleteProduct(Product product) throws Exception;
}


