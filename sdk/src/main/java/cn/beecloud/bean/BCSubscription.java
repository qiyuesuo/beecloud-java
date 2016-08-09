package cn.beecloud.bean;

import java.util.Date;
import java.util.Map;

/**
 * 订阅类，封装了BeeCloud订阅信息
 *
 * @author Rui.Feng
 * @since 2016.7.26
 */
public class BCSubscription extends BCObject{

    private String smsId;

    private String smsCode;

    private String buyerId;

    private String planId;

    private String cardId;

    private String bankName;

    private String cardNo;

    private String idName;

    private String idNo;

    private String mobile;

    private Double amount;

    private String couponId;

    private Date trialEnd;

    private Boolean cancelAtPeriodEnd;

    private Boolean valid;

    private String status;

    private String accountType;

    private String last4;

    private Map<String, Object> optional;

    private String optionalString;

    /**
     * 访问字段 {@link #buyerId}
     */
    public String getBuyerId() {
        return buyerId;
    }

    /**
     * @param buyerId
     * 订阅的buyer ID，可以是用户email，也可以是商户系统中的用户ID (必填)
     */
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    /**
     * 访问字段 {@link #planId}
     */
    public String getPlanId() {
        return planId;
    }

    /**
     * @param planId
     * 对应的计划id (必填)
     */
    public void setPlanId(String planId) {
        this.planId = planId;
    }

    /**
     * 访问字段 {@link #cardId}
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * @param cardId
     * 第一次订阅成功的情况下，webhook会返回，之后订阅可以直接使用cardId代替以下5个参数，
     * 即（{bank_name、card_no、id_name、id_no、mobile}和{cardId} 二选一）（选填）
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * 访问字段 {@link #bankName}
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName
     * 订阅用户银行名称（支持列表可参考API获取支持银行列表) (选填)
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 访问字段 {@link #cardNo}
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo
     * 订阅用户银行卡号，（选填）
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * 访问字段 {@link #idName}
     */
    public String getIdName() {
        return idName;
    }

    /**
     * @param idName
     * 订阅用户身份证姓名，（选填）
     */
    public void setIdName(String idName) {
        this.idName = idName;
    }

    /**
     * 访问字段 {@link #idNo}
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * @param idNo
     * 订阅用户身份证号， 选填）
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * 访问字段 {@link #mobile}
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     * 订阅用户银行预留手机号， 选填）
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 访问字段 {@link #amount}
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount
     * 对于类似收取电费的场景，计划的收费金额fee应当是电费的单价，用户每月使用的度数在订阅中的amount设置，
     * 在每次扣款时间点之前，商户的系统需要更新每个注册用户对应订阅的amount数值， 默认1（选填）
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 访问字段, 只在查询时返回 {@link #couponId}
     */
    public String getCouponId() {
        return couponId;
    }

    /**
     * 设置字段 {@link #couponId}
     */
    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    /**
     * 访问字段 {@link #optional}
     */
    public Map<String, Object> getOptional() {
        return optional;
    }

    /**
     * @param optional
     * 补充说明，（选填）
     */
    public void setOptional(Map<String, Object> optional) {
        this.optional = optional;
    }

    /**
     * 访问字段 {@link #smsId}
     */
    public String getSmsId() {
        return smsId;
    }

    /**
     * @param smsId
     * 短信验证码id， 通过短信验证接口获得（必填）
     */
    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    /**
     * @param trialEnd
     * 试用截止时间点，默认值为null，如果设置了，当前订阅直接从trialEnd的下一天进行第一次扣费，
     * 之后按照计划中设定的时间间隔，周期性扣费。该参量可以用来统一订阅用户的收费时间， （选填）
     */
    public void setTrialEnd(Date trialEnd) {
        this.trialEnd = trialEnd;
    }

    /**
     * 访问字段 {@link #trialEnd}
     */
    public Date getTrialEnd() {
        return trialEnd;
    }

    /**
     * 访问字段 {@link #smsCode}
     */
    public String getSmsCode() {
        return smsCode;
    }

    /**
     * @param smsCode
     * 短信验证码，（必填）
     */
    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    /**
     * 访问字段 {@link #cancelAtPeriodEnd}
     */
    public Boolean getCancelAtPeriodEnd() {
        return cancelAtPeriodEnd;
    }

    /**
     * @param cancelAtPeriodEnd
         * 是否在到期扣款后再取消订阅，默认为false, 即立即使该订阅失效, 如为true, 则在期数结束扣款后使该订阅失效,（选填）
     */
    public void setCancelAtPeriodEnd(Boolean cancelAtPeriodEnd) {
        this.cancelAtPeriodEnd = cancelAtPeriodEnd;
    }

    /**
     * 访问字段 {@link #valid}
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 设置字段 {@link #valid}
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    /**
     * 访问字段 {@link #status}
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置字段 {@link #status}
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 访问字段 {@link #accountType}
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * 设置字段 {@link #accountType}
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * 访问字段 {@link #last4}
     */
    public String getLast4() {
        return last4;
    }

    /**
     * 设置字段 {@link #last4}
     */
    public void setLast4(String last4) {
        this.last4 = last4;
    }

    /**
     * 访问字段 {@link #getOptionalString}
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
}
