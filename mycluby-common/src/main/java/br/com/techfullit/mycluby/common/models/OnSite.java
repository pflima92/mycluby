package br.com.techfullit.mycluby.common.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.BeanUtils;

import br.com.techfullit.mycluby.common.utils.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({ @NamedQuery(name = "OnSite.findOnSiteSessionsByUser", query = "from OnSite o WHERE o.user.id = :user_id") ,
    @NamedQuery(name = "OnSite.findOnSiteSessionsByEstablishment", query = "from OnSite o WHERE o.establishment.id = :establishment_id")})
@Entity
@Table(name = "ONSITE") 
public class OnSite implements Cloneable {

    @Transient
    public static final String UNOPENED = "UNOPENED";
    @Transient
    public static final String OPEN = "OPEN";
    @Transient
    public static final String CLOSED = "CLOSED";

    public OnSite() {
	this.status = OnSite.UNOPENED;
    }

    @Id
    @Column(name = "ID_ONSITE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /** The birth date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CHECKIN",columnDefinition="DATETIME")
    private Date checkIn;

    /** The birth date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_CHECKOUT",columnDefinition="DATETIME")
    private Date checkOut;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_ESTABLISHMENT")
    private Establishment establishment;

    @Column(name = "STATUS")
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CONSUME")
    private Consume consume;
    
    @Column(name = "COMMENTARY")
    private String commentary;
    
    @Column(name = "RATING")
    private Integer rating;
    
    @Transient
    private String labelStatus;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Establishment getEstablishment() {
	return establishment;
    }

    public void setEstablishment(Establishment establishment) {
	this.establishment = establishment;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public Date getCheckIn() {
	if(status.equals(UNOPENED)){
	    checkIn = new Date();
	}
	if(status == null){
	    checkIn = new Date();
	}
	return checkIn;
    }

    public void setCheckIn(Date checkIn) {
	this.checkIn = checkIn;
    }

    public Date getCheckOut() {
	return checkOut;
    }

    public void setCheckOut(Date checkOut) {
	this.checkOut = checkOut;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public Consume getConsume() {
	return consume;
    }

    public void setConsume(Consume consume) {
	this.consume = consume;
    }

    public String getLabelStatus() {
	if(status.equals(OPEN)){
	    labelStatus = "ABERTA";
	}else if(status.equals(CLOSED)){
	    labelStatus = "FECHADA";
	}else{
	    labelStatus = "---";
	}
        return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
        this.labelStatus = labelStatus;
    }

    public String getCommentary() {
	if(StringUtils.isBlank(commentary)){
	    return "Comentário não cadastrado";
	}
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Integer getRating() {
	if(rating == null)return 0;
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public OnSite getClone() {
	OnSite cloned = new OnSite();
   	    BeanUtils.copyProperties(this, cloned);
   	    return cloned;
       }
    
}
