package cn.beecloud.bean;

import cn.beecloud.BCEumeration.CARD_TYPE;


/**
 * PAYPAL支付信用卡信息类
 * 
 * @author Rui.Feng
 * @since 2015.11.24
 */
public class CreditCardInfo {

    private String cardNo;

    private Integer expireMonth;

    private Integer expireYear;

    private Integer cvv;

    private String firstName;

    private String lastName;

    private CARD_TYPE cardType;

    /**
     * 访问字段 {@link #cardNo}
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo
     * （必填）卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 访问字段 {@link #expireMonth}
     */
    public Integer getExpireMonth() {
        return expireMonth;
    }

    /**
     * @param expireMonth
     * （必填）过期时间中的月
     */
    public void setExpireMonth(Integer expireMonth) {
        this.expireMonth = expireMonth;
    }

    /**
     * 访问字段 {@link #expireYear}
     */
    public Integer getExpireYear() {
        return expireYear;
    }

    /**
     * @param expireYear
     * （必填）过期时间中的年
     */
    public void setExpireYear(Integer expireYear) {
        this.expireYear = expireYear;
    }

    /**
     * 访问字段 {@link #cvv}
     */
    public Integer getCvv() {
        return cvv;
    }

    /**
     * @param cvv
     * （必填）信用卡的三位cvv码
     */
    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    /**
     * 访问字段 {@link #firstName}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     * （必填）用户名字
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 访问字段 {@link #lastName}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     * （必填）用户的姓
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 访问字段 {@link #cardType}
     */
    public CARD_TYPE getCardType() {
        return cardType;
    }

    /**
     * @param cardType
     * （必填）卡类别 visa/mastercard/discover/amex
     */
    public void setCardType(CARD_TYPE cardType) {
        this.cardType = cardType;
    }
}
