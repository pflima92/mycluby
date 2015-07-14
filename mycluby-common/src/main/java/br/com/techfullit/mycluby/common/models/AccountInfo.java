/*
 * COPYRIGHT. TECHFULL - IT SERVICES 2013. ALL RIGHTS RESERVED.
 *
 * Este software � de propriedade e de uso exclusivo da corpora��o
 * TechFull. N�o sendo permitida reprodu��o, distribui��o, 
 * armazenados num sistema de recupera��o nem traduzida por qualquer
 * humano ou computador, idioma em qualquer forma ou para quaisquer 
 * outros fins sem a pr�via autoriza��o escrita da TechFull IT Services.
 *
 */
package br.com.techfullit.mycluby.common.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCOUNT_INFO")
public class AccountInfo {

	@Id
	@Column(name = "ID_ACCOUNT_INFO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION" ,columnDefinition="DATETIME")
	private Date accountCreation;

	@Column(name = "ACTIVE")
	private boolean accountActive;

	@Column(name = "TIME_ZONE")
	private String timezone;

	@Column(name = "ACTIVE_EMAIL_RECEIVER")
	private boolean activeEmailReceiver;

	@Column(name = "LANGUAGE")
	private String language;

	@Column(name = "USER_KEY")
	private String userkey;

	@Column(name = "FIRST_STEP")
	private boolean firstStep;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "accountInfo")
	private User user;
	
	@Column(name = "MOBILE_PAYMENT")
	private boolean mobilePayment;
	
	@Column(name = "MOBILE_NOTIFICATION")
	private boolean mobileNotification;
	
	@Column(name = "LIMIT_CONSUME_INDICATIVE")
	private boolean limitConsume;
	
	@Column(name = "LIMIT_CONSUME_VALUE")
	private Double limitConsumeValue;
	
	public Date getAccountCreation() {
		return accountCreation;
	}

	public void setAccountCreation(Date accountCreation) {
		this.accountCreation = accountCreation;
	}

	public boolean isActiveEmailReceiver() {
		return activeEmailReceiver;
	}

	public void setActiveEmailReceiver(boolean activeEmailReceiver) {
		this.activeEmailReceiver = activeEmailReceiver;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public boolean isAccountActive() {
		return accountActive;
	}

	public void setAccountActive(boolean accountActive) {
		this.accountActive = accountActive;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

	public boolean isFirstStep() {
		return firstStep;
	}

	public void setFirstStep(boolean firstStep) {
		this.firstStep = firstStep;
	}

	public boolean isMobilePayment() {
		return mobilePayment;
	}

	public void setMobilePayment(boolean mobilePayment) {
		this.mobilePayment = mobilePayment;
	}

	public boolean isMobileNotification() {
		return mobileNotification;
	}

	public void setMobileNotification(boolean mobileNotification) {
		this.mobileNotification = mobileNotification;
	}

	public boolean isLimitConsume() {
		return limitConsume;
	}

	public void setLimitConsume(boolean limitConsume) {
		this.limitConsume = limitConsume;
	}

	public Double getLimitConsumeValue() {
		return limitConsumeValue;
	}

	public void setLimitConsumeValue(Double limitConsumeValue) {
		this.limitConsumeValue = limitConsumeValue;
	}
}