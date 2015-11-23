package cn.beecloud.bean;

import cn.beecloud.BCEumeration.TRANSFER_CHANNEL;

public class TransferParameter {
	
	private TRANSFER_CHANNEL channel;
	
	private String transferNo;
	
	private Integer totalFee;
	
	private String description;
	
	private String channelUserId;
	
	private String channelUserName;
	
	private RedpackInfo redpackInfo;
	
	private String accountName;

	public TRANSFER_CHANNEL getChannel() {
		return channel;
	}

	public void setChannel(TRANSFER_CHANNEL channel) {
		this.channel = channel;
	}

	public String getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelUserId() {
		return channelUserId;
	}

	public void setChannelUserId(String channelUserId) {
		this.channelUserId = channelUserId;
	}

	public String getChannelUserName() {
		return channelUserName;
	}

	public void setChannelUserName(String channelUserName) {
		this.channelUserName = channelUserName;
	}

	public RedpackInfo getRedpackInfo() {
		return redpackInfo;
	}

	public void setRedpackInfo(RedpackInfo redpackInfo) {
		this.redpackInfo = redpackInfo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
