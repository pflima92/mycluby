package br.com.techfullit.mycluby.services.menu.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.techfullit.mycluby.common.models.Label;
import br.com.techfullit.mycluby.common.models.Menu;
import br.com.techfullit.mycluby.common.utils.Utils;
import br.com.techfullit.mycluby.services.menu.dao.MenuDAO;

public class MenuDAOImpl implements MenuDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.techfullit.cas.services.menu.dao.impl.MenuDAO#retrieveWebData()
	 */
	@Override
	public ArrayList<Menu> retrieveMenu(Object request) throws ClassNotFoundException, SQLException {
		ArrayList<Menu> links = new ArrayList<>();

		Menu item1 = new Menu();
		item1.setLabel(new Label("Painel de Controle"));
		item1.setTarget("");
		item1.setRendered(true);

		Menu child1 = new Menu();
		child1.setLabel(new Label("Início"));
		child1.setTarget("/pages/index.xhtml");
		child1.setRendered(true);
		child1.setIcon("view_page");

		Menu child2 = new Menu();
		child2.setLabel(new Label("Meu Perfil"));
		child2.setTarget("/pages/account/myAccount.xhtml");
		child2.setRendered(true);
		child2.setIcon("users");

		Menu child3 = new Menu();
		child3.setLabel(new Label("Alterar dados"));
		child3.setTarget("/pages/account/editAccount.xhtml");
		child3.setRendered(true);
		child3.setIcon("report");

		Menu child4 = new Menu();
		child4.setLabel(new Label("Preferências"));
		child4.setTarget("/pages/account/preferences.xhtml");
		child4.setRendered(true);
		child4.setIcon("config");

		Menu child5 = new Menu();
		child5.setLabel(new Label("Cadastrar Funcionário"));
		child5.setTarget("/pages/staff/createStaff.xhtml");
		child5.setRendered(true);
		child5.setIcon("add_page");
		
		Menu mngStaff = new Menu();
		mngStaff.setLabel(new Label("Manutenção de Funcionários"));
		mngStaff.setTarget("/pages/staff/manageStaff.xhtml");
		mngStaff.setRendered(true);
		mngStaff.setIcon("category");

		item1.getChilds().add(child1);
		/*item1.getChilds().add(child2);
		item1.getChilds().add(child3);
		item1.getChilds().add(child4);*/
		item1.getChilds().add(child5);
		item1.getChilds().add(mngStaff);

		Menu item2 = new Menu();
		item2.setLabel(new Label("Manutenção de Produtos"));
		item2.setRendered(true);
		item2.setTarget("");

		Menu child6 = new Menu();
		child6.setLabel(new Label("Cadastrar nova Categoria"));
		child6.setTarget("/pages/category/createCategory.xhtml");
		child6.setRendered(true);
		child6.setIcon("add_page");

		Menu child7 = new Menu();
		child7.setLabel(new Label("Manutenção de Categorias"));
		child7.setTarget("/pages/category/manageCategory.xhtml");
		child7.setRendered(true);
		child7.setIcon("category");
		
		Menu childProduct = new Menu();
		childProduct.setLabel(new Label("Cadastrar novo Produto"));
		childProduct.setTarget("/pages/product/createProduct.xhtml");
		childProduct.setRendered(true);
		childProduct.setIcon("add_page");

		Menu childManageProduct = new Menu();
		childManageProduct.setLabel(new Label("Manutenção de Produtos"));
		childManageProduct.setTarget("/pages/product/manageProduct.xhtml");
		childManageProduct.setRendered(true);
		childManageProduct.setIcon("category"); 
		
		Menu childPromotion = new Menu();
		childPromotion.setLabel(new Label("Cadastro de Promoções"));
		childPromotion.setTarget("/pages/promotion/createPromotion.xhtml");
		childPromotion.setRendered(true);
		childPromotion.setIcon("add_page");
		
		Menu childManagePromotion = new Menu();
		childManagePromotion.setLabel(new Label("Manutenção de Promoções"));
		childManagePromotion.setTarget("/pages/promotion/managePromotion.xhtml");
		childManagePromotion.setRendered(true);
		childManagePromotion.setIcon("category");
		

		item2.getChilds().add(child6);
		item2.getChilds().add(child7);
		item2.getChilds().add(childProduct);
		item2.getChilds().add(childManageProduct);
		//item2.getChilds().add(childPromotion);
	//item2.getChilds().add(childManagePromotion);

		Menu gerenciaPagamentos = new Menu();
		gerenciaPagamentos.setLabel(new Label("Gerência do Estabelecimento"));
		gerenciaPagamentos.setTarget("");
		gerenciaPagamentos.setRendered(true);

		Menu child8 = new Menu();
		child8.setLabel(new Label("Gerenciar meu Estabelecimento"));
		child8.setTarget("/pages/establishment/manageEstablishment.xhtml");
		child8.setRendered(true);
		child8.setIcon("category");

		Menu child9 = new Menu();
		child9.setLabel(new Label("Emissão de Relatórios"));
		child9.setTarget("/pages/reports/reports.xhtml");
		child9.setRendered(true);
		child9.setIcon("category");
		
		Menu child10 = new Menu();
		child10.setLabel(new Label("Gerenciar Clientes Onsite"));
		child10.setTarget("/pages/client/manageClient.xhtml");
		child10.setRendered(true);
		child10.setIcon("category");

//		gerenciaPagamentos.getChilds().add(child8);
		gerenciaPagamentos.getChilds().add(child10);
		gerenciaPagamentos.getChilds().add(child9);

		links.add(item1);
		links.add(item2);
		links.add(gerenciaPagamentos);

		for (Menu menu : links) {

			menu.setUiId("uid" + Utils.removeTrelling(menu.getLabel().getValue()));

			for (Menu child : menu.getChilds()) {
				child.setUiId(Utils.removeTrelling(menu.getLabel().getValue()) + "_"
						+ Utils.removeTrelling(child.getLabel().getValue()));
			}
		}

		return links;

	}

}
