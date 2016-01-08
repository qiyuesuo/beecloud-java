package cn.beecloud.bean;

import java.util.Map;


/**
 * BC单笔代付 Created by bianjianjun on 16/1/8.
 */
public class BCTransferParameter {
    private Integer totalFee;

    private String billNo;

    private String title;

    private String tradeSource;

    private String bankCode;

    private String bankAssociatedCode;

    private String bankFullName;

    private String cardType;

    private String accountType;

    private String accountNo;

    private String accountName;

    private String mobile;

    private Map<String, Object> optional;

    public BCTransferParameter(Integer totalFee, String billNo, String title, String tradeSource,
            String bankCode, String bankAssociatedCode, String bankFullName, String cardType,
            String accountType, String accountNo, String accountName, Map<String, Object> optional) {
        this.totalFee = totalFee;
        this.billNo = billNo;
        this.title = title;
        this.tradeSource = tradeSource;
        this.bankCode = bankCode;
        this.bankAssociatedCode = bankAssociatedCode;
        this.bankFullName = bankFullName;
        this.cardType = cardType;
        this.accountType = accountType;
        this.accountNo = accountNo;
        this.accountName = accountName;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTradeSource() {
        return tradeSource;
    }

    public void setTradeSource(String tradeSource) {
        this.tradeSource = tradeSource;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAssociatedCode() {
        return bankAssociatedCode;
    }

    public void setBankAssociatedCode(String bankAssociatedCode) {
        this.bankAssociatedCode = bankAssociatedCode;
    }

    public String getBankFullName() {
        return bankFullName;
    }

    public void setBankFullName(String bankFullName) {
        this.bankFullName = bankFullName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Map<String, Object> getOptional() {
        return optional;
    }

    public void setOptional(Map<String, Object> optional) {
        this.optional = optional;
    }
}
