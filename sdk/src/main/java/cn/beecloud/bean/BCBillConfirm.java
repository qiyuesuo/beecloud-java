package cn.beecloud.bean;



/**
 * 支付认证确认支付
 * 
 * @author wzq
 * @since 2017.4.24
 */
public class BCBillConfirm {

    private String token;

    private String billId;

    private String verifyCode;



    public BCBillConfirm() {}

    /**
     * 构造函数，参数为发起确认支付的3个参数
     *
     * @param token
     * {@link #setToken}
     * @param billId
     * {@link #setBillId}
     * @param verifyCode
     * {@link #setVerifyCode}
     */
    public BCBillConfirm(String token, String billId, String verifyCode) {
        this.token = token;
        this.billId = billId;
        this.verifyCode = verifyCode;
    }





    /**
     * 访问字段 {@link #token}
     */
    public String getToken() {
        return token;
    }
    /**
     * @param token
     * 渠道返回的token (选填)
     */
    public void setToken(String token) {
        this.token = token;
    }
    /**
     * 访问字段 {@link #billId}
     */
    public String getBillId() {
        return billId;
    }
    /**
     * @param billId
     * BeeCloud生成的唯一支付记录id
     */
    public void setBillId(String billId) {
        this.billId = billId;
    }
    /**
     * 访问字段 {@link #verifyCode}
     */
    public String getVerifyCode() {
        return verifyCode;
    }
    /**
     * @param verifyCode
     * 短信验证码
     */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
