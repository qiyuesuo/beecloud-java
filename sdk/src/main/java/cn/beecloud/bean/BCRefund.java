package cn.beecloud.bean;

import java.util.Map;

import cn.beecloud.BCEumeration.PAY_CHANNEL;


/**
 * 退款信息类，封装了BeeCloud退款记录信息
 * 
 * @author Rui.Feng
 * @since 2015.9.24
 */
public class BCRefund {

    private String objectId;

    private String billNo;

    private String refundNo;

    private Integer totalFee;

    private Integer refundFee;

    private PAY_CHANNEL channel;

    private Map<String, Object> optional;

    private String optionalString;

    private String title;

    private Boolean needApproval;

    private boolean finished;

    private boolean refunded;

    private String dateTime;

    private String aliRefundUrl;

    private String messageDetail = "不显示";

    /**
     * 构造函数，参数为发起退款的3个必填参数
     * 
     * @param billNo
     * {@link #setBillNo}
     * @param refundNo
     * {@link #setRefundNo}
     * @param refundFee
     * {@link #setRefundFee}
     */
    public BCRefund(String billNo, String refundNo, Integer refundFee) {

        this.billNo = billNo;
        this.refundNo = refundNo;
        this.refundFee = refundFee;
    }

    public BCRefund() {}

    /**
     * 访问字段 {@link #billNo}
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * @param billNo
     * 商户订单号， 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一 (必填)
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 访问字段 {@link #refundNo}
     */
    public String getRefundNo() {
        return refundNo;
    }

    /**
     * @param refundNo
     * 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24
     * 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。
     * 例如：201506101035040000001 (必填)
     */
    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    /**
     * @return 商品价格
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     * 设置字段{@link #totalFee}
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 访问字段 {@link #refundFee}
     */
    public Integer getRefundFee() {
        return refundFee;
    }

    /**
     * @param refundFee
     * 退款金额， 只能为整数，单位为分，例如1 (必填)
     */
    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    /**
     * @return 退款创建时间
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * 设置字段{@link #dateTime}
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return 退款是否结束
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * 设置字段{@link #finished}
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * @return 退款是否成功
     */
    public boolean isRefunded() {
        return refunded;
    }

    /**
     * 设置字段{@link #refunded}
     */
    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    /**
     * 访问字段{@link #channel}
     */
    public PAY_CHANNEL getChannel() {
        return channel;
    }

    /**
     * @param channel
     * 渠道类型， 根据不同场景选择不同的支付方式，包含： {@link PAY_CHANNEL#WX}: 微信
     * {@link PAY_CHANNEL#ALI}: 支付宝 {@link PAY_CHANNEL#UN}: 银联
     * {@link PAY_CHANNEL#YEE}: 易宝 {@link PAY_CHANNEL#JD}: 京东
     * {@link PAY_CHANNEL#KUAIQIAN}: 快钱 {@link PAY_CHANNEL#BD}: 百度 (选填)
     */
    public void setChannel(PAY_CHANNEL channel) {
        this.channel = channel;
    }

    /**
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置字段{@link #title}
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 渠道返回信息
     */
    public String getMessageDetail() {
        return messageDetail;
    }

    /**
     * 设置字段{@link #messageDetail}
     */
    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    /**
     * @return 退款记录唯一标识
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * 设置字段{@link #objectId}
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * 访问字段{@link #optional}
     */
    public Map<String, Object> getOptional() {
        return optional;
    }

    /**
     * @param optional
     * 附加数据 用户自定义的参数，将会在webhook通知中原样返回，该字段主要用于商户携带订单的自定义数据，例如{"key1":"value1",
     * "key2":"value2",...} (选填)
     */
    public void setOptional(Map<String, Object> optional) {
        this.optional = optional;
    }

    /**
     * @return 阿里退款跳转url
     */
    public String getAliRefundUrl() {
        return aliRefundUrl;
    }

    /**
     * 设置字段{@link #aliRefundUrl}
     */
    public void setAliRefundUrl(String aliRefundUrl) {
        this.aliRefundUrl = aliRefundUrl;
    }

    /**
     * 访问字段{@link #needApproval}
     */
    public Boolean isNeedApproval() {
        return needApproval;
    }

    /**
     * @param needApproval
     * 标识该笔是预退款还是直接退款 (选填)
     */
    public void setNeedApproval(Boolean needApproval) {
        this.needApproval = needApproval;
    }

    /**
     * @return 附加数据json字符串
     */
    public String getOptionalString() {
        return optionalString;
    }

    /**
     * 设置字段{@link #optionalString}
     */
    public void setOptionalString(String optionalString) {
        this.optionalString = optionalString;
    }
}
