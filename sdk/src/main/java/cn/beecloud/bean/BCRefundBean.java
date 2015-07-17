package cn.beecloud.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * A class which is used to encapsulate the refund returned by refund query.
 * @author Ray
 */
public class BCRefundBean {
	
	private String bill_no;
	
	private String refund_no;
	
	private String total_fee;
	
	private String refund_fee;
	
	private String channel;
	
	private boolean finished;
	
	private boolean refunded;
	
	private String dateTime;

	public String getBill_no() {
		return bill_no;
	}

	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}

	public String getRefund_no() {
		return refund_no;
	}

	public void setRefund_no(String refund_no) {
		this.refund_no = refund_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getRefund_fee() {
		return refund_fee;
	}

	public void setRefund_fee(String refund_fee) {
		this.refund_fee = refund_fee;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
