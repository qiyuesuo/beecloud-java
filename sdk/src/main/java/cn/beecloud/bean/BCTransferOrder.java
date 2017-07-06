package cn.beecloud.bean;


import cn.beecloud.BCEumeration;
import cn.beecloud.BCEumeration.PAY_CHANNEL;
import cn.beecloud.BCEumeration.QR_PAY_MODE;

import java.util.Map;


/**
 * 打款订单类，封装了BeeCloud打款订单信息
 * 
 * @author sy
 * @since 2017.7.5
 */
public class BCTransferOrder {

    private String id;

    private Integer totalFee;

    private String tradeNo;

    private String billNo;

    private String title;

    private Boolean spayResult;

    private String createTime;

    private Map<String, Object> optional;

    private String optionalString;

    private String messageDetail;

    private BCEumeration.TRANSFER_STATUS transferStatus;

    private String accountName;

    private String accountNo;

    private String bankFullName;

    private String notifyUrl;




    public BCTransferOrder() {}

    /**
     * 访问字段 {@link #totalFee}
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     * 访问字段 {@link #billNo}
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * 访问字段 {@link #title}
     */
    public String getTitle() {
        return title;
    }

    /**
     * 访问字段 {@link #optional}
     */
    public Map<String, Object> getOptional() {
        return optional;
    }

    /**
     * @param optional
     * 附加数据， 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据 (选填)
     */
    public void setOptional(Map<String, Object> optional) {
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
     * 异步回调地址 (选填)
     */
    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    /**
     * @param totalFee
     * 订单总金额， 只能为整数，单位为分，例如 1 (必填)
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * @param billNo
     * 商户订单号, 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一, 例如（201506101035040000001） (必填)
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * @param title
     * 订单标题， 32个字节内，最长支持16个汉字 (必填)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 渠道信息
     */
    public String getMessageDetail() {
        return messageDetail;
    }

    /**
     * 设置字段 {@link #messageDetail}
     */
    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    /**
     * @return optional json字符串
     */
    public String getOptionalString() {
        return optionalString;
    }

    /**
     * 设置字段 {@link #optionalString}
     */
    public void setOptionalString(String optionalString) {
        this.optionalString = optionalString;
    }

    /**
     * 访问字段 {@link #tradeNo}
     */
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * 访问字段 {@link #id}
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 访问字段 {@link #spayResult}
     */
    public Boolean getSpayResult() {
        return spayResult;
    }

    public void setSpayResult(Boolean spayResult) {
        this.spayResult = spayResult;
    }

    /**
     * 访问字段 {@link #createTime}
     */
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * 访问字段 {@link #transferStatus}
     */
    public BCEumeration.TRANSFER_STATUS getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(BCEumeration.TRANSFER_STATUS transferStatus) {
        this.transferStatus = transferStatus;
    }

    /**
     * 访问字段 {@link #accountName}
     */
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 访问字段 {@link #accountNo}
     */
    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 访问字段 {@link #bankFullName}
     */
    public String getBankFullName() {
        return bankFullName;
    }

    public void setBankFullName(String bankFullName) {
        this.bankFullName = bankFullName;
    }
}
