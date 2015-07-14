package br.com.techfullit.mycluby.common.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({ @NamedQuery(name = "Employee.findAllEmployers", query = "from Employee e") })
@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Cloneable {

    @Id
    @Column(name = "ID_EMPLOYEE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME_EMPLOYEE")
    private String name;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_ESTABLISHMENT")
    private Establishment establishment;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", targetEntity = Sale.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Sale> sales;

    @Transient
    private ArrayList<Role> roles;

    public ArrayList<Role> getRoles() {
	if (roles == null)
	    roles = new ArrayList<Role>();
	return roles;
    }

    @Transient
    public double getTotalAmount() {
	double totalAmount = 0;
	for (Sale sale : getSales()) {
	    if (!sale.isReversalIndicative()) {
		if (sale.isPromotional()) {
		    totalAmount += sale.getProduct().getPromotional();
		} else {
		    totalAmount += sale.getProduct().getPrice();
		}
	    }
	}

	return totalAmount;
    }

    public void setRoles(ArrayList<Role> roles) {
	this.roles = roles;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public Establishment getEstablishment() {
	return establishment;
    }

    public void setEstablishment(Establishment establishment) {
	this.establishment = establishment;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public List<Sale> getSales() {
	return sales;
    }

    public void setSales(List<Sale> sales) {
	this.sales = sales;
    }

    public Employee getClone() {
	Employee cloned = new Employee();
	    BeanUtils.copyProperties(this, cloned);
	    return cloned;
    }

}
