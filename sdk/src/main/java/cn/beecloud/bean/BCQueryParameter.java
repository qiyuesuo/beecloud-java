package cn.beecloud.bean;

import java.util.Date;

import cn.beecloud.BCEumeration.PAY_CHANNEL;


/**
 * 查询参数类，封装了BeeCloud账单查询所需的参数
 * 
 * @author Rui.Feng
 * @since 2015.9.24
 */
public class BCQueryParameter {

    private PAY_CHANNEL channel;

    private String billNo;

    private Date startTime;

    private Date endTime;

    private Integer skip;

    private Integer limit;

    private Boolean needDetail;

    private Boolean payResult;

    private Boolean refundResult;

    private Boolean needApproval;

    private String refundNo;

    /**
     * 访问字段 {@link #channel}
     */
    public PAY_CHANNEL getChannel() {
        return channel;
    }

    /**
     * @param channel
     * 渠道类型， 根据不同场景选择不同的支付方式，包含： {@link PAY_CHANNEL#WX}
     * {@link PAY_CHANNEL#WX_APP}: 微信手机APP支付 {@link PAY_CHANNEL#WX_JSAPI}:
     * 微信公众号二维码支付 {@link PAY_CHANNEL#ALI} {@link PAY_CHANNEL#ALI_APP}: 支付宝APP支付
     * {@link PAY_CHANNEL#ALI_WEB}: 支付宝网页支付 {@link PAY_CHANNEL#ALI_WAP}:
     * 支付宝移动网页支付 {@link PAY_CHANNEL#ALI_QRCODE}: 支付宝内嵌二维码支付
     * {@link PAY_CHANNEL#UN} {@link PAY_CHANNEL#UN_APP}: 银联APP支付
     * {@link PAY_CHANNEL#UN_WEB}: 银联网页支付 {@link PAY_CHANNEL#JD}
     * {@link PAY_CHANNEL#JD_WAP}: 京东移动网页支付 {@link PAY_CHANNEL#JD_WEB}: 京东PC网页支付
     * {@link PAY_CHANNEL#YEE} {@link PAY_CHANNEL#YEE_WAP}: 易宝移动网页支付
     * {@link PAY_CHANNEL#YEE_WEB}: 易宝PC网页支付 {@link PAY_CHANNEL#YEE_NOBANKCARD}:
     * 易宝点卡支付 {@link PAY_CHANNEL#KUAIQIAN} {@link PAY_CHANNEL#KUAIQIAN_WAP}:
     * 快钱移动网页支付 {@link PAY_CHANNEL#KUAIQIAN_WEB}: 快钱PC网页支付
     * {@link PAY_CHANNEL#PAYPAL} {@link PAY_CHANNEL#PAYPAL_SANDBOX}: paypal
     * 沙箱环境订单 {@link PAY_CHANNEL#PAYPAL_LIVE}: paypal 生产环境订单
     * {@link PAY_CHANNEL#BD} {@link PAY_CHANNEL#BD_APP} 百度APP支付
     * {@link PAY_CHANNEL#BD_WEB} 百度PC网页支付 {@link PAY_CHANNEL#BD_WAP} 百度移动网页支付
     **/
    public void setChannel(PAY_CHANNEL channel) {
        this.channel = channel;
    }

    /**
     * 访问字段 {@link #billNo}
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * @param billNo
     * 商户订单号， 8到32个字符内，数字和/或字母组合，确保在商户系统中唯一 (选填)
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 访问字段 {@link #startTime}
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     * 起始时间， Date类型 (选填)
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 访问字段 {@link #endTime}
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     * 结束时间， Date类型 (选填)
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 访问字段 {@link #skip}
     */
    public Integer getSkip() {
        return skip;
    }

    /**
     * @param skip
     * 查询起始位置 默认为0。设置为10，表示忽略满足条件的前10条数据 (选填)
     */
    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    /**
     * 访问字段 {@link #limit}
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit
     * 查询的条数， 默认为10，最大为50。设置为10，表示只查询满足条件的10条数据 (选填)
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 访问字段 {@link #needDetail}
     */
    public Boolean getNeedDetail() {
        return needDetail;
    }

    /**
     * @param needDetail
     * 是否需要返回渠道详细信息，不返回可减少网络开销 (选填)
     */
    public void setNeedDetail(Boolean needDetail) {
        this.needDetail = needDetail;
    }

    /**
     * 访问字段{@link #refundNo}
     */
    public String getRefundNo() {
        return refundNo;
    }

    /**
     * @param refundNo
     * 商户退款单号， 格式为:退款日期(8位) + 流水号(3~24
     * 位)。不可重复，且退款日期必须是当天日期。流水号可以接受数字或英文字符，建议使用数字，但不可接受“000”。 (选填)
     */
    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    /**
     * 访问字段{@link #payResult}
     */
    public Boolean getPayResult() {
        return payResult;
    }

    /**
     * @param payResult
     * 支付成功与否标识。 (选填)
     */
    public void setPayResult(Boolean payResult) {
        this.payResult = payResult;
    }

    /**
     * 访问字段{@link #needApproval}
     */
    public Boolean getNeedApproval() {
        return needApproval;
    }

    /**
     * @param needApproval
     * 是否是预退款。 (选填)
     */
    public void setNeedApproval(Boolean needApproval) {
        this.needApproval = needApproval;
    }

    /**
     * 访问字段{@link #refundResult}
     */
    public Boolean getRefundResult() {
        return refundResult;
    }

    /**
     * @param refundResult
     * 退款成功与否标识。 (选填)
     */
    public void setRefundResult(Boolean refundResult) {
        this.refundResult = refundResult;
    }
}
