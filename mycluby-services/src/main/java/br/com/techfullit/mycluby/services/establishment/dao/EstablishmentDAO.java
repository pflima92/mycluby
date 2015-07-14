package br.com.techfullit.mycluby.services.establishment.dao;

import java.util.List;

import br.com.techfullit.mycluby.common.models.Category;
import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.EstablishmentInfo;
import br.com.techfullit.mycluby.common.models.Product;
import br.com.techfullit.mycluby.services.generic.DAO;

public interface EstablishmentDAO extends DAO<Integer, EstablishmentInfo> {

	public abstract Establishment findEstablishmentByMail(String login);
	
	public abstract List<Establishment> findEstablishmentByName(String name);

	public abstract void updateEstablishment(Establishment establishment);

	public abstract EstablishmentInfo findEstablishmentByPattern(String pattern);
	
	public abstract void updateCategory(Category category) throws Exception;

	public abstract void deleteCategory(Category category) throws Exception;

	public abstract void updateProduct(Product product) throws Exception;
	
	public abstract void deleteProduct(Product product) throws Exception;

}