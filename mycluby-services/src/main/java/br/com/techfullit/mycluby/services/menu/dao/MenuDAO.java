package br.com.techfullit.mycluby.services.menu.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.techfullit.mycluby.common.models.Menu;

public interface MenuDAO {

	public abstract ArrayList<Menu> retrieveMenu(Object request) throws ClassNotFoundException, SQLException;

}