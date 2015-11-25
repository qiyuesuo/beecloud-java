package cn.beecloud.bean;

import cn.beecloud.BCEumeration.PAYPAL_CURRENCY;
import cn.beecloud.BCEumeration.PAY_CHANNEL;


public class BCInternationlOrder {
	
	private String objectId;
	
	private PAY_CHANNEL channel;
	
	private Integer totalFee;
	
	private PAYPAL_CURRENCY currency;
	
	private String billNo;
	
	private String title;
	
	private CreditCardInfo creditCardInfo;
	
	private String creditCardId;
	
	private String returnUrl;
	
	private String url;
	
	public PAY_CHANNEL getChannel() {
		return channel;
	}

	public void setChannel(PAY_CHANNEL channel) {
		this.channel = channel;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public PAYPAL_CURRENCY getCurrency() {
		return currency;
	}

	public void setCurrency(PAYPAL_CURRENCY currency) {
		this.currency = currency;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CreditCardInfo getCreditCardInfo() {
		return creditCardInfo;
	}

	public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
		this.creditCardInfo = creditCardInfo;
	}

	public String getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(String creditCardId) {
		this.creditCardId = creditCardId;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
