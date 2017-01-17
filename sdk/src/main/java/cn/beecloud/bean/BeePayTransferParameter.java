package cn.beecloud.bean;

import cn.beecloud.BCEumeration.TRANSFER_CHANNEL;

import java.util.Map;


/**
 * BeePayTransfer打款订单参数类
 * 
 * @author Rui.Feng
 * @since 2015.11.25
 */
public class BeePayTransferParameter {

    private Integer withdrawAmount;

    private String billNo;

    private String transferType;

    private String bankName;

    private String bankAccountNo;

    private String bankAccountName;

    private String bankCode;

    private String note;

    private Map<String,Object> optional;

    private String notifyUrl;




    /**
     * 访问字段 {@link #withdrawAmount}
     */
    public Integer getWithdrawAmount() {
        return withdrawAmount;
    }

    /**
     * @param withdrawAmount
     * （必填）打款订单总金额,必须是正整数,单位为分
     */
    public void setWithdrawAmount(Integer withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    /**
     * 访问字段 {@link #billNo}
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * @param billNo
     * （必填）商户订单号，8到32位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 访问字段 {@link #transferType}
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     * @param transferType
     * （必填）对私对公标识，"1"代表对私打款，"2"代表对公打款
     */
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    /**
     * 访问字段 {@link #bankName}
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName
     * （必填）银行全名
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 访问字段 {@link #bankAccountNo}
     */
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    /**
     * @param bankAccountNo
     * （必填）收款方的银行卡号
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    /**
     * 访问字段 {@link #bankAccountName}
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     * @param bankAccountName
     * （必填）收款方的姓名或者单位名
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     * 访问字段 {@link #bankCode}
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * @param bankCode
     * （必填）银行的标准编码
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * 访问字段 {@link #note}
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note
     * （必填）用户自定义的参数，将会在Webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 访问字段 {@link #optional}
     */
    public Map<String,Object> getOptional() {
        return optional;
    }

    /**
     * @param optional
     * （选填）用户自定义的参数，将会在Webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    public void setOptional(Map<String,Object> optional) {
        this.optional = optional;
    }

    /**
     * 访问字段 {@link #notifyUrl}
     */
    public String getNotifyUrl() {
        return notifyUrl;
    }

    /**
     * @param notifyUrl
     * （选填）商户自定义回调地址 ｜ 商户可通过此参数设定回调地址，此地址会覆盖用户在控制台设置的回调地址。必须以http://或https://开头
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
