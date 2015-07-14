package br.com.techfullit.mycluby.services.generic;

import java.io.Serializable;
import java.util.List;

import org.hibernate.exception.DataException;

public interface DAO<PK extends Serializable, T> {

	public abstract void insert(T obj) throws DataException;

	public abstract void update(T obj) throws DataException;

	public abstract void delete(T obj) throws DataException;

	public abstract T findById(PK pk);

	public abstract List<T> findAll(Class<T> classe);

}