package cn.beecloud.bean;

import java.util.LinkedList;
import java.util.List;

import cn.beecloud.BCEumeration.TRANSFER_CHANNEL;

public class TransfersParameter {
	
	private TRANSFER_CHANNEL channel;
	
	private String batchNo;
	
	private String accountName;
	
	private List<ALITransferData> transferDataList = new LinkedList<ALITransferData>();

	public TRANSFER_CHANNEL getChannel() {
		return channel;
	}

	public void setChannel(TRANSFER_CHANNEL channel) {
		this.channel = channel;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public List<ALITransferData> getTransferDataList() {
		return transferDataList;
	}

	public void setTransferDataList(List<ALITransferData> transferDataList) {
		this.transferDataList = transferDataList;
	}
}
