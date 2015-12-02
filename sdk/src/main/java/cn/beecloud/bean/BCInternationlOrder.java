package cn.beecloud.bean;

import cn.beecloud.BCEumeration.PAYPAL_CURRENCY;
import cn.beecloud.BCEumeration.PAY_CHANNEL;


/**
 * 境外支付订单类，封装了BeeCloud境外支付订单信息
 * 
 * @author Rui.Feng
 * @since 2015.11.24
 */
public class BCInternationlOrder {

    private String objectId;

    private PAY_CHANNEL channel;

    private Integer totalFee;

    private PAYPAL_CURRENCY currency;

    private String billNo;

    private String title;

    private CreditCardInfo creditCardInfo;

    private String creditCardId;

    private String returnUrl;

    private String url;

    /**
     * 访问字段 {@link #channel}
     */
    public PAY_CHANNEL getChannel() {
        return channel;
    }

    /**
     * @param channel
     * 渠道类型， 根据不同场景选择不同的支付方式，包含： {@link PAY_CHANNEL#PAYPAL_PAYPAL}: 微信公众号二维码支付
     * {@link PAY_CHANNEL#PAYPAL_CREDITCARD}: 微信公众号支付
     * {@link PAY_CHANNEL#PAYPAL_SAVED_CREDITCARD}: 支付宝网页支付 (必填)
     */
    public void setChannel(PAY_CHANNEL channel) {
        this.channel = channel;
    }

    /**
     * 访问字段 {@link #totalFee}
     */
    public Integer getTotalFee() {
        return totalFee;
    }

    /**
     * @param totalFee
     * （必填） 订单总金额，单位为分，正整数
     */
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    /**
     * 访问字段 {@link #currency}
     */
    public PAYPAL_CURRENCY getCurrency() {
        return currency;
    }

    /**
     * @param currency
     * （必填） 货币种类代码，包含：<br/>
     * {@link PAYPAL_CURRENCY#AUD}: Australian dollar <br/>
     * {@link PAYPAL_CURRENCY#BRL}: Brazilian real** <br/>
     * {@link PAYPAL_CURRENCY#CAD}: Canadian dollar <br/>
     * {@link PAYPAL_CURRENCY#CZK}: Czech koruna <br/>
     * {@link PAYPAL_CURRENCY#DKK}: Danish krone<br/>
     * {@link PAYPAL_CURRENCY#EUR}: Euro <br/>
     * {@link PAYPAL_CURRENCY#HKD}: Hong Kong dollar <br/>
     * {@link PAYPAL_CURRENCY#HUF}: Hungarian forint <br/>
     * {@link PAYPAL_CURRENCY#ILS}: Israeli new shekel <br/>
     * {@link PAYPAL_CURRENCY#JPY}: Japanese yen <br/>
     * {@link PAYPAL_CURRENCY#MYR}: Malaysian ringgit <br/>
     * {@link PAYPAL_CURRENCY#MXN}: Mexican peso <br/>
     * {@link PAYPAL_CURRENCY#TWD}: New Taiwan dollar <br/>
     * {@link PAYPAL_CURRENCY#NZD}: New Zealand dollar <br/>
     * {@link PAYPAL_CURRENCY#NOK}: Norwegian krone <br/>
     * {@link PAYPAL_CURRENCY#PHP}: Philippine peso <br/>
     * {@link PAYPAL_CURRENCY#PLN}: Polish złoty <br/>
     * {@link PAYPAL_CURRENCY#GBP}: Pound sterling <br/>
     * {@link PAYPAL_CURRENCY#SGD}: Singapore dollar <br/>
     * {@link PAYPAL_CURRENCY#SEK}: Swedish krona <br/>
     * {@link PAYPAL_CURRENCY#CHF}: Swiss franc <br/>
     * {@link PAYPAL_CURRENCY#THB}: Thai baht <br/>
     * {@link PAYPAL_CURRENCY#TRY}: Turkish lira <br/>
     * {@link PAYPAL_CURRENCY#THB}: Thai baht <br/>
     * {@link PAYPAL_CURRENCY#USD}: United States dollar
     */
    public void setCurrency(PAYPAL_CURRENCY currency) {
        this.currency = currency;
    }

    /**
     * 访问字段 {@link #billNo}
     */
    public String getBillNo() {
        return billNo;
    }

    /**
     * @param billNo
     * （必填） 商户订单号，8到32位数字和/或字母组合，请自行确保在商户系统中唯一，同一订单号不可重复提交，否则会造成订单重复
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 访问字段 {@link #title}
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     * 订单标题， 32个字节内，最长支持16个汉字 (必填)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 访问字段 {@link #creditCardInfo}
     */
    public CreditCardInfo getCreditCardInfo() {
        return creditCardInfo;
    }

    /**
     * @param creditCardInfo
     * （选填）信用卡信息， 当channel 为PAYPAL_CREDITCARD必填
     */
    public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    /**
     * 访问字段 {@link #creditCardId}
     */
    public String getCreditCardId() {
        return creditCardId;
    }

    /**
     * @param creditCardId
     * （选填）信用卡id，当使用PAYPAL_CREDITCARD支付完成后会返回一个credit_card_id
     * 当channel为PAYPAL_SAVED_CREDITCARD时必填
     */
    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    /**
     * 访问字段 {@link #returnUrl}
     */
    public String getReturnUrl() {
        return returnUrl;
    }

    /**
     * @param returnUrl
     * （选填）同步返回页面，支付渠道处理完请求后,当前页面自动跳转到商户网站里指定页面的http路径不包含?及&，
     * 当channel参数为PAYPAL_PAYPAL时为必填
     */
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    /**
     * @return 境外支付订单唯一标识
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * 设置字段 {@link #objectId}
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * @return 当channel 为PAYPAL_PAYPAL时返回，跳转支付的url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置字段 {@link #url}
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
