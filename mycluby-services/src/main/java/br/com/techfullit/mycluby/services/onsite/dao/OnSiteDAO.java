package br.com.techfullit.mycluby.services.onsite.dao;

import java.util.List;

import br.com.techfullit.mycluby.common.models.Establishment;
import br.com.techfullit.mycluby.common.models.OnSite;
import br.com.techfullit.mycluby.common.models.Sale;
import br.com.techfullit.mycluby.common.models.User;
import br.com.techfullit.mycluby.services.generic.DAO;

public interface OnSiteDAO extends DAO<Integer, OnSite> {
    
    public List<OnSite> findOnSiteSessionsByUser(User u);
    
    public List<OnSite> findOnSiteSessionsByEstablishment(Establishment e);
    
    public void insertSale(Sale sale);

    public void updateSale(Sale sale);

}