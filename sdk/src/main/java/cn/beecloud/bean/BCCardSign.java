package cn.beecloud.bean;

/**
 * Created by sunqifs on 6/29/17.
 */
public class BCCardSign {

    private String idName;

    private String idNo;

    private String cardNo;

    private String mobile;

    private String bank;

    private String cardId;

    private String notifyUrl;

    public BCCardSign() {}

    /**
     * 构造函数，参数为发起支付的4个必填参数
     *
     * @param idName
     * {@link #setIdName}
     * @param idNo
     * {@link #setIdNo}
     * @param cardNo
     * {@link #setCardNo}
     * @param mobile
     * {@link #setMobile}
     * @param bank
     * {@link #setBank}
     */
    public BCCardSign(String idName, String idNo, String cardNo, String mobile, String bank) {
        this.idName = idName;
        this.idNo = idNo;
        this.cardNo = cardNo;
        this.mobile = mobile;
        this.bank = bank;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
