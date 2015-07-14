package br.com.techfullit.mycluby.common.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SALE")
public class Sale {

    @Id
    @Column(name = "ID_SALE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** The birth date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_SALE", columnDefinition = "DATETIME")
    private Date date;

    @Column(name = "PROMOTIONAL_SALE")
    private boolean promotional;

    @Column(name = "REVERSAL_INDICATIVE")
    private boolean reversalIndicative;
    
    @Column(name = "REVERSAL_DESCRIPTION")
    private String reversalDescription;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REVERSAL_DATE", columnDefinition = "DATETIME")
    private Date reversalDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_CONSUME")
    private Consume consume;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_EMPLOYEE")
    private Employee employee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_PRODUCT")
    private Product product;
    
    @Transient
    private String reversalStatusLabel;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public Employee getEmployee() {
	return employee;
    }

    public void setEmployee(Employee employee) {
	this.employee = employee;
    }

    public Product getProduct() {
	return product;
    }

    public void setProduct(Product product) {
	this.product = product;
    }

    public Consume getConsume() {
	return consume;
    }

    public void setConsume(Consume consume) {
	this.consume = consume;
    }

    public boolean isPromotional() {
	return promotional;
    }

    public void setPromotional(boolean promotional) {
	this.promotional = promotional;
    }

    public boolean isReversalIndicative() {
        return reversalIndicative;
    }

    public void setReversalIndicative(boolean reversalIndicative) {
        this.reversalIndicative = reversalIndicative;
    }

    public String getReversalDescription() {
        return reversalDescription;
    }

    public void setReversalDescription(String reversalDescription) {
        this.reversalDescription = reversalDescription;
    }

    public Date getReversalDate() {
        return reversalDate;
    }

    public void setReversalDate(Date reversalDate) {
        this.reversalDate = reversalDate;
    }

    public String getReversalStatusLabel() {
	if(reversalIndicative){
	    reversalStatusLabel = "SIM";
	}else{
	    reversalStatusLabel = "NÃO";
	}
	
        return reversalStatusLabel;
    }

    public void setReversalStatusLabel(String reversalStatusLabel) {
        this.reversalStatusLabel = reversalStatusLabel;
    }
    
    
}
