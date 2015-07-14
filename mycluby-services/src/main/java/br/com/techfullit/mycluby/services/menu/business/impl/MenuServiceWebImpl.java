package br.com.techfullit.mycluby.services.menu.business.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;

import br.com.techfullit.mycluby.common.models.Menu;
import br.com.techfullit.mycluby.common.models.ResponseCode;
import br.com.techfullit.mycluby.common.models.ResponseEntity;
import br.com.techfullit.mycluby.services.menu.business.MenuService;
import br.com.techfullit.mycluby.services.menu.dao.MenuDAO;

public class MenuServiceWebImpl implements MenuService {

	@Resource(name = "menuDAO")
	private MenuDAO menuDAO;

	public ResponseEntity retrieveMenu() {
		ResponseEntity response = new ResponseEntity();
		try {
			ArrayList<Menu> listMenu = (ArrayList<Menu>) menuDAO.retrieveMenu(null);
			response.setStatus(ResponseCode.SUCCESS);
			response.setObject(listMenu);
		} catch (ClassNotFoundException | SQLException e) {
			return new ResponseEntity(e);
		}
		return response;
	}

	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDao) {
		this.menuDAO = menuDao;
	}

}
