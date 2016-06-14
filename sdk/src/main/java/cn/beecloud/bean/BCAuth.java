package cn.beecloud.bean;

import cn.beecloud.BCEumeration;

/**
 * Created by rui on 2016/6/13.
 */
public class BCAuth {

    private String name;

    private String idNo;

    private String cardNo;

    private String mobile;

    private boolean authResult;

    private String authMsg;

    private String cardId;

    public BCAuth() {}

    /**
     * 构造函数，参数为发起支付的4个必填参数
     *
     * @param name
     * {@link #setName}
     * @param idNo
     * {@link #setIdNo}
     * @param cardNo
     * {@link #setCardNo}
     */
    public BCAuth(String name, String idNo, String cardNo) {
        this.name = name;
        this.idNo = idNo;
        this.cardNo = cardNo;
    }

    /**
     * 访问字段 {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * 身份证姓名 (必填)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 访问字段 {@link #idNo}
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * @param idNo
     * 身份证号 (必填)
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * 访问字段 {@link #cardNo}
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo
     * 用户银行卡卡号 (必填)
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 访问字段 {@link #mobile}
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     * 手机号 (选填)
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return 鉴权是否成功
     */
    public boolean isAuthResult() {
        return authResult;
    }

    /**
     * @return 鉴权回执消息， 鉴权完成之后获得
     */
    public String getAuthMsg() {
        return authMsg;
    }

    /**
     * 设置字段 {@link #authResult}
     */
    public void setAuthResult(boolean authResult) {
        this.authResult = authResult;
    }

    /**
     * 设置字段 {@link #authMsg}
     */
    public void setAuthMsg(String authMsg) {
        this.authMsg = authMsg;
    }

    /**
     * @return 卡唯一标识符， 鉴权完成之后获得
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置字段 {@link #cardId}
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
