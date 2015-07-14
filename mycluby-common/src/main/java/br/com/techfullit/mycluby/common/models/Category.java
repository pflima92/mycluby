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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CATEGORIES")
public class Category {

    @Id
    @Column(name = "ID_CATEGORIES")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;
    
    @OneToMany(mappedBy = "categorie", targetEntity = Product.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Product> products;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_ESTABLISHMENT")
    private Establishment establishment;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Establishment getEstablishment() {
	return establishment;
    }

    public void setEstablishment(Establishment establishment) {
	this.establishment = establishment;
    }

    public List<Product> getProducts() {
    	if(products == null)
    		products = new ArrayList<Product>();
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    
}
