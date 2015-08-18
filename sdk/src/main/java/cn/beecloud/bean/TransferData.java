package cn.beecloud.bean;

public class TransferData {
	
	/**
	 * （必填）付款流水号，32位以内数字字母	
	 */
	private String transferId;
	
	/**
	 * （必填）收款方支付宝账号	
	 */
	private String receiverAccount;
	
	/**
	 * （必填）收款方支付宝账户名	
	 */
	private String receiverName;
	
	/**
	 * (必填)付款金额，单位为分	
	 */
	private Integer transferFee;
	
	/**
	 * （必填）付款备注
	 */
	private String transferNote;

	public String getTransferId() {
		return transferId;
	}

	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	public String getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount) {
		this.receiverAccount = receiverAccount;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Integer getTransferFee() {
		return transferFee;
	}

	public void setTransferFee(Integer transferFee) {
		this.transferFee = transferFee;
	}

	public String getTransferNote() {
		return transferNote;
	}

	public void setTransferNote(String transferNote) {
		this.transferNote = transferNote;
	}
	
	public TransferData(String transferId, 
			String receiverAccount, String receiverName, 
			Integer transferFee, String transferNote) {
		this.transferId = transferId;
		this.receiverAccount = receiverAccount;
		this.receiverName = receiverName;
		this.transferFee = transferFee;
		this.transferNote = transferNote;
	}
}
