package br.com.techfullit.mycluby.common.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.techfullit.mycluby.common.utils.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PICTURE")
public class Picture {

    public Picture() {
    }

    @Id
    @Column(name = "ID_PICTURE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPicture;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "VALUE")
    @Lob
    private byte[] value;

    @Column(name = "DESCRIPTION")
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;

    @Transient
    private String imageView;

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public byte[] getValue() {
	return value;
    }

    public void setValue(byte[] value) {
	this.value = value;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public String getImageView() {
	Base64 b64 = new Base64();
	if (value != null)
	    imageView = b64.encodeBytes(value);
	return imageView;
    }

    public void setImageView(String imageView) {
	this.imageView = imageView;
    }

    public Integer getIdPicture() {
	return idPicture;
    }

    public void setIdPicture(Integer idPicture) {
	this.idPicture = idPicture;
    }

}
