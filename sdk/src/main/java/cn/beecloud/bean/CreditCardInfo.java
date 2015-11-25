package cn.beecloud.bean;

import cn.beecloud.BCEumeration.CARD_TYPE;

public class CreditCardInfo {
	
	private String cardNo;
	
	private Integer expireMonth;
	
	private Integer expireYear;
	
	private Integer cvv;
	
	private String firstName;
	
	private String lastName;
	
	private CARD_TYPE cardType;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getExpireMonth() {
		return expireMonth;
	}

	public void setExpireMonth(Integer expireMonth) {
		this.expireMonth = expireMonth;
	}

	public Integer getExpireYear() {
		return expireYear;
	}

	public void setExpireYear(Integer expireYear) {
		this.expireYear = expireYear;
	}

	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public CARD_TYPE getCardType() {
		return cardType;
	}

	public void setCardType(CARD_TYPE cardType) {
		this.cardType = cardType;
	}
}
