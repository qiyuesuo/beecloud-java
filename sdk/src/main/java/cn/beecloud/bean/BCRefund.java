package cn.beecloud.bean;

import java.util.Map;

import cn.beecloud.BCEumeration.PAY_CHANNEL;

/**
 * A class which is used to encapsulate the refund returned by refund query.
 * @author Ray
 */
public class BCRefund {
	
	private String objectId;
	
	private String billNo;
	
	private String refundNo;
	
	private Integer totalFee;
	
	private Integer refundFee;
	
	private PAY_CHANNEL channel;
	
	private Map<String, Object> optional;
	
	private String optionalString;
	
	private String title;
	
	private Boolean needApproval;
	
	private boolean finished;
	
	private boolean refunded;
	
	private String dateTime;
	
	private String updateDateTime;
	
	private String aliRefundUrl;
	
	private String messageDetail = "不显示";
	
	public BCRefund(String billNo, String refundNo, Integer refundFee) {
		
		this.billNo = billNo;
		this.refundNo = refundNo;
		this.refundFee = refundFee;
	}

	public BCRefund() {
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isRefunded() {
		return refunded;
	}

	public void setRefunded(boolean refunded) {
		this.refunded = refunded;
	}

	public String getUpdateDateTime() {
		return updateDateTime;
	}

	public PAY_CHANNEL getChannel() {
		return channel;
	}
	
	/**
	 * @param channel 渠道类型， 根据不同场景选择不同的支付方式，包含：
	 * {@link PAY_CHANNEL#WX}: 微信
	 * {@link PAY_CHANNEL#ALI}: 支付宝
	 * {@link PAY_CHANNEL#UN}: 银联
	 * {@link PAY_CHANNEL#YEE}: 易宝
	 * {@link PAY_CHANNEL#JD}: 京东
	 * {@link PAY_CHANNEL#KUAIQIAN}: 快钱
	 * {@link PAY_CHANNEL#BD}: 百度
	 * (选填)
	 */
	public void setChannel(PAY_CHANNEL channel) {
		this.channel = channel;
	}

	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessageDetail() {
		return messageDetail;
	}

	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Map<String, Object> getOptional() {
		return optional;
	}

	public void setOptional(Map<String, Object> optional) {
		this.optional = optional;
	}

	public String getAliRefundUrl() {
		return aliRefundUrl;
	}

	public void setAliRefundUrl(String aliRefundUrl) {
		this.aliRefundUrl = aliRefundUrl;
	}

	public Boolean isNeedApproval() {
		return needApproval;
	}

	public void setNeedApproval(Boolean needApproval) {
		this.needApproval = needApproval;
	}

	public String getOptionalString() {
		return optionalString;
	}

	public void setOptionalString(String optionalString) {
		this.optionalString = optionalString;
	}
}
