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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CONSUME")
public class Consume {

    public Consume() {
	this.status = INACTIVE;
    }

    @Transient
    public static final String INACTIVE = "INACTIVE";
    @Transient
    public static final String PENDING_PAYMENT = "PENDING_PAYMENT";
    @Transient
    public static final String ACTIVE = "ACTIVE";
    @Transient
    public static final String CLOSED = "CLOSED";

    @Id
    @Column(name = "ID_CONSUME")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "consume")
    private OnSite onSite;

    @Column(name = "STATUS")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "consume", targetEntity = Sale.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private List<Sale> sales;

    @Transient
    private double totalAmount;

    @Transient
    private String labelStatus;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public OnSite getOnSite() {
	return onSite;
    }

    public void setOnSite(OnSite onSite) {
	this.onSite = onSite;
    }

    public List<Sale> getSales() {
	if (sales == null) {
	    sales = new ArrayList<Sale>();
	}
	return sales;
    }
    
    @Transient
    public List<Sale> getSalesActive() {
	List<Sale> actives = new ArrayList<Sale>();
	for (Sale sale : getSales()) {
	    if(!sale.isReversalIndicative()){
		actives.add(sale);
	    }
	}
	return actives;
    }

    public void setSales(List<Sale> sales) {
	this.sales = sales;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public double getTotalAmount() {
	totalAmount = 0;
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

    public void setTotalAmount(double totalAmount) {
	this.totalAmount = totalAmount;
    }

    public String getLabelStatus() {
	if (status.equals(ACTIVE)) {
	    labelStatus = "ATIVO";
	} else if (status.equals(CLOSED)) {
	    labelStatus = "FECHADA";
	} else if (status.equals(INACTIVE)) {
	    labelStatus = "INATIVO";
	} else if (status.equals(PENDING_PAYMENT)) {
	    labelStatus = "AGUARDANDO PAGAMENTO";
	} else {
	    labelStatus = "STATUS INVALIDO";
	}
	return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
	this.labelStatus = labelStatus;
    }

}
