package cn.beecloud.bean;

/**
 * 支付宝批量打款收款信息封装类
 * 
 * @author Rui.Feng
 * @since 2015.11.24
 */
public class ALITransferData {

    private String transferId;

    private String receiverAccount;

    private String receiverName;

    private Integer transferFee;

    private String transferNote;

    /**
     * 访问字段 {@link #transferId}
     */
    public String getTransferId() {
        return transferId;
    }

    /**
     * 带参构造函数
     */
    public ALITransferData(String transferId, String receiverAccount,
            String receiverName, Integer transferFee, String transferNote) {
        this.transferId = transferId;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
        this.transferFee = transferFee;
        this.transferNote = transferNote;
    }

    /**
     * @param transferId
     * （必填）付款流水号，32位以内数字字母
     */
    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    /**
     * 访问字段 {@link #receiverAccount}
     */
    public String getReceiverAccount() {
        return receiverAccount;
    }

    /**
     * @param receiverAccount
     * （必填）收款方账户
     */
    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    /**
     * 访问字段 {@link #receiverName}
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * @param receiverName
     * （必填）收款方账号姓名
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * 访问字段 {@link #transferFee}
     */
    public Integer getTransferFee() {
        return transferFee;
    }

    /**
     * @param transferFee
     * （必填）打款金额，单位为分
     */
    public void setTransferFee(Integer transferFee) {
        this.transferFee = transferFee;
    }

    /**
     * 访问字段 {@link #transferNote}
     */
    public String getTransferNote() {
        return transferNote;
    }

    /**
     * @param transferNote
     * （必填）打款备注
     */
    public void setTransferNote(String transferNote) {
        this.transferNote = transferNote;
    }
}
