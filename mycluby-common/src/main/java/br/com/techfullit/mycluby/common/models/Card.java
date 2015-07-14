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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "CARD")
public class Card {

    public Card() {
    }

    @Id
    @Column(name = "ID_CARD")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME_TYPED")
    private String name;

    @Column(name = "NUMBER_CARD")
    private String numberCard;
    
    @Column(name = "VALIDATOR_CARD")
    private Integer validatorCard;
    
    /** The birth date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "VALIDATE_DATE")
    private Date validateDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;

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

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public Integer getValidatorCard() {
        return validatorCard;
    }

    public void setValidatorCard(Integer validatorCard) {
        this.validatorCard = validatorCard;
    }

    public Date getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(Date validateDate) {
        this.validateDate = validateDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
