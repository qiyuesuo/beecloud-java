package cn.beecloud.bean;

import java.util.Date;

/**
 * User: jackybian
 * Date: 2017/1/7
 */
public class BCGateWayBanks {
    //区分B2C和B2B
    private String payType;
    //区分借记卡和信用卡
    private String cardType;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
