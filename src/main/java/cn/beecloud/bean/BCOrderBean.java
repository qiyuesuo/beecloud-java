package cn.beecloud.bean;

/**
 * A class which is used to encapsulate the order returned by order query.
 * @author Ray
 */
public class BCOrderBean {

	private String bill_no;
	
	private String buyer_id;
	
	private String total_fee;
	
	private String title;
	
	private String desc;
	
	private String channel;
	
	private boolean spay_result;
    
	private long created_time;
	
	private String dateTime;

	public String getBill_no() {
		return bill_no;
	}

	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
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

	public boolean isSpay_result() {
		return spay_result;
	}

	public void setSpay_result(boolean spay_result) {
		this.spay_result = spay_result;
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
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
