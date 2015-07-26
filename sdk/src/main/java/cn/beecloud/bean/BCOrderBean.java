package cn.beecloud.bean;

/**
 * A class which is used to encapsulate the order returned by order query.
 * @author Ray
 */
public class BCOrderBean {

	private String billNo;
	
	private String buyerId;
	
	private String totalFee;
	
	private String title;
	
	private String desc;
	
	private String channel;
	
	private boolean spayResult;
    
	private long createdTime;
	
	private String dateTime;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isSpayResult() {
		return spayResult;
	}

	public void setSpayResult(boolean spayResult) {
		this.spayResult = spayResult;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
