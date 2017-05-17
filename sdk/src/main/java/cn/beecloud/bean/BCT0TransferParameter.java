package cn.beecloud.bean;

import java.util.Map;

/**
 * BCT0单笔代付
 */
public class BCT0TransferParameter {
    // 下发订单总金额 必须是正整数，单位为分
    private Integer totalFee;
    // 商户订单号 8到32位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
    private String billNo;
    // 对公对私类型
    private String transferType;
    // 银行名称
    private String bankName;
    // 银行账户号
    private String bankAccountNo;
    // 银行账户名称
    private String bankAccountName;
    // 银行编码
    private String bankCode;
    // 备注
    private String note;
    // 异步通知地址
    private String notifyUrl;
    // optional
    private Map<String, Object> optional;


    public BCT0TransferParameter() {
    }

    public BCT0TransferParameter(Integer totalFee, String billNo,  String transferType, String bankName, String bankAccountNo,
                                 String bankAccountName, String bankCode, String note, String notifyUrl,
                                 Map<String, Object> optional) {
        this.totalFee = totalFee;
        this.billNo = billNo;
        this.transferType = transferType;
        this.bankName = bankName;
        this.bankAccountNo = bankAccountNo;
        this.bankAccountName = bankAccountName;
        this.bankCode = bankCode;
        this.note = note;
        this.notifyUrl = notifyUrl;
        this.optional = optional;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Map<String, Object> getOptional() {
        return optional;
    }

    public void setOptional(Map<String, Object> optional) {
        this.optional = optional;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transfer_type) {
        this.transferType = transfer_type;
    }
}
